import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility class for establishing a database connection.
 * 
 * The database connection parameters (database name, URL, username, password)
 * are defined here.
 * 
 * @author Raphael Vilete
 */

public class ConnectToData {

    /**
     * Get a database connection.
     * 
     * @return A database connection object.
     * @throws SQLException If a database-related error occurs.
     */

    public Connection getConnection() throws SQLException {

        Connection connection = null;

        try {

            String dbName = "test";
            String dbUrl = "jdbc:h2:./" + dbName;

            // String dbUrl = "jdbc:h2:~/" + dbName; // To access database in VsCode

            // Establish a connection to the database with the specified URL, username, and
            // password
            connection = DriverManager.getConnection(dbUrl, "root", "root");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}