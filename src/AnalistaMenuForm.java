import javax.swing.*;

public class AnalistaMenuForm extends JFrame{
    private JPanel panelPrincipal;
    private JButton SolicitanteButton;
    private JButton RequisitosButton;
    private JButton ExámenesButton;
    private JButton cerrarSesiónButton;
    private JButton LicenciaButton;
    private JButton TrámitesButton;
    private JLabel Titulo;

    public AnalistaMenuForm() {
        setTitle("Menú Analista");
        setContentPane(panelPrincipal);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        SolicitanteButton.addActionListener(e -> abrirVentana(new RegistrarSolicitanteForm()));

        RequisitosButton.addActionListener(e -> abrirVentana(new VerificarRequisitosForm()));

        ExámenesButton.addActionListener(e -> abrirVentana(new RegistrarExamenesForm()));

        TrámitesButton.addActionListener(e -> abrirVentana(new GestionTramitesForm()));

        LicenciaButton.addActionListener(e -> abrirVentana(new GenerarLicenciaForm()));

        cerrarSesiónButton.addActionListener(e -> cerrarSesion());
    }

    private void abrirVentana(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }

    private void cerrarSesion() {
        new Login("ANALISTA").setVisible(true);
        dispose();
    }
}
