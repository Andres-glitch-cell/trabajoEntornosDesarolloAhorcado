package AA_mainCodigo;

import BB_modeloBBDD_02.RespaldoAutomatico;
import CC_vistaCodigoInterfaz_03.LoginAdministrador;
import CC_vistaCodigoInterfaz_03.PantallaBienvenida;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;

public class Principal {
    private static final Logger LOGGER = Logger.getLogger(Principal.class.getName());

    static {
        configurarLogger();
    }

    public static void main(String[] args) {
        try {
            mostrarBannerInicio();

            LOGGER.info("Iniciando juego...");
            Thread.sleep(1000); // Puedes quitar este tipo de pausas si no son esenciales.

            LOGGER.info("Cargando base de datos...");
            LOGGER.info("Iniciando respaldo automático...");
            RespaldoAutomatico.iniciarRespaldo();
            iniciarModoSegunEleccion();
            LOGGER.info("Aplicación iniciada correctamente.");
        } catch (Exception e) {
            manejarErrorFatal(e);
        }
    }

    private static void configurarLogger() {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);

            FileHandler archivoLog = new FileHandler("Principal.log", true);
            archivoLog.setEncoding("UTF-8");
            archivoLog.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(archivoLog);

            ConsoleHandler consola = new ConsoleHandler();
            consola.setLevel(Level.INFO);
            consola.setFormatter(new ColorFormatter());
            LOGGER.addHandler(consola);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo crear el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrarBannerInicio() {
        String[] banner = {
                "\n",
                "  AAA    H   H   OOO   RRRR    CCCC   AAA    DDDD    OOO  ",
                " A   A   H   H  O   O  R   R  C      A   A   D   D  O   O ",
                " AAAAA   HHHHH  O   O  RRRR   C      AAAAA   D   D  O   O ",
                " A   A   H   H  O   O  R  R   C      A   A   D   D  O   O ",
                " A   A   H   H   OOO   R   R   CCCC  A   A   DDDD    OOO  ",
                "\n"
        };

        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";

        for (String line : banner) {
            System.out.println(ANSI_GREEN + line + ANSI_RESET);
        }
    }

    private static void iniciarModoSegunEleccion() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Deseas iniciar en modo Administrador?",
                "Modo Administrador",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            LOGGER.info("Abriendo ventana de login para administrador.");
            SwingUtilities.invokeLater(() -> new LoginAdministrador().setVisible(true));
        } else {
            LOGGER.info("Iniciando en modo usuario.");
            SwingUtilities.invokeLater(PantallaBienvenida::mostrarVentana);
        }
    }

    private static void manejarErrorFatal(Exception error) {
        LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación", error);
        error.printStackTrace();
        JOptionPane.showMessageDialog(null,
                "Error al iniciar la aplicación: " + error.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    private static class ColorFormatter extends Formatter {
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_GREEN = "\u001B[32m";
        private static final String ANSI_RED = "\u001B[31m";
        private static final String ANSI_YELLOW = "\u001B[33m";

        @Override
        public String format(LogRecord record) {
            String mensaje = formatMessage(record);
            String color = "";
            String prefijo = "";

            if (record.getLevel() == Level.INFO) {
                color = ANSI_GREEN;
                prefijo = "✔ ";
            } else if (record.getLevel() == Level.SEVERE) {
                color = ANSI_RED;
                prefijo = "✖ ";
            } else if (record.getLevel() == Level.WARNING) {
                color = ANSI_YELLOW;
                prefijo = "⚠ ";
            }

            return color + prefijo + mensaje + ANSI_RESET + "\n";
        }
    }
}