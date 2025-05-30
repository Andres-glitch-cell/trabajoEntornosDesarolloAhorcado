package BB_modeloBBDD_02;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackUpAutomatico extends Thread {
    private static final String NOMBRE_BBDD = "ahorcadoJuegoEntornosJesus";
    private static final long INTERVALO_MS = 30 * 60 * 1000; // 30 minutos en milisegundos
    private static final String RUTA_BACKUP_LINUX = "/media/andfersal/EXTERNAL_USB/00ADAW ordenador clase (he hecho cosas en casa)/trabajoEntornosDesarolloAhorcado/Backups/";
    private static final String RUTA_BACKUP_WINDOWS = "C:\\Ruta\\Windows\\Backup\\"; // Cambia esta ruta cuando la pases
    private static int backupCounter = 1; // Contador para numerar los backups

    @Override
    public void run() {
        System.out.println("Hilo de backup automático iniciado.");
        while (true) {
            try {
                realizarBackup();
                Thread.sleep(INTERVALO_MS);
            } catch (InterruptedException e) {
                System.err.println("Hilo de backup interrumpido: " + e.getMessage());
                break;
            }
        }
    }

    private void realizarBackup() {
        // Generar fecha y hora actuales para el nombre del archivo
        String fecha = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        String archivoBackup = "BackUp" + backupCounter + "_" + fecha + ".sql";
        String rutaCompleta;

        // Detectar sistema operativo
        String so = System.getProperty("os.name").toLowerCase();
        if (so.contains("win")) {
            rutaCompleta = RUTA_BACKUP_WINDOWS + archivoBackup;
        } else {
            rutaCompleta = RUTA_BACKUP_LINUX + archivoBackup;
        }

        // Verificar y crear carpeta
        File directorio = new File(so.contains("win") ? RUTA_BACKUP_WINDOWS : RUTA_BACKUP_LINUX);
        System.out.print("Verificando/creando carpeta de backup... ");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("✔");
            } else {
                System.out.println("✗ - Error al crear la carpeta");
                return;
            }
        } else {
            System.out.println("✔");
        }

        ProcessBuilder pb;
        if (so.contains("win")) {
            pb = new ProcessBuilder("cmd.exe", "/c", "mysqldump", "--defaults-file=C:\\path\\to\\my.cnf", "AhorcadoAndres");
        } else {
            pb = new ProcessBuilder("mysqldump", "--defaults-file=/home/andfersal/my.cnf", "AhorcadoAndres");
        }

        pb.redirectOutput(new File(rutaCompleta));
        pb.redirectError(ProcessBuilder.Redirect.PIPE);

        try {
            System.out.print("Ejecutando backup... ");
            Process proceso = pb.start();

            // Capturar el mensaje de error
            java.util.Scanner errorScanner = new java.util.Scanner(proceso.getErrorStream()).useDelimiter("\\A");
            String errorOutput = errorScanner.hasNext() ? errorScanner.next() : "";

            int exitCode = proceso.waitFor();

            if (exitCode == 0) {
                System.out.println("✔");
                System.out.println("Backup realizado correctamente en: " + rutaCompleta);
                backupCounter++; // Incrementar el contador después de un backup exitoso
            } else {
                System.out.println("✗");
                System.err.println("Error en el backup. Código de salida: " + exitCode);
                System.err.println("Mensaje de error: " + errorOutput);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("✗");
            System.err.println("Error durante el backup: " + e.getMessage());
        }
    }

    public static void iniciarBackup() {
        BackUpAutomatico backup = new BackUpAutomatico();
        backup.setDaemon(true);
        backup.start();
    }
}