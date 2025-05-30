package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.*;

public class VentanaAdministrador extends JFrame {

    private final int nivelAdmin; // Debe inicializarse en constructor

    private static final Logger LOGGER = Logger.getLogger(VentanaAdministrador.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);
            FileHandler fh = new FileHandler("VentanaAdministrador.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public VentanaAdministrador(int nivelAdmin) {
        super("Modo Administrador");
        this.nivelAdmin = nivelAdmin;
        configurarVentana();
        inicializarUI();
        LOGGER.info("VentanaAdministrador creada con nivelAdmin=" + nivelAdmin);
    }

    public static void mostrarVentana(int nivelAdmin) {
        SwingUtilities.invokeLater(() -> {
            if (nivelAdmin < 1 || nivelAdmin > 3) {
                JOptionPane.showMessageDialog(null, "Nivel de administrador inválido (debe ser 1, 2 o 3).", "Error", JOptionPane.ERROR_MESSAGE);
                LOGGER.warning("Intento de abrir VentanaAdministrador con nivel inválido: " + nivelAdmin);
                return;
            }
            LOGGER.info("Mostrando VentanaAdministrador con nivelAdmin=" + nivelAdmin);
            new VentanaAdministrador(nivelAdmin).setVisible(true);
        });
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarUI() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(34, 40, 49));
        add(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("¡Bienvenido al Modo Administrador!", SwingConstants.CENTER);
        titulo.setForeground(new Color(240, 248, 255));
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        fondo.add(titulo, gbc);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(34, 40, 49));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegistroJugadores = crearBoton("Registro de Jugadores", () -> {
            LOGGER.info("Botón 'Registro de Jugadores' pulsado.");
            JOptionPane.showMessageDialog(VentanaAdministrador.this, "Acción para registrar jugadores.");
        });
        menuPanel.add(btnRegistroJugadores);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if (nivelAdmin == 1) {
            JButton btnAdminNivel1 = crearBoton("Nivel 1: Máxima autoridad", () -> {
                LOGGER.info("Botón 'Nivel 1: Máxima autoridad' pulsado.");
                JOptionPane.showMessageDialog(VentanaAdministrador.this, "Acción de Nivel 1: Máxima autoridad");
            });
            menuPanel.add(btnAdminNivel1);
        } else if (nivelAdmin == 2) {
            JButton btnAdminNivel2 = crearBoton("Nivel 2: Copias de seguridad y restauraciones", () -> {
                LOGGER.info("Botón 'Nivel 2: Copias de seguridad y restauraciones' pulsado.");
                JOptionPane.showMessageDialog(VentanaAdministrador.this, "Acción de Nivel 2: Copias de seguridad y restauraciones");
            });
            menuPanel.add(btnAdminNivel2);
        } else if (nivelAdmin == 3) {
            JButton btnAdminNivel3 = crearBoton("Nivel 3: Solo copias de seguridad", () -> {
                LOGGER.info("Botón 'Nivel 3: Solo copias de seguridad' pulsado.");
                JOptionPane.showMessageDialog(VentanaAdministrador.this, "Acción de Nivel 3: Solo copias de seguridad");
            });
            menuPanel.add(btnAdminNivel3);
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        fondo.add(menuPanel, gbc);

        JButton btnVolver = crearBoton("Volver", () -> {
            LOGGER.info("Botón 'Volver' pulsado. Cerrando VentanaAdministrador y mostrando MenuDesplegable.");
            MenuDesplegable.mostrarVentana();
            VentanaAdministrador.this.dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        fondo.add(btnVolver, gbc);
    }

    private JButton crearBoton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(100, 149, 237));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(250, 40));
        boton.addActionListener(e -> accion.run());
        return boton;
    }
}
