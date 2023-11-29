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

import emailApi.sender.EmailSender;

/**
 * Classe de interface gráfica para inserir um código de confirmação.
 * 
 * Esta classe permite que o usuário insira o código de confirmação enviado ao
 * seu e-mail.
 * 
 * O código é utilizado para verificar a autenticidade do e-mail do usuário
 * durante o processo de cadastro.
 * 
 * @author Raphael Vilete
 */

public class InserirCodigo extends JFrame {

    // Componentes UI
    JLabel codigoEmail;
    JTextField campoCodigo;
    public JButton confirmarCodigo;
    JButton cancelar;

    private EmailSender emailSender = new EmailSender();

    // Construtor da classe
    public InserirCodigo() {

        // Configurações do frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setTitle("Confirme seu e-mail");

        // Inicializando os componentes UI
        codigoEmail = new JLabel("Insira o código enviado ao seu e-mail");
        codigoEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        codigoEmail.setBounds(40, 90, 420, 55);

        campoCodigo = new JTextField();
        campoCodigo.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
        campoCodigo.setBounds(40, 140, 420, 55);

        confirmarCodigo = new JButton("Confirmar");
        confirmarCodigo.setFont(new Font("Liberation Sans", Font.PLAIN, 30));
        confirmarCodigo.setForeground(Color.white);
        confirmarCodigo.setBackground(new Color(0X27b8cc));
        confirmarCodigo.setFocusable(false);
        confirmarCodigo.setBounds(100, 240, 300, 60);
        confirmarCodigo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmarCodigo.setBorderPainted(false);

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
        add(codigoEmail);
        add(campoCodigo);
        add(confirmarCodigo);
        add(cancelar);
    }

    public String getcampoCodigo() {
        return campoCodigo.getText();
    }
    
    // Primeiro passo para o envio do e-mail ao usuário
    public boolean callEmailSender(String emailTo, String codigo) {
        return emailSender.sendEmail(emailTo, codigo);
    }
}