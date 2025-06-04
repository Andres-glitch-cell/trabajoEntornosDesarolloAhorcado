package CC_vistaCodigoInterfaz_03;

// Importa la clase para exportar la base de datos
import BB_modeloBBDD_02.ExportadorBaseDeDatos;

import javax.swing.*;                 // Importa componentes Swing para interfaz gráfica
import java.awt.*;                    // Importa clases para manejo de colores, fuentes, layouts, etc.
import java.awt.event.ActionListener; // Importa para manejar eventos de botones
import java.io.IOException;           // Manejo de excepciones IO
import java.util.logging.FileHandler; // Para manejo de logs en archivos
import java.util.logging.Level;       // Niveles de logging (INFO, WARNING, etc.)
import java.util.logging.Logger;      // Clase para logging
import java.util.logging.SimpleFormatter; // Formato simple para logs

// Clase principal que extiende JFrame para crear ventana
public class PantallaBienvenida extends JFrame {

    // Logger para registrar eventos e incidencias de la aplicación
    private static final Logger LOGGER = Logger.getLogger(PantallaBienvenida.class.getName());

    // Bloque estático para configurar el logger antes de crear instancias
    static {
        try {
            // Crear un manejador para guardar logs en archivo "ahorcado.log" (modo append)
            FileHandler fh = new FileHandler("ahorcado.log", true);
            // Asignar formato simple para el archivo de log
            fh.setFormatter(new SimpleFormatter());
            // Añadir el manejador al logger
            LOGGER.addHandler(fh);
            // Evitar que se dupliquen logs en consola
            LOGGER.setUseParentHandlers(false);
            // Establecer nivel para capturar todos los mensajes (finos y generales)
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            // Si falla la inicialización, imprimir error en consola de errores
            System.err.println("No se pudo inicializar el logger: " + e.getMessage());
        }
    }

    // Constructor: configura la ventana y añade el panel principal
    public PantallaBienvenida() {
        super("Bienvenida al Juego del Ahorcado"); // Título ventana
        LOGGER.info("Inicializando PantallaBienvenida..."); // Log info

        configurarVentana();          // Configura tamaño, cierre, etc.
        add(crearPanelPrincipal());  // Añade panel con contenido principal

        LOGGER.info("PantallaBienvenida inicializada correctamente."); // Log info
    }

    // Método para configurar propiedades básicas de la ventana
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra app al cerrar ventana
        setSize(550, 340);  // Tamaño ventana ajustado para botones y contenido
        setResizable(false); // Ventana no redimensionable
        setLocationRelativeTo(null); // Centra ventana en pantalla
    }

    // Crea y devuelve el panel principal con todos los componentes
    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(); // Nuevo panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout vertical
        panel.setBackground(new Color(34, 40, 49)); // Color fondo oscuro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margenes internos

        panel.add(crearTitulo());            // Añade título
        panel.add(Box.createVerticalStrut(20));  // Espacio vertical
        panel.add(crearPanelBotonesPrincipales()); // Botones: iniciar, sesión, registro
        panel.add(Box.createVerticalStrut(15));  // Espacio vertical
        panel.add(crearBotonExportar());     // Botón exportar base de datos
        panel.add(Box.createVerticalStrut(20));  // Espacio vertical unificado
        panel.add(crearBotonSalir());        // Botón salir

        return panel; // Devuelve el panel completo
    }

    // Crea el JLabel con el título centrado y estilizado
    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("¡Bienvenido al Juego del Ahorcado!", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE); // Texto blanco
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20)); // Fuente grande y negrita
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        return titulo; // Devuelve el JLabel
    }

    // Crea un panel con los tres botones principales
    private JPanel crearPanelBotonesPrincipales() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Layout horizontal con espacio entre botones
        panel.setOpaque(false); // Fondo transparente

        // Crear botones con texto, color y acción
        JButton btnIniciar = crearBoton("Iniciar Juego", new Color(100, 149, 237), this::accionBoton);
        JButton btnSesion = crearBoton("Iniciar Sesión", new Color(100, 149, 237), this::accionBoton);
        JButton btnRegistro = crearBoton("Registrarse", new Color(100, 149, 237), this::accionBoton);

        // Añadir botones al panel
        panel.add(btnIniciar);
        panel.add(btnSesion);
        panel.add(btnRegistro);

        return panel; // Devolver panel con botones
    }

    // Crea el botón para exportar la base de datos
    private JButton crearBotonExportar() {
        return crearBoton("Exportar Base de Datos", new Color(50, 168, 82), this::accionBoton);
    }

    // Crea el botón para salir del juego
    private JButton crearBotonSalir() {
        return crearBoton("Salir del Juego", new Color(255, 69, 0), this::accionBoton);
    }

    // Método genérico para crear botones con texto, color de fondo y acción
    private JButton crearBoton(String texto, Color colorFondo, ActionListener listener) {
        JButton boton = new JButton(texto);        // Crear botón con texto
        boton.setBackground(colorFondo);            // Color de fondo personalizado
        boton.setForeground(Color.WHITE);           // Texto blanco
        boton.setFont(new Font("SansSerif", Font.BOLD, 14)); // Fuente negrita tamaño 14
        boton.setActionCommand(texto);               // Comando para identificar acción
        boton.addActionListener(listener);           // Añadir listener para evento click
        return boton;                                // Devolver botón creado
    }

    // Maneja la acción según el botón presionado
    private void accionBoton(java.awt.event.ActionEvent e) {
        String comando = e.getActionCommand();     // Obtener texto del botón presionado
        LOGGER.info("Botón presionado: " + comando); // Log info con el botón presionado

        switch (comando) {                         // Según el comando, hacer acción correspondiente
            case "Salir del Juego": {
                confirmarSalida();                 // Confirmar si desea salir
                break;
            }
            case "Iniciar Juego": {
                abrirPantalla("PantallaAhorcado"); // Abrir ventana del juego
                break;
            }
            case "Iniciar Sesión": {
                abrirPantalla("IniciarSesion");  // Abrir ventana inicio sesión
                break;
            }
            case "Registrarse": {
                abrirPantalla("Registrarse");    // Abrir ventana registro
                break;
            }
            case "Exportar Base de Datos": {
                ExportadorBaseDeDatos.exportar(this); // Exportar base de datos
                break;
            }
            case "Opciones del Juego": {
                mostrarOpciones();               // Mostrar opciones del juego
                break;
            }
            default: {
                LOGGER.warning("Acción no reconocida: " + comando); // Log advertencia si no se reconoce acción
                break;
            }
        }
    }

    // Muestra un mensaje con las opciones del juego (placeholder)
    private void mostrarOpciones() {
        JOptionPane.showMessageDialog(this,
                "Aquí puedes implementar las opciones generales del juego (idioma, dificultad, etc.).",
                "Opciones del Juego", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método que confirma si el usuario quiere salir, muestra diálogo y actúa según respuesta
    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres salir?", "Salir del Juego",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            LOGGER.info("Cerrando aplicación..."); // Log cierre
            System.exit(0);                         // Termina la aplicación
        } else {
            LOGGER.info("Cancelada salida.");      // Log cancelación de salida
        }
    }

    // Abre una ventana específica por su nombre de clase y cierra la actual
    private void abrirPantalla(String nombreClase) {
        try {
            // Busca la clase por nombre completo
            Class<?> clase = Class.forName("CC_vistaCodigoInterfaz_03." + nombreClase);
            // Invoca método estático "mostrarVentana" para mostrar esa ventana
            clase.getMethod("mostrarVentana").invoke(null);
            dispose(); // Cierra la ventana actual
            LOGGER.info(nombreClase + " abierta correctamente."); // Log éxito
        } catch (Exception e) {
            LOGGER.warning("Error abriendo " + nombreClase + ": " + e.getMessage()); // Log error
            JOptionPane.showMessageDialog(this,
                    "❌ No se puede abrir " + nombreClase + ".",
                    "Error", JOptionPane.ERROR_MESSAGE); // Mostrar diálogo de error
        }
    }

    // Método estático para crear y mostrar la ventana en el hilo de eventos Swing
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new PantallaBienvenida().setVisible(true));
    }
}
