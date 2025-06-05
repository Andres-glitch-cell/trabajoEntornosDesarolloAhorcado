// Define el paquete al que pertenece la clase
package BB_modeloBBDD_02;

// Importa las clases necesarias para interfaz gráfica (JOptionPane, JFrame), manejo de archivos (File, InputStream, BufferedReader),
// manejo de fechas y formatos (SimpleDateFormat, Date) y logging (Logger, Level)
import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// -----------------------------
// DEFINICIÓN DE LA CLASE
// -----------------------------

// Clase que permite exportar una base de datos MySQL a un archivo SQL
public class ExportadorBaseDeDatos {

    // -----------------------------
    // CONSTANTES Y VARIABLES ESTÁTICAS
    // -----------------------------

    // Logger para registrar eventos y errores relacionados con la exportación
    private static final Logger LOGGER = Logger.getLogger(ExportadorBaseDeDatos.class.getName());

    // -----------------------------
    // MÉTODO PÚBLICO PARA EXPORTAR LA BASE DE DATOS
    // -----------------------------

    /**
     * Método estático para exportar la base de datos MySQL.
     * Muestra diálogos para pedir contraseña y notificar resultados.
     *
     * @param parent ventana padre para centrar los diálogos
     */
    public static void exportar(JFrame parent) {
        // Crea un campo de texto para contraseña con máscara (oculta los caracteres)
        JPasswordField campoClave = new JPasswordField(20);

        // Crea un arreglo que contiene el mensaje y el campo de entrada para mostrar en el diálogo
        Object[] mensaje = {"Contraseña de MySQL (solo administradores):", campoClave};

        // Muestra un diálogo con opciones OK y Cancelar para que el usuario introduzca la contraseña
        int opcion = JOptionPane.showConfirmDialog(parent, mensaje, "Exportar Base de Datos", JOptionPane.OK_CANCEL_OPTION);

        // Si el usuario cancela o cierra el diálogo, registra info y termina el método sin hacer nada
        if (opcion != JOptionPane.OK_OPTION) {
            LOGGER.info("Exportación cancelada por el usuario.");
            return;
        }

        // Obtiene la contraseña introducida y le quita espacios en blanco al inicio y final
        String contraseña = new String(campoClave.getPassword()).trim();

        // Si la contraseña está vacía, muestra mensaje de error, registra advertencia y termina
        if (contraseña.isEmpty()) {
            mostrarError(parent, "La contraseña no puede estar vacía.");
            LOGGER.warning("Contraseña vacía proporcionada.");
            return;
        }

        // Verifica si la contraseña está dentro de las válidas para realizar exportación
        if (!(contraseña.equals("administrador1") || contraseña.equals("administrador2") || contraseña.equals("administrador3"))) {
            mostrarError(parent, "Contraseña inválida para exportar la base de datos.");
            LOGGER.warning("Intento no autorizado de exportación con contraseña: " + contraseña);
            return;
        }

        // Genera un string con fecha y hora actual formateado para nombrar el archivo de backup
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Define la ruta del directorio donde se guardarán los backups, usando separadores correctos según SO
        String carpetaAdmins = "D:" + File.separator + "00ADAW ordenador clase (he hecho cosas en casa)" + File.separator + "trabajoEntornosDesarolloAhorcado" + File.separator + "admins";

        // Crea un objeto File que representa esa carpeta
        File directorio = new File(carpetaAdmins);

        // Si la carpeta no existe, intenta crearla; si no puede, muestra error, registra el fallo y termina
        if (!directorio.exists() && !directorio.mkdirs()) {
            mostrarError(parent, "No se pudo crear la carpeta 'admins'.");
            LOGGER.severe("Fallo al crear la carpeta 'admins' en la ruta: " + carpetaAdmins);
            return;
        }

        // Construye el nombre del archivo de backup con prefijo, contraseña y fecha
        String nombreArchivo = "backup_" + contraseña + "_" + fecha + ".sql";

        // Construye la ruta completa al archivo de backup dentro de la carpeta
        String ruta = carpetaAdmins + File.separator + nombreArchivo;

        // Determina la ruta del ejecutable mysqldump según el sistema operativo (Windows o Linux/Mac)
        String mysqldump = System.getProperty("os.name").toLowerCase().contains("win") ? "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe" : "/usr/bin/mysqldump";

        try {
            // Verifica si el comando mysqldump está disponible en el sistema (usando el método auxiliar)
            if (!comandoDisponible("mysqldump")) {
                mostrarError(parent, "mysqldump no está disponible en el sistema.");
                LOGGER.severe("mysqldump no encontrado en el sistema.");
                return;
            }

            // Construye el comando para ejecutar mysqldump con usuario root, la contraseña dada y la base de datos 'ahorcadoandres'
            String[] comando = {mysqldump, "-u", "root", "-p" + contraseña, "ahorcadoandres"};

            // Crea un ProcessBuilder para ejecutar el comando externo
            ProcessBuilder pb = new ProcessBuilder(comando);

            // Redirige la salida estándar del proceso al archivo donde se guardará el backup
            pb.redirectOutput(new File(ruta));

            // Redirige la salida de error para capturar posibles mensajes
            pb.redirectErrorStream(true);

            // Inicia el proceso que ejecuta el mysqldump
            Process proceso = pb.start();

            // Lee los mensajes de error (si los hay) del proceso usando método auxiliar
            String errores = leerErrores(proceso.getErrorStream());

            // Espera a que el proceso termine y obtiene el código de salida (0 es éxito)
            int codigoSalida = proceso.waitFor();

            // Si terminó correctamente y sin errores, muestra mensaje de éxito e informa en logs
            if (codigoSalida == 0 && errores.isEmpty()) {
                mostrarInfo(parent, "Exportación exitosa a:\n" + ruta);
                LOGGER.info("Backup exitoso generado en: " + ruta);
            } else {
                // Si hubo errores o código distinto de 0, muestra error con detalles y registra en logs
                mostrarError(parent, "Error al exportar:\n" + errores + "\nCódigo de salida: " + codigoSalida);
                LOGGER.severe("Error en exportación: " + errores + " (Código: " + codigoSalida + ")");
            }

            // Captura excepciones de IO o interrupciones que puedan ocurrir durante la ejecución del proceso
        } catch (IOException | InterruptedException e) {
            mostrarError(parent, "Error inesperado:\n" + e.getMessage());
            LOGGER.log(Level.SEVERE, "Excepción inesperada durante la exportación", e);
        }
    }

    // -----------------------------
    // MÉTODO AUXILIAR PARA VERIFICAR DISPONIBILIDAD DE UN COMANDO EN EL SISTEMA
    // -----------------------------

    /**
     * Comprueba si un comando está disponible en el sistema.
     * En Windows usa "where", en Linux/Mac "which".
     *
     * @param cmd nombre del comando a verificar
     * @return true si el comando existe, false si no
     * @throws IOException          si falla la ejecución del proceso
     * @throws InterruptedException si el proceso es interrumpido
     */
    private static boolean comandoDisponible(String cmd) throws IOException, InterruptedException {
        // Define el comando para buscar el ejecutable según sistema operativo
        ProcessBuilder pb = new ProcessBuilder(System.getProperty("os.name").toLowerCase().contains("win") ? "where" : "which", cmd);
        // Ejecuta el comando
        Process p = pb.start();
        // Devuelve true si el proceso termina con código 0 (comando encontrado)
        return p.waitFor() == 0;
    }

    // -----------------------------
    // MÉTODO AUXILIAR PARA LEER ERRORES DEL PROCESO
    // -----------------------------

    /**
     * Lee el flujo de errores de un InputStream y retorna el contenido como cadena.
     *
     * @param errorStream flujo de errores del proceso
     * @return texto completo de errores leídos
     * @throws IOException si ocurre error durante la lectura
     */
    private static String leerErrores(InputStream errorStream) throws IOException {
        // Usa BufferedReader para leer línea a línea el flujo de error
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
            StringBuilder sb = new StringBuilder();
            String linea;
            // Lee cada línea y la añade al StringBuilder
            while ((linea = reader.readLine()) != null) {
                sb.append(linea).append("\n");
            }
            // Retorna el texto completo con todos los errores
            return sb.toString();
        }
    }

    // -----------------------------
    // MÉTODOS AUXILIARES PARA MOSTRAR MENSAJES AL USUARIO
    // -----------------------------

    /**
     * Muestra un diálogo de error con el mensaje proporcionado.
     *
     * @param parent  ventana padre
     * @param mensaje texto del error a mostrar
     */
    private static void mostrarError(JFrame parent, String mensaje) {
        // Usa JOptionPane para mostrar mensaje de error con ícono apropiado
        JOptionPane.showMessageDialog(parent, "ERROR: " + mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un diálogo informativo con el mensaje proporcionado.
     *
     * @param parent  ventana padre
     * @param mensaje texto informativo a mostrar
     */
    private static void mostrarInfo(JFrame parent, String mensaje) {
        // Usa JOptionPane para mostrar mensaje de información con ícono apropiado
        JOptionPane.showMessageDialog(parent, "INFO: " + mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
