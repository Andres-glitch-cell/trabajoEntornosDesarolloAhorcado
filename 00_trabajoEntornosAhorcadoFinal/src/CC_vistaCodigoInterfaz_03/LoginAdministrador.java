package CC_vistaCodigoInterfaz_03;  // Paquete donde está la clase

import javax.swing.*;  // Importar componentes Swing para la interfaz
import java.awt.*;  // Importar componentes AWT para layouts y colores

public class LoginAdministrador extends JFrame {  // Clase que extiende JFrame (ventana)
    private final JTextField campoUsuario = new JTextField(15);  // Campo de texto para usuario, ancho 15 columnas
    private final JPasswordField campoContrasena = new JPasswordField(15);  // Campo para contraseña, ancho 15 columnas

    public LoginAdministrador() {  // Constructor de la clase
        super("Inicio de Sesión - Administrador");  // Llama al constructor JFrame con título
        configurarVentana();  // Configura propiedades básicas de la ventana
        inicializarInterfaz();  // Inicializa y añade componentes visuales
        RegistroDeEventos.registrarInfo("Ventana LoginAdministrador creada.");  // Registra evento de creación
    }

    private void configurarVentana() {  // Método que configura la ventana JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Cierra la aplicación al cerrar la ventana
        setSize(350, 200);  // Define tamaño fijo de ventana (ancho 350, alto 200)
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setResizable(false);  // No permite redimensionar la ventana
    }

    private void inicializarInterfaz() {  // Método para inicializar los componentes GUI
        JPanel panel = new JPanel(new GridBagLayout());  // Crea un panel con layout GridBagLayout
        panel.setBackground(new Color(34, 40, 49));  // Establece color de fondo oscuro al panel
        add(panel);  // Añade el panel a la ventana JFrame

        GridBagConstraints gbc = new GridBagConstraints();  // Crea constraints para GridBagLayout
        gbc.insets = new Insets(10, 10, 10, 10);  // Define márgenes alrededor de cada componente

        agregarCampo(panel, gbc, 0, "Usuario:", campoUsuario);  // Añade etiqueta y campo usuario fila 0
        agregarCampo(panel, gbc, 1, "Contraseña:", campoContrasena);  // Añade etiqueta y campo contraseña fila 1

        gbc.gridx = 0;  // Posición columna 0
        gbc.gridy = 2;  // Posición fila 2
        gbc.gridwidth = 2;  // El botón ocupa 2 columnas
        gbc.anchor = GridBagConstraints.CENTER;  // Centra el componente en su celda
        JButton botonIniciar = new JButton("Iniciar Sesión");  // Crea botón para iniciar sesión
        panel.add(botonIniciar, gbc);  // Añade botón al panel con constraints definidos
        botonIniciar.addActionListener(e -> validarLogin());  // Añade acción para validar login al pulsar botón
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;  // Columna 0 para la etiqueta
        gbc.gridy = fila;  // Fila pasada por parámetro
        gbc.anchor = GridBagConstraints.EAST;  // Alinea etiqueta a la derecha
        JLabel lbl = new JLabel(etiqueta);  // Crea etiqueta con texto dado
        lbl.setForeground(Color.WHITE);  // Color blanco para la etiqueta (contraste con fondo oscuro)
        panel.add(lbl, gbc);  // Añade etiqueta al panel con constraints

        gbc.gridx = 1;  // Columna 1 para el campo (texto o contraseña)
        gbc.anchor = GridBagConstraints.WEST;  // Alinea campo a la izquierda
        panel.add(campo, gbc);  // Añade campo al panel con constraints
    }

    private void validarLogin() {
        String usuario = campoUsuario.getText().trim().toLowerCase();
        String contrasena = new String(campoContrasena.getPassword()).trim().toLowerCase();

        RegistroDeEventos.registrarInfo("Intento de inicio de sesión con usuario: " + usuario);

        String[] usuariosValidos = {
                "administrador1", "administrador1@gmail.com",
                "administrador2", "administrador2@gmail.com",
                "administrador3", "administrador3@gmail.com"
        };

        boolean usuarioValido = false;
        for (String u : usuariosValidos) {
            if (usuario.equals(u)) {
                usuarioValido = true;
                break;
            }
        }

        if (!usuarioValido) {
            RegistroDeEventos.registrarError("Usuario no autorizado: " + usuario);
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // La contraseña debe ser igual al usuario
        if (!contrasena.equals(usuario)) {
            RegistroDeEventos.registrarError("Contraseña incorrecta para usuario: " + usuario);
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nivel = 0;
        if (usuario.contains("1")) nivel = 1;
        else if (usuario.contains("2")) nivel = 2;
        else if (usuario.contains("3")) nivel = 3;

        abrirVentanaAdministrador(nivel);
    }

    private void abrirVentanaAdministrador(int nivel) {  // Método para abrir ventana principal admin con nivel
        RegistroDeEventos.registrarInfo("Inicio de sesión exitoso para usuario: administrador con nivel " + nivel);  // Registra login exitoso
        new VentanaAdministrador(nivel).setVisible(true);  // Crea y muestra ventana administrador con nivel
        dispose();  // Cierra la ventana de login actual
    }
}
