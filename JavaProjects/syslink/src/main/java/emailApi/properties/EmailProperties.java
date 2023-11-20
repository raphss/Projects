package emailApi.properties;

import java.util.Properties;

/**
 * Classe para configurar as propriedades do servidor de e-mail.
 * 
 * Esta classe fornece as propriedades necessárias para configurar o envio de
 * e-mails por meio do servidor SMTP, como o servidor Gmail.
 * 
 * @author Raphael Vilete
 */

public class EmailProperties {

    /**
     * Retorna um objeto Properties configurado com as propriedades para o servidor
     * de e-mail.
     * 
     * @return Objeto Properties configurado com as propriedades do servidor de
     *         e-mail.
     */

    public static Properties getProperties() {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return properties;
    }
}