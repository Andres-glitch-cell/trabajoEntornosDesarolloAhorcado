package BB_modeloBBDD_02;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.*;

public class ConexionBaseDatos {

    private static final Logger LOGGER = Logger.getLogger(ConexionBaseDatos.class.getName());

    private static final String URL_BASE_DATOS =
            "jdbc:mysql://localhost:3306/AhorcadoAndres?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USUARIO_BASE_DATOS;
    private static final String CLAVE_BASE_DATOS;

    static {
        USUARIO_BASE_DATOS = System.getenv().getOrDefault("DB_USUARIO", "root");
        CLAVE_BASE_DATOS = System.getenv().getOrDefault("DB_CLAVE", "abcd1234");

        try {
            LOGGER.setLevel(Level.ALL);

            String os = System.getProperty("os.name").toLowerCase();
            String rutaLog = os.contains("win")
                    ? "D:\\ruta\\a\\tu\\LOGS\\ConexionBaseDatos.log"
                    : "/ruta/a/tu/LOGS/ConexionBaseDatos.log";

            File archivoLog = new File(rutaLog);
            if (!archivoLog.getParentFile().exists()) archivoLog.getParentFile().mkdirs();

            FileHandler fh = new FileHandler(rutaLog, 1024 * 1024, 3, true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            ch.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(ch);

        } catch (IOException e) {
            System.err.println("No se pudo inicializar el archivo de logs: " + e.getMessage());
        }
    }

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.fine("Driver JDBC MySQL cargado.");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Driver JDBC MySQL no encontrado: " + e.getMessage());
            throw new SQLException("No se pudo cargar el driver JDBC de MySQL", e);
        }

        LOGGER.info("Intentando conectar a la base de datos...");
        return DriverManager.getConnection(URL_BASE_DATOS, USUARIO_BASE_DATOS, CLAVE_BASE_DATOS);
    }

    // --- MÉTODOS CRUD USUARIO ---
    public static boolean agregarUsuario(String nombreUsuario) {
        return ejecutarUpdate("INSERT INTO Usuario (nombre) VALUES (?)", nombreUsuario);
    }

    public static boolean eliminarUsuario(String nombreUsuario) {
        return ejecutarUpdate("DELETE FROM Usuario WHERE nombre = ?", nombreUsuario);
    }

    // --- MÉTODOS CRUD PALABRA ---
    public static boolean agregarPalabra(String palabra) {
        return ejecutarUpdate("INSERT INTO PalabrasFrases (palabra) VALUES (?)", palabra);
    }

    public static boolean eliminarPalabra(String palabra) {
        return ejecutarUpdate("DELETE FROM PalabrasFrases WHERE palabra = ?", palabra);
    }

    // --- MÉTODO COMÚN PARA INSERT/DELETE ---
    private static boolean ejecutarUpdate(String sql, String valor) {
        try (Connection conn = getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, valor);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                LOGGER.info("Operación realizada correctamente con valor: " + valor);
                return true;
            } else {
                LOGGER.warning("No se realizó ninguna operación para: " + valor);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.severe("Error en la operación con valor \"" + valor + "\": " + e.getMessage());
            return false;
        }
    }
}