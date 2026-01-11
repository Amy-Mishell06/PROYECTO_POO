import javax.swing.*;

public class filtroTramite extends JFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> licencias;
    private JComboBox<String> estado;
    private JButton REGRESARButton;
    private JButton APLICARButton;

    private GestionTramitesForm gestionTramitesForm;

    public filtroTramite(GestionTramitesForm gestionTramitesForm) {
        this.gestionTramitesForm = gestionTramitesForm;

        setTitle("Filtrar Trámites");
        setContentPane(panelPrincipal);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cargarCombos();
        inicializarEventos();
    }

    private void cargarCombos() {
        // TIPOS DE LICENCIA
        licencias.removeAllItems();
        licencias.addItem("TODOS");
        licencias.addItem("Tipo A");
        licencias.addItem("Tipo A1");
        licencias.addItem("Tipo B");
        licencias.addItem("Tipo C");
        licencias.addItem("Tipo C1");
        licencias.addItem("Tipo D");
        licencias.addItem("Tipo E");
        licencias.addItem("Tipo F");
        licencias.addItem("Tipo G");

        // ESTADOS
        estado.removeAllItems();
        estado.addItem("TODOS");
        estado.addItem("pendiente");
        estado.addItem("en_examenes");
        estado.addItem("aprobado");
        estado.addItem("reprobado");
        estado.addItem("licencia_emitida");
    }

    private void inicializarEventos() {
        APLICARButton.addActionListener(e -> aplicarFiltro());

        REGRESARButton.addActionListener(e -> {
            gestionTramitesForm.setVisible(true);
            dispose();
        });
    }

    private void aplicarFiltro() {
        String estadoSeleccionado = estado.getSelectedItem().toString();
        String licenciaSeleccionada = licencias.getSelectedItem().toString();

        gestionTramitesForm.aplicarFiltro(estadoSeleccionado, licenciaSeleccionada);
        gestionTramitesForm.setVisible(true);
        dispose();
    }
}



