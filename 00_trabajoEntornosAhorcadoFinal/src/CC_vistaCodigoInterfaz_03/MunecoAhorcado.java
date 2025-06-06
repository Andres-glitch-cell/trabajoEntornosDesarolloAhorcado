package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class MunecoAhorcado extends JPanel {

    private int errores;

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
        g.setColor(Color.WHITE);

        // Dibuja horca
        g.fillRect(50, getHeight() - 50, 200, 10);
        g.fillRect(140, 50, 10, getHeight() - 100);
        g.fillRect(140, 50, 120, 10);
        g.fillRect(260, 50, 5, 50);

        int x = 260, y = 100;

        if (errores >= 1) g.drawOval(x - 20, y, 40, 40);           // Cabeza
        if (errores >= 2) g.drawLine(x, y + 40, x, y + 120);      // Cuerpo
        if (errores >= 3) g.drawLine(x, y + 60, x - 40, y + 100); // Brazo izquierdo
        if (errores >= 4) g.drawLine(x, y + 60, x + 40, y + 100); // Brazo derecho
        if (errores >= 5) g.drawLine(x, y + 120, x - 40, y + 180);// Pierna izquierda
        if (errores >= 6) g.drawLine(x, y + 120, x + 40, y + 180);// Pierna derecha
    }
}
