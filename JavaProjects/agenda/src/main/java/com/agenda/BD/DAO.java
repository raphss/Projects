package com.agenda.BD;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.agenda.gerenciamento.AlterarContato;
import com.agenda.gerenciamento.ExcluirContato;

/**
 * Data Access Object (DAO) para interação com o banco de dados da
 * Agenda de Contatos.
 * 
 * Esta classe realiza operações relacionadas ao banco de dados,
 * como listar contatos e gerar botões.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class DAO {

    /**
     * Lista todos os contatos do banco de dados e gera botões para cada um.
     * 
     * @param painelContatos O JPanel para exibir os botões de contato.
     * @throws SQLException Se ocorrer um erro ao interagir com o banco de dados.
     */

    public void listaContatos(JPanel painelContatos) throws SQLException {

        // Limpa e atualiza o painel de contatos
        painelContatos.revalidate();
        painelContatos.repaint();

        Connection conexao = null;

        try {

            conexao = new ConexaoBD().getConexao();

            // Consulta SQL para obter informações sobre os contatos
            String sql = "SELECT NOME, TELEFONE, ENDERECO FROM AGENDA_CONTATOS ORDER BY NOME";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Itera sobre os resultados da consulta
            while (resultSet.next()) {
                String nome = resultSet.getString("NOME");
                String telefone = resultSet.getString("TELEFONE");
                String endereco = resultSet.getString("ENDERECO");

                gerarBotao(painelContatos, nome, telefone, endereco);

            }

            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gera um botão para um contato específico e o adiciona ao JPanel fornecido.
     * 
     * @param painelContatos O JPanel para adicionar o botão de contato.
     * @param nome           O nome do contato.
     * @param telefone       O telefone do contato.
     * @param endereco       O endereço do contato.
     */

    public void gerarBotao(JPanel painelContatos, String nome, String telefone, String endereco) {

        JButton nomeBotao = new JButton();
        nomeBotao.setText("• " + nome);
        nomeBotao.setFont(new Font("Liberation Sans", Font.PLAIN, 35));
        nomeBotao.setFocusable(false);
        nomeBotao.setForeground(Color.black);
        nomeBotao.setPreferredSize(new Dimension(300, 55));
        nomeBotao.setContentAreaFilled(false);
        nomeBotao.setBorderPainted(false);
        nomeBotao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nomeBotao.setHorizontalAlignment(SwingConstants.LEFT);
        nomeBotao.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String mensagem = "Nome: " + nome + "\nTelefone: " + telefone + "\nEndereço: " + endereco;
                String[] opcoes = { "Alterar", "Excluir", "Fechar" };

                int infoEOpcao = JOptionPane.showOptionDialog(null, mensagem, "Informações do Contato",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, opcoes, opcoes[2]);

                if (infoEOpcao == 0) {
                    // Abre a janela para alterar as informações do contato
                    new AlterarContato(painelContatos, nome, telefone, endereco);
                }

                if (infoEOpcao == 1) {

                    // Opções personalizadas para o diálogo de confirmação
                    String[] simOuNao = { "Sim", "Não" };

                    // Confirmação de exclusão
                    int escolha = JOptionPane.showOptionDialog(null, "Você tem certeza?", "",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, simOuNao, simOuNao[1]);

                    if (escolha == JOptionPane.YES_NO_OPTION) {
                        // Realiza a exclusão do contato selecionado
                        new ExcluirContato(painelContatos, nome, nomeBotao);
                    }
                }
            }
        });

        // Adiciona efeito de destaque ao passar o mouse sobre o botão
        nomeBotao.addMouseListener(new MouseListener() {

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
                if (e.getSource() == nomeBotao) {
                    nomeBotao.setForeground(new Color(0x007ACC));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == nomeBotao) {
                    nomeBotao.setForeground(Color.black);
                }
            }
        });

        // Adiciona o botão ao painel de contatos
        painelContatos.add(nomeBotao);
    }
}