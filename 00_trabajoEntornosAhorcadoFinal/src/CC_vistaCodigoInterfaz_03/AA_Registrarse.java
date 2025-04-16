package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana de registro de usuarios para el juego del ahorcado.
 * Permite introducir un nombre de usuario, una contraseña y confirmar la contraseña.
 *
 * @author Andrés Fernández Salaud
 * @version Ahorcado_v.0.0.4
 */
@SuppressWarnings("NonAsciiCharacters")
public class AA_Registrarse extends JFrame {

    /**
     * Constructor que inicializa la ventana de registro.
     * Configura un formulario con campos para nombre de usuario, contraseña y confirmación,
     * además de botones para registrarse o volver al inicio de sesión.
     */
    public AA_Registrarse() {
        super("Registrarse");
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

        JLabel introducirContraseña = new JLabel("Nueva Contraseña:");
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

        JLabel confirmarContraseña = new JLabel("Confirmar Contraseña:");
        confirmarContraseña.setForeground(new Color(240, 248, 255));
        confirmarContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        fondoPersonalizado.add(confirmarContraseña, gbc);

        JPasswordField campoConfirmarContraseña = new JPasswordField(15);
        campoConfirmarContraseña.setBackground(new Color(50, 60, 70));
        campoConfirmarContraseña.setForeground(Color.WHITE);
        campoConfirmarContraseña.setBorder(BorderFactory.createLineBorder(new Color(58, 92, 164), 1));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(campoConfirmarContraseña, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(34, 40, 49));

        JButton botonRegistrarse = new JButton("Registrarse Gratuitamente");
        botonRegistrarse.setBackground(new Color(100, 149, 237));
        botonRegistrarse.setForeground(Color.WHITE);
        botonRegistrarse.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonRegistrarse.setFocusPainted(false);
        botonRegistrarse.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelBotones.add(botonRegistrarse);

        JButton botonVolver = new JButton("Volver");
        botonVolver.setBackground(new Color(100, 149, 237));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BB_IniciarSesion ventanaIniciarSesion = new BB_IniciarSesion();
                ventanaIniciarSesion.setVisible(true);
                dispose();
            }
        });
        panelBotones.add(botonVolver);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        fondoPersonalizado.add(panelBotones, gbc);

        JCheckBox opcionOlvidarContraseña = new JCheckBox("¿Olvidaste la contraseña?");
        opcionOlvidarContraseña.setBackground(new Color(34, 40, 49));
        opcionOlvidarContraseña.setForeground(new Color(240, 248, 255));
        opcionOlvidarContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
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
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        fondoPersonalizado.add(opcionOlvidarCorreo, gbc);
    }
}