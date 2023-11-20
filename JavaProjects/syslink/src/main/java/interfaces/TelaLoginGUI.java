package interfaces;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import BD.Controle;
import ErrosESolucoes.ListaDeErros;

/**
 * Interface gráfica para o login do usuário.
 * 
 * @author Raphael Vilete
 */

public class TelaLoginGUI extends JFrame implements MouseListener, ActionListener {

    // Componentes UI
    JLabel tituloLabel;
    JLabel emailLabel;
    JTextField campoEmail;
    JLabel senhaLabel;
    JPasswordField campoSenha;
    JCheckBox mostrarSenhaCheckbox;
    JTextField senhaVisivel;
    JButton botaoLogin;
    JLabel loginBemSucedido;
    JLabel loginNaoSucedido;
    JButton botaoCadastro;
    JButton botaoMudarSenha;

    ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
    ImageIcon imagemLogo = new ImageIcon(getClass().getResource("/syslink.png"));
    JPanel painel;
    JPanel painelImagem;
    JLabel imagem;

    private static TelaCadastroGUI telaCadastro = new TelaCadastroGUI();
    private static ListaDeErros listaDeErros = new ListaDeErros();

    // Construtor da classe
    public TelaLoginGUI() {

        // Configurações do frame
        setTitle("SYSLINK");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 740);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0X27b8cc));
        setLayout(new FlowLayout(FlowLayout.CENTER, 100, 75));
        setMinimumSize(new Dimension(650, getHeight()));

        // Inicializando os componentes UI
        tituloLabel = new JLabel("Login");
        tituloLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 40));
        tituloLabel.setForeground(Color.white);
        tituloLabel.setBounds(200, 20, 180, 80);

        emailLabel = new JLabel("E-mail");
        emailLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        emailLabel.setForeground(Color.white);
        emailLabel.setBounds(40, 100, 540, 80);

        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoEmail.setBounds(40, 160, 420, 55);

        senhaLabel = new JLabel("Senha");
        senhaLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        senhaLabel.setForeground(Color.white);
        senhaLabel.setBounds(40, 215, 540, 80);

        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoSenha.setBounds(40, 275, 420, 55);

        mostrarSenhaCheckbox = new JCheckBox("Mostrar senha");
        mostrarSenhaCheckbox.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        mostrarSenhaCheckbox.setBounds(40, 320, 140, 60);
        mostrarSenhaCheckbox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mostrarSenhaCheckbox.setContentAreaFilled(false);
        mostrarSenhaCheckbox.setFocusable(false);
        mostrarSenhaCheckbox.addActionListener(this);
        senhaVisivel = new JTextField();
        senhaVisivel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        senhaVisivel.setBounds(40, 275, 420, 55);
        senhaVisivel.setVisible(false);

        botaoLogin = new JButton("Entrar");
        botaoLogin.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        botaoLogin.setForeground(Color.white);
        botaoLogin.setBackground(new Color(0X27b8cc));
        botaoLogin.setFocusable(false);
        botaoLogin.setBounds(100, 400, 300, 60);
        botaoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoLogin.setBorderPainted(false);
        botaoLogin.addMouseListener(this);

        botaoCadastro = new JButton("Realizar cadastro");
        botaoCadastro.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        botaoCadastro.setForeground(Color.white);
        botaoCadastro.setFocusable(false);
        botaoCadastro.setBounds(100, 480, 300, 40);
        botaoCadastro.setContentAreaFilled(false);
        botaoCadastro.setBorderPainted(false);
        botaoCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoCadastro.addMouseListener(this);

        botaoMudarSenha = new JButton("Esqueceu sua senha?");
        botaoMudarSenha.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
        botaoMudarSenha.setForeground(new Color(0x007ACC));
        botaoMudarSenha.setFocusable(false);
        botaoMudarSenha.setBounds(260, 330, 220, 40);
        botaoMudarSenha.setContentAreaFilled(false);
        botaoMudarSenha.setBorderPainted(false);
        botaoMudarSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoMudarSenha.addMouseListener(this);

        imagem = new JLabel();
        imagem.setIcon(imagemLogo);

        painel = new JPanel();
        painel.setPreferredSize(new Dimension(500, 550));
        painel.setBackground(new Color(0x76ced2));
        painel.setLayout(null);

        painelImagem = new JPanel();
        painelImagem.setPreferredSize(new Dimension(510, 130));
        painelImagem.setBackground(new Color(0X27b8cc));
        painelImagem.setLayout(new BoxLayout(painelImagem, BoxLayout.X_AXIS));
        painelImagem.add(imagem);

        // Ajusta os componentes na tela de acordo com o tamanho da tela
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {

                int largura = getWidth();
                int altura = getHeight();

                if (largura < 1210 || largura >= 1210 && largura <= 1223) {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));
                    painel.setPreferredSize(new Dimension(500, 550));
                }

                else if (altura > 800 && altura < 900) {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 100, 120));
                    painel.setPreferredSize(new Dimension(500, 550));
                }

                else if (altura > 900 && altura < 1000) {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 100, 180));
                    painel.setPreferredSize(new Dimension(500, 550));
                }

                else if (altura >= 1050) {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 100, 200));
                    painel.setPreferredSize(new Dimension(500, 550));
                }

                else {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 100, 75));
                    painel.setPreferredSize(new Dimension(500, 550));
                }

                revalidate();
            }
        });

        // Adiciona ao frame, os componentes criados
        painel.add(tituloLabel);
        painel.add(emailLabel);
        painel.add(campoEmail);
        painel.add(senhaLabel);
        painel.add(campoSenha);
        painel.add(mostrarSenhaCheckbox);
        painel.add(senhaVisivel);
        painel.add(botaoLogin);
        painel.add(botaoCadastro);
        painel.add(botaoMudarSenha);
        add(painelImagem);
        add(painel);

    }

    // Verifica eventos de cliques de mouse
    @Override
    public void mouseClicked(MouseEvent e) {

        // Redireciona para a página de cadastro quando o botão "Criar uma conta" for
        // clicado
        if (e.getSource() == botaoCadastro) {
            telaCadastro.setVisible(true);
            this.dispose();
        }

        if (e.getSource() == botaoLogin) {

            // Tentativa de fazer o login
            try {

                Controle loginController = new Controle();

                // Verifica se a "Mostrar senha" está selecionada, e faz os ajustes de acordo
                if (mostrarSenhaCheckbox.isSelected() || !senhaVisivel.getText().isEmpty()) {
                    campoSenha.setText(senhaVisivel.getText());
                }

                boolean check = loginController.logarConta(this);

                if (check) {
                    // Inicia a aplicação principal
                    listaDeErros.setVisible(true);

                    // Encerra a tela de login
                    this.dispose();
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        // Redireciona para a página de inserir o email para iniciar o processo de
        // alteração de senha
        if (e.getSource() == botaoMudarSenha) {
            AlterarSenha inserirEmail = new AlterarSenha();
            inserirEmail.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Este método está intencionalmente vazio
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Este método está intencionalmente vazio
    }

    // Verifica eventos de entrada de mouse
    @Override
    public void mouseEntered(MouseEvent e) {

        // Altera as bordas e cor do fundo dos botões
        if (e.getSource() == botaoLogin) {
            botaoLogin.setBorderPainted(true);
        }

        if (e.getSource() == botaoCadastro) {
            botaoCadastro.setForeground(new Color(0x007ACC));
        }
    }

    // Verifica eventos de saida de mouse
    @Override
    public void mouseExited(MouseEvent e) {

        // Altera as bordas e cor do fundo dos botões
        if (e.getSource() == botaoLogin) {
            botaoLogin.setBorderPainted(false);
        }

        if (e.getSource() == botaoCadastro) {
            botaoCadastro.setForeground(Color.white);
        }
    }

    // Verifica eventos de ação, e segue a lógica para mostrar ou esconder a senha
    @Override
    public void actionPerformed(ActionEvent e) {

        // Mostra a senha em forma de texto
        if (mostrarSenhaCheckbox.isSelected()) {
            senhaVisivel.setText(new String(campoSenha.getPassword()));
            campoSenha.setVisible(false);
            senhaVisivel.setVisible(true);
            senhaVisivel.requestFocusInWindow();

            // Esconde a senha
        } else if (!mostrarSenhaCheckbox.isSelected() || !senhaVisivel.getText().isEmpty()) {
            campoSenha.setText(senhaVisivel.getText());
            senhaVisivel.setText("");
            campoSenha.setVisible(true);
            campoSenha.requestFocusInWindow();
            senhaVisivel.setVisible(false);
        }
    }

    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public void setCampoEmail(JTextField campoEmail) {
        this.campoEmail = campoEmail;
    }

    public JPasswordField getCampoSenha() {
        return campoSenha;
    }

    public void setCampoSenha(JPasswordField campoSenha) {
        this.campoSenha = campoSenha;
    }

    // Exibe uma mensagem de sucesso quando o usuário realiza o login
    public void loginBemSucedido() {
        loginBemSucedido = new JLabel("Login realizado com sucesso.");
        loginBemSucedido.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, loginBemSucedido, "", JOptionPane.DEFAULT_OPTION);
    }

    // Exibe uma mensagem de erro caso o usuário tente realizar o login com uma
    // conta inexistente
    public void loginNaoSucedido() {
        loginNaoSucedido = new JLabel("Endereço de e-mail ou senha incorretos. Tente novamente.");
        loginNaoSucedido.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, loginNaoSucedido, "", JOptionPane.ERROR_MESSAGE);
    }

    // Chama a instancia de cadastro para permitir interações com a tela de cadastro
    public TelaCadastroGUI getTelaCadastro() {
        return telaCadastro;
    }
}