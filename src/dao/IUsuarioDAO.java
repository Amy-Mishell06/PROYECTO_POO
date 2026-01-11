package dao;

import model.Usuario;

public interface IUsuarioDAO {
    Usuario login(String username, String password);
    Usuario buscarPorUsernameOCedula(String texto);
    boolean actualizarUsuario(int id, String nombre, String estado);

}
