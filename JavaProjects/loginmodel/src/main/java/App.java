/**
 * The main entry point of the application.
 * 
 * This class initializes the login frame and starts the application.
 * 
 * @author Raphael Vilete
 */

public class App {

    /**
     * The main method that initializes the login frame and starts the application.
     * 
     * @param args Command-line arguments (not used in this application).
     */

    public static void main(String[] args) {

        // Create the LoginGUI frame
        LoginGUI loginFrame = new LoginGUI();

        // Make the login frame visible
        loginFrame.setVisible(true);
    }
}