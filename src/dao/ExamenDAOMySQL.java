package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExamenDAOMySQL implements ExamenDAO {

    @Override
    public boolean registrar(int tramiteId, double teorica, double practica, String estado) {

        String sqlExamen = """
            INSERT INTO examenes (tramite_id, nota_teorica, nota_practica)
            VALUES (?, ?, ?)
        """;

        String sqlEstado = "UPDATE tramites SET estado=? WHERE id=?";

        try (Connection con = ConexionDB.getConexion()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlExamen);
                 PreparedStatement ps2 = con.prepareStatement(sqlEstado)) {

                ps1.setInt(1, tramiteId);
                ps1.setDouble(2, teorica);
                ps1.setDouble(3, practica);
                ps1.executeUpdate();

                ps2.setString(1, estado);
                ps2.setInt(2, tramiteId);
                ps2.executeUpdate();

                con.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
