
package Proyecto_3;

/**
 *
 * @author JorgeJr
 */
public class Proceso {
    String nombre;
    int pid;
    int size;
    int paginas;
    int paginasEjecucion;
    int paginasVirtuales;
    int paginasFinalizadas;
    int quantum;
    String status;
    int marco;

    public Proceso(String nombre, int pid, int size, int paginas, int quantum){
        this.nombre = nombre;
        this.pid = pid;
        this.size = size;
        this.paginas = paginas;
        this.quantum = quantum;
        this.paginasEjecucion = 0;
        this.paginasVirtuales = 0;
        this.paginasFinalizadas = 0;
        this.status = "Asignado";
    }

    public Proceso(String nombre, int pid, int size, int quantum){
        this.nombre = nombre;
        this.pid = pid;
        this.size = size;
        this.quantum = quantum;
        this.paginasEjecucion = 0;
        this.paginasVirtuales = 0;
        this.paginasFinalizadas = 0;
        this.status = "Asignado";
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public int getPaginasEjecucion() {
        return paginasEjecucion;
    }

    public void setPaginasEjecucion(int paginasEjecucion) {
        this.paginasEjecucion = paginasEjecucion;
    }

    public int getPaginasVirtuales() {
        return paginasVirtuales;
    }

    public void setPaginasVirtuales(int paginasVirtuales) {
        this.paginasVirtuales = paginasVirtuales;
    }

    public int getPaginasFinalizadas() {
        return paginasFinalizadas;
    }

    public void setPaginasFinalizadas(int paginasFinalizadas) {
        this.paginasFinalizadas = paginasFinalizadas;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMarco() {
        return marco;
    }

    public void setMarco(int marco) {
        this.marco = marco;
    }

    


    
}
