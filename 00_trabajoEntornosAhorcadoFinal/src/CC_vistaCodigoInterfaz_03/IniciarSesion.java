// Declaración del paquete en el que se encuentra la clase
package CC_vistaCodigoInterfaz_03;

// Importa la clase ConexionBaseDatos para conexión con la base de datos

import BB_modeloBBDD_02.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Declara la clase IniciarSesion que extiende JFrame para crear ventana
public class IniciarSesion extends JFrame {

    // Declara campo de texto para el nombre de usuario o correo
    private JTextField campoUsuario;
    // Declara campo para la contraseña con ocultación de caracteres
    private JPasswordField campoContrasena;

    // Constructor de la clase IniciarSesion
    public IniciarSesion() {
        super("Iniciar Sesión");                          // Establece título ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrar
        setSize(400, 300);                                // Define tamaño fijo
        setResizable(false);                              // No permite redimensionar ventana
        setLocationRelativeTo(null);                      // Centra la ventana en la pantalla
        inicializarInterfaz();                            // Llama método para crear la interfaz gráfica
    }

    // Método estático para mostrar la ventana de login
    public static void mostrarVentana() {
        // Ejecuta en el hilo de eventos para mostrar ventana
        SwingUtilities.invokeLater(() -> new IniciarSesion().setVisible(true));
    }

    // Método para inicializar y configurar la interfaz gráfica
    private void inicializarInterfaz() {
        JPanel panel = new JPanel(new GridBagLayout());  // Panel principal con layout GridBagLayout
        panel.setBackground(new Color(34, 40, 49));      // Color de fondo oscuro
        add(panel);                                       // Añade panel a la ventana

        GridBagConstraints gbc = new GridBagConstraints();// Objeto para controlar posiciones de componentes
        gbc.insets = new Insets(10, 10, 10, 10);         // Márgenes alrededor de componentes

        // Crea etiqueta para el usuario
        JLabel etiquetaUsuario = new JLabel("Nombre de Usuario o Correo:");
        etiquetaUsuario.setForeground(Color.WHITE);      // Color texto blanco
        etiquetaUsuario.setFont(new Font("SansSerif", Font.BOLD, 14)); // Fuente negrita tamaño 14
        campoUsuario = new JTextField(20);                // Campo texto para usuario con 20 columnas
        campoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));   // Fuente normal tamaño 14

        // Crea etiqueta para la contraseña
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setForeground(Color.WHITE);   // Color texto blanco
        etiquetaContrasena.setFont(new Font("SansSerif", Font.BOLD, 14)); // Fuente negrita tamaño 14
        campoContrasena = new JPasswordField(20);        // Campo contraseña con 20 columnas (oculto)
        campoContrasena.setFont(new Font("SansSerif", Font.PLAIN, 14));  // Fuente normal tamaño 14

        // Posiciona etiquetaUsuario en columna 0, fila 0, alineada al final de la línea
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(etiquetaUsuario, gbc);                  // Añade etiquetaUsuario al panel

        // Posiciona etiquetaContrasena en columna 0, fila 1, alineada al final de la línea
        gbc.gridy = 1;
        panel.add(etiquetaContrasena, gbc);                // Añade etiquetaContrasena al panel

        // Posiciona campoUsuario en columna 1, fila 0, alineada al inicio de la línea
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;          // Rellenar horizontalmente el espacio disponible
        gbc.weightx = 1.0;                                  // Permite que el campo se expanda horizontalmente
        panel.add(campoUsuario, gbc);                       // Añade campoUsuario al panel

        // Posiciona campoContrasena en columna 1, fila 1
        gbc.gridy = 1;
        panel.add(campoContrasena, gbc);                    // Añade campoContrasena al panel

        // Crea botón para iniciar sesión
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(new Color(100, 149, 237)); // Color azul claro botón
        btnIniciarSesion.setForeground(Color.WHITE);              // Texto blanco
        btnIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 14)); // Fuente negrita 14
        btnIniciarSesion.addActionListener(e -> procesarInicioSesion()); // Evento click para procesar login

        // Crea botón para volver a la pantalla bienvenida
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(220, 20, 60));    // Color rojo botón
        btnVolver.setForeground(Color.WHITE);               // Texto blanco
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));  // Fuente negrita 14
        btnVolver.addActionListener(e -> {                   // Evento click para volver
            dispose();                                        // Cierra esta ventana
            PantallaBienvenida.mostrarVentana();              // Muestra pantalla bienvenida
        });

        // Crea un panel para contener los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(34, 40, 49));   // Mismo fondo oscuro
        panelBotones.add(btnIniciarSesion);                  // Añade botón iniciar sesión
        panelBotones.add(btnVolver);                          // Añade botón volver

        // Añade panel de botones al panel principal, en fila 2, columna 0, abarcando 2 columnas
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;                                    // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;               // Centra horizontalmente
        panel.add(panelBotones, gbc);                          // Añade panelBotones
    }

    // Método que se ejecuta al pulsar el botón Iniciar Sesión
    private void procesarInicioSesion() {
        String usuario = campoUsuario.getText().trim();      // Obtiene y limpia texto usuario
        String contrasena = new String(campoContrasena.getPassword()).trim(); // Obtiene y limpia contraseña

        // Valida que los campos no estén vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;                                           // Sale del método
        }

        try {
            // Verifica usuarios especiales con contraseñas planas para administradores
            if ((usuario.equals("administrador1@gmail.com") || usuario.equals("administrador1")) && contrasena.equals("administrador1")) {
                mostrarMenuAdministradorCompleto();           // Muestra menú completo
            } else if ((usuario.equals("administrador2@gmail.com") || usuario.equals("administrador2")) && contrasena.equals("administrador2")) {
                mostrarMenuBackupYRestauracion();             // Muestra menú backup y restauración
            } else if ((usuario.equals("administrador3@gmail.com") || usuario.equals("administrador3")) && contrasena.equals("administrador3")) {
                mostrarMenuSoloBackup();                       // Muestra menú solo backup
            } else if (validarCredenciales(usuario, contrasena)) { // Si credenciales son válidas en BD
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();                                     // Cierra ventana login
                PantallaAhorcado.mostrarVentana();             // Muestra pantalla principal (juego o app)
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al iniciar sesión: " + e.getMessage());
        }
    }

    // Método que valida las credenciales contra la base de datos
    private boolean validarCredenciales(String usuario, String contrasena) throws SQLException, NoSuchAlgorithmException {
        String sql = "SELECT contraseñaHash FROM Usuario WHERE nombreUsuario = ? OR correoElectronico = ?"; // Consulta
        try (Connection conexion = ConexionBaseDatos.getConexion(); // Obtiene conexión
             PreparedStatement ps = conexion.prepareStatement(sql)) {  // Prepara consulta
            ps.setString(1, usuario);                                  // Pone usuario en primer parámetro
            ps.setString(2, usuario);                                  // Pone usuario en segundo parámetro
            try (ResultSet rs = ps.executeQuery()) {                   // Ejecuta consulta
                if (rs.next()) {                                       // Si hay resultado
                    String hashBD = rs.getString("contraseñaHash");    // Obtiene hash almacenado
                    String hashIngresado = hashContrasena(contrasena); // Genera hash de la contraseña ingresada
                    return hashIngresado != null && hashIngresado.equals(hashBD); // Compara hashes
                } else {
                    return false;                                       // No encontró usuario
                }
            }
        }
    }

    // Método que genera el hash SHA-256 de una contraseña
    private String hashContrasena(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");      // Obtiene instancia SHA-256
        byte[] hashBytes = md.digest(contrasena.getBytes());          // Calcula hash bytes
        StringBuilder sb = new StringBuilder();                        // Constructor para string resultado
        for (byte b : hashBytes)                                       // Recorre bytes hash
            sb.append(String.format("%02x", b));                      // Convierte byte a hexadecimal
        return sb.toString();                                          // Retorna hash en hexadecimal
    }

    // Muestra ventana con menú completo para administrador 1
    private void mostrarMenuAdministradorCompleto() {
        JFrame ventana = crearVentana("Menú Administrador Completo", new String[]{
                "Añadir Palabras", "Eliminar Palabras", "Añadir Usuarios", "Eliminar Usuarios", "Copia de Seguridad y Restauración"
        });
        ventana.setVisible(true);                                      // Muestra ventana menú
        dispose();                                                    // Cierra ventana login
    }

    // Muestra ventana con menú para backup y restauración (administrador 2)
    private void mostrarMenuBackupYRestauracion() {
        JFrame ventana = crearVentana("Menú Backup y Restauración", new String[]{
                "Copia de Seguridad", "Restauración"
        });
        ventana.setVisible(true);                                      // Muestra ventana menú
        dispose();                                                    // Cierra ventana login
    }

    // Muestra ventana con menú solo para backup (administrador 3)
    private void mostrarMenuSoloBackup() {
        JFrame ventana = crearVentana("Menú Copia de Seguridad", new String[]{
                "Copia de Seguridad"
        });
        ventana.setVisible(true);                                      // Muestra ventana menú
        dispose();                                                    // Cierra ventana login
    }

    // Método auxiliar para crear ventana con botones según un arreglo de textos
    private JFrame crearVentana(String titulo, String[] botones) {
        JFrame ventana = new JFrame(titulo);                          // Crea ventana con título
        ventana.setSize(400, 300);                                    // Tamaño fijo ventana
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // Cierra solo esta ventana
        ventana.setLocationRelativeTo(null);                          // Centra ventana

        JPanel panel = new JPanel(new GridBagLayout());               // Panel con layout GridBagLayout
        panel.setBackground(new Color(34, 40, 49));                   // Fondo oscuro
        GridBagConstraints gbc = new GridBagConstraints();           // Constraints para botones
        gbc.insets = new Insets(10, 20, 10, 20);                      // Márgenes para botones
        gbc.fill = GridBagConstraints.HORIZONTAL;                     // Botones llenan horizontalmente
        gbc.weightx = 1.0;                                             // Botones pueden expandirse
        gbc.gridx = 0;                                                 // Columna 0

        // Crea un botón para cada texto en el arreglo botones
        for (int i = 0; i < botones.length; i++) {
            JButton boton = new JButton(botones[i]);                   // Crea botón con texto
            boton.setBackground(new Color(100, 149, 237));            // Fondo azul claro
            boton.setForeground(Color.WHITE);                          // Texto blanco
            boton.setFont(new Font("SansSerif", Font.BOLD, 14));      // Fuente negrita 14
            gbc.gridy = i;                                             // Fila i
            panel.add(boton, gbc);                                     // Añade botón al panel
        }

        // Crea botón para volver a login
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(220, 20, 60));               // Fondo rojo
        btnVolver.setForeground(Color.WHITE);                          // Texto blanco
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 14));      // Fuente negrita 14
        btnVolver.addActionListener(e -> {                             // Evento click
            ventana.dispose();                                          // Cierra ventana actual
            mostrarVentana();                                           // Muestra ventana login
        });
        gbc.gridy = botones.length;                                     // Posiciona debajo de los botones
        panel.add(btnVolver, gbc);                                      // Añade botón volver al panel
        ventana.add(panel);                                             // Añade panel a ventana
        return ventana;                                                 // Retorna ventana creada
    }
}