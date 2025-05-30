package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que dibuja el muñeco del ahorcado.
 * Se usará dentro de PantallaAhorcado.
 */
public class MunecoAhorcado extends JPanel {

    private int errores;  // Controla el número de errores para dibujar partes del muñeco

    public MunecoAhorcado() {
        setBackground(new Color(40, 45, 55));
    }

    public void setErrores(int errores) {
        this.errores = errores;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarHorca(g);
        dibujarMuneco(g, errores);
    }

    private void dibujarHorca(Graphics g) {
        g.setColor(Color.WHITE);
        // Base
        g.fillRect(50, getHeight() - 50, 200, 10);
        // Poste
        g.fillRect(140, 50, 10, getHeight() - 100);
        // Brazo
        g.fillRect(140, 50, 120, 10);
        // Cuerda
        g.fillRect(260, 50, 5, 50);
    }

    private void dibujarMuneco(Graphics g, int errores) {
        g.setColor(Color.WHITE);
        int xCentro = 260;
        int yInicio = 100;

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
