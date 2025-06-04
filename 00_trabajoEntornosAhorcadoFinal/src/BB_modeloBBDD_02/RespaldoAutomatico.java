package BB_modeloBBDD_02; // === PAQUETE BASE DE DATOS ===

import java.io.File; // Importa clase para manejar archivos y carpetas
import java.io.IOException; // Importa excepción para errores de entrada/salida
import java.text.SimpleDateFormat; // Importa clase para formatear fechas
import java.util.Date; // Importa clase para manejar fechas actuales
import java.util.logging.*; // Importa librería para logging (registro de eventos)

public class RespaldoAutomatico extends Thread { // Clase que extiende Thread para ejecutar respaldo en hilo separado

    // === BLOQUE 1: CONSTANTES Y LOGGER ===
    private static final Logger LOGGER = Logger.getLogger(RespaldoAutomatico.class.getName()); // Logger específico para esta clase

    private static final String DB_NAME = "AhorcadoAndres"; // Nombre de la base de datos a respaldar
    private static final long INTERVAL = 30 * 60 * 1000; // Intervalo de respaldo en milisegundos (30 minutos)

    private static final String MY_CNF_PATH; // Ruta al archivo de configuración MySQL
    private static final String BACKUP_FOLDER; // Ruta a la carpeta de backups
    private static final String LOG_FOLDER; // Ruta a la carpeta de logs

    // === BLOQUE 2: CONFIGURACIÓN ESTÁTICA INICIAL ===
    static { // Bloque estático para inicializar rutas y configurar logging al cargar la clase
        String os = System.getProperty("os.name").toLowerCase(); // Obtener nombre del sistema operativo en minúsculas

        // Rutas dependiendo del sistema operativo
        if (os.contains("win")) { // Si el SO es Windows
            MY_CNF_PATH = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\00_trabajoEntornosAhorcadoFinal\\src\\.my.cnf";
            BACKUP_FOLDER = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\Backups\\";
            LOG_FOLDER = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\LOGS\\";
        } else { // Si es otro SO (Linux, Mac, etc.)
            MY_CNF_PATH = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/00_trabajoEntornosAhorcadoFinal/src/.my.cnf";
            BACKUP_FOLDER = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/Backups/";
            LOG_FOLDER = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/LOGS/";
        }

        try { // Configurar sistema de logging
            LogManager.getLogManager().reset(); // Resetear configuración previa del logging
            LOGGER.setLevel(Level.ALL); // Registrar todos los niveles de logs

            File carpetaLogs = new File(LOG_FOLDER); // Objeto carpeta para logs
            if (!carpetaLogs.exists() && !carpetaLogs.mkdirs()) { // Crear carpeta si no existe
                System.err.println("No se pudo crear la carpeta LOGS en la ruta: " + LOG_FOLDER);
            }

            File archivoLog = new File(carpetaLogs, "RespaldoAutomatico.log"); // Archivo de log dentro de carpeta
            FileHandler fh = new FileHandler(archivoLog.getAbsolutePath(), 1024 * 1024, 3, true); // Handler de archivo con rotación
            fh.setEncoding("UTF-8"); // Codificación UTF-8 para archivo
            fh.setFormatter(new SimpleFormatter()); // Formateador simple para logs
            LOGGER.addHandler(fh); // Añadir handler archivo al logger

            ConsoleHandler ch = new ConsoleHandler(); // Handler para consola
            ch.setLevel(Level.INFO); // Nivel INFO para consola
            ch.setFormatter(new SimpleFormatter()); // Formateador simple para consola
            LOGGER.addHandler(ch); // Añadir handler consola al logger

        } catch (IOException e) { // Si falla configuración de logs
            System.err.println("No se pudo inicializar el archivo de log: " + e.getMessage());
        }
    }

    // === BLOQUE 3: MÉTODO PÚBLICO PARA INICIAR RESPALDO ===
    public static RespaldoAutomatico iniciarRespaldo() { // Método estático para crear e iniciar hilo respaldo
        RespaldoAutomatico respaldo = new RespaldoAutomatico(); // Crear instancia del hilo
        respaldo.start(); // Iniciar ejecución del hilo
        LOGGER.info("Hilo de respaldo automático iniciado."); // Loggear inicio del hilo
        return respaldo; // Retornar referencia al hilo
    }

    public void detener() { // Método para solicitar detención del hilo
        LOGGER.info("Solicitud de detener el hilo de respaldo."); // Loggear solicitud
        this.interrupt(); // Interrumpir hilo para detenerlo
    }

    // === BLOQUE 4: MÉTODO RUN DEL HILO ===
    @Override
    public void run() { // Código que se ejecuta en el hilo
        LOGGER.info("Hilo de respaldo automático iniciado."); // Loggear inicio de hilo
        while (!isInterrupted()) { // Ejecutar mientras no se interrumpa hilo
            realizarRespaldo(); // Ejecutar respaldo
            try {
                Thread.sleep(INTERVAL); // Esperar intervalo definido antes de siguiente respaldo
            } catch (InterruptedException e) { // Si se interrumpe el sleep
                LOGGER.warning("Hilo interrumpido: " + e.getMessage()); // Loggear advertencia
                Thread.currentThread().interrupt(); // Volver a interrumpir hilo
            }
        }
        LOGGER.info("Hilo de respaldo finalizado."); // Loggear fin de hilo
    }

    // === BLOQUE 5: REALIZAR RESPALDO ===
    private void realizarRespaldo() { // Método para hacer el respaldo de la base de datos
        File folder = new File(BACKUP_FOLDER); // Carpeta destino de backup
        if (!folder.exists() && !folder.mkdirs()) { // Crear carpeta si no existe
            LOGGER.severe("No se pudo crear la carpeta de respaldos: " + BACKUP_FOLDER);
            return; // Salir si no se puede crear carpeta
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // Formatear fecha para nombre archivo
        String backupFile = BACKUP_FOLDER + "backup_" + timeStamp + ".sql"; // Ruta y nombre completo del archivo backup

        String mysqldump = System.getProperty("os.name").toLowerCase().contains("win") // Comando mysqldump según SO
                ? "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe"
                : "mysqldump";

        try {
            if (!comandoDisponible(mysqldump)) { // Verificar que el comando mysqldump está disponible
                LOGGER.severe("El comando mysqldump no está disponible en el sistema.");
                return; // Salir si no está disponible
            }
        } catch (IOException | InterruptedException e) { // Captura error verificación comando
            LOGGER.severe("Error al verificar mysqldump: " + e.getMessage());
            return; // Salir si ocurre error
        }

        String[] command = {mysqldump, "--defaults-extra-file=" + MY_CNF_PATH, DB_NAME}; // Comando para ejecutar mysqldump con archivo de configuración y DB

        try {
            ProcessBuilder pb = new ProcessBuilder(command); // Crear proceso con comando
            pb.redirectOutput(new File(backupFile)); // Redirigir salida al archivo backup
            pb.redirectError(ProcessBuilder.Redirect.INHERIT); // Redirigir errores a consola
            Process process = pb.start(); // Iniciar proceso
            int exitCode = process.waitFor(); // Esperar finalización y obtener código salida

            if (exitCode == 0) { // Si exitCode es 0, éxito
                LOGGER.info("Respaldo exitoso: " + backupFile);
            } else { // Si no, error en respaldo
                LOGGER.severe("Error en respaldo (código " + exitCode + ").");
            }

        } catch (IOException e) { // Captura error de entrada/salida
            LOGGER.severe("IOException al ejecutar mysqldump: " + e.getMessage());
        } catch (InterruptedException e) { // Captura interrupción del proceso
            LOGGER.warning("Respaldo interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt(); // Reinterrumpir hilo
        }
    }

    // === BLOQUE 6: VERIFICAR DISPONIBILIDAD DE COMANDO ===
    private boolean comandoDisponible(String cmd) throws IOException, InterruptedException { // Método que verifica si un comando existe en el sistema
        ProcessBuilder pb = new ProcessBuilder(System.getProperty("os.name").toLowerCase().contains("win") ? "where" : "which", cmd);
        Process p = pb.start(); // Iniciar proceso para buscar comando
        return p.waitFor() == 0; // Retorna true si comando existe (código 0)
    }
}
