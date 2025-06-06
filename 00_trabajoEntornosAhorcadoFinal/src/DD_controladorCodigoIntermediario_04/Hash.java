package DD_controladorCodigoIntermediario_04;

// Importa la clase para usar codificación UTF-8 en las conversiones de texto a bytes

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    // Declara un método estático que recibe texto y devuelve su hash SHA-256 en hexadecimal
    public static String sha256(String texto) {
        try {
            // Pide al sistema un generador de hash que use SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");       // Crea un objeto MessageDigest con SHA-256
            // Convierte el texto a bytes con codificación UTF-8 y calcula su hash (array de bytes)
            byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));  // Convierte el texto a bytes UTF-8 y genera el hash
            // Prepara una variable para construir el resultado en formato hexadecimal
            StringBuilder hexString = new StringBuilder();                     // Crea un StringBuilder para el hash en hexadecimal
            // Recorre cada byte del hash para convertirlo en una representación hexadecimal
            for (int i = 0; i < hashBytes.length; i++) {                       // Recorre cada byte del hash generado
                // Convierte el byte en un número positivo y luego a su valor en hexadecimal
                String hex = Integer.toHexString(0xff & hashBytes[i]);         // Convierte byte a entero sin signo y luego a hexadecimal
                // Si el número hexadecimal es solo un dígito, añade un '0' delante para que siempre tenga dos dígitos
                if (hex.length() == 1)
                    hexString.append('0');                  // Si el hex es un dígito, añade un '0' delante para completar dos dígitos
                // Añade el valor hexadecimal al resultado final
                hexString.append(hex);                                          // Añade el valor hexadecimal al StringBuilder
            }
            // Devuelve el hash completo ya convertido a una cadena hexadecimal
            return hexString.toString();                                       // Devuelve el hash completo en formato hexadecimal
        } catch (
                NoSuchAlgorithmException e) {                                // Si el algoritmo no está disponible, maneja el error
            // Muestra el error en la consola para saber qué pasó
            System.err.println("Error generando hash SHA-256: " + e.getMessage()); // Imprime el error en consola
            // Retorna null para indicar que no se pudo generar el hash
            return null;                                                      // Devuelve null en caso de error
        }
    }
}