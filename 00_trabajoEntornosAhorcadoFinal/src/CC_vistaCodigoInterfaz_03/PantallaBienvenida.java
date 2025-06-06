package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PantallaBienvenida extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(PantallaBienvenida.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler("ahorcado.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
            LOGGER.setUseParentHandlers(false);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("No se pudo inicializar el logger: " + e.getMessage());
        }
    }

    public PantallaBienvenida() {
        super("Bienvenida al Juego del Ahorcado");
        LOGGER.info("Inicializando PantallaBienvenida...");
        configurarVentana();
        add(crearPanelPrincipal());
        LOGGER.info("PantallaBienvenida inicializada correctamente.");
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 340);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(34, 40, 49));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(crearTitulo());
        panel.add(Box.createVerticalStrut(20));
        panel.add(crearPanelBotonesPrincipales());
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearBotonOpciones());
        panel.add(Box.createVerticalStrut(20));
        panel.add(crearBotonSalir());

        return panel;
    }

    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("¡Bienvenido al Juego del Ahorcado!", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titulo;
    }

    private JPanel crearPanelBotonesPrincipales() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setOpaque(false);

        JButton btnIniciar = crearBoton("Iniciar Juego", new Color(100, 149, 237), this::accionBoton);
        JButton btnSesion = crearBoton("Iniciar Sesión", new Color(100, 149, 237), this::accionBoton);
        JButton btnRegistro = crearBoton("Registrarse", new Color(100, 149, 237), this::accionBoton);

        panel.add(btnIniciar);
        panel.add(btnSesion);
        panel.add(btnRegistro);

        return panel;
    }

    private JButton crearBotonOpciones() {
        return crearBoton("Opciones del Juego", new Color(255, 165, 0), this::accionBoton);
    }

    private JButton crearBotonSalir() {
        return crearBoton("Salir del Juego", new Color(255, 69, 0), this::accionBoton);
    }

    private JButton crearBoton(String texto, Color colorFondo, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setActionCommand(texto);
        boton.addActionListener(listener);
        return boton;
    }

    private void accionBoton(java.awt.event.ActionEvent e) {
        String comando = e.getActionCommand();
        LOGGER.info("Botón presionado: " + comando);

        switch (comando) {
            case "Salir del Juego":
                confirmarSalida();
                break;
            case "Iniciar Juego":
                mostrarOpcionesJuego();
                break;
            case "Iniciar Sesión":
                abrirPantalla("IniciarSesion");
                break;
            case "Registrarse":
                abrirPantalla("Registrarse");
                break;
            default:
                LOGGER.warning("Acción no reconocida: " + comando);
                break;
        }
    }

    private void mostrarOpcionesJuego() {
        PantallaOpcionesJuego opciones = new PantallaOpcionesJuego(this);
        opciones.setVisible(true);
    }

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres salir?", "Salir del Juego",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            LOGGER.info("Cerrando aplicación...");
            System.exit(0);
        } else {
            LOGGER.info("Cancelada salida.");
        }
    }

    private void abrirPantalla(String nombreClase) {
        try {
            Class<?> clase = Class.forName("CC_vistaCodigoInterfaz_03." + nombreClase);
            clase.getMethod("mostrarVentana").invoke(null);
            dispose();
            LOGGER.info(nombreClase + " abierta correctamente.");
        } catch (Exception e) {
            LOGGER.warning("Error abriendo " + nombreClase + ": " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "❌ No se puede abrir " + nombreClase + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new PantallaBienvenida().setVisible(true));
    }
}
