package CC_vistaCodigoInterfaz_03;
import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la ventana de inicio de sesión del Juego del Ahorcado.
 * Permite al usuario ingresar su nombre y contraseña, con opciones para recuperar credenciales olvidadas.
 *
 * @author Andres
 * @version Ahorcado_v.0.0.2
 */

public class Uno_Registrarse extends JFrame {
    /**
     * Constructor de la clase Uno_IniciarSesion.
     * Inicializa la ventana con campos de texto para usuario y contraseña,
     * un botón de registro y casillas de verificación.
     */
    public Uno_Registrarse() {
        super("Registrate para Iniciar Sesión en el Juego Del Ahorcado!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 280);
        setResizable(true);
        setLocationRelativeTo(null);

        // Configurar el panel principal con un fondo oscuro.
        JPanel fondoPersonalizado = new JPanel();
        fondoPersonalizado.setLayout(new GridBagLayout());
        fondoPersonalizado.setBackground(new Color(34, 40, 49));
        add(fondoPersonalizado);

        // Configurar las restricciones para el layout.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear y configurar la etiqueta y campo de usuario.
        JLabel introducirUsuario = new JLabel("Introduce tu nuevo Usuario: ");
        introducirUsuario.setForeground(new Color(240, 248, 255));
        introducirUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        fondoPersonalizado.add(introducirUsuario, gbc);

        JTextField nombreUsuario = new JTextField(15);
        nombreUsuario.setBackground(new Color(50, 60, 70));
        nombreUsuario.setForeground(Color.WHITE);
        nombreUsuario.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        fondoPersonalizado.add(nombreUsuario, gbc);

        // Crear y configurar la etiqueta y campo de contraseña.
        JLabel introducirContraseña = new JLabel("Introduce tu nueva contraseña: ");
        introducirContraseña.setForeground(new Color(240, 248, 255));
        introducirContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        fondoPersonalizado.add(introducirContraseña, gbc);

        JPasswordField valor2 = new JPasswordField(15);
        valor2.setBackground(new Color(50, 60, 70));
        valor2.setForeground(Color.WHITE);
        valor2.setBorder(BorderFactory.createLineBorder(new Color(58, 92, 164), 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        fondoPersonalizado.add(valor2, gbc);

        // --- Crear y configurar el botón de registro. ---
        JButton boton = new JButton("Registrarse");
        boton.setBackground(new Color(100, 149, 237));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        fondoPersonalizado.add(boton, gbc);

        // --- Crear y configurar la casilla para olvidar contraseña. ---
        JCheckBox opcionOlvidarContraseña = new JCheckBox("¿Olvidaste la contraseña?");
        opcionOlvidarContraseña.setBackground(new Color(34, 40, 49));
        opcionOlvidarContraseña.setForeground(new Color(240, 248, 255));
        opcionOlvidarContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        fondoPersonalizado.add(opcionOlvidarContraseña, gbc);

        // Crear y configurar la casilla para olvidar correo.
        JCheckBox opcionOlvidarCorreo = new JCheckBox("¿Olvidaste el correo?");
        opcionOlvidarCorreo.setBackground(new Color(34, 40, 49));
        opcionOlvidarCorreo.setForeground(new Color(240, 248, 255));
        opcionOlvidarCorreo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        fondoPersonalizado.add(opcionOlvidarCorreo, gbc);

        setVisible(true);
    }
}