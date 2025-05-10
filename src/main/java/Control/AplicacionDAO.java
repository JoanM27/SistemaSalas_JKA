package Control;

import Vista.Sub_gest_app;
import Database.ConexionOracle;
import Modelo.Aplicacion;
import Vista.Ventana_registro;
import Vista.Ventana_ingreso;

public class AplicacionDAO {

	private Sub_gest_app sub_gest_app;

	private ConexionOracle conexionOracle;

	private Aplicacion aplicacion;


	public void agregar() {

	}

	public void modificar() {

	}

	public void eliminar() {

	}

	public void lista_aplicaciones() {

	}

	public void detalles_aplicacion() {

	}

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

}
