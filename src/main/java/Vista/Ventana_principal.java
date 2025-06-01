package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Ventana_principal extends JFrame {

    private Estilo estilo = new Estilo();

    public Ventana_principal() {
        setTitle("Página Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Icono de la ventana
        URL urlIcono = getClass().getResource("/imagenes/icono.png");
        if (urlIcono != null) {
            setIconImage(new ImageIcon(urlIcono).getImage());
        } else {
            System.err.println("No se encontró el icono. Verifica la ruta.");
        }

        JPanel panel = estilo.crearPanelVertical();

        // Título
        JLabel titulo = estilo.crearTituloGrande("Préstamo de Salas de Informática");

        panel.add(Box.createVerticalStrut(80));
        panel.add(titulo);
        
        // Logo institucional
            estilo.agregar_imagen(panel, "/imagenes/UDI-logo.png");
        // Botones
        JButton btnIniciarSesion = estilo.crearBotonSimple("Iniciar Sesión", estilo.fuenteBoton, estilo.tamBotonGrande, estilo.colorFondoBoton, estilo.colorTextoBoton);
        JButton btnRegistrarse = estilo.crearBotonSimple("Registrarse", estilo.fuenteBoton, estilo.tamBotonGrande, estilo.colorFondoBoton, estilo.colorTextoBoton);
        JButton btnSalir = estilo.crearBotonSimple("Salir", estilo.fuenteBoton, estilo.tamBotonGrande, estilo.colorFondoBoton, estilo.colorTextoBoton);

        JButton[] botones = { btnIniciarSesion, btnRegistrarse, btnSalir };
        for (JButton btn : botones) {
            panel.add(Box.createVerticalStrut(30));
            panel.add(btn);
        }

        // Eventos
        btnIniciarSesion.addActionListener(e -> {
            Ventana_ingreso ingreso = new Ventana_ingreso();
            ingreso.setVisible(true);
            setVisible(false);
            ingreso.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setVisible(true);
                }
            });
        });

        btnRegistrarse.addActionListener(e -> {
            Ventana_registro registro = new Ventana_registro();
            registro.setVisible(true);
            setVisible(false);
            registro.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setVisible(true);
                }
            });
        });

        btnSalir.addActionListener(e -> System.exit(0));

        add(panel);
        setVisible(true);
    }
}

