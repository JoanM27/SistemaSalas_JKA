package Vista;

import Control.UsuarioDAO;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import Modelo.Solicitud_admin;

public class Ventana_registro extends JFrame {
    // Campos
    private JTextField txtCedula, txtNombre, txtApellido, txtCorreo, txtTelefono;
    private JPasswordField txtPassword, txtPasswordCheck;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar, btnCancelar;
    private Estilo estilo = new Estilo();
    public Ventana_registro() {
        //Configuracion de Jframe
        setTitle("Registro de Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ///Icono de Pestaña
        URL iconoUrl = getClass().getResource("/imagenes/icono.png");
        if (iconoUrl != null) setIconImage(new ImageIcon(iconoUrl).getImage());
        //Diseñar Panel
        JPanel panel = estilo.crearPanelVertical();
        //Crear titulo
        JLabel titulo = estilo.crearTituloGrande("Registro de Usuario");
        ///Ajustar Titulo
        panel.add(Box.createVerticalStrut(30));
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(40));
        ///Agregar Campos
        txtCedula = estilo.crearCampo(panel, "Cédula:", estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo, estilo.margenInterno,10);
        txtNombre = estilo.crearCampo(panel, "Nombre:", estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo, estilo.margenInterno,10);
        txtApellido = estilo.crearCampo(panel, "Apellido:", estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo, estilo.margenInterno,10);
        txtCorreo = estilo.crearCampo(panel, "Correo Institucional:", estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo, estilo.margenInterno,10);
        txtTelefono = estilo.crearCampo(panel, "Teléfono:", estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo, estilo.margenInterno,10);
        txtPassword = estilo.crearCampoPassword(panel, "Crear Contraseña:", estilo.fuenteSubtitulo, estilo.tamCampo, estilo.margenInterno,10);
        txtPasswordCheck = estilo.crearCampoPassword(panel, "Confirmar Contraseña:", estilo.fuenteSubtitulo, estilo.tamCampo, estilo.margenInterno,10);

        comboRol = estilo.crearCampoSeleccion(panel, "Rol de Usuario:", new String[]{"Seleccionar rol", "Profesor", "Administrador"}, estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo,10);
        ///Agregar Botones
        btnRegistrar = estilo.crearBoton(panel, "Registrar", estilo.fuenteBoton, estilo.tamBotonMediano, estilo.colorFondoBoton, estilo.colorTextoBoton,20);
        btnRegistrar.addActionListener(this::registrarUsuario);

        btnCancelar = estilo.crearBoton(panel, "Cancelar", estilo.fuenteBoton, estilo.tamBotonMediano, estilo.colorFondoBoton, estilo.colorTextoBoton,20);
        btnCancelar.addActionListener(e -> dispose());
        
        //Agregar
        
        add(panel);
        setVisible(true);
    }

   
    private void registrarUsuario(ActionEvent e) {
        String cedula = txtCedula.getText().trim();//trim elimina los espacios al principio y al final del texto
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String password = new String(txtPassword.getPassword());
        String passwordCheck = new String(txtPasswordCheck.getPassword());
        String tipoUsuario = (String) comboRol.getSelectedItem();

        StringBuilder errores = new StringBuilder();

        if (cedula.isEmpty()) errores.append("> Cédula requerida.\n");
        if (nombre.isEmpty()) errores.append("> Nombre requerido.\n");
        if (apellido.isEmpty()) errores.append("> Apellido requerido.\n");
        if (correo.isEmpty()) errores.append("> Correo requerido.\n");
        if (telefono.isEmpty()) errores.append("> Teléfono requerido.\n");
        if (password.isEmpty()) errores.append("> Contraseña requerida.\n");
        if (!password.equals(passwordCheck)) errores.append("> Las contraseñas no coinciden.\n");
        if (tipoUsuario == null || tipoUsuario.equals("Seleccionar rol"))
            errores.append("> Selecciona un rol válido.\n");

        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(this, errores.toString(), "Errores de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setCedula(cedula);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setPassword(password);
        usuario.setTipoUsuario(tipoUsuario.toLowerCase());

        UsuarioDAO dao = new UsuarioDAO();
        if("profesor".equals(usuario.getTipoUsuario())){
            if(dao.registrarUsuario(usuario)){
              JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
               dispose();
            }else{
              JOptionPane.showMessageDialog(this, "Usuario ya existe", "Error de registro", JOptionPane.OK_OPTION);
            }  
        }else if("administrador".equals(usuario.getTipoUsuario())){
            Solicitud_admin solicitud_admin = new Solicitud_admin();
            solicitud_admin.setEstado("en espera");
            solicitud_admin.setUsuario(usuario);

            if (dao.registrarUsuarioYCrearSolicitud(usuario, solicitud_admin)) {
                JOptionPane.showMessageDialog(this, "Usuario y solicitud registrados exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error en el registro de usuario y/o solicitud", "Error", JOptionPane.ERROR_MESSAGE);
    }  
        }else{
            JOptionPane.showMessageDialog(this, "Error interno", "Error de registro", JOptionPane.OK_OPTION);
        }
    }
}
