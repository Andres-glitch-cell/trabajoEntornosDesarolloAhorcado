package AA_mainCodigo;

import BB_modeloBBDD_02.BackUpAutomatico;
import CC_vistaCodigoInterfaz_03.PantallaBienvenida;
import javax.swing.*;

/**
 * Clase principal que inicia la aplicación del juego del ahorcado. (Clase 1)
 * <p>
 * Muestra la ventana de bienvenida al iniciar la aplicación.
 * </p>
 *
 * @author Andrés Fernández Salaud
 * @version 0.0.4EE
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {

        try {
            BackUpAutomatico.iniciarBackup();  // <-- Añadido para iniciar backup automático

            PantallaBienvenida.mostrarVentana();
        } catch (Exception e) {
            // TIP If an exception occurs, IntelliJ IDEA will show you the stack trace in the console.
            // TIP Press <shortcut actionId="ShowIntentionActions"/> to see possible fixes or suggestions for this error.
            e.printStackTrace();

            // TIP Show an error message dialog. You can test it by simulating an error.
            JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // TIP Use <shortcut actionId="GoToFile"/> to open this file by name and navigate to the main class.
    }
}
