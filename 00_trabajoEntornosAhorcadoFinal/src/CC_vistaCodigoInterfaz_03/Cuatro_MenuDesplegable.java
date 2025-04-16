package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class Cuatro_MenuDesplegable extends JFrame {
    public Cuatro_MenuDesplegable() {
        super("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        JMenu menuJugador = new JMenu("Jugador");
        menuJugador.setFont(new Font("SansSerif", Font.BOLD, 14));
        barraMenu.add(menuJugador);

        JMenuItem nuevoJuegoItem = new JMenuItem("Nuevo juego");
        nuevoJuegoItem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        menuJugador.add(nuevoJuegoItem);

        menuJugador.add("Abrir Juego");
        menuJugador.add("Ver Informe");

        JMenu menuAdministrador = new JMenu("Administrador");
        menuAdministrador.setFont(new Font("SansSerif", Font.BOLD, 14));
        barraMenu.add(menuAdministrador);

        menuAdministrador.add("Añadir/Eliminar idioma");
        menuAdministrador.add("Copia de seguridad");
        menuAdministrador.add("Restauración");
        menuAdministrador.add("Gestión palabra/Frase");
        menuAdministrador.add("Gestión usuarios");

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.LIGHT_GRAY);
        add(panelPrincipal);

        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCentral.setBackground(Color.LIGHT_GRAY);
        JLabel etiquetaBienvenida = new JLabel("¡BIENVENIDO AL JUEGO DEL AHORCADO!");
        etiquetaBienvenida.setForeground(new Color(0, 0, 204));
        etiquetaBienvenida.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelCentral.add(etiquetaBienvenida);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
    }
}