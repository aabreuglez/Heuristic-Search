/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */

package logica;

import java.util.ArrayList;


public class Trayectoria implements Comparable<Trayectoria> {
    private ArrayList<Nodo> nodos_;
    private int coste_;
    
    public Trayectoria(Nodo nodo, int coste){
        this.nodos_ = new ArrayList<>();
        this.nodos_.add(nodo);
        this.coste_ = coste;
    }
    
    public void set_coste(int coste){
        this.coste_ = coste;
    }
    
    public int get_coste(){
        return this.coste_;
    }
    
    public ArrayList<Nodo> get_nodos() {
        return nodos_;
    }

    public void set_nodos(ArrayList<Nodo> nodos_) {
        this.nodos_ = nodos_;
    }
    
    public void add_nodo(Nodo nodo){
        this.nodos_.add(nodo);
    }
    
    public Nodo get_llegada(){
        if(this.nodos_.size()>0)
            return this.nodos_.get(nodos_.size()-1);
        else
            return null;
    }
    
    public Nodo get_salida(){
        if(this.nodos_.size()>0)
            return this.nodos_.get(0);
        else
            return null;
    }
    
    public boolean igual(Trayectoria trayectoria){
        if(nodos_.size()==trayectoria.get_nodos().size())
            for(int i=0; i<this.nodos_.size(); i++){
                if((this.nodos_.get(i).igual(trayectoria.get_nodos().get(i)))!=true)
                    return false;
            }
        else
            return false;
        
        return true;
    }
    
    public boolean contiene(Nodo nodo){
        for(int i=0; i<this.nodos_.size(); i++){
            if(((this.nodos_.get(i).igual(nodo))==true))
                return true;
        }
        return false;
    }
    
    public boolean similar(Trayectoria trayectoria){
        int cont=0;
        if(nodos_.size()==trayectoria.get_nodos().size())
            for(int i=0; i<nodos_.size(); i++){
                if(this.nodos_.get(i).igual(trayectoria.get_nodos().get(i)))
                    cont++;
            }
        else 
            return false;
        
        if(cont>=((nodos_.size()-1)/2))
            return true;
        else
            return false;
    }
    
    public void copiar(Trayectoria trayectoria){
        this.nodos_.removeAll(this.nodos_);
        for(int i=0; i<trayectoria.get_nodos().size(); i++)
            this.nodos_.add(trayectoria.nodos_.get(i));
        this.set_coste(trayectoria.get_coste());
    }

    
    @Override
    public int compareTo(Trayectoria trayectoria) {
        return (int) this.coste_ - (int) trayectoria.get_coste();
    }
    
}
