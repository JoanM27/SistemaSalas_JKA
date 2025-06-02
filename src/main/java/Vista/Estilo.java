
package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Estilo {
    // Configuraciones externas
    Dimension tamCampo = new Dimension(400, 30);
    Dimension tamBotonGrande = new Dimension(600, 60);
    Dimension tamBotonMediano = new Dimension(200, 40);
    Insets margenInterno = new Insets(4, 10, 4, 10);
    Dimension tamBotonMenu = new Dimension(400, 50);

    final Color colorFondoPanel = new Color(235, 245, 253);
    final Color colorTitulo = new Color(6, 88, 159);
    final Color colorTextoBoton = new Color(8, 43, 73);
    final Color colorFondoBoton = new Color(255, 165, 0);
    final Color colorFondoBoton_A1 = new Color(122, 161, 195);
    final Color colorFondoBoton_A2 = new Color(142, 176, 205);
    final Color colorFondoBoton_A3 = new Color(171, 185, 196);
    final Color colorFondoMenu = new Color(33, 45, 62);
    
    final Font fuenteTitulo = new Font("Verdana", Font.BOLD, 30);
    final Font fuenteTituloGrande= new Font("Verdana", Font.BOLD, 46);
    final Font fuenteSubtitulo = new Font("Verdana", Font.BOLD, 16);
    final Font fuenteTexto = new Font("Verdana", Font.PLAIN, 14);
    final Font fuenteTextoPequeño = new Font("Verdana", Font.PLAIN, 10);
    final Font fuenteBoton = new Font("Verdana", Font.PLAIN, 24);
    final Font fuenteBotonMenu = new Font("Verdana", Font.BOLD, 18);
    
    
    public JTextField crearCampo(JPanel panel, String texto, Font fuenteLabel, Font fuenteCampo, Dimension dimension, Insets margen ,int espaciado) {
        JLabel label = new JLabel(texto);
        label.setFont(fuenteLabel);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField campo = new JTextField();
        campo.setFont(fuenteCampo);
        campo.setMaximumSize(dimension);
        campo.setMargin(margen);
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(campo);
        panel.add(Box.createVerticalStrut(espaciado));
        return campo;
    }

    public JPasswordField crearCampoPassword(JPanel panel, String texto, Font fuenteLabel, Dimension dimension, Insets margen, int espaciado) {
        JLabel label = new JLabel(texto);
        label.setFont(fuenteLabel);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField campo = new JPasswordField();
        campo.setMaximumSize(dimension);
        campo.setMargin(margen);
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(campo);
        panel.add(Box.createVerticalStrut(espaciado));
        return campo;
    }

    public JComboBox<String> crearCampoSeleccion(JPanel panel, String texto, String[] opciones, Font fuenteLabel, Font fuenteCombo, Dimension dimension, int espaciado) {
        JLabel label = new JLabel(texto);
        label.setFont(fuenteLabel);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(fuenteCombo);
        combo.setMaximumSize(dimension);
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(combo);
        panel.add(Box.createVerticalStrut(espaciado));
        return combo;
    }
    public JComboBox<String> crearCampoSeleccionSimple_str( Color fondo, Font fuenteCombo, Dimension dimension) {

        JComboBox<String> combo = new JComboBox<>();
        combo.setBackground(fondo);
        combo.setFont(fuenteCombo);
        combo.setMaximumSize(dimension);

        return combo;
    }
    public JComboBox<Integer> crearCampoSeleccionSimple_int( Color fondo, Font fuenteCombo, Dimension dimension) {

        JComboBox<Integer> combo = new JComboBox<>();
        combo.setBackground(fondo);
        combo.setFont(fuenteCombo);
        combo.setMaximumSize(dimension);

        return combo;
    }

    public JButton crearBoton(JPanel panel, String texto, Font fuente, Dimension dimension, Color fondo, Color textoColor, int espaciado) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setMaximumSize(dimension);
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(boton);
        panel.add(Box.createVerticalStrut(espaciado));
        return boton;
    }
    public JButton crearBotonSimple(String texto, Font fuente, Dimension dimension, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setMaximumSize(dimension);
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        return boton;
    }
    public JPanel crearPanelVertical(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(colorFondoPanel);
        return panel;
    }
    public JPanel crearPanelTabla(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(colorFondoPanel);
        return panel;
    }
    public JLabel crearTituloGrande(String titulo){
        JLabel label = new JLabel(titulo);
        label.setFont(fuenteTituloGrande);
        label.setForeground(colorTitulo);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
      return label;  
    }
    public void agregar_imagen_dimensionada(JPanel panel , String ruta , int ancho , int alto){
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon icono = new ImageIcon(url);
            Image imagenEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            JLabel etiquetaIcono = new JLabel(new ImageIcon(imagenEscalada));
            etiquetaIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(etiquetaIcono);
        } else {
            System.err.println("No se encontró la imagen.");
        }
    }
    public void agregar_imagen(JPanel panel , String ruta){
        URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoLogo = new ImageIcon(url);
            JLabel etiquetaLogo = new JLabel(iconoLogo);
            etiquetaLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createVerticalStrut(40));
            panel.add(etiquetaLogo);
        } else {
            System.err.println("No se encontró la imagen.");
        }
    }
    public JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(fuenteBoton);
        boton.setMaximumSize(tamBotonMenu);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setBackground(colorFondoBoton);
        boton.setForeground(colorTextoBoton);
        return boton;
    }
    public void aplicarEstiloMenuLateral(JPanel menu) {
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(colorFondoMenu);
    }
    public JLabel crearTituloMenu(String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setFont(fuenteTitulo);
        etiqueta.setForeground(colorTitulo);
        return etiqueta;
    }
    public JScrollPane convertirPanelDesplazableVertical(JPanel panel){
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scroll;
    }
    public JPanel crearPanelVerticalDividido_scroll(JPanel panelA,JScrollPane panelB){
        JPanel panelPrincipal =this.crearPanelVertical();
        panelPrincipal.setBackground(colorFondoPanel);
        Border borde = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        panelB.setBorder(borde);
        panelB.setBackground(colorFondoPanel);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(panelA);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(panelB);
        
        return panelPrincipal;
    }
    public JLabel crearTextoBasico(String contenido,Font fuente_texto,Color color_texto){
        JLabel label = new JLabel(contenido);
        label.setFont(fuenteTexto);
        label.setForeground(color_texto);
        return label;
    }
    public void limpiarPanel(JPanel panel){
        panel.removeAll();
    };
}
