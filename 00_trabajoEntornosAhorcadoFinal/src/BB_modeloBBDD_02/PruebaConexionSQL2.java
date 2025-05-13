package BB_modeloBBDD_02;

import java.sql.*;
import java.util.Scanner;


public class PruebaConexionSQL2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/Ahorcado";
        String usuario = "root";
        String password = "abcd1234";
        Connection conexion = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Usuarios (ID, Nombre, Contraseña) VALUES (?,?,?)";
        try {
            // Conectar con la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);
            // Preparar la sentencia SQL
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, 300);
            stmt.setString(2, "Juan Pérez");
            stmt.setString(3, "JuanPerez");
            // Ejecutar la sentencia SQL
            int filasAfectadas = stmt.executeUpdate();
            System.out.println(": " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            try {
                if (stmt != null) stmt.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}