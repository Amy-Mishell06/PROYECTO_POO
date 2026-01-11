package service;

import dao.IUsuarioDAO;
import dao.UsuarioDAOMySQL;
import model.Usuario;

public class AuthService {

    private IUsuarioDAO usuarioDAO = new UsuarioDAOMySQL();

    public Usuario autenticar(String username, String password) {
        return usuarioDAO.login(username, password);
    }
}

