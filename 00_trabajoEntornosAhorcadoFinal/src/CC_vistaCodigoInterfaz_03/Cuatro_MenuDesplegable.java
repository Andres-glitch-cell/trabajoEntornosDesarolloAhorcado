package CC_vistaCodigoInterfaz_03;
import java.awt.*;
import javax.swing.*;

public class Cuatro_MenuDesplegable extends JFrame {
    public Cuatro_MenuDesplegable() {
        super("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // --- Creación de la barra del menú ---
        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        // --- Crear el menú "Jugador" ---
        JMenu menuJugador = new JMenu("Jugador");
        menuJugador.setFont(new Font("SansSerif", Font.BOLD, 14));
        barraMenu.add(menuJugador);

        // --- Agregar las opciones al menú Jugador ---
        menuJugador.add("Nuevo juego");
        menuJugador.add("Abrir Juego");
        menuJugador.add("Ver Informe");

        // --- Crear el menú "Administrador" ---
        JMenu menuAdministrador = new JMenu("Administrador");
        menuAdministrador.setFont(new Font("SansSerif", Font.BOLD, 14));
        barraMenu.add(menuAdministrador);

        // --- Agregar las opciones al menú Administrador ---
        menuAdministrador.add("Añadir/Eliminar idioma");
        menuAdministrador.add("Copia de seguridad");
        menuAdministrador.add("Restauración");
        menuAdministrador.add("Gestión palabra/Frase");
        menuAdministrador.add("Gestión usuarios");

        // --- Panel principal con fondo claro ---
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.LIGHT_GRAY);
        add(panelPrincipal);

        // --- Panel de Bienvenido al Ahorcado ---
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(Color.LIGHT_GRAY);
        JLabel etiquetaBienvenida = new JLabel("¡BIENVENIDO AL JUEGO DEL AHORCADO!");
        etiquetaBienvenida.setForeground(new Color(0, 0, 204));
        etiquetaBienvenida.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelCentral.add(etiquetaBienvenida, new GridBagConstraints() {
            {
                gridx = 0;
                gridy = 0;
                insets = new Insets(10, 10, 10, 10);
            }});

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
    }
}