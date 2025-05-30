package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.*;

public class LoginAdministrador extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    private static final Logger LOGGER = Logger.getLogger(LoginAdministrador.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);
            FileHandler fh = new FileHandler("LoginAdministrador.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LoginAdministrador() {
        super("Inicio de Sesión - Administrador");
        configurarVentana();
        inicializarUI();
        LOGGER.info("Ventana LoginAdministrador creada.");
    }

    // Método para mostrar esta ventana (login)
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            new LoginAdministrador().setVisible(true);
            LOGGER.info("Ventana LoginAdministrador mostrada.");
        });
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Etiqueta Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel etiquetaUsuario = new JLabel("Usuario:");
        etiquetaUsuario.setForeground(Color.WHITE);
        panel.add(etiquetaUsuario, gbc);

        // Campo Usuario
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        campoUsuario = new JTextField(15);
        panel.add(campoUsuario, gbc);

        // Etiqueta Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setForeground(Color.WHITE);
        panel.add(etiquetaContrasena, gbc);

        // Campo Contraseña
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        campoContrasena = new JPasswordField(15);
        panel.add(campoContrasena, gbc);

        // Botón Iniciar Sesión
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnIniciar = new JButton("Iniciar Sesión");
        panel.add(btnIniciar, gbc);

        btnIniciar.addActionListener(e -> validarLogin());
    }

    private void validarLogin() {
        String usuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        LOGGER.info("Intento de login con usuario: " + usuario);

        if (usuario.equals("administrador") && contrasena.equals("administrador1")) {
            LOGGER.info("Login exitoso para usuario: " + usuario + " con nivel 1");
            abrirVentanaAdmin(1);
        } else if (usuario.equals("administrador") && contrasena.equals("administrador2")) {
            LOGGER.info("Login exitoso para usuario: " + usuario + " con nivel 2");
            abrirVentanaAdmin(2);
        } else if (usuario.equals("administrador") && contrasena.equals("administrador3")) {
            LOGGER.info("Login exitoso para usuario: " + usuario + " con nivel 3");
            abrirVentanaAdmin(3);
        } else {
            LOGGER.warning("Login fallido para usuario: " + usuario);
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVentanaAdmin(int nivel) {
        VentanaAdministrador ventanaAdmin = new VentanaAdministrador(nivel);
        ventanaAdmin.setVisible(true);
        this.dispose();
    }
}