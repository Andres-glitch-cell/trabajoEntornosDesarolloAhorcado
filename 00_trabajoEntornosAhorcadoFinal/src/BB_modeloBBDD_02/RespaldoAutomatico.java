package BB_modeloBBDD_02;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class RespaldoAutomatico extends Thread {

    private static final Logger LOGGER = Logger.getLogger(RespaldoAutomatico.class.getName());

    private static final String DB_NAME = "AhorcadoAndres";
    private static final long INTERVAL = 30 * 60 * 1000;

    private static final String MY_CNF_PATH;
    private static final String BACKUP_FOLDER;
    private static final String LOG_FOLDER;

    static {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            MY_CNF_PATH = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\00_trabajoEntornosAhorcadoFinal\\src\\.my.cnf";
            BACKUP_FOLDER = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\Backups\\";
            LOG_FOLDER = "D:\\00ADAW ordenador clase (he hecho cosas en casa)\\trabajoEntornosDesarolloAhorcado\\LOGS\\";
        } else {
            MY_CNF_PATH = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/00_trabajoEntornosAhorcadoFinal/src/.my.cnf";
            BACKUP_FOLDER = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/Backups/";
            LOG_FOLDER = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/LOGS/";
        }

        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);

            File carpetaLogs = new File(LOG_FOLDER);
            if (!carpetaLogs.exists() && !carpetaLogs.mkdirs()) {
                System.err.println("No se pudo crear la carpeta LOGS en la ruta: " + LOG_FOLDER);
            }

            File archivoLog = new File(carpetaLogs, "RespaldoAutomatico.log");
            FileHandler fh = new FileHandler(archivoLog.getAbsolutePath(), 1024 * 1024, 3, true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            ch.setFormatter(new ColorFormatter());  // <-- Aquí el formatter coloreado
            LOGGER.addHandler(ch);

        } catch (IOException e) {
            System.err.println("No se pudo inicializar el archivo de log: " + e.getMessage());
        }
    }

    public static RespaldoAutomatico iniciarRespaldo() {
        RespaldoAutomatico respaldo = new RespaldoAutomatico();
        respaldo.start();
        LOGGER.info("Hilo de respaldo automático iniciado.");
        return respaldo;
    }

    public void detener() {
        LOGGER.info("Solicitud de detener el hilo de respaldo.");
        this.interrupt();
    }

    @Override
    public void run() {
        LOGGER.info("Hilo de respaldo automático iniciado.");

        while (!isInterrupted()) {
            realizarRespaldo();
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                LOGGER.warning("Hilo interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info("Hilo de respaldo finalizado.");
    }

    private void realizarRespaldo() {
        File folder = new File(BACKUP_FOLDER);
        if (!folder.exists() && !folder.mkdirs()) {
            LOGGER.severe("No se pudo crear la carpeta de respaldos: " + BACKUP_FOLDER);
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFile = BACKUP_FOLDER + "backup_" + timeStamp + ".sql";

        String cmdComando = "mysqldump";

        String mysqldump = System.getProperty("os.name").toLowerCase().contains("win")
                ? "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe"
                : "mysqldump";

        try {
            if (!comandoDisponible(cmdComando)) {
                LOGGER.severe("El comando mysqldump no está disponible en el sistema.");
                return;
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.severe("Error al verificar mysqldump: " + e.getMessage());
            return;
        }

        String[] command = {mysqldump, "--defaults-extra-file=" + MY_CNF_PATH, DB_NAME};

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectOutput(new File(backupFile));
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                LOGGER.info("Respaldo exitoso: " + backupFile);
            } else {
                LOGGER.severe("Error en respaldo (código " + exitCode + ").");
            }

        } catch (IOException e) {
            LOGGER.severe("IOException al ejecutar mysqldump: " + e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.warning("Respaldo interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private boolean comandoDisponible(String cmd) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                System.getProperty("os.name").toLowerCase().contains("win") ? "where" : "which",
                cmd
        );
        Process p = pb.start();
        return p.waitFor() == 0;
    }

    // Clase interna para dar color verde y ticks a mensajes INFO en consola
    private static class ColorFormatter extends Formatter {
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_GREEN = "\u001B[32m";

        @Override
        public String format(LogRecord record) {
            String msg = formatMessage(record);
            if (record.getLevel() == Level.INFO) {
                return ANSI_GREEN + "✔ " + msg + " ✔" + ANSI_RESET + "\n";
            } else if (record.getLevel() == Level.SEVERE) {
                // Opcional: rojo para errores
                return "\u001B[31m" + msg + ANSI_RESET + "\n";
            }
            return msg + "\n";
        }
    }
}
