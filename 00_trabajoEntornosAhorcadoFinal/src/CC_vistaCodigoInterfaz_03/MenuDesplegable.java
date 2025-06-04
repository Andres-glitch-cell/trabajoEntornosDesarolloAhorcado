package CC_vistaCodigoInterfaz_03;                 // Define el paquete del proyecto

import javax.swing.*;
import java.awt.*;

public class MenuDesplegable extends JFrame {      // Declara clase que extiende JFrame para ventana

    // --------------------- CONSTRUCTOR ---------------------
    public MenuDesplegable() {                      // Constructor de la clase MenuDesplegable
        super("Menú Desplegable");                  // Llama al constructor padre con título ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Define que al cerrar la ventana, se termina la app
        setSize(300, 200);                          // Establece tamaño fijo de la ventana (ancho x alto)
        setLocationRelativeTo(null);                // Centra la ventana en la pantalla
        inicializarInterfaz();                       // Llama al método para construir la interfaz gráfica
        RegistroDeEventos.registrarInfo(            // Registra en log que la ventana fue creada
                "Ventana Menú Desplegable inicializada");
    }
    // --------------------------------------------------------

    // ----------------- MÉTODO PARA MOSTRAR VENTANA --------------
    public static void mostrarVentana() {            // Método estático para mostrar esta ventana
        SwingUtilities.invokeLater(() -> {            // Ejecuta el código dentro en el hilo de eventos Swing
            new MenuDesplegable().setVisible(true);  // Crea instancia de MenuDesplegable y la muestra
            RegistroDeEventos.registrarInfo(         // Registra en log que la ventana está visible
                    "Ventana Menú Desplegable visible");
        });
    }
    // ------------------------------------------------------------

    // ----------------------- MÉTODO MAIN -------------------------
    static void main(String[] args) {                  // Método main para iniciar la aplicación
        mostrarVentana();                             // Llama a mostrarVentana para desplegar interfaz
    }
    // -------------------------------------------------------------

    // ------------- INICIALIZAR COMPONENTES GRÁFICOS --------------
    private void inicializarInterfaz() {               // Método para construir la interfaz gráfica
        JPanel panel = new JPanel(new GridBagLayout());  // Crea panel principal con layout GridBagLayout
        panel.setBackground(new Color(34, 40, 49));      // Establece color de fondo oscuro al panel
        add(panel);                                       // Agrega panel a la ventana JFrame actual

        JMenuBar barraMenu = new JMenuBar();              // Crea la barra de menú superior
        setJMenuBar(barraMenu);                            // Asigna la barra de menú a la ventana

        JMenu menuPrincipal = new JMenu("Menú");          // Crea menú principal llamado "Menú"
        barraMenu.add(menuPrincipal);                      // Añade el menú principal a la barra

        JMenu menuJugador = new JMenu("Jugador");         // Crea submenú llamado "Jugador"

        JMenuItem abrirJuego = new JMenuItem("Abrir Juego");  // Crea ítem de menú "Abrir Juego"
        abrirJuego.addActionListener(e -> {               // Añade evento para cuando se selecciona ítem
            RegistroDeEventos.registrarInfo("Seleccionado 'Abrir Juego'");  // Registra selección
            PantallaAhorcado.mostrarVentana();             // Muestra ventana del juego Ahorcado
            dispose();                                     // Cierra ventana actual para liberar recursos
            RegistroDeEventos.registrarInfo(               // Registra cierre de la ventana menú
                    "Ventana Menú Desplegable cerrada tras abrir juego");
        });

        menuJugador.add(abrirJuego);                       // Añade ítem "Abrir Juego" al submenú Jugador
        menuPrincipal.add(menuJugador);                    // Añade submenú Jugador al menú principal

        JMenu menuAdministrador = new JMenu("Administrador");  // Crea submenú "Administrador"

        JMenuItem modoAdmin = new JMenuItem("Modo Administrador");  // Crea ítem "Modo Administrador"
        modoAdmin.addActionListener(e -> manejarModoAdministrador());  // Asocia evento al ítem

        menuAdministrador.add(modoAdmin);                    // Añade ítem modoAdmin al submenú Administrador
        menuPrincipal.add(menuAdministrador);                // Añade submenú Administrador al menú principal
    }
    // -------------------------------------------------------------

    // ------------- MÉTODO PARA MANEJAR MODO ADMINISTRADOR ----------
    private void manejarModoAdministrador() {             // Método para gestionar acceso modo administrador
        RegistroDeEventos.registrarInfo("Seleccionado 'Modo Administrador'"); // Log selección modo admin

        String entrada = JOptionPane.showInputDialog(this,  // Muestra diálogo para solicitar nivel admin
                "Ingresa el nivel de administrador (1-3):",
                "Nivel de Administrador",
                JOptionPane.QUESTION_MESSAGE);

        int nivelAdmin = 1;                               // Valor por defecto para nivel administrador

        try {
            if (entrada != null && !entrada.isEmpty()) { // Si el usuario ingresó algo
                nivelAdmin = Integer.parseInt(entrada);  // Intenta convertir la entrada a entero
            }
            RegistroDeEventos.registrarInfo("Nivel administrador ingresado: " + nivelAdmin); // Log nivel ingresado
        } catch (NumberFormatException ex) {               // Si la conversión falla
            JOptionPane.showMessageDialog(this,            // Muestra advertencia al usuario
                    "Nivel inválido, se usará 1 por defecto.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            RegistroDeEventos.registrarError("Nivel inválido ingresado: " + entrada);  // Registra error en log
        }

        VentanaAdministrador.mostrarVentana(nivelAdmin);  // Abre ventana administrador con el nivel indicado
        dispose();                                         // Cierra ventana actual para liberar recursos
        RegistroDeEventos.registrarInfo("Ventana Menú Desplegable cerrada tras abrir administrador"); // Log cierre ventana
    }
    // -------------------------------------------------------------
}