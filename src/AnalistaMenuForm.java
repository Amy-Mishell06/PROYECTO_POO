import javax.swing.*;
import model.Usuario;

public class AnalistaMenuForm extends JFrame {

    private JPanel panelPrincipal;
    private JButton SolicitanteButton;
    private JButton RequisitosButton;
    private JButton ExámenesButton;
    private JButton cerrarSesionButton;
    private JButton LicenciaButton;
    private JButton TrámitesButton;
    private JLabel Titulo;

    private Usuario usuario;
    private GestionTramitesForm gestionForm;
    private int idTramite;

    public AnalistaMenuForm(Usuario usuario) {
        this.usuario = usuario;
        this.gestionForm = gestionForm;
        this.idTramite = idTramite;

        setTitle("Menú Analista - " + usuario.getUsername());
        setContentPane(panelPrincipal);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        SolicitanteButton.addActionListener(e ->
                abrirVentana(new RegistrarSolicitanteForm(usuario))
        );

        RequisitosButton.addActionListener(e ->
                abrirVentana(new VerificarRequisitosForm(usuario))
        );

        ExámenesButton.addActionListener(e ->
                abrirVentana(new RegistrarExamenesForm(usuario, idTramite, gestionForm))
        );

        TrámitesButton.addActionListener(e ->
                abrirVentana(new GestionTramitesForm(usuario))
        );

        LicenciaButton.addActionListener(e ->
                abrirVentana(new GenerarLicenciaForm(usuario))
        );

        cerrarSesionButton.addActionListener(e -> cerrarSesion());
    }

    private void abrirVentana(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }

    private void cerrarSesion() {
        new Login().setVisible(true);
        dispose();
    }
}
