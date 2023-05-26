package Repositories;

import App.Audit;
import App.DBFunctions;
import App.DataDictionary;
import Models.Sanitizer.BacteriaSanitizer;
import Models.Sanitizer.FungalSanitizer;
import Models.Sanitizer.Sanitizer;
import Models.Sanitizer.VirusSanitizer;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SanitizerRepository {
    private static SanitizerRepository instance;
    private static Audit audit = Audit.getInstance();
    private SanitizerRepository() { }

    static {
        try {
            instance = new SanitizerRepository();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating DBFunctions singleton instance");
        }
    }

    public static SanitizerRepository getInstance() {
        if (instance == null) {
            instance = new SanitizerRepository();
        }
        return instance;
    }

    public void createTable() {
        String query1 = "CREATE TABLE IF NOT EXISTS BACTERIA_SANITIZER " +
                "(id_bacteriastz SERIAL PRIMARY KEY, " +
                "killed_organisms int, " +
                "ingredients TEXT[] NOT NULL, " +
                "surfaces TEXT[] NOT NULL, " +
                "price real, " +
                "efficiency real, " +
                "deleted boolean NOT NULL ); ";

        String query2 = "CREATE TABLE IF NOT EXISTS FUNGAL_SANITIZER " +
                "(id_fungalstz SERIAL PRIMARY KEY, " +
                "killed_organisms int, " +
                "ingredients TEXT[] NOT NULL, " +
                "surfaces TEXT[] NOT NULL, " +
                "price real, " +
                "efficiency real, " +
                "deleted boolean NOT NULL ); ";

        String query3 = "CREATE TABLE IF NOT EXISTS VIRUS_SANITIZER " +
                "(id_virusstz SERIAL PRIMARY KEY, " +
                "killed_organisms int, " +
                "ingredients TEXT[] NOT NULL, " +
                "surfaces TEXT[] NOT NULL, " +
                "price real, " +
                "efficiency real, " +
                "deleted boolean NOT NULL ); " ;

        Statement statement;
        try{
            DBFunctions db = DBFunctions.getInstance();
            Connection conn = db.connect_to_db();
            statement=conn.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            System.out.println("Table Created");
            audit.logAction("All 3 sanitizer tables have been crated");
            DBFunctions.closeDatabaseConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addSanitizer(Sanitizer sanitizer) {

        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        String insertSanitizerSql, ansmsg, errmsg;
        int op;
        try {
            if (sanitizer instanceof BacteriaSanitizer) {
                insertSanitizerSql = "INSERT INTO BACTERIA_SANITIZER(killed_organisms, ingredients, surfaces, price, efficiency, deleted) VALUES(?, ?, ?, ?, ?, ?);";
                ansmsg = "Bacteria sanitizer inserted successfully.";
                errmsg = "Inserting bacteria sanitizer failed.";
                op = 1;
            } else if (sanitizer instanceof FungalSanitizer) {
                insertSanitizerSql = "INSERT INTO FUNGAL_SANITIZER(killed_organisms, ingredients, surfaces, price, efficiency, deleted)  VALUES(?, ?, ?, ?, ?, ?);";
                ansmsg = "Fungal sanitizer inserted successfully.";
                errmsg = "Inserting fungal sanitizer failed.";
                op = 2;
            }else if (sanitizer instanceof VirusSanitizer) {
                insertSanitizerSql = "INSERT INTO VIRUS_SANITIZER(killed_organisms, ingredients, surfaces, price, efficiency, deleted)  VALUES(?, ?, ?, ?, ?, ?);";
                ansmsg = "Virus sanitizer inserted successfully.";
                errmsg = "Inserting virus sanitizer failed.";
                op = 3;
            } else {
                System.out.println("Error");
                return;
            }
            String[] auxIngredients = sanitizer.getIngredients().toArray(new String[0]);
            String[] auxSurfaces = sanitizer.getSurfaces().toArray(new String[0]);

            PreparedStatement sanitizerStatement = conn.prepareStatement(insertSanitizerSql, Statement.RETURN_GENERATED_KEYS);
            sanitizerStatement.setInt(1, sanitizer.getNoKilledOrganisms());
            sanitizerStatement.setArray(2, conn.createArrayOf("text", auxIngredients));
            sanitizerStatement.setArray(3, conn.createArrayOf("text", auxSurfaces));
            sanitizerStatement.setDouble(4,  sanitizer.getPrice());
            sanitizerStatement.setDouble(5,  sanitizer.getEfficiency());
            sanitizerStatement.setBoolean(6, false);

            int rowsInserted = sanitizerStatement.executeUpdate();
            ResultSet generatedKeys = sanitizerStatement.getGeneratedKeys();
            DBFunctions.closeDatabaseConnection();

            if (rowsInserted > 0) {
                System.out.println(ansmsg);
                int sanitizerId;
                if (generatedKeys.next()) {
                    sanitizerId = generatedKeys.getInt(1);
                    DataDictionary dataDictionary = DataDictionary.getInstance();
                    if(op == 1) {
                        dataDictionary.addBacterialSanitizersPrice(sanitizerId, sanitizer.getPrice());
                        audit.logAction("Added new Bacteria Sanitizer");
                    }
                    else if(op == 2) {
                        dataDictionary.addFungalSanitizersPrice(sanitizerId, sanitizer.getPrice());
                        audit.logAction("Added new Fungal Sanitizer");
                    }
                    else {
                        dataDictionary.addVirusSanitizersPrice(sanitizerId, sanitizer.getPrice());
                        audit.logAction("Added new Virus Sanitizer");
                    }
                }
            } else {
                audit.logAction("Could not add new Sanitizer");
                throw new SQLException(errmsg);
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }

    public Boolean printBCSanitizers() {
        String selectSql = "SELECT * FROM BACTERIA_SANITIZER WHERE BACTERIA_SANITIZER.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement sanitizerStatement = conn.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = sanitizerStatement.executeQuery(selectSql);
            while (resultSet.next())
            {
                if(empty){
                    System.out.println("List of all Bacteria Sanitizers:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("SanitizerID: " + resultSet.getString(1));
                System.out.print("Killed Organisms: " + resultSet.getString(2) + " ");
                System.out.println("Efficiency: " + resultSet.getString(6));
                System.out.println("Ingredients: " + resultSet.getString(3));
                System.out.println("Surfaces: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("----------");
            }
            DBFunctions.closeDatabaseConnection();
            audit.logAction("Printed all Bacteria Sanitizers");
            if (empty)
            {
                System.out.println("No existing Bacteria Sanitizers!");
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

    public Boolean printFGSanitizers() {
        String selectSql = "SELECT * FROM FUNGAL_SANITIZER WHERE FUNGAL_SANITIZER.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement sanitizerStatement = conn.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = sanitizerStatement.executeQuery(selectSql);
            while (resultSet.next())
            {
                if(empty){
                    System.out.println("List of all Fungal Sanitizers:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("SanitizerID: " + resultSet.getString(1));
                System.out.print("Killed Organisms: " + resultSet.getString(2) + " ");
                System.out.println("Efficiency: " + resultSet.getString(6));
                System.out.println("Ingredients: " + resultSet.getString(3));
                System.out.println("Surfaces: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("----------");
            }

            DBFunctions.closeDatabaseConnection();
            audit.logAction("Printed all Fungal Sanitizers");

            if (empty)
            {
                System.out.println("No existing Fungal Sanitizers!");
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

    public Boolean printVSSanitizers() {
        String selectSql = "SELECT * FROM VIRUS_SANITIZER WHERE VIRUS_SANITIZER.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement sanitizerStatement = conn.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = sanitizerStatement.executeQuery(selectSql);
            while (resultSet.next())
            {
                if(empty){
                    System.out.println("List of all Virus Sanitizers:");
                    System.out.println("----------");
                }
                empty = false;
                System.out.println("SanitizerID: " + resultSet.getString(1));
                System.out.print("Killed Organisms: " + resultSet.getString(2) + " ");
                System.out.println("Efficiency: " + resultSet.getString(6));
                System.out.println("Ingredients: " + resultSet.getString(3));
                System.out.println("Surfaces: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("----------");
            }

            DBFunctions.closeDatabaseConnection();
            audit.logAction("Printed all Virus Sanitizers");
            if (empty)
            {
                System.out.println("No existing Virus Sanitizers!");
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
    public void printSanitizers() {
        printBCSanitizers();
        printFGSanitizers();
        printVSSanitizers();
    }

    public void showBCSanitizer(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String deleteSanitizerSql = "SELECT * FROM BACTERIA_SANITIZER WHERE id_bacteriastz = ?";
            PreparedStatement sanitizerStatement = conn.prepareStatement(deleteSanitizerSql);
            sanitizerStatement.setInt(1, ID);
            ResultSet resultSet = sanitizerStatement.executeQuery();
            DBFunctions.closeDatabaseConnection();

            boolean empty = true;
            if (resultSet.next()) {
                empty = false;
                System.out.print("Killed Organisms: " + resultSet.getString(2) + " ");
                System.out.println("Efficiency: " + resultSet.getString(6));
                System.out.println("Ingredients: " + resultSet.getString(3));
                System.out.println("Surfaces: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("Was it deleted? " + resultSet.getString(7));
                audit.logAction("Showed Bacteria Sanitizer with given ID");
            }

            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Bacteria Sanitizer with this ID!");
                audit.logAction("No Bacteria Sanitizer found for show with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void showFGSanitizer(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {
            String deleteSanitizerSql = "SELECT * FROM FUNGAL_SANITIZER WHERE id_fungalstz = ?";
            PreparedStatement sanitizerStatement = conn.prepareStatement(deleteSanitizerSql);
            sanitizerStatement.setInt(1, ID);
            ResultSet resultSet = sanitizerStatement.executeQuery();
            DBFunctions.closeDatabaseConnection();

            boolean empty = true;
            if (resultSet.next()) {
                empty = false;
                System.out.print("Killed Organisms: " + resultSet.getString(2) + " ");
                System.out.println("Efficiency: " + resultSet.getString(6));
                System.out.println("Ingredients: " + resultSet.getString(3));
                System.out.println("Surfaces: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("Was it deleted? " + resultSet.getString(7));
                audit.logAction("Showed Fungal Sanitizer with given ID");
            }

            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Fungal Sanitizer with this ID!");
                audit.logAction("No Fungal Sanitizer found for show with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void showVSSanitizer(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String deleteSanitizerSql = "SELECT * FROM VIRUS_SANITIZER WHERE id_virusstz = ?";
            PreparedStatement sanitizerStatement = conn.prepareStatement(deleteSanitizerSql);
            sanitizerStatement.setInt(1, ID);

            ResultSet resultSet = sanitizerStatement.executeQuery();
            DBFunctions.closeDatabaseConnection();

            boolean empty = true;
            if (resultSet.next()) {
                empty = false;
                System.out.print("Killed Organisms: " + resultSet.getString(2) + " ");
                System.out.println("Efficiency: " + resultSet.getString(6));
                System.out.println("Ingredients: " + resultSet.getString(3));
                System.out.println("Surfaces: " + resultSet.getString(4));
                System.out.println("Price: " + resultSet.getString(5));
                System.out.println("Was it deleted? " + resultSet.getString(7));
                audit.logAction("Showed Virus Sanitizer with given ID");
            }

            DBFunctions.closeDatabaseConnection();
            if (empty)
            {
                System.out.println("No existing Virus Sanitizer with this ID!");
                audit.logAction("No Virus Sanitizer found for show with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }

    public void deleteBCSanitizer(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String deleteSanitizerSql = "UPDATE BACTERIA_SANITIZER SET deleted = true WHERE id_bacteriastz = ?";
            PreparedStatement sanitizerStatement = conn.prepareStatement(deleteSanitizerSql);
            sanitizerStatement.setInt(1, ID);
            int rowsAffected = sanitizerStatement.executeUpdate();
            DBFunctions.closeDatabaseConnection();
            if (rowsAffected > 0) {
                System.out.println("Bacteria Sanitizer deleted successfully.");
                audit.logAction("Bacteria Sanitizer deleted successfully");
                DataDictionary dataDictionary = DataDictionary.getInstance();
                dataDictionary.removeBacterialSanitizersPrice(ID);
            } else {
                System.out.println("No Bacteria Sanitizer found with the given ID.");
                audit.logAction("No Bacteria Sanitizer found for delete with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void deleteFGSanitizer(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {
            String deleteSanitizerSql = "UPDATE FUNGAL_SANITIZER SET deleted = true WHERE id_fungalstz = ?";
            PreparedStatement sanitizerStatement = conn.prepareStatement(deleteSanitizerSql);
            sanitizerStatement.setInt(1, ID);
            int rowsAffected = sanitizerStatement.executeUpdate();
            DBFunctions.closeDatabaseConnection();
            if (rowsAffected > 0) {
                System.out.println("Fungal Sanitizer deleted successfully.");
                audit.logAction("Fungal Sanitizer deleted successfully");
                DataDictionary dataDictionary = DataDictionary.getInstance();
                dataDictionary.removeFungalSanitizersPrice(ID);
            } else {
                System.out.println("No Fungal Sanitizer found with the given ID.");
                audit.logAction("No Fungal Sanitizer found for delete with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }
    public void deleteVSSanitizer(int ID){
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try {

            String deleteSanitizerSql = "UPDATE VIRUS_SANITIZER SET deleted = true WHERE id_virusstz = ?";
            PreparedStatement sanitizerStatement = conn.prepareStatement(deleteSanitizerSql);
            sanitizerStatement.setInt(1, ID);
            int rowsAffected = sanitizerStatement.executeUpdate();
            DBFunctions.closeDatabaseConnection();
            if (rowsAffected > 0) {
                System.out.println("Virus Sanitizer deleted successfully.");
                audit.logAction("Virus Sanitizer deleted successfully");
                DataDictionary dataDictionary = DataDictionary.getInstance();
                dataDictionary.removeVirusSanitizerPrice(ID);
            } else {
                System.out.println("No Virus Sanitizer found with the given ID.");
                audit.logAction("No Virus Sanitizer found for delete with the given ID");
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println("Error with audit: " + e);
        }
    }

    private Sanitizer[] getBCSanitizers() {
        Sanitizer[] allSanitizers = null;
        String selectSql = "SELECT * FROM BACTERIA_SANITIZER WHERE BACTERIA_SANITIZER.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();
        try (Statement sanitizerStatement = conn.createStatement())
        {
            ResultSet resultSet = sanitizerStatement.executeQuery(selectSql);
            while (resultSet.next())
            {
                int ko = resultSet.getInt(2);
                String[] ingr = (String[]) resultSet.getArray(3).getArray();
                String[] surf = (String[]) resultSet.getArray(4).getArray();
                Set<String> allingr = new HashSet<>(Arrays.asList(ingr));
                Set<String> allsurf = new HashSet<>(Arrays.asList(surf));
                Sanitizer s = new BacteriaSanitizer(ko, allingr, allsurf);
                if (allSanitizers == null) {
                    allSanitizers = new Sanitizer[1];
                    allSanitizers[0] = s;
                } else {
                    allSanitizers = Arrays.copyOf(allSanitizers, allSanitizers.length + 1);
                    allSanitizers[allSanitizers.length - 1] = s;
                }
            }
            DBFunctions.closeDatabaseConnection();
            if(allSanitizers == null)
                return null;
            return Arrays.copyOf(allSanitizers, allSanitizers.length);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private Sanitizer[] getFGSanitizers() {
        Sanitizer[] allSanitizers = null;
        String selectSql = "SELECT * FROM FUNGAL_SANITIZER WHERE FUNGAL_SANITIZER.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement sanitizerStatement = conn.createStatement())
        {
            ResultSet resultSet = sanitizerStatement.executeQuery(selectSql);
            while (resultSet.next())
            {
                int ko = resultSet.getInt(2);
                String[] ingr = (String[]) resultSet.getArray(3).getArray();
                String[] surf = (String[]) resultSet.getArray(4).getArray();
                Set<String> allingr = new HashSet<>(Arrays.asList(ingr));
                Set<String> allsurf = new HashSet<>(Arrays.asList(surf));
                Sanitizer s = new FungalSanitizer(ko, allingr, allsurf);
                if (allSanitizers == null) {
                    allSanitizers = new Sanitizer[1];
                    allSanitizers[0] = s;
                } else {
                    allSanitizers = Arrays.copyOf(allSanitizers, allSanitizers.length + 1);
                    allSanitizers[allSanitizers.length - 1] = s;
                }
            }
            DBFunctions.closeDatabaseConnection();
            if(allSanitizers == null)
                return null;
            return Arrays.copyOf(allSanitizers, allSanitizers.length);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private Sanitizer[] getVSSanitizers() {
        Sanitizer[] allSanitizers = null;
        String selectSql = "SELECT * FROM VIRUS_SANITIZER WHERE VIRUS_SANITIZER.DELETED = FALSE;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement sanitizerStatement = conn.createStatement())
        {
            ResultSet resultSet = sanitizerStatement.executeQuery(selectSql);
            while (resultSet.next())
            {
                int ko = resultSet.getInt(2);
                String[] ingr = (String[]) resultSet.getArray(3).getArray();
                String[] surf = (String[]) resultSet.getArray(4).getArray();
                Set<String> allingr = new HashSet<>(Arrays.asList(ingr));
                Set<String> allsurf = new HashSet<>(Arrays.asList(surf));
                Sanitizer s = new VirusSanitizer(ko, allingr, allsurf);
                if (allSanitizers == null) {
                    allSanitizers = new Sanitizer[1];
                    allSanitizers[0] = s;
                } else {
                    allSanitizers = Arrays.copyOf(allSanitizers, allSanitizers.length + 1);
                    allSanitizers[allSanitizers.length - 1] = s;
                }
            }
            DBFunctions.closeDatabaseConnection();
            if(allSanitizers == null)
                return null;
            return Arrays.copyOf(allSanitizers, allSanitizers.length);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public Sanitizer[] getAllSanitizers(){
        Sanitizer[] allSanitizers = null;

        Sanitizer[] ans1 = getBCSanitizers();
        if (ans1 != null) {
            for (Sanitizer s : ans1) {
                if (allSanitizers == null) {
                    allSanitizers = new Sanitizer[1];
                    allSanitizers[0] = s;
                } else {
                    allSanitizers = Arrays.copyOf(allSanitizers, allSanitizers.length + 1);
                    allSanitizers[allSanitizers.length - 1] = s;
                }
            }
        }

        Sanitizer[] ans2 = getFGSanitizers();
        if (ans2 != null) {
            for (Sanitizer s : ans2) {
                if (allSanitizers == null) {
                    allSanitizers = new Sanitizer[1];
                    allSanitizers[0] = s;
                } else {
                    allSanitizers = Arrays.copyOf(allSanitizers, allSanitizers.length + 1);
                    allSanitizers[allSanitizers.length - 1] = s;
                }
            }
        }

        Sanitizer[] ans3 = getVSSanitizers();
        if (ans3 != null) {
            for (Sanitizer s : ans3) {
                if (allSanitizers == null) {
                    allSanitizers = new Sanitizer[1];
                    allSanitizers[0] = s;
                } else {
                    allSanitizers = Arrays.copyOf(allSanitizers, allSanitizers.length + 1);
                    allSanitizers[allSanitizers.length - 1] = s;
                }
            }
        }

        try{
            audit.logAction("Got all sanitizers from the DB");
        }catch (IOException e){
            System.out.println("Error with audit: " + e);
        }

        if(allSanitizers == null)
            return null;
        return Arrays.copyOf(allSanitizers, allSanitizers.length);
    }
}
