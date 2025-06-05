package BB_modeloBBDD_02;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class RespaldoAutomatico extends Thread {

    private static final Logger LOGGER = Logger.getLogger(RespaldoAutomatico.class.getName());

    private static final String DB_NAME = "AhorcadoAndres";
    private static final long INTERVAL = 30 * 60 * 1000; // 30 minutos

    private static final String MY_CNF_PATH;
    private static final String BACKUP_FOLDER;
    private static final String LOG_FOLDER;
    private static final String CMD_MYSQLDUMP;

    static {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            MY_CNF_PATH = "D:\\ruta\\a\\tu\\.my.cnf";
            BACKUP_FOLDER = "D:\\ruta\\a\\tu\\Backups\\";
            LOG_FOLDER = "D:\\ruta\\a\\tu\\LOGS\\";
            CMD_MYSQLDUMP = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe";
        } else {
            MY_CNF_PATH = "/ruta/a/tu/.my.cnf";
            BACKUP_FOLDER = "/ruta/a/tu/Backups/";
            LOG_FOLDER = "/ruta/a/tu/LOGS/";
            CMD_MYSQLDUMP = "mysqldump";
        }

        try {
            File logDir = new File(LOG_FOLDER);
            if (!logDir.exists()) logDir.mkdirs();

            FileHandler fileHandler = new FileHandler(new File(logDir, "RespaldoAutomatico.log").getAbsolutePath(), 1024 * 1024, 3, true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new ColorFormatter());
            LOGGER.addHandler(consoleHandler);

        } catch (IOException e) {
            System.err.println("Error configurando logs de respaldo: " + e.getMessage());
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
        while (!isInterrupted()) {
            realizarRespaldo();
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                LOGGER.warning("Hilo interrumpido durante espera.");
                Thread.currentThread().interrupt(); // restaurar la interrupción
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

        try {
            if (!comandoDisponible(CMD_MYSQLDUMP)) {
                LOGGER.severe("El comando mysqldump no está disponible en el sistema.");
                return;
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.severe("Error verificando mysqldump: " + e.getMessage());
            return;
        }

        String[] command = {CMD_MYSQLDUMP, "--defaults-extra-file=" + MY_CNF_PATH, DB_NAME};

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

        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error al ejecutar respaldo", e);
            Thread.currentThread().interrupt(); // por si es interrupción
        }
    }

    private boolean comandoDisponible(String cmd) throws IOException, InterruptedException {
        String cmdName = new File(cmd).getName();
        ProcessBuilder pb = new ProcessBuilder(
                System.getProperty("os.name").toLowerCase().contains("win") ? "where" : "which", cmdName
        );
        Process p = pb.start();
        return p.waitFor() == 0;
    }

    private static class ColorFormatter extends Formatter {
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_GREEN = "\u001B[32m";
        private static final String ANSI_RED = "\u001B[31m";

        @Override
        public String format(LogRecord record) {
            String msg = formatMessage(record);
            if (record.getLevel() == Level.INFO) {
                return ANSI_GREEN + "✔ " + msg + ANSI_RESET + "\n";
            } else if (record.getLevel() == Level.SEVERE) {
                return ANSI_RED + "✖ " + msg + ANSI_RESET + "\n";
            }
            return msg + "\n";
        }
    }
}