package ErrosESolucoes.BD;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ErrosESolucoes.gerenciamento.ExibirOuExcluirErro;
import BD.ConexaoBD;

/**
 * Data Access Object (DAO) = classe para a interação com o banco de dados.
 * 
 * @author Raphael Vilete
 */

public class DAO {

    /**
     * Seleciona informações de erro do banco de dados e gera botões para cada erro.
     * 
     * @param painelErros O JPanel para exibir os botões de erro.
     * @throws SQLException Se ocorrer um erro ao interagir com o banco de dados.
     */

    public void listaErros(JPanel painelErros) throws SQLException {

        painelErros.revalidate();
        painelErros.repaint();

        Connection conexao = null;

        try {

            conexao = new ConexaoBD().getConexao();

            String sql = "SELECT IDERRO, DESCRICAO, SOLUCAO FROM ERROSESOLUCOES ORDER BY IDERRO";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idErro = resultSet.getString("IDERRO");
                String descricao = resultSet.getString("DESCRICAO");
                String solucao = resultSet.getString("SOLUCAO");

                gerarBotao(painelErros, idErro, descricao, solucao);

            }

            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gera um botão para um erro específico e o adiciona ao JPanel fornecido.
     * 
     * @param painelErros O JPanel para adicionar o botão de erro.
     * @param idErro      O ID do erro.
     * @param descricao   A descrição do erro.
     * @param solucao     A solução para o erro.
     */

    public void gerarBotao(JPanel painelErros, String idErro, String descricao, String solucao) {

        JButton erroBotao = new JButton();
        erroBotao.setText("• Erro: " + idErro);
        erroBotao.setFont(new Font("Liberation Sans", Font.PLAIN, 35));
        erroBotao.setFocusable(false);
        erroBotao.setForeground(Color.black);
        erroBotao.setPreferredSize(new Dimension(100, 55));
        erroBotao.setContentAreaFilled(false);
        erroBotao.setBorderPainted(false);
        erroBotao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        erroBotao.setHorizontalAlignment(SwingConstants.LEFT);
        erroBotao.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ExibirOuExcluirErro(painelErros, idErro, descricao, solucao);
            }
        });

        // Adiciona efeito de destaque ao passar o mouse sobre o botão
        erroBotao.addMouseListener(new MouseListener() {

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
                if (e.getSource() == erroBotao) {
                    erroBotao.setForeground(new Color(0x007ACC));
                    erroBotao.setFocusable(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == erroBotao) {
                    erroBotao.setForeground(Color.black);
                    erroBotao.setFocusable(false);
                }
            }
        });

        painelErros.add(erroBotao);
    }
}