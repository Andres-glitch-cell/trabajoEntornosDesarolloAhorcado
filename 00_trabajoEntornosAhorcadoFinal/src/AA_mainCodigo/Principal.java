// === BLOQUE 1: DECLARACIÓN DEL PAQUETE E IMPORTACIONES ===
package AA_mainCodigo;                                              // Define el paquete donde reside la clase Principal

import BB_modeloBBDD_02.RespaldoAutomatico;
import CC_vistaCodigoInterfaz_03.LoginAdministrador;
import CC_vistaCodigoInterfaz_03.PantallaBienvenida;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;

// === BLOQUE 2: DEFINICIÓN DE LA CLASE Y LOGGER ===
public class Principal {                                            // Declara la clase pública Principal
    private static final Logger LOGGER = Logger.getLogger(Principal.class.getName()); // Crea un Logger para la clase Principal

    // === BLOQUE 3: BLOQUE ESTÁTICO PARA CONFIGURACIÓN INICIAL ===
    static {                                                       // Bloque estático que se ejecuta al cargar la clase
        configurarLogger();                                        // Llama al método para configurar el sistema de logging
    }

    // === BLOQUE 4: MÉTODO MAIN (PUNTO DE ENTRADA) ===
    public static void main(String[] args) {                        // Método principal, punto de entrada del programa
        try {                                                      // Inicia un bloque try para capturar excepciones
            mostrarBannerInicio();                                 // Muestra un banner de bienvenida en la consola
            LOGGER.info("Iniciando juego...");                     // Registra un mensaje informativo de inicio del juego
            Thread.sleep(1000);                                    // Pausa la ejecución por 1 segundo para efecto visual
            LOGGER.info("Cargando base de datos...");              // Registra un mensaje sobre la carga de la base de datos
            LOGGER.info("Iniciando respaldo automático...");       // Registra un mensaje sobre el inicio del respaldo
            RespaldoAutomatico.iniciarRespaldo();                  // Inicia el proceso de respaldo automático
            iniciarModoSegunEleccion();                            // Llama al método para elegir entre modo admin o usuario
            LOGGER.info("Aplicación iniciada correctamente.");     // Registra un mensaje de éxito al iniciar la aplicación
        } catch (Exception e) {                                    // Captura cualquier excepción durante la ejecución
            manejarErrorFatal(e);                                  // Llama al método para manejar errores fatales
        }
    }

    // === BLOQUE 5: CONFIGURACIÓN DEL LOGGER ===
    private static void configurarLogger() {                       // Método para configurar el sistema de logging
        try {                                                      // Inicia un bloque try para manejar errores de I/O
            LogManager.getLogManager().reset();                    // Resetea la configuración de logging existente
            LOGGER.setLevel(Level.ALL);                            // Establece el nivel de logging para capturar todos los eventos
            FileHandler archivoLog = new FileHandler("Principal.log", true); // Crea un manejador para escribir logs en un archivo
            archivoLog.setEncoding("UTF-8");                       // Establece la codificación del archivo de log a UTF-8
            archivoLog.setFormatter(new SimpleFormatter());        // Aplica un formato simple al archivo de log
            LOGGER.addHandler(archivoLog);                         // Agrega el manejador de archivo al Logger
            ConsoleHandler consola = new ConsoleHandler();         // Crea un manejador para escribir logs en la consola
            consola.setLevel(Level.INFO);                          // Establece el nivel de logging de consola a INFO
            consola.setFormatter(new ColorFormatter());            // Aplica un formateador personalizado con colores
            LOGGER.addHandler(consola);                            // Agrega el manejador de consola al Logger
        } catch (IOException e) {                                  // Captura excepciones de entrada/salida
            JOptionPane.showMessageDialog(null,                    // Muestra un diálogo de error si falla la configuración
                    "No se pudo crear el archivo de logs: " + e.getMessage(), // Mensaje con el error específico
                    "Error", JOptionPane.ERROR_MESSAGE);           // Título y tipo de mensaje (error)
        }
    }

    // === BLOQUE 6: MOSTRAR BANNER DE INICIO ===
    private static void mostrarBannerInicio() {                    // Método para mostrar un banner ASCII en la consola
        String[] banner = {                                        // Define un arreglo con las líneas del banner ASCII
                "\n",                                              // Línea vacía inicial
                "  AAA    H   H   OOO   RRRR    CCCC   AAA    DDDD    OOO  ", // Línea 1 del banner
                " A   A   H   H  O   O  R   R  C      A   A   D   D  O   O ", // Línea 2 del banner
                " AAAAA   HHHHH  O   O  RRRR   C      AAAAA   D   D  O   O ", // Línea 3 del banner
                " A   A   H   H  O   O  R  R   C      A   A   D   D  O   O ", // Línea 4 del banner
                " A   A   H   H   OOO   R   R   CCCC  A   A   DDDD    OOO  ", // Línea 5 del banner
                "\n"                                                // Línea vacía final
        };
        final String ANSI_GREEN = "\u001B[32m";                    // Código ANSI para color verde
        final String ANSI_RESET = "\u001B[0m";                     // Código ANSI para resetear el color
        for (int i = 0; i < banner.length; i++) {                  // Itera sobre el arreglo de banner usando un bucle for
            System.out.println(ANSI_GREEN + banner[i] + ANSI_RESET); // Imprime cada línea en verde y resetea el color
        }
    }

    // === BLOQUE 7: SELECCIÓN DE MODO (ADMIN O USUARIO) ===
    private static void iniciarModoSegunEleccion() {               // Método para elegir el modo de inicio de la aplicación
        int opcion = JOptionPane.showConfirmDialog(null,           // Muestra un diálogo para elegir modo administrador
                "¿Deseas iniciar en modo Administrador?",          // Mensaje del diálogo
                "Modo Administrador",                              // Título del diálogo
                JOptionPane.YES_NO_OPTION);                        // Opciones: Sí (admin) o No (usuario)
        SwingUtilities.invokeLater(() -> {                         // Ejecuta la lógica en el hilo EDT de Swing
            if (opcion == JOptionPane.YES_OPTION) {                // Verifica si se seleccionó el modo administrador
                LOGGER.info("Abriendo ventana de login para administrador."); // Registra mensaje de apertura de login admin
                new LoginAdministrador().setVisible(true);         // Crea y muestra la ventana de login administrador
            } else {                                              // Si no se seleccionó modo admin, inicia modo usuario
                LOGGER.info("Iniciando en modo usuario.");        // Registra mensaje de inicio en modo usuario
                PantallaBienvenida.mostrarVentana();               // Muestra la ventana de bienvenida para usuarios
            }
        });
    }

    // === BLOQUE 8: MANEJO DE ERRORES FATALES ===
    private static void manejarErrorFatal(Exception error) {       // Método para manejar excepciones fatales
        LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación", error); // Registra el error con nivel SEVERE
        JOptionPane.showMessageDialog(null,                        // Muestra un diálogo con el error
                "Error al iniciar la aplicación: " + error.getMessage(), // Mensaje con detalles del error
                "Error", JOptionPane.ERROR_MESSAGE);               // Título y tipo de mensaje (error)
        System.exit(1);                                            // Termina la aplicación con código de error 1
    }

    // === BLOQUE 9: FORMATEADOR DE LOGS CON COLORES ===
    private static class ColorFormatter extends Formatter {        // Clase interna para formatear logs con colores
        private static final String ANSI_RESET = "\u001B[0m";      // Código ANSI para resetear el color
        private static final String ANSI_GREEN = "\u001B[32m";     // Código ANSI para color verde (INFO)
        private static final String ANSI_RED = "\u001B[31m";       // Código ANSI para color rojo (SEVERE)
        private static final String ANSI_YELLOW = "\u001B[33m";    // Código ANSI para color amarillo (WARNING)
        @Override
        public String format(LogRecord record) {                   // Sobrescribe el método para formatear logs
            String mensaje = formatMessage(record);                // Obtiene el mensaje formateado del registro
            String color = "";                                     // Inicializa la variable para el código de color
            String prefijo = "";                                   // Inicializa la variable para el prefijo del log
            if (record.getLevel() == Level.INFO) {                 // Verifica si el nivel del log es INFO
                color = ANSI_GREEN;                                // Asigna color verde para mensajes INFO
                prefijo = "✔ ";                                    // Asigna prefijo de check para INFO
            } else if (record.getLevel() == Level.SEVERE) {        // Verifica si el nivel del log es SEVERE
                color = ANSI_RED;                                  // Asigna color rojo para mensajes SEVERE
                prefijo = "✖ ";                                    // Asigna prefijo de cruz para SEVERE
            } else if (record.getLevel() == Level.WARNING) {       // Verifica si el nivel del log es WARNING
                color = ANSI_YELLOW;                               // Asigna color amarillo para mensajes WARNING
                prefijo = "⚠ ";                                    // Asigna prefijo de advertencia para WARNING
            }
            return color + prefijo + mensaje + ANSI_RESET + "\n"; // Retorna el mensaje con color, prefijo y salto de línea
        }
    }
}