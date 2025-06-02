package Vista;

import Control.Solicitud_adminDAO;
import Modelo.Solicitud_admin;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Sub_soli_admin extends JPanel {
    private JPanel panelPrincipal;
    private JPanel panelSolicitudes;
    private JPanel panelContenedor;
    private JButton btnActualizar;
    private Solicitud_adminDAO solicitudDAO;
    private Estilo estilo = new Estilo();

    public Sub_soli_admin() {
        setLayout(new BorderLayout());

        solicitudDAO = new Solicitud_adminDAO();

        panelSolicitudes = estilo.crearPanelVertical();
        panelContenedor = estilo.crearPanelVertical();

        JScrollPane scroll = estilo.convertirPanelDesplazableVertical(panelSolicitudes);
        panelPrincipal = estilo.crearPanelVerticalDividido_scroll(panelContenedor, scroll);

        btnActualizar = estilo.crearBotonSimple("Actualizar Solicitudes", estilo.fuenteSubtitulo, null, estilo.colorFondoBoton_A1, estilo.colorTextoBoton);
        btnActualizar.addActionListener(e -> cargarSolicitudes());

        panelContenedor.add(btnActualizar);

        setBackground(estilo.colorFondoPanel);
        add(panelPrincipal, BorderLayout.CENTER);

        cargarSolicitudes();
        setVisible(true);
    }

    private void cargarSolicitudes() {
        panelSolicitudes.removeAll();
        List<Solicitud_admin> lista = solicitudDAO.lista_solicitudes();

        for (Solicitud_admin soli : lista) {
            Usuario usuario = soli.getUsuario();
            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
            String cedula = usuario.getCedula();
            String textoBtn = "> " + nombreCompleto + " (" + cedula + ")";

            JButton btnSolicitud = estilo.crearBotonSimple(textoBtn, estilo.fuenteTexto, null, estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
            btnSolicitud.setAlignmentX(Component.LEFT_ALIGNMENT);
            btnSolicitud.setToolTipText("Estado: " + soli.getEstado());

            btnSolicitud.addActionListener(e -> mostrarVentanaGestionSolicitud(soli));

            panelSolicitudes.add(btnSolicitud);
        }

        panelSolicitudes.revalidate();
        panelSolicitudes.repaint();
    }

    private void mostrarVentanaGestionSolicitud(Solicitud_admin solicitud) {
    Usuario usuario = solicitud.getUsuario();

    Object[] campos = {
        "Nombre:", usuario.getNombre() + " " + usuario.getApellido(),
        "Cédula:", usuario.getCedula(),
        "Correo:", usuario.getCorreo(),
        "Estado actual:", solicitud.getEstado()
    };

    Object[] opciones = {"Aceptar", "Denegar", "Cancelar"};
    int opcion = JOptionPane.showOptionDialog(
        this,
        campos,
        "Gestionar Solicitud",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        opciones,
        opciones[0]
    );

    if (opcion == 0) { // Aceptar
        if (solicitudDAO.aceptarSolicitud(solicitud.getId())) {
            JOptionPane.showMessageDialog(this, "Solicitud aceptada.");
            cargarSolicitudes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al aceptar la solicitud.");
        }
    } else if (opcion == 1) { // Denegar
        if (solicitudDAO.denegarSolicitud(solicitud.getId())) {
            JOptionPane.showMessageDialog(this, "Solicitud denegada.");
            cargarSolicitudes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al denegar la solicitud.");
        }
    }
}
}
