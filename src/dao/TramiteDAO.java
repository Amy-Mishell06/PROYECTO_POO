package dao;

import model.Tramite;
import java.util.List;

public interface TramiteDAO {

    void crearTramite(String cedula, String nombre, String tipoLicencia);
    List<Tramite> listarTramites();
    List<Tramite> buscarPorCedulaONombre(String texto);
    Integer buscarTramiteId(String texto);
    void actualizarEstado(int tramiteId, String estado);
    String obtenerEstadoTramite(int idTramite);
    Tramite buscarPorId(int id);

}




