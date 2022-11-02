
package Proyecto_3;

/**
 *
 * @author JorgeJr
 */
public class MyThread extends Thread{
    
    public void run(){
        for(int i=0; i < 5; i++){
            System.out.println(i + this.getName());
        }
    }
}
