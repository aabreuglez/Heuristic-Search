/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */

package logica;


class Nodo {
    private int x_;
    private int y_;
    
    public Nodo(int x, int y){
        this.x_ = x;
        this.y_ = y;
    }
    
    public int get_x(){
        return x_;
    }
    
    public int get_y(){
        return y_;
    }
    
    public boolean igual(Nodo other){
        return (this.x_==other.get_x())&&(this.y_==other.get_y());
    }
}
