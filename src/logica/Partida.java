/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */


package logica;

import interfaz.ayuda.Coordenadas;
import interfaz.ayuda.Datos;
import static java.lang.Math.abs;
import java.util.Random;
import javafx.collections.ObservableList;


public class Partida {
    
    Matriz tablero_;
    Robot bot_;
    int modo_;
    int dest_x, dest_y;
            
    public Partida() {
        tablero_=new Matriz();
        
        modo_=Integer.parseInt(Datos.tabla.get(Datos.MODO).toString());
        if(this.modo_==0){
            manual_obstaculo();
            bot_= new Robot((Integer.parseInt(Datos.tabla.get(Datos.CROBOT).toString())-1), (Integer.parseInt(Datos.tabla.get(Datos.FROBOT).toString()))-1);
            dest_x=Integer.parseInt(Datos.tabla.get(Datos.FDESTINO).toString())-1;
            dest_y=Integer.parseInt(Datos.tabla.get(Datos.CDESTINO).toString())-1;
        }
        else{
            Random rn = new Random();
            int n = this.tablero_.get_fils();
            int n2 = this.tablero_.get_cols();
            int randomNum = abs(rn.nextInt() % n);
            int randomNum2= abs(rn.nextInt() % n2);
            bot_=new Robot(randomNum,randomNum2);
            do{
                dest_x = abs(rn.nextInt() % n);
                dest_y= abs(rn.nextInt() % n2);
            }while((dest_x==randomNum)&&(dest_y==randomNum2));
            auto_obstaculo(Integer.parseInt(Datos.tabla.get(Datos.CARGA).toString()));
        }       
    }
    
    public Matriz get_tablero() {
        return tablero_;
    }

    public char getobjeto(int x, int y){
        return tablero_.getMatrizItem(x, y);
    }
    
    public Robot get_bot() {
        return bot_;
    }
    
    public void setModo_(int modo_) {
        this.modo_ = modo_;
    }

    public int getModo_() {
        return modo_;
    }
    
    public void actualizar_matriz(){
        tablero_.setMatrizItem(bot_.get_x(), bot_.get_y(), '*');
        tablero_.setMatrizItem(dest_x, dest_y, '.');
        if(bot_.get_sol()!=null)
            for(int i=1; i<bot_.get_sol().get_nodos().size()-1;i++)
                    tablero_.setMatrizItem(bot_.get_sol().get_nodos().get(i).get_x(), bot_.get_sol().get_nodos().get(i).get_y(), ',');
    }
    
    public void add_obstaculo(int x, int y){
        if((0<=x)&&(x<tablero_.get_fils())&&(0<=y)&&(y<tablero_.get_cols()))
            if(tablero_.getMatrizItem(x, y)=='-')
                this.tablero_.setMatrizItem(x, y, '+');
    }
    
    public void manual_obstaculo(){
        ObservableList coords=(ObservableList)(Datos.tabla.get(Datos.LISTA));
        for (Object it : coords)
            tablero_.setMatrizItem(((Coordenadas)it).getX()-1, ((Coordenadas)it).getY()-1, '+');
    }
    
    public void auto_obstaculo(int porcentaje){
        int carga=((porcentaje * ((tablero_.get_fils())*(tablero_.get_cols()))/ 100));
        Random rn = new Random();
        int n = this.tablero_.get_fils();
        int n2 = this.tablero_.get_cols();
        for(int i=0; i<carga; i++){
            int Rx = abs(rn.nextInt() % n);
            int Ry= abs(rn.nextInt() % n2);
            add_obstaculo(Rx, Ry);
        }
    }
            
    public int buscar_camino(){
        bot_.algoritmo(tablero_, dest_x, dest_y);
        if(bot_.get_sol()!=null){
            return bot_.get_sol().get_coste();
        }
        else
            return 0;
    }

    public void iniciar(){
        actualizar_matriz();
        buscar_camino();
        actualizar_matriz();
    }
}
