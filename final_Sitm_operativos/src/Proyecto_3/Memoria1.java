
package Proyecto_3;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author JorgeJr
 */
public class Memoria1 extends Thread{

    private int nProcess = 0;
    private int quantum;
    private int memory = 2600000;    
    private int pId = 1;
    Paginas[] memoriaRam = new Paginas[13];
    ArrayList<Proceso> procesos = new ArrayList<Proceso>();
    Paginas[] memoriaVirtual = new Paginas[10];
    ArrayList<Paginas> paginas = new ArrayList<Paginas>();
    public JTable tblProcesos;
    public JTable tblPaginas;
    public JTable tblTablaPaginas;
    public JTable tblMemoriaVirtual;
    public JTable tblMemoriaRam;

    public Memoria1(JTable tblProcesos, JTable tblPaginas, JTable tblTablaPaginas, JTable tblMemoriaVirtual, JTable tblMemoriaRam){
        this.tblProcesos = tblProcesos;
        this.tblPaginas = tblPaginas;
        this.tblTablaPaginas = tblTablaPaginas;
        this.tblMemoriaVirtual = tblMemoriaVirtual;
        this.tblMemoriaRam = tblMemoriaRam;
        setTableMemoriaRam((DefaultTableModel) this.tblProcesos.getModel());
        this.tblProcesos.repaint();
    }

    public void setTableMemoriaRam(DefaultTableModel model){
        resetTable(model);
        int pos = 1;
        for(Paginas p : this.memoriaRam){
            if(p == null){
                model.addRow(new Object[]{pos,200000,0,200000,""});
            }else{
                model.addRow(new Object[]{pos,200000,p.getSize(),200000-p.getSize(),p.getProceso().getNombre()});
            }
            pos++;
        }
    }

    public void setTableMemoriaVirtual(DefaultTableModel model){
        resetTable(model);
        int pos = 1;
        for(Paginas p : this.memoriaVirtual){
            if(p == null){
                model.addRow(new Object[]{pos,"","",""});
            }else{
                model.addRow(new Object[]{pos,p.getProceso().getNombre(),p.getProceso().getPid(),p.getSize()});
            }
            pos++;
        }
    }

    public void setTableProcesos(DefaultTableModel model){
        resetTable(model);
        for(Proceso p : this.procesos){
            model.addRow(new Object[]{p.getNombre(), p.getPid(), p.getStatus(), p.getSize(), p.getPaginas(), p.getPaginasEjecucion(), p.getPaginasVirtuales(), p.getPaginasFinalizadas()});
        }
    }

    public void setTablePaginas(DefaultTableModel model){
        resetTable(model);
        for(Paginas p : this.paginas){
            if(p.getStatus() == "Asignado"){
                model.addRow(new Object[]{p.proceso.getPid(), p.getPagina(), p.getSize()});
            }
        }
    }

    public void setTableTablaPaginas(DefaultTableModel model){
        resetTable(model);
        for(Paginas p : this.paginas){
            if(p.getStatus() == "Asignado"){
                model.addRow(new Object[]{p.proceso.getPid(), p.getPagina(), p.getMarco()});
            }
        }
    }


    public void resetTable(DefaultTableModel model){
        int rows = model.getRowCount();
        for(int i=rows-1; i >= 0; i--){ model.removeRow(i); }
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void run(){

        try{
            while(true){
                Thread.sleep(this.quantum);


                for(Paginas p : this.paginas){
                    if(p.getStatus() == "Finalizado"){
                        for(int i=0; i<this.memoriaRam.length; i++){
                            if((p).equals(this.memoriaRam[i])){
                                this.memoriaRam[i] = null;
                                this.memory += 200000;

                                for(int vi=0; vi<this.memoriaVirtual.length; vi++){
                                    if(this.memory > 0 && this.memoriaVirtual[vi] != null){
                                        this.memoriaVirtual[vi].getProceso().setPaginasVirtuales(this.memoriaVirtual[vi].getProceso().getPaginasVirtuales() - 1);
                                        this.memoriaVirtual[vi].getProceso().setPaginasEjecucion(this.memoriaVirtual[vi].getProceso().getPaginasEjecucion() + 1);
                                        Paginas ram = (Paginas) this.memoriaVirtual[vi];
                                        
                                        this.memoriaVirtual[vi] = null;

                                        this.memory -= 200000;
                                        addMemoryRAM(ram, getRandomNumberUsingNextInt(0, 12));
                                    }
                                }
                            }
                        }
                    }
                }

                for(Proceso p : this.procesos){
                    if(p.getPaginas() == p.getPaginasFinalizadas()){
                        p.setStatus("Finalizado");
                    }
                }


                setTableProcesos((DefaultTableModel) this.tblProcesos.getModel());
                setTablePaginas((DefaultTableModel) this.tblPaginas.getModel());
                setTableTablaPaginas((DefaultTableModel) this.tblTablaPaginas.getModel());
                setTableMemoriaRam((DefaultTableModel) this.tblMemoriaRam.getModel());
                setTableMemoriaVirtual((DefaultTableModel) this.tblMemoriaVirtual.getModel());
                this.tblProcesos.repaint();
                this.tblPaginas.repaint();
                this.tblTablaPaginas.repaint();
                this.tblMemoriaRam.repaint();
                this.tblMemoriaVirtual.repaint();
            }
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
    }

    public void saveProcess(String name, int size){
        if(size < 4000000 && this.memory > 0){
            //resetTable((DefaultTableModel) jTblMemoria.getModel());
            Proceso p = new Proceso(name, this.pId, size, this.quantum);
            this.pId += 1;
            this.nProcess += 1;
            boolean flagAssign = true;

            float partitions = (float)p.getSize()/200000;
            System.out.println(partitions);

            if(partitions <= 1){
                partitions = 1;
            }else if(partitions - (int)partitions > 0.00){
                partitions = (int)partitions+1;
            }
            p.setPaginas((int) partitions);
            this.procesos.add(p);
            
            int pageSize = p.getSize();
            for(int i=0; i<(int)partitions; i++){
                Paginas page = null;
                if(pageSize > 200000){
                    page = new Paginas(p,i+1,200000);
                }else{
                    page = new Paginas(p,i+1, pageSize);
                }
                this.paginas.add(page);
                pageSize -= 200000;
                if(this.memory > 0){
                    this.memory -= 200000;
                    addMemoryRAM(page, getRandomNumberUsingNextInt(0, 12));
                }else{
                    addMemoryVirtual(page,getRandomNumberUsingNextInt(0, 9));
                }
            }


            setTableProcesos((DefaultTableModel) this.tblProcesos.getModel());
            setTablePaginas((DefaultTableModel) this.tblPaginas.getModel());
            setTableTablaPaginas((DefaultTableModel) this.tblTablaPaginas.getModel());
            setTableMemoriaRam((DefaultTableModel) this.tblMemoriaRam.getModel());
            setTableMemoriaVirtual((DefaultTableModel) this.tblMemoriaVirtual.getModel());
            this.tblProcesos.repaint();
            this.tblPaginas.repaint();
            this.tblTablaPaginas.repaint();
            this.tblMemoriaRam.repaint();
            this.tblMemoriaVirtual.repaint();

        }else{
            JOptionPane.showMessageDialog(null, "EL PROCESO EXCEDE EL NUMERO DE MEMORIA 4000000 O NUMERO DE PROCESO EXCEDIDO.");
        }
    }

    public void addMemoryRAM(Paginas page, int pos){
        if(this.memoriaRam[pos] == null){
            page.setMarco(pos+1);
            page.getProceso().setPaginasEjecucion(page.getProceso().getPaginasEjecucion()+1);
            this.memoriaRam[pos] = page;
            page.start();
        }else{
            boolean flag = true;
            for(int i=0; i<13; i++){
                if(this.memoriaRam[i] == null && flag){
                    addMemoryRAM(page, i);
                    flag = false;
                }
            }
        }
    }

    public void addMemoryVirtual(Paginas page, int pos){
        if(this.memoriaVirtual[pos] == null){
            page.setMarco(pos+1);
            page.getProceso().setPaginasVirtuales(page.getProceso().getPaginasVirtuales()+1);
            this.memoriaVirtual[pos] = page;
        }else{
            boolean flag = true;
            for(int i=0; i<10; i++){
                if(this.memoriaVirtual[i] == null && flag){
                    addMemoryVirtual(page, i);
                    flag = false;
                }
            }
        }
    }



    public int getnProcess() {
        return nProcess;
    }

    public void setnProcess(int nProcess) {
        this.nProcess = nProcess;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public Paginas[] getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(Paginas[] memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public ArrayList<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public Paginas[] getMemoriaVirtual() {
        return memoriaVirtual;
    }

    public void setMemoriaVirtual(Paginas[] memoriaVirtual) {
        this.memoriaVirtual = memoriaVirtual;
    }

    public ArrayList<Paginas> getPaginas() {
        return paginas;
    }

    public void setPaginas(ArrayList<Paginas> paginas) {
        this.paginas = paginas;
    }

}
