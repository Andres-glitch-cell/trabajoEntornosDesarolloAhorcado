package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class EE_MenuDesplegable extends JFrame {

    public EE_MenuDesplegable() {
        super("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        JMenu menuJugador = new JMenu("Jugador");
        menuJugador.setFont(new Font("SansSerif", Font.BOLD, 14));
        barraMenu.add(menuJugador);

        JMenuItem abrirJuegoItem = new JMenuItem("Abrir Juego");
        abrirJuegoItem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        abrirJuegoItem.addActionListener(e -> {
            new DD_PantallaAhorcado().setVisible(true);
            dispose(); // Cierra el menú para no tener ventanas duplicadas
        });
        menuJugador.add(abrirJuegoItem);

        JMenu menuAdministrador = new JMenu("Administrador");
        menuAdministrador.setFont(new Font("SansSerif", Font.BOLD, 14));
        barraMenu.add(menuAdministrador);

        JMenuItem adminItem = new JMenuItem("Modo Administrador");
        adminItem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        adminItem.addActionListener(e -> {
            new FF_VentanaAdministrador().setVisible(true);
            // Si quieres que el menú permanezca abierto, no uses dispose()
        });
        menuAdministrador.add(adminItem);

        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCentral.setBackground(Color.LIGHT_GRAY);
        JLabel etiquetaBienvenida = new JLabel("¡BIENVENIDO AL JUEGO DEL AHORCADO!");
        etiquetaBienvenida.setForeground(new Color(0, 0, 204));
        etiquetaBienvenida.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelCentral.add(etiquetaBienvenida);

        add(panelCentral);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EE_MenuDesplegable().setVisible(true));
    }
}
