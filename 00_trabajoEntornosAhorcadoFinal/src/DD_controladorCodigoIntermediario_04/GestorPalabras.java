package DD_controladorCodigoIntermediario_04;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorPalabras {

    private static final Logger LOGGER = Logger.getLogger(GestorPalabras.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/AhorcadoAndres?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "abcd1234";

    private static final List<String> DIFICULTADES = List.of("Fácil", "Media", "Difícil");
    private static final List<String> IDIOMAS = List.of("Español", "Inglés");

    public static PalabraFrase obtenerPalabraAleatoria() {
        String sql = "SELECT contenido AS palabra, IFNULL(significadoPalabra, '') AS significadoPalabra FROM PalabrasFrases WHERE activo = 1 ORDER BY RAND() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String palabra = rs.getString("palabra");
                String significado = rs.getString("significadoPalabra");
                LOGGER.info("Palabra obtenida: '" + palabra + "', significado: '" + significado + "'");
                return new PalabraFrase(palabra, significado);
            } else {
                LOGGER.warning("No se encontró ninguna palabra activa con significado.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener palabra aleatoria con significado", e);
        }
        return null;
    }

    public static String obtenerPalabraRandom() {
        String sql = "SELECT contenido AS palabra FROM PalabrasFrases ORDER BY RAND() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String palabra = rs.getString("palabra");
                LOGGER.info("Palabra random obtenida: '" + palabra + "'");
                return palabra;
            } else {
                LOGGER.warning("No se encontró ninguna palabra en la base de datos.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener palabra aleatoria", e);
        }
        return null;
    }

    public static List<String> obtenerTodasPalabras() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT contenido AS palabra FROM PalabrasFrases";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("palabra"));
            }
            LOGGER.info("Total palabras obtenidas: " + lista.size());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las palabras", e);
        }

        return lista;
    }

    public static PalabraFrase obtenerPalabraPorDificultadEIdioma(String dificultad, String idioma) {
        if (!DIFICULTADES.contains(dificultad)) {
            LOGGER.warning("Dificultad inválida: '" + dificultad + "'. Valores válidos: " + DIFICULTADES);
            return null;
        }
        if (!IDIOMAS.contains(idioma)) {
            LOGGER.warning("Idioma inválido: '" + idioma + "'. Valores válidos: " + IDIOMAS);
            return null;
        }

        String sql = """
                SELECT contenido AS palabra, IFNULL(significadoPalabra, '') AS significadoPalabra
                FROM PalabrasFrases
                WHERE activo = 1 AND dificultad = ? AND idioma = ?
                ORDER BY RAND() LIMIT 1
                """;

        LOGGER.info("Obteniendo palabra por dificultad='" + dificultad + "', idioma='" + idioma + "'");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dificultad);
            ps.setString(2, idioma);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String palabra = rs.getString("palabra");
                    String significado = rs.getString("significadoPalabra");
                    LOGGER.info("Palabra encontrada: " + palabra + ", significado: " + significado);
                    return new PalabraFrase(palabra, significado);
                } else {
                    LOGGER.warning("No se encontró palabra activa con esos filtros.");
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener palabra filtrada por dificultad e idioma", e);
        }

        return null;
    }

    public record PalabraFrase(String palabra, String significado) {
    }
}