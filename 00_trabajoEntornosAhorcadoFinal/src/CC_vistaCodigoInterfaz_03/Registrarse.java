package CC_vistaCodigoInterfaz_03;

import BB_modeloBBDD_02.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Registrarse extends JFrame {

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
        panel.setBackground(new Color(34, 40, 49));
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
            labels[i].setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        campoNombreCompleto = new JTextField(30);
        campoUsuario = new JTextField(30);
        campoCorreo = new JTextField(30);
        campoContraseña = new JPasswordField(30);
        campoContraseñaRepetida = new JPasswordField(30);

        // 🟢 FocusListeners con impresión profesional en consola
        campoNombreCompleto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!campoNombreCompleto.getText().trim().isEmpty()) {
                    imprimirConsola("Campo Nombre Completo completado");
                }
            }
        });

        campoUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!campoUsuario.getText().trim().isEmpty()) {
                    imprimirConsola("Campo Usuario completado");
                }
            }
        });

        campoCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!campoCorreo.getText().trim().isEmpty()) {
                    imprimirConsola("Campo Correo completado");
                }
            }
        });

        campoContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (campoContraseña.getPassword().length > 0) {
                    imprimirConsola("Campo Contraseña completado");
                }
            }
        });

        campoContraseñaRepetida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (campoContraseñaRepetida.getPassword().length > 0) {
                    imprimirConsola("Campo Repetir Contraseña completado");
                }
            }
        });

        Dimension dimCampo = new Dimension(350, 30);
        JTextField[] campos = {
                campoNombreCompleto, campoUsuario, campoCorreo,
                campoContraseña, campoContraseñaRepetida
        };
        for (JTextField c : campos) c.setPreferredSize(dimCampo);

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
        panelBotones.setBackground(new Color(34, 40, 49));
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

        if (nombreCompleto.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || contraseñaRepetida.isEmpty()) {
            mostrarError("Por favor, completa todos los campos.");
            return;
        }

        if (!contraseña.equals(contraseñaRepetida)) {
            mostrarError("Las contraseñas no coinciden.");
            return;
        }

        if (contraseña.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        String contraseñaHash = hashContraseña(contraseña);
        if (contraseñaHash == null) {
            mostrarError("Error al generar hash de la contraseña.");
            return;
        }

        if (registrarUsuarioEnBBDD(nombreCompleto, usuario, correo, contraseñaHash)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            escribirLog("Registro exitoso del usuario: " + usuario);
            PantallaBienvenida.mostrarVentana();
            dispose();
        } else {
            mostrarError("Error al registrar el usuario. Puede que el nombre de usuario ya exista.");
            escribirLog("Error al registrar usuario: " + usuario);
        }
    }

    private boolean registrarUsuarioEnBBDD(String nombreCompleto, String usuario, String correo, String contraseñaHash) {
        String sql = "INSERT INTO Usuario (nombreCompleto, nombre, correo, contraseñaHash, fechaCreacion, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = ConexionBaseDatos.getConexion();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, nombreCompleto);
            s.setString(2, usuario);
            s.setString(3, correo);
            s.setString(4, contraseñaHash);
            s.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            s.setInt(6, 1);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            return false;
        }
    }

    private String hashContraseña(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = contraseña.getBytes(StandardCharsets.UTF_8);
            byte[] hash = md.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al generar hash: " + e.getMessage());
            return null;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void escribirLog(String mensaje) {
        try {
            File dir = new File("LOGS");
            if (!dir.exists()) dir.mkdir();
            File logFile = new File(dir, "registro.log");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                bw.write(fechaHora + " - " + mensaje);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir log: " + e.getMessage());
        }
    }

    // ✅ Método para impresión profesional en consola
    private void imprimirConsola(String mensaje) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_BOLD = "\u001B[1m";

        System.out.println(ANSI_CYAN + ANSI_BOLD + "[INFO] " + ANSI_GREEN + mensaje + ANSI_RESET);
    }
}
