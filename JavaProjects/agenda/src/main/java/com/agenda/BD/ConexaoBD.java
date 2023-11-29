package com.agenda.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    public Connection getConexao() throws SQLException {

        Connection conexao = null;

        try {

            String dbNome = "agendaDeContatos";
            // String dbUrl = "jdbc:h2:./" + dbNome; // para o arquivo .jar
            String dbUrl = "jdbc:h2:./JavaProjects/agenda/src/main/resources/" + dbNome; // Para executar pela ide

            conexao = DriverManager.getConnection(dbUrl, "root", "root");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conexao;
    }
}