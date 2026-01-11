package model;

public class Solicitante {

    private int id;  // nuevo atributo
    private String cedula;
    private String nombre;
    private String tipoLicencia;
    private String estado;

    public Solicitante(String cedula, String nombre, String tipoLicencia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.tipoLicencia = tipoLicencia;
        this.estado = "ACTIVO"; // estado inicial
    }

    // constructor completo
    public Solicitante(int id, String cedula, String nombre, String tipoLicencia, String estado) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.tipoLicencia = tipoLicencia;
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
    public String getNombre() {
        return nombre;
    }
    public String getTipoLicencia() {
        return tipoLicencia;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}


