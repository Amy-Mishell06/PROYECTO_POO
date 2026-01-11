package model;

import java.time.LocalDate;

public class Licencia {

    private int id;
    private int idTramite;
    private String numero;
    private String tipoLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    // Constructor completo
    public Licencia(int id, int idTramite, String numero, String tipoLicencia,
                    LocalDate fechaEmision, LocalDate fechaVencimiento) {
        this.id = id;
        this.idTramite = idTramite;
        this.numero = numero;
        this.tipoLicencia = tipoLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Constructor sin id (para crear una licencia antes de insertarla en DB)
    public Licencia(int idTramite, String numero, String tipoLicencia,
                    LocalDate fechaEmision, LocalDate fechaVencimiento) {
        this.idTramite = idTramite;
        this.numero = numero;
        this.tipoLicencia = tipoLicencia;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public int getIdTramite() {
        return idTramite;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}

