package interfaces;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BD.ConexaoBD;
import emailApi.GerarCodigo;

/**
 * Classe de interface gráfica para solicitar a alteração de senha.
 * 
 * Esta classe permite ao usuário solicitar uma alteração de senha ao inserir
 * seu endereço de e-mail. Ela realiza a validação do e-mail, procura-o no banco
 * de dados, e envia um código de confirmação.
 * 
 * Após a confirmação do código, o usuário pode definir uma nova senha.
 * 
 * @author Raphael Vilete
 */

public class AlterarSenha extends JFrame {

    // Componentes UI
    JLabel email;
    JTextField campoEmail;
    JButton enviarCodigo;
    JButton cancelar;

    // Construtor da classe
    public AlterarSenha() {

        // Configurações do frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Confirme seu e-mail");

        // Inicializando os componentes UI
        email = new JLabel("Insira o e-mail da sua conta");
        email.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        email.setBounds(40, 90, 420, 55);

        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoEmail.setBounds(40, 140, 420, 55);

        enviarCodigo = new JButton("Enviar código");
        enviarCodigo.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        enviarCodigo.setForeground(Color.white);
        enviarCodigo.setBackground(new Color(0X27b8cc));
        enviarCodigo.setFocusable(false);
        enviarCodigo.setBounds(100, 240, 300, 60);
        enviarCodigo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enviarCodigo.setBorderPainted(false);
        enviarCodigo.addActionListener(new ActionListener() {

            // Verifica se o e-mail inserido está registrado no banco de dados.
            @Override
            public void actionPerformed(ActionEvent e) {

                if (getcampoEmail().equals("")) {
                    JLabel emailInvalido = new JLabel("Preencha o campo acima.");
                    emailInvalido.setHorizontalAlignment(SwingConstants.CENTER);
                    JOptionPane.showMessageDialog(null, emailInvalido, "E-mail vazio", JOptionPane.ERROR_MESSAGE);
                }

                else {

                    Connection conexao = null;

                    // Obtem uma conexão com o banco de dados e procura pelo e-mail do usuário.
                    try {

                        conexao = new ConexaoBD().getConexao();

                        String sql = "SELECT email FROM users WHERE email = '" + getcampoEmail() + "'";

                        PreparedStatement statement = conexao.prepareStatement(sql);
                        ResultSet rs = statement.executeQuery();

                        // Caso encontre o e-mail no banco de dados, faz:
                        if (rs.next()) {

                            // Cria uma nova instância da classe InserirCodigo
                            InserirCodigo inserirCodigo = new InserirCodigo();

                            // Gera um código aleatório que será enviado ao e-mail do usuário
                            GerarCodigo gerarCodigo = new GerarCodigo();
                            String codigo = gerarCodigo.gerarCodigo();

                            // Envia o e-mail para o usuário

                            boolean check = inserirCodigo.callEmailSender(getcampoEmail(), codigo);

                            if (check) {

                                inserirCodigo.setVisible(true);
                                dispose();

                                inserirCodigo.confirmarCodigo.addActionListener(new ActionListener() {

                                    // Verifica se o código inserido pelo usuário é o mesmo que lhe foi enviado por
                                    // e-mail
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        if (!inserirCodigo.getcampoCodigo().equals(codigo)) {

                                            JLabel codigoIncorreto = new JLabel("Código incorreto.");
                                            codigoIncorreto.setHorizontalAlignment(SwingConstants.CENTER);
                                            JOptionPane.showMessageDialog(null, codigoIncorreto, "",
                                                    JOptionPane.ERROR_MESSAGE);

                                        } else {

                                            // Com o e-mail confirmado, gera uma nova tela para a atualização da senha
                                            NovaSenha novaSenha = new NovaSenha();
                                            novaSenha.setVisible(true);
                                            inserirCodigo.dispose();

                                            novaSenha.mudarSenha.addActionListener(new ActionListener() {

                                                // Faz a validação da senha e depois obtem uma conexão com o banco de
                                                // dados
                                                @Override
                                                public void actionPerformed(ActionEvent e) {

                                                    if (novaSenha.getcampoSenha().equals("")
                                                            || novaSenha.getcampoSenha().length() < 8) {

                                                        JLabel codigoIncorreto = new JLabel(
                                                                "A senha deve conter no minimo oito caracteres.");
                                                        codigoIncorreto.setHorizontalAlignment(SwingConstants.CENTER);
                                                        JOptionPane.showMessageDialog(null, codigoIncorreto,
                                                                "Senha vazia ou pequena",
                                                                JOptionPane.ERROR_MESSAGE);

                                                    } else {

                                                        Connection conexao = null;

                                                        // Após obter a conexão com o banco de dados, faz o update da
                                                        // conta
                                                        // do usuário. Alterando somente a senha
                                                        try {

                                                            conexao = new ConexaoBD().getConexao();

                                                            String updateSql = "UPDATE users SET PASSWORD =? WHERE email =?";

                                                            PreparedStatement statement1 = conexao
                                                                    .prepareStatement(updateSql);
                                                            statement1.setString(1, novaSenha.getcampoSenha());
                                                            statement1.setString(2, getcampoEmail());
                                                            statement1.executeUpdate();

                                                            conexao.commit();
                                                            conexao.close();

                                                            // Encerra a conexão com o banco e exibe uma mensagem de
                                                            // sucesso
                                                            System.out.println("Senha atualizada com sucesso!");
                                                            JLabel atualizacaoSucedida = new JLabel(
                                                                    "Sua senha foi atualizada.");
                                                            atualizacaoSucedida
                                                                    .setHorizontalAlignment(SwingConstants.CENTER);
                                                            JOptionPane.showMessageDialog(null, atualizacaoSucedida,
                                                                    "Nova senha definida", JOptionPane.DEFAULT_OPTION);

                                                            novaSenha.dispose();

                                                        } catch (SQLException e1) {
                                                            e1.printStackTrace();
                                                        }

                                                    }
                                                }

                                            });

                                        }
                                    }

                                });

                            }
                        }

                        // Caso não encontre o e-mail no banco de dados, faz:
                        else {
                            JLabel emailInvalido = new JLabel("Este e-mail não foi encontrado no banco de dados.");
                            emailInvalido.setHorizontalAlignment(SwingConstants.CENTER);
                            JOptionPane.showMessageDialog(null, emailInvalido, "E-mail não registrado.",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        conexao.close();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });

        cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        cancelar.setForeground(new Color(0x007ACC));
        cancelar.setFocusable(false);
        cancelar.setBounds(100, 310, 300, 80);
        cancelar.setContentAreaFilled(false);
        cancelar.setBorderPainted(false);
        cancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

        // Adicionando ao frame, os componentes criados
        add(email);
        add(campoEmail);
        add(enviarCodigo);
        add(cancelar);
    }

    public String getcampoEmail() {
        return campoEmail.getText();
    }
}