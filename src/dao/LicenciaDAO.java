package dao;

import model.Licencia;

import java.time.LocalDate;

public interface LicenciaDAO {

    boolean insertarLicencia(int idTramite,
                             String numero,
                             LocalDate fechaEmision,
                             LocalDate fechaVencimiento);

    Licencia obtenerLicenciaPorTramite(int idTramite);
}

