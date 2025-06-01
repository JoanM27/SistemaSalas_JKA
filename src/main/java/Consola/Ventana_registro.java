package Consola;

import Control.UsuarioDAO;
import Control.Autenticar;
import Modelo.Usuario;
import java.util.Scanner;

public class Ventana_registro {
	public void run() {
             Scanner entrada = new Scanner(System.in);
             ///////PARAMETROS DE CONTROL
             boolean close = false;
             boolean check1,check2,check3,check4,check5,check6,check7;
             check1=check2=check3=check4=check5=check6=check7=false;
             String mensaje_error = "";
             boolean error = true;
             ///////VARIABLES
             UsuarioDAO registro = new UsuarioDAO();
             Usuario usuario = new Usuario();
             ///////////////////////////////
                String cedula;
                String nombre;
                String apellido;
                String correo;
                String telefono;
                String password;
                String password_check;
                String tipoUsuario;
             //////////////////////////////
             //Ejecucion del formulario
             while(close==false) {
                 System.out.println("=====================================");
                 System.out.println("||||-Registro de usuario-||||");
                 ////validación del formulario
                 if(error){
                     System.out.print(mensaje_error);
                     mensaje_error="";
                 }
                 //(Dato 1)
                 if(check1==false){
                     //Pedir dato al usuario
                     System.out.print("Ingrese su cedula: ");
                     cedula =entrada.nextLine();
                     check1=true;//Ingresar validador si es necesario
                     usuario.setCedula(cedula);
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Cedula: "+usuario.getCedula());
                 }
                 //(Dato 2)
                 if(check2==false){
                     //Pedir dato al usuario
                     System.out.print("Ingrese su nombre: ");
                     nombre =entrada.nextLine();
                     check2=true;//Ingresar validador si es necesario
                     usuario.setNombre(nombre);
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Nombre: "+usuario.getNombre());
                 }
                 //(Dato 3)
                 if(check3==false){
                     //Pedir dato al usuario
                     System.out.print("Ingrese su apellido: ");
                     apellido=entrada.nextLine();
                     check3=true;//Ingresar validador si es necesario
                     usuario.setApellido(apellido);
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Apellido: "+usuario.getApellido());
                 }
                 //(Dato 4)
                 if(check4==false){
                     //Pedir dato al usuario
                     System.out.print("Ingrese su correo institucional: ");
                     correo = entrada.nextLine();
                     check4=true;//Ingresar validador si es necesario
                     usuario.setCorreo(correo);
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Correo: "+usuario.getCorreo());
                 }
                 //(Dato 5)
                 if(check5==false){
                     //Pedir dato al usuario
                     System.out.print("Ingrese su telefono: ");
                     telefono = entrada.nextLine();
                     check5=true;//Ingresar validador si es necesario
                     usuario.setTelefono(telefono);
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Telefono: "+usuario.getTelefono());
                 }
                 //(Dato 6)
                 boolean check6_1 = false;
                 boolean check6_2 = false;
                 if(check6==false){
                     
                     //Pedir dato al usuario
                     System.out.print("Crear contraseña: ");
                     password = entrada.nextLine();
                     check6_1=true;//Ingresar validador si es necesario
                     
                     
                     System.out.print("Confirmar contraseña: ");
                     password_check = entrada.nextLine();
                     if(password == password_check ){
                         check6_2=true;
                     }else{
                         mensaje_error += "> Confirmar contraseña -> contraseñas diferentes <\n";
                     }
                     
                     if(check6_1==true && check6_2==true ){
                         usuario.setPassword(password);
                         check6=true;
                        }
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Contraseña: "+usuario.getPassword());
                 }
                 //(Dato 7)
                 if(check7==false){
                     String opt_rol;
                     //Pedir dato al usuario
                     System.out.println("||Elija su rol|| ");
                     System.out.println("(1) Profesor");
                     System.out.println("(2) administrador (Requiere confirmación)");
                     System.out.print("> ");
                     opt_rol = entrada.nextLine();
                     switch(opt_rol){
                         case "1":
                             usuario.setTipoUsuario("profesor");
                             check7=true;
                             break;
                         case "2":
                             usuario.setTipoUsuario("administrador");
                             check7=true;
                             break;
                         default:
                             mensaje_error+="> Opción de rol no valida <\n";
                             break;
                     }
                 }else{
                     //Imprimir lo que guarda
                     System.out.println("Rol: "+usuario.getTipoUsuario());
                 }
                 //OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
                 //Final
                 if(check1==true && check2==true && check3==true && check4==true && check5==true && check6==true &&check7==true){
                     error=false;
                 }
                 boolean opt_valida = false;
                 if(error){
                     System.out.println("Errores encontrados: ");
                     System.out.print(mensaje_error);
                     while(opt_valida ==false){
                         String opt_error;
                     System.out.println("--------------------------------");
                            
                    System.out.println("(1) Ingresar de Nuevo");
                    System.out.println("(2) Cancelar");
                    ///ENTRADA DE OPCION
                    System.out.print("Elegir opción: ");
                    opt_error =entrada.nextLine();
                    //DECISION
                    switch(opt_error) {
                        case "1":
                            opt_valida =true;
                            break;
                        case "2":
                            opt_valida =true;
                            close=true;
                            break;
                        default:
                            System.out.println("> Error al elegir opción, ingresar de nuevo <");
                            break;
                         }
                         System.out.println("--------------------------------");
                     }
                 }else{
                     String opt_registro;
                     while(opt_valida ==false){
                         String opt;
                     System.out.println("--------------------------------");
                            
                    System.out.println("(1) Guardar");
                    System.out.println("(2) Cancelar");
                    ///ENTRADA DE OPCION
                    System.out.print("Elegir opción: ");
                    opt_registro =entrada.nextLine();
                    //DECISION
                    switch(opt_registro) {
                        case "1":
                            registro.registrarUsuario(usuario);
                            opt_valida =true;
                            break;
                        case "2":
                            opt_valida =true;
                            close=true;
                            break;
                        default:
                            System.out.println("> Error al elegir opción, ingresar de nuevo <");
                            break;
                         }
                         System.out.println("--------------------------------");
                     }
                     
                 }
                 ///___Interaccion del usuario___
                 
                 
                        System.out.println("=====================================");
		}
                
             
             ///////------------------------------///////////////////////////////
	}
}


