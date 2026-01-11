import model.Licencia;
import model.Tramite;
import model.Usuario;
import service.LicenciaService;
import service.TramiteService;
import java.io.File;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class SolicitanteDatos extends JFrame {

    private JPanel panelPrincipal;
    private JTable tablaTramites;
    private JButton DESCARGARLICENCIAButton;
    private JButton CERRARSESIONButton;
    private DefaultTableModel modelo;
    private Usuario usuario;

    private TramiteService tramiteService = new TramiteService();
    private LicenciaService licenciaService = new LicenciaService();

    public SolicitanteDatos(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Mis Trámites");
        setContentPane(panelPrincipal);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarTabla();
        cargarTramites(); // filtramos solo los trámites del solicitante
        inicializarEventos();
    }

    // ================= TABLA =================
    private void inicializarTabla() {
        modelo = new DefaultTableModel(
                new Object[]{"ID", "CÉDULA", "NOMBRE", "TIPO", "FECHA SOLICITUD", "ESTADO"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTramites.setModel(modelo);
    }

    // ================= CARGAR TRÁMITES =================
    private void cargarTramites() {
        modelo.setRowCount(0);

        // ✅ Filtramos solo los trámites del solicitante usando su cédula
        List<Tramite> lista = tramiteService.buscar(usuario.getCedula());

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No tiene trámites registrados",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Tramite t : lista) {
            modelo.addRow(new Object[]{
                    t.getId(),
                    t.getCedula(),
                    t.getNombre(),
                    t.getTipoLicencia(),
                    t.getFechaSolicitud().format(formato),
                    t.getEstado()
            });
        }
    }

    // ================= EVENTOS =================
    private void inicializarEventos() {
        DESCARGARLICENCIAButton.addActionListener(e -> descargarLicenciaPDF());
        CERRARSESIONButton.addActionListener(e -> cerrarSesion());
    }

    // ================= DESCARGAR LICENCIA =================
    private void descargarLicenciaPDF() {
        // Obtener el último trámite del usuario
        Tramite ultimoTramite = tramiteService.obtenerUltimoTramite(usuario.getCedula());
        if (ultimoTramite == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún trámite.");
            return;
        }

        // Obtener la licencia del último trámite
        Licencia licencia = licenciaService.obtenerLicenciaPorTramite(ultimoTramite.getId());
        if (licencia == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la licencia generada.");
            return;
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar licencia PDF");

            String nombreArchivo = "Licencia_" + licencia.getNumero() + ".pdf";
            fileChooser.setSelectedFile(new File(nombreArchivo));

            int seleccion = fileChooser.showSaveDialog(this);

            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return; // usuario canceló
            }

            File archivo = fileChooser.getSelectedFile();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(archivo));
            document.open();

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            document.add(new Paragraph("------ LICENCIA DE CONDUCIR ------"));
            document.add(new Paragraph("Número de Licencia: " + licencia.getNumero()));
            document.add(new Paragraph("Tipo de Licencia: " + licencia.getTipoLicencia()));
            document.add(new Paragraph("Fecha de Emisión: " + licencia.getFechaEmision().format(formato)));
            document.add(new Paragraph("Fecha de Vencimiento: " + licencia.getFechaVencimiento().format(formato)));
            document.add(new Paragraph("Nombre: " + ultimoTramite.getNombre()));
            document.add(new Paragraph("Cedula: " + ultimoTramite.getCedula()));
            document.add(new Paragraph("--- REPÚBLICA DEL ECUADOR ---"));

            document.close();

            JOptionPane.showMessageDialog(this,
                    "Licencia descargada correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar el PDF.");
        }

    }

    // ================= CERRAR SESIÓN =================
    private void cerrarSesion() {
        new Login().setVisible(true);
        dispose();
    }

}

