import javax.swing.*;

public class Login extends JFrame{
    private JTextField textUsuario;
    private JButton entrarButton;
    private JButton salirButton;
    private JLabel JLUsuario;
    private JLabel JLContraseña;
    private JLabel JLTitulo;
    private JPanel panelPrincipal;
    private JPasswordField password1;

    //guarda el rol
    private String rol;

    public Login(String rol) {
        this.rol = rol;
        setTitle("Login - " + rol);
        setContentPane(panelPrincipal);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        entrarButton.addActionListener(e -> validarLogin());
        salirButton.addActionListener(e -> System.exit(0));
    }

    //valida usuario y contraseña
    private void validarLogin() {
        String usuario = textUsuario.getText();
        String contraseña = new String(password1.getPassword());

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        //validacion SIMPLE
        if (rol.equals("ADMIN") && usuario.equals("admin") && contraseña.equals("123")) {
            JOptionPane.showMessageDialog(this, "Bienvenido ADMIN");

        } else if (rol.equals("ANALISTA") && usuario.equals("analista") && contraseña.equals("123")) {
            JOptionPane.showMessageDialog(this, "Bienvenido ANALISTA");

        }else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }

}
