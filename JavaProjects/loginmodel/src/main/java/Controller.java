import java.sql.Connection;
import java.sql.SQLException;

/**
 * Controller class that handles user account creation and login.
 * 
 * @author Raphael Vilete
 */

public class Controller {

    /**
     * Create a new user account.
     * 
     * @param signUpGui The SignUpGui instance containing user input data.
     * @throws SQLException If a database-related error occurs.
     */

    public void createAccount(SignUpGui signUpGui) throws SQLException {

        Connection connection = null;
        DAO signUp;

        try {

            // Establish a database connection
            connection = new ConnectToData().getConnection();

            signUp = new DAO();

            // Call the method to create a user account
            signUp.signUpUser(signUpGui.getNameField().getText(), signUpGui.getEmailField().getText(),
                    signUpGui.getPasswordField().getPassword());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close the database connection
        connection.close();
    }

    /**
     * Authenticate and log in a user.
     * 
     * @param loginGUI The LoginGUI instance containing user login data.
     * @throws SQLException If a database-related error occurs.
     */

    public void loginAccount(LoginGUI loginGUI) throws SQLException {

        Connection connection = null;
        DAO login;

        try {

            connection = new ConnectToData().getConnection();

            login = new DAO();

            // Call the method to authenticate and log in the user
            login.loginUser(loginGUI.getEmailField().getText(), loginGUI.getPasswordField().getPassword());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close the database connection
        connection.close();
    }
}