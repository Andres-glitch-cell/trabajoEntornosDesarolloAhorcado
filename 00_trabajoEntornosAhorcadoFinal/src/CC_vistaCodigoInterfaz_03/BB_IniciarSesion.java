package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("NonAsciiCharacters")
public class BB_IniciarSesion extends JFrame {

    public BB_IniciarSesion() {
        super("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel fondoPersonalizado = new JPanel(new GridBagLayout());
        fondoPersonalizado.setBackground(new Color(34, 40, 49));
        add(fondoPersonalizado);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel introducirUsuario = new JLabel("Nombre del Usuario:");
        introducirUsuario.setForeground(new Color(240, 248, 255));
        introducirUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        fondoPersonalizado.add(introducirUsuario, gbc);

        JTextField campoUsuario = new JTextField(15);
        campoUsuario.setBackground(new Color(50, 60, 70));
        campoUsuario.setForeground(Color.WHITE);
        campoUsuario.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        fondoPersonalizado.add(campoUsuario, gbc);

        JLabel introducirContraseña = new JLabel("Contraseña:");
        introducirContraseña.setForeground(new Color(240, 248, 255));
        introducirContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        fondoPersonalizado.add(introducirContraseña, gbc);

        JPasswordField campoContraseña = new JPasswordField(15);
        campoContraseña.setBackground(new Color(50, 60, 70));
        campoContraseña.setForeground(Color.WHITE);
        campoContraseña.setBorder(BorderFactory.createLineBorder(new Color(58, 92, 164), 1));
        gbc.gridx = 1;
        fondoPersonalizado.add(campoContraseña, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(34, 40, 49));

        JButton botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.setBackground(new Color(100, 149, 237));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonIniciarSesion.addActionListener(e -> {
            String nombreUsuario = campoUsuario.getText().trim();
            String contraseña = new String(campoContraseña.getPassword()).trim();

            if (nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(BB_IniciarSesion.this, "⚠ Por favor, completa todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nombreUsuario.equals("administrador") && contraseña.equals("administrador")) {
                FF_VentanaAdministrador.mostrarVentana();  // Cambiado aquí a mostrarVentana()
                dispose();
            } else {
                JOptionPane.showMessageDialog(BB_IniciarSesion.this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelBotones.add(botonIniciarSesion);

        JButton botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setBackground(new Color(100, 149, 237));
        botonRegistrarse.setForeground(Color.WHITE);
        botonRegistrarse.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonRegistrarse.addActionListener(e -> {
            AA_Registrarse.mostrarVentana();
            dispose();
        });
        panelBotones.add(botonRegistrarse);

        // Añadir botón "Volver" para volver a la pantalla de bienvenida
        JButton botonVolver = new JButton("Volver");
        botonVolver.setBackground(new Color(255, 99, 71));  // Un color rojo para diferenciarlo
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonVolver.addActionListener(e -> {
            new AAA_PantallaBienvenida().setVisible(true); // Mostrar la ventana de bienvenida
            dispose();  // Cerrar la ventana actual
        });
        panelBotones.add(botonVolver);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        fondoPersonalizado.add(panelBotones, gbc);

        JCheckBox opcionOlvidarContraseña = new JCheckBox("¿Olvidaste la contraseña?");
        opcionOlvidarContraseña.setBackground(new Color(34, 40, 49));
        opcionOlvidarContraseña.setForeground(new Color(240, 248, 255));
        opcionOlvidarContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(opcionOlvidarContraseña, gbc);

        JCheckBox opcionOlvidarCorreo = new JCheckBox("¿Olvidaste el correo?");
        opcionOlvidarCorreo.setBackground(new Color(34, 40, 49));
        opcionOlvidarCorreo.setForeground(new Color(240, 248, 255));
        opcionOlvidarCorreo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridy = 4;
        fondoPersonalizado.add(opcionOlvidarCorreo, gbc);
    }

    /**
     * Método limpio para mostrar la ventana.
     */
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            BB_IniciarSesion ventana = new BB_IniciarSesion();
            ventana.setVisible(true);
        });
    }
}