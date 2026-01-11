import model.Usuario;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class ReportesForm extends JFrame {

    private JRadioButton PDFRadioButton;
    private JRadioButton CSVRadioButton;
    private JButton GENERARREPORTEButton;
    private JButton REGRESARButton;
    private JPanel panelPrincipal;

    private Usuario usuario;
    private JTable tablaTramites;

    public ReportesForm(Usuario usuario, JTable tablaTramites) {
        this.usuario = usuario;
        this.tablaTramites = tablaTramites;

        setTitle("Generar Reporte");
        setContentPane(panelPrincipal);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(PDFRadioButton);
        grupo.add(CSVRadioButton);

        inicializarEventos();
    }

    private void inicializarEventos() {

        GENERARREPORTEButton.addActionListener(e -> {
            if (PDFRadioButton.isSelected()) {
                generarPDF();
            } else if (CSVRadioButton.isSelected()) {
                generarCSV();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione PDF o CSV");
            }
        });

        REGRESARButton.addActionListener(e -> dispose());
    }

    // ================= PDF =================
    private void generarPDF() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String ruta = chooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".pdf")) ruta += ".pdf";

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            doc.add(new Paragraph("REPORTE DE TRÁMITES"));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(tablaTramites.getColumnCount());

            // encabezados
            for (int i = 0; i < tablaTramites.getColumnCount(); i++) {
                tabla.addCell(tablaTramites.getColumnName(i));
            }

            // datos
            for (int f = 0; f < tablaTramites.getRowCount(); f++) {
                for (int c = 0; c < tablaTramites.getColumnCount(); c++) {
                    Object val = tablaTramites.getValueAt(f, c);
                    tabla.addCell(val != null ? val.toString() : "");
                }
            }

            doc.add(tabla);
            doc.close();

            JOptionPane.showMessageDialog(this, "PDF generado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF");
        }
    }

    // ================= CSV =================
    private void generarCSV() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String ruta = chooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".csv")) ruta += ".csv";

        try (
                PrintWriter pw = new PrintWriter(
                        new java.io.OutputStreamWriter(
                                new java.io.FileOutputStream(ruta),
                                java.nio.charset.StandardCharsets.UTF_8
                        )
                )
        ) {

            // 🔹 BOM para Excel
            pw.print("\uFEFF");

            // encabezados
            for (int i = 0; i < tablaTramites.getColumnCount(); i++) {
                pw.print(tablaTramites.getColumnName(i));
                if (i < tablaTramites.getColumnCount() - 1) pw.print(";");
            }
            pw.println();

            // datos
            for (int f = 0; f < tablaTramites.getRowCount(); f++) {
                for (int c = 0; c < tablaTramites.getColumnCount(); c++) {
                    Object val = tablaTramites.getValueAt(f, c);
                    pw.print(val != null ? val.toString() : "");
                    if (c < tablaTramites.getColumnCount() - 1) pw.print(";");
                }
                pw.println();
            }

            JOptionPane.showMessageDialog(this, "CSV generado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar CSV");
        }
    }

}
