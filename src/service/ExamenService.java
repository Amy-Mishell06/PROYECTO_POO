package service;

import conexion.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExamenService {

    public boolean registrarExamen(int tramiteId,
                                   double teorica,
                                   double practica,
                                   String estado) {

        String sql = """
            INSERT INTO examenes
            (tramite_id, nota_teorica, nota_practica, estado, fecha)
            VALUES (?, ?, ?, ?, NOW())
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tramiteId);
            ps.setDouble(2, teorica);
            ps.setDouble(3, practica);
            ps.setString(4, estado);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error registrar examen: " + e.getMessage());
            return false;
        }
    }
}


