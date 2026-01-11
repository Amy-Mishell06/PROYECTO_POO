import javax.swing.*;
import model.Usuario;
import service.AuthService;
import service.SolicitanteLoginService;
import model.SolicitanteLogin;

public class Login extends JFrame {

    private JTextField textUsuario;
    private JPasswordField password1;
    private JButton entrarButton;
    private JButton salirButton;
    private JPanel panelPrincipal;
    private JLabel JLTitulo;
    private JLabel JLUsuario;
    private JLabel JLContraseña;

    public Login() {
        setTitle("Login");
        setContentPane(panelPrincipal);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        entrarButton.addActionListener(e -> validarLogin());
        salirButton.addActionListener(e -> System.exit(0));
    }

    private void validarLogin() {
        String usuarioTxt = textUsuario.getText().trim();
        String passTxt = new String(password1.getPassword()).trim();

        if (usuarioTxt.isEmpty() || passTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        // 1️⃣ Intentar autenticar como Admin o Analista
        AuthService authService = new AuthService();
        Usuario usuario = authService.autenticar(usuarioTxt, passTxt);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getRol());

            if ("ANALISTA".equalsIgnoreCase(usuario.getRol())) {
                new AnalistaMenuForm(usuario).setVisible(true);
            } else if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
                new AdministradorMenuForm(usuario).setVisible(true);
            }

            dispose();
            return;
        }

        // 2️⃣ Intentar autenticar como Solicitante
        SolicitanteLoginService solicitanteService = new SolicitanteLoginService();
        SolicitanteLogin solicitanteLogin = solicitanteService.login(usuarioTxt, passTxt);

        if (solicitanteLogin != null) {
            if ("ACTIVO".equalsIgnoreCase(solicitanteLogin.getEstado())) {
                JOptionPane.showMessageDialog(this,
                        "Bienvenido solicitante: " + solicitanteLogin.getUsername());

                // Crear un Usuario temporal para el solicitante
                Usuario solicitanteUsuario = new Usuario();
                solicitanteUsuario.setCedula(solicitanteLogin.getCedula());
                solicitanteUsuario.setNombre(solicitanteLogin.getUsername()); // o buscar nombre real
                solicitanteUsuario.setRol("SOLICITANTE");

                new SolicitanteDatos(solicitanteUsuario).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuario deshabilitado. Contacte con el administrador.");
            }
            return;
        }

        // 3️⃣ Si no se encontró ningún usuario
        JOptionPane.showMessageDialog(this,
                "Credenciales incorrectas o usuario inactivo");
    }
}


