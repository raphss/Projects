package ErrosESolucoes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ErrosESolucoes.BD.DAO;
import ErrosESolucoes.gerenciamento.AdicionarErro;
import ErrosESolucoes.gerenciamento.PesquisarErro;
import interfaces.TelaLoginGUI;

/**
 * Esta classe representa a lista de erros do sistema.
 * Permite visualizar, pesquisar e adicionar novos erros.
 * 
 * @author Raphael Vilete
 */

public class ListaDeErros extends JFrame {

    // Componentes UI
    JMenuBar barraMenu;
    JMenu opcoes;
    JMenuItem sair;
    JMenuItem voltarAoInicio;
    JPanel painelTela;
    JPanel painelVazio;
    JLabel tituloLabel;
    JPanel painelPesquisa;
    JTextField campoPesquisa;
    JButton botaoPesquisar;
    JButton botaoAdicionar;
    JPanel painelErros;
    JScrollPane scroll;

    ImageIcon iconePesquisar = new ImageIcon(getClass().getResource("/searchIcon.png"));
    ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));

    JLabel imagem;
    JPanel painelImagem;
    ImageIcon imagemLogo = new ImageIcon(getClass().getResource("/syslink2.png"));

    private static DAO dao = new DAO();
    private static TelaLoginGUI telaLoginGUI = new TelaLoginGUI();

    /**
     * Construtor da classe ListaDeErros.
     */
    public ListaDeErros() {

        setTitle("Lista de Erros");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1350, 740);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(getWidth(), getHeight()));

        // Ajusta os componentes na tela de acordo com o tamanho da tela
        addComponentListener(new ComponentAdapter() {

            int largura = getWidth();
            int altura = getHeight();

            @Override
            public void componentResized(ComponentEvent e) {

                int novaAltura = getHeight();
                int novaLargura = getWidth();

                if (novaAltura > altura && novaLargura > largura) {
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    painelVazio.setPreferredSize(new Dimension(680, 130));
                    painelImagem.setPreferredSize(new Dimension(735, 180));
                    painelImagem.setLayout(new GridLayout(0, 1, 10, 40));
                    scroll.setPreferredSize(new Dimension(500, 730));
                }

                else if (altura <= 1300 || largura <= 740) {
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    painelVazio.setPreferredSize(new Dimension(80, 130));
                    painelImagem.setPreferredSize(new Dimension(400, 180));
                    painelImagem.setLayout(new GridLayout(0, 1, 10, 40));
                    scroll.setPreferredSize(new Dimension(500, 470));
                }

                altura = novaAltura;
                largura = novaLargura;
                revalidate();
            }
        });

        // Inicialização dos componentes UI
        painelTela = new JPanel();
        painelTela.setLayout(new GridLayout());

        painelVazio = new JPanel();
        painelVazio.setPreferredSize(new Dimension(80, 130));
        painelVazio.setLayout(null);

        imagem = new JLabel();
        imagem.setIcon(imagemLogo);

        painelImagem = new JPanel();
        painelImagem.setPreferredSize(new Dimension(400, 180));
        painelImagem.setLayout(new GridLayout(0, 1, 10, 40));
        painelImagem.add(imagem);

        tituloLabel = new JLabel("  Lista de Erros");
        tituloLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 40));
        tituloLabel.setForeground(Color.black);
        tituloLabel.setBounds(0, 0, 140, 10);
        painelImagem.add(tituloLabel);

        painelErros = new JPanel();
        painelErros.setLayout(new GridLayout(0, 1));

        // Lista todos os erros ao iniciar a aplicação
        try {
            dao.listaErros(painelErros);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        scroll = new JScrollPane(painelErros);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(500, 470));

        painelPesquisa = new JPanel();
        painelPesquisa.setPreferredSize(new Dimension(450, 55));
        painelPesquisa.setLayout(null);
        campoPesquisa = new JTextField();
        campoPesquisa.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoPesquisa.setBounds(0, 0, 350, 55);
        campoPesquisa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Simula o clique no botão de pesquisa
                botaoPesquisar.doClick();
            }

        });
        painelPesquisa.add(campoPesquisa);

        botaoPesquisar = new JButton();
        botaoPesquisar.setFocusable(false);
        botaoPesquisar.setBounds(360, 0, 50, 55);
        iconePesquisar.setImage(iconePesquisar.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        botaoPesquisar.setIcon(iconePesquisar);
        botaoPesquisar.setContentAreaFilled(false);
        botaoPesquisar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoPesquisar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String idErroPesquisado = campoPesquisa.getText();

                // Pesquisa por ID de erro
                if (!idErroPesquisado.isEmpty()) {
                    new PesquisarErro(painelErros, idErroPesquisado);
                }

                // Exibe todos os erros se o campo de pesquisa estiver vazio
                else {

                    try {
                        painelErros.removeAll();
                        dao.listaErros(painelErros);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

        painelPesquisa.add(botaoPesquisar);

        botaoAdicionar = new JButton();
        botaoAdicionar.setFocusable(false);
        botaoAdicionar.setText("Adicionar");
        botaoAdicionar.setFont(new Font("Liberation Sans", Font.PLAIN, 35));
        botaoAdicionar.setBounds(210, 120, 200, 55);
        botaoAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela de adição de erro
                new AdicionarErro(painelErros);
            }

        });

        painelPesquisa.add(botaoAdicionar);

        // Configuração do menu e seus itens
        barraMenu = new JMenuBar();
        opcoes = new JMenu("Opções");
        barraMenu.add(opcoes);
        voltarAoInicio = new JMenuItem("Voltar ao inicio");
        opcoes.add(voltarAoInicio);
        sair = new JMenuItem("Sair");
        opcoes.add(sair);

        sair.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Volta à tela de login e fecha a ListaDeErros
                telaLoginGUI.setVisible(true);
                dispose();
            }

        });

        voltarAoInicio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Volta à lista completa de erros
                    painelErros.removeAll();
                    dao.listaErros(painelErros);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Adiciona os componentes ao painel principal
        painelTela.add(painelImagem);
        painelTela.add(painelVazio);
        painelTela.add(painelPesquisa);
        setJMenuBar(barraMenu);
        add(painelTela);
        add(scroll);
    }
}