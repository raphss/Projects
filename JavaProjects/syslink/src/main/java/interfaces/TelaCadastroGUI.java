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

/**
 * Classe de interface gráfica para o cadastro de usuário.
 *
 * @author Raphael Vilete
 */

public class TelaCadastroGUI extends JFrame implements MouseListener, ActionListener {

    // Componentes UI
    JLabel nomeLabel;
    JTextField campoNome;
    JLabel emailLabel;
    JTextField campoEmail;
    JLabel senhaLabel;
    JPasswordField campoSenha;
    JCheckBox mostrarSenhaCheckBox;
    JTextField senhaVisivel;
    public JButton botaoCadastro;
    JButton botaoLogin;
    boolean vericarEmailDuplicado;

    ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
    ImageIcon imagemLogo = new ImageIcon(getClass().getResource("/syslink.png"));
    JPanel painel;
    JPanel painelImagem;
    JLabel imagem;

    private static TelaLoginGUI telaLogin = new TelaLoginGUI();

    // Construtor da classe
    public TelaCadastroGUI() {

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
        nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        nomeLabel.setForeground(Color.white);
        nomeLabel.setBounds(40, 0, 540, 80);

        campoNome = new JTextField();
        campoNome.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoNome.setBounds(40, 60, 420, 55);

        emailLabel = new JLabel("E-mail");
        emailLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        emailLabel.setForeground(Color.white);
        emailLabel.setBounds(40, 115, 540, 80);

        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoEmail.setBounds(40, 175, 420, 55);

        senhaLabel = new JLabel("Senha");
        senhaLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        senhaLabel.setForeground(Color.white);
        senhaLabel.setBounds(40, 230, 540, 80);

        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoSenha.setBounds(40, 290, 420, 55);

        mostrarSenhaCheckBox = new JCheckBox("Mostrar senha");
        mostrarSenhaCheckBox.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        mostrarSenhaCheckBox.setBounds(40, 335, 140, 60);
        mostrarSenhaCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mostrarSenhaCheckBox.setContentAreaFilled(false);
        mostrarSenhaCheckBox.setFocusable(false);
        mostrarSenhaCheckBox.addActionListener(this);
        senhaVisivel = new JTextField();
        senhaVisivel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        senhaVisivel.setBounds(40, 290, 420, 55);
        senhaVisivel.setVisible(false);

        botaoCadastro = new JButton("Cadastrar");
        botaoCadastro.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        botaoCadastro.setForeground(Color.white);
        botaoCadastro.setBackground(new Color(0X27b8cc));
        botaoCadastro.setFocusable(false);
        botaoCadastro.setBounds(100, 410, 300, 60);
        botaoCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoCadastro.setBorderPainted(false);
        botaoCadastro.addMouseListener(this);

        botaoLogin = new JButton("Voltar para o login");
        botaoLogin.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        botaoLogin.setForeground(Color.white);
        botaoLogin.setFocusable(false);
        botaoLogin.setBounds(100, 490, 300, 40);
        botaoLogin.setContentAreaFilled(false);
        botaoLogin.setBorderPainted(false);
        botaoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoLogin.addMouseListener(this);

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
        painel.add(nomeLabel);
        painel.add(campoNome);
        painel.add(emailLabel);
        painel.add(campoEmail);
        painel.add(senhaLabel);
        painel.add(campoSenha);
        painel.add(mostrarSenhaCheckBox);
        painel.add(senhaVisivel);
        painel.add(botaoCadastro);
        painel.add(botaoLogin);
        add(painelImagem);
        add(painel);
    }

    // Verifica eventos de cliques de mouse
    @Override
    public void mouseClicked(MouseEvent e) {

        // Redireciona para a página de login quando o botão "Voltar para o Login" for
        // clicado
        if (e.getSource() == botaoLogin) {
            telaLogin.setVisible(true);
            this.dispose();
        }

        // Lida com o cadastro do usuário quando o botão "Cadastrar" for clicado
        if (e.getSource() == botaoCadastro) {

            StringBuilder passwordString = new StringBuilder();

            for (char c : campoSenha.getPassword()) {
                passwordString.append(c);
            }

            try {

                // Tentativa de fazer o cadastro
                Controle signUpController = new Controle();

                // Verifica se a opção "Mostrar senha" está selecionada e lida de acordo
                if (mostrarSenhaCheckBox.isSelected() || !senhaVisivel.getText().isEmpty()) {
                    campoSenha.setText(senhaVisivel.getText());
                }

                // Chama a classe InserirCodigo para confirmar que o email inserido pelo usuário
                // é real
                InserirCodigo inserirCodigo = new InserirCodigo();

                signUpController.criarConta(this, inserirCodigo);

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

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
        if (e.getSource() == botaoCadastro) {
            botaoCadastro.setBorderPainted(true);
        }

        if (e.getSource() == botaoLogin) {
            botaoLogin.setForeground(new Color(0x007ACC));
        }
    }

    // Verifica eventos de saida de mouse
    @Override
    public void mouseExited(MouseEvent e) {

        // Altera as bordas e cor do fundo dos botões
        if (e.getSource() == botaoCadastro) {
            botaoCadastro.setBorderPainted(false);
        }

        if (e.getSource() == botaoLogin) {
            botaoLogin.setForeground(Color.white);
        }
    }

    // Verifica eventos de ação, e segue a lógica para mostrar ou esconder a senha
    @Override
    public void actionPerformed(ActionEvent e) {

        // Mostra a senha em forma de texto
        if (mostrarSenhaCheckBox.isSelected()) {
            senhaVisivel.setText(new String(campoSenha.getPassword()));
            campoSenha.setVisible(false);
            senhaVisivel.setVisible(true);
            senhaVisivel.requestFocusInWindow();

            // Esconde a senha
        } else if (!mostrarSenhaCheckBox.isSelected() || !senhaVisivel.getText().isEmpty()) {
            campoSenha.setText(senhaVisivel.getText());
            senhaVisivel.setText("");
            campoSenha.setVisible(true);
            campoSenha.requestFocusInWindow();
            senhaVisivel.setVisible(false);
        }
    }

    public JTextField getCampoNome() {
        return campoNome;
    }

    public void setCampoNome(JTextField campoNome) {
        this.campoNome = campoNome;
    }

    public String getCampoEmail() {
        return campoEmail.getText();
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

    // Notifica o usuário de que todos os campos devem ser preenchidos
    public void camposInvalidos() {

        JLabel camposInvalidos = new JLabel("Você deve preencher todos os campos acima.");
        camposInvalidos.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, camposInvalidos, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notifica o usuário de que o nome de usuário não deve conter números ou
    // caracteres especiais
    public void nomeInvalido() {

        JLabel nomeInvalido = new JLabel("O nome de usuário não deve conter números ou caracteres especiais.");
        nomeInvalido.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, nomeInvalido, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notifica o usuário de que o e-mail inserido não parece válido
    public void emailInvalido() {

        JLabel emailInvalido = new JLabel("Este e-mail não parece real. Tente outro.");
        emailInvalido.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, emailInvalido, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notifica o usuário de que a senha deve ter pelo menos oito caracteres
    public void senhaInvalida() {

        JLabel senhaInvalida = new JLabel("A senha deve conter no minimo oito caracteres.");
        senhaInvalida.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, senhaInvalida, "", JOptionPane.ERROR_MESSAGE);
    }

    // Notifica o usuário de que o e-mail inserido já está cadastrado no sistema
    public void emailDuplicado() {

        JLabel emailDuplicado = new JLabel("Este e-mail já está cadastrado. Tente outro.");
        emailDuplicado.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, emailDuplicado, "", JOptionPane.ERROR_MESSAGE);

        vericarEmailDuplicado = true;
    }

    // Notifica o usuário de que a conta foi criada com sucesso
    public void cadastroSucedido() {

        JLabel cadastroSucedido = new JLabel("Sua conta foi cadastrada.");
        cadastroSucedido.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(this, cadastroSucedido, "", JOptionPane.DEFAULT_OPTION);

        telaLogin.getTelaCadastro().setVisible(false);
        telaLogin.setVisible(true);
    }
}