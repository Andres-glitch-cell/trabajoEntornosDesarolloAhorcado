package CC_vistaCodigoInterfaz_03;  // Paquete donde está la clase

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Ventana gráfica para el modo administrador del juego.
 * Muestra opciones dependiendo del nivel del administrador (1, 2 o 3).
 * Incorpora logging para registrar eventos, errores y acciones del usuario.
 */
public class VentanaAdministrador extends JFrame {  // Clase que extiende JFrame (ventana)

    // ---------------------- BLOQUE LOGGER -----------------------
    private static final Logger LOGGER = Logger.getLogger(VentanaAdministrador.class.getName());

    static {  // Bloque estático para configurar el logger solo una vez
        try {
            LOGGER.setLevel(Level.ALL);  // Registrar todos los niveles de log
            FileHandler fh = new FileHandler("VentanaAdministrador.log", true);  // Archivo de logs en modo append
            fh.setEncoding("UTF-8");  // Codificación UTF-8 para los logs
            fh.setFormatter(new SimpleFormatter());  // Formato simple para los mensajes
            LOGGER.addHandler(fh);  // Añadir handler al logger
            LOGGER.setUseParentHandlers(false);  // Evitar duplicados en consola
        } catch (IOException e) {
            System.err.println("No se pudo inicializar el archivo de logs: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "No se pudo inicializar el archivo de logs:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // -------------------------------------------------------------

    // -------------------- VARIABLES DE INSTANCIA -----------------
    private final int nivelAdmin;  // Nivel de permisos del administrador (1, 2 o 3)
    // -------------------------------------------------------------

    // ---------------------- CONSTRUCTOR --------------------------
    public VentanaAdministrador(int nivelAdmin) {
        super("Modo Administrador");  // Título de la ventana

        // Validar nivel de administrador válido
        if (nivelAdmin < 1 || nivelAdmin > 3) {
            throw new IllegalArgumentException("Nivel de administrador inválido: " + nivelAdmin);
        }

        this.nivelAdmin = nivelAdmin;  // Guardar nivel en variable de instancia
        configurarVentana();  // Configurar propiedades básicas de la ventana
        inicializarUI();  // Inicializar componentes gráficos
        LOGGER.info("VentanaAdministrador creada con nivelAdmin=" + nivelAdmin);  // Log de creación
    }
    // -------------------------------------------------------------

    // --------------- MÉTODO PARA MOSTRAR VENTANA -----------------
    public static void mostrarVentana(int nivelAdmin) {
        SwingUtilities.invokeLater(() -> {  // Ejecutar en el hilo de Swing
            if (nivelAdmin < 1 || nivelAdmin > 3) {
                mostrarError(null, "Nivel de administrador inválido (debe ser 1, 2 o 3).");
                LOGGER.warning("Intento de abrir VentanaAdministrador con nivel inválido: " + nivelAdmin);
                return;
            }
            LOGGER.info("Mostrando VentanaAdministrador con nivelAdmin=" + nivelAdmin);
            new VentanaAdministrador(nivelAdmin).setVisible(true);
        });
    }
    // -------------------------------------------------------------

    // ----------------- MÉTODO PARA MOSTRAR ERROR -----------------
    private static void mostrarError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, "ERROR: " + mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    // -------------------------------------------------------------

    // -------------------- CONFIGURAR VENTANA ---------------------
    private void configurarVentana() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Solo cerrar esta ventana
        setSize(600, 400);  // Tamaño de la ventana
        setLocationRelativeTo(null);  // Centrar en pantalla
        setResizable(false);  // No permitir redimensionar
    }
    // -------------------------------------------------------------

    // ------------- INICIALIZAR INTERFAZ GRÁFICA -------------------
    private void inicializarUI() {
        // Panel principal con layout GridBagLayout y fondo oscuro
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(34, 40, 49));
        add(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título principal
        JLabel titulo = new JLabel("¡Bienvenido al Modo Administrador!", SwingConstants.CENTER);
        titulo.setForeground(new Color(240, 248, 255));
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        fondo.add(titulo, gbc);

        // Panel para menú de botones (layout vertical)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(34, 40, 49));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón Registro de Jugadores
        menuPanel.add(crearBoton("Registro de Jugadores", () -> {
            LOGGER.info("Botón 'Registro de Jugadores' pulsado.");
            JOptionPane.showMessageDialog(this, "Acción para registrar jugadores.");
        }));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Separador vertical

        // Agregar botones específicos según el nivel del administrador
        agregarBotonesPorNivel(menuPanel);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        fondo.add(menuPanel, gbc);

        // Botón Volver (cierra ventana y muestra menú desplegable)
        JButton btnVolver = crearBoton("Volver", () -> {
            LOGGER.info("Botón 'Volver' pulsado. Cerrando ventana y mostrando MenuDesplegable.");
            MenuDesplegable.mostrarVentana();
            dispose();
        });
        gbc.gridy = 2;
        fondo.add(btnVolver, gbc);
    }
    // -------------------------------------------------------------

    // ---------- AGREGAR BOTONES SEGÚN NIVEL DE ADMIN -------------

    private void agregarBotonesPorNivel(JPanel panel) {
        switch (nivelAdmin) {
            case 1 -> {
                panel.add(crearBoton("Nivel 1: Máxima autoridad", () -> {
                    LOGGER.info("Botón 'Nivel 1: Máxima autoridad' pulsado.");
                    JOptionPane.showMessageDialog(this, "Acción de Nivel 1: Máxima autoridad");
                }));
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            case 2 -> {
                panel.add(crearBoton("Nivel 2: Copias de seguridad y restauraciones", () -> {
                    LOGGER.info("Botón 'Nivel 2: Copias de seguridad y restauraciones' pulsado.");
                    JOptionPane.showMessageDialog(this, "Acción de Nivel 2: Copias y restauraciones");
                }));
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            case 3 -> {
                panel.add(crearBoton("Nivel 3: Solo copias de seguridad", () -> {
                    LOGGER.info("Botón 'Nivel 3: Solo copias de seguridad' pulsado.");
                    JOptionPane.showMessageDialog(this, "Acción de Nivel 3: Solo copias de seguridad");
                }));
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
    }
    // -------------------------------------------------------------

    // ------------- MÉTODO AUXILIAR PARA CREAR BOTONES -------------
    private JButton crearBoton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(100, 149, 237));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(250, 40));
        boton.addActionListener(e -> accion.run());
        return boton;
    }
    // -------------------------------------------------------------

}
