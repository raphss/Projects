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

public class AlterarContato extends JFrame {

    JLabel nomeAtual;
    JTextField novoNome;
    JLabel telefoneAtual;
    JTextField novoTelefone;
    JLabel enderecoAtual;
    JTextField novoEndereco;
    JButton salvarInformacoes;
    JButton cancelar;

    private static DAO dao = new DAO();

    public AlterarContato(JPanel painelContatos, String nome, String telefone, String endereco) {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle(("Alterar Contato"));

        nomeAtual = new JLabel("Nome atual: " + nome);
        nomeAtual.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        nomeAtual.setBounds(40, 0, 420, 55);

        novoNome = new JTextField();
        novoNome.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        novoNome.setBounds(40, 40, 420, 55);

        telefoneAtual = new JLabel("Telefone atual: " + telefone);
        telefoneAtual.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        telefoneAtual.setBounds(40, 110, 420, 55);

        novoTelefone = new JTextField();
        novoTelefone.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        novoTelefone.setBounds(40, 150, 420, 55);

        enderecoAtual = new JLabel("Endereço atual: " + endereco);
        enderecoAtual.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        enderecoAtual.setBounds(40, 220, 420, 55);

        novoEndereco = new JTextField();
        novoEndereco.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        novoEndereco.setBounds(40, 270, 420, 55);

        salvarInformacoes = new JButton("Salvar");
        salvarInformacoes.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        salvarInformacoes.setForeground(Color.white);
        salvarInformacoes.setBackground(new Color(0X27b8cc));
        salvarInformacoes.setFocusable(false);
        salvarInformacoes.setBounds(100, 370, 300, 60);
        salvarInformacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salvarInformacoes.setBorderPainted(false);
        salvarInformacoes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (getNovoNome().matches("") || getNovoTelefone().matches("") || getNovoEndereco().matches("")) {
                    JOptionPane.showMessageDialog(null, "Os campos não podem ser vazios.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                else {

                    Connection conexao = null;

                    try {

                        conexao = new ConexaoBD().getConexao();

                        String updateSql = "UPDATE AGENDA_CONTATOS SET NOME=?, TELEFONE=?, ENDERECO=? WHERE NOME=?";

                        PreparedStatement statement = conexao.prepareStatement(updateSql);
                        statement.setString(1, getNovoNome());
                        statement.setString(2, getNovoTelefone());
                        statement.setString(3, getNovoEndereco());
                        statement.setString(4, nome);

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
        add(nomeAtual);
        add(novoNome);
        add(telefoneAtual);
        add(novoTelefone);
        add(enderecoAtual);
        add(novoEndereco);
        add(salvarInformacoes);
        add(cancelar);

        setVisible(true);
    }

    public String getNovoNome() {
        return novoNome.getText();
    }

    public String getNovoTelefone() {
        return novoTelefone.getText();
    }

    public String getNovoEndereco() {
        return novoEndereco.getText();
    }
}