
package Modelo;

public class Usuario {
    private String cedula;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String password;
    private String tipoUsuario;

    public Usuario(String cedula, String nombre, String apellido, 
                  String correo, String telefono, String password, 
                  String tipoUsuario) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }
    public Usuario(){
        this.cedula = "";
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.telefono = "";
        this.password = "";
        this.tipoUsuario = "";
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
