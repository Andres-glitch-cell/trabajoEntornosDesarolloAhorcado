package CC_vistaCodigoInterfaz_03;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class VentanaAdministrador extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VentanaAdministrador.class.getName());

    private final int nivelAdmin; // Ahora se recibe desde el constructor
    private JButton btnCrearCopia;
    private JButton btnRestaurarCopia;

    static {
        inicializarLogger();
    }

    // Constructor que recibe el nivelAdmin
    public VentanaAdministrador(int nivelAdmin) {
        super("Modo Administrador");
        this.nivelAdmin = nivelAdmin;

        configurarVentana();

        if (nivelAdmin < 1 || nivelAdmin > 3) {
            LOGGER.warning("Nivel de administrador inválido: " + nivelAdmin + ". Cerrando ventana.");
            JOptionPane.showMessageDialog(this, "Nivel de administrador inválido. Cerrando ventana.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        inicializarUI();
        accionSegunNivel();

        setAlwaysOnTop(true);
        toFront();

        LOGGER.info("VentanaAdministrador creada con nivelAdmin=" + nivelAdmin);
    }

    // Método mostrarVentana que recibe el nivelAdmin y lanza la ventana con ese nivel
    public static void mostrarVentana(int nivelAdmin) {
        SwingUtilities.invokeLater(() -> new VentanaAdministrador(nivelAdmin).setVisible(true));
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
            JOptionPane.showMessageDialog(null, "No se pudo inicializar el archivo de logs:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("¡Bienvenido al Modo Administrador!", SwingConstants.CENTER);
        titulo.setForeground(new Color(240, 248, 255));
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        return titulo;
    }

    private JPanel crearPanelCentral() {
        JPanel contenedorCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contenedorCentral.setBackground(new Color(34, 40, 49));

        btnCrearCopia = crearBoton("Crear Copia de Seguridad", this::crearCopiaSeguridad);
        btnRestaurarCopia = crearBoton("Restaurar Copia de Seguridad", this::restaurarCopiaSeguridad);

        if (nivelAdmin == 1) {
            contenedorCentral.add(btnCrearCopia);
            contenedorCentral.add(btnRestaurarCopia);
        } else if (nivelAdmin == 2) {
            contenedorCentral.add(btnCrearCopia);
            contenedorCentral.add(btnRestaurarCopia);
        } else if (nivelAdmin == 3) {
            contenedorCentral.add(btnCrearCopia);
            // No se añade btnRestaurarCopia
        }

        return contenedorCentral;
    }

    private JPanel crearBotonesInferiores() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(new Color(34, 40, 49));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setBackground(new Color(34, 40, 49));

        botones.add(crearBoton("Volver a Menu Admin", () -> {
            LOGGER.info("Botón 'Volver a Menu Admin' pulsado.");
            MenuDesplegable.mostrarVentana(nivelAdmin);
            dispose();
        }));

        botones.add(crearBoton("Volver al Menú Principal", () -> {
            LOGGER.info("Botón 'Volver al Menú Principal' pulsado.");
            PantallaBienvenida.mostrarVentana();
            dispose();
        }, new Color(220, 20, 60), 14));

        contenedor.add(botones);
        return contenedor;
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

    private void accionSegunNivel() {
        switch (nivelAdmin) {
            case 1 -> {
                LOGGER.info("Nivel 1: Máxima autoridad, todo habilitado.");
                btnCrearCopia.setEnabled(true);
                btnRestaurarCopia.setEnabled(true);
            }
            case 2 -> {
                LOGGER.info("Nivel 2: Copias y restauraciones habilitadas.");
                btnCrearCopia.setEnabled(true);
                btnRestaurarCopia.setEnabled(true);
            }
            case 3 -> {
                LOGGER.info("Nivel 3: Solo copias de seguridad habilitadas.");
                btnCrearCopia.setEnabled(true);
            }
            default -> {
                LOGGER.warning("Nivel de administrador inesperado: " + nivelAdmin);
                btnCrearCopia.setEnabled(false);
                btnRestaurarCopia.setEnabled(false);
            }
        }
    }

    private void crearCopiaSeguridad() {
        LOGGER.info("Creando copia de seguridad...");
        JOptionPane.showMessageDialog(this, "Copia de seguridad creada correctamente.", "Copia de Seguridad", JOptionPane.INFORMATION_MESSAGE);
    }

    private void restaurarCopiaSeguridad() {
        LOGGER.info("Iniciando proceso de restauración de copia de seguridad.");

        int opcion = JOptionPane.showOptionDialog(this, "¿Deseas seleccionar un archivo manualmente o usar la ubicación predeterminada?", "Restaurar copia de seguridad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Seleccionar archivo", "Usar ubicación predeterminada", "Cancelar"}, "Seleccionar archivo");

        if (opcion == 0) {
            JFileChooser fileChooser = crearFileChooser("Seleccionar archivo de copia de seguridad", "Archivos SQL", "sql");

            if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                LOGGER.info("Restauración cancelada por el usuario.");
                return;
            }

            File archivoSQL = fileChooser.getSelectedFile();
            ejecutarRestauracionSQL(archivoSQL);

        } else if (opcion == 1) {
            File archivoPorDefecto = new File("C:/backups/mysql/backup.sql");

            if (!archivoPorDefecto.exists()) {
                mostrarError(this, "No se encontró el archivo en la ubicación por defecto:\n" + archivoPorDefecto.getAbsolutePath(), true);
                LOGGER.warning("Archivo de respaldo no encontrado en: " + archivoPorDefecto.getAbsolutePath());
                return;
            }

            ejecutarRestauracionSQL(archivoPorDefecto);

        } else {
            LOGGER.info("Usuario canceló la operación de restauración.");
        }
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
}