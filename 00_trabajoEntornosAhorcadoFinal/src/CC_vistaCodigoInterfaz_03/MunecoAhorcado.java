package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

/**
 * Panel personalizado que dibuja el muñeco del ahorcado.
 * Se usará dentro de PantallaAhorcado para mostrar el estado del juego.
 */
public class MunecoAhorcado extends JPanel {

    private int errores;  // Número de errores, controla cuántas partes del muñeco se dibujan

    public MunecoAhorcado() {
        // Establecer color de fondo oscuro para resaltar el dibujo
        setBackground(new Color(40, 45, 55));
    }

    /**
     * Establece el número de errores actuales y repinta el panel.
     *
     * @param errores número de errores cometidos
     */
    public void setErrores(int errores) {
        this.errores = errores;
        repaint(); // Vuelve a dibujar el panel con el nuevo número de errores
    }

    /**
     * Sobrescribe el método paintComponent para dibujar la horca y el muñeco según los errores.
     *
     * @param g Objeto Graphics para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarHorca(g);
        dibujarMuneco(g, errores);
    }

    /**
     * Dibuja la horca completa.
     *
     * @param g Objeto Graphics.
     */
    private void dibujarHorca(Graphics g) {
        g.setColor(Color.WHITE);
        // Base horizontal de la horca
        g.fillRect(50, getHeight() - 50, 200, 10);
        // Poste vertical
        g.fillRect(140, 50, 10, getHeight() - 100);
        // Brazo horizontal
        g.fillRect(140, 50, 120, 10);
        // Cuerda vertical
        g.fillRect(260, 50, 5, 50);
    }

    /**
     * Dibuja las partes del muñeco según el número de errores.
     *
     * @param g       Objeto Graphics.
     * @param errores Número de errores para determinar las partes a dibujar.
     */
    private void dibujarMuneco(Graphics g, int errores) {
        g.setColor(Color.WHITE);
        int xCentro = 260; // Centro del muñeco (posición X)
        int yInicio = 100; // Inicio de la cabeza (posición Y)

        if (errores >= 1) { // Cabeza
            g.drawOval(xCentro - 20, yInicio, 40, 40);
        }
        if (errores >= 2) { // Cuerpo
            g.drawLine(xCentro, yInicio + 40, xCentro, yInicio + 120);
        }
        if (errores >= 3) { // Brazo izquierdo
            g.drawLine(xCentro, yInicio + 60, xCentro - 40, yInicio + 100);
        }
        if (errores >= 4) { // Brazo derecho
            g.drawLine(xCentro, yInicio + 60, xCentro + 40, yInicio + 100);
        }
        if (errores >= 5) { // Pierna izquierda
            g.drawLine(xCentro, yInicio + 120, xCentro - 40, yInicio + 180);
        }
        if (errores >= 6) { // Pierna derecha
            g.drawLine(xCentro, yInicio + 120, xCentro + 40, yInicio + 180);
        }
    }
}