package Repositories;

import App.Audit;
import App.DBFunctions;
import Models.Order.Acquisition;

import java.io.IOException;
import java.sql.*;

public class AcquisitionRepository {
    private static AcquisitionRepository instance;
    private static Audit audit = Audit.getInstance();

    private AcquisitionRepository() { }

    static {
        try {
            instance = new AcquisitionRepository();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating DBFunctions singleton instance");
        }
    }

    public static AcquisitionRepository getInstance() {
        if (instance == null) {
            instance = new AcquisitionRepository();
        }
        return instance;
    }

    public void createTable()
    {
        String query1 = "CREATE TABLE IF NOT EXISTS AQUISITION " +
                "(id_aquisition SERIAL PRIMARY KEY, " +
                "totalPrice real, " +
                "date_aq date, " +
                "special_order boolean, " +
                "special_order_description varchar(200), " +
                "ids_surgicalmasks int[]," +
                "ids_polycarbonatemasks int[]," +
                "ids_bacteriasanitizers int[]," +
                "ids_fungalsanitizers int[]," +
                "ids_virussanitizers int[]," +
                "id_client int);" +
                "ALTER TABLE AQUISITION " +
                "ADD FOREIGN KEY (id_client) REFERENCES CLIENT(id_client) ON DELETE SET NULL;";

        Statement statement;
        try{
            DBFunctions db = DBFunctions.getInstance();
            Connection conn = db.connect_to_db();
            statement=conn.createStatement();
            statement.executeUpdate(query1);
            DBFunctions.closeDatabaseConnection();
            audit.logAction("Acquisition table has been crated");
            System.out.println("Table Created");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void addAcquisition(Acquisition acquisition)
    {
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String insertAcquisitionSql = "INSERT INTO AQUISITION(totalPrice, date_aq, special_order, special_order_description, " +
                    "ids_surgicalmasks, ids_polycarbonatemasks, ids_bacteriasanitizers, ids_fungalsanitizers, " +
                    "ids_virussanitizers, id_client) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            java.util.Date utilDate = acquisition.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            PreparedStatement acquisitionStatement = conn.prepareStatement(insertAcquisitionSql, Statement.RETURN_GENERATED_KEYS);
            acquisitionStatement.setDouble(1, acquisition.getTotalPrice());
            acquisitionStatement.setDate(2, sqlDate);
            acquisitionStatement.setBoolean(3, acquisition.getSpecialOrder());
            acquisitionStatement.setString(4, acquisition.getSpecialOrderDescription());
            acquisitionStatement.setArray(5, conn.createArrayOf("int", acquisition.getSgMasksIds()));
            acquisitionStatement.setArray(6, conn.createArrayOf("int", acquisition.getPcMasksIds()));
            acquisitionStatement.setArray(7, conn.createArrayOf("int", acquisition.getBcSanitizersIds()));
            acquisitionStatement.setArray(8, conn.createArrayOf("int", acquisition.getFgSanitizersIds()));
            acquisitionStatement.setArray(9, conn.createArrayOf("int", acquisition.getVsSanitizersIds()));
            acquisitionStatement.setInt(10, acquisition.getClientId());

            int rowsInserted = acquisitionStatement.executeUpdate();
            DBFunctions.closeDatabaseConnection();

            if (rowsInserted > 0) {
                System.out.println("Acquisition inserted successfully.");
                audit.logAction("Added new Acquisition");
            } else {
                throw new SQLException("Inserting acquisition failed.");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void printAquisitions() {
        String selectSql = "SELECT * FROM AQUISITION;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement stmt = conn.createStatement()) {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                if (empty) {
                    System.out.println("List of all Aquisitions:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("AcquisitionID: " + resultSet.getString(1));
                System.out.print("Total price: " + resultSet.getString(2) + " ");
                System.out.println("Aquisition date: " + resultSet.getString(3));
                if (resultSet.getBoolean(4)) {
                    System.out.println("Special command description: " + resultSet.getString(5));
                }
                System.out.println("ClientID: " + resultSet.getString(11));
                System.out.println("Surgical masks IDs: " + resultSet.getString(6));
                System.out.println("Polycarbonate masks IDs: " + resultSet.getString(7));
                System.out.println("Bacteria sanitizers IDs: " + resultSet.getString(8));
                System.out.println("Fungal sanitizers IDs: " + resultSet.getString(9));
                System.out.println("Virus sanitizers IDs: " + resultSet.getString(10));
                System.out.println("----------");
            }
            DBFunctions.closeDatabaseConnection();
            audit.logAction("Printed all Bacteria Sanitizers");
            if (empty) {
                System.out.println("No existing acquisitions!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error with audit: " + e);
        }
    }

    public double getAcquisitionMonthYear(int month, int year) {
        String selectSql = "SELECT totalPrice FROM AQUISITION WHERE EXTRACT(MONTH FROM date_aq) = ? AND EXTRACT(YEAR FROM date_aq) = ?;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        int ans = 0;
        try {
            PreparedStatement acquisitionStatement = conn.prepareStatement(selectSql);
            acquisitionStatement.setInt(1, month);
            acquisitionStatement.setInt(2, year);

            boolean empty = true;
            ResultSet resultSet = acquisitionStatement.executeQuery();
            while (resultSet.next()) {
                empty = false;
                ans += resultSet.getInt(1);
            }

            if (empty) {
                System.out.println("No existing acquisitions!");
                audit.logAction("Called total income in a given year and month: No data found!");
                return -1;
            }
            audit.logAction("Called total income in a given year and month");
            return ans;
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        } catch (IOException e) {
            System.out.println("Error with audit: " + e);
            return -1;
        }
    }

    public double getAcquisitionYear(int year)
    {
        String selectSql = "SELECT totalPrice FROM AQUISITION WHERE EXTRACT(YEAR FROM date_aq) = ?;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        int ans = 0;
        try {
            PreparedStatement acquisitionStatement = conn.prepareStatement(selectSql);
            acquisitionStatement.setInt(1, year);

            boolean empty = true;
            ResultSet resultSet = acquisitionStatement.executeQuery();
            while (resultSet.next())
            {
                empty = false;
                ans += resultSet.getInt(1);
            }

            if (empty)
            {
                System.out.println("No existing acquisitions!");
                audit.logAction("Called vta in a given year: No data found!");
                return -1;
            }
            audit.logAction("Called vta in a given year");
            return 0.19 * ans;
        }
        catch (SQLException e){
            System.out.println(e);
            return -1;
        }
        catch (IOException e) {
            System.out.println("Error with audit: " + e);
            return -1;
        }
    }
}
