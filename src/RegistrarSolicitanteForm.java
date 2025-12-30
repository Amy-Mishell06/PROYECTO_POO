import javax.swing.*;

public class RegistrarSolicitanteForm extends JFrame {
    private JPanel panelPrincipal;
    private JButton REGRESARButton;
    private JTextField textCedula;
    private JTextField textNombre;
    private JTextField textFecha;
    private JButton LIMPIARButton;
    private JButton GUARDARButton;
    private JComboBox<String> comboLicencia;
    private JLabel Titulo;
    private JLabel JLabelCedula;
    private JLabel JLabelNombre;
    private JLabel JLabelLicencia;
    private JLabel JLabelFecha;

    public RegistrarSolicitanteForm() {
        setTitle("Registro de Solicitantes");
        setContentPane(panelPrincipal);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //textCedula.setEditable(false);
        //textNombre.setEditable(false);
        //textFecha.setEditable(false);

        cargarTiposLicencia();

        GUARDARButton.addActionListener(e -> guardarSolicitante());
        LIMPIARButton.addActionListener(e -> limpiarCampos());
        REGRESARButton.addActionListener(e -> regresarMenu());

    }

    private void cargarTiposLicencia() {
        //Elimina opciones anteriores
        comboLicencia.removeAllItems();

        comboLicencia.addItem("Seleccione ...");
        comboLicencia.addItem("Tipo A");
        comboLicencia.addItem("Tipo A1");
        comboLicencia.addItem("Tipo B");
        comboLicencia.addItem("Tipo C");
        comboLicencia.addItem("Tipo C1");
        comboLicencia.addItem("Tipo D");
        comboLicencia.addItem("Tipo E");
        comboLicencia.addItem("Tipo F");
        comboLicencia.addItem("Tipo G");

    }

    private void guardarSolicitante() {
        String cedula = textCedula.getText().trim();
        String nombre = textNombre.getText().trim();
        String tipoLicencia = (String) comboLicencia.getSelectedItem().toString();
        String fecha = textFecha.getText();

        //Validacion de los campos
        if (cedula.isEmpty() || nombre.isEmpty() || tipoLicencia.equals("Seleccione")) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Solicitante registrado correctamente");
    }

    private void limpiarCampos() {
        textCedula.setText("");
        textNombre.setText("");
        comboLicencia.setSelectedItem("");
    }

    private void regresarMenu() {
        new AnalistaMenuForm().setVisible(true);
        dispose();
    }
}
