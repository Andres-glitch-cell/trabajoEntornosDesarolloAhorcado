package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registrarse extends JFrame {

    private static final String URL_BBDD = "jdbc:mysql://localhost/ahorcadoJuegoEntornosJesus?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO_BBDD = "root";
    private static final String CONTRASEÑA_BBDD = "abcd1234";

    private JTextField campoNombreCompleto;
    private JTextField campoUsuario;
    private JTextField campoCorreo;
    private JPasswordField campoContraseña;

    public Registrarse() {
        super("Registrarse");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelNombreCompleto = new JLabel("Nombre Completo:");
        labelNombreCompleto.setForeground(Color.WHITE);
        labelNombreCompleto.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoNombreCompleto = new JTextField(20);
        campoNombreCompleto.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel labelUsuario = new JLabel("Nombre de Usuario:");
        labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoUsuario = new JTextField(20);
        campoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel labelCorreo = new JLabel("Correo Electrónico:");
        labelCorreo.setForeground(Color.WHITE);
        labelCorreo.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoCorreo = new JTextField(20);
        campoCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel labelContraseña = new JLabel("Contraseña:");
        labelContraseña.setForeground(Color.WHITE);
        labelContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoContraseña = new JPasswordField(20);
        campoContraseña.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Labels - columna 0
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridy = 0;
        panel.add(labelNombreCompleto, gbc);

        gbc.gridy = 1;
        panel.add(labelUsuario, gbc);

        gbc.gridy = 2;
        panel.add(labelCorreo, gbc);

        gbc.gridy = 3;
        panel.add(labelContraseña, gbc);

        // Campos - columna 1
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridy = 0;
        panel.add(campoNombreCompleto, gbc);

        gbc.gridy = 1;
        panel.add(campoUsuario, gbc);

        gbc.gridy = 2;
        panel.add(campoCorreo, gbc);

        gbc.gridy = 3;
        panel.add(campoContraseña, gbc);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(100, 149, 237));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegistrar.addActionListener(e -> registrarUsuario());

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 99, 71));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVolver.addActionListener(e -> {
            PantallaBienvenida.mostrarVentana();
            dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(34, 40, 49));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);
    }

    private void registrarUsuario() {
        String nombreCompleto = campoNombreCompleto.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String correo = campoCorreo.getText().trim();
        String contraseña = new String(campoContraseña.getPassword());

        if (nombreCompleto.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (registrarUsuarioEnBBDD(nombreCompleto, usuario, correo, contraseña)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            PantallaBienvenida.mostrarVentana();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario. Puede que el correo o usuario ya existan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean registrarUsuarioEnBBDD(String nombreCompleto, String usuario, String correo, String contraseña) {
        String contraseñaHasheada = hashSHA256(contraseña);

        String sql = "INSERT INTO Usuario (nombreCompleto, nombreUsuario, correoElectronico, contraseñaHash, fechaRegistro) VALUES (?, ?, ?, ?, NOW())";

        try (Connection c = DriverManager.getConnection(URL_BBDD, USUARIO_BBDD, CONTRASEÑA_BBDD);
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, nombreCompleto);
            s.setString(2, usuario);
            s.setString(3, correo);
            s.setString(4, contraseñaHasheada);   // Solo el hash

            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            return false;
        }
    }

    // Método para calcular el hash SHA-256
    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No se encontró el algoritmo SHA-256", e);
        }
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new Registrarse().setVisible(true));
    }
}
