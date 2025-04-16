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
        // 1.- Objeto "objetoRegistrarse" de la clase Uno_Registrarse
        AA_Registrarse objetoRegistrarse = new AA_Registrarse();
        objetoRegistrarse.setVisible(true);

        // 2.- Objeto "objetoLogin" de la clase Cinco_IniciarSesion (Cambiar número)
        BB_IniciarSesion objetoLogin = new BB_IniciarSesion();
        objetoLogin.setVisible(true);

        // 3.- Objeto "objetoNuevoJuego" de la clase Seis_NuevoJuego
        CC_NuevoJuego objetoNuevoJuego = new CC_NuevoJuego();
        objetoNuevoJuego.setVisible(true);

        // 4.- Objeto "PantallaDeJuego" de la clase Dos_PantallaAhorcado
        DD_PantallaAhorcado PantallaDeJuego = new DD_PantallaAhorcado();
        PantallaDeJuego.setVisible(true);

        // 5.- Objeto "objetoMenuDesplegable" de la clase Cuatro_MenuDesplegable (Cambiar número)
        EE_MenuDesplegable objetoMenuDesplegable = new EE_MenuDesplegable();
        objetoMenuDesplegable.setVisible(true);
    }
}