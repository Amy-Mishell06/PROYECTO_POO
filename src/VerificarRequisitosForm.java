import javax.swing.*;

import model.Usuario;
import service.TramiteService;

import javax.swing.*;

public class VerificarRequisitosForm extends JFrame {

    private JPanel panelPrincipal;
    private JButton REGRESARButton;
    private JTextField textObservaciones;
    private JButton GUARDARButton;
    private JButton LIMPIARButton;
    private JCheckBox checkCertificado;
    private JCheckBox checkPago;
    private JCheckBox checkMultas;
    private JButton BUSCARButton;
    private JTextField txtBuscar;
    private JLabel Titulo;
    private JLabel JLabelObservaciones;
    private JLabel JLabelCertificado;
    private JLabel JLabelPago;
    private JLabel JLabelMultas;

    private Usuario usuario;
    private Integer idTramite = null;

    public VerificarRequisitosForm(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Verificación de Requisitos");
        setContentPane(panelPrincipal);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarEventos();
    }

    private void inicializarEventos() {

        BUSCARButton.addActionListener(e -> buscarTramite());
        GUARDARButton.addActionListener(e -> guardarRequisitos());
        LIMPIARButton.addActionListener(e -> limpiarCampos());
        REGRESARButton.addActionListener(e -> regresarMenu());
    }

    // ================= BUSCAR TRÁMITE =================
    private void buscarTramite() {

        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese cédula o nombre del solicitante");
            return;
        }

        TramiteService service = new TramiteService();
        idTramite = service.buscarTramiteId(texto);

        if (idTramite == null) {
            JOptionPane.showMessageDialog(this,
                    "Trámite no encontrado");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Trámite encontrado correctamente");
        }
    }

    // ================= GUARDAR REQUISITOS =================
    private void guardarRequisitos() {

        TramiteService service = new TramiteService();
        if (idTramite == null) {
            String texto = txtBuscar.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese cédula o nombre del solicitante");
                return;
            }
            idTramite = service.buscarTramiteId(texto);
            if (idTramite == null) {
                JOptionPane.showMessageDialog(this,
                        "Trámite no encontrado");
                return;
            }
        }
        boolean certificado = checkCertificado.isSelected();
        boolean pago = checkPago.isSelected();
        boolean multas = checkMultas.isSelected();
        String nuevoEstado;

        if (certificado && pago && !multas) {
            nuevoEstado = "en_examenes";
        } else {
            nuevoEstado = "pendiente_requisitos";
        }
        service.actualizarEstadoTramite(idTramite, nuevoEstado);
        JOptionPane.showMessageDialog(this,
                "Requisitos verificados.\nEstado actualizado a: " + nuevoEstado);

        limpiarCampos();
        idTramite = null;
    }


    // ================= LIMPIAR =================
    private void limpiarCampos() {
        txtBuscar.setText("");
        checkCertificado.setSelected(false);
        checkPago.setSelected(false);
        checkMultas.setSelected(false);
        textObservaciones.setText("");
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
