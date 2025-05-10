package Modelo;

public abstract class Reserva {//completa
	private String id;
	private String cedula_usuario;

	public String get_id() {
            return id;
	}

	public void set_id(String i) {
            id=i;
	}

	public String get_cedula_usuario() {
            return cedula_usuario;
	}

	public void set_cedula_usuario(String c) {
            cedula_usuario=c;
	}

}
