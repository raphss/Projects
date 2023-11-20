import interfaces.TelaLoginGUI;

/**
 * O ponto de entrada principal da aplicação.
 * 
 * Esta classe inicializa o frame da tela de login e inicia a aplicação.
 * 
 * @author Raphael Vilete
 * @version 1.0
 */

public class App {

    /**
     * O método principal que inicializa o frame de login e inicia a aplicação.
     * 
     * @param args Argumentos da linha de comando (não usados nesta aplicação).
     */

    public static void main(String[] args) {

        // Cria o frame da tela de login
        TelaLoginGUI loginFrame = new TelaLoginGUI();

        // Torna o frame de login visível
        loginFrame.setVisible(true);
    }
}