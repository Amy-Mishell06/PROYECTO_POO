import model.SolicitanteLogin;
import model.Usuario;
import service.SolicitanteLoginService;
import service.UsuarioService;

import javax.swing.*;

public class GestionUsuariosForm extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JTextField txtNombreUsuario;
    private JTextField txtContraUsuario;
    private JRadioButton ACTIVARRadioButton;
    private JRadioButton DESACTIVARRadioButton;
    private JButton BUSCARButton;
    private JButton ACTUALIZARButton;
    private JButton LIMPIARButton;
    private JButton REGRESARButton;

    private Usuario usuarioAdmin;
    private Usuario usuarioActual = null;
    private boolean esSolicitante = false;

    public GestionUsuariosForm(Usuario usuario) {
        this.usuarioAdmin = usuario;

        setTitle("Gestión de Usuarios");
        setContentPane(panelPrincipal);
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(ACTIVARRadioButton);
        grupo.add(DESACTIVARRadioButton);

        // ===== EVENTOS =====
        BUSCARButton.addActionListener(e -> buscarUsuario());
        ACTUALIZARButton.addActionListener(e -> actualizarUsuario());
        LIMPIARButton.addActionListener(e -> limpiarCampos());
        REGRESARButton.addActionListener(e -> regresarMenu());
    }

    // ================= BUSCAR USUARIO =================
    private void buscarUsuario() {
        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese username o cédula");
            return;
        }

        UsuarioService usuarioService = new UsuarioService();
        usuarioActual = usuarioService.buscarUsuario(texto); // busca admin/analista

        esSolicitante = false; // reset bandera

        if (usuarioActual == null) {
            // si no encuentra en usuarios, buscamos en solicitantes
            SolicitanteLoginService loginService = new SolicitanteLoginService();
            SolicitanteLogin login = loginService.loginPorCedula(texto);

            if (login != null) {
                // Creamos un objeto Usuario temporal para mostrar en el formulario
                usuarioActual = new Usuario();
                usuarioActual.setCedula(login.getUsername()); // username == cédula
                usuarioActual.setNombre(login.getUsername()); // para mostrar
                usuarioActual.setRol("SOLICITANTE");
                usuarioActual.setEstado(login.getEstado());

                txtContraUsuario.setText(login.getPassword()); // mostramos la contraseña actual
                esSolicitante = true; // es un solicitante
            }
        } else {
            // usuario admin/analista
            txtContraUsuario.setText(""); // no mostramos contraseña
        }

        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
            limpiarCampos();
            return;
        }

        // cargamos datos en formulario
        txtNombreUsuario.setText(usuarioActual.getNombre());
        if ("ACTIVO".equalsIgnoreCase(usuarioActual.getEstado())) {
            ACTIVARRadioButton.setSelected(true);
        } else {
            DESACTIVARRadioButton.setSelected(true);
        }

        JOptionPane.showMessageDialog(this, "Usuario cargado correctamente");
    }

    // ================= ACTUALIZAR USUARIO =================
    private void actualizarUsuario() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Primero busque un usuario");
            return;
        }

        String nombre = txtNombreUsuario.getText().trim();
        String contraseña = txtContraUsuario.getText().trim();
        String estado = ACTIVARRadioButton.isSelected() ? "ACTIVO" : "DESHABILITADO";

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
            return;
        }

        if (esSolicitante) {
            // Actualizar solicitante_login
            SolicitanteLoginService loginService = new SolicitanteLoginService();
            SolicitanteLogin login = loginService.loginPorCedula(usuarioActual.getCedula());

            if (login != null) {
                login.setEstado(estado);
                if (!contraseña.isEmpty()) {
                    login.setPassword(contraseña); // actualizar contraseña solo si el campo no está vacío
                }

                loginService.actualizarLoginSolicitante(login);
                JOptionPane.showMessageDialog(this, "Solicitante actualizado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: solicitante no encontrado para actualizar");
            }

        } else {
            // Actualizar admin/analista
            UsuarioService service = new UsuarioService();
            boolean ok = service.actualizarUsuario(usuarioActual.getId(), nombre, estado);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario");
            }
        }
    }

    // ================= LIMPIAR =================
    private void limpiarCampos() {
        txtBuscar.setText("");
        txtNombreUsuario.setText("");
        txtContraUsuario.setText("");
        ACTIVARRadioButton.setSelected(false);
        DESACTIVARRadioButton.setSelected(false);
        usuarioActual = null;
        esSolicitante = false;
    }

    // ================= REGRESAR =================
    private void regresarMenu() {
        new AdministradorMenuForm(usuarioAdmin).setVisible(true);
        dispose();
    }
}





