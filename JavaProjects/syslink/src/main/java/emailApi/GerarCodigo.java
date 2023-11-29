package emailApi;

import java.util.Random;

/**
 * Classe para gerar códigos de confirmação aleatórios.
 * 
 * Esta classe fornece um método para gerar códigos de confirmação aleatórios,
 * que são
 * usados em processos de confirmação por e-mail, como o envio de códigos de
 * verificação.
 * 
 * @author Raphael Vilete
 */

public class GerarCodigo {

    /**
     * Gera um código de confirmação aleatório.
     * 
     * @return Código de confirmação gerado.
     */

    public String gerarCodigo() {

        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int comprimento = 6;

        Random random = new Random();

        StringBuilder codigo = new StringBuilder(comprimento);

        for (int i = 0; i < comprimento; i++) {
            int index = random.nextInt(caracteres.length());
            char caractere = caracteres.charAt(index);
            codigo.append(caractere);
        }

        return codigo.toString();
    }
}