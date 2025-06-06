package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class LoginAdministrador extends JFrame {

    private static final String[] USUARIOS_VALIDOS = {
            "administrador1", "administrador1@gmail.com",
            "administrador2", "administrador2@gmail.com",
            "administrador3", "administrador3@gmail.com"
    };

    private final JTextField campoUsuario = new JTextField(15);
    private final JPasswordField campoContrasena = new JPasswordField(15);

    public LoginAdministrador() {
        super("Inicio de Sesión - Administrador");
        configurarVentana();
        inicializarInterfaz();
        RegistroDeEventos.registrarInfo("Ventana LoginAdministrador creada.");
    }

    private void configurarVentana() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        agregarCampo(panel, gbc, 0, "Usuario:", campoUsuario);
        agregarCampo(panel, gbc, 1, "Contraseña:", campoContrasena);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton botonIniciar = new JButton("Iniciar Sesión");
        panel.add(botonIniciar, gbc);
        botonIniciar.addActionListener(e -> validarLogin());
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setForeground(Color.WHITE);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campo, gbc);
    }

    private void validarLogin() {
        String usuario = campoUsuario.getText().trim().toLowerCase();
        String contrasena = new String(campoContrasena.getPassword()).trim().toLowerCase();

        RegistroDeEventos.registrarInfo("Intento de inicio de sesión con usuario: " + usuario);

        if (!esUsuarioValido(usuario)) {
            falloLogin("Usuario no autorizado: " + usuario);
            return;
        }

        if (!esContrasenaValida(usuario, contrasena)) {
            falloLogin("Contraseña incorrecta para usuario: " + usuario);
            return;
        }

        int nivel = obtenerNivel(usuario);
        abrirVentanaAdministrador(nivel);
    }

    private boolean esUsuarioValido(String usuario) {
        for (String u : USUARIOS_VALIDOS) {
            if (usuario.equals(u)) {
                return true;
            }
        }
        return false;
    }

    private boolean esContrasenaValida(String usuario, String contrasena) {
        return contrasena.equals(usuario);
    }

    private void falloLogin(String mensajeError) {
        RegistroDeEventos.registrarError(mensajeError);
        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
    }

    private int obtenerNivel(String usuario) {
        if (usuario.contains("1")) return 1;
        if (usuario.contains("2")) return 2;
        if (usuario.contains("3")) return 3;
        return 0;
    }

    private void abrirVentanaAdministrador(int nivel) {
        RegistroDeEventos.registrarInfo("Inicio de sesión exitoso para usuario: administrador con nivel " + nivel);
        new VentanaAdministrador(nivel).setVisible(true);
        dispose();
    }
}
