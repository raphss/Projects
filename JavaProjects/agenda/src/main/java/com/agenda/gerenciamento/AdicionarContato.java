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
 * Classe que representa a interface gráfica para adicionar um novo
 * contato à agenda.
 * 
 * Permite ao usuário inserir informações como nome, telefone e endereço
 * para criar um novo contato.
 * 
 * Após a adição do contato, atualiza a lista de contatos no painel.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class AdicionarContato extends JFrame {

    // Componentes UI
    JLabel nomeLabel;
    JTextField campoNome;
    JLabel telefoneLabel;
    JTextField campoTelefone;
    JLabel enderecoLabel;
    JTextField campoEndereco;
    JButton adicionarContato;
    JButton cancelar;

    // Instância do DAO para manipulação do banco de dados
    private static DAO dao = new DAO();

    /**
     * Construtor da classe AdicionarContato.
     * 
     * @param painelContatos O painel que contém a lista de contatos.
     */

    public AdicionarContato(JPanel painelContatos) {

        // Configurações do JFrame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Novo Contato");

        // Inicialização dos componentes UI
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

                // Verifica se algum campo está vazio
                if (getcampoNome().matches("") || getcampoNome().matches(" ") || getcampoTelefone().matches("")
                        || getcampoTelefone().matches(" ") || getcampoEndereco().matches("")
                        || getcampoEndereco().matches(" ")) {
                    JOptionPane.showMessageDialog(null, "Os campos não podem ser vazios.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Verifica se o novo nome contém caracteres inválidos
                else if (getcampoNome().matches(".*\\d.*|.*[^\\p{L}\\s].*")) {
                    JOptionPane.showMessageDialog(null, "Nomes devem conter apenas letras.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Verifica se o novo telefone contém caracteres inválidos
                else if (getcampoTelefone().matches(".*[^\\d()\\-\\s].*")) {
                    JOptionPane.showMessageDialog(null, "O Telefone não deve conter letras ou caracteres especiais.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }

                // Se todos os campos estiverem preenchidos corretamente...
                else {

                    Connection conexao = null;

                    try {

                        conexao = new ConexaoBD().getConexao();

                        String insertSql = "INSERT INTO AGENDA_CONTATOS (NOME, TELEFONE, ENDERECO) VALUES (?, ?, ?)";

                        PreparedStatement statement = conexao.prepareStatement(insertSql);
                        statement.setString(1, getcampoNome());
                        statement.setString(2, getcampoTelefone());
                        statement.setString(3, getcampoEndereco());

                        // Executa a instrução SQL para adicionar o novo contato
                        statement.executeUpdate();

                        conexao.commit();
                        // Fecha a conexão com o banco de dados
                        conexao.close();

                        // Fecha a janela de adição de contato
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
                // Fecha a janela de adição de contato
                dispose();
            }

        });

        // Adiciona ao JFrame os componentes criados
        add(nomeLabel);
        add(campoNome);
        add(telefoneLabel);
        add(campoTelefone);
        add(enderecoLabel);
        add(campoEndereco);
        add(adicionarContato);
        add(cancelar);

        // Torna o JFrame visível
        setVisible(true);
    }

    /**
     * Obtém o nome inserido no campo de texto.
     * 
     * @return O nome do novo contato.
     */

    public String getcampoNome() {
        return campoNome.getText();
    }

    /**
     * Obtém o telefone inserido no campo de texto.
     * 
     * @return O telefone do novo contato.
     */

    public String getcampoTelefone() {
        return campoTelefone.getText();
    }

    /**
     * Obtém o endereço inserido no campo de texto.
     * 
     * @return O endereço do novo contato.
     */

    public String getcampoEndereco() {
        return campoEndereco.getText();
    }
}