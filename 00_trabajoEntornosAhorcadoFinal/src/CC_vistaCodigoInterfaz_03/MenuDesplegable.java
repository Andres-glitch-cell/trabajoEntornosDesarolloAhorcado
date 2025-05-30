package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.*;

public class MenuDesplegable extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(MenuDesplegable.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);
            FileHandler fh = new FileHandler("MenuDesplegable.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public MenuDesplegable() {
        super("Menu Desplegable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        initUI();
        LOGGER.info("Ventana MenuDesplegable inicializada");
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            new MenuDesplegable().setVisible(true);
            LOGGER.info("Ventana MenuDesplegable visible");
        });
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        JMenu menuBarra = new JMenu("JMenuBar");
        barraMenu.add(menuBarra);

        JMenu menuJugador = new JMenu("Jugador");
        JMenuItem abrirJuego = new JMenuItem("Abrir Juego", KeyEvent.VK_J);
        abrirJuego.addActionListener(e -> {
            LOGGER.info("Seleccionado 'Abrir Juego'");
            PantallaAhorcado.mostrarVentana();
            dispose();
            LOGGER.info("Ventana MenuDesplegable cerrada tras abrir juego");
        });
        menuJugador.add(abrirJuego);
        menuBarra.add(menuJugador);

        JMenu menuAdministrador = new JMenu("Administrador");
        JMenuItem modoAdmin = new JMenuItem("Modo Administrador", KeyEvent.VK_A);
        modoAdmin.addActionListener(e -> {
            LOGGER.info("Seleccionado 'Modo Administrador'");
            String input = JOptionPane.showInputDialog(this, "Ingresa el nivel de administrador (1-3):", "Nivel de Administrador", JOptionPane.QUESTION_MESSAGE);
            int nivelAdmin = 1; // Valor por defecto
            try {
                if (input != null && !input.isEmpty()) {
                    nivelAdmin = Integer.parseInt(input);
                }
                LOGGER.info("Nivel administrador ingresado: " + nivelAdmin);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nivel inválido, usando 1 por defecto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                LOGGER.warning("Nivel inválido ingresado para administrador: " + input);
            }
            CC_vistaCodigoInterfaz_03.VentanaAdministrador.mostrarVentana(nivelAdmin);
            dispose();
            LOGGER.info("Ventana MenuDesplegable cerrada tras abrir administrador");
        });
        menuAdministrador.add(modoAdmin);
        menuBarra.add(menuAdministrador);
    }

    public static void main(String[] args) {
        mostrarVentana();
    }
}
