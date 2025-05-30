package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.*;

/**
 * Ventana principal del juego del ahorcado.
 * Muestra la horca, letras usadas, puntos y botones de interacción.
 *
 * @author Andrés
 * @version Ahorcado_v.0.0.6
 */
public class PantallaAhorcado extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(PantallaAhorcado.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);
            FileHandler fh = new FileHandler("PantallaAhorcado.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final Color BG_COLOR = new Color(34, 40, 49);
    private static final Color PANEL_COLOR = new Color(40, 45, 55);
    private static final Color TEXT_COLOR = new Color(240, 248, 255);
    private static final Color ACCENT_COLOR = new Color(100, 149, 237);
    private static final Color BUTTON_COLOR = new Color(50, 60, 70);
    private static final Color BUTTON_HOVER_COLOR = new Color(70, 80, 90);
    private static final Color USED_LETTER_BG = new Color(60, 65, 80);

    private static final Dimension WINDOW_SIZE = new Dimension(1100, 900);
    private static final Dimension BUTTON_SIZE = new Dimension(130, 48);
    private static final Dimension LETTER_BUTTON_SIZE = new Dimension(60, 60);
    private static final Dimension LEFT_PANEL_SIZE = new Dimension(280, 0);
    private static final Dimension HORCA_SIZE = new Dimension(600, 600);

    private JLabel letrasUsadasLabel;
    private JLabel puntosLabel;
    private JLabel turnoLabel;
    private JLabel palabraLabel;
    private JLabel definicionLabel;

    public PantallaAhorcado() {
        super("Juego del Ahorcado - Andrés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel mainPanel = createMainPanel();
        add(mainPanel);

        LOGGER.info("Ventana PantallaAhorcado creada.");
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> {
            PantallaAhorcado ventana = new PantallaAhorcado();
            ventana.setVisible(true);
            LOGGER.info("Ventana PantallaAhorcado mostrada.");
        });
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, BG_COLOR, 0, getHeight(), new Color(20, 25, 30)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.setOpaque(false);

        panel.add(createTopPanel(), BorderLayout.NORTH);
        panel.add(createCentralPanel(), BorderLayout.CENTER);
        panel.add(createLetterButtonsPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = createLabel("JUEGO DEL AHORCADO", 44, Font.BOLD, ACCENT_COLOR, JLabel.CENTER);
        title.setBorder(new EmptyBorder(5, 0, 15, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonsPanel.setOpaque(false);

        JButton idiomaBtn = createIconButton("Idioma", "🌐");
        JButton partidaBtn = createIconButton("Partida", "🎮");
        JButton salirBtn = createIconButton("Salir", "⏻");

        salirBtn.addActionListener(e -> {
            LOGGER.info("Botón 'Salir' presionado. Volviendo a PantallaBienvenida.");
            setVisible(false);
            dispose();
            PantallaBienvenida.mostrarVentana();
        });

        buttonsPanel.add(idiomaBtn);
        buttonsPanel.add(partidaBtn);
        buttonsPanel.add(salirBtn);
        panel.add(buttonsPanel, BorderLayout.CENTER);

        JSeparator separator = new JSeparator();
        separator.setForeground(ACCENT_COLOR);
        panel.add(separator, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCentralPanel() {
        JPanel panel = new JPanel(new BorderLayout(40, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));

        panel.add(createLeftPanel(), BorderLayout.WEST);
        panel.add(createGamePanel(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(createRoundedBorder(30));
        panel.setPreferredSize(LEFT_PANEL_SIZE);

        panel.add(createLabel("Letras Usadas", 24, Font.BOLD, TEXT_COLOR, JLabel.CENTER));
        letrasUsadasLabel = createLabel("Ninguna", 20, Font.PLAIN, TEXT_COLOR, JLabel.CENTER);
        letrasUsadasLabel.setOpaque(true);
        letrasUsadasLabel.setBackground(USED_LETTER_BG);
        letrasUsadasLabel.setBorder(new EmptyBorder(12, 12, 12, 12));
        letrasUsadasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        letrasUsadasLabel.setMaximumSize(new Dimension(LEFT_PANEL_SIZE.width - 40, 100));
        panel.add(Box.createVerticalStrut(10));
        panel.add(letrasUsadasLabel);
        panel.add(Box.createVerticalStrut(50));

        panel.add(createLabel("Puntos", 24, Font.BOLD, TEXT_COLOR, JLabel.CENTER));
        puntosLabel = createLabel("<html>Jugador 1: 0<br>Jugador 2: 0<br>Jugador N: 0</html>", 20, Font.PLAIN, TEXT_COLOR, JLabel.CENTER);
        puntosLabel.setBorder(new EmptyBorder(15, 0, 15, 0));
        panel.add(puntosLabel);

        panel.add(Box.createVerticalGlue());

        JButton btnVolver = createStyledButton("Volver al Menú", BUTTON_SIZE);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            LOGGER.info("Botón 'Volver al Menú' presionado. Volviendo a PantallaBienvenida.");
            setVisible(false);
            dispose();
            PantallaBienvenida.mostrarVentana();
        });
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnVolver);

        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        turnoLabel = createLabel("Turno: Jugador 1", 28, Font.BOLD, ACCENT_COLOR, JLabel.CENTER);
        panel.add(turnoLabel, BorderLayout.NORTH);

        JPanel horcaPanel = new MunecoAhorcado();
        horcaPanel.setLayout(new BorderLayout());
        horcaPanel.setPreferredSize(HORCA_SIZE);
        horcaPanel.setBorder(createRoundedBorder(20));

        palabraLabel = createLabel("_ _ _ _ _ _", 42, Font.BOLD, Color.WHITE, JLabel.CENTER);
        horcaPanel.add(palabraLabel, BorderLayout.SOUTH);

        definicionLabel = createLabel("Definición o descripción de la palabra", 20, Font.PLAIN, TEXT_COLOR, JLabel.CENTER);
        horcaPanel.add(definicionLabel, BorderLayout.NORTH);

        panel.add(horcaPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLetterButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 13, 14, 14));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = createStyledButton(String.valueOf(c), LETTER_BUTTON_SIZE);
            button.setFocusable(false);
            button.addActionListener(e -> {
                button.setEnabled(false);
                char letra = e.getActionCommand().charAt(0);
                agregarLetraUsada(letra);
                LOGGER.info("Letra usada agregada: " + letra);
                // TODO: Agregar lógica del juego aquí
            });
            panel.add(button);
        }

        return panel;
    }

    private void agregarLetraUsada(char letra) {
        String actuales = letrasUsadasLabel.getText();
        letrasUsadasLabel.setText("Ninguna".equals(actuales) ? String.valueOf(letra) : actuales + ", " + letra);
    }

    private JButton createStyledButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setPreferredSize(size);
        button.setBorder(createRoundedBorder(8, 14));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(BUTTON_HOVER_COLOR); button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(BUTTON_COLOR); button.setCursor(Cursor.getDefaultCursor()); }
        });
        return button;
    }

    private JButton createIconButton(String text, String iconEmoji) {
        JButton button = new JButton(iconEmoji + " " + text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(BUTTON_SIZE);
        button.setBorder(createRoundedBorder(10, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(BUTTON_HOVER_COLOR); button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(BUTTON_COLOR); button.setCursor(Cursor.getDefaultCursor()); }
        });
        return button;
    }

    private JLabel createLabel(String text, int fontSize, int fontStyle, Color color, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(color);
        label.setFont(new Font("SansSerif", fontStyle, fontSize));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private Border createRoundedBorder(int padding) {
        return createRoundedBorder(padding, padding);
    }

    private Border createRoundedBorder(int paddingY, int paddingX) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 3, true),
                new EmptyBorder(paddingY, paddingX, paddingY, paddingX));
    }
}
