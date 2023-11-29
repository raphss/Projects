package com.agenda.gerenciamento;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.agenda.BD.ConexaoBD;
import com.agenda.BD.DAO;

public class AdicionarContato extends JFrame {

    JLabel nomeLabel;
    JTextField campoNome;
    JLabel telefoneLabel;
    JTextField campoTelefone;
    JLabel enderecoLabel;
    JTextField campoEndereco;
    JButton adicionarContato;
    JButton cancelar;

    private static DAO dao = new DAO();

    public AdicionarContato(JPanel painelContatos) {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Novo Contato");

        nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        nomeLabel.setBounds(40, 0, 420, 55);

        campoNome = new JTextField();
        campoNome.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoNome.setBounds(40, 40, 420, 55);

        telefoneLabel = new JLabel("Telefone");
        telefoneLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        telefoneLabel.setBounds(40, 110, 420, 55);

        campoTelefone = new JTextField();
        campoTelefone.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoTelefone.setBounds(40, 150, 420, 55);

        enderecoLabel = new JLabel("Endereço");
        enderecoLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        enderecoLabel.setBounds(40, 220, 420, 55);

        campoEndereco = new JTextField();
        campoEndereco.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoEndereco.setBounds(40, 270, 420, 55);

        adicionarContato = new JButton("Adicionar");
        adicionarContato.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        adicionarContato.setForeground(Color.white);
        adicionarContato.setBackground(new Color(0X27b8cc));
        adicionarContato.setFocusable(false);
        adicionarContato.setBounds(100, 370, 300, 60);
        adicionarContato.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adicionarContato.setBorderPainted(false);
        adicionarContato.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (getcampoNome().matches("") || getcampoTelefone().matches("") || getcampoEndereco().matches("")) {
                    JOptionPane.showMessageDialog(null, "Os campos não podem ser vazios.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                else {

                    Connection conexao = null;

                    try {

                        conexao = new ConexaoBD().getConexao();

                        String insertSql = "INSERT INTO AGENDA_CONTATOS (NOME, TELEFONE, ENDERECO) VALUES (?, ?, ?)";

                        PreparedStatement statement = conexao.prepareStatement(insertSql);
                        statement.setString(1, getcampoNome());
                        statement.setString(2, getcampoTelefone());
                        statement.setString(3, getcampoEndereco());

                        statement.executeUpdate();

                        conexao.commit();
                        conexao.close();

                        dispose();

                        painelContatos.removeAll();

                        try {
                            dao.listaContatos(painelContatos);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Este nome já está registrado. Tente outro.",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        cancelar.setForeground(new Color(0x007ACC));
        cancelar.setFocusable(false);
        cancelar.setBounds(100, 430, 300, 80);
        cancelar.setContentAreaFilled(false);
        cancelar.setBorderPainted(false);
        cancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });
        add(nomeLabel);
        add(campoNome);
        add(telefoneLabel);
        add(campoTelefone);
        add(enderecoLabel);
        add(campoEndereco);
        add(adicionarContato);
        add(cancelar);

        setVisible(true);
    }

    public String getcampoNome() {
        return campoNome.getText();
    }

    public String getcampoTelefone() {
        return campoTelefone.getText();
    }

    public String getcampoEndereco() {
        return campoEndereco.getText();
    }
}