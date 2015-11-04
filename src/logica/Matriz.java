/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */

package logica;

import interfaz.ayuda.Datos;

public class Matriz {
    
    private int m_; //Columnas
    private int n_; //Filas
    private char M_[][]; //Matriz de char - libre, + obstaculo, * robot
    
    public Matriz() {
        this.m_=Integer.parseInt(Datos.tabla.get(Datos.COLUMNAS).toString());
        this.n_=Integer.parseInt(Datos.tabla.get(Datos.FILAS).toString());
        this.M_=new char[n_][m_];
        for(int i=0; i<n_;i++){
            for(int j=0; j<m_;j++){
                this.M_[i][j]='-';
            }
        }
    }
    
    public int get_cols(){
        return m_;
    }
    
    public int get_fils() {
        return n_;
    }
    
    public void set_cols(int m){
        this.m_=m;
    }
    
    public void set_fils(int n) {
        this.n_=n;
    }
      
    public char getMatrizItem(int i, int j){
        return M_[i][j];
    }
     
    public void setMatrizItem(int i, int j, char value){
        this.M_[i][j]=value;
    }
    
}
