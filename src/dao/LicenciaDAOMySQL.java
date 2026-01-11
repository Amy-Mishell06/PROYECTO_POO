package dao;

import conexion.ConexionDB;
import model.Licencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LicenciaDAOMySQL implements LicenciaDAO {

    @Override
    public Licencia obtenerLicenciaPorTramite(int idTramite) {
        String sql = """
            SELECT l.id, l.numero_licencia, t.tipo_licencia, l.fecha_emision, l.fecha_vencimiento
            FROM licencia l
            JOIN tramites t ON l.tramite_id = t.tramite_id
            WHERE l.tramite_id = ?
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTramite);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Licencia(
                        rs.getInt("id"),
                        idTramite,
                        rs.getString("numero_licencia"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_emision").toLocalDate(),
                        rs.getDate("fecha_vencimiento").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertarLicencia(int idTramite,
                                    String numero,
                                    LocalDate fechaEmision,
                                    LocalDate fechaVencimiento) {

        String sql = """
                INSERT INTO licencia
                (tramite_id, numero_licencia, fecha_emision, fecha_vencimiento)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTramite);
            ps.setString(2, numero);
            ps.setDate(3, java.sql.Date.valueOf(fechaEmision));
            ps.setDate(4, java.sql.Date.valueOf(fechaVencimiento));
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

