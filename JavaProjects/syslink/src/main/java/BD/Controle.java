package BD;

import java.sql.SQLException;

import interfaces.InserirCodigo;
import interfaces.TelaCadastroGUI;
import interfaces.TelaLoginGUI;

/**
 * Classe de controle que lida com a criação de contas de usuário e o login.
 * 
 * @author Raphael Vilete
 */

public class Controle {

    /**
     * Cria uma nova conta de usuário.
     * 
     * @param telaCadastro  A instância da telaCadastro contendo os dados de entrada
     *                      do usuário.
     * @param inserirCodigo A instância da classe InserirCodigo que será usada para
     *                      confirmar a autenticidade do e-mail.
     * @throws SQLException Se ocorrer um erro relacionado ao banco de dados.
     */

    public Controle() {

    }

    public void criarConta(TelaCadastroGUI telaCadastro, InserirCodigo inserirCodigo) throws SQLException {

        DAO cadastrar;

        try {

            cadastrar = new DAO();

            // Chama o método para criar uma conta de usuário
            cadastrar.cadastrarUsuario(telaCadastro.getCampoNome().getText(), telaCadastro.getCampoEmail(),
                    telaCadastro.getCampoSenha().getPassword(), inserirCodigo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Autentica e faz o login de um usuário.
     * 
     * @param telaLogin A instância da telaLogin contem os dados de login do
     *                  usuário.
     * @throws SQLException Se ocorrer um erro relacionado ao banco de dados.
     */

    public boolean logarConta(TelaLoginGUI telaLogin) throws SQLException {

        DAO login;

        try {

            login = new DAO();

            // Chama o método para autenticar e fazer login do usuário, retornando se a
            // conta existe ou não
            return login.logarUsuario(telaLogin.getCampoEmail().getText(), telaLogin.getCampoSenha().getPassword());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}