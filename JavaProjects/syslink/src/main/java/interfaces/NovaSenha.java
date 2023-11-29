package interfaces;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Classe de interface gráfica para definir uma nova senha.
 * 
 * Esta classe permite que o usuário defina uma nova senha para a sua conta.
 * 
 * Ela oferece um campo para inserção da nova senha, além de botões para confirmar
 * a atualização da senha ou cancelar a operação.
 * 
 * @author Raphael Vilete
 */

public class NovaSenha extends JFrame {

    // Componentes UI
    JLabel novaSenha;
    JTextField campoSenha;
    public JButton mudarSenha;
    JButton cancelar;

    // Construtor da classe
    public NovaSenha() {

        // Configurações do frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Nova senha");

        // Inicializando os componentes UI
        novaSenha = new JLabel("Nova senha");
        novaSenha.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        novaSenha.setBounds(40, 90, 420, 55);

        campoSenha = new JTextField();
        campoSenha.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoSenha.setBounds(40, 140, 420, 55);

        mudarSenha = new JButton("Atualizar senha");
        mudarSenha.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        mudarSenha.setForeground(Color.white);
        mudarSenha.setBackground(new Color(0X27b8cc));
        mudarSenha.setFocusable(false);
        mudarSenha.setBounds(100, 240, 300, 60);
        mudarSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mudarSenha.setBorderPainted(false);

        cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
        cancelar.setForeground(new Color(0x007ACC));
        cancelar.setFocusable(false);
        cancelar.setBounds(100, 310, 300, 80);
        cancelar.setContentAreaFilled(false);
        cancelar.setBorderPainted(false);
        cancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Adicionando ao frame, os componentes criados
        add(novaSenha);
        add(campoSenha);
        add(mudarSenha);
        add(cancelar);
    }

    public String getcampoSenha() {
        return campoSenha.getText();
    }
}