// Declaración del paquete donde se encuentra esta clase.
package AA_mainCodigo;

// Importación de la clase RespaldoAutomatico desde el paquete BB_modeloBBDD_02.
import BB_modeloBBDD_02.RespaldoAutomatico;

// Importación de la clase PantallaBienvenida desde el paquete CC_vistaCodigoInterfaz_03.
import CC_vistaCodigoInterfaz_03.PantallaBienvenida;

// Importación de la librería Swing para mostrar cuadros de diálogo.
import javax.swing.*;

// Importación para manejar excepciones de entrada/salida (usado en FileHandler).
import java.io.IOException;

// Importaciones para el sistema de logging de Java.
import java.util.logging.*;

// Declaración de la clase principal del programa.
public class Principal {

    // Creación de un objeto Logger para registrar eventos de la clase.
    private static final Logger LOGGER = Logger.getLogger(Principal.class.getName());

    // Bloque estático: se ejecuta una vez cuando se carga la clase en memoria.
    static {
        try {
            // Reinicia la configuración del LogManager para limpiar handlers previos.
            LogManager.getLogManager().reset();

            // Establece el nivel del logger para que registre todos los niveles de logs.
            LOGGER.setLevel(Level.ALL);

            // Crea un handler para guardar los logs en un archivo llamado "Principal.log", en modo adjuntar (true).
            FileHandler fh = new FileHandler("Principal.log", true);

            // Establece la codificación del archivo de log a UTF-8.
            fh.setEncoding("UTF-8");

            // Define el formato simple de salida del log (fecha, mensaje, etc.).
            fh.setFormatter(new SimpleFormatter());

            // Agrega el FileHandler al logger para guardar los logs en el archivo.
            LOGGER.addHandler(fh);

            // Crea un handler para mostrar los logs también en la consola.
            ConsoleHandler ch = new ConsoleHandler();

            // Define el nivel de logs que se mostrarán en consola (en este caso, INFO o superior).
            ch.setLevel(Level.INFO);

            // Define el formato del log en consola también como SimpleFormatter.
            ch.setFormatter(new SimpleFormatter());

            // Agrega el ConsoleHandler al logger para mostrar logs en la consola.
            LOGGER.addHandler(ch);

        } catch (IOException e) {
            // Si ocurre un error al crear el archivo de log, se muestra un cuadro de diálogo con el mensaje de error.
            JOptionPane.showMessageDialog(null, "No se pudo crear el archivo de logs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal del programa, punto de entrada (nota: falta 'public' para que sea ejecutable desde la consola).
    static void main(String[] args) {
        try {
            // Registra en el log que se inicia el respaldo automático.
            LOGGER.info("Iniciando respaldo automático...");

            // Llama al método que inicia el respaldo automático de la base de datos u otros archivos.
            RespaldoAutomatico.iniciarRespaldo();

            // Registra en el log que se va a mostrar la pantalla de bienvenida.
            LOGGER.info("Mostrando pantalla de bienvenida...");

            // Llama al método que muestra la ventana gráfica inicial de la aplicación.
            PantallaBienvenida.mostrarVentana();

            // Registra que la aplicación se inició correctamente.
            LOGGER.info("Aplicación iniciada correctamente.");

        } catch (Exception error) {
            // Si ocurre cualquier excepción, se registra como error grave en el log.
            LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación", error);

            // También imprime el stack trace del error en la consola.
            error.printStackTrace();

            // Muestra al usuario un mensaje de error mediante un cuadro de diálogo.
            JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            // Termina la aplicación con código de error (1).
            System.exit(1);
        }
    }
}
