/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4quiz_chungunyum;

/**
 *
 * @author chung
 */
public class Nodo {
    Cancion cancion;
    Nodo siguiente; 
    
    public Nodo (Cancion cancion){
        this.cancion = cancion;
        siguiente = null; 
    }
    
    public void enlazarSiguiente(Nodo nodoSiguiente){
        this.siguiente = nodoSiguiente;
    }
    
    public Nodo getSiguiente(){
        return siguiente;
    }
    
    public Cancion getCancion(){
        return cancion;
    }
    
}
