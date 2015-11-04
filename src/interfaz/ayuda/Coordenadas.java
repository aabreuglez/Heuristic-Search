/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */

package interfaz.ayuda;

public class Coordenadas {
       
    private int x_;
    private int y_;
    
    public Coordenadas() {}
    
    public Coordenadas(int x, int y) {
        this.x_ = x;
        this.y_ = y;
    }
    
    public int getX() {
        return x_;
    }

    public void setX(int x) {
        this.x_ = x;
    }

    public int getY() {
        return y_;
    }

    public void setY(int y) {
        this.y_ = y;
    }

    @Override
    public String toString() {
        return "{" + "x=" + x_ + ", y=" + y_ + '}';
    }
    
    public boolean igual (Coordenadas b){
            return (this.x_==b.x_)&&(this.y_==b.y_);
    }
}
