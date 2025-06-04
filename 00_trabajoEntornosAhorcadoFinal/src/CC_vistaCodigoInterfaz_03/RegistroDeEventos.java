// Indica que esta clase pertenece al paquete CC_vistaCodigoInterfaz_03
package CC_vistaCodigoInterfaz_03;

// Importa la clase File para trabajar con archivos

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Declara una clase pública llamada RegistroDeEventos
public class RegistroDeEventos {

    // ---------------------------------------
    // Constantes para configuración de logs
    // ---------------------------------------

    // Declara una constante con el nombre de la carpeta donde se guardarán los archivos de log
    private static final String CARPETA_LOGS = "LOGS";

    // Declara una constante con la ruta completa del archivo de log (incluye la carpeta)
    private static final String ARCHIVO_LOG = CARPETA_LOGS + "/app.log";

    // Declara una constante para el formato de fecha y hora que se usará en cada mensaje de log
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ---------------------------------------
    // Métodos para gestionar la carpeta de logs
    // ---------------------------------------

    // Método privado que se asegura de que la carpeta de logs exista
    private static void asegurarCarpetaLogs() {
        // Crea un objeto File para representar la carpeta de logs
        File carpeta = new File(CARPETA_LOGS);

        // Verifica si la carpeta no existe
        if (!carpeta.exists()) {
            // Intenta crear la carpeta
            boolean creada = carpeta.mkdir();

            // Si la creación falla, muestra un mensaje de error en consola
            if (!creada) {
                System.err.println("No se pudo crear la carpeta de logs: " + CARPETA_LOGS);
            }
        }
    }

    // ---------------------------------------
    // Métodos públicos para registrar logs
    // ---------------------------------------

    // Método público para registrar un mensaje con nivel ERROR en el archivo de log
    public static void registrarError(String mensaje) {
        // Llama al método escribirEnLog con el nivel "ERROR" y el mensaje recibido
        escribirEnLog("ERROR", mensaje);
    }

    // Método público para registrar un mensaje con nivel INFO en el archivo de log
    public static void registrarInfo(String mensaje) {
        // Llama al método escribirEnLog con el nivel "INFO" y el mensaje recibido
        escribirEnLog("INFO", mensaje);
    }

    // ---------------------------------------
    // Método privado para escribir en el archivo de log
    // ---------------------------------------

    // Método privado que escribe una línea en el archivo de log
    private static void escribirEnLog(String nivel, String mensaje) {
        // Asegura que la carpeta de logs existe antes de escribir el mensaje
        asegurarCarpetaLogs();

        // Obtiene la fecha y hora actual y la formatea según FORMATO_FECHA_HORA
        // Crea una cadena con formato: "fecha hora [nivel] mensaje"
        String lineaLog = String.format("%s [%s] %s%n", LocalDateTime.now().format(FORMATO_FECHA_HORA), nivel, mensaje);

        // Intenta abrir el archivo ARCHIVO_LOG en modo de agregar contenido (append)
        try (FileWriter escritor = new FileWriter(ARCHIVO_LOG, true)) {
            // Escribe la línea en el archivo de log
            escritor.write(lineaLog);
        } catch (IOException e) {
            // Si ocurre un error al escribir, muestra un mensaje de error en consola
            System.err.printf("No se pudo escribir en el archivo de log: %s%n", e.getMessage());
        }
    }
}