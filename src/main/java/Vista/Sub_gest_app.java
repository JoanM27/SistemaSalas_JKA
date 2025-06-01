package Vista;

import Control.AplicacionDAO;
import Modelo.Aplicacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Sub_gest_app extends JPanel {
    private JPanel panelPrincipal;
    private JPanel panelAplicaciones;
    private JPanel panelContenedor;
    private JButton btnAgregarAplicacion;
    private AplicacionDAO aplicacionDAO;
    private Estilo estilo = new Estilo();

    public Sub_gest_app() {
        setLayout(new BorderLayout());

        aplicacionDAO = new AplicacionDAO();

        panelAplicaciones = estilo.crearPanelVertical();
        panelContenedor = estilo.crearPanelVertical();

        JScrollPane scroll = estilo.convertirPanelDesplazableVertical(panelAplicaciones);
        panelPrincipal = estilo.crearPanelVerticalDividido_scroll(panelContenedor, scroll);

        btnAgregarAplicacion = estilo.crearBotonSimple("Agregar Aplicación", estilo.fuenteSubtitulo, null, estilo.colorFondoBoton_A1, estilo.colorTextoBoton);
        btnAgregarAplicacion.addActionListener(e -> mostrarVentanaAgregarAplicacion());
        panelContenedor.add(btnAgregarAplicacion);

        setBackground(estilo.colorFondoPanel);
        add(panelPrincipal, BorderLayout.CENTER);

        cargarAplicaciones();
        setVisible(true);
    }

    private void cargarAplicaciones() {
        panelAplicaciones.removeAll();
        List<Aplicacion> lista = aplicacionDAO.obtenerTodasAplicaciones();

        for (Aplicacion app : lista) {
            JButton btnApp = estilo.crearBotonSimple("> "+app.getNombre(), estilo.fuenteTexto, null, estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
            btnApp.setAlignmentX(Component.LEFT_ALIGNMENT);
            btnApp.setToolTipText(app.getDescripcion());
            btnApp.addActionListener(e -> mostrarVentanaEditarAplicacion(app));
            panelAplicaciones.add(btnApp);
        }

        panelAplicaciones.revalidate();
        panelAplicaciones.repaint();
    }

    private void mostrarVentanaAgregarAplicacion() {
        JTextField txtNombre = new JTextField();
        JTextArea txtDescripcion = new JTextArea(5, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        Object[] campos = {
            "Nombre:", txtNombre,
            "Descripción:", scrollDescripcion
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Agregar Aplicación", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Aplicacion nueva = new Aplicacion(null, nombre, descripcion);
            if (aplicacionDAO.crearAplicacion(nueva)) {
                JOptionPane.showMessageDialog(this, "Aplicación agregada con éxito.");
                cargarAplicaciones();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la aplicación.");
            }
        }
    }

    private void mostrarVentanaEditarAplicacion(Aplicacion aplicacion) {
        JTextField txtNombre = new JTextField(aplicacion.getNombre());
        JTextArea txtDescripcion = new JTextArea(aplicacion.getDescripcion(), 5, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        Object[] campos = {
            "Nombre:", txtNombre,
            "Descripción:", scrollDescripcion
        };

        Object[] opciones = {"Guardar Cambios", "Eliminar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(
            this,
            campos,
            "Editar Aplicación",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (opcion == 0) { // Guardar
            aplicacion.setNombre(txtNombre.getText().trim());
            aplicacion.setDescripcion(txtDescripcion.getText().trim());

            if (aplicacionDAO.modificarAplicacion(aplicacion)) {
                JOptionPane.showMessageDialog(this, "Aplicación actualizada.");
                cargarAplicaciones();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la aplicación.");
            }

        } else if (opcion == 1) { // Eliminar
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar esta aplicación?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (aplicacionDAO.eliminarAplicacionPorId(aplicacion.getId())) {
                    JOptionPane.showMessageDialog(this, "Aplicación eliminada.");
                    cargarAplicaciones();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}