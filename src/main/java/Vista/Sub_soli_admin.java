package Vista;

import Control.Solicitud_adminDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Sub_soli_admin extends JPanel {
        public Sub_soli_admin() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            JLabel etiqueta = new JLabel("Ver solicitudes de admin", SwingConstants.CENTER);
            etiqueta.setFont(new Font("Verdana", Font.BOLD, 30));
            etiqueta.setForeground(new Color(44, 62, 80));
            add(etiqueta, BorderLayout.CENTER);
        }
    }
