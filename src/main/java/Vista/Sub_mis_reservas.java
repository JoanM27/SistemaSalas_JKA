
package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Sub_mis_reservas extends JPanel {
        public Sub_mis_reservas() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            JLabel etiqueta = new JLabel("Mis reservas", SwingConstants.CENTER);
            etiqueta.setFont(new Font("Verdana", Font.BOLD, 30));
            etiqueta.setForeground(new Color(44, 62, 80));
            add(etiqueta, BorderLayout.CENTER);
        }
    }
