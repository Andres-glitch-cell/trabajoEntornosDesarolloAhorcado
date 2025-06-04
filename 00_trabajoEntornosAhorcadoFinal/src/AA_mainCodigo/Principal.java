package AA_mainCodigo;

import BB_modeloBBDD_02.RespaldoAutomatico;
import CC_vistaCodigoInterfaz_03.PantallaBienvenida;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;

public class Principal {
    private static final Logger LOGGER = Logger.getLogger(Principal.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);

            FileHandler fh = new FileHandler("Principal.log", true);
            fh.setEncoding("UTF-8");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            ch.setFormatter(new ColorFormatter());  // Usamos formatter con color y ticks aquí
            LOGGER.addHandler(ch);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo crear el archivo de logs: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            printAhorcadoAscii();  // Muestra el banner claro y espacioso

            Thread.sleep(2000);

            LOGGER.info("Iniciando juego...");
            Thread.sleep(2000);

            LOGGER.info("Cargando base de datos...");
            Thread.sleep(2000);

            LOGGER.info("Iniciando respaldo automático...");
            RespaldoAutomatico.iniciarRespaldo();

            LOGGER.info("Mostrando pantalla de bienvenida...");
            PantallaBienvenida.mostrarVentana();

            LOGGER.info("Aplicación iniciada correctamente.");

        } catch (Exception error) {
            LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación", error);
            error.printStackTrace();

            JOptionPane.showMessageDialog(null,
                    "Error al iniciar la aplicación: " + error.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    private static void printAhorcadoAscii() {
        String[] banner = new String[]{ "\n",
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

    // Formatter para mensajes INFO en verde con ticks en consola
    private static class ColorFormatter extends Formatter {
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_GREEN = "\u001B[32m";

        @Override
        public String format(LogRecord record) {
            String msg = formatMessage(record);
            if (record.getLevel() == Level.INFO) {
                return ANSI_GREEN + "✔ " + msg + " ✔" + ANSI_RESET + "\n";
            } else if (record.getLevel() == Level.SEVERE) {
                return "\u001B[31m" + msg + ANSI_RESET + "\n";
            }
            return msg + "\n";
        }
    }
}
