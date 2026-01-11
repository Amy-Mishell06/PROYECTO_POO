package model;

public class Usuario {

    private int id;
    private String nombre;
    private String cedula;
    private String username;
    private String rol;
    private String estado;

    public Usuario(int id, String nombre, String cedula, String username, String rol, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.username = username;
        this.rol = rol;
        this.estado = estado;
    }

    public Usuario() {

    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getUsername() { return username; }
    public String getRol() { return rol; }
    public String getEstado() { return estado; }

    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(String rol) { this.rol = rol; }
    public void setEstado(String estado) { this.estado = estado; }
}
