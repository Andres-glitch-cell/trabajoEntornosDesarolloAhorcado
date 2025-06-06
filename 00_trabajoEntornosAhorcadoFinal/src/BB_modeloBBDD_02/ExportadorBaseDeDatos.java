// === BLOQUE 1: DECLARACIÓN DEL PAQUETE E IMPORTACIONES ===
package BB_modeloBBDD_02;                                      // Define el paquete donde reside la clase ExportadorBaseDeDatos

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// === BLOQUE 2: DEFINICIÓN DE LA CLASE Y CONSTANTES ===
public class ExportadorBaseDeDatos {                           // Declara la clase pública ExportadorBaseDeDatos
    private static final Logger LOGGER = Logger.getLogger(ExportadorBaseDeDatos.class.getName()); // Crea un Logger para la clase

    // === BLOQUE 3: MÉTODO PÚBLICO PARA EXPORTAR LA BASE DE DATOS ===
    public static void exportar(JFrame parent) {                // Método estático para exportar la base de datos MySQL
        JPasswordField campoClave = new JPasswordField(20);    // Crea un campo de texto con máscara para la contraseña
        Object[] mensaje = {"Contraseña de MySQL (solo administradores):", campoClave}; // Crea un arreglo con mensaje y campo de entrada
        int opcion = JOptionPane.showConfirmDialog(parent, mensaje, "Exportar Base de Datos", JOptionPane.OK_CANCEL_OPTION); // Muestra diálogo para ingresar contraseña
        if (opcion != JOptionPane.OK_OPTION) {                  // Verifica si el usuario canceló o cerró el diálogo
            LOGGER.info("Exportación cancelada por el usuario."); // Registra mensaje de cancelación
            return;                                            // Termina el método si se cancela
        }
        String contraseña = new String(campoClave.getPassword()).trim(); // Obtiene la contraseña y elimina espacios
        if (contraseña.isEmpty()) {                            // Verifica si la contraseña está vacía
            mostrarError(parent, "La contraseña no puede estar vacía."); // Muestra mensaje de error
            LOGGER.warning("Contraseña vacía proporcionada.");  // Registra advertencia de contraseña vacía
            return;                                            // Termina el método si la contraseña está vacía
        }
        if (!(contraseña.equals("administrador1") || contraseña.equals("administrador2") || contraseña.equals("administrador3"))) { // Verifica si la contraseña es válida
            mostrarError(parent, "Contraseña inválida para exportar la base de datos."); // Muestra mensaje de error
            LOGGER.warning("Intento no autorizado de exportación con contraseña: " + contraseña); // Registra advertencia de contraseña inválida
            return;                                            // Termina el método si la contraseña no es válida
        }
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // Genera un timestamp para el nombre del archivo
        String carpetaAdmins = "D:" + File.separator + "00ADAW ordenador clase (he hecho cosas en casa)" + File.separator + "trabajoEntornosDesarolloAhorcado" + File.separator + "admins"; // Define la ruta de la carpeta de backups
        File directorio = new File(carpetaAdmins);             // Crea un objeto File para la carpeta de backups
        if (!directorio.exists() && !directorio.mkdirs()) {    // Verifica y crea la carpeta si no existe
            mostrarError(parent, "No se pudo crear la carpeta 'admins'."); // Muestra mensaje de error
            LOGGER.severe("Fallo al crear la carpeta 'admins' en la ruta: " + carpetaAdmins); // Registra error de creación de carpeta
            return;                                            // Termina el método si falla la creación
        }
        String nombreArchivo = "backup_" + contraseña + "_" + fecha + ".sql"; // Construye el nombre del archivo de backup
        String ruta = carpetaAdmins + File.separator + nombreArchivo; // Construye la ruta completa del archivo
        String mysqldump = System.getProperty("os.name").toLowerCase().contains("win") ? "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe" : "/usr/bin/mysqldump"; // Define la ruta de mysqldump según el SO
        try {                                                  // Inicia un bloque try para ejecutar el respaldo
            if (!comandoDisponible("mysqldump")) {             // Verifica si mysqldump está disponible
                mostrarError(parent, "mysqldump no está disponible en el sistema."); // Muestra mensaje de error
                LOGGER.severe("mysqldump no encontrado en el sistema."); // Registra error de mysqldump no encontrado
                return;                                        // Termina el método si mysqldump no está disponible
            }
            String[] comando = {mysqldump, "-u", "root", "-p" + contraseña, "ahorcadoandres"}; // Construye el comando mysqldump
            ProcessBuilder pb = new ProcessBuilder(comando);   // Crea un ProcessBuilder con el comando
            pb.redirectOutput(new File(ruta));                 // Redirige la salida al archivo de backup
            pb.redirectErrorStream(true);                      // Combina la salida de error con la estándar
            Process proceso = pb.start();                      // Inicia el proceso de mysqldump
            String errores = leerErrores(proceso.getErrorStream()); // Lee los mensajes de error del proceso
            int codigoSalida = proceso.waitFor();               // Espera a que el proceso termine y obtiene el código de salida
            if (codigoSalida == 0 && errores.isEmpty()) {      // Verifica si el respaldo fue exitoso
                mostrarInfo(parent, "Exportación exitosa a:\n" + ruta); // Muestra mensaje de éxito
                LOGGER.info("Backup exitoso generado en: " + ruta); // Registra mensaje de éxito
            } else {                                           // Caso en que el respaldo falla
                mostrarError(parent, "Error al exportar:\n" + errores + "\nCódigo de salida: " + codigoSalida); // Muestra mensaje de error
                LOGGER.severe("Error en exportación: " + errores + " (Código: " + codigoSalida + ")"); // Registra error con detalles
            }
        } catch (IOException e) {                              // Captura excepciones de entrada/salida
            mostrarError(parent, "Error inesperado:\n" + e.getMessage()); // Muestra mensaje de error
            LOGGER.log(Level.SEVERE, "Excepción de IO durante la exportación", e); // Registra error de IO
        } catch (InterruptedException e) {                     // Captura excepciones de interrupción
            mostrarError(parent, "Proceso interrumpido:\n" + e.getMessage()); // Muestra mensaje de error
            LOGGER.log(Level.SEVERE, "Interrupción durante la exportación", e); // Registra error de interrupción
            Thread.currentThread().interrupt();                // Restaura el estado de interrupción
        }
    }

    // === BLOQUE 4: MÉTODO AUXILIAR PARA VERIFICAR DISPONIBILIDAD DE COMANDO ===
    private static boolean comandoDisponible(String cmd) throws IOException, InterruptedException { // Verifica si un comando está disponible
        ProcessBuilder pb = new ProcessBuilder(System.getProperty("os.name").toLowerCase().contains("win") ? "where" : "which", cmd); // Crea ProcessBuilder para buscar el comando
        Process p = pb.start();                                // Inicia el proceso de verificación
        return p.waitFor() == 0;                               // Retorna true si el comando existe (código de salida 0)
    }

    // === BLOQUE 5: MÉTODO AUXILIAR PARA LEER ERRORES DEL PROCESO ===
    private static String leerErrores(InputStream errorStream) throws IOException { // Lee el flujo de errores del proceso
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) { // Crea BufferedReader para leer el flujo
            StringBuilder sb = new StringBuilder();            // Crea StringBuilder para construir el texto de errores
            while (reader.readLine() != null) {                // Lee líneas del flujo hasta que no haya más
                sb.append(reader.readLine()).append("\n");     // Añade cada línea al StringBuilder con salto
            }
            return sb.toString();                              // Retorna el texto completo de errores
        }
    }

    // === BLOQUE 6: MÉTODOS AUXILIARES PARA MOSTRAR MENSAJES ===
    private static void mostrarError(JFrame parent, String mensaje) { // Muestra un diálogo de error
        JOptionPane.showMessageDialog(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE); // Muestra mensaje con ícono de error
    }

    private static void mostrarInfo(JFrame parent, String mensaje) { // Muestra un diálogo informativo
        JOptionPane.showMessageDialog(parent, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE); // Muestra mensaje con ícono de éxito
    }
}