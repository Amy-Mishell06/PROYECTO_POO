package model;

import java.time.LocalDateTime;

public class Tramite {

    private int id;
    private String cedula;
    private String nombre;
    private String tipoLicencia;
    private LocalDateTime fechaSolicitud;
    private String estado;

    public Tramite() {}

    public Tramite(int id, String cedula, String nombre,
                   String tipoLicencia, LocalDateTime fechaSolicitud,
                   String estado) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.tipoLicencia = tipoLicencia;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}



