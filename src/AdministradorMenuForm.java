import javax.swing.*;
import model.Usuario;

public class AdministradorMenuForm extends JFrame {
    private JButton registrarSolicitanteButton;
    private JButton verificarRequisitosButton;
    private JButton registrarExamenesButton;
    private JButton gestionDeTramitesButton;
    private JButton generarLicenciaButton;
    private JButton gestionDeUsuariosButton;
    private JButton cerrarSesionButton;
    private JPanel panelPrincipal;
    private GestionTramitesForm gestionForm;
    private Usuario usuario;
    private JTable tablaTramites;
    private int idTramite;

    public AdministradorMenuForm(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menú Administrador - " + usuario.getUsername());
        setContentPane(panelPrincipal);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        registrarSolicitanteButton.addActionListener(e ->
                abrirVentana(new RegistrarSolicitanteForm(usuario))
        );

        verificarRequisitosButton.addActionListener(e ->
                abrirVentana(new VerificarRequisitosForm(usuario))
        );

        registrarExamenesButton.addActionListener(e ->
                abrirVentana(new RegistrarExamenesForm(usuario, idTramite, gestionForm))
        );

        gestionDeTramitesButton.addActionListener(e ->
                abrirVentana(new GestionTramitesForm(usuario))
        );

        generarLicenciaButton.addActionListener(e ->
                abrirVentana(new GenerarLicenciaForm(usuario))
        );

        gestionDeUsuariosButton.addActionListener(e ->
                abrirVentana(new GestionUsuariosForm(usuario))
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

