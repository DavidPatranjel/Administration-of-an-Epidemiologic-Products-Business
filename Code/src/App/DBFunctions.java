package App;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBFunctions {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/paopandemic";
    private static final String USER = "lab1512";
    private static final String PASSWORD = "lab1512";
    private static Connection databaseConnection = null;
    private static DBFunctions instance;
    private static Audit audit = Audit.getInstance();

    private DBFunctions() { }

    static {
        try {
            instance = new DBFunctions();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating DBFunctions singleton instance");
        }
    }

    public static DBFunctions getInstance() {
        if (instance == null) {
            instance = new DBFunctions();
        }
        return instance;
    }

    public Connection connect_to_db(){
        try{
            Class.forName("org.postgresql.Driver");
            if (databaseConnection == null || databaseConnection.isClosed()) {
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                if (databaseConnection != null) {
                    audit.logAction("Open connection with database");
                } else {
                    System.out.println("Connection Failed");
                    audit.logAction("Connection failed with database!");
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return databaseConnection;
    }
    public static void closeDatabaseConnection()
    {
        try
        {
            if (databaseConnection != null && !databaseConnection.isClosed())
            {
                databaseConnection.close();
                audit.logAction("Close connection with database");
            }
        }
        catch (IOException | SQLException e)
        {
            e.printStackTrace();
        }
    }
}
