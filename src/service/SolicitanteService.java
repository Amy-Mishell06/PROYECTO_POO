package service;

import dao.SolicitanteDAO;
import dao.SolicitanteDAOMySQL;
import model.Solicitante;

public class SolicitanteService {

    private SolicitanteDAO dao = new SolicitanteDAOMySQL();

    public boolean registrarSolicitante(Solicitante s) {
        return dao.registrar(s);
    }
}
