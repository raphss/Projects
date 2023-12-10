package com.agenda.gerenciamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.agenda.BD.ConexaoBD;

/**
 * Esta classe gerencia a exclusão de contatos na agenda.
 * Permite excluir um contato específico do banco de dados
 * e atualizar a interface gráfica.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class ExcluirContato {

    /**
     * Construtor da classe ExcluirContato.
     * 
     * @param painelContatos O painel que contém a lista de contatos.
     * @param nome           O nome do contato a ser excluído.
     * @param nomeBotao      O botão associado ao contato na interface gráfica.
     */

    public ExcluirContato(JPanel painelContatos, String nome, JButton nomeBotao) {

        Connection conexao = null;

        try {

            // Estabelece a conexão com o banco de dados
            conexao = new ConexaoBD().getConexao();

            // Prepara a instrução SQL para excluir o contato
            String deleteSql = "DELETE FROM AGENDA_CONTATOS WHERE NOME = ?";
            PreparedStatement statement = conexao.prepareStatement(deleteSql);
            statement.setString(1, nome);

            // Executa a instrução SQL para excluir o contato
            statement.executeUpdate();

            // Fecha a conexão com o banco de dados
            conexao.close();

            // Remove o botão do painel de contatos
            painelContatos.remove(nomeBotao);

            // Limpa e atualiza o painel de contatos
            painelContatos.revalidate();
            painelContatos.repaint();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}