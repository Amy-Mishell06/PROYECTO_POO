package service;

import dao.LicenciaDAO;
import dao.LicenciaDAOMySQL;
import dao.TramiteDAO;
import dao.TramiteDAOMySQL;
import model.Licencia;
import model.Tramite;

import java.time.LocalDate;
import java.util.List;

public class LicenciaService {

    private final LicenciaDAO dao = new LicenciaDAOMySQL();
    private final TramiteDAO tramiteDAO = new TramiteDAOMySQL();

    // Generar licencia nueva
    public boolean generarLicencia(int idTramite,
                                   String numero,
                                   LocalDate fechaEmision,
                                   LocalDate fechaVencimiento) {
        return dao.insertarLicencia(idTramite, numero, fechaEmision, fechaVencimiento);
    }

    // Obtener licencia existente por tramite_id
    public Licencia obtenerLicenciaPorTramite(int idTramite) {
        return dao.obtenerLicenciaPorTramite(idTramite);
    }

}


