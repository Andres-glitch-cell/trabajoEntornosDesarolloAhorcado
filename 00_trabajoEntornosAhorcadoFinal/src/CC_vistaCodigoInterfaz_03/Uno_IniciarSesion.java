package CC_vistaCodigoInterfaz_03;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Uno_IniciarSesion extends JFrame {
    public Uno_IniciarSesion() {
        super("Iniciar Sesión");

        // Configuración de la ventana principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 280); // Aumenté ligeramente el alto para el checkbox
        setResizable(false);
        setLocationRelativeTo(null);

        // Panel principal con fondo personalizado
        JPanel fondoPersonalizado = new JPanel();
        fondoPersonalizado.setLayout(new GridBagLayout());
        fondoPersonalizado.setBackground(new Color(34, 40, 49));
        add(fondoPersonalizado);

        // Configuración de GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta de usuario
        JLabel introducirUsuario = new JLabel("Usuario:");
        introducirUsuario.setForeground(new Color(240, 248, 255));
        introducirUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        fondoPersonalizado.add(introducirUsuario, gbc);

        // Campo de texto para usuario
        JTextField nombreUsuario = new JTextField(15);
        nombreUsuario.setBackground(new Color(50, 60, 70));
        nombreUsuario.setForeground(Color.WHITE);
        nombreUsuario.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        fondoPersonalizado.add(nombreUsuario, gbc);

        // Etiqueta de contraseña
        JLabel introducirContraseña = new JLabel("Contraseña:");
        introducirContraseña.setForeground(new Color(240, 248, 255));
        introducirContraseña.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        fondoPersonalizado.add(introducirContraseña, gbc);

        // Campo de texto para contraseña
        JPasswordField valor2 = new JPasswordField(15);
        valor2.setBackground(new Color(50, 60, 70));
        valor2.setForeground(Color.WHITE);
        valor2.setBorder(BorderFactory.createLineBorder(new Color(58, 92, 164), 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        fondoPersonalizado.add(valor2, gbc);

        // Botón de registrarse
        JButton boton = new JButton("Registrarse");
        boton.setBackground(new Color(100, 149, 237));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        fondoPersonalizado.add(boton, gbc);

        // Checkbox "Olvidaste la contraseña"
        JCheckBox opcionOlvidarContraseña = new JCheckBox("¿Olvidaste la contraseña?");
        opcionOlvidarContraseña.setBackground(new Color(34, 40, 49)); // Mismo color que el fondo
        opcionOlvidarContraseña.setForeground(new Color(240, 248, 255)); // Mismo color que etiquetas
        opcionOlvidarContraseña.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Fuente más pequeña
        gbc.gridx = 0;
        gbc.gridy = 3; // Debajo del botón
        gbc.gridwidth = 2; // Ocupa ambas columnas para centrar
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar el checkbox
        fondoPersonalizado.add(opcionOlvidarContraseña, gbc);

        // Checkbox "Olvidaste el correo"
        JCheckBox opcionOlvidarCorreo = new JCheckBox("¿Olvidaste el correo?");
        // Mismo color que el fondo
        opcionOlvidarCorreo.setBackground(new Color(34, 40, 49));
        // Mismo color que etiquetas
        opcionOlvidarCorreo.setForeground(new Color(240, 248, 255));
        opcionOlvidarCorreo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbc.gridx = 0;
        // Debajo del botón
        gbc.gridy = 2;
        // Ocupa ambas columnas para centrar
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar el checkbox
        fondoPersonalizado.add(opcionOlvidarCorreo, gbc);

        // Hacer visible la ventana
        setVisible(true);

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer(5000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        dispose();
                        new Tres_VentanaEmergente();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });


        class Main {
            public static void main(String[] args) {
                JFrame.setDefaultLookAndFeelDecorated(true);

                // Crear la ventana principal del juego
                JFrame ventana = new JFrame("JUEGO DEL AHORCADO");
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
                ventana.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
                new Uno_IniciarSesion();
            }
        }
        }
    }
