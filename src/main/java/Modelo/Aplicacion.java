package Modelo;

public class Aplicacion {//completa
	private String id;
	private String nombre;
	private String descripcion;

	public String get_id() {
	return id;
	}

	public void set_id(String i) {
            id=i;
	}

	public String get_nombre() {
            return nombre;
	}

	public void set_nombre(String n) {
            nombre=n;
	}

	public String get_descripcion() {
            return descripcion;
	}

	public void set_descripcion(String d) {
            descripcion=d;
	}
        ///Constructor de Aplicacion
	public Aplicacion(String i, String n, String d) {
            id = i;
            nombre = n;
            descripcion = d;
        }

}
