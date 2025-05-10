package Control;

import Database.ConexionOracle;
import Modelo.Usuario;

public class UsuarioDAO {

	private AplicacionDAO.Autenticar autenticar;

	private ConexionOracle conexionOracle;

	private Usuario usuario;


	public Usuario inicio_sesion(String cedula, String contraseña) {
		return null;
	}

	public boolean registro(Usuario usuario) {
		return false;
	}

	public String[] detalles_sesion(Usuario usuario) {
		return null;
	}

	public boolean eliminar_usuario(Usuario usuario) {
		return false;
	}

	public boolean editar_usuario(Usuario usuario) {
		return false;
	}

        
}
