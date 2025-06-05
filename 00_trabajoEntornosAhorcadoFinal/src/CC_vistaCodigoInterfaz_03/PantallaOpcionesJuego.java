package CC_vistaCodigoInterfaz_03;

import DD_controladorCodigoIntermediario_04.PalabrasFrasesBBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana modal que permite seleccionar opciones antes de iniciar el juego.
 */
public class PantallaOpcionesJuego extends JDialog {

    private JComboBox<String> comboDificultad;
    private JComboBox<String> comboIdioma;

    public PantallaOpcionesJuego(JFrame parent) {
        super(parent, "Opciones del Juego", true); // true = modal
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dificultad
        JPanel panelDificultad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDificultad.add(new JLabel("Dificultad:"));
        comboDificultad = new JComboBox<>(new String[]{"Fácil", "Media", "Difícil"});
        panelDificultad.add(comboDificultad);

        // Idioma
        JPanel panelIdioma = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIdioma.add(new JLabel("Idioma:"));
        comboIdioma = new JComboBox<>(new String[]{"Español", "Inglés"});
        panelIdioma.add(comboIdioma);

        // Botón para comenzar el juego
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

        // Agregar componentes al panel
        panel.add(panelDificultad);
        panel.add(panelIdioma);
        panel.add(new JLabel()); // Espacio
        panel.add(btnComenzar);

        add(panel);
    }

    private void comenzarJuego() {
        // Podrías usar dificultad/idioma para filtrar palabras si implementas eso

        PalabrasFrasesBBDD.PalabraFrase pf = PalabrasFrasesBBDD.obtenerPalabraAleatoria();

        if (pf == null || pf.contenido == null || pf.contenido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo obtener una palabra de la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose(); // Cierra esta ventana

        // Llama al método que muestra la ventana del juego con palabra y significado
        PantallaAhorcado.mostrarVentanaConPalabra(pf.contenido.toUpperCase(), pf.significado);
    }
}
