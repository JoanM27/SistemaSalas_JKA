package Vista;

import Modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import Control.Solicitud_adminDAO;

public class Ventana_usuario extends JFrame {

    private JPanel panelPrincipal;
    private Estilo estilo = new Estilo(); // <--- Instancia de estilo

    public Ventana_usuario(Usuario usuario) {
        ///Solo funcional para admin
        Solicitud_adminDAO solicitud_adminDAO = new Solicitud_adminDAO();
        String estado_solicitud = solicitud_adminDAO.obtenerEstadoPorCedula(usuario.getCedula());
        //Configuracion de la ventana
        setTitle("Sistema de Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //icono de la ventana
        URL url = getClass().getResource("/imagenes/icono.png");
        if (url != null) {
            setIconImage(new ImageIcon(url).getImage());
        }

        // Panel lateral estilizado
        JPanel menuLateral = new JPanel();
        estilo.aplicarEstiloMenuLateral(menuLateral);

        // Botones
        JButton btnInfoUsuario = estilo.crearBotonMenu("Información de usuario");
        JButton btnBuscarSalas = estilo.crearBotonMenu("Buscar Sala");
        JButton btnMisReservas = estilo.crearBotonMenu("Mis Reservas");
        JButton btnGestionUbicaciones = estilo.crearBotonMenu("Gestión Ubicaciones");
        JButton btnAdministrarSalas = estilo.crearBotonMenu("Gestión Salas");
        JButton btnGestionApps = estilo.crearBotonMenu("Gestión Aplicaciones");
        JButton btnSolicitudApps = estilo.crearBotonMenu("Solicitudes de instalación");
        JButton btnReservasActivas = estilo.crearBotonMenu("Reservas Activas");
        JButton btnSolicitudesAdmin = estilo.crearBotonMenu("Solicitudes de administrador");
        JButton btnSalir = estilo.crearBotonMenu("Cerrar Sesión");

        // Eventos
        btnInfoUsuario.addActionListener(e -> mostrarPanel(new PanelInicio()));
        btnBuscarSalas.addActionListener(e -> mostrarPanel(new Sub_buscar_sala()));
        btnMisReservas.addActionListener(e -> mostrarPanel(new Sub_mis_reservas()));
        btnGestionUbicaciones.addActionListener(e -> mostrarPanel(new Sub_gest_ubicacion()));
        btnAdministrarSalas.addActionListener(e -> mostrarPanel(new Sub_gest_salas()));
        btnGestionApps.addActionListener(e -> mostrarPanel(new Sub_gest_app()));
        btnSolicitudApps.addActionListener(e -> mostrarPanel(new Sub_soli_app()));
        btnReservasActivas.addActionListener(e -> mostrarPanel(new Sub_reservas_activas()));
        btnSolicitudesAdmin.addActionListener(e -> mostrarPanel(new Sub_soli_admin()));
        btnSalir.addActionListener(e -> dispose());

        // Organización del menú
        menuLateral.add(Box.createVerticalStrut(30));
        menuLateral.add(btnInfoUsuario);
        menuLateral.add(Box.createVerticalStrut(10));

        if ("profesor".equals(usuario.getTipoUsuario())) {
            menuLateral.add(btnBuscarSalas);
            menuLateral.add(Box.createVerticalStrut(10));
            menuLateral.add(btnMisReservas);
        } else if ("administrador".equals(usuario.getTipoUsuario())) {
            switch (estado_solicitud) {
                case "aprobado" -> {
                    menuLateral.add(btnReservasActivas);
                    menuLateral.add(Box.createVerticalStrut(10));
                    menuLateral.add(btnGestionUbicaciones);
                    menuLateral.add(Box.createVerticalStrut(10));
                    menuLateral.add(btnAdministrarSalas);
                    menuLateral.add(Box.createVerticalStrut(10));
                    menuLateral.add(btnGestionApps);
                    menuLateral.add(Box.createVerticalStrut(10));
                    menuLateral.add(btnSolicitudApps);
                    menuLateral.add(Box.createVerticalStrut(10));
                    menuLateral.add(btnSolicitudesAdmin);
                }
                case "en espera" -> JOptionPane.showMessageDialog(this, "Solicitud de admin en espera");
                case "rechazado" -> JOptionPane.showMessageDialog(this, "Solicitud de admin rechazada");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }

        menuLateral.add(Box.createVerticalGlue());
        menuLateral.add(btnSalir);
        menuLateral.add(Box.createVerticalStrut(30));

        add(menuLateral, BorderLayout.WEST);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(estilo.colorFondoPanel);
        add(panelPrincipal, BorderLayout.CENTER);

        mostrarPanel(new PanelInicio());
        setVisible(true);
    }

    private void mostrarPanel(JPanel nuevoPanel) {
        panelPrincipal.removeAll();
        panelPrincipal.add(nuevoPanel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    // Panel de bienvenida estilizado
    private class PanelInicio extends JPanel {
        public PanelInicio() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            JLabel etiqueta = estilo.crearTituloMenu("Bienvenido al Programa");
            add(etiqueta, BorderLayout.CENTER);
        }
    }
}

