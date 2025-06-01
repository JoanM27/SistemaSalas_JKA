package Vista;

import Control.SalaDAO;
import Modelo.Sala;
import Modelo.Ubicacion;
import Modelo.Aplicacion;
import Control.AplicacionDAO;
import Control.UbicacionDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class PanelAgregarSalas extends JPanel{
    private JComboBox<Object> cbUbicaciones;
    private Ubicacion   select_ubicacion;
    private JTextField tfNombre;
    private JTextArea taNormas;
    private JSpinner spMaxEquipos;
    private JPanel panelAppsSeleccionadas;
    private JButton btnAgregarApp;
    private JButton btnGuardar;
    private SalaDAO salaDAO = new SalaDAO();
    private AplicacionDAO appDAO = new AplicacionDAO();
    private UbicacionDAO ubicacionDAO = new UbicacionDAO();
    private List<Aplicacion> aplicacionesSeleccionadas = new ArrayList<>();
    private Estilo estilo = new Estilo();

    public PanelAgregarSalas() {
        //disposicion
        setLayout(new BorderLayout());
        //panel de contenido
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(estilo.colorFondoPanel);
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //componentes
        ///Campo nombre
        JLabel lblNombre = new JLabel("Nombre Sala:");
        lblNombre.setForeground(estilo.colorTextoBoton);
        lblNombre.setFont(estilo.fuenteSubtitulo);
        tfNombre = new JTextField(20);
        tfNombre.setForeground(estilo.colorTextoBoton);
        tfNombre.setFont(estilo.fuenteTexto);
        //Selector de ubicaciones
        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setForeground(estilo.colorTextoBoton);
        lblUbicacion.setFont(estilo.fuenteSubtitulo);
        cbUbicaciones = new JComboBox<>();
        cbUbicaciones.setForeground(estilo.colorTextoBoton);
        cbUbicaciones.setFont(estilo.fuenteTexto);
        cargarUbicaciones();
        //Campo Maximo de equipos
        JLabel lblMaxEquipos = new JLabel("Máximo Equipos:");
        lblMaxEquipos.setForeground(estilo.colorTextoBoton);
        lblMaxEquipos.setFont(estilo.fuenteSubtitulo);
        spMaxEquipos = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        spMaxEquipos.setForeground(estilo.colorTextoBoton);
        spMaxEquipos.setFont(estilo.fuenteTexto);
        //Campo de Normas
        JLabel lblNormas = new JLabel("Normas:");
        lblNormas.setForeground(estilo.colorTextoBoton);
        lblNormas.setFont(estilo.fuenteSubtitulo);
        taNormas = new JTextArea(4, 20);
        JScrollPane scrollNormas = new JScrollPane(taNormas);
        scrollNormas.setForeground(estilo.colorTextoBoton);
        scrollNormas.setFont(estilo.fuenteTexto);
        //campo de aplicaciones
        JLabel lblApps = new JLabel("Aplicaciones:");
        lblApps.setForeground(estilo.colorTextoBoton);
        lblApps.setFont(estilo.fuenteSubtitulo);
        ///apps seleccionadas
        panelAppsSeleccionadas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAppsSeleccionadas.setBackground(estilo.colorFondoBoton_A3);
        panelAppsSeleccionadas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnAgregarApp = estilo.crearBotonSimple("Agregar Aplicacion", estilo.fuenteTexto,null,estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
        btnAgregarApp.addActionListener(e -> mostrarSelectorAplicaciones());

        btnGuardar = estilo.crearBotonSimple("Guardar Sala", estilo.fuenteTexto,null,estilo.colorFondoBoton_A2, estilo.colorTextoBoton);
        btnGuardar.addActionListener(e -> guardarSala());

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(lblNombre, gbc);
        gbc.gridx = 1; panelForm.add(tfNombre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(lblUbicacion, gbc);
        gbc.gridx = 1; panelForm.add(cbUbicaciones, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(lblMaxEquipos, gbc);
        gbc.gridx = 1; panelForm.add(spMaxEquipos, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(lblNormas, gbc);
        gbc.gridx = 1; panelForm.add(scrollNormas, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(lblApps, gbc);
        gbc.gridx = 1; panelForm.add(panelAppsSeleccionadas, gbc);

        gbc.gridx = 1; gbc.gridy++;
        panelForm.add(btnAgregarApp, gbc);

        gbc.gridx = 1; gbc.gridy++;
        panelForm.add(btnGuardar, gbc);
        add(panelForm, BorderLayout.CENTER);
    }

    private void cargarUbicaciones() {
        List<Ubicacion> ubicaciones = ubicacionDAO.obtenerTodasUbicaciones();
        for (Ubicacion u : ubicaciones) {
            cbUbicaciones.addItem(u.getBloque()+" piso "+Integer.toString (u.getPiso()));
            cbUbicaciones.addItemListener(e -> {
                select_ubicacion= u;
            });
        }
    }

    private void mostrarSelectorAplicaciones() {
        List<String> disponibles = appDAO.obtenerListaDeNombresAplicaciones();
        
        Aplicacion nueva = new Aplicacion("Nueva...",null,null);
        disponibles.add(nueva.getNombre());
        
        String seleccion = (String)JOptionPane.showInputDialog(
            this,
            "Selecciona una aplicación:",
            "Agregar Aplicación",
            JOptionPane.PLAIN_MESSAGE,
            null,
            disponibles.toArray(),
            disponibles.get(0))
                ;
        Aplicacion appSeleccion =appDAO.obtenerAplicacionPorNombre(seleccion);
        System.out.println("Aplicacion"+"\n"+"Id: "+appSeleccion.getId()+"\n"+"Nombre: "+appSeleccion.getNombre());
        if (seleccion != null) {
            
            if ("Nueva...".equals(seleccion)) {
                String nombreNueva = JOptionPane.showInputDialog(this, "Nombre de nueva aplicación:");
                if (nombreNueva != null && !nombreNueva.trim().isEmpty()) {
                    Aplicacion nuevaApp = new Aplicacion(null, nombreNueva, "");
                    if (appDAO.crearAplicacion(nuevaApp)) {
                        aplicacionesSeleccionadas.add(nuevaApp);
                        agregarChipApp(nuevaApp);
                        
                    }
                }
            } else if (!aplicacionesSeleccionadas.contains(appSeleccion)) {
                aplicacionesSeleccionadas.add(appSeleccion);
                
                agregarChipApp(appSeleccion);
            }
        }
    }

    private void agregarChipApp(Aplicacion app) {
        JButton chip = estilo.crearBotonSimple(app.getNombre(),estilo.fuenteTextoPequeño, null, estilo.colorFondoBoton_A1,estilo.colorTextoBoton);
        chip.addActionListener(e -> {
            aplicacionesSeleccionadas.remove(app);
            panelAppsSeleccionadas.remove(chip);
            panelAppsSeleccionadas.revalidate();
            panelAppsSeleccionadas.repaint();
        });
        panelAppsSeleccionadas.add(chip);
        panelAppsSeleccionadas.revalidate();
        panelAppsSeleccionadas.repaint();
    }

    private void guardarSala() {
        String nombre = tfNombre.getText();
        int maxEquipos = (int) spMaxEquipos.getValue();
        String normas = taNormas.getText();
        Ubicacion ubicacion = select_ubicacion;

        Sala nuevaSala = new Sala(null, nombre, maxEquipos, normas, ubicacion);

        if (salaDAO.crearSala(nuevaSala)) {
            for (Aplicacion app : aplicacionesSeleccionadas) {
                salaDAO.agregarAplicacionASala(nuevaSala.getId(), app.getId());
            }
            JOptionPane.showMessageDialog(this, "Sala creada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar sala.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
