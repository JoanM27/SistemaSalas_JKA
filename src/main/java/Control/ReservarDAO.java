package Control;

import Vista.Sub_reservar;
import Database.ConexionOracle;
import Modelo.Reserva;

public class ReservarDAO {

	private AplicacionDAO.Autenticar autenticar;

	private Sub_reservar sub_reservar;

	private ConexionOracle conexionOracle;

	private Solicitud_aplicacionDAO solicitud_aplicacionDAO;

	private Reserva reserva;

	private HorarioDAO horarioDAO;

	private UbicacionDAO ubicacionDAO;

	public boolean reservar() {
		return false;
	}

	public boolean disponibilidad_horario() {
		return false;
	}

	public char reserva_activas() {
		return 0;
	}

	public char mis_reservas() {
		return 0;
	}

	public char detalles_reservas() {
		return 0;
	}

	public void cancelar() {

	}

	public void modificar() {

	}

}
