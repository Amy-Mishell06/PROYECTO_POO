package dao;

import model.SolicitanteLogin;

public interface SolicitanteLoginDAO {

    boolean crear(SolicitanteLogin solicitante);
    SolicitanteLogin login(String username, String password);
    SolicitanteLogin buscarPorCedula(String cedula);

    boolean actualizar(SolicitanteLogin login);
}
