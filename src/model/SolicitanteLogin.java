package model;

public class SolicitanteLogin {
    private int id;
    private String cedula;
    private String username;
    private String password;
    private String estado;
    private int intentos;


    public SolicitanteLogin() {}

    public SolicitanteLogin(int id, String cedula, String username, String password, String estado, int intentos) {
        this.id = id;
        this.cedula = cedula;
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.intentos = intentos;
    }


    public int getId() { return id; }
    public String getCedula() { return cedula; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEstado() { return estado; }

    public void setId(int id) { this.id = id; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getIntentos() { return intentos; }

    public void setIntentos(int intentos) { this.intentos = intentos; }

}





