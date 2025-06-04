package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.*;

public class PantallaAhorcado extends JFrame { // Clase que extiende JFrame para crear ventana

    // Logger para registrar eventos e incidencias en el juego
    private static final Logger LOGGER = Logger.getLogger(PantallaAhorcado.class.getName());
    // Constantes de color usadas para la interfaz gráfica
    private static final Color BG_COLOR = new Color(34, 40, 49);
    // Constantes para tamaño de ventana y componentes
    private static final Dimension WINDOW_SIZE = new Dimension(1100, 900);
    private static final Color PANEL_COLOR = new Color(40, 45, 55);
    private static final Color TEXT_COLOR = new Color(240, 248, 255);
    private static final Color ACCENT_COLOR = new Color(100, 149, 237);
    private static final Color BUTTON_COLOR = new Color(50, 60, 70);
    private static final Color BUTTON_HOVER_COLOR = new Color(70, 80, 90);
    private static final Color USED_LETTER_BG = new Color(60, 65, 80);

    // Bloque estático para configurar el logger al cargar la clase
    static {
        try {
            // Crear carpeta "LOGS" si no existe para guardar archivos de log
            Files.createDirectories(Paths.get("LOGS"));
            // Reiniciar configuración global de logs
            LogManager.getLogManager().reset();
            // Configura el nivel de registro para capturar todos los mensajes
            LOGGER.setLevel(Level.ALL);
            // Crear manejador de archivos para registrar en "LOGS/PantallaAhorcado.log" con append=true
            FileHandler fh = new FileHandler("LOGS/PantallaAhorcado.log", true);
            // Establecer codificación UTF-8 para el archivo de log
            fh.setEncoding("UTF-8");
            // Usar formato simple para los mensajes de log
            fh.setFormatter(new SimpleFormatter());
            // Añadir el manejador de archivos al logger
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            // Si falla la configuración del logger, mostrar mensaje de error
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static final Dimension BUTTON_SIZE = new Dimension(130, 48);
    private static final Dimension LETTER_BUTTON_SIZE = new Dimension(60, 60);
    private static final Dimension LEFT_PANEL_SIZE = new Dimension(280, 0);
    private static final Dimension HORCA_SIZE = new Dimension(600, 600);

    // Etiquetas que muestran información del juego
    private JLabel letrasUsadasLabel; // Mostrar letras ya usadas
    private JLabel puntosLabel;       // Mostrar puntos del jugador
    private JLabel turnoLabel;        // Mostrar el turno actual
    private JLabel palabraLabel;      // Mostrar la palabra a adivinar
    private JLabel definicionLabel;   // Mostrar la definición de la palabra

    // Constructor que configura la ventana principal del juego
    public PantallaAhorcado() {
        super("Juego del Ahorcado - Andrés"); // Establece título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra toda la app al cerrar ventana
        setSize(WINDOW_SIZE); // Establece tamaño fijo de la ventana
        setResizable(true); // Permite que la ventana se pueda redimensionar
        setLocationRelativeTo(null); // Centra la ventana en pantalla

        add(createMainPanel()); // Añade el panel principal con todos los componentes de la interfaz
        LOGGER.info("PantallaAhorcado inicializada."); // Registro informativo en log
    }

    // Método estático para mostrar la ventana (se usa desde otras clases)
    public static void mostrarVentana() {
        // Ejecuta en el hilo de eventos de Swing para seguridad en interfaz
        SwingUtilities.invokeLater(() -> new PantallaAhorcado().setVisible(true));
    }

    // Método que crea el panel principal con un gradiente de fondo
    private JPanel createMainPanel() {
        // Panel con BorderLayout para distribuir componentes en Norte, Centro, Sur, etc.
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Crear gradiente vertical con dos tonos de color para fondo
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, BG_COLOR, 0, getHeight(), new Color(20, 25, 30)));
                g2d.fillRect(0, 0, getWidth(), getHeight()); // Rellenar el fondo con el gradiente
            }
        };
        panel.setOpaque(false); // Hace que el fondo sea transparente para mostrar el gradiente
        panel.setBorder(new EmptyBorder(30, 40, 30, 40)); // Añade margen externo al panel
        panel.add(createTopPanel(), BorderLayout.NORTH); // Añade panel superior (título y botones)
        panel.add(createCentralPanel(), BorderLayout.CENTER); // Añade panel central con juego y datos
        panel.add(createLetterButtonsPanel(), BorderLayout.SOUTH); // Añade panel inferior con botones letras
        return panel;
    }

    // Crea el panel superior con título y botones para idioma, partida y salir
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // Panel con BorderLayout
        panel.setOpaque(false); // Fondo transparente para mostrar gradiente
        panel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Margen interno

        // Etiqueta con título grande, negrita y color acento, centrada
        JLabel title = createLabel("JUEGO DEL AHORCADO", 44, Font.BOLD, ACCENT_COLOR, JLabel.CENTER);
        panel.add(title, BorderLayout.NORTH); // Añade título en la parte superior

        // Panel con botones alineados a la izquierda y espacio entre ellos
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonsPanel.setOpaque(false); // Fondo transparente

        // Botón "Idioma" con icono de globo terráqueo
        buttonsPanel.add(createIconButton("Idioma", "🌐"));
        // Botón "Partida" con icono de control de juego
        buttonsPanel.add(createIconButton("Partida", "🎮"));

        // Botón "Salir" que cierra la ventana actual y vuelve al menú principal
        JButton salirBtn = createIconButton("Salir", "⏻");
        salirBtn.addActionListener(e -> {
            LOGGER.info("Saliendo al menú principal."); // Registrar evento de salida
            dispose(); // Cierra esta ventana
            PantallaBienvenida.mostrarVentana(); // Abre ventana del menú principal
        });
        buttonsPanel.add(salirBtn); // Añadir botón salir al panel de botones

        panel.add(buttonsPanel, BorderLayout.CENTER); // Añadir panel de botones al centro del panel superior
        panel.add(new JSeparator(), BorderLayout.SOUTH); // Línea separadora debajo

        return panel;
    }

    // Crea el panel central con panel izquierdo y panel central para el juego
    private JPanel createCentralPanel() {
        JPanel panel = new JPanel(new BorderLayout(40, 20)); // Espaciado horizontal y vertical
        panel.setOpaque(false); // Fondo transparente
        panel.setBorder(new EmptyBorder(20, 40, 20, 40)); // Margen interno
        panel.add(createLeftPanel(), BorderLayout.WEST); // Panel izquierdo con letras usadas y puntos
        panel.add(createGamePanel(), BorderLayout.CENTER); // Panel central con horca y palabra
        return panel;
    }

    // Crea el panel izquierdo con letras usadas, puntos y botón volver
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout vertical
        panel.setBackground(PANEL_COLOR); // Color de fondo del panel
        panel.setPreferredSize(LEFT_PANEL_SIZE); // Tamaño preferido fijo en ancho
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno

        // Título para sección "Letras Usadas"
        panel.add(createLabel("Letras Usadas", 24, Font.BOLD, TEXT_COLOR, JLabel.CENTER));
        // Etiqueta para mostrar letras usadas inicialmente "Ninguna"
        letrasUsadasLabel = createLabel("Ninguna", 20, Font.PLAIN, TEXT_COLOR, JLabel.CENTER);
        letrasUsadasLabel.setOpaque(true); // Hace visible el fondo
        letrasUsadasLabel.setBackground(USED_LETTER_BG); // Color de fondo para letras usadas
        panel.add(letrasUsadasLabel); // Añadir etiqueta al panel

        panel.add(Box.createVerticalStrut(50)); // Espacio vertical fijo

        // Título para sección "Puntos"
        panel.add(createLabel("Puntos", 24, Font.BOLD, TEXT_COLOR, JLabel.CENTER));
        // Etiqueta para mostrar puntos iniciales
        puntosLabel = createLabel("Jugador 1: 0", 20, Font.PLAIN, TEXT_COLOR, JLabel.CENTER);
        panel.add(puntosLabel);

        panel.add(Box.createVerticalGlue()); // Espacio flexible para empujar elementos abajo

        // Botón para volver al menú principal
        JButton volverBtn = createStyledButton("Volver al Menú", BUTTON_SIZE);
        volverBtn.addActionListener(e -> {
            dispose(); // Cierra ventana actual
            PantallaBienvenida.mostrarVentana(); // Muestra ventana menú principal
        });
        panel.add(Box.createVerticalStrut(20)); // Espacio antes del botón
        panel.add(volverBtn); // Añadir botón al panel

        return panel;
    }

    // Crea el panel central que muestra el turno, dibujo de la horca y palabra con definición
    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20)); // Layout con espacio vertical
        panel.setOpaque(false); // Fondo transparente
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen interno

        // Label para mostrar el turno actual (Jugador 1)
        turnoLabel = createLabel("Turno: Jugador 1", 28, Font.BOLD, ACCENT_COLOR, JLabel.CENTER);
        panel.add(turnoLabel, BorderLayout.NORTH);

        JPanel horcaPanel;
        try {
            horcaPanel = new MunecoAhorcado(); // Intentar crear panel con dibujo del muñeco ahorcado
        } catch (Exception e) {
            LOGGER.warning("MunecoAhorcado no está implementado, usando JPanel vacío."); // Log si no existe la clase
            horcaPanel = new JPanel(); // Panel vacío si no está implementado
        }

        horcaPanel.setPreferredSize(HORCA_SIZE); // Tamaño fijo del panel horca
        horcaPanel.setLayout(new BorderLayout());
        horcaPanel.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2)); // Borde azul alrededor

        // Label para mostrar la definición de la palabra
        definicionLabel = createLabel("Definición", 20, Font.PLAIN, TEXT_COLOR, JLabel.CENTER);
        // Label para mostrar la palabra oculta a adivinar (con guiones)
        palabraLabel = createLabel("_ _ _ _ _", 42, Font.BOLD, TEXT_COLOR, JLabel.CENTER);

        horcaPanel.add(definicionLabel, BorderLayout.NORTH); // Añade definición arriba
        horcaPanel.add(palabraLabel, BorderLayout.SOUTH);    // Añade palabra oculta abajo

        panel.add(horcaPanel, BorderLayout.CENTER); // Añade panel horca al centro del panel principal
        return panel;
    }

    // Panel inferior con botones para cada letra del abecedario (A-Z)
    private JPanel createLetterButtonsPanel() {
        // Grid de 2 filas x 13 columnas con espacio horizontal y vertical entre botones
        JPanel panel = new JPanel(new GridLayout(2, 13, 14, 14));
        panel.setOpaque(false); // Fondo transparente
        panel.setBorder(new EmptyBorder(30, 40, 30, 40)); // Margen interno

        // Bucle para crear botones de letras A a Z
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton btn = createStyledButton(String.valueOf(c), LETTER_BUTTON_SIZE);
            btn.addActionListener(e -> {
                btn.setEnabled(false); // Deshabilitar botón cuando se usa una letra
                agregarLetraUsada(e.getActionCommand().charAt(0)); // Añadir letra usada a la etiqueta
            });
            panel.add(btn); // Añadir botón al panel
        }
        return panel;
    }

    // Método que agrega una letra a la etiqueta que muestra las letras usadas
    private void agregarLetraUsada(char letra) {
        String actuales = letrasUsadasLabel.getText(); // Obtener texto actual
        // Si texto es "Ninguna", reemplazar con la letra, si no, añadir letra separada por coma
        letrasUsadasLabel.setText("Ninguna".equals(actuales) ? String.valueOf(letra) : actuales + ", " + letra);
    }

    // Método para crear un botón con estilo personalizado (color, tamaño, fuente)
    private JButton createStyledButton(String text, Dimension size) {
        JButton btn = new JButton(text); // Crear botón con texto
        btn.setPreferredSize(size); // Tamaño preferido
        btn.setBackground(BUTTON_COLOR); // Color fondo
        btn.setForeground(TEXT_COLOR);  // Color texto
        btn.setFocusPainted(false);     // No mostrar borde foco al seleccionar
        btn.setFont(new Font("SansSerif", Font.BOLD, 16)); // Fuente en negrita tamaño 16

        // Cambiar color de fondo al pasar el ratón y restaurar al salir
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BUTTON_HOVER_COLOR); // Color hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(BUTTON_COLOR); // Color normal
            }
        });
        return btn; // Devolver botón estilizado
    }

    // Método para crear un botón con texto y emoji/icono, sin funcionalidad especial
    private JButton createIconButton(String text, String icon) {
        JButton btn = new JButton(text + " " + icon); // Texto + emoji
        btn.setBackground(BUTTON_COLOR); // Color fondo
        btn.setForeground(TEXT_COLOR);   // Color texto
        btn.setFocusPainted(false);      // Sin borde foco
        btn.setFont(new Font("SansSerif", Font.BOLD, 14)); // Fuente en negrita más pequeña
        return btn; // Devolver botón
    }

    // Método auxiliar para crear etiquetas con texto, tamaño, fuente y color personalizados
    private JLabel createLabel(String text, int fontSize, int fontStyle, Color color, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(color);
        label.setFont(new Font("SansSerif", fontStyle, fontSize));
        return label;
    }
}
