package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AA_Registrarse extends JFrame {

    private static final String URL_BBDD = "jdbc:mysql://localhost/AhorcadoAndres?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO_BBDD = "root";
    private static final String CLAVE_BBDD = "abcd1234";

    public AA_Registrarse() {
        super("Registrarse");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // CORREGIDO: Cierra solo esta ventana
        setSize(550, 300);
        setResizable(true);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(34, 40, 49));
        add(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelUsuario = crearLabel("Nombre del Usuario:");
        JTextField campoUsuario = crearCampoTexto();

        JLabel labelPassword = crearLabel("Nueva Contraseña:");
        JPasswordField campoPassword = crearCampoPassword();

        JLabel labelConfirmar = crearLabel("Confirmar Contraseña:");
        JPasswordField campoConfirmar = crearCampoPassword();

        gbc.gridx = 0; gbc.gridy = 0; fondo.add(labelUsuario, gbc);
        gbc.gridx = 1; fondo.add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; fondo.add(labelPassword, gbc);
        gbc.gridx = 1; fondo.add(campoPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; fondo.add(labelConfirmar, gbc);
        gbc.gridx = 1; fondo.add(campoConfirmar, gbc);

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.addActionListener(e -> registrarUsuario(campoUsuario.getText(), campoPassword.getPassword(), campoConfirmar.getPassword()));

        // Botón para volver a la pantalla de bienvenida
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            // Al hacer clic en "Volver", se cierra esta ventana y se muestra la ventana de bienvenida
            new AAA_PantallaBienvenida().setVisible(true);  // Muestra la ventana de bienvenida
            dispose();  // Cierra la ventana actual (de registro)
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(34, 40, 49));
        panelBotones.add(btnRegistrarse);
        panelBotones.add(btnVolver);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        fondo.add(panelBotones, gbc);
    }

    private void registrarUsuario(String nombreUsuario, char[] contraseña, char[] confirmar) {
        String pass = new String(contraseña);
        String confirm = new String(confirmar);

        if (nombreUsuario.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            mostrarError("⚠ Por favor, completa todos los campos.");
            return;
        }

        if (!pass.equals(confirm)) {
            mostrarError("⚠ Las contraseñas no coinciden.");
            return;
        }

        if (usuarioExistente(nombreUsuario)) {
            mostrarError("⚠ El nombre de usuario ya existe.");
            return;
        }

        if (registrarUsuarioEnBBDD(nombreUsuario, pass)) {
            JOptionPane.showMessageDialog(this, "✅ Usuario registrado correctamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cierra la ventana tras registrar
        }
    }

    private boolean registrarUsuarioEnBBDD(String usuario, String contraseña) {
        String sql = "INSERT INTO Usuarios (nombre, contraseña) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL_BBDD, USUARIO_BBDD, CLAVE_BBDD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, hashContraseña(contraseña));
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("❌ Error en BBDD: " + e.getMessage());
            return false;
        }
    }

    private boolean usuarioExistente(String usuario) {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(URL_BBDD, USUARIO_BBDD, CLAVE_BBDD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            mostrarError("❌ Error al verificar usuario: " + e.getMessage());
            return true;
        }
    }

    private String hashContraseña(String contraseña) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(contraseña.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            mostrarError("❌ Error al hashear contraseña: " + e.getMessage());
            return "";
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(new Color(240, 248, 255));
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField(15);
        campo.setBackground(new Color(50, 60, 70));
        campo.setForeground(Color.WHITE);
        return campo;
    }

    private JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField(15);
        campo.setBackground(new Color(50, 60, 70));
        campo.setForeground(Color.WHITE);
        return campo;
    }

    // Método estático limpio para mostrar la ventana desde cualquier otra clase
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new AA_Registrarse().setVisible(true));
    }
}