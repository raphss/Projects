import java.util.Random;

/**
 * Core password generation logic class.
 * 
 *  @author Raphael Vilete.
*/

public class PasswordGenerator {

    // Character sets
    public static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVXWYZ";
    public static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvxwyz";
    public static final String NUMBERS = "0123456789";
    public static final String SYMBOLS = "!@#$%^&*()_+-={}[]|/:;\"'<>,.?~";

    private final Random random;

    public PasswordGenerator() {
        // Initialize a random number generator
        random = new Random();
    }

    // Method to generate passwords based on user preferences
    public String generatePassword(int length, boolean addUpperCase, boolean addLowerCase, boolean addNumbers,
            boolean addSymbols) {

        // Create a valid character set based on user preferences
        String validChars = "";

        if (addUpperCase) {
            validChars += UPPERCASE_CHARS;
        }

        if (addLowerCase) {
            validChars += LOWERCASE_CHARS;
        }

        if (addNumbers) {
            validChars += NUMBERS;
        }

        if (addSymbols) {
            validChars += SYMBOLS;
        }

        // Generate a password using the valid character set
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int randomIndex = random.nextInt(validChars.length());
            char randomChar = validChars.charAt(randomIndex);
            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString();
    }
}