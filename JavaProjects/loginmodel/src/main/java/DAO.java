import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for interacting with the database.
 * 
 * @author Raphael Vilete
 */

public class DAO {

    private static LoginGUI loginGui = new LoginGUI();
    private static SignUpGui signUpGui = new SignUpGui();

    /**
     * Create a new user account in the database.
     * 
     * @param name     The name of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @throws SQLException If a database-related error occurs.
     */

    public void signUpUser(String name, String email, char[] password) throws SQLException {

        Connection connection = null;

        try {

            // Get a database connection
            connection = new ConnectToData().getConnection();

            // Convert password from char array to a string
            StringBuilder passwordString = new StringBuilder();
            for (char c : password) {
                passwordString.append(c);
            }

            // Check if any of the required fields is empty and trigger appropriate messages
            if (name.matches("") || email.matches("") || passwordString.equals("")) {
                signUpGui.invalidSignUpFields();
            }

            // Check if the typed name has any special characters
            else if (name.matches(".*[^A-Za-zÀ-ÖØ-öø-ÿ ].*")) {
                signUpGui.invalidNameMessage();
            }

            // Check if the typed email follows an usual email structure
            else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                signUpGui.invalidEmailMessage();
            }

            // Check if the typed password has at least eight characters
            else if (passwordString.length() <= 7) {
                signUpGui.invalidPasswordMessage();
            }

            // If all checks pass, construct an SQL statement and insert the new user into
            // the database
            else {

                String sql = "INSERT INTO users (name, email, password) VALUES ('" + name + "', '" + email + "', '"
                        + passwordString + "')";

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();

                // Display a success message when the user account is created
                signUpGui.successfulCreatedAccount();
            }

            // Close the database connection
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

            // Display an error message if there's a duplicated email in the database
            signUpGui.duplicatedEmailMessage();
        }
    }

    /**
     * Check if a user can log in with the provided email and password.
     * 
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @throws SQLException If a database-related error occurs.
     */

    public void loginUser(String email, char[] password) throws SQLException {

        // Get a database connection
        Connection connection = null;

        try {

            connection = new ConnectToData().getConnection();

            // Convert password from char array to a string
            StringBuilder passwordString = new StringBuilder();
            for (char c : password) {
                passwordString.append(c);
            }

            // Construct an SQL query to check if the email and password match a user in the
            // database
            String sql = "SELECT email, password FROM users WHERE email = '" + email
                    + "' AND password = '" + passwordString + "'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            // If a matching user is found, display a success message
            if (rs.next()) {
                loginGui.loginSuccess();
            }

            // If no matching user is found, display an error message
            else {
                loginGui.loginUnsuccess();
            }

            // Close the database connection
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}