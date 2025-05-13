package AA_mainCodigo;

import CC_vistaCodigoInterfaz_03.*;

/**
 * Clase principal que inicia el sistema de gestión de usuarios para el juego del ahorcado.
 * Crea y muestra las ventanas de registro, inicio de sesión, nuevo juego, pantalla de juego y menú desplegable.
 *
 * @author Andrés Fernández Salaud
 * @version Ahorcado_v.0.0.4
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     * Instancia y hace visibles las ventanas principales del sistema.
     *
     * @param args argumentos de la línea de comandos (no utilizados)
     */

    public static void main(String[] args) {
        // 1.- Mostrar la ventana de bienvenida
        // Solo mostramos la pantalla de bienvenida en el inicio.
        AAA_PantallaBienvenida bienvenida = new AAA_PantallaBienvenida();
        bienvenida.setVisible(true);
    }
}