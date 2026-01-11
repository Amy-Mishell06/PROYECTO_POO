import model.Tramite;
import model.Usuario;
import service.ExamenService;
import service.TramiteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class RegistrarExamenesForm extends JFrame {

    private JPanel panelPrincipal;
    private JTextField textTeorica;
    private JTextField textPractica;
    private JButton GUARDARButton;
    private JButton REGRESARButton;
    private JButton BUSCARButton;
    private JLabel  Titulo;
    private JLabel  JLabelTeorica;
    private JLabel  JLabelPractica;
    private JTextField txtBuscar;

    private Usuario usuario;
    private int idTramite;

    private GestionTramitesForm gestionForm;

    private void bloquearFormulario(boolean bloquear) {
        textTeorica.setEnabled(!bloquear);
        textPractica.setEnabled(!bloquear);
        GUARDARButton.setEnabled(!bloquear);
    }

    public RegistrarExamenesForm(Usuario usuario, int idTramite, GestionTramitesForm gestionForm) {
        this.usuario = usuario;
        this.idTramite = idTramite;
        this.gestionForm = gestionForm;

        setTitle("Registrar Exámenes");
        setContentPane(panelPrincipal);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarEventos();
    }

    private void inicializarEventos() {

        GUARDARButton.addActionListener(e -> guardarExamenes());
        BUSCARButton.addActionListener(e -> buscar());
        REGRESARButton.addActionListener(e -> regresar());
    }
    private void buscar() {

        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese cédula o nombre");
            return;
        }

        TramiteService service = new TramiteService();
        Integer tramiteIdEncontrado = service.buscarTramiteId(texto);

        if (tramiteIdEncontrado == null) {
            JOptionPane.showMessageDialog(this,
                    "Trámite no encontrado");
            bloquearFormulario(true);
            return;
        }

        String estado = service.obtenerEstadoTramite(tramiteIdEncontrado);

        if (!"en_examenes".equalsIgnoreCase(estado)) {
            JOptionPane.showMessageDialog(this,
                    "No se puede registrar el examen.\n" +
                            "El Usuario NO cumple todos los requisitos.\n\n" +
                            "Estado actual: " + estado.toUpperCase(),
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);

            bloquearFormulario(true);
            return;
        }

        this.idTramite = tramiteIdEncontrado;
        bloquearFormulario(false);

        JOptionPane.showMessageDialog(this,
                "Usuario habilitado para registro de calificaciones");
    }

    // ================= GUARDAR EXÁMENES =================
    private void guardarExamenes() {

        String teoricaTxt = textTeorica.getText().trim();
        String practicaTxt = textPractica.getText().trim();

        if (teoricaTxt.isEmpty() || practicaTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese ambas notas",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double teorica = Double.parseDouble(teoricaTxt);
            double practica = Double.parseDouble(practicaTxt);

            if (teorica < 0 || teorica > 20 || practica < 0 || practica > 20) {
                JOptionPane.showMessageDialog(this,
                        "Las notas deben estar entre 0 y 20",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String estadoFinal = (teorica >= 14 && practica >= 14)
                    ? "aprobado"
                    : "reprobado";

            ExamenService examenService = new ExamenService();
            TramiteService tramiteService = new TramiteService();

            boolean ok = examenService.registrarExamen(
                    idTramite,
                    teorica,
                    practica,
                    estadoFinal
            );

            if (ok) {
                tramiteService.actualizarEstadoTramite(idTramite, estadoFinal);

                JOptionPane.showMessageDialog(this,
                        "Exámenes registrados correctamente\nEstado: " + estadoFinal.toUpperCase(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                gestionForm.refrescarTabla();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar los exámenes",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese solo números válidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void regresar() {
        if (usuario.getRol().equals("ANALISTA")) {
            new AnalistaMenuForm(usuario).setVisible(true);
        } else if (usuario.getRol().equals("ADMIN")) {
            new AdministradorMenuForm(usuario).setVisible(true);
        }
        dispose();
    }
}

