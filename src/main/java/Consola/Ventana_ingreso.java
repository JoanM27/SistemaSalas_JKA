/*package Consola;

import jm.sistemasalas_jka.SistemaSalas_JKA;
import Control.AplicacionDAO;
import Control.Autenticar;
import java.util.Scanner;

public class Ventana_ingreso {

	private SistemaSalas_JKA sistemaSalas;

	private Ventana_usuario ventana_usuario;

	private AplicacionDAO.Autenticar autenticar;

	private Ventana_principal ventana_principal;

	public void run() {
             Scanner entrada = new Scanner(System.in);
		String opt ="";
		boolean close = false;
		while(close==false) {
			///////////////////////////////////////////////////
			//MENU PRINCIPAL
			System.out.println("=====================================");
			System.out.println("||||-Pagina Principal-||||");
			System.out.println("(1) Iniciar Sesion");
			System.out.println("(2) Registrarse");
			System.out.println("(3) Salir");
			////////////////////////////////////////////////////
			///ENTRADA DE OPCION
			System.out.print("Elegir opción: ");
			opt =entrada.nextLine();
			System.out.println("=====================================");
			///////////////////////////////////////////////////
			//DIRECCIONAMIENTO
			switch(opt) {
			case "1":
				//TODO
				break;
			case "2":
				//TODO
				break;
			case "3":
				close = true;
				break;
			default:
				System.out.println("> Error al elegir opción <");
				break;
			}
		}
	}

}*/
