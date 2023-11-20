package ErrosESolucoes.gerenciamento;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import BD.ConexaoBD;
import ErrosESolucoes.BD.DAO;

/**
 * Esta classe gerencia a adição de novos erros no sistema.
 * Permite inserir informações sobre um novo erro, como ID, descrição e solução.
 * 
 * @author Raphael Vilete
 */

public class AdicionarErro extends JFrame {

    // Componentes UI
    JLabel idErroLabel;
    JTextField campoIdErro;
    JLabel descricaoLabel;
    JTextField campoDescricao;
    JLabel solucaoLabel;
    JTextArea campoSolucao;
    JScrollPane scroll;
    JButton adicionarErro;
    JButton cancelar;

    private static DAO dao = new DAO();

    /**
     * Construtor da classe AdicionarErro.
     * 
     * @param painelErros O painel que contém a lista de erros.
     */

    public AdicionarErro(JPanel painelErros) {

        // Configurações do frame
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Adicionar Erro");

        // Inicialização dos componentes UI
        idErroLabel = new JLabel("ID Erro (Apenas dígitos)");
        idErroLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        idErroLabel.setBounds(40, 0, 820, 55);

        campoIdErro = new JTextField();
        campoIdErro.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoIdErro.setBounds(40, 40, 820, 55);

        descricaoLabel = new JLabel("Descrição do Erro");
        descricaoLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        descricaoLabel.setBounds(40, 110, 820, 55);

        campoDescricao = new JTextField();
        campoDescricao.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoDescricao.setBounds(40, 150, 820, 55);

        solucaoLabel = new JLabel("Soluções para o erro");
        solucaoLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        solucaoLabel.setBounds(40, 220, 820, 55);

        campoSolucao = new JTextArea();
        campoSolucao.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoSolucao.setLayout(new FlowLayout());

        scroll = new JScrollPane(campoSolucao);
        scroll.setBounds(40, 270, 820, 200);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        adicionarErro = new JButton("Adicionar");
        adicionarErro.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        adicionarErro.setForeground(Color.white);
        adicionarErro.setBackground(new Color(0X27b8cc));
        adicionarErro.setFocusable(false);
        adicionarErro.setBounds(300, 520, 300, 60);
        adicionarErro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adicionarErro.setBorderPainted(false);
        adicionarErro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Verifica se os campos estão preenchidos
                if (getcampoIdErro().matches("") || getcampoDescricao().matches("") || getcampoSolucao().matches("")) {
                    JOptionPane.showMessageDialog(null, "Os campos não podem ser vazios.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Expressão regular que verifica se há apenas digitos no ID Erro
                else if (!getcampoIdErro().matches("^\\d+$")) {
                    JOptionPane.showMessageDialog(null, "O ID Erro deve conter apenas dígitos.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Insere as informações no banco de dados
                else {

                    Connection conexao = null;

                    try {

                        conexao = new ConexaoBD().getConexao();

                        String insertSql = "INSERT INTO ERROSESOLUCOES (IDERRO, DESCRICAO, SOLUCAO) VALUES (?, ?, ?)";

                        PreparedStatement statement = conexao.prepareStatement(insertSql);
                        statement.setString(1, getcampoIdErro());
                        statement.setString(2, getcampoDescricao());
                        statement.setString(3, getcampoSolucao());

                        // Adiciona o novo erro e suas informações ao banco de dados
                        statement.executeUpdate();

                        conexao.commit();
                        conexao.close();

                        dispose();

                        // Atualiza a lista de erros no painel, primero removendo os botões do painel,
                        // depois gerando-os novamente a partir do banco de dados
                        painelErros.removeAll();

                        try {
                            // Aqui os botẽos são gerados
                            dao.listaErros(painelErros);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        // O ID Erro é uma chave primária. Neste caso uma mensagem de erro informa ao
                        // usuário de que o ID Erro em especifico já está registrado no banco de dados
                        JOptionPane.showMessageDialog(null, "Este ID Erro já está registrado.",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        cancelar.setForeground(new Color(0x007ACC));
        cancelar.setFocusable(false);
        cancelar.setBounds(390, 590, 120, 40);
        cancelar.setContentAreaFilled(false);
        cancelar.setBorderPainted(false);
        cancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Fecha o frame
                dispose();
            }

        });

        // Adiciona ao frame, os componentes criados
        add(idErroLabel);
        add(campoIdErro);
        add(descricaoLabel);
        add(campoDescricao);
        add(solucaoLabel);
        add(scroll);
        add(adicionarErro);
        add(cancelar);

        setVisible(true);
    }

    /**
     * Obtém o valor atual do campo de ID Erro.
     * 
     * @return O valor do campo de ID Erro.
     */

    public String getcampoIdErro() {
        return campoIdErro.getText();
    }

    /**
     * Obtém o valor atual do campo de descrição.
     * 
     * @return O valor do campo de descrição.
     */

    public String getcampoDescricao() {
        return campoDescricao.getText();
    }

    /**
     * Obtém o valor atual do campo de solução.
     * 
     * @return O valor do campo de solução.
     */

    public String getcampoSolucao() {
        return campoSolucao.getText();
    }
}