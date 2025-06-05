package BB_modeloBBDD_02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDPalabras {

    private static final Logger LOGGER = Logger.getLogger(BDPalabras.class.getName());

    /**
     * Obtiene una palabra o frase aleatoria de la tabla palabrasFrases.
     *
     * @return palabra/frase aleatoria o null si no se encuentra ninguna.
     */
    public static String obtenerPalabraRandom() {
        String palabra = null;
        String sql = "SELECT palabra FROM palabrasFrases ORDER BY RAND() LIMIT 1";

        try (Connection conn = ConexionBaseDatos.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                palabra = rs.getString("palabra");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener palabra aleatoria: " + e.getMessage(), e);
        }
        return palabra;
    }

    /**
     * Obtiene todas las palabras o frases de la tabla palabrasFrases.
     *
     * @return lista con todas las palabras/frases.
     */
    public static List<String> obtenerTodasPalabras() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT palabra FROM palabrasFrases";

        try (Connection conn = ConexionBaseDatos.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("palabra"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las palabras: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Obtiene una palabra aleatoria filtrada por dificultad e idioma.
     *
     * @param dificultad puede ser "facil", "medio", "dificil"
     * @param idioma     puede ser "es" o "en"
     * @return palabra/frase aleatoria o null si no encuentra ninguna.
     */
    public static String obtenerPalabraPorDificultadEIdioma(String dificultad, String idioma) {
        String palabra = null;
        String sql = "SELECT palabra FROM palabrasFrases WHERE dificultad = ? AND idioma = ? ORDER BY RAND() LIMIT 1";

        try (Connection conn = ConexionBaseDatos.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dificultad);
            ps.setString(2, idioma);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    palabra = rs.getString("palabra");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener palabra por dificultad e idioma: " + e.getMessage(), e);
        }
        return palabra;
    }
}
