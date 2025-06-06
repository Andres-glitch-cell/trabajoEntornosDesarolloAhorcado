package CC_vistaCodigoInterfaz_03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroDeEventos {

    private static final String CARPETA_LOGS = "LOGS";
    private static final String ARCHIVO_LOG = CARPETA_LOGS + "/app.log";
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void asegurarCarpetaLogs() {
        File carpeta = new File(CARPETA_LOGS);

        if (!carpeta.exists()) {
            boolean creada = carpeta.mkdirs();  // Cambiado a mkdirs()
            if (!creada) {
                System.err.println("No se pudo crear la carpeta de logs: " + CARPETA_LOGS);
            }
        } else if (!carpeta.isDirectory()) {
            System.err.println(CARPETA_LOGS + " existe pero no es una carpeta.");
        }
    }

    public static void registrarError(String mensaje) {
        escribirEnLog("ERROR", mensaje);
    }

    public static void registrarInfo(String mensaje) {
        escribirEnLog("INFO", mensaje);
    }

    private static void escribirEnLog(String nivel, String mensaje) {
        asegurarCarpetaLogs();
        String lineaLog = String.format("%s [%s] %s%n", LocalDateTime.now().format(FORMATO_FECHA_HORA), nivel, mensaje);

        try (FileWriter escritor = new FileWriter(ARCHIVO_LOG, true)) {
            escritor.write(lineaLog);
        } catch (IOException e) {
            System.err.printf("No se pudo escribir en el archivo de log: %s%n", e.getMessage());
        }
    }
}
