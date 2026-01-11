import conexion.ConexionDB;

public class Main {
    public static void main(String[] args) {
        if (ConexionDB.getConexion() != null) {
            System.out.println("Conectando...");
            new Login().setVisible(true);
        } else {
            System.out.println("Error de conexión a la base de datos");
        }
    }
}
