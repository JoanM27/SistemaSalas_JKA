package Control;

import Vista.Ventana_registro;
import Vista.Ventana_ingreso;

public class Autenticar {

	private Ventana_registro ventana_registro;

	private Ventana_ingreso ventana_ingreso;

	private UsuarioDAO usuarioDAO;

	private SalaDAO salaDAO;

	private ReservarDAO reservarDAO;


	public boolean Inicio_sesion() {
		return false;
	}

	public boolean registro() {
		return false;
	}

	public boolean reserva_horario() {
		return false;
	}

	public boolean disponibilidad_sala() {
		return false;
	}

	public boolean clave_valida() {
		return false;
	}

}
