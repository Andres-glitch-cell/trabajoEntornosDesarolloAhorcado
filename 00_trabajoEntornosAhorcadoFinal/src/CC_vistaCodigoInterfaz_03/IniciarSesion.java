package CC_vistaCodigoInterfaz_03;

import BB_modeloBBDD_02.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IniciarSesion extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    public IniciarSesion() {
        super("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        inicializarInterfaz();
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new IniciarSesion().setVisible(true));
    }

    private void inicializarInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel etiquetaUsuario = new JLabel("Nombre de Usuario o Correo:");
        etiquetaUsuario.setForeground(Color.WHITE);
        etiquetaUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoUsuario = new JTextField(20);
        campoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setForeground(Color.WHITE);
        etiquetaContrasena.setFont(new Font("SansSerif", Font.BOLD, 14));
        campoContrasena = new JPasswordField(20);
        campoContrasena.setFont(new Font("SansSerif", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(etiquetaUsuario, gbc);

        gbc.gridy = 1;
        panel.add(etiquetaContrasena, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(campoUsuario, gbc);

        gbc.gridy = 1;
        panel.add(campoContrasena, gbc);

        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(new Color(100, 149, 237));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIniciarSesion.addActionListener(e -> procesarInicioSesion());

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(220, 20, 60));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVolver.addActionListener(e -> {
            dispose();
            PantallaBienvenida.mostrarVentana();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(34, 40, 49));
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);
    }

    private void procesarInicioSesion() {
        String usuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if ((usuario.equals("administrador1@gmail.com") || usuario.equals("administrador1")) && contrasena.equals("administrador1")) {
                mostrarMenuAdministradorCompleto();
            } else if ((usuario.equals("administrador2@gmail.com") || usuario.equals("administrador2")) && contrasena.equals("administrador2")) {
                mostrarMenuBackupYRestauracion();
            } else if ((usuario.equals("administrador3@gmail.com") || usuario.equals("administrador3")) && contrasena.equals("administrador3")) {
                mostrarMenuSoloBackup();
            } else if (validarCredenciales(usuario, contrasena)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // En vez de cerrar y abrir juego directamente:
                dispose();

                // Mostrar ventana modal de opciones de juego, pasándole esta ventana como parent (ya cerrada)
                PantallaOpcionesJuego opciones = new PantallaOpcionesJuego(null);
                opciones.setVisible(true);
                // Cuando opciones se cierre, se lanza el juego (esto ya está en PantallaOpcionesJuego)
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al iniciar sesión: " + e.getMessage());
        }
    }

    private boolean validarCredenciales(String usuario, String contrasena) throws SQLException, NoSuchAlgorithmException {
        String sql = "SELECT contraseñaHash FROM usuario WHERE nombre = ? OR correo = ?";
        try (Connection conexion = ConexionBaseDatos.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashBD = rs.getString("contraseñaHash");
                    String hashIngresado = hashContrasena(contrasena);
                    return hashIngresado != null && hashIngresado.equals(hashBD);
                } else {
                    return false;
                }
            }
        }
    }

    private String hashContrasena(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(contrasena.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private void mostrarMenuAdministradorCompleto() {
        JFrame ventana = crearVentana("Menú Administrador Completo", new String[]{
                "Añadir Palabras", "Eliminar Palabras", "Añadir Usuarios", "Eliminar Usuarios", "Copia de Seguridad y Restauración"
        });
        ventana.setVisible(true);
        dispose();
    }

    private void mostrarMenuBackupYRestauracion() {
        JFrame ventana = crearVentana("Menú Backup y Restauración", new String[]{
                "Copia de Seguridad", "Restauración"
        });
        ventana.setVisible(true);
        dispose();
    }

    private void mostrarMenuSoloBackup() {
        JFrame ventana = crearVentana("Menú Copia de Seguridad", new String[]{
                "Copia de Seguridad"
        });
        ventana.setVisible(true);
        dispose();
    }

    private JFrame crearVentana(String titulo, String[] botones) {
        JFrame ventana = new JFrame(titulo);
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        for (int i = 0; i < botones.length; i++) {
            JButton boton = new JButton(botones[i]);
            boton.setBackground(new Color(100, 149, 237));
            boton.setForeground(Color.WHITE);
            boton.setFont(new Font("SansSerif", Font.BOLD, 14));
            gbc.gridy = i;
            panel.add(boton, gbc);
        }

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(220, 20, 60));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVolver.addActionListener(e -> {
            ventana.dispose();
            mostrarVentana();
        });
        gbc.gridy = botones.length;
        panel.add(btnVolver, gbc);

        ventana.add(panel);
        return ventana;
    }
}