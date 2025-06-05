package CC_vistaCodigoInterfaz_03;

import DD_controladorCodigoIntermediario_04.GestorPalabras;

import javax.swing.*;
import java.awt.*;

public class MenuDesplegable extends JFrame {

    public MenuDesplegable(int nivelAdmin) {
        super("Menú Desplegable");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        inicializarInterfaz(nivelAdmin);
        RegistroDeEventos.registrarInfo("Ventana Menú Desplegable inicializada");
    }

    public static void mostrarVentana(int nivelAdmin) {
        SwingUtilities.invokeLater(() -> {
            new MenuDesplegable(nivelAdmin).setVisible(true);
            RegistroDeEventos.registrarInfo("Ventana Menú Desplegable visible");
        });
    }

    private void inicializarInterfaz(int nivelAdmin) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(new Color(34, 40, 49));
        add(panel);

        if (nivelAdmin == 0) {
            JButton botonJuego = new JButton("Abrir Juego");
            botonJuego.setBackground(new Color(0, 100, 100));
            botonJuego.setForeground(Color.WHITE);
            botonJuego.setFont(new Font("Arial", Font.BOLD, 14));
            botonJuego.setPreferredSize(new Dimension(150, 40));
            botonJuego.addActionListener(e -> {
                RegistroDeEventos.registrarInfo("Seleccionado 'Abrir Juego'");
                String palabraRandom = GestorPalabras.obtenerPalabraRandom();
                if (palabraRandom == null) {
                    palabraRandom = "PalabraPorDefecto";
                }
                PantallaAhorcado.mostrarVentana(palabraRandom);
                dispose();
                RegistroDeEventos.registrarInfo("Ventana Menú Desplegable cerrada tras abrir juego");
            });
            panel.add(botonJuego);
            panel.add(Box.createVerticalStrut(20));
        }

        JButton botonAdmin = new JButton("Modo Administrador");
        botonAdmin.setBackground(new Color(0, 100, 100));
        botonAdmin.setForeground(Color.WHITE);
        botonAdmin.setFont(new Font("Arial", Font.BOLD, 14));
        botonAdmin.setPreferredSize(new Dimension(150, 40));
        botonAdmin.addActionListener(e -> manejarModoAdministrador());
        panel.add(botonAdmin);
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