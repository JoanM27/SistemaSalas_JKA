package Control;

import Vista.Sub_reservar;
import Vista.Sub_soli_app;
import Database.ConexionOracle;
import Modelo.Solicitud_aplicacion;

public class Solicitud_aplicacionDAO {

	private Sub_reservar sub_reservar;

	private Sub_soli_app sub_soli_app;

	private ConexionOracle conexionOracle;

	private Solicitud_aplicacion solicitud_aplicacion;

	public void solicitar() {

	}

	public boolean validar_solicitud() {
		return false;
	}

	public char detalles_aplicacion() {
		return 0;
	}

	public char lista_solicitud() {
		return 0;
	}

}
