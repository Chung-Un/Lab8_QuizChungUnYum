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
    private String direccion;
    
    public Reproductor(String direccion){
        this.direccion = direccion;
    }
    
    public void play() throws JavaLayerException, FileNotFoundException{
            FileInputStream fis = new FileInputStream(direccion);
            player = new Player(fis);
            player.play();
        
    }
    
    public void stop(){
        if (player!= null){
            player.close();
        }
    }
    
}
