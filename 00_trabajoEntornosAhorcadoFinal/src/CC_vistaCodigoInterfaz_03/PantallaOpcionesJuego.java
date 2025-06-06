package CC_vistaCodigoInterfaz_03;

import DD_controladorCodigoIntermediario_04.GestorPalabras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Ventana modal que permite seleccionar opciones antes de iniciar el juego.
 */
public class PantallaOpcionesJuego extends JDialog {

    private static final Logger LOGGER = Logger.getLogger(PantallaOpcionesJuego.class.getName());

    private JComboBox<String> comboDificultad;
    private JComboBox<String> comboIdioma;

    public PantallaOpcionesJuego(JFrame parent) {
        super(parent, "Opciones del Juego", true); // modal
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel dificultad
        JPanel panelDificultad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDificultad.add(new JLabel("Dificultad:"));
        comboDificultad = new JComboBox<>(new String[]{"Fácil", "Media", "Difícil"});
        panelDificultad.add(comboDificultad);

        // Panel idioma
        JPanel panelIdioma = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIdioma.add(new JLabel("Idioma:"));
        comboIdioma = new JComboBox<>(new String[]{"Español", "Inglés"});
        panelIdioma.add(comboIdioma);

        // Botón comenzar juego
        JButton btnComenzar = new JButton("Comenzar Juego");
        btnComenzar.setBackground(new Color(100, 149, 237));
        btnComenzar.setForeground(Color.WHITE);
        btnComenzar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnComenzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comenzarJuego();
            }
        });

        // Añadir componentes al panel principal
        panel.add(panelDificultad);
        panel.add(panelIdioma);
        panel.add(new JLabel()); // espacio vacío
        panel.add(btnComenzar);

        add(panel);
    }

    private void comenzarJuego() {
        String dificultad = (String) comboDificultad.getSelectedItem();
        String idioma = (String) comboIdioma.getSelectedItem();

        LOGGER.info("Intentando obtener palabra para dificultad: " + dificultad + ", idioma: " + idioma);

        GestorPalabras.PalabraFrase pf = GestorPalabras.obtenerPalabraPorDificultadEIdioma(dificultad, idioma);

        if (pf == null) {
            LOGGER.warning("No se encontró palabra para dificultad='" + dificultad + "', idioma='" + idioma + "'");
            JOptionPane.showMessageDialog(this,
                    "No se pudo obtener una palabra para la dificultad e idioma seleccionados.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pf.palabra() == null || pf.palabra().trim().isEmpty()) {
            LOGGER.severe("Palabra obtenida está vacía o nula.");
            JOptionPane.showMessageDialog(this, "La palabra obtenida está vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose(); // cierra esta ventana modal

        PantallaAhorcado.mostrarVentanaConPalabra(pf.palabra().toUpperCase(), pf.significado());
    }
}
