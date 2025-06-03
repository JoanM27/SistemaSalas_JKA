package Control;


import Modelo.Sala;
import Modelo.Ubicacion;
import Modelo.Aplicacion;
import Vista.Sub_buscar_sala;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Filtro_salas {

    private Sub_buscar_sala sub_buscar_sala;
    private SalaDAO salaDAO;
    private AplicacionDAO aplicacionDAO;
    private UbicacionDAO ubicacionDAO;
    
    public SalaDAO getSalaDAO() {
        return salaDAO;
    }
    public Filtro_salas() {
        salaDAO = new SalaDAO();
        aplicacionDAO = new AplicacionDAO();
        ubicacionDAO = new UbicacionDAO();
    }

    /**
     * Filtra las salas según los parámetros proporcionados
     * @param ubicacion Ubicación a filtrar (puede ser null)
     * @param nombresAplicaciones Lista de nombres de aplicaciones requeridas (puede ser null o vacía)
     * @param minComputadoras Capacidad mínima de computadoras (0 si no aplica)
     * @return Lista de salas que cumplen con los criterios de filtrado
     */
    public List<Sala> filtrarSalas(Ubicacion ubicacion, List<String> nombresAplicaciones, int minComputadoras) {
        List<Sala> todasLasSalas = salaDAO.obtenerTodasSalas();
        List<Sala> salasFiltradas = todasLasSalas;
        
        // Filtro por ubicación
        if (ubicacion != null && (ubicacion.getId() != null || ubicacion.getBloque() != null)) {
            salasFiltradas = filtrarPorUbicacion(salasFiltradas, ubicacion);
        }
        
        // Filtro por aplicaciones
        if (nombresAplicaciones != null && !nombresAplicaciones.isEmpty()) {
            salasFiltradas = filtrarPorAplicaciones(salasFiltradas, nombresAplicaciones);
        }
        
        // Filtro por capacidad
        if (minComputadoras > 0) {
            salasFiltradas = filtrarPorCapacidadComputadoras(salasFiltradas, minComputadoras);
        }
        
        return salasFiltradas;
    }

    private List<Sala> filtrarPorUbicacion(List<Sala> salas, Ubicacion ubicacion) {
        return salas.stream()
            .filter(sala -> {
                if (sala.getUbicacion() == null) return false;
                
                // Si tenemos ID, comparamos por ID
                if (ubicacion.getId() != null) {
                    return ubicacion.getId().equals(sala.getUbicacion().getId());
                }
                // Si no, comparamos por bloque y piso
                return sala.getUbicacion().getBloque().equalsIgnoreCase(ubicacion.getBloque());
            })
            .collect(Collectors.toList());
    }

    private List<Sala> filtrarPorAplicaciones(List<Sala> salas, List<String> nombresAplicaciones) {
        // Convertir nombres a IDs de aplicaciones
        List<String> idsAplicaciones = nombresAplicaciones.stream()
            .map(nombre -> aplicacionDAO.obtenerAplicacionPorNombre(nombre))
            .filter(Objects::nonNull)
            .map(Aplicacion::getId)
            .collect(Collectors.toList());
        
        // Si no hay aplicaciones válidas, no filtramos
        if (idsAplicaciones.isEmpty()) {
            return salas;
        }
        
        return salas.stream()
            .filter(sala -> {
                List<String> appsDeSala = salaDAO.obtenerAplicacionesDeSala(sala.getId()).stream()
                    .map(Aplicacion::getId)
                    .collect(Collectors.toList());
                
                // Verificar que la sala tenga TODAS las aplicaciones requeridas
                return appsDeSala.containsAll(idsAplicaciones);
            })
            .collect(Collectors.toList());
    }

    /**
     * Filtra salas por capacidad mínima de computadoras
     */
    private List<Sala> filtrarPorCapacidadComputadoras(List<Sala> salas, int minComputadoras) {
        return salas.stream()
            .filter(sala -> sala.getMaxComputadoras() >= minComputadoras)
            .collect(Collectors.toList());
    }

    /**
     * Método para obtener todas las salas sin filtros
     */
    public List<Sala> obtenerTodasLasSalas() {
        return salaDAO.obtenerTodasSalas();
    }

    /**
     * Método para listar salas (puede ser usado por la interfaz gráfica)
     */
    public void lista_salas() {
        // Este método podría ser implementado para interactuar con la interfaz
        if (sub_buscar_sala != null) {
            List<Sala> salas = obtenerTodasLasSalas();
            // Aquí se actualizaría la interfaz con las salas obtenidas
        }
    }
}