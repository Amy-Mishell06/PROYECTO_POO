import model.Usuario;
import model.Tramite;
import service.TramiteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class GestionTramitesForm extends JFrame {

    private JButton BUSCARButton;
    private JTable tablaTramites;
    private JTextField txtBuscar;
    private JButton VERDETALLEButton;
    private JButton REGRESARButton;
    private JButton REGISTRAREXAMENButton;
    private JButton GENERARLICENCIAButton;
    private JButton FILTRARPORESTADOButton;
    private JButton REQUERIMIENTOSButton;
    private JPanel panelPrincipal;
    private JScrollPane scrollTabla;

    private DefaultTableModel modelo;
    private Usuario usuario;
    private GestionTramitesForm gestionForm;
    private int idTramite;

    public GestionTramitesForm(Usuario usuario) {
        this.usuario = usuario;
        this.gestionForm = this;

        setTitle("Gestión de Trámites");
        setContentPane(panelPrincipal);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarTabla();
        cargarTramites();
        inicializarEventos();
    }


    // ================= TABLA =================
    private void inicializarTabla() {

        modelo = new DefaultTableModel(
                new Object[]{"ID", "CÉDULA", "NOMBRE", "TIPO", "FECHA SOLICITUD", "ESTADO"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTramites.setModel(modelo);

    }


    // ================= CARGAR TODO =================
    private void cargarTramites() {

        modelo.setRowCount(0);

        TramiteService service = new TramiteService();
        var lista = service.obtenerTramites();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No existen trámites registrados",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Tramite t : lista) {
            modelo.addRow(new Object[]{
                    t.getId(),
                    t.getCedula(),
                    t.getNombre(),
                    t.getTipoLicencia(),
                    t.getFechaSolicitud().format(formato),
                    t.getEstado()
            });
        }
    }


    // ================= BUSCAR =================
    private void buscar() {

        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            cargarTramites();
            return;
        }

        modelo.setRowCount(0);

        TramiteService service = new TramiteService();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Tramite t : service.buscar(texto)) {
            modelo.addRow(new Object[]{
                    t.getId(),
                    t.getCedula(),
                    t.getNombre(),
                    t.getTipoLicencia(),
                    t.getFechaSolicitud().format(formato),
                    t.getEstado()
            });
        }
    }

    // ================= EVENTOS =================
    private void inicializarEventos() {

        BUSCARButton.addActionListener(e -> buscar());
        VERDETALLEButton.addActionListener(e -> verDetalle());
        FILTRARPORESTADOButton.addActionListener(e -> filtrarPorEstado());
        REQUERIMIENTOSButton.addActionListener(e -> marcarRequisitosOK());
        REGISTRAREXAMENButton.addActionListener(e -> registrarExamen());
        GENERARLICENCIAButton.addActionListener(e -> generarLicencia());
        REGRESARButton.addActionListener(e -> regresar());
    }

    // ================= ACCIONES =================
    private void verDetalle() {
        new ReportesForm(usuario, tablaTramites).setVisible(true);
    }

    public void aplicarFiltro(String estado, String licencia) {
        modelo.setRowCount(0);

        TramiteService service = new TramiteService();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Tramite t : service.obtenerTramites()) {
            boolean coincide = true;

            if (!estado.equals("TODOS") && !t.getEstado().equalsIgnoreCase(estado)) {
                coincide = false;
            }

            if (!licencia.equals("TODOS") && !t.getTipoLicencia().equalsIgnoreCase(licencia)) {
                coincide = false;
            }

            if (coincide) {
                modelo.addRow(new Object[]{
                        t.getId(),
                        t.getCedula(),
                        t.getNombre(),
                        t.getTipoLicencia(),
                        t.getFechaSolicitud().format(formato),
                        t.getEstado()
                });
            }
        }
    }

    private void filtrarPorEstado() {
        new filtroTramite(this).setVisible(true);
    }

    private void marcarRequisitosOK() {
        int fila = tablaTramites.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un trámite");
            return;
        }
        int idTramite = (int) modelo.getValueAt(fila, 0);
        new VerificarRequisitosForm(usuario).setVisible(true);
        setVisible(false);
    }


    private void registrarExamen() {
        new RegistrarExamenesForm(usuario, idTramite, gestionForm).setVisible(true);
        dispose();
    }

    private void generarLicencia() {
        new GenerarLicenciaForm(usuario).setVisible(true);
        dispose();
    }

    private void regresar() {
        if (usuario.getRol().equals("ANALISTA")) {
            new AnalistaMenuForm(usuario).setVisible(true);
        } else if (usuario.getRol().equals("ADMIN")) {
            new AdministradorMenuForm(usuario).setVisible(true);
        }
        dispose();
    }

    public void refrescarTabla() {
        cargarTramites();
        this.setVisible(true);
    }
}

