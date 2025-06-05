package DD_controladorCodigoIntermediario_04;

import java.sql.*;

public class PalabrasFrasesBBDD {

    private static final String URL = "jdbc:mysql://localhost:3306/AhorcadoAndres";
    private static final String USER = "root";
    private static final String PASS = "abcd1234";

    public static class PalabraFrase {
        public final String contenido;
        public final String significado;

        public PalabraFrase(String contenido, String significado) {
            this.contenido = contenido;
            this.significado = significado;
        }
    }

    public static PalabraFrase obtenerPalabraAleatoria() {
        String sql = "SELECT contenido, significadoPalabra FROM palabrasFrases WHERE activo = 1 ORDER BY RAND() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String contenido = rs.getString("contenido");
                String significado = rs.getString("significadoPalabra");
                return new PalabraFrase(contenido, significado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí podrías manejar errores, loguear, etc.
        }
        return null; // O lanzar excepción según diseño
    }
}
