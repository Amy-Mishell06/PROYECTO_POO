package dao;

import conexion.ConexionDB;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAOMySQL implements IUsuarioDAO {
    @Override
    public Usuario buscarPorUsernameOCedula(String texto) {
        String sql = """
        SELECT * FROM usuarios
        WHERE username = ? OR cedula = ?
    """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, texto);
            ps.setString(2, texto);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("rol"),
                        rs.getString("estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actualizarUsuario(int id, String nombre, String estado) {
        String sql = """
        UPDATE usuarios
        SET nombre = ?, estado = ?
        WHERE id = ?
    """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, estado);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario login(String username, String password) {

        String sql = """
            SELECT * FROM usuarios
            WHERE username = ?
              AND password = ?
              AND estado = 'ACTIVO'
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("rol"),
                        rs.getString("estado")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


