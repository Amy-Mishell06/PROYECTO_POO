import model.Tramite;
import model.Usuario;
import service.TramiteService;
import service.LicenciaService;

import javax.swing.*;
import java.time.LocalDate;
import java.util.UUID;

public class GenerarLicenciaForm extends JFrame {

    private JPanel panelPrincipal;
    private JButton GENERARButton;
    private JButton REGRESARButton;
    private JButton BUSCARButton;
    private JTextField txtBuscar;

    private String numeroLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    private Usuario usuario;
        private int idTramite;

    public GenerarLicenciaForm(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Generar Licencia");
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarEventos();
    }

    private void inicializarEventos() {
        BUSCARButton.addActionListener(e -> buscarYGenerar());
        REGRESARButton.addActionListener(e -> regresar());
        // El botón GENERAR ya no es necesario, porque la licencia se genera al buscar
        GENERARButton.setEnabled(false);
    }

    private void buscarYGenerar() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese la cédula del usuario",
                    "Atención",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        TramiteService tramiteService = new TramiteService();
        Integer id = tramiteService.buscarTramiteId(texto);

        if (id == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún trámite para esa cédula",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String estado = tramiteService.obtenerEstadoTramite(id);

        if (!"aprobado".equalsIgnoreCase(estado)) {
            JOptionPane.showMessageDialog(this,
                    "El usuario no aprobó los exámenes.\nNo se puede generar la licencia.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Generar la licencia
        numeroLicencia = generarNumeroLicencia();
        fechaEmision = LocalDate.now();
        fechaVencimiento = fechaEmision.plusYears(5);

        LicenciaService licenciaService = new LicenciaService();
        boolean ok = licenciaService.generarLicencia(id, numeroLicencia, fechaEmision, fechaVencimiento);

        if (ok) {
            tramiteService.actualizarEstadoTramite(id, "licencia_emitida");

            JOptionPane.showMessageDialog(this,
                    "Licencia generada correctamente.\nNúmero: " + numeroLicencia,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al generar la licencia",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private String generarNumeroLicencia() {
        return UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    private void regresar() {
        if ("ANALISTA".equalsIgnoreCase(usuario.getRol())) {
            new AnalistaMenuForm(usuario).setVisible(true);
        } else if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            new AdministradorMenuForm(usuario).setVisible(true);
        }
        dispose();
    }
}


