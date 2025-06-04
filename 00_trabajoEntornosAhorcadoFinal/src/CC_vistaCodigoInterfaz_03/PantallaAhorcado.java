package CC_vistaCodigoInterfaz_03;                 // Paquete donde está esta clase, para organizar el código.

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.*;

public class PantallaAhorcado extends JFrame {    // Clase que representa la ventana principal del juego, hereda de JFrame.

    // -------------------------
    // CONSTANTES Y CONFIGURACIÓN
    // -------------------------

    private static final Logger REGISTRADOR = Logger.getLogger(PantallaAhorcado.class.getName());   // Logger para eventos y errores.

    private static final Color COLOR_FONDO = new Color(34, 40, 49);         // Color de fondo principal de la ventana.
    private static final Color COLOR_PANEL = new Color(40, 45, 55);          // Color de fondo para paneles laterales.
    private static final Color COLOR_TEXTO = new Color(240, 248, 255);       // Color para textos visibles.
    private static final Color COLOR_ACENTO = new Color(100, 149, 237);      // Color para destacar elementos.
    private static final Color COLOR_BOTON = new Color(50, 60, 70);          // Color base para botones.
    private static final Color COLOR_BOTON_HOVER = new Color(70, 80, 90);    // Color para botón cuando el cursor está encima.
    private static final Color COLOR_LETRA_USADA_FONDO = new Color(60, 65, 80); // Fondo para letras usadas.

    private static final Dimension TAMAÑO_VENTANA = new Dimension(1100, 900);     // Dimensiones ventana principal.
    private static final Dimension TAMAÑO_BOTON_GRANDE = new Dimension(130, 48);  // Dimensiones para botones grandes.
    private static final Dimension TAMAÑO_BOTON_LETRA = new Dimension(60, 60);    // Dimensiones para botones de letras.
    private static final Dimension TAMAÑO_PANEL_IZQUIERDO = new Dimension(280, 0);// Tamaño panel izquierdo.
    private static final Dimension TAMAÑO_PANEL_AHORCADO = new Dimension(600, 600);// Tamaño panel del ahorcado.

    // Configuración del logger (bloque estático)
    static {
        try {
            Files.createDirectories(Paths.get("LOGS"));               // Crea carpeta "LOGS" si no existe.
            LogManager.getLogManager().reset();                       // Reinicia configuración global de logging.
            REGISTRADOR.setLevel(Level.ALL);                           // Logger para todos los niveles.
            FileHandler manejadorArchivo = new FileHandler("LOGS/PantallaAhorcado.log", true); // Archivo log modo append.
            manejadorArchivo.setEncoding("UTF-8");                     // Codificación UTF-8.
            manejadorArchivo.setFormatter(new SimpleFormatter());      // Formato simple para logs.
            REGISTRADOR.addHandler(manejadorArchivo);                  // Añade handler al logger.
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,                       // Muestra alerta si error creando logs.
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------------
    // COMPONENTES PRINCIPALES
    // -------------------------

    private JLabel etiquetaLetrasUsadas;       // Etiqueta para mostrar letras usadas.
    private JLabel etiquetaPuntos;              // Etiqueta para mostrar puntos jugadores.
    private JLabel etiquetaTurno;               // Etiqueta para mostrar turno actual.
    private JLabel etiquetaPalabra;             // Etiqueta para palabra a adivinar.
    private JLabel etiquetaDefinicion;          // Etiqueta para definición palabra.

    // -------------------------
    // CONSTRUCTOR
    // -------------------------

    public PantallaAhorcado() {
        super("Juego del Ahorcado - Andrés");  // Título ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Cierra ventana al salir.
        setSize(TAMAÑO_VENTANA);                            // Establece tamaño ventana.
        setResizable(true);                                 // Permite redimensionar.
        setLocationRelativeTo(null);                        // Centra ventana.
        add(crearPanelPrincipal());                         // Añade panel principal.
        REGISTRADOR.info("PantallaAhorcado inicializada."); // Log creación ventana.
    }

    // -------------------------
    // MÉTODOS PÚBLICOS
    // -------------------------

    /**
     * Muestra la ventana de forma segura en el hilo de eventos Swing.
     */
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new PantallaAhorcado().setVisible(true)); // Crear y mostrar ventana.
    }

    // -------------------------
    // CREACIÓN DE LA INTERFAZ
    // -------------------------

    /**
     * Panel principal con fondo degradado y estructura general.
     */
    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, COLOR_FONDO, 0, getHeight(), new Color(20, 25, 30))); // Degradado vertical.
                g2d.fillRect(0, 0, getWidth(), getHeight());      // Pintar fondo.
            }
        };
        panel.setOpaque(false);                                // Panel transparente para ver fondo.
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));      // Margen externo.
        panel.add(crearPanelSuperior(), BorderLayout.NORTH);  // Panel superior (título y botones).
        panel.add(crearPanelCentral(), BorderLayout.CENTER);  // Panel central.
        panel.add(crearPanelBotonesLetras(), BorderLayout.SOUTH); // Botones letras abajo.
        return panel;                                          // Devuelve panel principal.
    }

    /**
     * Panel superior con título y botones de control.
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());         // Layout BorderLayout.
        panel.setOpaque(false);                                 // Transparente.
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));      // Margen interno.
        JLabel titulo = crearEtiqueta("JUEGO DEL AHORCADO", 44, Font.BOLD, COLOR_ACENTO, JLabel.CENTER); // Título.
        panel.add(titulo, BorderLayout.NORTH);                  // Añade título arriba.

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Botones alineados izquierda.
        panelBotones.setOpaque(false);                          // Transparente.

        panelBotones.add(crearBotonIcono("Idioma", "🌐"));      // Botón idioma.
        panelBotones.add(crearBotonIcono("Partida", "🎮"));    // Botón partida.

        JButton botonSalir = crearBotonIcono("Salir", "⏻");    // Botón salir.
        botonSalir.addActionListener(e -> {
            REGISTRADOR.info("Saliendo al menú principal.");   // Log salida.
            dispose();                                          // Cierra ventana.
            PantallaBienvenida.mostrarVentana();                // Muestra menú principal.
        });
        panelBotones.add(botonSalir);                           // Añade botón salir.
        panel.add(panelBotones, BorderLayout.CENTER);           // Añade panel botones centro.
        panel.add(new JSeparator(), BorderLayout.SOUTH);        // Línea separadora abajo.

        return panel;                                           // Devuelve panel superior.
    }

    /**
     * Panel central con panel izquierdo (info) y panel juego.
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(40, 20));   // Espaciado entre componentes.
        panel.setOpaque(false);                                 // Transparente.
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));      // Margen interno.
        panel.add(crearPanelIzquierdo(), BorderLayout.WEST);   // Panel izquierdo info.
        panel.add(crearPanelJuego(), BorderLayout.CENTER);     // Panel juego.
        return panel;                                           // Devuelve panel central.
    }

    /**
     * Panel izquierdo con letras usadas, puntos y botón volver.
     */
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout vertical.
        panel.setBackground(COLOR_PANEL);                         // Color fondo panel.
        panel.setPreferredSize(TAMAÑO_PANEL_IZQUIERDO);          // Ancho fijo.
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno.

        panel.add(crearEtiqueta("Letras Usadas", 24, Font.BOLD, COLOR_TEXTO, JLabel.CENTER)); // Título sección.
        etiquetaLetrasUsadas = crearEtiqueta("Ninguna", 20, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER); // Letras usadas.
        etiquetaLetrasUsadas.setOpaque(true);                    // Habilita fondo.
        etiquetaLetrasUsadas.setBackground(COLOR_LETRA_USADA_FONDO); // Fondo oscuro para letras usadas.
        panel.add(etiquetaLetrasUsadas);                          // Añade etiqueta letras usadas.

        panel.add(Box.createVerticalStrut(50));                   // Espacio vertical.
        panel.add(crearEtiqueta("Puntos", 24, Font.BOLD, COLOR_TEXTO, JLabel.CENTER)); // Título puntos.
        etiquetaPuntos = crearEtiqueta("Jugador 1: 0", 20, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER); // Puntos iniciales.
        panel.add(etiquetaPuntos);                                 // Añade etiqueta puntos.

        panel.add(Box.createVerticalGlue());                       // Espacio flexible.
        JButton botonVolver = crearBotonEstilizado("Volver al Menú", TAMAÑO_BOTON_GRANDE); // Botón volver.
        botonVolver.addActionListener(e -> {
            dispose();                                             // Cierra ventana.
            PantallaBienvenida.mostrarVentana();                   // Muestra menú principal.
        });
        panel.add(Box.createVerticalStrut(20));                    // Espacio antes botón.
        panel.add(botonVolver);                                    // Añade botón volver.

        return panel;                                             // Devuelve panel izquierdo.
    }

    /**
     * Panel central con muñeco, palabra y turno.
     */
    private JPanel crearPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));       // Layout vertical.
        panel.setOpaque(false);                                    // Transparente.
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));         // Margen interno.

        etiquetaTurno = crearEtiqueta("Turno: Jugador 1", 28, Font.BOLD, COLOR_ACENTO, JLabel.CENTER); // Turno actual.
        panel.add(etiquetaTurno, BorderLayout.NORTH);              // Añade turno arriba.

        JPanel panelHorca;
        try {
            panelHorca = new MunecoAhorcado();                    // Panel dibujo ahorcado (clase externa).
        } catch (Exception e) {
            REGISTRADOR.warning("MunecoAhorcado no está implementado, usando JPanel vacío."); // Aviso.
            panelHorca = new JPanel();                             // Panel vacío si falla.
        }
        panelHorca.setPreferredSize(TAMAÑO_PANEL_AHORCADO);       // Tamaño fijo dibujo.
        panelHorca.setLayout(new BorderLayout());                  // Layout para definición y palabra.

        etiquetaPalabra = crearEtiqueta("Palabra: _ _ _ _ _", 38, Font.BOLD, COLOR_TEXTO, JLabel.CENTER); // Palabra oculta.
        panelHorca.add(etiquetaPalabra, BorderLayout.NORTH);       // Palabra arriba dibujo.

        etiquetaDefinicion = crearEtiqueta("Definición: ---", 16, Font.ITALIC, COLOR_TEXTO, JLabel.CENTER); // Definición.
        panelHorca.add(etiquetaDefinicion, BorderLayout.SOUTH);    // Definición abajo.

        panel.add(panelHorca, BorderLayout.CENTER);                 // Añade panel ahorcado centro.

        return panel;                                               // Devuelve panel juego.
    }

    /**
     * Panel con botones para letras A-Z.
     */
    private JPanel crearPanelBotonesLetras() {
        JPanel panel = new JPanel(new GridLayout(2, 13, 8, 8));    // 2 filas, 13 columnas, espacios.
        panel.setOpaque(false);                                     // Transparente.
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));          // Margen interno.

        for (char letra = 'A'; letra <= 'Z'; letra++) {
            final char letraFinal = letra;                          // Variable final para lambda
            JButton botonLetra = crearBotonEstilizado(String.valueOf(letraFinal), TAMAÑO_BOTON_LETRA);
            botonLetra.addActionListener(e -> {
                REGISTRADOR.info("Letra pulsada: " + letraFinal);
                botonLetra.setEnabled(false);
            });
            panel.add(botonLetra);
        }

        return panel;
    }
    // -------------------------
    // MÉTODOS AUXILIARES PARA CREAR COMPONENTES
    // -------------------------

    /**
     * Crea un JLabel con texto, tamaño, estilo, color y alineación.
     */
    private JLabel crearEtiqueta(String texto, int tamañoFuente, int estilo, Color color, int alineacion) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", estilo, tamañoFuente));
        etiqueta.setForeground(color);
        etiqueta.setHorizontalAlignment(alineacion);
        return etiqueta;
    }

    /**
     * Crea un JButton con texto y tamaño fijo, estilo personalizado.
     */
    private JButton crearBotonEstilizado(String texto, Dimension tamaño) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(tamaño);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON);
            }
        });

        return boton;
    }

    /**
     * Crea un botón con un icono (usando texto emoji para simplificar).
     */
    private JButton crearBotonIcono(String tooltip, String iconoTexto) {
        JButton boton = new JButton(iconoTexto);
        boton.setToolTipText(tooltip);
        boton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        boton.setPreferredSize(new Dimension(60, 48));
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON);
            }
        });

        return boton;
    }
}