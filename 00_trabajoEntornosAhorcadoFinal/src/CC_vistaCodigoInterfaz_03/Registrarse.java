package CC_vistaCodigoInterfaz_03;

import BB_modeloBBDD_02.ConexionBaseDatos;
import DD_controladorCodigoIntermediario_04.Hash;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Registrarse extends JFrame {

    private static final Color COLOR_FONDO = new Color(34, 40, 49);
    private static final Font FUENTE_LABEL = new Font("SansSerif", Font.BOLD, 14);

    private JTextField campoNombreCompleto, campoUsuario, campoCorreo;
    private JPasswordField campoContraseña, campoContraseñaRepetida;

    public Registrarse() {
        super("Registrarse");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        initUI();
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new Registrarse().setVisible(true));
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        String[] etiquetas = {
                "Nombre Completo:", "Nombre de Usuario:", "Correo Electrónico:",
                "Contraseña:", "Repite Contraseña:"
        };

        JLabel[] labels = new JLabel[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            labels[i] = new JLabel(etiquetas[i]);
            labels[i].setForeground(Color.WHITE);
            labels[i].setFont(FUENTE_LABEL);
        }

        campoNombreCompleto = new JTextField(30);
        campoUsuario = new JTextField(30);
        campoCorreo = new JTextField(30);
        campoContraseña = new JPasswordField(30);
        campoContraseñaRepetida = new JPasswordField(30);

        agregarFocusListener(campoNombreCompleto, "Nombre Completo");
        agregarFocusListener(campoUsuario, "Usuario");
        agregarFocusListener(campoCorreo, "Correo");
        agregarFocusListener(campoContraseña, "Contraseña");
        agregarFocusListener(campoContraseñaRepetida, "Repetir Contraseña");

        JTextField[] campos = {
                campoNombreCompleto, campoUsuario, campoCorreo,
                campoContraseña, campoContraseñaRepetida
        };

        for (JTextField c : campos) c.setPreferredSize(new Dimension(350, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        for (JLabel label : labels) {
            panel.add(label, gbc);
            gbc.gridy++;
        }

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        for (JTextField campo : campos) {
            panel.add(campo, gbc);
            gbc.gridy++;
        }

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(100, 149, 237));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(e -> registrarUsuario());

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 99, 71));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> {
            PantallaBienvenida.mostrarVentana();
            dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
    }

    private void registrarUsuario() {
        String nombreCompleto = campoNombreCompleto.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String correo = campoCorreo.getText().trim();
        String contraseña = new String(campoContraseña.getPassword());
        String contraseñaRepetida = new String(campoContraseñaRepetida.getPassword());

        // Validamos los campos para evitar errores o datos vacíos
        String error = validarCampos(nombreCompleto, usuario, correo, contraseña, contraseñaRepetida);
        if (error != null) {
            mostrarError(error);
            return;
        }

        // *** Aquí usamos la clase Hash para obtener el hash SHA-256 de la contraseña ***
        // Esto es importante para no guardar la contraseña en texto plano en la base de datos,
        // sino una versión cifrada que no se puede revertir.
        String contraseñaHash = Hash.sha256(contraseña);
        if (contraseñaHash == null) {
            mostrarError("Error al generar hash de la contraseña.");
            return;
        }

        // Registramos el usuario en la base de datos usando el hash en lugar de la contraseña original
        if (registrarUsuarioEnBBDD(nombreCompleto, usuario, correo, contraseñaHash)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("[LOG] Registro exitoso del usuario: " + usuario);
            PantallaBienvenida.mostrarVentana();
            dispose();
        } else {
            mostrarError("Error al registrar el usuario. Puede que el nombre de usuario ya exista.");
            System.out.println("[LOG] Error al registrar usuario: " + usuario);
        }
    }

    private boolean registrarUsuarioEnBBDD(String nombreCompleto, String usuario, String correo, String contraseñaHash) {
        String sql = "INSERT INTO Usuario (nombreCompleto, nombre, correo, contraseñaHash, fechaCreacion, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = ConexionBaseDatos.getConexion();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, nombreCompleto);
            s.setString(2, usuario);
            s.setString(3, correo);
            // Guardamos la contraseña como hash para mayor seguridad
            s.setString(4, contraseñaHash);
            s.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            s.setInt(6, 1); // Usuario activo
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void agregarFocusListener(JTextField campo, String nombreCampo) {
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!campo.getText().trim().isEmpty()) {
                    imprimirConsola("Campo " + nombreCampo + " completado");
                }
            }
        });
    }

    private void imprimirConsola(String mensaje) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_BOLD = "\u001B[1m";

        System.out.println(ANSI_CYAN + ANSI_BOLD + "[INFO] " + ANSI_GREEN + mensaje + ANSI_RESET);
    }

    // Método que valida los campos del formulario
    private String validarCampos(String nombreCompleto, String usuario, String correo, String contraseña, String contraseñaRepetida) {
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            return "El nombre completo es obligatorio.";
        }
        if (usuario == null || usuario.isEmpty()) {
            return "El nombre de usuario es obligatorio.";
        }
        if (correo == null || correo.isEmpty() || !correo.contains("@")) {
            return "Debe ingresar un correo electrónico válido.";
        }
        if (contraseña == null || contraseña.isEmpty()) {
            return "La contraseña es obligatoria.";
        }
        if (!contraseña.equals(contraseñaRepetida)) {
            return "Las contraseñas no coinciden.";
        }
        // Puedes agregar más validaciones aquí (longitud mínima, caracteres especiales, etc.)
        return null; // Todo bien
    }
}