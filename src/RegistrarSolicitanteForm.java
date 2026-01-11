import model.Usuario;
import model.Solicitante;
import model.SolicitanteLogin;
import service.SolicitanteService;
import service.TramiteService;
import service.SolicitanteLoginService;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistrarSolicitanteForm extends JFrame {

    private JPanel panelPrincipal;
    private JButton REGRESARButton;
    private JTextField textCedula;
    private JTextField textNombre;
    private JButton LIMPIARButton;
    private JButton GUARDARButton;
    private JComboBox<String> comboLicencia;
    private JLabel Titulo;
    private JLabel JLabelCedula;
    private JLabel JLabelNombre;
    private JLabel JLabelLicencia;
    private JLabel JLabelFecha;
    private JLabel Fecha;

    private Usuario usuario; // ADMIN o ANALISTA

    public RegistrarSolicitanteForm(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Registro de Solicitantes");
        setContentPane(panelPrincipal);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cargarTiposLicencia();
        cargarFechaActual();

        GUARDARButton.addActionListener(e -> guardarSolicitante());
        LIMPIARButton.addActionListener(e -> limpiarCampos());
        REGRESARButton.addActionListener(e -> regresarMenu());
    }

    // ================= CARGA INICIAL =================
    private void cargarTiposLicencia() {
        comboLicencia.removeAllItems();
        comboLicencia.addItem("Seleccione ...");
        comboLicencia.addItem("Tipo A");
        comboLicencia.addItem("Tipo A1");
        comboLicencia.addItem("Tipo B");
        comboLicencia.addItem("Tipo C");
        comboLicencia.addItem("Tipo C1");
        comboLicencia.addItem("Tipo D");
        comboLicencia.addItem("Tipo E");
        comboLicencia.addItem("Tipo F");
        comboLicencia.addItem("Tipo G");
    }

    private void cargarFechaActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Fecha.setText(ahora.format(formato));
    }

    private boolean cedulaValida(String cedula) {
        return cedula.matches("\\d{10}");
    }

    // ================= GUARDAR =================
    private void guardarSolicitante() {

        String cedula = textCedula.getText().trim();
        String nombre = textNombre.getText().trim();
        String tipoLicencia = comboLicencia.getSelectedItem().toString();

        if (cedula.isEmpty() || nombre.isEmpty() || tipoLicencia.equals("Seleccione ...")) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios");
            return;
        }

        if (!cedulaValida(cedula)) {
            JOptionPane.showMessageDialog(this,
                    "Cedula mal ingresada, Revise",
                    "Cédula inválida",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1️⃣ Crear solicitante
        Solicitante solicitante = new Solicitante(cedula, nombre, tipoLicencia);
        solicitante.setEstado("ACTIVO"); // estado inicial

        SolicitanteService solicitanteService = new SolicitanteService();
        boolean registrado = solicitanteService.registrarSolicitante(solicitante);

        if (!registrado) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar solicitante");
            return;
        }

        // 2️⃣ Crear trámite
        TramiteService tramiteService = new TramiteService();
        tramiteService.crearTramite(cedula, nombre, tipoLicencia);

        // 3️⃣ Crear login para el solicitante
        String primerNombre = nombre.split("\\s+")[0];
        String password = cedula + primerNombre;
        SolicitanteLoginService loginService = new SolicitanteLoginService();
        SolicitanteLogin login = new SolicitanteLogin(
                0,
                solicitante.getCedula(),
                cedula,
                password,
                "ACTIVO",
                0 // intentos
        );


        boolean loginCreado = loginService.crearLoginSolicitante(login);
        if (!loginCreado) {
            JOptionPane.showMessageDialog(this,
                    "Solicitante registrado pero error al crear login");
            return;
        }

        // 4️⃣ Mostrar credenciales al usuario
        JOptionPane.showMessageDialog(this,
                "Solicitante registrado correctamente\n\n" +
                        "USUARIO: " + cedula);

        limpiarCampos();
    }

    // ================= LIMPIAR =================
    private void limpiarCampos() {
        textCedula.setText("");
        textNombre.setText("");
        comboLicencia.setSelectedIndex(0);
    }

    // ================= REGRESAR =================
    private void regresarMenu() {
        if (usuario.getRol().equals("ANALISTA")) {
            new AnalistaMenuForm(usuario).setVisible(true);
        } else {
            new AdministradorMenuForm(usuario).setVisible(true);
        }
        dispose();
    }
}

