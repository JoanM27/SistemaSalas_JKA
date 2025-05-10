package Modelo;

public class Usuario {

	private String cedula;
	private String correo_institucional;
	private String nombre;
	private String apellido;
	private String telefono;
	private String clave;
	private String tipo_usuario;
        ////////////////////////////////////
	public String get_cedula(){
            return cedula;
	}

	public void set_cedula(String c) {
            cedula = c;
	}

	public String get_correo_institucional() {
		return correo_institucional;
	}

	public void set_correo_institucional(String c) {
            correo_institucional=c;
	}

	public String get_nombre() {
		return nombre;
	}

	public void set_nombre(String n) {
            nombre = n;
	}

	public String get_apellido() {
		return apellido;
	}

	public void set_apellido(String a) {
            apellido=a;
	}

	public String get_telefono() {
		return telefono;
	}

	public void set_telefono(String t) {
            telefono = t;
	}

	public String get_clave() {
		return clave;
	}

	public void set_clave(String c) {
            clave=c;
	}

	public String get_tipo_usuario() {
		return tipo_usuario;
	}

	public void set_tipo_usuario(String t) {
            tipo_usuario = t;
	}

	public Usuario(String cedula, String nombre, String apellido, String correo, String telefono, String rol, String password) {
            set_cedula(cedula);
            set_nombre(nombre);
            set_apellido(apellido);
            set_correo_institucional(correo);
            set_telefono(telefono);
            set_tipo_usuario(rol);
            set_clave(password);
	}

}
