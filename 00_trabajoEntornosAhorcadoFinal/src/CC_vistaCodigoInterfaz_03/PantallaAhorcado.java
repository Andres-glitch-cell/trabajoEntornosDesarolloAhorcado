// === BLOQUE 1: DECLARACIÓN DEL PAQUETE E IMPORTACIONES ===
package CC_vistaCodigoInterfaz_03;                             // Define el paquete donde reside la clase PantallaAhorcado

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;

// === BLOQUE 2: DEFINICIÓN DE LA CLASE Y CONSTANTES ===
public class PantallaAhorcado extends JFrame {                  // Declara la clase PantallaAhorcado que hereda de JFrame
    private static final Logger REGISTRADOR = Logger.getLogger(PantallaAhorcado.class.getName()); // Crea un Logger para la clase

    private static final Color COLOR_FONDO = new Color(34, 40, 49); // Color de fondo principal
    private static final Color COLOR_PANEL = new Color(40, 45, 55); // Color de paneles secundarios
    private static final Color COLOR_TEXTO = new Color(240, 248, 255); // Color de texto
    private static final Color COLOR_ACENTO = new Color(100, 149, 237); // Color de acento
    private static final Color COLOR_BOTON = new Color(50, 60, 70); // Color de botones
    private static final Color COLOR_BOTON_HOVER = new Color(70, 80, 90); // Color de botones al pasar el ratón
    private static final Color COLOR_LETRA_USADA_FONDO = new Color(60, 65, 80); // Color de fondo para letras usadas

    private static final Dimension TAMAÑO_VENTANA = new Dimension(1100, 900); // Tamaño de la ventana
    private static final Dimension TAMAÑO_BOTON_GRANDE = new Dimension(130, 48); // Tamaño de botones grandes
    private static final Dimension TAMAÑO_BOTON_LETRA = new Dimension(60, 60); // Tamaño de botones de letras
    private static final Dimension TAMAÑO_PANEL_IZQUIERDO = new Dimension(280, 0); // Tamaño del panel izquierdo
    private static final Dimension TAMAÑO_PANEL_AHORCADO = new Dimension(600, 600); // Tamaño del panel del muñeco

    // === BLOQUE 3: BLOQUE ESTÁTICO PARA CONFIGURACIÓN DEL LOGGER ===
    static {                                                   // Bloque estático que se ejecuta al cargar la clase
        try {                                                  // Inicia un bloque try para configurar el logging
            Files.createDirectories(Paths.get("LOGS"));        // Crea el directorio LOGS si no existe
            LogManager.getLogManager().reset();                // Resetea el administrador de logs
            REGISTRADOR.setLevel(Level.ALL);                   // Establece el nivel de logging para capturar todos los eventos
            FileHandler manejadorArchivo = new FileHandler("LOGS/PantallaAhorcado.log", true); // Crea manejador para logs en archivo
            manejadorArchivo.setEncoding("UTF-8");             // Establece codificación UTF-8
            manejadorArchivo.setFormatter(new SimpleFormatter()); // Aplica formato simple al archivo de log
            REGISTRADOR.addHandler(manejadorArchivo);          // Agrega el manejador de archivo al Logger
        } catch (IOException e) {                              // Captura excepciones de entrada/salida
            JOptionPane.showMessageDialog(null,                // Muestra mensaje de error
                    "No se pudo inicializar el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private final Set<Character> letrasUsadas = new HashSet<>(); // Conjunto para almacenar letras usadas
    // === BLOQUE 4: CAMPOS DE LA CLASE ===
    private JLabel etiquetaLetrasUsadas;                       // Etiqueta para mostrar letras usadas
    private JLabel etiquetaPuntos;                             // Etiqueta para mostrar puntos
    private JLabel etiquetaTurno;                              // Etiqueta para mostrar turno
    private JLabel etiquetaPalabra;                            // Etiqueta para mostrar palabra oculta
    private JLabel etiquetaDefinicion;                         // Etiqueta para mostrar definición
    private String palabraAdivinar;                            // Palabra real a adivinar
    private int errores = 0;                                   // Contador de errores
    private MunecoAhorcado panelMuneco;                        // Panel para dibujar el muñeco

    // === BLOQUE 5: CONSTRUCTOR ===
    public PantallaAhorcado() {                                // Constructor de la clase PantallaAhorcado
        super("Juego del Ahorcado - Andrés");                  // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Cierra la aplicación al cerrar la ventana
        setSize(TAMAÑO_VENTANA);                               // Establece el tamaño de la ventana
        setResizable(true);                                    // Permite redimensionar la ventana
        setLocationRelativeTo(null);                           // Centra la ventana en la pantalla
        add(crearPanelPrincipal());                            // Añade el panel principal a la ventana
        REGISTRADOR.info("PantallaAhorcado inicializada.");    // Registra mensaje de inicialización
    }

    // === BLOQUE 6: MÉTODOS ESTÁTICOS PARA MOSTRAR LA VENTANA ===
    public static void mostrarVentana(String palabra) {        // Método para mostrar la ventana con una palabra
        SwingUtilities.invokeLater(() -> {                     // Ejecuta en el EDT
            PantallaAhorcado ventana = new PantallaAhorcado(); // Crea una nueva ventana
            ventana.iniciarJuego(palabra);                     // Inicia el juego con la palabra
            ventana.setVisible(true);                          // Muestra la ventana
        });
    }

    public static void mostrarVentanaConPalabra(String palabra, String definicion) { // Método para mostrar con palabra y definición
        SwingUtilities.invokeLater(() -> {                     // Ejecuta en el EDT
            PantallaAhorcado ventana = new PantallaAhorcado(); // Crea una nueva ventana
            ventana.iniciarJuego(palabra);                     // Inicia el juego con la palabra
            ventana.setDefinicion(definicion);                 // Establece la definición
            ventana.setVisible(true);                          // Muestra la ventana
        });
    }

    // === BLOQUE 7: INICIALIZACIÓN DEL JUEGO ===
    public void iniciarJuego(String palabra) {                 // Método para iniciar el juego con una palabra
        if (palabra == null || palabra.trim().isEmpty()) {     // Verifica si la palabra es válida
            palabra = "DEFAULT";                               // Usa una palabra por defecto si es inválida
            REGISTRADOR.warning("Palabra inválida proporcionada. Usando 'DEFAULT'."); // Registra advertencia
        }
        this.palabraAdivinar = palabra.toUpperCase();          // Convierte la palabra a mayúsculas
        mostrarPalabraOculta();                                // Muestra la palabra oculta
        errores = 0;                                           // Reinicia los errores
        panelMuneco.setErrores(errores);                       // Actualiza el panel del muñeco
        letrasUsadas.clear();                                  // Limpia las letras usadas
        actualizarEtiquetaLetrasUsadas();                      // Actualiza la etiqueta de letras usadas
        etiquetaTurno.setText("Turno: Jugador 1");             // Establece el turno inicial
        etiquetaPuntos.setText("Jugador 1: 0");                // Establece los puntos iniciales
    }

    // === BLOQUE 8: ACTUALIZAR DEFINICIÓN ===
    public void setDefinicion(String definicion) {             // Método para establecer la definición
        etiquetaDefinicion.setText("Definición: " + (definicion == null || definicion.trim().isEmpty() ? "---" : definicion)); // Actualiza la etiqueta
    }

    // === BLOQUE 9: CREACIÓN DEL PANEL PRINCIPAL ===
    private JPanel crearPanelPrincipal() {                     // Método para crear el panel principal
        JPanel panel = new JPanel(new BorderLayout()) {        // Crea un panel con diseño de borde
            @Override
            protected void paintComponent(Graphics g) {        // Sobrescribe el método de pintado
                super.paintComponent(g);                       // Llama al método padre
                Graphics2D g2d = (Graphics2D) g;               // Obtiene contexto gráfico 2D
                g2d.setPaint(new GradientPaint(0, 0, COLOR_FONDO, 0, getHeight(), new Color(20, 25, 30))); // Aplica gradiente
                g2d.fillRect(0, 0, getWidth(), getHeight());   // Rellena el fondo
            }
        };
        panel.setOpaque(false);                                // Hace el panel transparente
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));      // Añade un borde vacío
        panel.add(crearPanelSuperior(), BorderLayout.NORTH);   // Añade el panel superior
        panel.add(crearPanelCentral(), BorderLayout.CENTER);   // Añade el panel central
        panel.add(crearPanelBotonesLetras(), BorderLayout.SOUTH); // Añade el panel de botones de letras
        return panel;                                          // Retorna el panel configurado
    }

    // === BLOQUE 10: MOSTRAR PALABRA OCULTA ===
    private void mostrarPalabraOculta() {                      // Método para mostrar la palabra oculta
        boolean esFrase = palabraAdivinar.contains(" ");       // Verifica si es una frase
        StringBuilder sb = new StringBuilder((esFrase ? "Frase: " : "Palabra: ")); // Inicia el texto
        for (int i = 0; i < palabraAdivinar.length(); i++) {   // Itera sobre la palabra
            char c = palabraAdivinar.charAt(i);                // Obtiene el carácter
            if (c == ' ') {                                    // Si es un espacio
                sb.append("  ");                               // Añade dos espacios
            } else if (letrasUsadas.contains(c)) {             // Si la letra ya fue usada
                sb.append(c).append(' ');                      // Muestra la letra
            } else {                                           // Si no ha sido adivinada
                sb.append("_ ");                               // Muestra un guion bajo
            }
        }
        etiquetaPalabra.setText(sb.toString());                // Actualiza la etiqueta
    }

    // === BLOQUE 11: CREACIÓN DEL PANEL SUPERIOR ===
    private JPanel crearPanelSuperior() {                      // Método para crear el panel superior
        JPanel panel = new JPanel(new BorderLayout());         // Crea un panel con diseño de borde
        panel.setOpaque(false);                                // Hace el panel transparente
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));      // Añade un borde vacío
        JLabel titulo = crearEtiqueta("JUEGO DEL AHORCADO", 44, Font.BOLD, COLOR_ACENTO, JLabel.CENTER); // Crea el título
        panel.add(titulo, BorderLayout.NORTH);                 // Añade el título
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Crea panel para botones
        panelBotones.setOpaque(false);                         // Hace el panel transparente
        JButton botonSalir = crearBoton("Salir", "⏻");         // Crea el botón de salir
        botonSalir.addActionListener(e -> {                    // Añade acción al botón
            REGISTRADOR.info("Saliendo al menú principal.");   // Registra mensaje de salida
            dispose();                                         // Cierra la ventana
            PantallaBienvenida.mostrarVentana();               // Muestra la ventana de bienvenida
        });
        panelBotones.add(botonSalir);                          // Añade el botón de salir
        panel.add(panelBotones, BorderLayout.CENTER);          // Añade el panel de botones
        panel.add(new JSeparator(), BorderLayout.SOUTH);       // Añade un separador
        return panel;                                          // Retorna el panel configurado
    }

    // === BLOQUE 12: CREACIÓN DEL PANEL CENTRAL ===
    private JPanel crearPanelCentral() {                       // Método para crear el panel central
        JPanel panel = new JPanel(new BorderLayout(40, 20));   // Crea un panel con diseño de borde
        panel.setOpaque(false);                                // Hace el panel transparente
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));      // Añade un borde vacío
        panel.add(crearPanelIzquierdo(), BorderLayout.WEST);   // Añade el panel izquierdo
        panel.add(crearPanelJuego(), BorderLayout.CENTER);     // Añade el panel del juego
        return panel;                                          // Retorna el panel configurado
    }

    // === BLOQUE 13: CREACIÓN DEL PANEL IZQUIERDO ===
    private JPanel crearPanelIzquierdo() {                     // Método para crear el panel izquierdo
        JPanel panel = new JPanel();                           // Crea un panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Establece un diseño vertical
        panel.setBackground(COLOR_PANEL);                      // Configura el color de fondo
        panel.setPreferredSize(TAMAÑO_PANEL_IZQUIERDO);        // Establece el tamaño preferido
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Añade un borde vacío

        panel.add(crearEtiqueta("Letras Usadas", 24, Font.BOLD, COLOR_TEXTO, JLabel.CENTER)); // Añade el título de letras usadas
        etiquetaLetrasUsadas = crearEtiqueta("Ninguna", 20, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER); // Crea la etiqueta de letras usadas
        etiquetaLetrasUsadas.setOpaque(true);                  // Hace la etiqueta opaca
        etiquetaLetrasUsadas.setBackground(COLOR_LETRA_USADA_FONDO); // Configura el color de fondo
        panel.add(etiquetaLetrasUsadas);                       // Añade la etiqueta al panel

        panel.add(Box.createVerticalStrut(50));                // Añade un espacio vertical
        panel.add(crearEtiqueta("Puntos", 24, Font.BOLD, COLOR_TEXTO, JLabel.CENTER)); // Añade el título de puntos
        etiquetaPuntos = crearEtiqueta("Jugador 1: 0", 20, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER); // Crea la etiqueta de puntos
        panel.add(etiquetaPuntos);                             // Añade la etiqueta al panel

        panel.add(Box.createVerticalGlue());                   // Añade espacio expansible
        JButton botonVolver = crearBotonEstilizado("Volver al Menú", TAMAÑO_BOTON_GRANDE); // Crea el botón de volver
        botonVolver.addActionListener(e -> {                   // Añade acción al botón
            dispose();                                         // Cierra la ventana
            PantallaBienvenida.mostrarVentana();               // Muestra la ventana de bienvenida
        });
        panel.add(Box.createVerticalStrut(20));                // Añade un espacio vertical
        panel.add(botonVolver);                                // Añade el botón al panel

        return panel;                                          // Retorna el panel configurado
    }

    // === BLOQUE 14: CREACIÓN DEL PANEL DE JUEGO ===
    private JPanel crearPanelJuego() {                         // Método para crear el panel del juego
        JPanel panel = new JPanel(new BorderLayout(0, 20));    // Crea un panel con diseño de borde
        panel.setOpaque(false);                                // Hace el panel transparente
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));      // Añade un borde vacío

        etiquetaTurno = crearEtiqueta("Turno: Jugador 1", 28, Font.BOLD, COLOR_ACENTO, JLabel.CENTER); // Crea la etiqueta de turno
        panel.add(etiquetaTurno, BorderLayout.NORTH);          // Añade la etiqueta al panel

        panelMuneco = new MunecoAhorcado();                    // Crea el panel del muñeco
        panelMuneco.setPreferredSize(TAMAÑO_PANEL_AHORCADO);   // Establece el tamaño del panel
        panel.add(panelMuneco, BorderLayout.CENTER);           // Añade el panel del muñeco

        etiquetaPalabra = crearEtiqueta("Palabra: ", 34, Font.BOLD, COLOR_TEXTO, JLabel.CENTER); // Crea la etiqueta de la palabra
        panel.add(etiquetaPalabra, BorderLayout.SOUTH);        // Añade la etiqueta al panel

        etiquetaDefinicion = crearEtiqueta("Definición: ---", 22, Font.PLAIN, COLOR_TEXTO, JLabel.CENTER); // Crea la etiqueta de definición
        panel.add(etiquetaDefinicion, BorderLayout.EAST);      // Añade la etiqueta al panel

        return panel;                                          // Retorna el panel configurado
    }

    // === BLOQUE 15: CREACIÓN DEL PANEL DE BOTONES DE LETRAS ===
    private JPanel crearPanelBotonesLetras() {                 // Método para crear el panel de botones de letras
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6)); // Crea un panel con diseño de flujo
        panel.setOpaque(false);                                // Hace el panel transparente

        String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";        // Define las letras disponibles
        for (int i = 0; i < letras.length(); i++) {            // Itera sobre las letras
            char letra = letras.charAt(i);                     // Obtiene la letra
            JButton boton = crearBotonLetra(letra);            // Crea el botón de la letra
            panel.add(boton);                                  // Añade el botón al panel
        }
        return panel;                                          // Retorna el panel configurado
    }

    // === BLOQUE 16: CREACIÓN DE BOTÓN DE LETRA ===
    private JButton crearBotonLetra(char letra) {              // Método para crear un botón de letra
        JButton botonLetra = new JButton(String.valueOf(letra)); // Crea el botón con la letra
        botonLetra.setPreferredSize(TAMAÑO_BOTON_LETRA);       // Establece el tamaño preferido
        botonLetra.setBackground(COLOR_BOTON);                 // Configura el color de fondo
        botonLetra.setForeground(COLOR_TEXTO);                 // Configura el color del texto
        botonLetra.setFont(new Font("Arial", Font.BOLD, 26));  // Configura la fuente
        botonLetra.setFocusPainted(false);                     // Desactiva el borde de foco
        botonLetra.addActionListener(e -> {                    // Añade acción al botón
            botonLetra.setEnabled(false);                      // Desactiva el botón
            procesarLetra(letra);                              // Procesa la letra seleccionada
        });
        return botonLetra;                                     // Retorna el botón configurado
    }

    // === BLOQUE 17: PROCESAR LETRA ===
    private void procesarLetra(char letra) {                   // Método para procesar una letra seleccionada
        letra = Character.toUpperCase(letra);                  // Convierte la letra a mayúscula
        if (letrasUsadas.contains(letra)) {                    // Verifica si la letra ya fue usada
            return;                                            // Sale si ya fue usada
        }
        letrasUsadas.add(letra);                               // Añade la letra al conjunto
        actualizarEtiquetaLetrasUsadas();                       // Actualiza la etiqueta de letras usadas

        if (palabraAdivinar.indexOf(letra) >= 0) {             // Verifica si la letra está en la palabra
            REGISTRADOR.info("Letra correcta: " + letra);      // Registra letra correcta
            mostrarPalabraOculta();                            // Actualiza la palabra mostrada
            if (palabraCompletaAdivinada()) {                // Verifica si se adivinó la palabra
                JOptionPane.showMessageDialog(this, "¡Has ganado! La palabra era: " + palabraAdivinar); // Muestra mensaje de victoria
                REGISTRADOR.info("Juego ganado.");             // Registra victoria
            }
        } else {                                               // Si la letra no está en la palabra
            REGISTRADOR.info("Letra incorrecta: " + letra);    // Registra letra incorrecta
            errores++;                                         // Incrementa los errores
            panelMuneco.setErrores(errores);                   // Actualiza el muñeco
            if (errores >= 6) {                               // Verifica si se alcanzó el límite de errores
                JOptionPane.showMessageDialog(this, "¡Has perdido! La palabra era: " + palabraAdivinar); // Muestra mensaje de derrota
                REGISTRADOR.info("Juego perdido.");            // Registra derrota
            }
        }
    }

    // === BLOQUE 18: VERIFICAR SI LA PALABRA FUE ADIVINADA ===
    private boolean palabraCompletaAdivinada() {               // Método para verificar si se adivinó la palabra
        for (int i = 0; i < palabraAdivinar.length(); i++) {   // Itera sobre la palabra
            char c = palabraAdivinar.charAt(i);                // Obtiene el carácter
            if (c != ' ' && !letrasUsadas.contains(c)) {       // Verifica si hay letras no adivinadas
                return false;                                  // Retorna falso si falta alguna letra
            }
        }
        return true;                                           // Retorna verdadero si se adivinó todo
    }

    // === BLOQUE 19: ACTUALIZAR ETIQUETA DE LETRAS USADAS ===
    private void actualizarEtiquetaLetrasUsadas() {            // Método para actualizar la etiqueta de letras usadas
        StringBuilder sb = new StringBuilder();                // Crea un StringBuilder
        if (letrasUsadas.isEmpty()) {                          // Verifica si no hay letras usadas
            sb.append("Ninguna");                              // Establece texto por defecto
        } else {                                               // Si hay letras usadas
            char[] letras = new char[letrasUsadas.size()];     // Crea un arreglo para las letras
            int index = 0;                                     // Índice para el arreglo
            for (Character c : letrasUsadas) {                 // Itera sobre las letras usadas
                letras[index++] = c;                           // Añade la letra al arreglo
            }
            for (int i = 0; i < letras.length; i++) {          // Itera sobre el arreglo
                sb.append(letras[i]).append(' ');              // Añade la letra y un espacio
            }
        }
        etiquetaLetrasUsadas.setText(sb.toString());           // Actualiza la etiqueta
    }

    // === BLOQUE 20: CREAR ETIQUETA ===
    private JLabel crearEtiqueta(String texto, int tamañoFuente, int estilo, Color color, int alineacion) { // Método para crear una etiqueta
        JLabel etiqueta = new JLabel(texto);                   // Crea la etiqueta
        etiqueta.setFont(new Font("Arial", estilo, tamañoFuente)); // Configura la fuente
        etiqueta.setForeground(color);                         // Configura el color del texto
        etiqueta.setHorizontalAlignment(alineacion);           // Configura la alineación
        return etiqueta;                                       // Retorna la etiqueta configurada
    }

    // === BLOQUE 21: CREAR BOTÓN ESTILIZADO ===
    private JButton crearBotonEstilizado(String texto, Dimension tamaño) { // Método para crear un botón estilizado
        JButton boton = new JButton(texto);                    // Crea el botón
        boton.setPreferredSize(tamaño);                        // Establece el tamaño preferido
        boton.setFont(new Font("Arial", Font.BOLD, 22));       // Configura la fuente
        boton.setBackground(COLOR_BOTON);                      // Configura el color de fondo
        boton.setForeground(COLOR_TEXTO);                      // Configura el color del texto
        boton.setFocusPainted(false);                          // Desactiva el borde de foco
        boton.setOpaque(true);                                 // Hace el botón opaco
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añade un borde vacío
        boton.addMouseListener(new java.awt.event.MouseAdapter() { // Añade listener para efectos de ratón
            public void mouseEntered(java.awt.event.MouseEvent evt) { // Al entrar el ratón
                boton.setBackground(COLOR_BOTON_HOVER);            // Cambia el color de fondo
            }

            public void mouseExited(java.awt.event.MouseEvent evt) { // Al salir el ratón
                boton.setBackground(COLOR_BOTON);                  // Restaura el color de fondo
            }
        });
        return boton;                                          // Retorna el botón configurado
    }

    // === BLOQUE 22: CREAR BOTÓN ===
    private JButton crearBoton(String texto, String icono) {   // Método para crear un botón con icono
        JButton boton = new JButton(icono + " " + texto);      // Crea el botón con icono y texto
        boton.setFont(new Font("Arial", Font.BOLD, 18));       // Configura la fuente
        boton.setForeground(COLOR_TEXTO);                      // Configura el color del texto
        boton.setBackground(COLOR_BOTON);                      // Configura el color de fondo
        boton.setFocusPainted(false);                          // Desactiva el borde de foco
        boton.setPreferredSize(TAMAÑO_BOTON_GRANDE);           // Establece el tamaño preferido
        boton.setOpaque(true);                                 // Hace el botón opaco
        boton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // Añade un borde vacío
        boton.addMouseListener(new java.awt.event.MouseAdapter() { // Añade listener para efectos de ratón
            public void mouseEntered(java.awt.event.MouseEvent evt) { // Al entrar el ratón
                boton.setBackground(COLOR_BOTON_HOVER);            // Cambia el color de fondo
            }

            public void mouseExited(java.awt.event.MouseEvent evt) { // Al salir el ratón
                boton.setBackground(COLOR_BOTON);                  // Restaura el color de fondo
            }
        });
        return boton;                                          // Retorna el botón configurado
    }

    // === BLOQUE 23: CLASE INTERNA PARA EL MUÑECO ===
    private static class MunecoAhorcado extends JPanel {       // Clase interna para dibujar el muñeco
        private int errores = 0;                               // Contador de errores

        public void setErrores(int errores) {                  // Método para establecer los errores
            this.errores = errores;                            // Actualiza los errores
            repaint();                                         // Redibuja el panel
        }

        @Override
        protected void paintComponent(Graphics g) {            // Sobrescribe el método de pintado
            super.paintComponent(g);                           // Llama al método padre
            Graphics2D g2 = (Graphics2D) g;                    // Obtiene contexto gráfico 2D

            g2.setColor(COLOR_PANEL);                          // Configura el color de fondo
            g2.fillRect(0, 0, getWidth(), getHeight());        // Rellena el fondo

            g2.setStroke(new BasicStroke(5));                  // Configura el grosor de la línea
            g2.setColor(COLOR_TEXTO);                          // Configura el color de las líneas
            g2.drawLine(50, getHeight() - 50, 200, getHeight() - 50); // Dibuja la base
            g2.drawLine(125, getHeight() - 50, 125, 50);       // Dibuja el palo vertical
            g2.drawLine(125, 50, 350, 50);                    // Dibuja el palo horizontal
            g2.drawLine(350, 50, 350, 100);                   // Dibuja la cuerda

            if (errores > 0) {                                 // Dibuja la cabeza
                g2.drawOval(320, 100, 60, 60);                // Cabeza
            }
            if (errores > 1) {                                 // Dibuja el cuerpo
                g2.drawLine(350, 160, 350, 300);              // Cuerpo
            }
            if (errores > 2) {                                 // Dibuja el brazo izquierdo
                g2.drawLine(350, 180, 300, 240);              // Brazo izquierdo
            }
            if (errores > 3) {                                 // Dibuja el brazo derecho
                g2.drawLine(350, 180, 400, 240);              // Brazo derecho
            }
            if (errores > 4) {                                 // Dibuja la pierna izquierda
                g2.drawLine(350, 300, 300, 370);              // Pierna izquierda
            }
            if (errores > 5) {                                 // Dibuja la pierna derecha
                g2.drawLine(350, 300, 400, 370);              // Pierna derecha
            }
        }
    }
}