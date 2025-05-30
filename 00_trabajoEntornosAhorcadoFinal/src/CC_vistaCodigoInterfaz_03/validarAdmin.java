package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.sql.*;
import java.io.IOException;
import java.util.logging.*;

public class validarAdmin {

    private static final String URL_BBDD = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String USUARIO_BBDD = "tu_usuario";
    private static final String CLAVE_BBDD = "tu_contraseña";

    private static final Logger LOGGER = Logger.getLogger(validarAdmin.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);
            FileHandler fh = new FileHandler("validarAdmin.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Integer validarAdmin(String correo, String contraseña) {
        String sql = "SELECT nivel FROM Administradores WHERE correo = ? AND contraseña = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            mostrarError("Driver MySQL no encontrado: " + e.getMessage());
            LOGGER.severe("Driver MySQL no encontrado: " + e.getMessage());
            return null;
        }

        try (Connection conn = DriverManager.getConnection(URL_BBDD, USUARIO_BBDD, CLAVE_BBDD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            LOGGER.info("Conexión a base de datos establecida.");

            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int nivel = rs.getInt("nivel");
                    LOGGER.info("Usuario '" + correo + "' autenticado correctamente con nivel " + nivel);
                    return nivel;
                } else {
                    LOGGER.warning("Intento de login fallido para usuario: " + correo);
                    return null;
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al validar administrador: " + e.getMessage());
            LOGGER.severe("Error al validar administrador: " + e.getMessage());
            return null;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean loginAdmin(String correo, String contraseña) {
        Integer nivelAdmin = validarAdmin(correo, contraseña);
        if (nivelAdmin != null) {
            LOGGER.info("Login exitoso para usuario: " + correo);
            SwingUtilities.invokeLater(() -> {
                VentanaAdministrador ventanaAdmin = new VentanaAdministrador(nivelAdmin);
                ventanaAdmin.setVisible(true);
            });
            return true;
        } else {
            mostrarError("Correo o contraseña incorrectos.");
            LOGGER.warning("Login fallido para usuario: " + correo);
            return false;
        }
    }
}
