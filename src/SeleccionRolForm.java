import javax.swing.*;

public class SeleccionRolForm extends JFrame {
    private JPanel panelPrincipal;
    private JButton ADMINISTRADORButton;
    private JButton ANALISTAButton;
    private JLabel JLTitulo;

    public SeleccionRolForm() {
        setTitle("Rol");
        setContentPane(panelPrincipal);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ADMINISTRADORButton.addActionListener(e -> abrirLogin("ADMIN"));
        ANALISTAButton.addActionListener(e -> abrirLogin("ANALISTA"));
    }

    private void abrirLogin(String rol) {
        new Login(rol).setVisible(true);

        //cierra la ventana cuando se selecciona el rol
        dispose();
    }

}
