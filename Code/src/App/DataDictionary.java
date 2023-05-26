package App;

import Repositories.AcquisitionRepository;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;
public class DataDictionary {
    private Hashtable<Integer, Double> surgicalMasksPrice;
    private Hashtable<Integer, Double> polycarbonateMasksPrice;
    private Hashtable<Integer, Double> bacterialSanitizersPrice;
    private Hashtable<Integer, Double> fungalSanitizersPrice;
    private Hashtable<Integer, Double> virusSanitizersPrice;
    private Hashtable<Integer, Boolean> clients;

    private static DataDictionary instance;

    private DataDictionary() {
        surgicalMasksPrice = new Hashtable<>();
        polycarbonateMasksPrice = new Hashtable<>();
        bacterialSanitizersPrice = new Hashtable<>();
        fungalSanitizersPrice = new Hashtable<>();
        virusSanitizersPrice = new Hashtable<>();
        clients = new Hashtable<>();

        String selectSGMSql = "SELECT * FROM SURGICAL_MASK WHERE SURGICAL_MASK.DELETED = FALSE;";
        String selectPCMSql = "SELECT * FROM POLYCARBONATE_MASK WHERE POLYCARBONATE_MASK.DELETED = FALSE;";
        String selectBAS = "SELECT * FROM BACTERIA_SANITIZER WHERE BACTERIA_SANITIZER.DELETED = FALSE;";
        String selectFGS = "SELECT * FROM FUNGAL_SANITIZER WHERE FUNGAL_SANITIZER.DELETED = FALSE;";
        String selectVISSql = "SELECT * FROM VIRUS_SANITIZER WHERE VIRUS_SANITIZER.DELETED = FALSE;";
        String selectCLSql = "SELECT * FROM CLIENT;";
        DBFunctions db = DBFunctions.getInstance();
        Connection conn = db.connect_to_db();

        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectSGMSql);
            while (resultSet.next()) {
                Integer key=resultSet.getInt(1);
                Double pret=resultSet.getDouble(5);
                addSurgicalMasksPrice(key, pret);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectPCMSql);
            while (resultSet.next()) {
                Integer key=resultSet.getInt(1);
                Double pret=resultSet.getDouble(5);
                addPolycarbonateMasksPrice(key, pret);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectBAS);
            while (resultSet.next()) {
                Integer key=resultSet.getInt(1);
                Double pret=resultSet.getDouble(5);
                addBacterialSanitizersPrice(key, pret);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectFGS);
            while (resultSet.next()) {
                Integer key=resultSet.getInt(1);
                Double pret=resultSet.getDouble(5);
                addFungalSanitizersPrice(key, pret);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectVISSql);
            while (resultSet.next()) {
                Integer key=resultSet.getInt(1);
                Double pret=resultSet.getDouble(5);
                addVirusSanitizersPrice(key, pret);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectCLSql);
            while (resultSet.next()) {
                Integer key=resultSet.getInt(1);
                addClients(key);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        DBFunctions.closeDatabaseConnection();
    }

    static {
        try {
            instance = new DataDictionary();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating DataDictionary singleton instance");
        }
    }

    public static DataDictionary getInstance() {
        if (instance == null) {
            instance = new DataDictionary();
        }
        return instance;
    }

    public Double getSurgicalMasksPrice(Integer id) {
        return surgicalMasksPrice.get(id);
    }

    public Double getPolycarbonateMasksPrice(Integer id) {
        return polycarbonateMasksPrice.get(id);
    }

    public Double getBacterialSanitizersPrice(Integer id) {
        return bacterialSanitizersPrice.get(id);
    }

    public Double getFungalSanitizersPrice(Integer id) {
        return fungalSanitizersPrice.get(id);
    }

    public Double getVirusSanitizersPrice(Integer id) {
        return virusSanitizersPrice.get(id);
    }

    public void addSurgicalMasksPrice(Integer id, Double price) {
        this.surgicalMasksPrice.put(id, price);
    }

    public void addPolycarbonateMasksPrice(Integer id, Double price) {
        this.polycarbonateMasksPrice.put(id, price);
    }

    public void addBacterialSanitizersPrice(Integer id, Double price) {
        this.bacterialSanitizersPrice.put(id, price);
    }

    public void addFungalSanitizersPrice(Integer id, Double price) {
        this.fungalSanitizersPrice.put(id, price);
    }

    public void addVirusSanitizersPrice(Integer id, Double price) {
        this.virusSanitizersPrice.put(id, price);
    }

    public void addClients(Integer id) {
        this.clients.put(id, true);
    }

    public void removeSurgicalMasksPrice(Integer id) { this.surgicalMasksPrice.remove(id);}

    public void removePolycarbonateMasksPrice(Integer id) { this.polycarbonateMasksPrice.remove(id);}

    public void removeBacterialSanitizersPrice(Integer id) {
        this.bacterialSanitizersPrice.remove(id);
    }
    public void removeFungalSanitizersPrice(Integer id) {
        this.fungalSanitizersPrice.remove(id);
    }

    public void removeVirusSanitizerPrice(Integer id) {
        this.virusSanitizersPrice.remove(id);
    }

    public boolean is_inSurgicalMasksPrice(Integer id){
        return this.surgicalMasksPrice.containsKey(id);}

    public boolean is_inPolycarbonateMasksPrice(Integer id){
        return this.polycarbonateMasksPrice.containsKey(id);}

    public boolean is_inBacterialSanitizersPrice(Integer id){
        return this.bacterialSanitizersPrice.containsKey(id);}

    public boolean is_inFungalSanitizersPrice(Integer id){
        return this.fungalSanitizersPrice.containsKey(id);}

    public boolean is_inVirusSanitizersPrice(Integer id){
        return this.virusSanitizersPrice.containsKey(id);
    }

    public boolean is_inClients(Integer id){
        return this.clients.containsKey(id);
    }

    public boolean emptySurgicalMasksPrice() {return this.surgicalMasksPrice.isEmpty();}

    public boolean emptyPolycarbonateMasksPrice() {return this.polycarbonateMasksPrice.isEmpty();}

    public boolean emptyBacterialSanitizersPrice() {return this.bacterialSanitizersPrice.isEmpty();}

    public boolean emptyFungalSanitizersPrice() {return this.fungalSanitizersPrice.isEmpty();}

    public boolean emptyVirusSanitizerPrice() {return this.virusSanitizersPrice.isEmpty();}

    public boolean emptyClients() {return this.clients.isEmpty();}

    public void printAllData(){
        Enumeration<Integer> keys = surgicalMasksPrice.keys();
        System.out.println("Surgical Masks - local:");
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            System.out.println("Key: " + key + ", Value: " + surgicalMasksPrice.get(key));
        }

        System.out.println("Polycarbonate Masks - local:");
        keys = polycarbonateMasksPrice.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            System.out.println("Key: " + key + ", Value: " + polycarbonateMasksPrice.get(key));
        }

        System.out.println("Bacteria Sanitizers - local:");
        keys = bacterialSanitizersPrice.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            System.out.println("Key: " + key + ", Value: " + bacterialSanitizersPrice.get(key));
        }

        System.out.println("Fungal Sanitizers - local:");
        keys = fungalSanitizersPrice.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            System.out.println("Key: " + key + ", Value: " + fungalSanitizersPrice.get(key));
        }

        System.out.println("Virus Sanitizers - local:");
        keys = virusSanitizersPrice.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            System.out.println("Key: " + key + ", Value: " + virusSanitizersPrice.get(key));
        }
    }
}
