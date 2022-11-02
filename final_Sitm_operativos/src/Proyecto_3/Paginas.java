
package Proyecto_3;

import javax.swing.JOptionPane;


/**
 *
 * @author JorgeJr
 */
public class Paginas extends Thread{

    Proceso proceso;
    int pagina;
    int size;
    int marco;
    String status = "Asignado";

    public Paginas(Proceso proceso, int pagina, int size, int marco) {
        this.proceso = proceso;
        this.pagina = pagina;
        this.size = size;
        this.marco = marco;
    }

    public Paginas(Proceso proceso, int pagina, int size) {
        this.proceso = proceso;
        this.pagina = pagina;
        this.size = size;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMarco() {
        return marco;
    }

    public void setMarco(int marco) {
        this.marco = marco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void run(){
        try{
            Thread.sleep(this.proceso.getQuantum());
            this.status = "Finalizado";
            this.proceso.setPaginasFinalizadas(this.proceso.getPaginasFinalizadas() + 1);
            this.proceso.setPaginasEjecucion(this.proceso.getPaginasEjecucion() - 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }


    
}
