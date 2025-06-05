package CC_vistaCodigoInterfaz_03;

import BB_modeloBBDD_02.BDPalabras;

import javax.swing.*;
import java.awt.*;

public class MenuDesplegable extends JFrame {

    public MenuDesplegable() {
        super("Menú Desplegable");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        inicializarInterfaz();
        RegistroDeEventos.registrarInfo("Ventana Menú Desplegable inicializada");
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            new MenuDesplegable().setVisible(true);
            RegistroDeEventos.registrarInfo("Ventana Menú Desplegable visible");
        });
    }

    public static void main(String[] args) {
        mostrarVentana();
    }

    private void inicializarInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        JMenu menuPrincipal = new JMenu("Menú");
        barraMenu.add(menuPrincipal);

        JMenu menuJugador = new JMenu("Jugador");

        JMenuItem abrirJuego = new JMenuItem("Abrir Juego");
        abrirJuego.addActionListener(e -> {
            RegistroDeEventos.registrarInfo("Seleccionado 'Abrir Juego'");

            // Obtener palabra random de la BBDD para pasar a PantallaAhorcado
            String palabraRandom = BDPalabras.obtenerPalabraRandom();
            if (palabraRandom == null) {
                palabraRandom = "PalabraPorDefecto"; // fallback si no hay palabras en BBDD
            }

            PantallaAhorcado.mostrarVentana(palabraRandom);
            dispose();
            RegistroDeEventos.registrarInfo("Ventana Menú Desplegable cerrada tras abrir juego");
        });

        menuJugador.add(abrirJuego);
        menuPrincipal.add(menuJugador);

        JMenu menuAdministrador = new JMenu("Administrador");

        JMenuItem modoAdmin = new JMenuItem("Modo Administrador");
        modoAdmin.addActionListener(e -> manejarModoAdministrador());

        menuAdministrador.add(modoAdmin);
        menuPrincipal.add(menuAdministrador);
    }

    private void manejarModoAdministrador() {
        RegistroDeEventos.registrarInfo("Seleccionado 'Modo Administrador'");

        String entrada = JOptionPane.showInputDialog(this,
                "Ingresa el nivel de administrador (1-3):",
                "Nivel de Administrador",
                JOptionPane.QUESTION_MESSAGE);

        int nivelAdmin = 1;

        try {
            if (entrada != null && !entrada.isEmpty()) {
                nivelAdmin = Integer.parseInt(entrada);
            }
            RegistroDeEventos.registrarInfo("Nivel administrador ingresado: " + nivelAdmin);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Nivel inválido, se usará 1 por defecto.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            RegistroDeEventos.registrarError("Nivel inválido ingresado: " + entrada);
        }

        VentanaAdministrador.mostrarVentana(nivelAdmin);
        dispose();
        RegistroDeEventos.registrarInfo("Ventana Menú Desplegable cerrada tras abrir administrador");
    }
}
