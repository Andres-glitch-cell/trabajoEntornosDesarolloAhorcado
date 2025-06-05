package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;

public class PantallaAhorcado extends JFrame {

    private static final Logger REGISTRADOR = Logger.getLogger(PantallaAhorcado.class.getName());

    private static final Color COLOR_FONDO = new Color(34, 40, 49);
    private static final Color COLOR_PANEL = new Color(40, 45, 55);
    private static final Color COLOR_TEXTO = new Color(240, 248, 255);
    private static final Color COLOR_ACENTO = new Color(100, 149, 237);
    private static final Color COLOR_BOTON = new Color(50, 60, 70);
    private static final Color COLOR_BOTON_HOVER = new Color(70, 80, 90);
    private static final Color COLOR_LETRA_USADA_FONDO = new Color(60, 65, 80);

    private static final Dimension TAMAÑO_VENTANA = new Dimension(1100, 900);
    private static final Dimension TAMAÑO_BOTON_GRANDE = new Dimension(130, 48);
    private static final Dimension TAMAÑO_BOTON_LETRA = new Dimension(60, 60);
    private static final Dimension TAMAÑO_PANEL_IZQUIERDO = new Dimension(280, 0);
    private static final Dimension TAMAÑO_PANEL_AHORCADO = new Dimension(600, 600);

    static {
        try {
            Files.createDirectories(Paths.get("LOGS"));
            LogManager.getLogManager().reset();
            REGISTRADOR.setLevel(Level.ALL);
            FileHandler manejadorArchivo = new FileHandler("LOGS/PantallaAhorcado.log", true);
            manejadorArchivo.setEncoding("UTF-8");
            manejadorArchivo.setFormatter(new SimpleFormatter());
            REGISTRADOR.addHandler(manejadorArchivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------------
    // COMPONENTES PRINCIPALES
    // -------------------------

    private JLabel etiquetaLetrasUsadas;
    private JLabel etiquetaPuntos;
    private JLabel etiquetaTurno;
    private JLabel etiquetaPalabra;
    private JLabel etiquetaDefinicion;

    // Conjunto para almacenar letras usadas
    private final Set<Character> letrasUsadas = new HashSet<>();

    // Nueva variable para la palabra real a adivinar
    private String palabraAdivinar;

    // Controla los errores para el muñeco
    private int errores = 0;

    // Panel del muñeco
    private MunecoAhorcado panelMuneco;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------

    public PantallaAhorcado() {
        super("Juego del Ahorcado - Andrés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(TAMAÑO_VENTANA);
        setResizable(true);
        setLocationRelativeTo(null);
        add(crearPanelPrincipal());

        // No iniciar juego ni definir palabra aquí.
        // Palabra y definición se setean cuando se llama iniciarJuego() y setDefinicion()

        REGISTRADOR.info("PantallaAhorcado inicializada.");
    }

    /**
     * Método estático para mostrar la ventana con la palabra indicada (sin definición)
     */
    public static void mostrarVentana(String palabra) {
        SwingUtilities.invokeLater(() -> {
            PantallaAhorcado ventana = new PantallaAhorcado();
            ventana.iniciarJuego(palabra);
            ventana.setVisible(true);
        });
    }

    /**
     * Método estático para mostrar la ventana con palabra y definición
     */
    public static void mostrarVentanaConPalabra(String palabra, String definicion) {
        SwingUtilities.invokeLater(() -> {
            PantallaAhorcado ventana = new PantallaAhorcado();
            ventana.iniciarJuego(palabra);
            ventana.setDefinicion(definicion);
            ventana.setVisible(true);
        });
    }

    /**
     * Método para iniciar el juego con la palabra que se quiere adivinar
     */
    public void iniciarJuego(String palabra) {
        this.palabraAdivinar = palabra.toUpperCase();
        mostrarPalabraOculta();
        errores = 0;
        panelMuneco.setErrores(errores);
        letrasUsadas.clear();
        actualizarEtiquetaLetrasUsadas();
        etiquetaTurno.setText("Turno: Jugador 1");
        etiquetaPuntos.setText("Jugador 1: 0");
    }

    /**
     * Método para actualizar la etiqueta de definición
     */
    public void setDefinicion(String definicion) {
        etiquetaDefinicion.setText("Definición: " + (definicion == null || definicion.trim().isEmpty() ? "---" : definicion));
    }

    /**
     * Muestra la palabra oculta en la etiqueta con guiones bajos
     */
    private void mostrarPalabraOculta() {
        StringBuilder sb = new StringBuilder("Palabra: ");
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            char c = palabraAdivinar.charAt(i);
            if (c == ' ') {
                sb.append("  "); // Espacio doble para separar palabras
            } else if (letrasUsadas.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        etiquetaPalabra.setText(sb.toString());
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, COLOR_FONDO, 0, getHeight(), new Color(20, 25, 30)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.add(crearPanelSuperior(), BorderLayout.NORTH);
        panel.add(crearPanelCentral(), BorderLayout.CENTER);
        panel.add(crearPanelBotonesLetras(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel titulo = crearEtiqueta("JUEGO DEL AHORCADO", 44, Font.BOLD, COLOR_ACENTO, JLabel.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelBotones.setOpaque(false);

        panelBotones.add(crearBotonIcono("Idioma", "🌐"));
        panelBotones.add(crearBotonIcono("Partida", "🎮"));

        JButton botonSalir = crearBotonIcono("Salir", "⏻");
        botonSalir.addActionListener(e -> {
            REGISTRADOR.info("Saliendo al menú principal.");
            dispose();
            // PantallaBienvenida.mostrarVentana(); // Si tienes esta clase, descomenta
        });
        panelBotones.add(botonSalir);
        panel.add(panelBotones, BorderLayout.CENTER);
        panel.add(new JSeparator(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(40, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        panel.add(crearPanelIzquierdo(), BorderLayout.WEST);
        panel.add(crearPanelJuego(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_PANEL);
        panel.setPreferredSize(TAMAÑO_PANEL_IZQUIERDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(crearEtiqueta("Letras Usadas", 24, Font.BOLD, COLOR_TEXTO, JLabel.CENTER));
        etiquetaLetrasUsadas = crearEtiqueta("Ninguna", 20, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER);
        etiquetaLetrasUsadas.setOpaque(true);
        etiquetaLetrasUsadas.setBackground(COLOR_LETRA_USADA_FONDO);
        panel.add(etiquetaLetrasUsadas);

        panel.add(Box.createVerticalStrut(50));
        panel.add(crearEtiqueta("Puntos", 24, Font.BOLD, COLOR_TEXTO, JLabel.CENTER));
        etiquetaPuntos = crearEtiqueta("Jugador 1: 0", 20, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER);
        panel.add(etiquetaPuntos);

        panel.add(Box.createVerticalGlue());
        JButton botonVolver = crearBotonEstilizado("Volver al Menú", TAMAÑO_BOTON_GRANDE);
        botonVolver.addActionListener(e -> {
            dispose();
            // PantallaBienvenida.mostrarVentana(); // Si tienes esta clase, descomenta
        });
        panel.add(Box.createVerticalStrut(20));
        panel.add(botonVolver);

        return panel;
    }

    private JPanel crearPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        etiquetaTurno = crearEtiqueta("Turno: Jugador 1", 28, Font.BOLD, COLOR_ACENTO, JLabel.CENTER);
        panel.add(etiquetaTurno, BorderLayout.NORTH);

        panelMuneco = new MunecoAhorcado();
        panelMuneco.setPreferredSize(TAMAÑO_PANEL_AHORCADO);
        panel.add(panelMuneco, BorderLayout.CENTER);

        etiquetaPalabra = crearEtiqueta("Palabra: ", 34, Font.BOLD, COLOR_TEXTO, JLabel.CENTER);
        panel.add(etiquetaPalabra, BorderLayout.SOUTH);

        etiquetaDefinicion = crearEtiqueta("Definición: ---", 22, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER);
        panel.add(etiquetaDefinicion, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelBotonesLetras() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        panel.setOpaque(false);

        String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        for (char letra : letras.toCharArray()) {
            JButton botonLetra = new JButton(String.valueOf(letra));
            botonLetra.setPreferredSize(TAMAÑO_BOTON_LETRA);
            botonLetra.setBackground(COLOR_BOTON);
            botonLetra.setForeground(COLOR_TEXTO);
            botonLetra.setFont(new Font("Arial", Font.BOLD, 26));
            botonLetra.setFocusPainted(false);

            botonLetra.addActionListener(e -> {
                botonLetra.setEnabled(false);
                procesarLetra(letra);
            });

            panel.add(botonLetra);
        }
        return panel;
    }

    private void procesarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        if (letrasUsadas.contains(letra)) {
            return;
        }
        letrasUsadas.add(letra);
        actualizarEtiquetaLetrasUsadas();

        if (palabraAdivinar.indexOf(letra) >= 0) {
            REGISTRADOR.info("Letra correcta: " + letra);
            mostrarPalabraOculta();
            if (palabraCompletaAdivinada()) {
                JOptionPane.showMessageDialog(this, "¡Has ganado! La palabra era: " + palabraAdivinar);
                REGISTRADOR.info("Juego ganado.");
            }
        } else {
            REGISTRADOR.info("Letra incorrecta: " + letra);
            errores++;
            panelMuneco.setErrores(errores);
            if (errores >= 6) {
                JOptionPane.showMessageDialog(this, "¡Has perdido! La palabra era: " + palabraAdivinar);
                REGISTRADOR.info("Juego perdido.");
            }
        }
    }

    private boolean palabraCompletaAdivinada() {
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            char c = palabraAdivinar.charAt(i);
            if (c != ' ' && !letrasUsadas.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private void actualizarEtiquetaLetrasUsadas() {
        if (letrasUsadas.isEmpty()) {
            etiquetaLetrasUsadas.setText("Ninguna");
        } else {
            StringBuilder sb = new StringBuilder();
            for (char c : letrasUsadas) {
                sb.append(c).append(' ');
            }
            etiquetaLetrasUsadas.setText(sb.toString());
        }
    }

    private JLabel crearEtiqueta(String texto, int tamañoFuente, int estilo, Color color, int alineacion) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", estilo, tamañoFuente));
        etiqueta.setForeground(color);
        etiqueta.setHorizontalAlignment(alineacion);
        return etiqueta;
    }

    private JButton crearBotonIcono(String texto, String icono) {
        JButton boton = new JButton(icono + " " + texto);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setForeground(COLOR_TEXTO);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JButton crearBotonEstilizado(String texto, Dimension tamaño) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(tamaño);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_TEXTO);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    // Clase interna para el muñeco del ahorcado (puedes personalizarla)
    private static class MunecoAhorcado extends JPanel {

        private int errores;

        public void setErrores(int errores) {
            this.errores = errores;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibuja el muñeco según errores (puedes mejorar con dibujos más complejos)
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLACK);

            // Horca
            g2d.drawLine(50, 550, 250, 550);  // Base
            g2d.drawLine(150, 550, 150, 150); // Poste vertical
            g2d.drawLine(150, 150, 350, 150); // Poste horizontal
            g2d.drawLine(350, 150, 350, 200); // Cuerda

            if (errores >= 1) {
                // Cabeza
                g2d.drawOval(320, 200, 60, 60);
            }
            if (errores >= 2) {
                // Cuerpo
                g2d.drawLine(350, 260, 350, 370);
            }
            if (errores >= 3) {
                // Brazo izquierdo
                g2d.drawLine(350, 290, 300, 330);
            }
            if (errores >= 4) {
                // Brazo derecho
                g2d.drawLine(350, 290, 400, 330);
            }
            if (errores >= 5) {
                // Pierna izquierda
                g2d.drawLine(350, 370, 300, 430);
            }
            if (errores >= 6) {
                // Pierna derecha
                g2d.drawLine(350, 370, 400, 430);
            }
        }
    }
}