package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana de inicio de sesión para el juego del ahorcado.
 * Permite introducir un nombre de usuario y contraseña para acceder al sistema.
 *
 * @author Andrés Fernández Salaud
 * @version Ahorcado_v.0.0.4
 */
@SuppressWarnings("NonAsciiCharacters")
public class BB_IniciarSesion extends JFrame {

    /**
     * Constructor que inicializa la ventana de inicio de sesión.
     * Configura un formulario con campos para nombre de usuario y contraseña,
     * además de botones para iniciar sesión o ir al registro.
     */
    public BB_IniciarSesion() {
        super("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel fondoPersonalizado = new JPanel();
        fondoPersonalizado.setLayout(new GridBagLayout());
        fondoPersonalizado.setBackground(new Color(34, 40, 49));
        add(fondoPersonalizado);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel introducirUsuario = new JLabel("Nombre del Usuario:");
        introducirUsuario.setForeground(new Color(240, 248, 255));
        introducirUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        fondoPersonalizado.add(introducirUsuario, gbc);

        JTextField campoUsuario = new JTextField(15);
        campoUsuario.setBackground(new Color(50, 60, 70));
        campoUsuario.setForeground(Color.WHITE);
        campoUsuario.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(campoUsuario, gbc);

        JLabel introducirContraseña = new JLabel("Contraseña:");
        introducirContraseña.setForeground(new Color(240, 248, 255));
        introducirContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        fondoPersonalizado.add(introducirContraseña, gbc);

        JPasswordField campoContraseña = new JPasswordField(15);
        campoContraseña.setBackground(new Color(50, 60, 70));
        campoContraseña.setForeground(Color.WHITE);
        campoContraseña.setBorder(BorderFactory.createLineBorder(new Color(58, 92, 164), 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(campoContraseña, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(34, 40, 49));

        JButton botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.setBackground(new Color(100, 149, 237));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonIniciarSesion.setFocusPainted(false);
        botonIniciarSesion.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelBotones.add(botonIniciarSesion);

        JButton botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setBackground(new Color(100, 149, 237));
        botonRegistrarse.setForeground(Color.WHITE);
        botonRegistrarse.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonRegistrarse.setFocusPainted(false);
        botonRegistrarse.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botonRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AA_Registrarse ventanaRegistrarse = new AA_Registrarse();
                ventanaRegistrarse.setVisible(true);
                dispose();
            }
        });
        panelBotones.add(botonRegistrarse);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        fondoPersonalizado.add(panelBotones, gbc);

        JCheckBox opcionOlvidarContraseña = new JCheckBox("¿Olvidaste la contraseña?");
        opcionOlvidarContraseña.setBackground(new Color(34, 40, 49));
        opcionOlvidarContraseña.setForeground(new Color(240, 248, 255));
        opcionOlvidarContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(opcionOlvidarContraseña, gbc);

        JCheckBox opcionOlvidarCorreo = new JCheckBox("¿Olvidaste el correo?");
        opcionOlvidarCorreo.setBackground(new Color(34, 40, 49));
        opcionOlvidarCorreo.setForeground(new Color(240, 248, 255));
        opcionOlvidarCorreo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(opcionOlvidarCorreo, gbc);
    }
}