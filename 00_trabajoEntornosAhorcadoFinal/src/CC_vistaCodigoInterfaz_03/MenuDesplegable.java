package CC_vistaCodigoInterfaz_03;
// Define el paquete al que pertenece esta clase, usado para organizar el proyecto

import javax.swing.*;
import java.awt.*;
// Importa las clases del paquete AWT para gestionar componentes gráficos y layouts

public class MenuDesplegable extends JFrame {
// Declaración de la clase MenuDesplegable que hereda de JFrame para crear una ventana gráfica

    // Constructor de la clase, se ejecuta al crear una instancia de MenuDesplegable
    public MenuDesplegable() {
        super("Menú Desplegable");
        // Llama al constructor padre JFrame con el título de la ventana "Menú Desplegable"

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Define que la aplicación se cierre completamente al cerrar esta ventana

        setSize(300, 200);
        // Establece el tamaño de la ventana en 300 píxeles de ancho y 200 de alto

        setLocationRelativeTo(null);
        // Centra la ventana en la pantalla

        inicializarInterfaz();
        // Llama al método que construye y organiza los componentes gráficos de la ventana

        RegistroDeEventos.registrarInfo("Ventana Menú Desplegable inicializada");
        // Llama a un método externo para registrar en el log que la ventana fue inicializada
    }

    // Método estático que muestra la ventana de forma segura dentro del hilo de eventos Swing
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            // Pide a Swing que ejecute el código en el hilo de eventos para evitar problemas de concurrencia

            new MenuDesplegable().setVisible(true);
            // Crea una instancia de MenuDesplegable y la hace visible (muestra la ventana)

            RegistroDeEventos.registrarInfo("Ventana Menú Desplegable visible");
            // Registra en log que la ventana ya está visible para el usuario
        });
    }

    // Método principal que se ejecuta al iniciar la aplicación
    static void main(String[] args) {
        mostrarVentana();
        // Llama al método para mostrar la ventana del menú desplegable al iniciar la aplicación
    }

    // Método privado que crea y organiza todos los componentes gráficos dentro de la ventana
    private void inicializarInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        // Crea un panel con layout GridBagLayout para colocar los componentes de forma flexible

        panel.setBackground(new Color(34, 40, 49));
        // Establece un color oscuro para el fondo del panel

        add(panel);
        // Añade el panel creado a la ventana JFrame principal

        JMenuBar barraMenu = new JMenuBar();
        // Crea una barra de menú que contendrá los menús desplegables

        setJMenuBar(barraMenu);
        // Asigna la barra de menú creada a la ventana principal

        JMenu menuPrincipal = new JMenu("Menú");
        // Crea un menú principal con el texto "Menú"

        barraMenu.add(menuPrincipal);
        // Añade el menú principal a la barra de menús

        JMenu menuJugador = new JMenu("Jugador");
        // Crea un submenú llamado "Jugador"

        JMenuItem abrirJuego = new JMenuItem("Abrir Juego");
        // Crea un ítem dentro del submenú "Jugador" con el texto "Abrir Juego"

        abrirJuego.addActionListener(e -> {
            // Define la acción a realizar cuando se selecciona "Abrir Juego"

            RegistroDeEventos.registrarInfo("Seleccionado 'Abrir Juego'");
            // Registra en el log que se seleccionó esta opción

            PantallaAhorcado.mostrarVentana();
            // Muestra la ventana del juego del ahorcado

            dispose();
            // Cierra la ventana actual del menú desplegable

            RegistroDeEventos.registrarInfo("Ventana Menú Desplegable cerrada tras abrir juego");
            // Registra en log que la ventana se cerró después de abrir el juego
        });

        menuJugador.add(abrirJuego);
        // Añade la opción "Abrir Juego" al submenú "Jugador"

        menuPrincipal.add(menuJugador);
        // Añade el submenú "Jugador" al menú principal

        JMenu menuAdministrador = new JMenu("Administrador");
        // Crea otro submenú llamado "Administrador"

        JMenuItem modoAdmin = new JMenuItem("Modo Administrador");
        // Crea una opción dentro del submenú "Administrador" llamada "Modo Administrador"

        modoAdmin.addActionListener(e -> manejarModoAdministrador());
        // Al seleccionar esta opción, se ejecuta el método manejarModoAdministrador()

        menuAdministrador.add(modoAdmin);
        // Añade la opción "Modo Administrador" al submenú "Administrador"

        menuPrincipal.add(menuAdministrador);
        // Añade el submenú "Administrador" al menú principal
    }

    // Método privado para manejar la selección del modo administrador
    private void manejarModoAdministrador() {
        RegistroDeEventos.registrarInfo("Seleccionado 'Modo Administrador'");
        // Registra en log que se seleccionó el modo administrador

        String entrada = JOptionPane.showInputDialog(this,
                "Ingresa el nivel de administrador (1-3):",
                "Nivel de Administrador",
                JOptionPane.QUESTION_MESSAGE);
        // Muestra un cuadro de diálogo pidiendo al usuario que ingrese un nivel (del 1 al 3)

        int nivelAdmin = 1;
        // Valor por defecto para nivel administrador si no se ingresa nada o la entrada no es válida

        try {
            if (entrada != null && !entrada.isEmpty()) {
                // Verifica que se haya ingresado algo distinto de vacío

                nivelAdmin = Integer.parseInt(entrada);
                // Convierte la cadena ingresada a un número entero
            }
            RegistroDeEventos.registrarInfo("Nivel administrador ingresado: " + nivelAdmin);
            // Registra el nivel ingresado en el log
        } catch (NumberFormatException ex) {
            // Captura el error si la conversión a entero falla (entrada inválida)

            JOptionPane.showMessageDialog(this,
                    "Nivel inválido, se usará 1 por defecto.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            // Muestra un mensaje de advertencia al usuario

            RegistroDeEventos.registrarError("Nivel inválido ingresado: " + entrada);
            // Registra en el log el error de entrada inválida
        }

        VentanaAdministrador.mostrarVentana(nivelAdmin);
        // Abre la ventana de administrador pasando el nivel indicado

        dispose();
        // Cierra la ventana actual del menú desplegable

        RegistroDeEventos.registrarInfo("Ventana Menú Desplegable cerrada tras abrir administrador");
        // Registra en el log el cierre de la ventana tras abrir la ventana administrador
    }
}
