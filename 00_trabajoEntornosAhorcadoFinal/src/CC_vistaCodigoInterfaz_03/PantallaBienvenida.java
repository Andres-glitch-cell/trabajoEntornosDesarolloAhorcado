package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.*;

public class PantallaBienvenida extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(PantallaBienvenida.class.getName());

    static {
        try {
            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            FileHandler fh = new FileHandler("ahorcado.log", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            LOGGER.addHandler(fh);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            ch.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(ch);

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

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new PantallaBienvenida().setVisible(true));
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
        panel.add(crearBoton("Iniciar Juego", new Color(100, 149, 237)));
        panel.add(crearBoton("Iniciar Sesión", new Color(100, 149, 237)));
        panel.add(crearBoton("Registrarse", new Color(100, 149, 237)));
        return panel;
    }

    private JButton crearBotonSalir() {
        return crearBoton("Salir del Juego", new Color(255, 69, 0));
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.addActionListener(this::accionBoton);
        return boton;
    }

    private void accionBoton(ActionEvent e) {
        String comando = e.getActionCommand();
        LOGGER.info("Botón presionado: " + comando);
        switch (comando) {
            case "Salir del Juego" -> confirmarSalida();
            case "Iniciar Juego" -> {
                dispose();
                PantallaAhorcado.mostrarVentana("TEST");
            }
            case "Iniciar Sesión" -> abrirPantalla("IniciarSesion");
            case "Registrarse" -> abrirPantalla("Registrarse");
            case "Opciones del Juego" -> abrirPantalla("OpcionesJuego");
            default -> {
                LOGGER.warning("Acción no reconocida: " + comando);
                JOptionPane.showMessageDialog(this, "Funcionalidad no implementada para: " + comando,
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres salir?", "Salir del Juego",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            LOGGER.info("Cerrando aplicación...");
            System.exit(0);
        } else {
            LOGGER.info("Salida cancelada.");
        }
    }

    private void abrirPantalla(String nombreClase) {
        String mensajeError = "No se pudo abrir " + nombreClase + ".";
        dispose();

        switch (nombreClase) {
            case "IniciarSesion":
                CC_vistaCodigoInterfaz_03.IniciarSesion.mostrarVentana();
                LOGGER.info(nombreClase + " abierta correctamente.");
                break;
            case "Registrarse":
                CC_vistaCodigoInterfaz_03.Registrarse.mostrarVentana();
                LOGGER.info(nombreClase + " abierta correctamente.");
                break;
            // Agrega más casos según las pantallas que tengas
            default:
                LOGGER.warning("Pantalla desconocida: " + nombreClase);
                JOptionPane.showMessageDialog(this, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

}