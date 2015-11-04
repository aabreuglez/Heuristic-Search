/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */

package logica;

import java.util.ArrayList;
import java.util.Collections;


public class Robot {

    private int x_;
    private int y_;
    //private int sensors_[];
    private Trayectoria sol_;
    
    public Robot(int x, int y){
        this.x_ = x;
        this.y_ = y;
        //this.sensors_ = new int[4];
        this.sol_=null;
    }
    
    public void set_x(int x) {
        this.x_ = x;
    }

    public void set_y(int y) {
        this.y_ = y;
    }

    public void set_posicion(int x, int y) {
        this.set_x(x);
        this.set_y(y);
    }

    public int get_x(){
        return this.x_;
    }

    public int get_y(){
        return this.y_;
    }
    
    public void set_sol(Trayectoria solucion){
        this.sol_=solucion;
    }
    
    public Trayectoria get_sol(){
        return this.sol_;
    }
    
    //La función de coste total estimado f*(n) para cada nodo n, viene dada por:
        // f*(n) = g(n) + h*(n)
        // g(n): coste acumulado desde la raíz hasta el nodo n
        // h*(n): estimación del coste h(n) desde el nodo n hasta el objetivo o coste restante
    
    public int coste_estimado(Trayectoria trayectoria, Nodo destino){
        return (int) (trayectoria.get_coste()) + (Manhattan(trayectoria.get_salida(), destino)); 
    }
    
    public int Manhattan(Nodo origen, Nodo destino){
        int xDiferencia = Math.abs(origen.get_x() - destino.get_x());
        int yDiferencia = Math.abs(origen.get_y() - destino.get_y());
        return (int) Math.sqrt(Math.pow(xDiferencia, 2) + Math.pow(yDiferencia, 2));
    }
    
    public boolean visitado(Nodo nodo, ArrayList<Trayectoria> abierta, ArrayList<Trayectoria> cerrada){
        for(int i=0; i<abierta.size(); i++)
            for(int j=0; j<abierta.get(i).get_nodos().size(); j++)
                if(nodo.igual(abierta.get(i).get_nodos().get(j)))
                    return true;
        
        for(int i=0; i<cerrada.size(); i++)
            for(int j=0; j<cerrada.get(i).get_nodos().size(); j++)
                if(nodo.igual(cerrada.get(i).get_nodos().get(j)))
                    return true;
        
        return false;
    }
    
    public void diversificar_nodo(ArrayList<Trayectoria> abierta, ArrayList<Trayectoria> cerrada, Trayectoria aux, Nodo destino, Matriz M){
        //2B2.Formar nuevas trayectorias a partir de la trayectoria eliminada de ABIERTA ramificando el último nodo de la misma.
        //2B3.Añadir las nuevas trayectorias a la lista ABIERTA, si existen.
        Nodo NodoN =new Nodo((aux.get_nodos().get(aux.get_nodos().size()-1)).get_x()-1,(aux.get_nodos().get(aux.get_nodos().size()-1)).get_y()); //Diversificamos el nodo  
        if(!visitado(NodoN, abierta, cerrada)) //Si no se ha visitado en otra trayectoria
            if((NodoN.get_x()<M.get_fils())&&(NodoN.get_y()<M.get_cols())&&(NodoN.get_x()>=0)&&(NodoN.get_y()>=0)){ //Comprobamos que este dentro del rango de la matriz       
                if(M.getMatrizItem(NodoN.get_x(),NodoN.get_y())!='+'){ //Si hay una casilla libre
                    Trayectoria NTrayectoria = new Trayectoria(NodoN, 0); //Creamos una nueva trayectoria
                    NTrayectoria.copiar(aux);   //Copia de la extraida de abierta
                    if(NTrayectoria.contiene(NodoN)==false){ //Si no contiene el nodo a insertar (para evitar bucles)
                        NTrayectoria.add_nodo(NodoN); //Añadimos el nodo a la trayectoria
                        NTrayectoria.set_coste(coste_estimado(NTrayectoria, destino)); //Le estimamos el coste
                        abierta.add(NTrayectoria); //Y se añade a abierta
                    }
                }
            }
        
        Nodo NodoE=new Nodo((aux.get_nodos().get(aux.get_nodos().size()-1)).get_x(),(aux.get_nodos().get(aux.get_nodos().size()-1)).get_y()+1);
        if(!visitado(NodoE, abierta, cerrada))
            if((NodoE.get_x()<M.get_fils())&&(NodoE.get_y()<M.get_cols())&&(NodoE.get_x()>=0)&&(NodoE.get_y()>=0)){
                if(M.getMatrizItem(NodoE.get_x(),NodoE.get_y())!='+'){
                    Trayectoria ETrayectoria = new Trayectoria(NodoE, 0);
                    ETrayectoria.copiar(aux);
                    if(ETrayectoria.contiene(NodoE)==false){
                        ETrayectoria.add_nodo(NodoE);
                        ETrayectoria.set_coste(coste_estimado(ETrayectoria, destino));
                        abierta.add(ETrayectoria);
                    }
                }
            }
        
        Nodo NodoS=new Nodo((aux.get_nodos().get(aux.get_nodos().size()-1)).get_x()+1,(aux.get_nodos().get(aux.get_nodos().size()-1)).get_y());
        if(!visitado(NodoS, abierta, cerrada))
            if((NodoS.get_x()<M.get_fils())&&(NodoS.get_y()<M.get_cols())&&(NodoS.get_x()>=0)&&(NodoS.get_y()>=0)){
                if(M.getMatrizItem(NodoS.get_x(),NodoS.get_y())!='+'){
                    Trayectoria STrayectoria = new Trayectoria(NodoS, 0);
                    STrayectoria.copiar(aux);
                    if(STrayectoria.contiene(NodoS)==false){
                        STrayectoria.add_nodo(NodoS);
                        STrayectoria.set_coste(coste_estimado(STrayectoria, destino));
                        abierta.add(STrayectoria);
                    }            
                }
            }
        
        Nodo NodoO=new Nodo((aux.get_nodos().get(aux.get_nodos().size()-1)).get_x(),(aux.get_nodos().get(aux.get_nodos().size()-1)).get_y()-1);               
        if(!visitado(NodoO, abierta, cerrada)) 
            if((NodoO.get_x()<M.get_fils())&&(NodoO.get_y()<M.get_cols())&&(NodoO.get_x()>=0)&&(NodoO.get_y()>=0)){ 
                if(M.getMatrizItem(NodoO.get_x(),NodoO.get_y())!='+'){ 
                    Trayectoria OTrayectoria = new Trayectoria(NodoO, 0); 
                    OTrayectoria.copiar(aux);   
                    if(OTrayectoria.contiene(NodoO)==false){    
                        OTrayectoria.add_nodo(NodoO);   
                        OTrayectoria.set_coste(coste_estimado(OTrayectoria, destino));  
                        abierta.add(OTrayectoria);
                    }        
                }
            }
    }
    
    public void algoritmo(Matriz Matriz, int dest_x, int dest_y){
        
        ArrayList<Trayectoria> abierta = new ArrayList<>(); //1. Formar una lista de trayectorias parciales, ABIERTA,
        Nodo NodoInicio = new Nodo(this.x_, this.y_);
        Nodo NodoFinal = new Nodo(dest_x, dest_y);
        Trayectoria Inicio = new Trayectoria(NodoInicio, 0);
        abierta.add(Inicio); //con una trayectoria inicial que comienza en el nodo raíz.
        ArrayList<Trayectoria> cerrada = new ArrayList<>(); //Formar una lista CERRADA, de trayectorias desechadas mínimas, inicialmente vacía.
        
            while(abierta.size()>0){ //2. Hasta que la lista ABIERTA esté vacía o se encuentre el objetivo, analizar su primera trayectoria:
                //if((((abierta.get(0)).get_nodos()).get(abierta.get(0).get_nodos().size()-1)).igual(NodoFinal)){ //2A.Si la trayectoria termina en el nodo objetivo, se finaliza el bucle.
                if(((abierta.get(0).get_nodos()).get(abierta.get(0).get_nodos().size()-1).get_x()==NodoFinal.get_x())&&((abierta.get(0).get_nodos()).get(abierta.get(0).get_nodos().size()-1).get_y()==NodoFinal.get_y()))
                    break;
                else{ //2B.Si la primera trayectoria no termina en el nodo objetivo:
                    Trayectoria aux=abierta.remove(0); //2B1.Eliminar la primera trayectoria de la lista ABIERTA 
                    cerrada.add(aux); //incluyendola en la lista CERRADA.
                    for(int i=0; i<cerrada.size(); i++){
                        if(cerrada.get(i).similar(aux)){ //En el caso de que ya exista una similar
                            if(cerrada.get(i).get_coste()>aux.get_coste()){ 
                                cerrada.remove(i);  //eliminar la de mayor coste.
                            }
                        }
                    }
                    
                    diversificar_nodo(abierta, cerrada, aux, NodoFinal, Matriz);
                 
                    Collections.sort(abierta); //2B4.Ordenar la lista ABIERTA en base al costo total estimado de cada una, colocando la de mínimo coste al inicio de la lista.
                    Trayectoria aux1, aux2;
                    for(int i=0; i<abierta.size(); i++){
                        for(int j=i+1; j<abierta.size()-1; j++){
                            if(abierta.get(i).get_llegada().igual(abierta.get(j).get_llegada())){ //2B5.Si dos o más trayectorias de ABIERTA acaban en un nodo común
                                if((abierta.get(i).get_coste())>=(abierta.get(j).get_coste())){ // borrar las mismas excepto la que posee mínimo coste entre ellas. 
                                    aux1=abierta.remove(i);
                                    for(int k=0; k<cerrada.size(); k++){
                                        if((abierta.get(j).similar(cerrada.get(k)))&&(abierta.get(j).get_coste()<cerrada.get(k).get_coste())){
                                            aux2=abierta.remove(j); //Eliminar esta última también si existe una similar con menor coste en la lista CERRADA. 
                                            cerrada.add(aux2);  //Al eliminar trayectorias de ABIERTA deben insertarse en CERRADA salvo que ya exista allí una similar de menor coste.
                                        }
                                        if((!aux1.similar(cerrada.get(k)))&&(aux1.get_coste()<cerrada.get(k).get_coste())){
                                            cerrada.add(aux1);  //Al eliminar trayectorias de ABIERTA deben insertarse en CERRADA salvo que ya exista allí una similar de menor coste.
                                        }
                                    }
                                }
                                else{
                                    aux1=abierta.remove(j);
                                    for(int k=0; k<cerrada.size(); k++){
                                        if((abierta.get(i).similar(cerrada.get(k)))&&(abierta.get(i).get_coste()<cerrada.get(k).get_coste())){
                                            aux2=abierta.remove(i); //Eliminar esta última también si existe una similar con menor coste en la lista CERRADA.
                                            cerrada.add(aux2);  //Al eliminar trayectorias de ABIERTA deben insertarse en CERRADA salvo que ya exista allí una similar de menor coste.
                                        }
                                        if((aux1.similar(cerrada.get(k)))&&(aux1.get_coste()<cerrada.get(k).get_coste())){
                                            cerrada.add(aux1);  //Al eliminar trayectorias de ABIERTA deben insertarse en CERRADA salvo que ya exista allí una similar de menor coste.
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }           
        
        //3. Si se alcanza el nodo objetivo, el problema tiene solución y se determina la trayectoria óptima, en caso contrario no existe solución.
        if(abierta.size()>0)
            set_sol(abierta.get(0));
    }
}
    