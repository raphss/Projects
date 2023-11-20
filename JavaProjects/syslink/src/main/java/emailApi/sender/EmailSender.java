package emailApi.sender;

import java.util.Properties;
import emailApi.properties.EmailProperties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Classe para enviar e-mails de confirmação com um código de autenticação.
 * 
 * Esta classe é responsável por enviar e-mails contendo um código de confirmação
 * para os usuários como parte do processo de autenticação.
 * 
 * @author Raphael Vilete
 */

public class EmailSender {

    private final String username = "syslinktestes@gmail.com";
    private final String password = "souz tvup jclr gjnq";

    private Properties properties = EmailProperties.getProperties();

    private Session session = Session.getInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    /**
     * Envia um e-mail de confirmação com um código para o destinatário especificado.
     * 
     * @param emailTo Endereço de e-mail do destinatário.
     * @param codigo  Código de confirmação a ser enviado no e-mail.
     */
    
    public boolean sendEmail(String emailTo, String codigo) {

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("syslinktestes@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Syslink - Código de confirmação");
            message.setText("O código de confirmação é: " + codigo);

            Transport.send(message);
            System.out.println("E-mail enviado com sucesso!");

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Falha ao enviar o e-mail. Verifique sua conexão com a internet");
            return false;
        }
    }
}