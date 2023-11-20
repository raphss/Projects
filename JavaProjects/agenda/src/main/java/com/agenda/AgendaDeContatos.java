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

public class AgendaDeContatos extends JFrame {

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

    DAO dao = new DAO();

    AgendaDeContatos() {

        setTitle("Agenda de contatos");
        setIconImage(icone.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 740);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT, 40, 50));
        setMinimumSize(new Dimension(762, getHeight()));

        // Ajusta os componentes na tela de acordo com o tamanho da tela
        addComponentListener(new ComponentAdapter() {

            int largura = getWidth();
            int altura = getHeight();

            @Override
            public void componentResized(ComponentEvent e) {

                int novaAltura = getHeight();
                int novaLargura = getWidth();

                if (novaAltura > altura && novaLargura > largura) {
                    setLayout(new FlowLayout(FlowLayout.LEFT, 40, 150));
                }

                else if (altura <= 1300 || largura <= 740){
                    setLayout(new FlowLayout(FlowLayout.LEFT, 40, 50));
                }

                altura = novaAltura;
                largura = novaLargura;
                revalidate();
            }
        });

        tituloLabel = new JLabel("Contatos");
        tituloLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 45));
        tituloLabel.setForeground(Color.black);

        painelContatos = new JPanel();
        painelContatos.setLayout(new GridLayout(0, 1));

        scroll = new JScrollPane(painelContatos);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(400, 500));

        painelAdicionar = new JPanel();
        painelAdicionar.setPreferredSize(new Dimension(500, 55));
        painelAdicionar.setLayout(null);

        botaoAdicionar = new JButton("Novo contato+");
        botaoAdicionar.setFont(new Font("Liberation Sans", Font.PLAIN, 25));
        botaoAdicionar.setForeground(Color.black);
        botaoAdicionar.setFocusable(false);
        botaoAdicionar.setBounds(0, 0, 200, 55);
        botaoAdicionar.setForeground(Color.black);
        botaoAdicionar.setContentAreaFilled(false);
        botaoAdicionar.setBorderPainted(false);
        botaoAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarContato(painelContatos);
            }
        });

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
                // Simula o clique no botão de pesquisa
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

                if (!nomePesquisado.isEmpty()) {
                    new PesquisarContato(painelContatos, nomePesquisado);
                }

                else {

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

        try {
            dao.listaContatos(painelContatos);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(tituloLabel);
        add(painelAdicionar);
        add(painelPesquisa);
        add(scroll);
    }
}