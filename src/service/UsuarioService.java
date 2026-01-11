package service;

import dao.IUsuarioDAO;
import dao.UsuarioDAOMySQL;
import model.Usuario;

public class UsuarioService {

    private final IUsuarioDAO dao = new UsuarioDAOMySQL();

    public Usuario buscarUsuario(String texto) {
        return dao.buscarPorUsernameOCedula(texto);
    }

    public boolean actualizarUsuario(int id, String nombre, String estado) {
        return dao.actualizarUsuario(id, nombre, estado);
    }
}
