package com.agenda;

/**
 * Aplicação principal da Agenda de Contatos.
 * 
 * Esta classe contém o método principal para iniciar a aplicação da Agenda de
 * Contatos.
 * 
 * @author Raphael Vilete
 * @version 2.0
 */

public class AgendaApplication {

    /**
     * Método principal que inicia a aplicação da Agenda de Contatos.
     * 
     * @param args Os argumentos de linha de comando (não utilizados).
     */

    public static void main(String[] args) {

        // Cria uma instância da classe AgendaDeContatos
        AgendaDeContatos agendaDeContatos = new AgendaDeContatos();

        // Exibe a janela principal da Agenda de Contatos
        agendaDeContatos.setVisible(true);
    }
}