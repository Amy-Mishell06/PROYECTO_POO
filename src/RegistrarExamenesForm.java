import javax.swing.*;

public class RegistrarExamenesForm extends JFrame {
    private JPanel panelPrincipal;
    private JTextField textTeorica;
    private JTextField textPractica;
    private JButton GUARDARButton;
    private JButton REGRESARButton;
    private JLabel Titulo;
    private JLabel JLabelTeorica;
    private JLabel JLabelPractica;

    public RegistrarExamenesForm() {
        setTitle("Exámenes");
        setContentPane(panelPrincipal);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GUARDARButton.addActionListener(e -> guardarExamenes());
        REGRESARButton.addActionListener(e -> regresarMenu());
    }

    private void guardarExamenes() {
        String notaTeoricaTxt = textTeorica.getText().trim();
        String notaPracticaTxt = textPractica.getText().trim();

        if (notaTeoricaTxt.isEmpty() || notaPracticaTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese todas las notas",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double notaTeorica = Double.parseDouble(notaTeoricaTxt);
            double notaPractica = Double.parseDouble(notaPracticaTxt);

            if (notaTeorica < 0 || notaTeorica > 20 ||
                    notaPractica < 0 || notaPractica > 20) {

                JOptionPane.showMessageDialog(this,
                        "Las notas deben estar entre 0 y 20",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Exámenes registrados correctamente");

        } catch (NumberFormatException ex) {
            // Si el usuario escribe letras
            JOptionPane.showMessageDialog(this,
                    "Ingrese solo números en las notas",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresarMenu() {
        new AnalistaMenuForm().setVisible(true);
        dispose();
    }
}
