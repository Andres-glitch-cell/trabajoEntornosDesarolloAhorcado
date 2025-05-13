package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class AAA_PantallaBienvenida extends JFrame {

    public AAA_PantallaBienvenida() {
        super("Bienvenida al Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel fondoPersonalizado = new JPanel(new GridBagLayout());
        fondoPersonalizado.setBackground(new Color(34, 40, 49));
        add(fondoPersonalizado);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título de bienvenida
        JLabel bienvenidaLabel = new JLabel("¡Bienvenido al Juego del Ahorcado!", SwingConstants.CENTER);
        bienvenidaLabel.setForeground(new Color(240, 248, 255));
        bienvenidaLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        fondoPersonalizado.add(bienvenidaLabel, gbc);

        // Botón "Iniciar Juego"
        JButton btnIniciarJuego = new JButton("Iniciar Juego");
        btnIniciarJuego.setBackground(new Color(100, 149, 237));
        btnIniciarJuego.setForeground(Color.WHITE);
        btnIniciarJuego.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIniciarJuego.addActionListener(e -> {
            // Acción para iniciar el juego
            JOptionPane.showMessageDialog(this, "Iniciando el juego...");
            // Aquí puedes agregar la lógica para iniciar el juego
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        fondoPersonalizado.add(btnIniciarJuego, gbc);

        // Botón "Iniciar sesión"
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(new Color(100, 149, 237));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIniciarSesion.addActionListener(e -> {
            BB_IniciarSesion.mostrarVentana(); // Muestra la ventana de inicio de sesión
            dispose();  // Cierra la ventana de bienvenida
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        fondoPersonalizado.add(btnIniciarSesion, gbc);

        // Botón "Salir del Juego"
        JButton btnSalir = new JButton("Salir del Juego");
        btnSalir.setBackground(new Color(255, 69, 0));  // Un color rojo anaranjado para indicar que es salir
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres salir?",
                    "Salir del Juego", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);  // Cierra la aplicación
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        fondoPersonalizado.add(btnSalir, gbc);
    }

    /**
     * Método para mostrar la ventana.
     */
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            AAA_PantallaBienvenida ventana = new AAA_PantallaBienvenida();
            ventana.setVisible(true);
        });
    }
}