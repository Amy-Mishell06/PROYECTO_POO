package service;

import dao.SolicitanteLoginDAO;
import dao.SolicitanteLoginDAOMySQL;
import model.SolicitanteLogin;

public class SolicitanteLoginService {

    private final SolicitanteLoginDAO dao = new SolicitanteLoginDAOMySQL();

    public boolean crearLoginSolicitante(SolicitanteLogin login) {
        return dao.crear(login);
    }

    public SolicitanteLogin login(String username, String password) {
        return dao.login(username, password);
    }

    public SolicitanteLogin loginPorCedula(String cedula) {
        return dao.buscarPorCedula(cedula);
    }
    public boolean actualizarLoginSolicitante(SolicitanteLogin login) {
        return dao.actualizar(login);
    }

}



