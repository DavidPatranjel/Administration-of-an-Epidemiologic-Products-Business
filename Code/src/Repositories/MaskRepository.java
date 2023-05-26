package Repositories;

import App.Audit;
import App.DBFunctions;
import App.DataDictionary;
import Models.Mask.Mask;
import Models.Mask.PolycarbonateMask;
import Models.Mask.SurgicalMask;

import java.io.IOException;
import java.sql.*;

public class MaskRepository {
    private static MaskRepository instance;
    private static Audit audit = Audit.getInstance();
    private MaskRepository() { }

    static {
        try {
            instance = new MaskRepository();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating DBFunctions singleton instance");
        }
    }

    public static MaskRepository getInstance() {
        if (instance == null) {
            instance = new MaskRepository();
        }
        return instance;
    }

    public void createTable()
    {
        String query1 = "CREATE TABLE IF NOT EXISTS POLYCARBONATE_MASK " +
                "(id_pcmask SERIAL PRIMARY KEY, " +
                "protection_type varchar(100), " +
                "colour varchar(100), " +
                "nr_folds int, " +
                "price real, " +
                "grip_type varchar(100), " +
                "deleted boolean NOT NULL ); ";

        String query2 = "CREATE TABLE IF NOT EXISTS SURGICAL_MASK " +
                "(id_sgmask SERIAL PRIMARY KEY, " +
                "protection_type varchar(100), " +
                "colour varchar(100), " +
                "nr_folds int, " +
                "price real, " +
                "deleted boolean NOT NULL ); ";

        Statement statement;
        try{
            DBFunctions db = DBFunctions.getInstance();
            Connection conn = db.connect_to_db();
            statement=conn.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            DBFunctions.closeDatabaseConnection();
            audit.logAction("All 2 sanitizer tables have been crated");
            System.out.println("Table Created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addMask(Mask mask)
    {
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {
            if (mask instanceof PolycarbonateMask) {
                String insertAddressSql = "INSERT INTO POLYCARBONATE_MASK(protection_type, colour, nr_folds, price, grip_type, deleted) VALUES(?, ?, ?, ?, ?, ?);";

                PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql, Statement.RETURN_GENERATED_KEYS);
                maskStatement.setString(1, mask.getProtectionType());
                maskStatement.setString(2, mask.getColour());
                maskStatement.setInt(3, mask.getNoFolds());
                maskStatement.setDouble(4,  mask.getPrice());
                maskStatement.setString(5,  ((PolycarbonateMask) mask).getGripType());
                maskStatement.setBoolean(6, false);
                int rowsInserted = maskStatement.executeUpdate();
                ResultSet generatedKeys = maskStatement.getGeneratedKeys();

                DBFunctions.closeDatabaseConnection();

                if (rowsInserted > 0) {
                    System.out.println("Polycarbonate mask inserted successfully.");
                    audit.logAction("Added new Polycarbonate Mask");
                    int maskId;
                    if (generatedKeys.next()) {
                        maskId = generatedKeys.getInt(1);
                        DataDictionary dataDictionary = DataDictionary.getInstance();
                        dataDictionary.addPolycarbonateMasksPrice(maskId, mask.getPrice());
                    }
                } else {
                    audit.logAction("Could not add new Mask");
                    throw new SQLException("Inserting polycarbonate mask failed.");
                }

            } else if (mask instanceof SurgicalMask) {
                String insertAddressSql = "INSERT INTO SURGICAL_MASK(protection_type, colour, nr_folds, price, deleted) VALUES(?, ?, ?, ?, ?);";

                PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql, Statement.RETURN_GENERATED_KEYS);
                maskStatement.setString(1, mask.getProtectionType());
                maskStatement.setString(2, mask.getColour());
                maskStatement.setInt(3, mask.getNoFolds());
                maskStatement.setDouble(4,  mask.getPrice());
                maskStatement.setBoolean(5, false);
                int rowsInserted = maskStatement.executeUpdate();
                ResultSet generatedKeys = maskStatement.getGeneratedKeys();

                DBFunctions.closeDatabaseConnection();

                if (rowsInserted > 0) {
                    System.out.println("Surgical mask inserted successfully.");
                    audit.logAction("Added new Surgical Mask");
                    int maskId;
                    if (generatedKeys.next()) {
                        maskId = generatedKeys.getInt(1);
                        DataDictionary dataDictionary = DataDictionary.getInstance();
                        dataDictionary.addSurgicalMasksPrice(maskId, mask.getPrice());
                    }
                } else {
                    audit.logAction("Could not add new Mask");
                    throw new SQLException("Inserting surgical mask failed.");
                }
            } else {
                System.out.println("Error");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }

    public Boolean printPCMasks()
    {
        String selectSql = "SELECT * FROM POLYCARBONATE_MASK WHERE POLYCARBONATE_MASK.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try (Statement stmt = conn.createStatement()) {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                if (empty) {
                    System.out.println("List of all Polycarbonate masks:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("MaskID: " + resultSet.getString(1));
                System.out.print("Protection type: " + resultSet.getString(2) + " ");
                System.out.println("Grip type: " + resultSet.getString(6));
                System.out.print("Colour: " + resultSet.getString(3) + " ");
                System.out.println("Number of folds: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("----------");
            }
            audit.logAction("Printed all Polycarbonate Masks");
            DBFunctions.closeDatabaseConnection();
            if (empty) {
                System.out.println("No existing polycarbonate masks!");
                return false;
            }
            return true;
        }catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
            return false;
        }
    }

    public Boolean printSGMasks()
    {
        String selectSql = "SELECT * FROM SURGICAL_MASK WHERE SURGICAL_MASK.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try (Statement stmt = conn.createStatement()) {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                if(empty){
                    System.out.println("List of all Surgical masks:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("MaskID: " + resultSet.getString(1));
                System.out.println("Protection type: " + resultSet.getString(2));
                System.out.print("Colour: " + resultSet.getString(3) + " ");
                System.out.println("Number of folds: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("----------");
            }
            audit.logAction("Printed all Surgical Masks");
            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing surgical masks!");
                return false;
            }
            return true;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
            return false;
        }
    }
    public void printMasks() {
        printPCMasks();
        printSGMasks();
    }

    public void showSGMask(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String insertAddressSql = "SELECT * FROM SURGICAL_MASK WHERE id_sgmask = ?";
            PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql);
            maskStatement.setInt(1, ID);
            ResultSet resultSet = maskStatement.executeQuery();
            DBFunctions.closeDatabaseConnection();

            boolean empty = true;
            if (resultSet.next()) {
                empty = false;
                System.out.println("Protection type: " + resultSet.getString(2));
                System.out.print("Colour: " + resultSet.getString(3) + " ");
                System.out.println("Number of folds: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("Was it deleted? " + resultSet.getString(6));
                audit.logAction("Showed Surgical Mask with given ID");
            }

            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Surgical Mask with this ID!");
                audit.logAction("No Surgical Mask found for show with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void showPCMask(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String insertAddressSql = "SELECT * FROM POLYCARBONATE_MASK WHERE id_pcmask = ?";
            PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql);
            maskStatement.setInt(1, ID);
            ResultSet resultSet = maskStatement.executeQuery();
            DBFunctions.closeDatabaseConnection();

            boolean empty = true;
            if (resultSet.next()) {
                empty = false;
                System.out.print("Protection type: " + resultSet.getString(2) + " ");
                System.out.println("Grip type: " + resultSet.getString(6));
                System.out.print("Colour: " + resultSet.getString(3) + " ");
                System.out.println("Number of folds: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("Was it deleted? " + resultSet.getString(7));
                audit.logAction("Showed Polycarbonate Mask with given ID");
            }

            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Polycarbonate Mask with this ID!");
                audit.logAction("No Polycarbonate Mask found for show with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }

    public void deleteSGMask(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String insertAddressSql = "UPDATE SURGICAL_MASK SET deleted = true WHERE id_sgmask = ?";
            PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql);
            maskStatement.setInt(1, ID);
            int rowsAffected = maskStatement.executeUpdate();
            DBFunctions.closeDatabaseConnection();
            if (rowsAffected > 0) {
                System.out.println("Surgical Mask deleted successfully.");
                audit.logAction("Surgical Mask deleted successfully");
                DataDictionary dataDictionary = DataDictionary.getInstance();
                dataDictionary.removeSurgicalMasksPrice(ID);
            } else {
                System.out.println("No Surgical Mask found with the given ID.");
                audit.logAction("No Surgical Mask found for delete with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void deletePCMask(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String insertAddressSql = "UPDATE POLYCARBONATE_MASK SET deleted = true WHERE id_pcmask = ?";
            PreparedStatement maskStatement = conn.prepareStatement(insertAddressSql);
            maskStatement.setInt(1, ID);
            int rowsAffected = maskStatement.executeUpdate();
            DBFunctions.closeDatabaseConnection();
            if (rowsAffected > 0) {
                System.out.println("Polycarbonate Mask deleted successfully.");
                audit.logAction("Polycarbonate Mask deleted successfully");
                DataDictionary dataDictionary = DataDictionary.getInstance();
                dataDictionary.removePolycarbonateMasksPrice(ID);
            } else {
                System.out.println("No Polycarbonate Mask found with the given ID.");
                audit.logAction("No Polycarbonate Mask found for delete with the given ID");
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
