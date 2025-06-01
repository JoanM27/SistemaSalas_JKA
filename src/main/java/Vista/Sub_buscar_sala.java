package Vista;

import Control.Filtro_salas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Sub_buscar_sala extends JPanel {
        public Sub_buscar_sala() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            JLabel etiqueta = new JLabel("Buscar Sala", SwingConstants.CENTER);
            etiqueta.setFont(new Font("Verdana", Font.BOLD, 30));
            etiqueta.setForeground(new Color(44, 62, 80));
            add(etiqueta, BorderLayout.CENTER);
        }
    }
