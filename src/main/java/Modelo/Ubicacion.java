
package Modelo;

public class Ubicacion {
    private String id;
    private String bloque;
    private int piso;

    public Ubicacion(String id, String bloque, int piso) {
        this.id = id;
        this.bloque = bloque;
        this.piso = piso;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBloque() { return bloque; }
    public void setBloque(String bloque) { this.bloque = bloque; }
    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }
}