import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Graphical user interface class.
 * 
 * @author Raphael Vilete.
 */

public class PasswordGeneratorGUI extends JFrame implements ActionListener {

    // Icons for the application
    ImageIcon icon = new ImageIcon(getClass().getResource("/lock.png"));
    ImageIcon copyIcon = new ImageIcon(getClass().getResource("/copyButtonz.png"));

    // UI components
    JLabel titleLabel;

    JTextArea passwordOutput;
    JScrollPane passOutPane;

    JLabel passLengthLabel;
    JSpinner passwordLengthSpinner;

    JToggleButton uppercase;
    JToggleButton lowercase;
    JToggleButton numbers;
    JToggleButton symbols;

    JButton generateButton;
    JButton copyButton;

    private PasswordGenerator passwordGenerator = new PasswordGenerator();

    public PasswordGeneratorGUI() {

        // Frame title
        super("Password Generator");

        // Frame settings
        setSize(540, 570);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.lightGray);
        setIconImage(icon.getImage());

        // Title Label for the application
        titleLabel = new JLabel("Password Generator");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 540, 40);

        // Text Area for displaying the generated password
        passwordOutput = new JTextArea();
        passwordOutput.setEditable(false);
        passwordOutput.setFont(new Font("Helvetica", Font.BOLD, 32));
        passOutPane = new JScrollPane(passwordOutput);
        passOutPane.setBounds(28, 100, 400, 60);
        passOutPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Label for indicating password length
        passLengthLabel = new JLabel("Password Lenght: ");
        passLengthLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
        passLengthLabel.setBounds(28, 215, 280, 40);

        // Spinner for selecting the desired password length
        SpinnerModel spinnerModel = new SpinnerNumberModel(8, 0, 100, 1);
        passwordLengthSpinner = new JSpinner(spinnerModel);
        passwordLengthSpinner.setFont(new Font("Helvetica", Font.PLAIN, 30));
        passwordLengthSpinner.setBounds(314, 215, 190, 40);
        passwordLengthSpinner.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Toggle buttons for selecting character sets

        // The "uppercase" toggle button allows including uppercase letters in the
        // password.
        uppercase = new JToggleButton("Uppercase");
        uppercase.setFont(new Font("Helvetica", Font.PLAIN, 26));
        uppercase.setBounds(28, 300, 225, 56);
        uppercase.setFocusable(false);
        uppercase.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // The "lowercase" toggle button allows including lowercase letters in the
        // password.
        lowercase = new JToggleButton("Lowercase");
        lowercase.setFont(new Font("Helvetica", Font.PLAIN, 26));
        lowercase.setBounds(283, 300, 225, 56);
        lowercase.setFocusable(false);
        lowercase.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // The "numbers" toggle button allows including numbers in the password.
        numbers = new JToggleButton("Numbers");
        numbers.setFont(new Font("Helvetica", Font.PLAIN, 26));
        numbers.setBounds(28, 370, 225, 56);
        numbers.setFocusable(false);
        numbers.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // The "symbols" toggle button allows including symbols in the password.
        symbols = new JToggleButton("Symbols");
        symbols.setFont(new Font("Helvetica", Font.PLAIN, 26));
        symbols.setBounds(283, 370, 225, 56);
        symbols.setFocusable(false);
        symbols.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button for generating passwords
        generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Helvetica", Font.PLAIN, 30));
        generateButton.setBounds(155, 462, 222, 40);
        generateButton.setFocusable(false);
        generateButton.setBackground(Color.GREEN);
        generateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        generateButton.addActionListener(new ActionListener() {

            // The generateButton is used to generate passwords based on user preferences.
            @Override
            public void actionPerformed(ActionEvent e) {

                if ((int) passwordLengthSpinner.getValue() <= 0) {
                    return;
                }

                boolean anyToggleSelected = uppercase.isSelected() || lowercase.isSelected() || numbers.isSelected()
                        || symbols.isSelected();

                if (anyToggleSelected) {
                    String generatedPassword = passwordGenerator.generatePassword(
                            (int) passwordLengthSpinner.getValue(), uppercase.isSelected(), lowercase.isSelected(),
                            numbers.isSelected(), symbols.isSelected());

                    passwordOutput.setText(generatedPassword);
                }
            }
        });

        // Button for copying the generated password to the clipboard
        copyButton = new JButton();
        copyButton.setBounds(455, 105, 50, 50);
        copyButton.setFocusable(false);
        copyButton.addActionListener(this);
        copyButton.setLayout(null);
        copyIcon.setImage(copyIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        copyButton.setIcon(copyIcon);
        copyButton.setContentAreaFilled(false);
        copyButton.setToolTipText("Copy");
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add UI Components to the frame
        this.add(titleLabel);
        this.add(passOutPane);
        this.add(passLengthLabel);
        this.add(passwordLengthSpinner);
        this.add(uppercase);
        this.add(lowercase);
        this.add(numbers);
        this.add(symbols);
        this.add(generateButton);
        this.add(copyButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Copy the generated password to the clipboard
        String passwordString = passwordOutput.getText();
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

        StringSelection passwordSelection = new StringSelection(passwordString);
        clip.setContents(passwordSelection, passwordSelection);

        if (!passwordString.isEmpty()) {

            // Show a "Copied" message
            JLabel message = new JLabel("Copied");
            message.setHorizontalAlignment(SwingConstants.CENTER);
            JOptionPane.showMessageDialog(this, message, "", JOptionPane.DEFAULT_OPTION);

        } else {
            return;
        }
    }
}