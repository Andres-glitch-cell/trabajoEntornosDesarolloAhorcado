// Se importan las librerías necesarias para la gestión de base de datos
package BB_modeloBBDD_02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PruebaConexionSQL {
    public static void main(String[] args) {
        try {
// Se indica el nombre del driver de MySQL necesario para gestionar bases de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Se establece la conexión al servidor MySQL así como a su base de datos empresa
// El usuario y la contraseña son phpmyadmin 12345
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/AhorcadoAndres", "root", "abcd1234");
// Se define una variable de tipo Statement para poder ejecutar una sentencia SQL
            Statement stmt = con.createStatement();
// Se ejecuta la sentencia SQL
            ResultSet result = stmt.executeQuery("SELECT * FROM Usuarios");
        // Se recorren cada uno de los registros contenidos en la result
            // Para cada registro, en cada iteración del bucle, se visualiza su número de empleado
            while (result.next()) {
                int id_usuarios = result.getInt("id");
                int nombre_usuarios = result.getInt("id");
                System.out.println(nombre_usuarios);
            }
        } catch (Exception e) {
            // de datos o a su tabla EMP.
            System.out.println(e);
        }
    }
}