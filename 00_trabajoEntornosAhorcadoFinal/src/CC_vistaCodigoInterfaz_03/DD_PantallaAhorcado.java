package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la ventana principal del juego del ahorcado.
 * Muestra el área de la horca, letras usadas, puntos de los jugadores y botones para interactuar.
 *
 * @author Andrés Fernández Salaud
 * @version Ahorcado_v.0.0.4
 */
public class DD_PantallaAhorcado extends JFrame {

    /**
     * Constructor que inicializa la ventana del juego del ahorcado.
     * Configura paneles para mostrar la horca, letras usadas, puntos, botones de letras A-Z
     * y opciones de navegación. Los botones de letras y algunas opciones no son funcionales.
     */
    public DD_PantallaAhorcado() {
        // Configulación básica de la ventana
        super("Juego del Ahorcado - Andrés Fernández Salaud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setResizable(true);
        setLocationRelativeTo(null);

        // Panel principal con márgenes
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(34, 40, 49));
        fondo.setBorder(new EmptyBorder(20, 30, 20, 30));
        add(fondo);

        // Título y botones superiores
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(34, 40, 49));
        JLabel titulo = new JLabel("PANTALLA JUEGO", JLabel.CENTER);
        titulo.setForeground(new Color(100, 149, 237));
        titulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(new Color(34, 40, 49));
        String[] botonesTexto = new String[]{"idioma", "partida", "Salir"};
        for (int i = 0; i < botonesTexto.length; i++) {
            JButton boton = new JButton(botonesTexto[i]);
            boton.setBackground(new Color(50, 60, 70));
            boton.setForeground(Color.WHITE);
            boton.setFont(new Font("SansSerif", Font.BOLD, 16));
            boton.setFocusPainted(false);
            boton.setPreferredSize(new Dimension(100, 40));
            if (botonesTexto[i].equals("Salir")) {
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EE_MenuDesplegable ventanaMenu = new EE_MenuDesplegable();
                        ventanaMenu.setVisible(true);
                        dispose();
                    }
                });
            }
            panelBotones.add(boton);
        }
        panelSuperior.add(panelBotones, BorderLayout.CENTER);
        fondo.add(panelSuperior, BorderLayout.NORTH);

        // Panel central (letras usadas, puntos y área de juego)
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(34, 40, 49));
        panelCentral.setBorder(new EmptyBorder(10, 30, 10, 30));

        // Panel izquierdo (letras usadas y puntos)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(34, 40, 49));
        panelIzquierdo.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel letrasUsadasLabel = new JLabel("Usadas");
        letrasUsadasLabel.setForeground(new Color(240, 248, 255));
        letrasUsadasLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        panelIzquierdo.add(letrasUsadasLabel);

        JLabel letrasUsadas = new JLabel("X, L, W, M");
        letrasUsadas.setForeground(Color.WHITE);
        letrasUsadas.setFont(new Font("SansSerif", Font.PLAIN, 16));
        letrasUsadas.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
        panelIzquierdo.add(letrasUsadas);
        panelIzquierdo.add(Box.createVerticalStrut(30));

        JLabel puntosLabel = new JLabel("Puntos");
        puntosLabel.setForeground(new Color(240, 248, 255));
        puntosLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        panelIzquierdo.add(puntosLabel);

        JLabel puntosJugadores = new JLabel("<html>Nombre1: 0<br>Nombre2: 0<br>Nombren: 0</html>");
        puntosJugadores.setForeground(Color.WHITE);
        puntosJugadores.setFont(new Font("SansSerif", Font.PLAIN, 16));
        puntosJugadores.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
        panelIzquierdo.add(puntosJugadores);

        panelCentral.add(panelIzquierdo, BorderLayout.WEST);

        // Panel derecho (área de juego)
        JPanel panelJuego = new JPanel(new BorderLayout());
        panelJuego.setBackground(new Color(34, 40, 49));

        JLabel turnoLabel = new JLabel("Turno: _ _ _ _ _ _ _ _", JLabel.CENTER);
        turnoLabel.setForeground(new Color(240, 248, 255));
        turnoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        panelJuego.add(turnoLabel, BorderLayout.NORTH);

        // Área de la horca
        JPanel panelHorca = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(34, 40, 49));

                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(4));

                // Dibujar la horca (en negro)
                g2d.setColor(Color.BLACK);
                g2d.drawLine(80, 450, 300, 450);
                g2d.drawLine(190, 450, 190, 150);
                g2d.drawLine(190, 150, 340, 150);
                g2d.drawLine(340, 150, 340, 180);

                // Dibujar el muñeco (en blanco)
                g2d.setColor(Color.WHITE);
                g2d.drawOval(310, 180, 60, 60);
                g2d.fillOval(325, 195, 6, 6);
                g2d.fillOval(349, 195, 6, 6);
                g2d.drawRect(325, 240, 30, 70);
                g2d.drawLine(325, 260, 295, 290);
                g2d.drawLine(355, 260, 385, 290);
                g2d.drawLine(325, 310, 295, 350);
                g2d.drawLine(355, 310, 385, 350);
            }
        };
        panelHorca.setLayout(new BorderLayout());
        panelHorca.setPreferredSize(new Dimension(500, 500));
        panelHorca.setBackground(new Color(34, 40, 49));
        panelHorca.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        JLabel palabraLabel = new JLabel("_ _ _ _", JLabel.CENTER);
        palabraLabel.setForeground(Color.WHITE);
        palabraLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        panelHorca.add(palabraLabel, BorderLayout.SOUTH);

        JLabel definicionLabel = new JLabel("Definición o descripción de la palabra", JLabel.CENTER);
        definicionLabel.setForeground(new Color(240, 248, 255));
        definicionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panelHorca.add(definicionLabel, BorderLayout.NORTH);

        panelJuego.add(panelHorca, BorderLayout.CENTER);
        panelCentral.add(panelJuego, BorderLayout.CENTER);
        fondo.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior (botones de letras A-Z)
        JPanel panelLetras = new JPanel(new GridLayout(2, 13, 8, 8));
        panelLetras.setBackground(new Color(34, 40, 49));
        panelLetras.setBorder(new EmptyBorder(20, 30, 20, 30));

        for (char c = 'A'; c <= 'Z'; c++) {
            JButton btnLetra = new JButton(String.valueOf(c));
            btnLetra.setBackground(new Color(50, 60, 70));
            btnLetra.setForeground(Color.WHITE);
            btnLetra.setFont(new Font("SansSerif", Font.BOLD, 16));
            btnLetra.setFocusPainted(false);
            btnLetra.setPreferredSize(new Dimension(50, 50));
            panelLetras.add(btnLetra);
        }
        fondo.add(panelLetras, BorderLayout.SOUTH);
    }
}