// === BLOQUE 1: DECLARACIÓN DEL PAQUETE E IMPORTACIONES ===
package BB_modeloBBDD_02;                                      // Define el paquete donde reside la clase RespaldoAutomatico

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

// === BLOQUE 2: DEFINICIÓN DE LA CLASE Y CONSTANTES ===
public class RespaldoAutomatico extends Thread {                // Declara la clase RespaldoAutomatico que hereda de Thread
    private static final Logger LOGGER = Logger.getLogger(RespaldoAutomatico.class.getName()); // Crea un Logger para la clase
    private static final String DB_NAME = "AhorcadoAndres";    // Define el nombre de la base de datos a respaldar
    private static final long INTERVAL = 30 * 60 * 1000;       // Define intervalo de respaldo (30 minutos en milisegundos)
    private static final String MY_CNF_PATH;                   // Declara la ruta del archivo de configuración MySQL
    private static final String BACKUP_FOLDER;                 // Declara la carpeta para guardar los respaldos
    private static final String LOG_FOLDER;                    // Declara la carpeta para los archivos de log
    private static final String CMD_MYSQLDUMP;                 // Declara la ruta o comando para mysqldump

    // === BLOQUE 3: BLOQUE ESTÁTICO PARA CONFIGURACIÓN INICIAL ===
    static {                                                   // Bloque estático que se ejecuta al cargar la clase
        String os = System.getProperty("os.name").toLowerCase(); // Obtiene el nombre del sistema operativo
        if (os.contains("win")) {                              // Verifica si el sistema operativo es Windows
            MY_CNF_PATH = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\00_trabajoEntornosAhorcadoFinal\\src\\.my.cnf"; // Ruta del archivo .my.cnf en Windows
            BACKUP_FOLDER = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\Backups"; // Carpeta de respaldos en Windows
            LOG_FOLDER = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\LOGS"; // Carpeta de logs en Windows
            CMD_MYSQLDUMP = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe"; // Ruta de mysqldump en Windows
        } else {                                               // Caso para sistemas no Windows (Linux, etc.)
            MY_CNF_PATH = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/00_trabajoEntornosAhorcadoFinal/src/.my.cnf"; // Ruta del archivo .my.cnf en Linux
            BACKUP_FOLDER = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/Backups/"; // Carpeta de respaldos en Linux
            LOG_FOLDER = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/LOGS/"; // Carpeta de logs en Linux
            CMD_MYSQLDUMP = "mysqldump";                       // Comando mysqldump en Linux (en PATH)
        }
        try {                                                  // Inicia un bloque try para configurar el logging
            File logDir = new File(LOG_FOLDER);                // Crea un objeto File para la carpeta de logs
            if (!logDir.exists()) logDir.mkdirs();             // Crea la carpeta de logs si no existe
            FileHandler fileHandler = new FileHandler(new File(logDir, "RespaldoAutomatico.log").getAbsolutePath(), 1024 * 1024, 3, true); // Crea un manejador para logs con rotación
            fileHandler.setEncoding("UTF-8");                  // Establece codificación UTF-8 para el archivo de log
            fileHandler.setFormatter(new SimpleFormatter());   // Aplica un formato simple al archivo de log
            LOGGER.addHandler(fileHandler);                    // Agrega el manejador de archivo al Logger
            ConsoleHandler consoleHandler = new ConsoleHandler(); // Crea un manejador para logs en consola
            consoleHandler.setLevel(Level.INFO);               // Establece nivel INFO para logs en consola
            consoleHandler.setFormatter(new ColorFormatter()); // Aplica formateador personalizado con colores
            LOGGER.addHandler(consoleHandler);                 // Agrega el manejador de consola al Logger
        } catch (IOException e) {                              // Captura excepciones de entrada/salida
            System.err.println("Error configurando logs de respaldo: " + e.getMessage()); // Imprime error en consola
        }
    }

    // === BLOQUE 4: MÉTODO PARA INICIAR EL RESPALDO ===
    public static RespaldoAutomatico iniciarRespaldo() {       // Método estático para iniciar el hilo de respaldo
        RespaldoAutomatico respaldo = new RespaldoAutomatico(); // Crea una nueva instancia de RespaldoAutomatico
        respaldo.start();                                      // Inicia el hilo de respaldo
        LOGGER.info("Hilo de respaldo automático iniciado.");  // Registra mensaje de inicio del hilo
        return respaldo;                                       // Retorna la instancia para control externo
    }

    // === BLOQUE 5: MÉTODO PARA DETENER EL HILO ===
    public void detener() {                                    // Método para solicitar la detención del hilo
        LOGGER.info("Solicitud de detener el hilo de respaldo."); // Registra mensaje de solicitud de detención
        this.interrupt();                                      // Interrumpe el hilo
    }

    // === BLOQUE 6: LÓGICA DEL HILO DE RESPALDO ===
    @Override
    public void run() {                                        // Sobrescribe el método run del hilo
        while (!isInterrupted()) {                             // Bucle que continúa mientras el hilo no esté interrumpido
            realizarRespaldo();                                // Realiza un respaldo de la base de datos
            try {                                              // Inicia un bloque try para manejar interrupciones
                Thread.sleep(INTERVAL);                        // Pausa el hilo por el intervalo definido (30 min)
            } catch (InterruptedException e) {                 // Captura interrupciones durante la espera
                LOGGER.warning("Hilo interrumpido durante espera."); // Registra advertencia de interrupción
                Thread.currentThread().interrupt();            // Restaura el estado de interrupción
            }
        }
        LOGGER.info("Hilo de respaldo finalizado.");           // Registra mensaje cuando el hilo termina
    }

    // === BLOQUE 7: MÉTODO PARA REALIZAR EL RESPALDO ===
    private void realizarRespaldo() {                          // Método para ejecutar el proceso de respaldo
        File folder = new File(BACKUP_FOLDER);                 // Crea un objeto File para la carpeta de respaldos
        if (!folder.exists() && !folder.mkdirs()) {            // Verifica y crea la carpeta de respaldos si no existe
            LOGGER.severe("No se pudo crear la carpeta de respaldos: " + BACKUP_FOLDER); // Registra error si falla
            return;                                            // Sale del método si no se puede crear la carpeta
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // Crea un timestamp para el archivo
        String backupFile = BACKUP_FOLDER + "backup_" + timeStamp + ".sql"; // Define la ruta del archivo de respaldo
        try {                                                  // Inicia un bloque try para ejecutar el comando
            if (!comandoDisponible(CMD_MYSQLDUMP)) {           // Verifica si mysqldump está disponible
                LOGGER.severe("El comando mysqldump no está disponible en el sistema."); // Registra error si no está
                return;                                        // Sale del método si mysqldump no está disponible
            }
        } catch (IOException | InterruptedException e) {       // Captura excepciones al verificar mysqldump
            LOGGER.severe("Error verificando mysqldump: " + e.getMessage()); // Registra error con detalles
            return;                                            // Sale del método si falla la verificación
        }
        String[] command = {CMD_MYSQLDUMP, "--defaults-extra-file=" + MY_CNF_PATH, DB_NAME}; // Define el comando mysqldump
        try {                                                  // Inicia un bloque try para ejecutar el respaldo
            ProcessBuilder pb = new ProcessBuilder(command);   // Crea un ProcessBuilder con el comando
            pb.redirectOutput(new File(backupFile));           // Redirige la salida al archivo de respaldo
            pb.redirectError(ProcessBuilder.Redirect.INHERIT); // Redirige errores a la salida estándar
            Process process = pb.start();                      // Inicia el proceso de respaldo
            int exitCode = process.waitFor();                  // Espera a que el proceso termine y obtiene el código de salida
            if (exitCode == 0) {                               // Verifica si el respaldo fue exitoso
                LOGGER.info("Respaldo exitoso: " + backupFile); // Registra mensaje de éxito con la ruta del archivo
            } else {                                           // Caso en que el respaldo falla
                LOGGER.severe("Error en respaldo (código " + exitCode + ")."); // Registra error con el código de salida
            }
        } catch (IOException | InterruptedException e) {       // Captura excepciones durante el respaldo
            LOGGER.log(Level.SEVERE, "Error al ejecutar respaldo", e); // Registra error con detalles
            Thread.currentThread().interrupt();                // Restaura el estado de interrupción si fue interrumpido
        }
    }

    // === BLOQUE 8: VERIFICACIÓN DE COMANDO mysqldump ===
    private boolean comandoDisponible(String cmd) throws IOException, InterruptedException { // Verifica si un comando está disponible
        String cmdName = new File(cmd).getName();              // Obtiene el nombre del comando (sin ruta)
        ProcessBuilder pb = new ProcessBuilder(                // Crea un ProcessBuilder para verificar el comando
                System.getProperty("os.name").toLowerCase().contains("win") ? "where" : "which", cmdName); // Usa where (Windows) o which (Linux)
        Process p = pb.start();                                // Inicia el proceso de verificación
        return p.waitFor() == 0;                               // Retorna true si el comando existe (código de salida 0)
    }

    // === BLOQUE 9: FORMATEADOR DE LOGS CON COLORES ===
    private static class ColorFormatter extends Formatter {    // Clase interna para formatear logs con colores
        private static final String ANSI_RESET = "\u001B[0m";  // Código ANSI para resetear el color
        private static final String ANSI_GREEN = "\u001B[32m"; // Código ANSI para color verde (INFO)
        private static final String ANSI_RED = "\u001B[31m";   // Código ANSI para color rojo (SEVERE)
        @Override
        public String format(LogRecord record) {               // Sobrescribe el método para formatear logs
            String message = formatMessage(record);            // Obtiene el mensaje formateado del registro
            if (record.getLevel() == Level.INFO) {             // Verifica si el nivel del log es INFO
                return ANSI_GREEN + "✔ " + message + ANSI_RESET + "\n"; // Formatea mensaje INFO con color verde y check
            } else if (record.getLevel() == Level.SEVERE) {    // Verifica si el nivel del log es SEVERE
                return ANSI_RED + "✖ " + message + ANSI_RESET + "\n"; // Formatea mensaje SEVERE con color rojo y cruz
            }
            return message + "\n";                             // Retorna mensaje sin formato para otros niveles
        }
    }
}