package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Sub_soli_app extends JPanel {
        public Sub_soli_app() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            JLabel etiqueta = new JLabel("Gestion de solicitudes de aplicacion", SwingConstants.CENTER);
            etiqueta.setFont(new Font("Verdana", Font.BOLD, 30));
            etiqueta.setForeground(new Color(44, 62, 80));
            add(etiqueta, BorderLayout.CENTER);
        }
    }
