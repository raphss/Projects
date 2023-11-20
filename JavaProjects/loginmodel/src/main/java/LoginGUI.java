import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Graphical user interface class for user login.
 * 
 * @author Raphael Vilete
 */

public class LoginGUI extends JFrame implements MouseListener, ActionListener {

    // UI components
    JPanel backgroundPanel;
    JLabel titleLabel;
    JLabel emailLabel;
    JTextField emailField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JCheckBox showPasswordButton;
    JTextField passwordRevealed;
    JButton loginButton;
    JLabel successfulLogin;
    JLabel unsuccessfulLogin;
    JButton signUpButton;

    private static SignUpGui signUpGui = new SignUpGui();

    // Constructor to create the Login GUI.
    LoginGUI() {

        // Frame settings
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 900);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0X27b8cc));
        setLayout(null);

        // Initialize UI components
        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0x76ced2));
        backgroundPanel.setBounds(400, 60, 500, 750);

        titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 50));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(590, 100, 540, 80);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        emailLabel.setForeground(Color.white);
        emailLabel.setBounds(440, 230, 540, 80);

        emailField = new JTextField();
        emailField.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        emailField.setBounds(440, 300, 420, 60);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        passwordLabel.setForeground(Color.white);
        passwordLabel.setBounds(440, 360, 540, 80);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        passwordField.setBounds(440, 430, 420, 60);

        showPasswordButton = new JCheckBox("Show password");
        showPasswordButton.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        showPasswordButton.setBounds(440, 480, 420, 60);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setFocusable(false);
        showPasswordButton.addActionListener(this);
        passwordRevealed = new JTextField();
        passwordRevealed.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        passwordRevealed.setBounds(440, 430, 420, 60);
        passwordRevealed.setVisible(false);

        loginButton = new JButton("Sign in");
        loginButton.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        loginButton.setForeground(Color.white);
        loginButton.setBackground(new Color(0X27b8cc));
        loginButton.setFocusable(false);
        loginButton.setBounds(500, 580, 300, 80);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorderPainted(false);
        loginButton.addMouseListener(this);

        signUpButton = new JButton("Create an account?");
        signUpButton.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        signUpButton.setForeground(Color.white);
        signUpButton.setFocusable(false);
        signUpButton.setBounds(500, 710, 300, 80);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addMouseListener(this);

        // Adding created components to the frame
        add(titleLabel);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(showPasswordButton);
        add(passwordRevealed);
        add(loginButton);
        add(signUpButton);
        add(backgroundPanel);
    }

    // Handle mouse click events
    @Override
    public void mouseClicked(MouseEvent e) {

        // Redirect to the sign up page when the "Create an account?" button is clicked
        if (e.getSource() == signUpButton) {
            signUpGui.setVisible(true);
            this.dispose();
        }

        // Handle user login when the "Sign in" button is clicked
        if (e.getSource() == loginButton) {

            try {

                // Attempt to log in
                Controller loginController = new Controller();

                // Check if the "Show password" option is selected, and handle accordingly
                if (showPasswordButton.isSelected() || !passwordRevealed.getText().isEmpty()) {
                    passwordField.setText(passwordRevealed.getText());
                }

                loginController.loginAccount(this);

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // This method is intentionally left empty
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // This method is intentionally left empty
    }

    // Handle mouse enter events
    @Override
    public void mouseEntered(MouseEvent e) {

        // Change the borders and foreground of the buttons
        if (e.getSource() == loginButton) {
            loginButton.setBorderPainted(true);
        }

        if (e.getSource() == signUpButton) {
            signUpButton.setForeground(new Color(0x007ACC));
        }
    }

    // Handle mouse exit events
    @Override
    public void mouseExited(MouseEvent e) {

        // Change the borders and foreground of the buttons
        if (e.getSource() == loginButton) {
            loginButton.setBorderPainted(false);
        }

        if (e.getSource() == signUpButton) {
            signUpButton.setForeground(Color.white);
        }
    }

    // Handle action events, e.g., when the showPasswordButton is clicked
    @Override
    public void actionPerformed(ActionEvent e) {

        // Show the password in text
        if (showPasswordButton.isSelected()) {
            passwordRevealed.setText(new String(passwordField.getPassword()));
            passwordField.setVisible(false);
            passwordRevealed.setVisible(true);
            passwordRevealed.requestFocusInWindow();

            // Hide the text password and restore the password field
        } else if (!showPasswordButton.isSelected() || !passwordRevealed.getText().isEmpty()) {
            passwordField.setText(passwordRevealed.getText());
            passwordRevealed.setText("");
            passwordField.setVisible(true);
            passwordField.requestFocusInWindow();
            passwordRevealed.setVisible(false);
        }
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    // Display a success message when the user successfully logs in
    public void loginSuccess() {
        successfulLogin = new JLabel("Login was successful.");
        successfulLogin.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, successfulLogin, "", JOptionPane.DEFAULT_OPTION);
    }

    // Display an error message when the user enters an incorrect or nonexistent
    // email address or password
    public void loginUnsuccess() {
        unsuccessfulLogin = new JLabel("Incorrect email address or password. Try again.");
        unsuccessfulLogin.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, unsuccessfulLogin, "", JOptionPane.ERROR_MESSAGE);
    }

    // Get the SignUpGui instance to allow interactions with the sign-up page
    public SignUpGui getSignUpGui() {
        return signUpGui;
    }
}