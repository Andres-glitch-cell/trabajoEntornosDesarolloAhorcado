package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;


public class IniciarSesion extends JFrame {

    private static final String URL_BBDD = "jdbc:mysql://localhost/ahorcadoJuegoEntornosJesus?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO_BBDD = "root";
    private static final String CONTRASEÑA_BBDD = "abcd1234";

    private JTextField campoUsuario;
    private JPasswordField campoContraseña;

    public IniciarSesion() {
        super("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);  // Más ancho y alto
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

        JLabel labelUsuario = new JLabel("Nombre de Usuario:");
        labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoUsuario = new JTextField(30);
        campoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel labelContraseña = new JLabel("Contraseña:");
        labelContraseña.setForeground(Color.WHITE);
        labelContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoContraseña = new JPasswordField(30);
        campoContraseña.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Etiquetas - columna 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0;   // No ocupa espacio extra
        gbc.fill = GridBagConstraints.NONE;
        panel.add(labelUsuario, gbc);

        gbc.gridy = 1;
        panel.add(labelContraseña, gbc);

        // Campos - columna 1
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;                  // Para que ocupe espacio horizontal
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Para que se expanda horizontalmente
        panel.add(campoUsuario, gbc);

        gbc.gridy = 1;
        panel.add(campoContraseña, gbc);

        JButton btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setBackground(new Color(100, 149, 237));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIniciar.addActionListener(e -> iniciarSesion());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(34, 40, 49));
        panelBoton.add(btnIniciar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(panelBoton, gbc);
    }

    private void iniciarSesion() {
        String usuario = campoUsuario.getText().trim();
        String contraseña = new String(campoContraseña.getPassword());

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (validarCredenciales(usuario, contraseña)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Aquí puedes abrir la ventana principal o lo que corresponda
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCredenciales(String usuario, String contraseña) {
        System.out.println("Intentando login con usuario: " + usuario);
        String sql = "SELECT contraseñaHash FROM Usuario WHERE nombreUsuario = ?";

        try (Connection c = DriverManager.getConnection(URL_BBDD, USUARIO_BBDD, CONTRASEÑA_BBDD); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, usuario);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    String contraseñaHashBD = rs.getString("contraseñaHash");
                    System.out.println("Hash almacenado en BD: " + contraseñaHashBD);
                    String hashIngresado = hashSHA256(contraseña);
                    System.out.println("Hash ingresado: " + hashIngresado);
                    return contraseñaHashBD.equals(hashIngresado);
                } else {
                    System.out.println("Usuario no encontrado");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            return false;
        }
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new IniciarSesion().setVisible(true));
    }
}
