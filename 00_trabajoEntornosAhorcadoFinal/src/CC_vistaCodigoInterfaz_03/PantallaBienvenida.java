package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.*;

public class PantallaBienvenida extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(PantallaBienvenida.class.getName());

    static {
        try {
            // Logger que escribe en archivo "ahorcado.log" con formato simple
            FileHandler fh = new FileHandler("ahorcado.log", true); // append = true
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);

            // Evitar que también imprima en consola (solo archivo)
            LOGGER.setUseParentHandlers(false);

            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("No se pudo inicializar logger de archivo: " + e.getMessage());
        }
    }

    public PantallaBienvenida() {
        super("Bienvenida al Juego del Ahorcado");
        LOGGER.info("Inicializando PantallaBienvenida...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(34, 40, 49));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        JLabel title = new JLabel("¡Bienvenido al Juego del Ahorcado!", SwingConstants.CENTER);
        title.setForeground(new Color(240, 248, 255));
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        panel.add(buttonPanel);

        JButton startButton = createButton("Iniciar Juego", new Color(100, 149, 237));
        JButton loginButton = createButton("Iniciar Sesión", new Color(100, 149, 237));
        JButton registerButton = createButton("Registrarse", new Color(100, 149, 237));

        buttonPanel.add(startButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        panel.add(Box.createVerticalStrut(15));

        JButton exportarButton = createButton("Exportar Base de Datos", new Color(50, 168, 82));
        exportarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exportarButton);

        panel.add(Box.createVerticalStrut(20));

        JButton exitButton = createButton("Salir del Juego", new Color(255, 69, 0));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exitButton);

        ActionListener buttonListener = e -> {
            String command = e.getActionCommand();
            LOGGER.info("Botón presionado: " + command);
            switch (command) {
                case "Salir del Juego" -> {
                    LOGGER.info("Confirmando salida...");
                    int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres salir?", "Salir del Juego", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        LOGGER.info("Salida confirmada. Cerrando aplicación.");
                        System.exit(0);
                    } else {
                        LOGGER.info("Salida cancelada.");
                    }
                }
                case "Iniciar Juego" -> {
                    LOGGER.info("Iniciando juego...");
                    PantallaAhorcado.mostrarVentana();
                    dispose();
                }
                case "Iniciar Sesión" -> {
                    LOGGER.info("Abriendo pantalla de inicio de sesión...");
                    IniciarSesion.mostrarVentana();
                    dispose();
                }
                case "Registrarse" -> {
                    LOGGER.info("Abriendo pantalla de registro...");
                    Registrarse.mostrarVentana();
                    dispose();
                }
                case "Exportar Base de Datos" -> {
                    LOGGER.info("Iniciando exportación de base de datos...");
                    exportarBaseDatos();
                }
            }
        };

        startButton.addActionListener(buttonListener);
        loginButton.addActionListener(buttonListener);
        registerButton.addActionListener(buttonListener);
        exitButton.addActionListener(buttonListener);
        exportarButton.addActionListener(buttonListener);

        LOGGER.info("PantallaBienvenida inicializada correctamente.");
    }

    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new PantallaBienvenida().setVisible(true));
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setActionCommand(text);
        return button;
    }

    private void exportarBaseDatos() {
        JTextField campoRuta = new JTextField("~/Escritorio/backup_AhorcadoAndres.sql", 20);
        JPasswordField campoClave = new JPasswordField(20);
        Object[] mensaje = {"Ruta de salida (ej. ~/Escritorio/backup.sql):", campoRuta, "Contraseña de MySQL:", campoClave};

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Exportar Base de Datos", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            LOGGER.info("Exportación cancelada por el usuario.");
            return;
        }

        String rutaSalida = campoRuta.getText().trim();
        String contraseña = new String(campoClave.getPassword());

        if (rutaSalida.startsWith("~")) {
            String home = System.getProperty("user.home");
            rutaSalida = rutaSalida.replace("~", home);
            LOGGER.info("Ruta expandida a: " + rutaSalida);
        } else {
            LOGGER.info("Ruta de salida: " + rutaSalida);
        }

        String mysqldumpPath;
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            mysqldumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe";
            LOGGER.info("Sistema operativo detectado: Windows");
        } else {
            mysqldumpPath = "/usr/bin/mysqldump";
            LOGGER.info("Sistema operativo detectado: Linux/Unix");
        }

        LOGGER.info("Verificando disponibilidad de mysqldump...");

        try {
            ProcessBuilder check;
            if (os.contains("win")) {
                check = new ProcessBuilder("where", "mysqldump");
            } else {
                check = new ProcessBuilder("which", "mysqldump");
            }
            Process checkProcess = check.start();
            int checkResult = checkProcess.waitFor();

            if (checkResult != 0) {
                LOGGER.severe("mysqldump no está disponible en el sistema.");
                JOptionPane.showMessageDialog(this, "❌ mysqldump no está disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                LOGGER.info("mysqldump encontrado.");
            }

            LOGGER.info("Ejecutando backup...");

            String[] comando = {mysqldumpPath, "-u", "root", "-p" + contraseña, "AhorcadoAndres"};
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectOutput(new java.io.File(rutaSalida));
            pb.redirectErrorStream(true);

            Process proceso = pb.start();

            java.io.BufferedReader errorReader = new java.io.BufferedReader(new java.io.InputStreamReader(proceso.getErrorStream()));
            StringBuilder errores = new StringBuilder();
            String linea;
            while ((linea = errorReader.readLine()) != null) {
                errores.append(linea).append("\n");
            }

            int exitCode = proceso.waitFor();

            if (exitCode == 0 && errores.length() == 0) {
                LOGGER.info("Backup realizado correctamente en: " + rutaSalida);
                JOptionPane.showMessageDialog(this, "✅ Exportación exitosa a:\n" + rutaSalida, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                LOGGER.severe("Error durante el backup. Código de salida: " + exitCode);
                LOGGER.severe("Mensajes de error:\n" + errores);
                JOptionPane.showMessageDialog(this, "❌ Error al exportar:\n" + errores, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Excepción inesperada durante exportación", e);
            JOptionPane.showMessageDialog(this, "❌ Error inesperado:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
