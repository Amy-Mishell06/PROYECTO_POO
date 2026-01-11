package dao;

import conexion.ConexionDB;
import model.Tramite;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TramiteDAOMySQL implements TramiteDAO {

    @Override
    public void crearTramite(String cedula, String nombre, String tipoLicencia) {

        String sql = """
            INSERT INTO tramites
            (cedula, nombre, tipo_licencia, fecha_solicitud, estado)
            VALUES (?, ?, ?, NOW(), 'PENDIENTE')
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setString(2, nombre);
            ps.setString(3, tipoLicencia);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error crear trámite: " + e.getMessage());
        }
    }

    @Override
    public List<Tramite> listarTramites() {

        List<Tramite> lista = new ArrayList<>();
        String sql = "SELECT * FROM tramites ORDER BY tramite_id DESC";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Tramite t = new Tramite(
                        rs.getInt("tramite_id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getTimestamp("fecha_solicitud").toLocalDateTime(),
                        rs.getString("estado")
                );
                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Error listar trámites: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Tramite> buscarPorCedulaONombre(String texto) {
        String sql = """
        SELECT * FROM tramites
        WHERE cedula LIKE ? OR nombre LIKE ? OR tipo_licencia LIKE ?
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String like = "%" + texto + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);

            ResultSet rs = ps.executeQuery();
            List<Tramite> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new Tramite(
                        rs.getInt("tramite_id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_solicitud").toLocalDate().atStartOfDay(),
                        rs.getString("estado")
                ));
            }
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    @Override
    public Integer buscarTramiteId(String texto) {

        String sql = """
            SELECT tramite_id FROM tramites
            WHERE cedula = ? OR nombre LIKE ?
            LIMIT 1
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, texto);
            ps.setString(2, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("tramite_id");
            }

        } catch (SQLException e) {
            System.out.println("Error buscar ID trámite: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizarEstado(int tramiteId, String estado) {

        String sql = "UPDATE tramites SET estado = ? WHERE tramite_id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, tramiteId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error actualizar estado: " + e.getMessage());
        }
    }

    @Override
    public String obtenerEstadoTramite(int idTramite) {

        String sql = "SELECT estado FROM tramites WHERE tramite_id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTramite);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("estado");
            }

        } catch (SQLException e) {
            System.out.println("Error obtener estado trámite: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Tramite buscarPorId(int id) {
        String sql = "SELECT * FROM tramites WHERE tramite_id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Tramite(
                        rs.getInt("tramite_id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_solicitud").toLocalDate().atStartOfDay(),
                        rs.getString("estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
