/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4quiz_chungunyum;

import javax.swing.ImageIcon;

/**
 *
 * @author chung
 */
public class Cancion {
    String titulo, artista, genero, direccionArchivo;
    ImageIcon img;
    int duracion;
    
    public Cancion(String titulo ,String artista, ImageIcon img, String genero, int duracion, String direccionArchivo){
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.img = img;
        this.duracion = duracion;
        this.direccionArchivo = direccionArchivo;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    public String getDireccionArchivo() {
        return direccionArchivo;
    }
    
    public String toString(){
        return titulo;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cancion cancion = (Cancion) obj;
        return titulo.equals(cancion.titulo); // Comparar por título
    }
}
