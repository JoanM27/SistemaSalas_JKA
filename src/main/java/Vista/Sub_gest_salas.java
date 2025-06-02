package Vista;

import Control.SalaDAO;
import Control.UbicacionDAO;
import Modelo.Sala;
import Modelo.Ubicacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Sub_gest_salas extends JPanel {
    private JComboBox<String> comboBloques;
    private JComboBox<Integer> comboPisos;
    private JPanel panelSalas;
    private SalaDAO salaDAO;
    private UbicacionDAO ubicacionDAO;
    private Estilo estilo =new Estilo();

    public Sub_gest_salas() {
        salaDAO = new SalaDAO();
        ubicacionDAO = new UbicacionDAO();
        setLayout(new BorderLayout());

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ///Campos de seleccion
        comboBloques = estilo.crearCampoSeleccionSimple_str(estilo.colorFondoPanel, estilo.fuenteTexto, null);
        comboPisos = estilo.crearCampoSeleccionSimple_int(estilo.colorFondoPanel, estilo.fuenteTexto, null);

        panelFiltros.add(estilo.crearTextoBasico("Bloque:", estilo.fuenteSubtitulo, estilo.colorTextoBoton));
        panelFiltros.add(comboBloques);
        panelFiltros.add(estilo.crearTextoBasico("Piso:", estilo.fuenteSubtitulo, estilo.colorTextoBoton));
        panelFiltros.add(comboPisos);
        add(panelFiltros, BorderLayout.NORTH);

        // Panel de contenido principal
        panelSalas = estilo.crearPanelVertical();
        
        JScrollPane scrollPane = new JScrollPane(panelSalas);
        scrollPane.setBackground(estilo.colorFondoPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(new PanelAgregarSalas(),BorderLayout.EAST);
        // Eventos
        comboBloques.addActionListener(e -> cargarPisos());
        comboPisos.addActionListener(e -> cargarSalas());

        // Carga inicial
        cargarBloques();
    }

    private void cargarBloques() {
        comboBloques.removeAllItems();
        List<String> bloques = ubicacionDAO.obtenerTodasUbicaciones().stream()
            .map(Ubicacion::getBloque)
            .distinct()
            .collect(Collectors.toList());

        bloques.forEach(comboBloques::addItem);

        if (!bloques.isEmpty()) {
            cargarPisos();
        }
    }

    private void cargarPisos() {
        comboPisos.removeAllItems();
        String bloque = (String) comboBloques.getSelectedItem();
        if (bloque == null) return;

        ubicacionDAO.obtenerTodasUbicaciones().stream()
            .filter(u -> u.getBloque().equals(bloque))
            .map(Ubicacion::getPiso)
            .distinct()
            .sorted()
            .forEach(comboPisos::addItem);

        if (comboPisos.getItemCount() > 0) {
            cargarSalas();
        }
    }

    private void cargarSalas() {
        panelSalas.removeAll();

        String bloque = (String) comboBloques.getSelectedItem();
        Integer piso = (Integer) comboPisos.getSelectedItem();
        if (bloque == null || piso == null) return;

        Ubicacion ubicacion = ubicacionDAO.obtenerUbicacionPorBloqueYPiso(bloque, piso);
        if (ubicacion == null) return;

        List<Sala> salas = salaDAO.obtenerSalasPorUbicacionId(ubicacion.getId());

        for (Sala sala : salas) {
            JPanel panelItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelItem.add(estilo.crearTextoBasico("Sala: " + sala.getNombre(), estilo.fuenteSubtitulo, estilo.colorTextoBoton));

            JButton btnEditar = estilo.crearBotonSimple("Editar", estilo.fuenteTexto,null,estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
            btnEditar.addActionListener(e -> editarSala(sala));
            
            JButton btnEliminar = estilo.crearBotonSimple("Eliminar", estilo.fuenteTexto,null,estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
            btnEliminar.addActionListener(e -> eliminarSala(sala));
            
            panelItem.add(btnEditar);
            panelItem.add(btnEliminar);
            panelSalas.add(panelItem);
        }

        panelSalas.revalidate();
        panelSalas.repaint();
    }

    private void eliminarSala(Sala sala) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "¿Eliminar sala " + sala.getNombre() + "?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION && salaDAO.eliminarSalaPorId(sala.getId())) {
            cargarSalas();
            JOptionPane.showMessageDialog(this, "Sala eliminada.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar sala.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarSala(Sala sala) {
        String nuevoNombre = JOptionPane.showInputDialog(
            this, 
            "Nuevo nombre de la sala:", 
            sala.getNombre()
        );
        
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            sala.setNombre(nuevoNombre.trim());
            if (salaDAO.modificarSala(sala)) {
                cargarSalas();
                JOptionPane.showMessageDialog(this, "Sala actualizada.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar sala.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}