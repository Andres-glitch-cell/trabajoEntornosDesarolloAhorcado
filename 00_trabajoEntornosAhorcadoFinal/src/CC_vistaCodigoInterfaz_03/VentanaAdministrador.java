package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Ventana gráfica para el modo administrador del juego.
 * Muestra opciones dependiendo del nivel del administrador (1, 2 o 3).
 * Incorpora logging para registrar eventos, errores y acciones del usuario.
 */

// ...package y imports sin cambios

public class VentanaAdministrador extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VentanaAdministrador.class.getName());
    private final int nivelAdmin;

    static {
        inicializarLogger();
    }

    public VentanaAdministrador(int nivelAdmin) {
        super("Modo Administrador");

        if (nivelAdmin < 1 || nivelAdmin > 3) {
            throw new IllegalArgumentException("Nivel de administrador inválido: " + nivelAdmin);
        }

        this.nivelAdmin = nivelAdmin;
        configurarVentana();
        inicializarUI();
        setAlwaysOnTop(true);
        toFront();
        LOGGER.info("VentanaAdministrador creada con nivelAdmin=" + nivelAdmin);
    }

    public static void mostrarVentana(int nivelAdmin) {
        SwingUtilities.invokeLater(() -> {
            if (nivelAdmin < 1 || nivelAdmin > 3) {
                mostrarError(null, "Nivel de administrador inválido (debe ser 1, 2 o 3).", true);
                LOGGER.warning("Intento de abrir VentanaAdministrador con nivel inválido: " + nivelAdmin);
                return;
            }
            new VentanaAdministrador(nivelAdmin).setVisible(true);
        });
    }

    private static void inicializarLogger() {
        try {
            LOGGER.setLevel(Level.ALL);
            FileHandler fh = new FileHandler("VentanaAdministrador.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("No se pudo inicializar el archivo de logs: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "No se pudo inicializar el archivo de logs:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrarError(Component parent, String mensaje) {
        mostrarError(parent, mensaje, false);
    }

    private static void mostrarError(Component parent, String mensaje, boolean siempreAlFrente) {
        JOptionPane pane = new JOptionPane("ERROR: " + mensaje, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = pane.createDialog(parent, "Error");
        dialog.setAlwaysOnTop(siempreAlFrente);
        dialog.setVisible(true);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarUI() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(34, 40, 49));
        add(fondo);

        GridBagConstraints gbc = crearGBC();

        fondo.add(crearBotonSuperior(), gbc);
        gbc.gridy++;
        fondo.add(crearTitulo(), gbc);
        gbc.gridy++;
        fondo.add(crearPanelCentral(), gbc);
        gbc.gridy++;
        fondo.add(crearBotonesInferiores(), gbc);
    }

    private GridBagConstraints crearGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        return gbc;
    }

    private Component crearBotonSuperior() {
        return crearBoton("Volver al Menú Principal", () -> {
            LOGGER.info("Botón 'Volver al Menú Principal' pulsado (parte superior).");
            PantallaBienvenida.mostrarVentana();
            dispose();
        }, new Color(220, 20, 60), 12);
    }

    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("¡Bienvenido al Modo Administrador!", SwingConstants.CENTER);
        titulo.setForeground(new Color(240, 248, 255));
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        return titulo;
    }

    private Component crearPanelCentral() {
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setBackground(new Color(34, 40, 49));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(34, 40, 49));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(crearBoton("Registro de Jugadores", () -> {
            LOGGER.info("Botón 'Registro de Jugadores' pulsado.");
            JOptionPane.showMessageDialog(this, "Acción para registrar jugadores.");
        }));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        agregarBotonesPorNivel(menuPanel);

        contenedorCentral.add(menuPanel);
        return contenedorCentral;
    }

    private JPanel crearBotonesInferiores() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(34, 40, 49));

        panel.add(crearBoton("Volver a Menu Admin", () -> {
            LOGGER.info("Botón 'Volver a Menu Admin' pulsado.");
            MenuDesplegable.mostrarVentana();
            dispose();
        }));

        panel.add(crearBoton("Volver al Menú Principal", () -> {
            LOGGER.info("Botón 'Volver al Menú Principal' pulsado.");
            PantallaBienvenida.mostrarVentana();
            dispose();
        }));

        return panel;
    }

    private void agregarBotonesPorNivel(JPanel panel) {
        if (nivelAdmin >= 1) {
            panel.add(crearBoton("Ver Estadísticas", () -> {
                LOGGER.info("Botón 'Ver Estadísticas' pulsado.");
                JOptionPane.showMessageDialog(this, "Mostrando estadísticas del juego.");
            }));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        if (nivelAdmin >= 2) {
            panel.add(crearBoton("Exportar Datos a CSV", this::exportarDatosCSV));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            panel.add(crearBoton("Restaurar Copia de Seguridad", this::restaurarCopiaSeguridad));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        if (nivelAdmin == 3) {
            panel.add(crearBoton("Restaurar Datos (Nivel 3)", () -> {
                LOGGER.info("Botón 'Restaurar Datos' pulsado (Nivel 3).");
                JOptionPane.showMessageDialog(this, "Funcionalidad especial para administradores de nivel 3.");
            }));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private JButton crearBoton(String texto, Runnable accion) {
        return crearBoton(texto, accion, new Color(100, 149, 237), 14);
    }

    private JButton crearBoton(String texto, Runnable accion, Color colorFondo, int fontSize) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        boton.setPreferredSize(new Dimension(250, 40));
        boton.addActionListener(e -> accion.run());
        return boton;
    }

    private void exportarDatosCSV() {
        LOGGER.info("Exportando datos a CSV desde la base de datos AhorcadoAndres.");
        JFileChooser fileChooser = crearFileChooser("Guardar archivo CSV", "Archivo CSV", "csv");

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            LOGGER.info("Exportación cancelada por el usuario.");
            return;
        }

        File archivo = getArchivoConExtension(fileChooser.getSelectedFile(), ".csv");

        try (
                Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/AhorcadoAndres", "root", "abcd1234");
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Jugadores");
                BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))
        ) {
            escribirCSV(rs, writer);
            JOptionPane.showMessageDialog(this, "Datos exportados correctamente a " + archivo.getAbsolutePath());
            LOGGER.info("Datos exportados correctamente a " + archivo.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al exportar datos a CSV", e);
            mostrarError(this, "Error al exportar datos: " + e.getMessage(), true);
        }
    }

    private void escribirCSV(ResultSet rs, BufferedWriter writer) throws SQLException, IOException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnas = metaData.getColumnCount();

        for (int i = 1; i <= columnas; i++) {
            writer.write(metaData.getColumnName(i));
            if (i < columnas) writer.write(",");
        }
        writer.newLine();

        while (rs.next()) {
            for (int i = 1; i <= columnas; i++) {
                writer.write(rs.getString(i));
                if (i < columnas) writer.write(",");
            }
            writer.newLine();
        }

        writer.flush();
    }

    private void restaurarCopiaSeguridad() {
        LOGGER.info("Restaurando copia de seguridad.");
        JFileChooser fileChooser = crearFileChooser("Seleccionar archivo de copia de seguridad", "Archivos SQL", "sql");

        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            LOGGER.info("Restauración cancelada por el usuario.");
            return;
        }

        File archivoSQL = fileChooser.getSelectedFile();
        ejecutarRestauracionSQL(archivoSQL);
    }

    private void ejecutarRestauracionSQL(File archivoSQL) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "mysql", "-u", "root", "-pabcd1234", "AhorcadoAndres", "-e", "source " + archivoSQL.getAbsolutePath()
            );
            pb.redirectErrorStream(true);
            Process proceso = pb.start();
            int resultado = proceso.waitFor();

            if (resultado == 0) {
                JOptionPane.showMessageDialog(this, "Base de datos restaurada correctamente desde: " + archivoSQL.getAbsolutePath());
                LOGGER.info("Restauración completada desde archivo: " + archivoSQL.getAbsolutePath());
            } else {
                mostrarError(this, "Error al restaurar la base de datos. Código de salida: " + resultado, true);
                LOGGER.severe("Fallo en la restauración. Código de salida: " + resultado);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al restaurar la base de datos", e);
            mostrarError(this, "Error al restaurar la base de datos: " + e.getMessage(), true);
        }
    }
    private JFileChooser crearFileChooser(String titulo, String descripcion, String extension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileFilter(new FileNameExtensionFilter(descripcion, extension));
        return fileChooser;
    }

    private File getArchivoConExtension(File archivo, String extension) {
        return archivo.getName().endsWith(extension) ? archivo : new File(archivo + extension);
    }
}
