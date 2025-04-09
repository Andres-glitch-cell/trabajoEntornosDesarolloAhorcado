package CC_vistaCodigoInterfaz_03

import java.awt.*
import javax.swing.*

class Uno_IniciarSesion : JFrame("Iniciar Sesión") {
    init {
        // Llama a la SUPERCLASE (Uno_IniciarSesion.java)
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(400, 250)
        isResizable = false
        setLocationRelativeTo(null)

        // Panel principal con fondo personalizado
        val fondoPersonalizado = JPanel()
        fondoPersonalizado.layout = GridBagLayout()
        fondoPersonalizado.background = Color(34, 40, 49)
        add(fondoPersonalizado)

        // Configuración de GridBagConstraints
        val gbc = GridBagConstraints()
        gbc.insets = Insets(10, 10, 10, 10)
        gbc.fill = GridBagConstraints.HORIZONTAL

        /*
        gbc.gridx = 1;  // Columna 1 (segunda columna)
        gbc.gridy = 1;  // Fila 1 (segunda fila)
        fondoPersonalizado.add(valor2, gbc);

        gbc.gridx = 0;          // Columna 0 (primera columna)
        gbc.gridy = 2;          // Fila 2 (tercera fila)
        gbc.gridwidth = 2;      // Ocupa 2 columnas
        gbc.fill = GridBagConstraints.NONE;  // No se estira
        fondoPersonalizado.add(boton, gbc);
         */

        // Etiqueta de usuario
        val introducirUsuario = JLabel("Usuario:")
        introducirUsuario.foreground = Color(240, 248, 255)
        introducirUsuario.font = Font("SansSerif", Font.BOLD, 14)
        gbc.gridx = 0
        gbc.gridy = 0
        fondoPersonalizado.add(introducirUsuario, gbc)

        // Campo de texto para usuario
        val nombreUsuario = JTextField(15)
        nombreUsuario.background = Color(50, 60, 70) // Color
        nombreUsuario.foreground = Color.WHITE // Color
        nombreUsuario.border = BorderFactory.createLineBorder(Color(100, 149, 237), 1)
        gbc.gridx = 1
        gbc.gridy = 0
        fondoPersonalizado.add(nombreUsuario, gbc)

        // Etiqueta de contraseña
        val introducirContraseña = JLabel("Contraseña:")
        introducirContraseña.foreground = Color(240, 248, 255)
        introducirContraseña.font = Font("SansSerif", Font.BOLD, 14)
        gbc.gridx = 0
        gbc.gridy = 1
        fondoPersonalizado.add(introducirContraseña, gbc)

        val ventana = JFrame()
        val checkBox1 = JCheckBox("He olvidado mi contraseña")
        val checkBox2 = JCheckBox("He olvidado mi correo electronico")
        val checkBox3 = JCheckBox("Ambas? Que pendejo")
        gbc.gridx = 0
        gbc.gridy = 1
        ventana.add(checkBox1)
        ventana.add(checkBox2)
        ventana.add(checkBox3)

        isVisible = true
        // Campo de texto para contraseña
        val valor2 = JPasswordField(15)
        valor2.background = Color(50, 60, 70)
        valor2.foreground = Color.WHITE
        valor2.border = BorderFactory.createLineBorder(Color(58, 92, 164), 1)
        gbc.gridx = 1
        gbc.gridy = 1
        fondoPersonalizado.add(valor2, gbc)

        // Botón de registrarse
        val boton = JButton("Registrarse")
        boton.background = Color(100, 149, 237)
        boton.foreground = Color.WHITE
        boton.font = Font("SansSerif", Font.BOLD, 14)
        boton.isFocusPainted = false
        boton.border = BorderFactory.createEmptyBorder(8, 15, 8, 15)
        gbc.gridx = 0
        gbc.gridy = 2
        gbc.gridwidth = 2
        gbc.fill = GridBagConstraints.NONE
        fondoPersonalizado.add(boton, gbc)

        // Hacer visible la ventana
        isVisible = true

        // Agregar ActionListener al botón
        boton.addActionListener { // Crear un Timer para esperar 5 segundos y luego abrir otro formulario
            val timer = Timer(5000) {
                dispose()
                Tres_VentanaEmergente()
            }
            timer.isRepeats = false
            timer.start()
        }
    }
}