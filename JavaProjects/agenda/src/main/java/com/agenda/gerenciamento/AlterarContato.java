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

/**
 * Classe que representa a interface gráfica para alterar informações de um
 * contato na agenda.
 * 
 * Permite ao usuário modificar o nome, telefone e endereço de um contato
 * específico.
 * 
 * Além disso, atualiza a lista de contatos no painel após as modificações.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class AlterarContato extends JFrame {

    // Componentes UI
    JLabel nomeAtual;
    JTextField novoNome;
    JLabel telefoneAtual;
    JTextField novoTelefone;
    JLabel enderecoAtual;
    JTextField novoEndereco;
    JButton salvarInformacoes;
    JButton cancelar;

    // Instância do DAO para manipulação do banco de dados
    private static DAO dao = new DAO();

    /**
     * Construtor da classe AlterarContato.
     * 
     * @param painelContatos O painel que contém a lista de contatos.
     * @param nome           O nome atual do contato.
     * @param telefone       O telefone atual do contato.
     * @param endereco       O endereço atual do contato.
     */

    public AlterarContato(JPanel painelContatos, String nome, String telefone, String endereco) {

        // Configurações do JFrame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle(("Alterar Contato"));

        // Inicialização dos componentes UI
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

                // Verifica se algum campo está vazio
                if (getNovoNome().matches("") || getNovoNome().matches(" ") || getNovoTelefone().matches("")
                        || getNovoTelefone().matches(" ") || getNovoEndereco().matches("")
                        || getNovoEndereco().matches(" ")) {
                    JOptionPane.showMessageDialog(null, "Os campos não podem ser vazios.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Verifica se o novo nome contém caracteres inválidos
                else if (getNovoNome().matches(".*\\d.*|.*[^\\p{L}\\s].*")) {
                    JOptionPane.showMessageDialog(null, "Nomes devem conter apenas letras.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Verifica se o novo telefone contém caracteres inválidos
                else if (getNovoTelefone().matches(".*[^\\d()\\-\\s].*")) {
                    JOptionPane.showMessageDialog(null, "O Telefone não deve conter letras ou caracteres especiais.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }

                // Se todos os campos estiverem preenchidos corretamente...
                else {

                    Connection conexao = null;

                    try {

                        // Estabelece a conexão com o banco de dados
                        conexao = new ConexaoBD().getConexao();

                        // Prepara a instrução SQL para atualizar o contato
                        String updateSql = "UPDATE AGENDA_CONTATOS SET NOME=?, TELEFONE=?, ENDERECO=? WHERE NOME=?";

                        PreparedStatement statement = conexao.prepareStatement(updateSql);
                        statement.setString(1, getNovoNome());
                        statement.setString(2, getNovoTelefone());
                        statement.setString(3, getNovoEndereco());
                        statement.setString(4, nome);

                        // Executa a instrução SQL para atualizar o contato
                        statement.executeUpdate();

                        conexao.commit();
                        // Fecha a conexão com o banco de dados
                        conexao.close();

                        // Fecha a janela de alterações de contato
                        dispose();

                        // Limpa o painel de contatos
                        painelContatos.removeAll();

                        try {
                            // Gera novamente os botões a partir do banco de dados e adiciona-os ao painel
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
                // Fecha a janela de alterações de contato
                dispose();
            }

        });

        // Adiciona ao JFrame, os componentes criados
        add(nomeAtual);
        add(novoNome);
        add(telefoneAtual);
        add(novoTelefone);
        add(enderecoAtual);
        add(novoEndereco);
        add(salvarInformacoes);
        add(cancelar);

        // Torna o JFrame visivel
        setVisible(true);
    }

    /**
     * Obtém o novo nome inserido no campo de texto.
     * 
     * @return O novo nome.
     */

    public String getNovoNome() {
        return novoNome.getText();
    }

    /**
     * Obtém o novo telefone inserido no campo de texto.
     * 
     * @return O novo telefone.
     */

    public String getNovoTelefone() {
        return novoTelefone.getText();
    }

    /**
     * Obtém o novo endereço inserido no campo de texto.
     * 
     * @return O novo endereço.
     */

    public String getNovoEndereco() {
        return novoEndereco.getText();
    }
}