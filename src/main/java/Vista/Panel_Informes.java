
package Vista;

import Control.InformesDAO;
import Modelo.ReportesEstadisticaSala;
import Modelo.Sala;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Panel_Informes extends JPanel {

    private JPanel panelPrincipal; 
    private JPanel panelEstadisticas;
    private JPanel panelContenedor;
    private Estilo estilo = new Estilo();
    private InformesDAO reporteDAO;

    public Panel_Informes() {
        setLayout(new BorderLayout());
        reporteDAO = new InformesDAO();

        panelEstadisticas = estilo.crearPanelVertical();
        panelContenedor = estilo.crearPanelVertical();

        JScrollPane scroll = estilo.convertirPanelDesplazableVertical(panelEstadisticas);
        panelPrincipal = estilo.crearPanelVerticalDividido_scroll(panelContenedor, scroll);

        panelPrincipal.setBackground(estilo.colorFondoPanel);
        add(panelPrincipal, BorderLayout.CENTER);

        cargarEstadisticas();
        setVisible(true);
    }

    private void cargarEstadisticas() {
        estilo.limpiarPanel(panelEstadisticas);

        int totalReservas = reporteDAO.obtenerCantidadTotalReservas();
        List<ReportesEstadisticaSala> estadisticas = reporteDAO.obtenerReservasPorSala();
        List<Sala> estadoSalas = reporteDAO.obtenerEstadoSalas();

        // Sección 1: Total de Reservas
        JLabel lblTotal = estilo.crearTextoBasico("Total de Reservas: " + totalReservas, estilo.fuenteSubtitulo, estilo.colorTextoBoton);
        panelEstadisticas.add(lblTotal);
        panelEstadisticas.add(Box.createVerticalStrut(15));

        // Sección 2: Estadísticas por sala
        panelEstadisticas.add(estilo.crearTextoBasico("Reservas por Sala:", estilo.fuenteSubtitulo, estilo.colorTextoBoton));
        for (ReportesEstadisticaSala r : estadisticas) {
            String info = "Sala: " + r.getNombreSala() + " | Reservas: " + r.getCantidadReservas();
            panelEstadisticas.add(estilo.crearTextoBasico("  -"+info, estilo.fuenteTexto, estilo.colorTextoBoton));
     
        }
        panelEstadisticas.add(Box.createVerticalStrut(15));

        // Sección 3: Estado actual de salas
        
    }
}


