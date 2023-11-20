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
 * Graphical user interface class for user sign-up.
 * 
 * @author Raphael Vilete
 */

public class SignUpGui extends JFrame implements MouseListener, ActionListener {

    // UI components
    JPanel backgroundPanel;
    JLabel titleLabel;
    JLabel nameLabel;
    JTextField nameField;
    JLabel emailLabel;
    JTextField emailField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JCheckBox showPasswordButton;
    JTextField passwordRevealed;
    JButton signUpButton;
    JButton backToLoginButton;
    boolean checkDuplicatedEmail;

    private static LoginGUI loginGui = new LoginGUI();

    // Constructor to create the Sign Up GUI.
    SignUpGui() {

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

        titleLabel = new JLabel("Create your Account");
        titleLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 40));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(470, 100, 540, 80);

        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        nameLabel.setForeground(Color.white);
        nameLabel.setBounds(440, 170, 540, 80);

        nameField = new JTextField();
        nameField.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        nameField.setBounds(440, 240, 420, 60);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        emailLabel.setForeground(Color.white);
        emailLabel.setBounds(440, 300, 540, 80);

        emailField = new JTextField();
        emailField.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        emailField.setBounds(440, 370, 420, 60);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        passwordLabel.setForeground(Color.white);
        passwordLabel.setBounds(440, 430, 540, 80);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        passwordField.setBounds(440, 500, 420, 60);

        showPasswordButton = new JCheckBox("Show password");
        showPasswordButton.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        showPasswordButton.setBounds(440, 550, 420, 60);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setFocusable(false);
        showPasswordButton.addActionListener(this);
        passwordRevealed = new JTextField();
        passwordRevealed.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        passwordRevealed.setBounds(440, 500, 420, 60);
        passwordRevealed.setVisible(false);

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        signUpButton.setForeground(Color.white);
        signUpButton.setBackground(new Color(0X27b8cc));
        signUpButton.setFocusable(false);
        signUpButton.setBounds(500, 620, 300, 80);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.setBorderPainted(false);
        signUpButton.addMouseListener(this);

        backToLoginButton = new JButton("Go back to Login");
        backToLoginButton.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        backToLoginButton.setForeground(Color.white);
        backToLoginButton.setFocusable(false);
        backToLoginButton.setBounds(500, 710, 300, 80);
        backToLoginButton.setContentAreaFilled(false);
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLoginButton.addMouseListener(this);

        // Adding created components to the frame
        add(titleLabel);
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(showPasswordButton);
        add(passwordRevealed);
        add(signUpButton);
        add(backToLoginButton);
        add(backgroundPanel);
    }

    // Handle mouse click events
    @Override
    public void mouseClicked(MouseEvent e) {

        // Redirect to the login page when the "Go back to Login" button is clicked
        if (e.getSource() == backToLoginButton) {
            loginGui.setVisible(true);
            this.dispose();
        }

        // Handle user sign-up when the "Sign Up" button is clicked
        if (e.getSource() == signUpButton) {

            StringBuilder passwordString = new StringBuilder();

            for (char c : passwordField.getPassword()) {
                passwordString.append(c);
            }

            try {

                Controller signUpController = new Controller();

                // Check if the "Show password" option is selected, and handle accordingly
                if (showPasswordButton.isSelected() || !passwordRevealed.getText().isEmpty()) {
                    passwordField.setText(passwordRevealed.getText());
                }

                signUpController.createAccount(this);

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
        if (e.getSource() == signUpButton) {
            signUpButton.setBorderPainted(true);
        }

        if (e.getSource() == backToLoginButton) {
            backToLoginButton.setForeground(new Color(0x007ACC));
        }
    }

    // Handle mouse exit events
    @Override
    public void mouseExited(MouseEvent e) {

        // Change the borders and foreground of the buttons
        if (e.getSource() == signUpButton) {
            signUpButton.setBorderPainted(false);
        }

        if (e.getSource() == backToLoginButton) {
            backToLoginButton.setForeground(Color.white);
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

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
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

    // Notify the user that all required fields must be filled
    public void invalidSignUpFields() {

        JLabel invalidSignUp = new JLabel("You must fill in all requested fields.");
        invalidSignUp.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, invalidSignUp, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notify the user that the username must not contain special characters
    public void invalidNameMessage() {

        JLabel invalidName = new JLabel("The username must not contain special characters.");
        invalidName.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, invalidName, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notify the user that the entered email doesn't seem valid
    public void invalidEmailMessage() {

        JLabel invalidEmail = new JLabel("This email doesn't seem real. Try a different one.");
        invalidEmail.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, invalidEmail, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notify the user that the password must be at least eight characters long
    public void invalidPasswordMessage() {

        JLabel invalidPassword = new JLabel("The password length must be at least eight characters.");
        invalidPassword.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, invalidPassword, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notify the user that the entered email is already registered
    public void duplicatedEmailMessage() {

        JLabel primaryKeyEmail = new JLabel("This email is already registered. Try a different one.");
        primaryKeyEmail.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, primaryKeyEmail, "", JOptionPane.ERROR_MESSAGE);

        checkDuplicatedEmail = true;
    }

    // Notify the user that the account has been successfully created
    public void successfulCreatedAccount() {

        JLabel signUpSuccess = new JLabel("Your account is created.");
        signUpSuccess.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, signUpSuccess, "", JOptionPane.DEFAULT_OPTION);

        loginGui.getSignUpGui().setVisible(false);
        loginGui.setVisible(true);
    }
}