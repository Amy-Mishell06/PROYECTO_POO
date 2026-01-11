package dao;

import model.SolicitanteLogin;
import conexion.ConexionDB;

import java.sql.*;

public class SolicitanteLoginDAOMySQL implements SolicitanteLoginDAO {

    @Override
    public boolean actualizar(SolicitanteLogin login) {
        String sql = "UPDATE solicitante_login SET password = ?, estado = ? WHERE username = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login.getPassword());
            ps.setString(2, login.getEstado());
            ps.setString(3, login.getUsername());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean crear(SolicitanteLogin login) {
        String sql = "INSERT INTO solicitante_login (cedula, username, password, estado) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login.getCedula());
            ps.setString(2, login.getUsername());
            ps.setString(3, login.getPassword());
            ps.setString(4, login.getEstado());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error crear login solicitante: " + e.getMessage());
            return false;
        }
    }

    @Override
    public SolicitanteLogin login(String username, String password) {

        String buscar = "SELECT * FROM solicitante_login WHERE username = ?";
        String actualizar = "UPDATE solicitante_login SET intentos = ?, estado = ? WHERE username = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement psBuscar = con.prepareStatement(buscar)) {
            psBuscar.setString(1, username);
            ResultSet rs = psBuscar.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int intentos = rs.getInt("intentos");
            String estado = rs.getString("estado");
            String passBD = rs.getString("password");
            // Si esta bloqueado
            if ("DESHABILITADO".equalsIgnoreCase(estado)) {
                return null;
            }
            // Password correcto
            if (passBD.equals(password)) {
                try (PreparedStatement psUpdate = con.prepareStatement(actualizar)) {
                    psUpdate.setInt(1, 0);
                    psUpdate.setString(2, "ACTIVO");
                    psUpdate.setString(3, username);
                    psUpdate.executeUpdate();
                }
                return new SolicitanteLogin(
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        passBD,
                        "ACTIVO",
                        0
                );
            }
            //error en la contraseña
            intentos++;
            String nuevoEstado = intentos >= 3 ? "DESHABILITADO" : "ACTIVO";
            try (PreparedStatement psUpdate = con.prepareStatement(actualizar)) {
                psUpdate.setInt(1, intentos);
                psUpdate.setString(2, nuevoEstado);
                psUpdate.setString(3, username);
                psUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error login solicitante: " + e.getMessage());
        }
        return null;
    }


    @Override
    public SolicitanteLogin buscarPorCedula(String cedula) {
        String sql = """
        SELECT * FROM solicitante_login
        WHERE cedula = ? 
        """;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new SolicitanteLogin(
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("estado"),
                        rs.getInt("intentos")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error buscar usuario por cédula: " + e.getMessage());
        }
        return null;
    }


}
