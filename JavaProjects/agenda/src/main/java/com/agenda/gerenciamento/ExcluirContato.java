package com.agenda.gerenciamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.agenda.BD.ConexaoBD;

public class ExcluirContato {

    public ExcluirContato(JPanel painelContatos, String nome, JButton nomeBotao) {

        Connection conexao = null;

        try {

            conexao = new ConexaoBD().getConexao();

            String deleteSql = "DELETE FROM AGENDA_CONTATOS WHERE NOME = ?";
            PreparedStatement statement = conexao.prepareStatement(deleteSql);
            statement.setString(1, nome);
            statement.executeUpdate();

            conexao.close();

            painelContatos.remove(nomeBotao);
            painelContatos.revalidate();
            painelContatos.repaint();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}