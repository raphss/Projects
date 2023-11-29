package ErrosESolucoes.gerenciamento;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
 * Classe para exibir ou excluir os erros, além de permitir a atualização das
 * informações.
 * Esta classe fornece uma interface de usuário para visualizar e gerenciar
 * detalhes do erro.
 * 
 * @author Raphael Vilete
 */

public class ExibirOuExcluirErro extends JFrame {

    // Componentes UI
    JLabel idErroLabel;
    JTextField campoIdErro;
    JLabel descricaoLabel;
    JTextField campoDescricao;
    JLabel solucaoLabel;
    JTextArea campoSolucao;
    JScrollPane scroll;
    JButton excluir;
    JButton editar;
    JButton fechar;
    JButton salvarInformacoes;

    ImageIcon iconeExcluir = new ImageIcon(getClass().getResource("/trash.png"));

    private static DAO dao = new DAO();

    /**
     * Construtor da classe ExibirOuExcluirErro.
     * 
     * @param painelErros O painel que contém a lista de erros.
     * @param idErro      O identificador único do erro.
     * @param descricao   A descrição do erro.
     * @param solucao     A solução para o erro.
     */

    public ExibirOuExcluirErro(JPanel painelErros, String idErro, String descricao, String solucao) {

        // Configurações do frame
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle(("Erro " + idErro));

        // Inicialização dos componentes UI
        idErroLabel = new JLabel("ID Erro (Apenas dígitos)");
        idErroLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        idErroLabel.setBounds(40, 0, 820, 55);

        campoIdErro = new JTextField();
        campoIdErro.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoIdErro.setBounds(40, 40, 820, 55);
        campoIdErro.setText(idErro);
        campoIdErro.setEditable(false);

        descricaoLabel = new JLabel("Descrição do Erro");
        descricaoLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        descricaoLabel.setBounds(40, 110, 820, 55);

        campoDescricao = new JTextField();
        campoDescricao.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoDescricao.setBounds(40, 150, 820, 55);
        campoDescricao.setText(descricao);
        campoDescricao.setEditable(false);

        solucaoLabel = new JLabel("Soluções para o erro");
        solucaoLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        solucaoLabel.setBounds(40, 220, 820, 55);

        campoSolucao = new JTextArea();
        campoSolucao.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoSolucao.setLayout(new FlowLayout());
        campoSolucao.setText(solucao);
        campoSolucao.setEditable(false);

        scroll = new JScrollPane(campoSolucao);
        scroll.setBounds(40, 270, 820, 200);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        excluir = new JButton();
        excluir.setFocusable(false);
        excluir.setBounds(810, 520, 50, 60);
        iconeExcluir.setImage(iconeExcluir.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        excluir.setIcon(iconeExcluir);
        excluir.setBackground(new Color(255, 0, 0, 150));
        excluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        excluir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Confirmação de exclusão
                int escolha = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (escolha == JOptionPane.YES_NO_OPTION) {

                    Connection conexao = null;

                    try {

                        conexao = new ConexaoBD().getConexao();

                        String deleteSql = "DELETE FROM ERROSESOLUCOES WHERE IDERRO = ?";
                        PreparedStatement statement = conexao.prepareStatement(deleteSql);
                        statement.setString(1, idErro);

                        // Exclui o erro do banco de dados
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
                    }
                }
            }
        });

        editar = new JButton("Editar");
        editar.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        editar.setForeground(Color.white);
        editar.setBackground(new Color(0X27b8cc));
        editar.setFocusable(false);
        editar.setBounds(300, 520, 300, 60);
        editar.setBorderPainted(false);
        editar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Habilita a edição dos campos e das informações contidas neles
                campoIdErro.setEditable(true);
                campoDescricao.setEditable(true);
                campoSolucao.setEditable(true);
                editar.setVisible(false);
                salvarInformacoes.setVisible(true);
                fechar.setText("Cancelar");
            }
        });

        fechar = new JButton("Fechar");
        fechar.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        fechar.setForeground(new Color(0x007ACC));
        fechar.setFocusable(false);
        fechar.setBounds(390, 590, 120, 40);
        fechar.setContentAreaFilled(false);
        fechar.setBorderPainted(false);
        fechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fechar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Fecha o frame
                dispose();
            }

        });

        salvarInformacoes = new JButton("Salvar");
        salvarInformacoes.setVisible(false);
        salvarInformacoes.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        salvarInformacoes.setForeground(Color.white);
        salvarInformacoes.setBackground(new Color(0X27b8cc));
        salvarInformacoes.setFocusable(false);
        salvarInformacoes.setBounds(300, 520, 300, 60);
        salvarInformacoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salvarInformacoes.setBorderPainted(false);
        salvarInformacoes.addActionListener(new ActionListener() {

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

                // Insere as informações editadas no banco de dados
                else {

                    Connection conexao = null;

                    try {

                        conexao = new ConexaoBD().getConexao();

                        String updateSql = "UPDATE ERROSESOLUCOES SET IDERRO =?, DESCRICAO =?, SOLUCAO=? WHERE IDERRO =?";

                        PreparedStatement statement = conexao.prepareStatement(updateSql);
                        statement.setString(1, getcampoIdErro());
                        statement.setString(2, getcampoDescricao());
                        statement.setString(3, getcampoSolucao());
                        statement.setString(4, idErro);

                        // Atualiza as informações sobre o erro
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

        // Adiciona ao frame, os componentes criados
        add(idErroLabel);
        add(campoIdErro);
        add(descricaoLabel);
        add(campoDescricao);
        add(solucaoLabel);
        add(scroll);
        add(excluir);
        add(editar);
        add(salvarInformacoes);
        add(fechar);

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