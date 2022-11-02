/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto_3;

/**
 *
 * @author Josue
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         memoriMy hilo1 = new memoriMy();
        memoriMy hilo2 = new memoriMy();
        
        hilo1.start();
        hilo2.start();
        
        memoria me = new memoria();
        me.setVisible(true);
    }
    
}
