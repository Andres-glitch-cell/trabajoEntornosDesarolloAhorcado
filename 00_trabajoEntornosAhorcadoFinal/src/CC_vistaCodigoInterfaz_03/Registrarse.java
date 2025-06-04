// Clase que representa la ventana para registrarse en el juego
package CC_vistaCodigoInterfaz_03;  // Define el paquete donde está la clase

// Importaciones necesarias para conexión a base de datos, interfaz gráfica, seguridad y manejo de archivos

import BB_modeloBBDD_02.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Declaración de la clase Registrarse que hereda de JFrame (ventana gráfica)
public class Registrarse extends JFrame {

    // Campos para los distintos controles de la interfaz
    private JTextField campoNombreCompleto, campoUsuario, campoCorreo;  // Campos de texto para nombre, usuario y correo
    private JPasswordField campoContraseña, campoContraseñaRepetida;    // Campos de contraseña para entrada segura

    // Constructor de la clase, configura la ventana principal de registro
    public Registrarse() {
        super("Registrarse");                          // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Solo cierra esta ventana sin cerrar la aplicación
        setSize(550, 450);                             // Establece tamaño fijo (ancho 550, alto 450)
        setResizable(false);                           // Deshabilita cambiar tamaño de ventana
        setLocationRelativeTo(null);                   // Centra la ventana en la pantalla
        initUI();                                      // Llama al método que inicializa los componentes gráficos
    }

    // Método estático para mostrar esta ventana en el hilo de eventos Swing
    public static void mostrarVentana() {
        SwingUtilities.invokeLater(() -> new Registrarse().setVisible(true));  // Crea e muestra la ventana de forma segura en el hilo UI
    }

    // Método privado para inicializar y configurar los componentes gráficos de la ventana
    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());     // Panel principal con layout GridBag para diseño flexible
        panel.setBackground(new Color(34, 40, 49));         // Establece color de fondo oscuro para el panel
        add(panel);                                          // Añade el panel a la ventana JFrame

        GridBagConstraints gbc = new GridBagConstraints();  // Objeto para configurar posición y espacio de componentes
        gbc.insets = new Insets(10, 10, 10, 10);            // Margen (espaciado) de 10 píxeles en todos los lados

        // Etiquetas para los campos del formulario
        String[] etiquetas = {"Nombre Completo:", "Nombre de Usuario:", "Correo Electrónico:", "Contraseña:", "Repite Contraseña:"};
        JLabel[] labels = new JLabel[etiquetas.length];     // Array para guardar las etiquetas JLabel

        // Crear etiquetas con texto, color blanco y fuente en negrita
        for (int i = 0; i < etiquetas.length; i++) {
            labels[i] = new JLabel(etiquetas[i]);           // Crear JLabel con texto
            labels[i].setForeground(Color.WHITE);            // Color del texto blanco
            labels[i].setFont(new Font("SansSerif", Font.BOLD, 14)); // Fuente SansSerif negrita tamaño 14
        }

        // Crear campos de texto y contraseña para los datos de registro
        campoNombreCompleto = new JTextField(30);            // Campo de texto con 30 columnas de ancho
        campoUsuario = new JTextField(30);                    // Campo de texto para nombre de usuario
        campoCorreo = new JTextField(30);                     // Campo de texto para correo electrónico
        campoContraseña = new JPasswordField(30);            // Campo para contraseña, con 30 columnas
        campoContraseñaRepetida = new JPasswordField(30);    // Campo para repetir contraseña

        // Establecer dimensión fija para todos los campos (ancho 350, alto 30 píxeles)
        Dimension dimCampo = new Dimension(350, 30);
        JTextField[] campos = {campoNombreCompleto, campoUsuario, campoCorreo, campoContraseña, campoContraseñaRepetida};
        for (JTextField c : campos) c.setPreferredSize(dimCampo); // Aplica tamaño preferido a cada campo

        // Añadir etiquetas al panel en la columna 0, alineadas a la derecha
        gbc.gridx = 0;                                        // Columna 0
        gbc.gridy = 0;                                        // Fila 0
        gbc.anchor = GridBagConstraints.EAST;                 // Alinear etiquetas a la derecha
        for (JLabel label : labels) {
            panel.add(label, gbc);                             // Añadir etiqueta al panel
            gbc.gridy++;                                       // Pasar a siguiente fila
        }

        // Añadir campos de texto y contraseña en la columna 1, alineados a la izquierda
        gbc.gridx = 1;                                        // Columna 1
        gbc.gridy = 0;                                        // Fila 0 (reinicia contador)
        gbc.anchor = GridBagConstraints.WEST;                 // Alinear campos a la izquierda
        for (JTextField campo : campos) {
            panel.add(campo, gbc);                             // Añadir campo al panel
            gbc.gridy++;                                       // Pasar a siguiente fila
        }

        // Crear botón Registrar y configurar su apariencia y acción
        JButton btnRegistrar = new JButton("Registrar");       // Crear botón con texto "Registrar"
        btnRegistrar.setBackground(new Color(100, 149, 237));  // Color azul claro de fondo
        btnRegistrar.setForeground(Color.WHITE);                // Texto en color blanco
        btnRegistrar.addActionListener(e -> registrarUsuario()); // Al hacer clic, llama a método registrarUsuario()

        // Crear botón Volver y configurar apariencia y acción para volver a pantalla bienvenida
        JButton btnVolver = new JButton("Volver");              // Botón "Volver"
        btnVolver.setBackground(new Color(255, 99, 71));        // Color rojo tomate de fondo
        btnVolver.setForeground(Color.WHITE);                   // Texto en blanco
        btnVolver.addActionListener(e -> {
            PantallaBienvenida.mostrarVentana();                 // Mostrar ventana bienvenida
            dispose();                                           // Cerrar esta ventana actual
        });

        // Panel para contener los botones, con mismo fondo que el panel principal
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(34, 40, 49));      // Fondo oscuro igual al principal
        panelBotones.add(btnRegistrar);                         // Añadir botón Registrar
        panelBotones.add(btnVolver);                            // Añadir botón Volver

        // Añadir el panel de botones al formulario, ocupando dos columnas
        gbc.gridx = 0;                                           // Columna 0
        gbc.gridy++;                                            // Pasar a la siguiente fila
        gbc.gridwidth = 2;                                      // Ocupa dos columnas
        panel.add(panelBotones, gbc);                            // Añadir panel de botones al panel principal
    }

    // Método que gestiona el registro del usuario cuando se pulsa el botón Registrar
    private void registrarUsuario() {
        // Obtener texto de los campos, eliminando espacios al inicio y final
        String nombreCompleto = campoNombreCompleto.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String correo = campoCorreo.getText().trim();
        String contraseña = new String(campoContraseña.getPassword());           // Convertir arreglo de caracteres a String
        String contraseñaRepetida = new String(campoContraseñaRepetida.getPassword());

        // Validación: verificar que ningún campo esté vacío
        if (nombreCompleto.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || contraseñaRepetida.isEmpty()) {
            mostrarError("Por favor, completa todos los campos.");              // Mostrar error si hay campos vacíos
            return;  // Termina el método si hay campos vacíos
        }

        // Validar que las contraseñas coincidan
        if (!contraseña.equals(contraseñaRepetida)) {
            mostrarError("Las contraseñas no coinciden.");                      // Error si contraseñas no coinciden
            return;  // Termina si las contraseñas no coinciden
        }

        // Validar longitud mínima de la contraseña (6 caracteres)
        if (contraseña.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres.");   // Error si contraseña es muy corta
            return;  // Termina si la contraseña es muy corta
        }

        // Generar hash SHA-256 para almacenar la contraseña de forma segura
        String contraseñaHash = hashContraseña(contraseña);
        if (contraseñaHash == null) {
            mostrarError("Error al generar hash de la contraseña.");           // Error si falla la generación de hash
            return;  // Termina si falla el hash
        }

        // Intentar registrar el usuario en la base de datos con la información proporcionada
        if (registrarUsuarioEnBBDD(nombreCompleto, usuario, correo, contraseña, contraseñaHash)) {
            // Si se registra correctamente:
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            escribirLog("Registro exitoso del usuario: " + usuario);           // Escribir en log local con éxito
            PantallaBienvenida.mostrarVentana();                               // Mostrar pantalla de bienvenida
            dispose();                                                         // Cerrar esta ventana
        } else {
            // Si falla el registro (p.ej. usuario ya existe), mostrar error y escribir log
            mostrarError("Error al registrar el usuario. Puede que el nombre de usuario ya exista.");
            escribirLog("Error al registrar usuario: " + usuario);
        }
    }

    // Método para insertar el usuario en la base de datos, retorna true si se insertó correctamente
    private boolean registrarUsuarioEnBBDD(String nombreCompleto, String usuario, String correo, String contraseña, String contraseñaHash) {
        // Consulta SQL para insertar un nuevo usuario
        String sql = "INSERT INTO Usuario (nombreCompleto, nombreUsuario, correoElectronico, contraseña, contraseñaHash, fechaRegistro) " +
                "VALUES (?, ?, ?, ?, ?, NOW())";  // La fecha se registra con la función NOW() de la base de datos
        try (Connection c = ConexionBaseDatos.getConexion();               // Obtener conexión a la base de datos
             PreparedStatement s = c.prepareStatement(sql)) {               // Preparar la sentencia SQL para evitar inyección
            // Asignar valores a los parámetros de la consulta preparada
            s.setString(1, nombreCompleto);
            s.setString(2, usuario);
            s.setString(3, correo);
            s.setString(4, contraseña);          // Guarda la contraseña en texto plano (idealmente no se debería hacer)
            s.setString(5, contraseñaHash);     // Guarda el hash para seguridad

            // Ejecutar la inserción y devolver true si al menos una fila fue afectada
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());  // Imprime error SQL en consola
            return false;  // Retornar false en caso de error SQL
        }
    }

    // Método para generar hash SHA-256 de la contraseña para almacenar seguro
    private String hashContraseña(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");   // Crear instancia de algoritmo SHA-256
            byte[] bytes = contraseña.getBytes(StandardCharsets.UTF_8); // Convertir contraseña a bytes en UTF-8
            byte[] hash = md.digest(bytes);                             // Generar hash SHA-256

            // Convertir bytes hash a cadena hexadecimal legible
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));                   // Formato hexadecimal con dos dígitos
            }
            return sb.toString();  // Retornar hash como cadena hexadecimal
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al generar hash: " + e.getMessage());  // Error si algoritmo no existe
            return null;          // Retorna null si falla la generación de hash
        }
    }

    // Método para mostrar mensajes de error en ventana emergente
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);  // Ventana de diálogo con error
    }

    // Método para escribir mensajes en un archivo de log local con fecha y hora
    private void escribirLog(String mensaje) {
        try {
            File dir = new File("LOGS");          // Carpeta donde se guardan logs
            if (!dir.exists()) dir.mkdir();       // Crear carpeta si no existe

            File logFile = new File(dir, "registro.log");  // Archivo de log dentro de la carpeta
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {  // Abrir archivo para agregar texto (append=true)
                // Formatear la fecha y hora actual
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                // Escribir línea en el archivo log con fecha y mensaje
                bw.write(fechaHora + " - " + mensaje);
                bw.newLine();  // Salto de línea
            }
        } catch (IOException e) {
            System.err.println("Error al escribir log: " + e.getMessage());  // Mostrar error si falla escritura
        }
    }
}