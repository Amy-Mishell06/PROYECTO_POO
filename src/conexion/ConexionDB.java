package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL =
            "jdbc:mysql://uphwnbxobuw5nec9:jvAStqb3nYSvMKeEVLUZ@blwpbcvmvszsrmw0lhvl-mysql.services.clever-cloud.com:3306/blwpbcvmvszsrmw0lhvl";
    private static final String USER = "uphwnbxobuw5nec9";
    private static final String PASSWORD = "jvAStqb3nYSvMKeEVLUZ";

    public static Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }
}


