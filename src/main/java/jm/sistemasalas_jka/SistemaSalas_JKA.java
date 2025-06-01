package jm.sistemasalas_jka;

import Vista.Ventana_principal;

public class SistemaSalas_JKA {

   public static void main(String[] args) {
        System.out.println("Funcionando");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_principal().setVisible(true);
            }
        });
    }
}
