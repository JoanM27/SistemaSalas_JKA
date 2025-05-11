package Modelo;

public class Sala {
    private String id;
    private String nombre;
    private int maxComputadoras;
    private String normas;
    private Ubicacion ubicacion;

    public Sala(String id,String nombre,int maxComputadoras, String normas, Ubicacion ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.maxComputadoras = maxComputadoras;
        this.normas = normas;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getMaxComputadoras() { return maxComputadoras; }
    public void setMaxComputadoras(int maxComputadoras) { this.maxComputadoras = maxComputadoras; }
    public String getNormas() { return normas; }
    public void setNormas(String normas) { this.normas = normas; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }
}
