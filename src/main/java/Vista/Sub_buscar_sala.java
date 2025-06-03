package Vista;

import Control.AplicacionDAO;
import Control.Filtro_salas;
import Control.UbicacionDAO;
import Modelo.Sala;
import Modelo.Ubicacion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Sub_buscar_sala extends JPanel {
    private Estilo estilo = new Estilo();
    private Filtro_salas filtroSalas = new Filtro_salas();
    private UbicacionDAO ubicacionDAO = new UbicacionDAO();
    private AplicacionDAO aplicacionDAO = new AplicacionDAO();
    
    // Componentes de filtro
    private JSpinner spinnerMinComputadoras;
    private JComboBox<String> comboBloques;
    private JComboBox<String> comboAplicaciones;
    private JButton btnFiltrar;
    
    // Panel central para mostrar salas
    private JPanel panelSalasCentral;
    private JScrollPane scrollPanel;

    public Sub_buscar_sala() {
        setLayout(new BorderLayout());
        
        // Panel norte - Filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBackground(estilo.colorFondoPanel);
        
        // Título "Filtrar salas"
        panelFiltros.add(estilo.crearTextoBasico("Filtrar salas:", estilo.fuenteSubtitulo, estilo.colorTextoBoton));
        
        // Campo: Mínimo de computadores
        panelFiltros.add(estilo.crearTextoBasico("Mín computadores:", estilo.fuenteTexto, estilo.colorTextoBoton));
        spinnerMinComputadoras = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        estilo.aplicarEstiloCampo(spinnerMinComputadoras);
        panelFiltros.add(spinnerMinComputadoras);
        
        // Campo: Ubicación (bloque)
        panelFiltros.add(estilo.crearTextoBasico("Ubicación:", estilo.fuenteTexto, estilo.colorTextoBoton));
        comboBloques = new JComboBox<>();
        estilo.aplicarEstiloCampo(comboBloques);
        comboBloques.addItem(""); // Opción vacía (sin filtro)
        panelFiltros.add(comboBloques);
        
        // Campo: Aplicaciones
        panelFiltros.add(estilo.crearTextoBasico("Aplicaciones:", estilo.fuenteTexto, estilo.colorTextoBoton));
        comboAplicaciones = new JComboBox<>();
        estilo.aplicarEstiloCampo(comboAplicaciones);
        comboAplicaciones.addItem(""); // Opción vacía (sin filtro)
        panelFiltros.add(comboAplicaciones);
        
        // Botón Filtrar
        btnFiltrar = estilo.crearBotonSimple("Filtrar", estilo.fuenteTexto, 
                null, estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        panelFiltros.add(btnFiltrar);
        
        add(panelFiltros, BorderLayout.NORTH);
        
        // Panel central - Salas
        panelSalasCentral = estilo.crearPanelVertical();
        scrollPanel = new JScrollPane(panelSalasCentral);
        scrollPanel.setBackground(estilo.colorFondoPanel);
        add(scrollPanel, BorderLayout.CENTER);
        
        // Cargar datos iniciales
        cargarBloques();
        cargarAplicaciones();
        cargarSalas(null, new ArrayList<>(), 0); // Cargar todas las salas inicialmente
    }
    
    private void cargarBloques() {
        comboBloques.removeAllItems();
        comboBloques.addItem(""); // Opción vacía (sin filtro)
        List<String> bloques = ubicacionDAO.obtenerTodasUbicaciones().stream()
                .map(Ubicacion::getBloque)
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        bloques.forEach(comboBloques::addItem);
    }
    
    private void cargarAplicaciones() {
        comboAplicaciones.removeAllItems();
        comboAplicaciones.addItem(""); // Opción vacía (sin filtro)
        List<String> aplicaciones = aplicacionDAO.obtenerListaDeNombresAplicaciones();
        aplicaciones.forEach(comboAplicaciones::addItem);
    }
    
    private void aplicarFiltros() {
        // Recoger los valores de los filtros
        int minComputadoras = (Integer) spinnerMinComputadoras.getValue();
        String bloqueSeleccionado = (String) comboBloques.getSelectedItem();
        String aplicacionSeleccionada = (String) comboAplicaciones.getSelectedItem();
        
        // Preparar parámetros para el filtro
        Ubicacion ubicacion = null;
        if (bloqueSeleccionado != null && !bloqueSeleccionado.isEmpty()) {
            ubicacion = new Ubicacion(null, bloqueSeleccionado, 0); // Piso 0 indica que no se filtra por piso
        }
        
        List<String> aplicaciones = new ArrayList<>();
        if (aplicacionSeleccionada != null && !aplicacionSeleccionada.isEmpty()) {
            aplicaciones.add(aplicacionSeleccionada);
        }
        
        // Filtrar salas
        cargarSalas(ubicacion, aplicaciones, minComputadoras);
    }
    
    private void cargarSalas(Ubicacion ubicacion, List<String> aplicaciones, int minComputadoras) {
        panelSalasCentral.removeAll();
        
        // Obtener salas filtradas
        List<Sala> salas = filtroSalas.filtrarSalas(ubicacion, aplicaciones, minComputadoras);
        
        // Agrupar salas por bloque y piso
        java.util.Map<String, java.util.Map<Integer, List<Sala>>> salasPorBloqueYPiso = salas.stream()
            .filter(sala -> sala.getUbicacion() != null)
            .collect(java.util.stream.Collectors.groupingBy(
                sala -> sala.getUbicacion().getBloque(),
                java.util.stream.Collectors.groupingBy(sala -> sala.getUbicacion().getPiso())
            ));
        
        // Agregar también las salas sin ubicación
        List<Sala> salasSinUbicacion = salas.stream()
            .filter(sala -> sala.getUbicacion() == null)
            .collect(java.util.stream.Collectors.toList());
        
        // Mostrar salas organizadas por bloque y piso
        for (java.util.Map.Entry<String, java.util.Map<Integer, List<Sala>>> bloqueEntry : salasPorBloqueYPiso.entrySet()) {
            String bloque = bloqueEntry.getKey();
            java.util.Map<Integer, List<Sala>> salasPorPiso = bloqueEntry.getValue();
            
            // Título del bloque
            JLabel bloqueLabel = estilo.crearTextoBasico("Bloque: " + bloque, estilo.fuenteSubtitulo, estilo.colorTextoBoton);
            bloqueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelSalasCentral.add(bloqueLabel);
            panelSalasCentral.add(Box.createVerticalStrut(5));
            
            for (java.util.Map.Entry<Integer, List<Sala>> pisoEntry : salasPorPiso.entrySet()) {
                int piso = pisoEntry.getKey();
                List<Sala> salasDelPiso = pisoEntry.getValue();
                
                // Subtítulo del piso
                JLabel pisoLabel = estilo.crearTextoBasico("  Piso: " + piso, estilo.fuenteTexto, estilo.colorTextoBoton);
                pisoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelSalasCentral.add(pisoLabel);
                panelSalasCentral.add(Box.createVerticalStrut(5));
                
                // Panel para las salas de este piso
                JPanel salasPisoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
                salasPisoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                salasPisoPanel.setBackground(estilo.colorFondoPanel);
                
                for (Sala sala : salasDelPiso) {
                    JButton salaButton = estilo.crearBotonSimple(sala.getNombre(), estilo.fuenteTexto, 
                            null, estilo.colorFondoBoton_A1, estilo.colorTextoBoton);
                    salaButton.setToolTipText(crearTooltipSala(sala));
                    
                    // Agregar acción para seleccionar la sala
                    salaButton.addActionListener(e -> mostrarDetallesSala(sala));
                    
                    salasPisoPanel.add(salaButton);
                }
                
                panelSalasCentral.add(salasPisoPanel);
                panelSalasCentral.add(Box.createVerticalStrut(10));
            }
        }
        
        // Mostrar salas sin ubicación si las hay
        if (!salasSinUbicacion.isEmpty()) {
            JLabel sinUbicacionLabel = estilo.crearTextoBasico("Salas sin ubicación asignada", 
                    estilo.fuenteSubtitulo, estilo.colorTextoBoton);
            sinUbicacionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelSalasCentral.add(sinUbicacionLabel);
            panelSalasCentral.add(Box.createVerticalStrut(5));
            
            JPanel sinUbicacionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            sinUbicacionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            sinUbicacionPanel.setBackground(estilo.colorFondoPanel);
            
            for (Sala sala : salasSinUbicacion) {
                JButton salaButton = estilo.crearBotonSimple(sala.getNombre(), estilo.fuenteTexto, 
                        null, estilo.colorFondoBoton_A1, estilo.colorTextoBoton);
                salaButton.setToolTipText(crearTooltipSala(sala));
                
                // Agregar acción para seleccionar la sala
                salaButton.addActionListener(e -> mostrarDetallesSala(sala));
                
                sinUbicacionPanel.add(salaButton);
            }
            
            panelSalasCentral.add(sinUbicacionPanel);
        }
        
        // Mostrar mensaje si no hay salas
        if (salas.isEmpty()) {
            JLabel noResults = estilo.crearTextoBasico("No se encontraron salas con los criterios seleccionados", 
                    estilo.fuenteSubtitulo, estilo.colorTextoBoton);
            noResults.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelSalasCentral.add(noResults);
        }
        
        panelSalasCentral.revalidate();
        panelSalasCentral.repaint();
    }
    
    private String crearTooltipSala(Sala sala) {
        StringBuilder tooltip = new StringBuilder();
        tooltip.append("<html><b>").append(sala.getNombre()).append("</b><br>");
        tooltip.append("Capacidad: ").append(sala.getMaxComputadoras()).append(" equipos<br>");
        
        if (sala.getUbicacion() != null) {
            tooltip.append("Ubicación: Bloque ").append(sala.getUbicacion().getBloque())
                   .append(", Piso ").append(sala.getUbicacion().getPiso()).append("<br>");
        }
        
        List<Modelo.Aplicacion> aplicaciones = filtroSalas.getSalaDAO().obtenerAplicacionesDeSala(sala.getId());
        if (!aplicaciones.isEmpty()) {
            tooltip.append("Aplicaciones:<ul>");
            for (Modelo.Aplicacion app : aplicaciones) {
                tooltip.append("<li>").append(app.getNombre()).append("</li>");
            }
            tooltip.append("</ul>");
        }
        
        tooltip.append("</html>");
        return tooltip.toString();
    }
    
    private void mostrarDetallesSala(Sala sala) {
        // Crear un diálogo para mostrar los detalles de la sala
        JDialog dialog = new JDialog();
        dialog.setTitle("Detalles de Sala: " + sala.getNombre());
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Mostrar información básica
        contentPanel.add(estilo.crearTextoBasico("Nombre: " + sala.getNombre(), estilo.fuenteSubtitulo, estilo.colorTextoBoton));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(estilo.crearTextoBasico("Capacidad: " + sala.getMaxComputadoras() + " equipos", estilo.fuenteTexto, estilo.colorTextoBoton));
        
        // Mostrar ubicación si existe
        if (sala.getUbicacion() != null) {
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(estilo.crearTextoBasico("Ubicación:", estilo.fuenteSubtitulo, estilo.colorTextoBoton));
            contentPanel.add(estilo.crearTextoBasico("Bloque: " + sala.getUbicacion().getBloque(), estilo.fuenteTexto, estilo.colorTextoBoton));
            contentPanel.add(estilo.crearTextoBasico("Piso: " + sala.getUbicacion().getPiso(), estilo.fuenteTexto, estilo.colorTextoBoton));
        }
        
        // Mostrar aplicaciones si existen
        List<Modelo.Aplicacion> aplicaciones = filtroSalas.getSalaDAO().obtenerAplicacionesDeSala(sala.getId());
        if (!aplicaciones.isEmpty()) {
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(estilo.crearTextoBasico("Aplicaciones instaladas:", estilo.fuenteSubtitulo, estilo.colorTextoBoton));
            
            for (Modelo.Aplicacion app : aplicaciones) {
                contentPanel.add(estilo.crearTextoBasico("- " + app.getNombre(), estilo.fuenteTexto, estilo.colorTextoBoton));
            }
        }
        
        // Botón para cerrar
        JButton btnCerrar = estilo.crearBotonSimple("Cerrar", estilo.fuenteTexto, 
                null, estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
        btnCerrar.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnCerrar);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}