package dao;

import conexion.ConexionDB;
import model.Solicitante;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SolicitanteDAOMySQL implements SolicitanteDAO {

    @Override
    public boolean registrar(Solicitante s) {

        String sql = "INSERT INTO solicitantes (cedula, nombre, tipo_licencia, estado) " +
                "VALUES (?, ?, ?, ?)";


        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getCedula());
            ps.setString(2, s.getNombre());
            ps.setString(3, s.getTipoLicencia());
            ps.setString(4, s.getEstado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al registrar solicitante: " + e.getMessage());
            return false;
        }
    }
}
