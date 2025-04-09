package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;

public class Tres_VentanaEmergente extends JFrame {
    public Tres_VentanaEmergente() {
        super("Bienvenido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel mensaje = new JLabel("¡Registro exitoso!", SwingConstants.CENTER);
        mensaje.setForeground(Color.WHITE);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 16));
        getContentPane().setBackground(new Color(34, 40, 49));
        add(mensaje);
        setVisible(true);
    }
}
