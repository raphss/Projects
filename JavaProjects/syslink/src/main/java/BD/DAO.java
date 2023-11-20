package BD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import emailApi.GerarCodigo;
import interfaces.InserirCodigo;
import interfaces.TelaCadastroGUI;
import interfaces.TelaLoginGUI;

/**
 * Data Access Object (DAO) = classe para a interação com o banco de dados.
 * 
 * @author Raphael Vilete
 */

public class DAO {

    private static TelaLoginGUI telaLogin = new TelaLoginGUI();
    private static TelaCadastroGUI telaCadastro = new TelaCadastroGUI();

    /**
     * Cria uma nova conta de usuário no banco de dados
     * 
     * @param nome          O nome do usuário.
     * @param email         O endereço de e-mail do usuário.
     * @param senha         A senha do usuário.
     * @param inserirCodigo Confirmação de e-mail do usuário.
     * @throws SQLException Se ocorrer um erro relacionado ao banco de dados.
     */

    public void cadastrarUsuario(String nome, String email, char[] senha, InserirCodigo inserirCodigo)
            throws SQLException {

        // Converte a senha de um array de caracteres para uma string
        StringBuilder senhaString = new StringBuilder();
        for (char c : senha) {
            senhaString.append(c);
        }

        // Verifica se algum dos campos de preenchimento estão vazios e aciona a
        // mensagem apropriada
        if (nome.matches("") || email.matches("") || senhaString.equals("")) {
            telaCadastro.camposInvalidos();
        }

        // Verifica se o nome digitado contém números ou caracteres especiais
        else if (nome.matches(".*[^A-Za-zÀ-ÖØ-öø-ÿ ].*")) {
            telaCadastro.nomeInvalido();
        }

        // Verifica se o e-mail digitado segue uma estrutura comum de e-mail
        else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            telaCadastro.emailInvalido();
        }

        // Verifica se a senha digitada tem pelo menos oito caracteres
        else if (senhaString.length() <= 7) {
            telaCadastro.senhaInvalida();
        }

        // Se todas as verificações passarem, faz o processo de verificação de e-mail e
        // depois constrói uma instrução SQL para inserir o novo usuário no banco de
        // dados
        else {

            // Gera o código que será enviado ao e-mail
            GerarCodigo gerarCodigo = new GerarCodigo();
            String codigo = gerarCodigo.gerarCodigo();

            // Envia o código ao email inserido pelo usuário
            boolean check = inserirCodigo.callEmailSender(email, codigo);

            if (check) {

                inserirCodigo.setVisible(true);

                // Verifica se o código digitado pelo usuário é igual ao que lhe foi enviado por
                // e-mail
                inserirCodigo.confirmarCodigo.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (!inserirCodigo.getcampoCodigo().equals(codigo)) {

                            JLabel codigoIncorreto = new JLabel("Código incorreto.");
                            codigoIncorreto.setHorizontalAlignment(SwingConstants.CENTER);
                            JOptionPane.showMessageDialog(null, codigoIncorreto, "", JOptionPane.ERROR_MESSAGE);

                        } else {

                            // Obtem uma conexão com o banco de dados e cadastra o usuário
                            try {

                                Connection conexao = null;
                                conexao = new ConexaoBD().getConexao();

                                String sql = "INSERT INTO users (name, email, password) VALUES ('" + nome + "', '"
                                        + email
                                        + "', '"
                                        + senhaString + "')";

                                PreparedStatement statement = conexao.prepareStatement(sql);
                                statement.execute();

                                inserirCodigo.dispose();

                                // Exiba uma mensagem de sucesso quando a conta do usuário for criada
                                telaCadastro.cadastroSucedido();

                            } catch (SQLException e1) {
                                e1.printStackTrace();
                                telaCadastro.emailDuplicado();
                                inserirCodigo.dispose();
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * Verifica se um usuário pode fazer login com o e-mail e a senha fornecidos.
     * 
     * @param email O endereço de e-mail do usuário.
     * @param senha A senha do usuário.
     * @throws SQLException Se ocorrer um erro relacionado ao banco de dados.
     */

    public boolean logarUsuario(String email, char[] senha) throws SQLException {

        // Obtem uma conexão com o banco de dados
        Connection conexao = null;

        try {

            conexao = new ConexaoBD().getConexao();

            // Converte a senha de um array de caracteres para uma string
            StringBuilder senhaString = new StringBuilder();
            for (char c : senha) {
                senhaString.append(c);
            }

            // Constrói uma consulta SQL para verificar se o e-mail e a senha correspondem a
            // um usuário cadastrado no banco de dados
            String sql = "SELECT email, password FROM users WHERE email = '" + email
                    + "' AND password = '" + senhaString + "'";

            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            // Se um usuário correspondente for encontrado, exibe uma mensagem de sucesso e
            // retorna como "true". Sendo essa verificação usada para iniciar a aplicação
            // principal
            if (rs.next()) {
                telaLogin.loginBemSucedido();
                return true;
            }

            // Se nenhum usuário correspondente for encontrado, exibe uma mensagem de erro
            else {
                telaLogin.loginNaoSucedido();
            }

            // Encerra a conexão com o banco de dados
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Informa que a conta não foi encontrada. Negando o acesso a aplicação
        // principal
        return false;
    }
}