package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHiddenUtilHash {
    public static String hashSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña: " + e.getMessage(), e);
        }
    }
}
