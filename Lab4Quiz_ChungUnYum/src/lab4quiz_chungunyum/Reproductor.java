/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4quiz_chungunyum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author chung
 */
public class Reproductor {
    private Player player;
    public String direccion;
    private Thread hiloReproducir;
    private boolean pausado;
    private boolean parado;


    public boolean isParado() {
        return parado;
    }
    
    public Reproductor(String direccion){
        this.direccion = direccion;
        this.pausado = false;
        this.parado = false;
    }
    
    public void play() throws JavaLayerException, FileNotFoundException{
        if (hiloReproducir != null && hiloReproducir.isAlive() && !pausado && !parado) {
            return;
        }
        
        if (pausado) {
            pausado = false;
        }
        
        parado = false;
        hiloReproducir = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(direccion);
                player = new Player(fis);
                player.play();
                parado = true;
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        });
        hiloReproducir.start();
    }
    
    public void pause(){
        if (player != null && !pausado && !parado) {
            player.close();
            pausado = true;
        }
    }
    
    public void stop() {
         if (player != null) {
            player.close(); 
            if (hiloReproducir != null) {
                hiloReproducir.interrupt(); 
            }
            pausado = false;
            parado = true;
        }
    }
    
    
    
    
}
