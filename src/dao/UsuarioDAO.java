package dao;

import model.Usuario;

public interface UsuarioDAO {

    boolean crearUsuarioSolicitante(Usuario usuario);

    Usuario login(String username, String password);

    Usuario buscarPorUsername(String username);

    boolean actualizarEstado(int id, boolean activo);
}

