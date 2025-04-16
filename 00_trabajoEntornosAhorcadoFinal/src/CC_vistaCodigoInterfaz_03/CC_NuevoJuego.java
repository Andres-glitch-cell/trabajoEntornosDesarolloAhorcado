package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la ventana para configurar un nuevo juego en el juego del ahorcado.
 * Permite seleccionar el tipo de juego, número de jugadores, nivel de dificultad y otros parámetros.
 *
 * @author Andrés Fernández Salaud
 * @version Ahorcado_v.0.0.4
 */
@SuppressWarnings("NonAsciiCharacters")
public class CC_NuevoJuego extends JFrame {

    /**
     * Constructor que inicializa la ventana de configuración de un nuevo juego.
     * Configura un formulario con opciones para tipo de juego, número de jugadores,
     * nivel de dificultad y botones para aceptar o cancelar.
     */
    public CC_NuevoJuego() {
        super("Juego Nuevo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(34, 40, 49));
        add(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("JUEGO NUEVO", SwingConstants.CENTER);
        titulo.setForeground(new Color(240, 248, 255));
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        fondo.add(titulo, gbc);

        JLabel tipoJuegoLabel = new JLabel("Tipo:");
        tipoJuegoLabel.setForeground(new Color(240, 248, 255));
        tipoJuegoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        fondo.add(tipoJuegoLabel, gbc);

        JPanel tipoJuegoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoJuegoPanel.setBackground(new Color(34, 40, 49));
        ButtonGroup tipoJuegoGroup = new ButtonGroup();

        JRadioButton palabrasRadio = new JRadioButton("Palabras");
        palabrasRadio.setForeground(new Color(240, 248, 255));
        palabrasRadio.setBackground(new Color(34, 40, 49));
        palabrasRadio.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JRadioButton frasesRadio = new JRadioButton("Frases");
        frasesRadio.setForeground(new Color(240, 248, 255));
        frasesRadio.setBackground(new Color(34, 40, 49));
        frasesRadio.setFont(new Font("SansSerif", Font.PLAIN, 12));

        tipoJuegoGroup.add(palabrasRadio);
        tipoJuegoGroup.add(frasesRadio);
        tipoJuegoPanel.add(palabrasRadio);
        tipoJuegoPanel.add(frasesRadio);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fondo.add(tipoJuegoPanel, gbc);

        JLabel jugadoresLabel = new JLabel("Número de Jugadores:");
        jugadoresLabel.setForeground(new Color(240, 248, 255));
        jugadoresLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        fondo.add(jugadoresLabel, gbc);

        JTextField campoJugadores = new JTextField(15);
        campoJugadores.setBackground(new Color(50, 60, 70));
        campoJugadores.setForeground(Color.WHITE);
        campoJugadores.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fondo.add(campoJugadores, gbc);

        JLabel nivelLabel = new JLabel("Nivel Dificultad:");
        nivelLabel.setForeground(new Color(240, 248, 255));
        nivelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        fondo.add(nivelLabel, gbc);

        JTextField campoNivel = new JTextField(15);
        campoNivel.setBackground(new Color(50, 60, 70));
        campoNivel.setForeground(Color.WHITE);
        campoNivel.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fondo.add(campoNivel, gbc);

        JLabel dificultadLabel = new JLabel("Dificultad:");
        dificultadLabel.setForeground(new Color(240, 248, 255));
        dificultadLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        fondo.add(dificultadLabel, gbc);

        JTextField campoDificultad = new JTextField(15);
        campoDificultad.setBackground(new Color(50, 60, 70));
        campoDificultad.setForeground(Color.WHITE);
        campoDificultad.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fondo.add(campoDificultad, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(34, 40, 49));

        JButton botonOk = new JButton("OK");
        botonOk.setBackground(new Color(100, 149, 237));
        botonOk.setForeground(Color.WHITE);
        botonOk.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonOk.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelBotones.add(botonOk);

        JButton botonCancel = new JButton("CANCEL");
        botonCancel.setBackground(new Color(100, 149, 237));
        botonCancel.setForeground(Color.WHITE);
        botonCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonCancel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelBotones.add(botonCancel);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        fondo.add(panelBotones, gbc);
    }
}