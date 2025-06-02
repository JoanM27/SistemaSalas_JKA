package Vista;
import Control.Solicitud_adminDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Control.UsuarioDAO;
import Modelo.Usuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sub_info_usuario extends JPanel {
    private Usuario usuario;
    private Estilo estilo;
    private UsuarioDAO usuarioDAO;
    private JFrame sistema;

    public Sub_info_usuario(Usuario usuario, JFrame sistema) {
        this.usuario = usuario;
        this.estilo = new Estilo();
        this.usuarioDAO = new UsuarioDAO();
        this.sistema =sistema;
        construirPanel();
    }

    private void construirPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(estilo.colorFondoPanel);

        this.add(estilo.crearTituloGrande("Información del Usuario"));
        this.add(Box.createVerticalStrut(20));

        mostrarCampoSoloLectura("Cédula:", usuario.getCedula());
        agregarCampoEditable("Correo institucional:", usuario.getCorreo(), "correo");
        agregarCampoEditable("Nombre:", usuario.getNombre(), "nombre");
        agregarCampoEditable("Apellido:", usuario.getApellido(), "apellido");
        agregarCampoEditable("Teléfono:", usuario.getTelefono(), "telefono");
        agregarBotonEditarClave();

        mostrarCampoSoloLectura("Tipo de usuario:", usuario.getTipoUsuario());
        if (usuario.getTipoUsuario().equalsIgnoreCase("administrador")) {
            mostrarCampoSoloLectura("Estado de solicitud:", obtenerEstadoSolicitud(usuario.getCedula()));
        }

        if (!usuario.getCedula().equals("0000")) {
            JButton eliminar = estilo.crearBoton(this, "Eliminar Usuario", estilo.fuenteBotonMenu, estilo.tamBotonMediano,
                                                 Color.RED, Color.WHITE, 10);
            eliminar.addActionListener(e -> {
                try {
                    confirmarYEliminarUsuario();
                } catch (SQLException ex) {
                    Logger.getLogger(Sub_info_usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private void mostrarCampoSoloLectura(String titulo, String valor) {
        JLabel label = estilo.crearTextoBasico(titulo + " " + valor, estilo.fuenteTexto, Color.BLACK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(label);
        this.add(Box.createVerticalStrut(10));
    }

    private void agregarCampoEditable(String titulo, String valorActual, String campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        JLabel label = estilo.crearTextoBasico(titulo + " " + valorActual, estilo.fuenteTexto, Color.BLACK);
        JButton editar = estilo.crearBotonSimple("Editar", estilo.fuenteTexto, new Dimension(100, 25),
                                                 estilo.colorFondoBoton_A2, estilo.colorTextoBoton);

        editar.addActionListener(e -> editarCampoUsuario(campo, titulo, valorActual));

        panel.add(Box.createHorizontalStrut(20));
        panel.add(label);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(editar);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(panel);
        this.add(Box.createVerticalStrut(10));
    }

    private void agregarBotonEditarClave() {
        JButton boton = estilo.crearBotonSimple("Editar Contraseña", estilo.fuenteTexto, estilo.tamBotonMediano,
                                                estilo.colorFondoBoton_A1, estilo.colorTextoBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> editarClaveUsuario());
        this.add(boton);
        this.add(Box.createVerticalStrut(10));
    }

    private void editarCampoUsuario(String campo, String titulo, String valorActual) {
        JTextField nuevoValor = new JTextField(valorActual);
        int resultado = JOptionPane.showConfirmDialog(this, nuevoValor, "Editar " + titulo, JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String valor = nuevoValor.getText().trim();
            if (!valor.isEmpty()) {
                switch (campo) {
                    case "correo": usuario.setCorreo(valor); break;
                    case "nombre": usuario.setNombre(valor); break;
                    case "apellido": usuario.setApellido(valor); break;
                    case "telefono": usuario.setTelefono(valor); break;
                }
                usuarioDAO.actualizarUsuario(usuario);
                recargarPanel();
            }
        }
    }

    private void editarClaveUsuario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JPasswordField actual = new JPasswordField();
        JPasswordField nueva = new JPasswordField();
        JPasswordField repetir = new JPasswordField();

        panel.add(new JLabel("Contraseña actual:"));
        panel.add(actual);
        panel.add(new JLabel("Nueva contraseña:"));
        panel.add(nueva);
        panel.add(new JLabel("Repetir nueva contraseña:"));
        panel.add(repetir);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Contraseña", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String passActual = new String(actual.getPassword());
            String passNueva = new String(nueva.getPassword());
            String passRepetida = new String(repetir.getPassword());

            if (!usuario.getPassword().equals(passActual)) {
                JOptionPane.showMessageDialog(this, "Contraseña actual incorrecta.");
            } else if (!passNueva.equals(passRepetida)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            } else {
                usuario.setPassword(passNueva);
                usuarioDAO.actualizarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Contraseña actualizada.");
            }
        }
    }

    private void confirmarYEliminarUsuario() throws SQLException {
        JPasswordField campo = new JPasswordField();
        int res = JOptionPane.showConfirmDialog(this, campo, "Confirmar contraseña para eliminar", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String clave = new String(campo.getPassword());
            if (!usuario.getPassword().equals(clave)) {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (usuarioDAO.eliminarUsuarioPorCedula(usuario.getCedula())) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                    this.removeAll();
                    this.revalidate();
                    this.repaint();
                    sistema.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el usuario.");
                }
            }
        }
    }

    private String obtenerEstadoSolicitud(String cedula) {
        
        Solicitud_adminDAO soli_admin_DAO = new Solicitud_adminDAO();
        return soli_admin_DAO.obtenerEstadoPorCedula(cedula); 
    }

    private void recargarPanel() {
        this.removeAll();
        construirPanel();
        this.revalidate();
        this.repaint();
    }
}