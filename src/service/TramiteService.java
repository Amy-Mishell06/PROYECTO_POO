package service;

import dao.TramiteDAO;
import dao.TramiteDAOMySQL;
import model.Tramite;

import java.util.List;

public class TramiteService {

    private final TramiteDAO dao = new TramiteDAOMySQL();

    public void crearTramite(String cedula,
                                             String nombre,
                                             String tipoLicencia) {
        dao.crearTramite(cedula, nombre, tipoLicencia);
    }

    public List<Tramite> obtenerTramites() {
        return dao.listarTramites();
    }

    public List<Tramite> buscar(String texto) {
        return dao.buscarPorCedulaONombre(texto);
    }

    public Integer buscarTramiteId(String texto) {
        return dao.buscarTramiteId(texto);
    }

    public String obtenerEstadoTramite(int idTramite) {
        return dao.obtenerEstadoTramite(idTramite);
    }

    public void actualizarEstadoTramite(int id, String estado) {
        dao.actualizarEstado(id, estado);
    }
    public Tramite obtenerUltimoTramite(String cedula) {
        Integer ultimoId = dao.buscarTramiteId(cedula); // tu método ya existe
        if (ultimoId != null) {
            return dao.buscarPorId(ultimoId); // ESTE método debes agregarlo en tu DAO
        }
        return null;
    }


}



