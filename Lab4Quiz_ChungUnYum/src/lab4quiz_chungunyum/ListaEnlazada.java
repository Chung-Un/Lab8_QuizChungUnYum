/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4quiz_chungunyum;

import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author chung
 */
public class ListaEnlazada {
    Nodo nodoCabeza;
    
    public ListaEnlazada(){
        this.nodoCabeza = null;
    }
    
    public void agregarNodo(Cancion cancion){ 
        Nodo nuevoNodo = new Nodo(cancion);
        if (nodoCabeza ==null){
            nodoCabeza = nuevoNodo;
        }
        else{
            Nodo nodoActual = nodoCabeza;
                while(nodoActual.getSiguiente()!=null){
                    nodoActual = nodoActual.getSiguiente();
                }
                nodoActual.enlazarSiguiente(nuevoNodo);
        }
    }
    
    public void borrarNodo(Cancion cancion){
        if(nodoCabeza!=null && nodoCabeza.cancion.equals(cancion)){
            nodoCabeza = nodoCabeza.siguiente; 
        }
        else{
            Nodo nodoActual = nodoCabeza;
            while( nodoActual!=null && nodoActual.getSiguiente() != null){
                if(nodoActual.getSiguiente().getCancion().equals(cancion)){
                    nodoActual.enlazarSiguiente(nodoActual.getSiguiente().getSiguiente());
                    return;
                }
                nodoActual = nodoActual.getSiguiente();
            }
            
        }}
    
    public Nodo getNodo(String titulo){
        Nodo nodoActual = nodoCabeza;
        while(nodoActual!=null){
            if(nodoActual.getCancion().getTitulo().equals(titulo)){
                return nodoActual;
            }
        }
        return null;
    }
   
}
