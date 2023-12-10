package com.agenda;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.agenda.BD.DAO;
import com.agenda.gerenciamento.AdicionarContato;
import com.agenda.gerenciamento.PesquisarContato;

/**
 * Classe principal que representa a interface da Agenda de Contatos.
 * Extende JFrame para criar a interface gráfica.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class AgendaDeContatos extends JFrame {

    // Componentes UI
    JLabel tituloLabel;
    JPanel painelAdicionar;
    JButton botaoAdicionar;
    JPanel painelPesquisa;
    JTextField areaDePesquisa;
    JButton botaoPesquisar;
    JPanel painelContatos;
    JScrollPane scroll;

    ImageIcon icone = new ImageIcon(getClass().getResource("/icone.png"));
    ImageIcon iconePesquisar = new ImageIcon(getClass().getResource("/searchIcon.png"));

    // Objeto de acesso ao banco de dados
    DAO dao = new DAO();

    /**
     * Construtor da classe AgendaDeContatos.
     * Configura a interface gráfica e inicializa os componentes.
     */

    AgendaDeContatos() {

        // Configurações do JFrame
        setTitle("Agenda de contatos");
        setIconImage(icone.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 740);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        setMinimumSize(new Dimension(1000, getHeight()));

        // Ajusta os componentes na tela de acordo com o tamanho da tela
        addComponentListener(new ComponentAdapter() {

            int largura = getWidth();
            int altura = getHeight();

            @Override
            public void componentResized(ComponentEvent e) {

                int novaAltura = getHeight();
                int novaLargura = getWidth();

                if (novaAltura > altura && novaLargura > largura) {
                    setLayout(new FlowLayout(FlowLayout.LEFT, 80, 80));
                    painelPesquisa.setPreferredSize(new Dimension(550, 55));
                    painelPesquisa.setLayout(null);
                    areaDePesquisa.setBounds(130, 0, 350, 55);
                    botaoPesquisar.setBounds(490, 0, 50, 55);
                }

                else if (altura <= 1300 || largura <= 740) {
                    setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
                    painelPesquisa.setPreferredSize(new Dimension(450, 55));
                    painelPesquisa.setLayout(null);
                    areaDePesquisa.setBounds(0, 0, 350, 55);
                    botaoPesquisar.setBounds(360, 0, 50, 55);
                }

                altura = novaAltura;
                largura = novaLargura;
                revalidate();
            }
        });

        // Inicialização e configuração dos componentes UI
        tituloLabel = new JLabel("Contatos");
        tituloLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 45));
        tituloLabel.setForeground(Color.black);

        painelContatos = new JPanel();
        painelContatos.setLayout(new GridLayout(0, 1));

        scroll = new JScrollPane(painelContatos);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(500, 500));

        painelAdicionar = new JPanel();
        painelAdicionar.setPreferredSize(new Dimension(500, 55));
        painelAdicionar.setLayout(null);

        botaoAdicionar = new JButton("Adicionar contato");
        botaoAdicionar.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        botaoAdicionar.setForeground(Color.black);
        botaoAdicionar.setFocusable(false);
        botaoAdicionar.setBounds(30, 0, 240, 55);
        botaoAdicionar.setForeground(Color.black);
        botaoAdicionar.setContentAreaFilled(false);
        botaoAdicionar.setBorderPainted(true);
        botaoAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela de adição de contato
                new AdicionarContato(painelContatos);
            }
        });

        // Adiciona efeito de destaque ao passar o mouse sobre o botão
        botaoAdicionar.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Este método está intencionalmente vazio
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Este método está intencionalmente vazio
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Este método está intencionalmente vazio
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == botaoAdicionar) {
                    botaoAdicionar.setForeground(new Color(0x007ACC));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == botaoAdicionar) {
                    botaoAdicionar.setForeground(Color.black);
                }
            }
        });

        painelAdicionar.add(botaoAdicionar);

        painelPesquisa = new JPanel();
        painelPesquisa.setPreferredSize(new Dimension(450, 55));
        painelPesquisa.setLayout(null);
        areaDePesquisa = new JTextField();
        areaDePesquisa.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        areaDePesquisa.setBounds(0, 0, 350, 55);
        areaDePesquisa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Simula o clique no botão de pesquisa ao pressionar a tecla "Enter"
                botaoPesquisar.doClick();
            }

        });
        painelPesquisa.add(areaDePesquisa);

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

                String nomePesquisado = areaDePesquisa.getText();

                // Lista todos os contatos correspondentes à pesquisa
                if (!nomePesquisado.isEmpty()) {
                    new PesquisarContato(painelContatos, nomePesquisado);
                }

                else {

                    // Lista todos os contatos novamente
                    try {
                        painelContatos.removeAll();
                        dao.listaContatos(painelContatos);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

        painelPesquisa.add(botaoPesquisar);

        // Lista todos os contatos ao iniciar a aplicação
        try {
            dao.listaContatos(painelContatos);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adiciona ao JFrame, os componentes criados
        add(tituloLabel);
        add(painelAdicionar);
        add(painelPesquisa);
        add(scroll);
    }
}