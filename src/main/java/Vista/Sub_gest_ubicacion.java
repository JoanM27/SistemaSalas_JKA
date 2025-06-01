    
package Vista;

import Control.UbicacionDAO;
import Modelo.Ubicacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Sub_gest_ubicacion extends JPanel {
    private JPanel panelPrincipal;
    private JButton btnAgregarUbicacion;
    private JPanel panelUbicaciones;
    private JPanel panelContenedor;
    private UbicacionDAO ubicacionDAO;
    private Estilo estilo = new Estilo();
    public Sub_gest_ubicacion() {
        //Configuracion
        setLayout(new BorderLayout());
        //Objeto de procesos en BD
        ubicacionDAO = new UbicacionDAO();
        //Objeto de estilizacion
        
        //Iniciar paneles
        
        
        
        panelUbicaciones = estilo.crearPanelVertical();
        panelContenedor = estilo.crearPanelVertical();
        
        JScrollPane scroll = estilo.convertirPanelDesplazableVertical(panelUbicaciones);
        panelPrincipal = estilo.crearPanelVerticalDividido_scroll(panelContenedor,scroll);
        //boton agregar ubicacion
        btnAgregarUbicacion = estilo.crearBotonSimple("Agregar Bloque / Edificio", estilo.fuenteSubtitulo, null,estilo.colorFondoBoton_A1, estilo.colorTextoBoton);
        btnAgregarUbicacion.addActionListener(e -> mostrarVentanaAgregarUbicacion());
        panelContenedor.add(btnAgregarUbicacion);
        ///darle color de fondo al panel principal
        panelPrincipal.setBackground(estilo.colorFondoPanel);
        add(panelPrincipal,BorderLayout.CENTER);
        cargarUbicaciones();
        setVisible(true);
    }

    private void cargarUbicaciones() {
        //Limpieza del panel
        estilo.limpiarPanel(panelUbicaciones);
        //Lista de ubicaciones
        List<Ubicacion> ubicaciones = ubicacionDAO.obtenerTodasUbicaciones();
        
        ubicaciones.stream()//encadenar operaciones 
            .map(Ubicacion::getBloque)//Tranforma cada objeto en su nombre
            .distinct()//filtra los nombres repetidos si los hubiera
            .forEach(bloque -> {//ejecuta las operaciones anteriores y ejecuta objeto por objeto
                String bloqueStr = "o Bloque: " + bloque;//Mensaje del boton
                JButton btnBloque = estilo.crearBotonSimple(bloqueStr, estilo.fuenteSubtitulo, null,estilo.colorFondoBoton_A2 ,estilo.colorTextoBoton);
                btnBloque.setAlignmentX(Component.LEFT_ALIGNMENT);
                //Acciones de los botones
                btnBloque.addActionListener(e -> mostrarVentanaEditarBloque(bloque));
                btnBloque.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
                //Añadir boton por boton en al panel
                panelUbicaciones.add(btnBloque);
                ///Dentro de este for crea otro for ahora con los pisos de este bloque
                ubicaciones.stream()
                    .filter(u -> u.getBloque().equals(bloque))//filtra los datos que cumplan con esa condicion
                    .forEach(ubicacion -> {
                        ///configuracion boton
                        String PisoStr =" > Piso " + ubicacion.getPiso();
                        JButton btnPiso = estilo.crearBotonSimple(PisoStr, estilo.fuenteTexto, null,estilo.colorFondoBoton_A3 ,estilo.colorTextoBoton);
                        btnPiso.setAlignmentX(Component.LEFT_ALIGNMENT);
                        ///agregar accion
                        btnPiso.addActionListener(e -> mostrarVentanaEditarUbicacion(ubicacion));
                        ///añadir al panel
                        panelUbicaciones.add(btnPiso);
                    });//fin del ciclo pisos
            });//fin del ciclo bloques

        panelUbicaciones.revalidate();
        panelUbicaciones.repaint();
    }

    private void mostrarVentanaAgregarUbicacion() {
        //campos de texto
        JTextField txtBloque = new JTextField();
        JTextField txtCantidadPisos = new JTextField();
        //Array que almacena objetos de cualquier tipo
        Object[] campos = {
            "Nombre del bloque/edificio:", txtBloque,
            "Cantidad de pisos:", txtCantidadPisos
        };
        //ventana emergente
        int opcion = JOptionPane.showConfirmDialog(this, campos, "Agregar Ubicación", JOptionPane.OK_CANCEL_OPTION);
        //si opcion es OK realiza el guardado
        if (opcion == JOptionPane.OK_OPTION) {
            String bloque = txtBloque.getText().trim();//quitar espacios lado a lado
            int pisos;
            try {
                //obtener cantidad de pisos
                pisos = Integer.parseInt(txtCantidadPisos.getText());
                //Crear ubicaciones
                for (int i = 1; i <= pisos; i++) {
                    ubicacionDAO.crearUbicacion(new Ubicacion(null, bloque, i));
                }
                ///
                cargarUbicaciones();///Actualizar la lista
                JOptionPane.showMessageDialog(this, "Ubicación agregada con éxito.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad de pisos inválida.");
            }
        }
    }

    private void mostrarVentanaEditarUbicacion(Ubicacion ubicacion) {
        // Crear panel con mensaje personalizado
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("¿Estás seguro de que deseas eliminar este piso y sus salas asociadas?"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Bloque: " + ubicacion.getBloque()));
        panel.add(new JLabel("Piso: " + ubicacion.getPiso()));

        // Botones personalizados
        Object[] opciones = {"Eliminar Piso", "Cancelar"};
        //Ventana emergente
        int opcion = JOptionPane.showOptionDialog(
            this,                     // 1. Componente Padre (parentComponent)
            panel,                    // 2. Mensaje (message)
            "Eliminar Piso",          // 3. Título del Diálogo (title)
            JOptionPane.YES_NO_OPTION,// 4. Tipo de Opción (optionType)
            JOptionPane.WARNING_MESSAGE,// 5. Tipo de Mensaje (messageType)
            null,                     // 6. Icono (icon)
            opciones,                 // 7. Opciones de Botón (options)
            opciones[1]               // 8. Opción por Defecto (initialValue)
        );
        ///La opcion eliminar piso elimina la ubicacion
        if (opcion == JOptionPane.YES_OPTION) {
            if (ubicacionDAO.eliminarUbicacionConSalasPorId(ubicacion.getId())) {
                JOptionPane.showMessageDialog(this, "Ubicación y salas eliminadas correctamente.");
                cargarUbicaciones(); // Vuelve a cargar la lista
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la ubicación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarVentanaEditarBloque(String bloqueActual) {
    JTextField txtBloque = new JTextField(bloqueActual);

    Object[] campos = {
        "Editar nombre del bloque:", txtBloque
    };

    Object[] opciones = {"Guardar cambios", "Agregar piso", "Eliminar bloque", "Cancelar"};

    int opcion = JOptionPane.showOptionDialog(
        this,
        campos,
        "Editar Bloque",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        opciones,
        opciones[0]
    );

    if (opcion == 0) { // Guardar cambios (renombrar bloque)
        String nuevoBloque = txtBloque.getText().trim();

        if (nuevoBloque.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del bloque no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Ubicacion> ubicaciones = ubicacionDAO.obtenerTodasUbicaciones();
        boolean exitoso = true;

        for (Ubicacion ubic : ubicaciones) {
            if (ubic.getBloque().equalsIgnoreCase(bloqueActual)) {
                Ubicacion nuevaUbic = new Ubicacion(ubic.getId(), nuevoBloque, ubic.getPiso());
                if (!ubicacionDAO.modificarUbicacion(nuevaUbic)) {
                    exitoso = false;
                    break;
                }
            }
        }

        if (exitoso) {
            JOptionPane.showMessageDialog(this, "Bloque renombrado correctamente.");
            cargarUbicaciones();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al renombrar el bloque.");
        }

    } else if (opcion == 1) { // Agregar piso nuevo
        String pisoStr = JOptionPane.showInputDialog(this, "Ingresa el número de piso que deseas agregar:", "Nuevo Piso", JOptionPane.PLAIN_MESSAGE);

        if (pisoStr != null) {
            try {
                int nuevoPiso = Integer.parseInt(pisoStr.trim());

                // Verificar si ya existe ese piso en el bloque
                boolean yaExiste = false;
                for (Ubicacion u : ubicacionDAO.obtenerTodasUbicaciones()) {
                    if (u.getBloque().equalsIgnoreCase(bloqueActual) && u.getPiso() == nuevoPiso) {
                        yaExiste = true;
                        break;
                    }
                }

                if (yaExiste) {
                    JOptionPane.showMessageDialog(this, "Ese piso ya existe en el bloque.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Crear nueva ubicación
                Ubicacion nuevaUbic = new Ubicacion(null, bloqueActual, nuevoPiso);
                if (ubicacionDAO.crearUbicacion(nuevaUbic)) {
                    JOptionPane.showMessageDialog(this, "Piso agregado con éxito.");
                    cargarUbicaciones();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el nuevo piso.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Número de piso inválido.");
            }
        }

    } else if (opcion == 2) { // Eliminar todo el bloque
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas eliminar este bloque y todas sus ubicaciones y salas?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ubicacionDAO.eliminarBloqueConTodo(bloqueActual)) {
                JOptionPane.showMessageDialog(this, "Bloque y todos sus datos eliminados.");
                cargarUbicaciones();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el bloque.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

}

