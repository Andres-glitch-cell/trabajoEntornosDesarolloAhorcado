// Declaración del paquete donde está esta clase
package BB_modeloBBDD_02;

// Importa clases para manejo de archivos

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;

// Declaración de la clase para manejar la conexión a base de datos
public class ConexionBaseDatos {

    // -----------------------------
    // CONSTANTES Y LOGGER
    // -----------------------------

    // Logger para registrar mensajes relacionados con esta clase
    private static final Logger LOGGER = Logger.getLogger(ConexionBaseDatos.class.getName());

    // Cadena de conexión JDBC a MySQL con parámetros para configuración
    private static final String URL_BASE_DATOS = "jdbc:mysql://localhost:3306/AhorcadoAndres?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // Variables para usuario y contraseña, serán inicializadas en bloque estático
    private static final String USUARIO_BASE_DATOS;
    private static final String CLAVE_BASE_DATOS;

    // -----------------------------
    // BLOQUE ESTÁTICO DE INICIALIZACIÓN
    // -----------------------------

    static {
        // Intenta obtener usuario desde variable de entorno "DB_USUARIO"
        String usuarioBD = System.getenv("DB_USUARIO");
        // Si no está definida, usa "root" como valor por defecto
        if (usuarioBD == null) usuarioBD = "root";
        // Asigna el usuario obtenido o por defecto a la constante
        USUARIO_BASE_DATOS = usuarioBD;

        // Intenta obtener contraseña desde variable de entorno "DB_CLAVE"
        String claveBD = System.getenv("DB_CLAVE");
        // Si no está definida, usa "abcd1234" como valor por defecto
        if (claveBD == null) claveBD = "abcd1234";
        // Asigna la clave obtenida o por defecto a la constante
        CLAVE_BASE_DATOS = claveBD;

        try {
            // Reinicia configuración del LogManager para limpiar handlers previos
            LogManager.getLogManager().reset();

            // Configura el nivel del logger para capturar todos los mensajes
            LOGGER.setLevel(Level.ALL);

            // Obtiene el nombre del sistema operativo actual en minúsculas
            String os = System.getProperty("os.name").toLowerCase();
            String rutaLog;

            // Si el sistema operativo es Windows, define ruta para archivo log en D:
            if (os.contains("win")) {
                rutaLog = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\LOGS\\ConexionBaseDatos.log";
            } else {
                // Si no es Windows, define ruta alternativa (Linux/Unix) para archivo log
                rutaLog = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/LOGS/ConexionBaseDatos.log";
            }

            // Crea un objeto File para el archivo log en la ruta especificada
            File archivoLog = new File(rutaLog);
            // Obtiene la carpeta padre del archivo log
            File carpetaLogs = archivoLog.getParentFile();

            // Si la carpeta de logs no existe
            if (!carpetaLogs.exists()) {
                // Intenta crear la carpeta y todas las carpetas padres necesarias
                if (!carpetaLogs.mkdirs()) {
                    // Si falla, imprime mensaje de error en consola de error
                    System.err.println("No se pudo crear la carpeta LOGS.");
                }
            }

            // Configura un FileHandler para logs con rotación: 1MB por archivo, 3 archivos guardados, modo append
            FileHandler fh = new FileHandler(rutaLog, 1024 * 1024, 3, true);
            // Define codificación UTF-8 para los logs
            fh.setEncoding("UTF-8");
            // Establece formato simple para la salida de logs
            fh.setFormatter(new SimpleFormatter());
            // Añade este handler para guardar logs en archivo al logger
            LOGGER.addHandler(fh);

            // Crea un ConsoleHandler para mostrar logs en la consola
            ConsoleHandler ch = new ConsoleHandler();
            // Establece nivel INFO para que solo se muestren logs de nivel INFO o superior en consola
            ch.setLevel(Level.INFO);
            // Define formato simple para logs en consola
            ch.setFormatter(new SimpleFormatter());
            // Añade el handler de consola al logger
            LOGGER.addHandler(ch);

        } catch (IOException e) {
            // En caso de error al configurar logging en archivos, imprime mensaje en consola de error
            System.err.println("No se pudo inicializar el archivo de logs de conexión: " + e.getMessage());
        }
    }

    // -----------------------------
    // MÉTODO PÚBLICO PARA OBTENER CONEXIÓN
    // -----------------------------

    /**
     * Método para obtener una conexión activa a la base de datos MySQL mediante JDBC.
     *
     * @return Objeto Connection para interactuar con la base de datos.
     * @throws SQLException si ocurre algún error durante la conexión.
     */
    public static Connection getConexion() throws SQLException {
        try {
            // Intenta cargar el driver JDBC de MySQL (clase del driver)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Si carga bien, registra información en log
            LOGGER.info("Driver JDBC MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            // Si no se encuentra el driver, registra error en log con nivel SEVERE
            LOGGER.severe("Driver JDBC MySQL no encontrado: " + e.getMessage());
            // Lanza excepción SQLException indicando que no se pudo cargar el driver
            throw new SQLException("No se pudo cargar el driver JDBC de MySQL", e);
        }

        try {
            // Registra que se intenta conectar a la base de datos
            LOGGER.info("Intentando conectar a la base de datos...");
            // Establece la conexión usando URL, usuario y clave configurados
            Connection conn = DriverManager.getConnection(URL_BASE_DATOS, USUARIO_BASE_DATOS, CLAVE_BASE_DATOS);
            // Si la conexión es exitosa, registra el evento en el log
            LOGGER.info("Conexión establecida con éxito.");
            // Retorna la conexión creada para ser usada por el llamante
            return conn;
        } catch (SQLException e) {
            // Si falla la conexión, registra el error en log con nivel SEVERE
            LOGGER.severe("Error al conectar a la base de datos: " + e.getMessage());
            // Propaga la excepción para que quien llame al método pueda manejarla
            throw e;
        }
    }
}
