package com.agenda.BD;

// import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Uma classe utilitária para estabelecer uma conexão com o banco de dados da
 * Agenda de Contatos.
 * 
 * Os parâmetros de conexão com o banco de dados (nome do banco de dados, URL,
 * nome de usuário, senha) são definidos aqui.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class ConexaoBD {

    /**
     * Obtem uma conexão com o banco de dados.
     * 
     * @return Um objeto de conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro relacionado ao banco de dados.
     */

    public Connection getConexao() throws SQLException {

        Connection conexao = null;

        try {

            String dbNome = "agendaDeContatos";

            String dbUrl = "jdbc:h2:./JavaProjects/agenda/src/main/resources/" + dbNome; // Para executar pela ide

            /*
             * Para executar pelo .jar
             * 
             * String caminhoDocumentos = System.getProperty("user.home") + "\\Documents";
             * String caminhoBancoDados = Paths.get(caminhoDocumentos, "Agenda de Contatos",
             * dbNome).toString();
             * String dbUrl = "jdbc:h2:" + caminhoBancoDados;
             */

            // Estabelece uma conexão com o banco de dados usando a URL, nome de usuário e
            // senha especificados
            conexao = DriverManager.getConnection(dbUrl, "root", "root");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conexao;
    }
}