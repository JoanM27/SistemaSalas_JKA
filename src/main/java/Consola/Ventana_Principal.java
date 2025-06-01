
package Consola;
import java.util.*;
import Consola.Ventana_registro;
public class Ventana_Principal {
   public void run(){
        Scanner entrada = new Scanner(System.in);
        Ventana_registro registro = new Ventana_registro();
        
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
				registro.run();
				break;
			case "3":
				close = true;
				break;
			default:
				System.out.println("> Error al elegir opción <");
				break;
			}
		}
                ////
                System.out.println("Salida exitosa");
   }
}
