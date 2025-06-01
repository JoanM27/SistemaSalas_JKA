package Vista;

import Control.UsuarioDAO;
import Modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Ventana_ingreso extends JFrame {

    private JTextField txtCedula;
    private JPasswordField txtPassword;
    private Estilo estilo = new Estilo();

    public Ventana_ingreso() {
        setTitle("Inicio de Sesión");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Icono de la ventana
        URL url = getClass().getResource("/imagenes/icono.png");
        if (url != null) {
            setIconImage(new ImageIcon(url).getImage());
        } else {
            System.err.println("No se encontró el icono.");
        }
        ///Crear panel
        JPanel panel = estilo.crearPanelVertical();
        ///Agregar titulo
        JLabel titulo = estilo.crearTituloGrande("Inicio de Sesión");
        ///Ajustar titulo
        panel.add(Box.createVerticalStrut(80));
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(40));
        // Icono Usuario
        estilo.agregar_imagen_dimensionada(panel,"/imagenes/Icon_user.png",150,150);
        //Agrega separacion
        panel.add(Box.createVerticalStrut(40));
        // Campos de entrada
        txtCedula = estilo.crearCampo(panel, "Ingrese Cédula:", estilo.fuenteSubtitulo, estilo.fuenteTexto, estilo.tamCampo, estilo.margenInterno,10);
        txtPassword = estilo.crearCampoPassword(panel, "Ingrese Contraseña:", estilo.fuenteSubtitulo, estilo.tamCampo, estilo.margenInterno,10);
        //Agrega separacion
        panel.add(Box.createVerticalStrut(30));
        // Botones
        JButton btnIngresar = estilo.crearBotonSimple("Iniciar Sesión", estilo.fuenteBoton, estilo.tamBotonMediano, estilo.colorFondoBoton, estilo.colorTextoBoton);
        JButton btnCancelar = estilo.crearBotonSimple("Cancelar", estilo.fuenteBoton, estilo.tamBotonMediano, estilo.colorFondoBoton, estilo.colorTextoBoton);

        btnIngresar.addActionListener(this::comprobarUsuario);
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnIngresar);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnCancelar);

        add(panel);
        setVisible(true);
    }

    private void comprobarUsuario(ActionEvent e) {
        String cedula = txtCedula.getText().trim();
        String password = new String(txtPassword.getPassword());

        StringBuilder errores = new StringBuilder();

        if (cedula.isEmpty()) errores.append("> Cédula requerida.\n");
        if (password.isEmpty()) errores.append("> Contraseña requerida.\n");

        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(this, errores.toString(), "Errores de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.autenticar(cedula, password);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "> Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Ingreso exitoso");
            Ventana_usuario ventanaUsuario = new Ventana_usuario(usuario);
            ventanaUsuario.setVisible(true);
            setVisible(false);
            ventanaUsuario.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setVisible(true);
                }
            });
        }
    }
}

