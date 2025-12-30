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
    private JLabel Titulo;
    private JLabel JLabelObservaciones;
    private JLabel JLabelCertificado;
    private JLabel JLabelPago;
    private JLabel JLabelMultas;

    public VerificarRequisitosForm() {
        setTitle("Requisitos");
        setContentPane(panelPrincipal);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GUARDARButton.addActionListener(e -> guardarRequisitos());
        LIMPIARButton.addActionListener(e -> limpiarCampos());
        REGRESARButton.addActionListener(e -> regresarMenu());
    }

    private void guardarRequisitos() {
        boolean certificado = checkCertificado.isSelected();
        boolean pago = checkPago.isSelected();
        boolean multas = checkMultas.isSelected();
        String observaciones = textObservaciones.getText().trim();

        JOptionPane.showMessageDialog(this,
                "Requisitos verificados correctamente");
    }

    private void limpiarCampos() {
        checkCertificado.setSelected(false);
        checkPago.setSelected(false);
        checkMultas.setSelected(false);
        textObservaciones.setText("");
    }

    private void regresarMenu() {
        new AnalistaMenuForm().setVisible(true);
        dispose();
    }

}
