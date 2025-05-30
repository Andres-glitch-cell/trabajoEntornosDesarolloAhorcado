package BB_modeloBBDD_02;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class inicializarUI {

    // Asumo que nivelAdmin y contraseñaBBDD están definidos en la clase
    private final int nivelAdmin = 2; // ejemplo
    private final String contraseñaBBDD = "tu_contraseña";

    // Método para inicializar la UI (construir componentes, etc.)
    private void inicializarUI() {
        JPanel menuPanel = new JPanel();

        // Botón para exportar base de datos (disponible en niveles 2 y 3)
        if (nivelAdmin >= 2) {
            JButton btnExportar = crearBoton("Exportar Base de Datos", () -> exportarBaseDatos());
            menuPanel.add(btnExportar);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Suponiendo que tienes GridBagConstraints y fondo definidos en tu clase
        // gbc.gridx = 0;
        // gbc.gridy = 1;
        // gbc.gridwidth = 2;
        // fondo.add(menuPanel, gbc);

        // Resto del código...
    }

    // Método para crear botón con acción (supongo que lo tienes)
    private JButton crearBoton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> accion.run());
        return boton;
    }

    // Método exportarBaseDatos definido fuera de inicializarUI
    private void exportarBaseDatos() {
        JTextField campoRuta = new JTextField(System.getProperty("user.home") + "/Escritorio/backup_AhorcadoAndres.sql", 20);
        Object[] mensaje = {
                "Ruta de salida (ej. ~/Escritorio/backup.sql):", campoRuta
        };

        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Exportar Base de Datos", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String rutaSalida = campoRuta.getText().trim();
        if (rutaSalida.startsWith("~")) {
            String home = System.getProperty("user.home");
            rutaSalida = rutaSalida.replace("~", home);
        }

        String[] comando = {
                "mysqldump",
                "-u", "root", // Ajusta el usuario si es diferente
                "-p" + contraseñaBBDD, // Asegúrate de que contraseñaBBDD esté definida
                "AhorcadoAndres"
        };

        try {
            ProcessBuilder check = new ProcessBuilder("which", "mysqldump");
            Process checkProcess = check.start();
            int checkExitCode = checkProcess.waitFor();
            if (checkExitCode != 0) {
                mostrarError("Error: mysqldump no está instalado o no es accesible. Contacta al administrador.");
                return;
            }

            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectOutput(new File(rutaSalida));
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            StringBuilder errores = new StringBuilder();
            String linea;
            while ((linea = errorReader.readLine()) != null) {
                errores.append(linea).append("\n");
            }

            int exitCode = proceso.waitFor();
            if (exitCode == 0 && errores.length() == 0) {
                JOptionPane.showMessageDialog(null, "✅ Exportación exitosa. Archivo guardado en: " + rutaSalida, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarError("❌ Error al exportar: " + (errores.length() > 0 ? errores.toString() : "Código de salida: " + exitCode));
            }
        } catch (IOException | InterruptedException e) {
            mostrarError("❌ Error: " + e.getMessage() + "\nVerifica los permisos en: " + rutaSalida);
        }
    }

    // Método para mostrar errores en diálogo
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
