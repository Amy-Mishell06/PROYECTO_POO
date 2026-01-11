package dao;

public interface ExamenDAO {

    boolean registrar(int tramiteId, double teorica, double practica, String estado);
}

