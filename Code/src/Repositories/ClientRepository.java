package Repositories;

import App.Audit;
import App.DBFunctions;
import App.DataDictionary;
import Models.Person.Address;
import Models.Person.Client;

import java.io.IOException;
import java.sql.*;

public class ClientRepository {
    private static ClientRepository instance;
    private static Audit audit = Audit.getInstance();

    private ClientRepository() { }

    static {
        try {
            instance = new ClientRepository();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating DBFunctions singleton instance");
        }
    }

    public static ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }

    public void createTable()
    {
        String query1 = "CREATE TABLE IF NOT EXISTS ADDRESS " +
                "(id_address SERIAL PRIMARY KEY, " +
                "country varchar(100), " +
                "city varchar(15), " +
                "street varchar(40), " +
                "number_address int); ";

        String query2 = "CREATE TABLE IF NOT EXISTS CLIENT " +
                "(id_client SERIAL PRIMARY KEY, " +
                "clientName varchar(100), " +
                "CUI varchar(15), " +
                "id_address int);" +
                "ALTER TABLE CLIENT " +
                "ADD FOREIGN KEY (id_address) REFERENCES ADDRESS(id_address) ON DELETE CASCADE;";

        Statement statement;
        try{
            DBFunctions db = DBFunctions.getInstance();
            Connection conn = db.connect_to_db();
            statement=conn.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            System.out.println("Tables Created");
            audit.logAction("Client and address tables have been crated");
            DBFunctions.closeDatabaseConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addClient(Client client)
    {
        Address a = client.getAddress();
        String insertAddressSql = "INSERT INTO ADDRESS(country, city, street, number_address) VALUES(?, ?, ?, ?);";

        try
        {
            DBFunctions db = DBFunctions.getInstance();
            Connection conn = db.connect_to_db();
            PreparedStatement addressStatement = conn.prepareStatement(insertAddressSql, Statement.RETURN_GENERATED_KEYS);
            addressStatement.setString(1, a.getCountry());
            addressStatement.setString(2, a.getCity());
            addressStatement.setString(3, a.getStreet());
            addressStatement.setInt(4,  a.getNumber());

            int rows = addressStatement.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Inserting address failed, no rows affected.");
            }

            ResultSet generatedKeys = addressStatement.getGeneratedKeys();
            int addressId;
            if (generatedKeys.next()) {
                addressId = generatedKeys.getInt(1);
                String insertClientSql = "INSERT INTO CLIENT(clientName, CUI, id_address) VALUES(?, ?, ?);";
                PreparedStatement clientStatement = conn.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS);
                clientStatement.setString(1, client.getClientName());
                clientStatement.setString(2, client.getCUI());
                clientStatement.setInt(3,  addressId);

                int rowsInserted = clientStatement.executeUpdate();
                generatedKeys = clientStatement.getGeneratedKeys();

                DBFunctions.closeDatabaseConnection();
                if (rowsInserted > 0) {
                    System.out.println("Client inserted successfully.");
                    audit.logAction("Added new Address and Client");
                    int clientId;
                    if (generatedKeys.next()) {
                        clientId = generatedKeys.getInt(1);
                        DataDictionary dataDictionary = DataDictionary.getInstance();
                        dataDictionary.addClients(clientId);
                    }
                } else {
                    throw new SQLException("Inserting client failed.");
                }
            } else {
                throw new SQLException("Inserting address failed, no ID obtained.");
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void printClients()
    {
        String selectSql = "SELECT * FROM CLIENT INNER JOIN ADDRESS ON CLIENT.ID_ADDRESS = ADDRESS.ID_ADDRESS;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement stmt = conn.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next())
            {
                if(empty){
                    System.out.println("List of all Clients:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("ClientID: " + resultSet.getString(1));
                System.out.print("Client Name: " + resultSet.getString(2) + " ");
                System.out.println("CUI: " + resultSet.getString(3));
                System.out.print("Address: " + resultSet.getString(6) + " ");
                System.out.println(resultSet.getString(7) + " " + resultSet.getString(8) + " " + resultSet.getString(9));
                System.out.println("----------");
            }
            audit.logAction("Printed all Clients");
            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Clients!");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }

    private void updateAddress(int ID, Address a)
    {
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String updateAddressSql = "UPDATE ADDRESS SET country = ?, city = ?, street = ?, number_address = ? WHERE id_address = ?";
            PreparedStatement addressStatement = conn.prepareStatement(updateAddressSql);
            addressStatement.setString(1, a.getCountry());
            addressStatement.setString(2, a.getCity());
            addressStatement.setString(3, a.getStreet());
            addressStatement.setInt(4,  a.getNumber());
            addressStatement.setInt(5, ID);

            addressStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void updateClient(int ID, Client client)
    {
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String insertAddressSql = "SELECT * FROM CLIENT WHERE id_client = ?";
            PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql);
            maskStatement.setInt(1, ID);
            ResultSet resultSet = maskStatement.executeQuery();

            boolean empty = true;
            if (resultSet.next()) {
                empty = false;
                int ID_ADDRESS = resultSet.getInt(4);
                updateAddress(ID_ADDRESS, client.getAddress());
                String updateClientSql = "UPDATE CLIENT SET clientname = ?, cui = ? WHERE id_client = ?";
                PreparedStatement clientStatement = conn.prepareStatement(updateClientSql);
                clientStatement.setString(1, client.getClientName());
                clientStatement.setString(2, client.getCUI());
                clientStatement.setInt(3, ID);

                clientStatement.executeUpdate();
                audit.logAction("The client and address were updated");
                System.out.println("The client and address were updated.");
            }

            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Client with this ID!");
                audit.logAction("No Client found for update with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
}
