// ======= Paquete e imports =======
package BB_modeloBBDD_02;

import java.io.File;                       // Importa para manejar archivos
import java.io.IOException;                // Importa para manejo de excepciones IO
import java.sql.Connection;                // Importa para conexiones a base de datos
import java.sql.DriverManager;             // Importa para gestionar drivers JDBC
import java.sql.SQLException;              // Importa para manejo de excepciones SQL
import java.util.logging.*;                // Importa para logging de eventos y errores

// ======= Declaración de la clase =======
public class ConexionBaseDatos {          // Clase para gestionar la conexión a la BBDD

    // ======= Variables estáticas y Logger =======
    private static final Logger LOGGER = Logger.getLogger(ConexionBaseDatos.class.getName());  // Logger para esta clase

    private static final String URL_BASE_DATOS =                                         // URL para conexión JDBC a MySQL
            "jdbc:mysql://localhost:3306/AhorcadoAndres?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USUARIO_BASE_DATOS;        // Usuario base de datos (inicializado en bloque estático)
    private static final String CLAVE_BASE_DATOS;          // Contraseña base de datos (inicializado en bloque estático)

    // ======= Bloque estático: Inicialización de variables y configuración del logging =======
    static {                                               // Bloque estático para inicializar constantes y logging

        String usuarioBD = System.getenv("DB_USUARIO");  // Intenta obtener usuario desde variable entorno
        if (usuarioBD == null) usuarioBD = "root";       // Si no existe, usa "root" por defecto
        USUARIO_BASE_DATOS = usuarioBD;                   // Asigna usuario obtenido o por defecto

        String claveBD = System.getenv("DB_CLAVE");      // Intenta obtener clave desde variable entorno
        if (claveBD == null) claveBD = "abcd1234";       // Si no existe, usa clave por defecto
        CLAVE_BASE_DATOS = claveBD;                       // Asigna clave obtenida o por defecto

        try {
            LogManager.getLogManager().reset();          // Limpia configuración previa de LogManager

            LOGGER.setLevel(Level.ALL);                    // Configura logger para capturar todos los niveles

            String os = System.getProperty("os.name").toLowerCase();   // Detecta sistema operativo

            String rutaLog;                                // Variable para ruta archivo log

            if (os.contains("win")) {                      // Si sistema operativo es Windows
                rutaLog = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\LOGS\\ConexionBaseDatos.log";   // Ruta Windows
            } else {                                       // Si no es Windows
                rutaLog = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/LOGS/ConexionBaseDatos.log"; // Ruta Linux/Unix
            }

            File archivoLog = new File(rutaLog);           // Crea archivo log en la ruta asignada
            File carpetaLogs = archivoLog.getParentFile(); // Obtiene carpeta padre (directorio)

            if (!carpetaLogs.exists()) {                    // Si carpeta no existe
                if (!carpetaLogs.mkdirs()) {                 // Intenta crear carpeta y padres
                    System.err.println("No se pudo crear la carpeta LOGS.");  // Muestra error si falla creación
                }
            }

            FileHandler fh = new FileHandler(rutaLog, 1024 * 1024, 3, true);  // FileHandler con rotación 1MB, 3 archivos, append
            fh.setEncoding("UTF-8");                     // Define codificación UTF-8 para logs
            fh.setFormatter(new SimpleFormatter());      // Formato simple para logs
            LOGGER.addHandler(fh);                        // Añade handler para archivo al logger

            ConsoleHandler ch = new ConsoleHandler();    // Crea handler para consola
            ch.setLevel(Level.INFO);                      // Muestra solo logs nivel INFO o superior en consola
            ch.setFormatter(new SimpleFormatter());      // Formato simple para consola
            LOGGER.addHandler(ch);                        // Añade handler consola al logger

        } catch (IOException e) {                         // Captura errores al configurar logging
            System.err.println("No se pudo inicializar el archivo de logs de conexión: " + e.getMessage());  // Muestra error por consola
        }
    }

    // ======= Método público: Obtiene la conexión a la base de datos =======
    /**
     * Obtiene una conexión activa a la base de datos MySQL
     * @return Connection activa a la base de datos
     * @throws SQLException si ocurre error en conexión
     */
    public static Connection getConexion() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Carga driver JDBC MySQL
            LOGGER.info("Driver JDBC MySQL cargado correctamente.");  // Log éxito carga driver
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Driver JDBC MySQL no encontrado: " + e.getMessage());  // Log error driver no encontrado
            throw new SQLException("No se pudo cargar el driver JDBC de MySQL", e); // Lanza SQLException
        }

        try {
            LOGGER.info("Intentando conectar a la base de datos...");   // Log intento conexión
            Connection conn = DriverManager.getConnection(URL_BASE_DATOS, USUARIO_BASE_DATOS, CLAVE_BASE_DATOS);  // Establece conexión
            LOGGER.info("Conexión establecida con éxito.");              // Log éxito conexión
            return conn;                                                 // Retorna conexión establecida
        } catch (SQLException e) {
            LOGGER.severe("Error al conectar a la base de datos: " + e.getMessage());  // Log error conexión
            throw e;                                                     // Propaga SQLException
        }
    }
}
