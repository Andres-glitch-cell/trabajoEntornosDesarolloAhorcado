package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class FF_VentanaAdministrador extends JFrame {

    public FF_VentanaAdministrador() {
        super("Modo Administrador");

        // Configuración básica de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);  // Tamaño adecuado
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con fondo oscuro
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(34, 40, 49));  // Color de fondo similar
        add(fondo);

        // Configuración del GridBagConstraints para los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título del administrador
        JLabel adminLabel = new JLabel("¡Bienvenido al Modo Administrador!", SwingConstants.CENTER);
        adminLabel.setForeground(new Color(240, 248, 255));  // Texto en blanco
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Para que abarque las dos columnas
        fondo.add(adminLabel, gbc);

        // Botón "Volver" para volver a la pantalla de bienvenida
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(100, 149, 237));  // Color azul
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVolver.addActionListener(e -> {
            new AAA_PantallaBienvenida().setVisible(true);  // Muestra la ventana de bienvenida
            dispose();  // Cierra la ventana actual de administrador
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;  // El botón no debe abarcar varias columnas
        fondo.add(btnVolver, gbc);

        // Botón "Administrador 1"
        JButton btnAdmin1 = new JButton("Administrador 1");
        btnAdmin1.setBackground(new Color(100, 149, 237));  // Azul
        btnAdmin1.setForeground(Color.WHITE);
        btnAdmin1.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAdmin1.addActionListener(e -> {
            // Aquí agregarías la acción para Administrador 1
            JOptionPane.showMessageDialog(this, "Acción de Administrador 1");
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        fondo.add(btnAdmin1, gbc);

        // Botón "Administrador 2"
        JButton btnAdmin2 = new JButton("Administrador 2");
        btnAdmin2.setBackground(new Color(100, 149, 237));  // Azul
        btnAdmin2.setForeground(Color.WHITE);
        btnAdmin2.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAdmin2.addActionListener(e -> {
            // Aquí agregarías la acción para Administrador 2
            JOptionPane.showMessageDialog(this, "Acción de Administrador 2");
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        fondo.add(btnAdmin2, gbc);

        // Botón "Administrador 3"
        JButton btnAdmin3 = new JButton("Administrador 3");
        btnAdmin3.setBackground(new Color(100, 149, 237));  // Azul
        btnAdmin3.setForeground(Color.WHITE);
        btnAdmin3.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAdmin3.addActionListener(e -> {
            // Aquí agregarías la acción para Administrador 3
            JOptionPane.showMessageDialog(this, "Acción de Administrador 3");
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        fondo.add(btnAdmin3, gbc);
    }

    // Método estático para mostrar la ventana de administrador
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new FF_VentanaAdministrador().setVisible(true));
    }
}