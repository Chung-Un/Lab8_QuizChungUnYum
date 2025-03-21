/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4quiz_chungunyum;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author chung
 */
public class GUI {
    private JFrame frame;
    private JList<Cancion> cancionesLista;
    private DefaultListModel<Cancion> modeloLista;
    private ListaEnlazada listaEnlazada;
    private Cancion cancion;
    private JTextArea areaTexto;
    private JPanel panelInfo;
    private JLabel labelImg;
    private Thread hiloReproducir,hiloPausar,hiloSiguiente;
    private Reproductor reproductor =null;
    
    public GUI(){
        listaEnlazada = new ListaEnlazada();
        frame = new JFrame("Spotify");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,400);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new BorderLayout());
        panelInfo.setPreferredSize(new Dimension(200,400));
        
        labelImg = new JLabel();
        labelImg.setHorizontalAlignment(JLabel.CENTER);
        panelInfo.add(labelImg,BorderLayout.NORTH);
        
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        panelInfo.add(areaTexto, BorderLayout.CENTER);
        
        frame.add(panelInfo, BorderLayout.EAST);
        
        modeloLista = new DefaultListModel<>();
        cancionesLista = new JList<>(modeloLista);
        
        JScrollPane scrollPaneCanciones  = new JScrollPane(cancionesLista);
        frame.add(scrollPaneCanciones,BorderLayout.CENTER);
        
        cancionesLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    Cancion cancionSeleccionada = cancionesLista.getSelectedValue();
                    if(cancionSeleccionada!=null){
                        mostrarInfo(cancionSeleccionada);
                    }
                }
            }
        });
        
        JButton btnAgregar = new JButton("Agregar cancion");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try{
                String titulo = JOptionPane.showInputDialog(null, "Ingrese el titulo de su cancion: ", 
                "Titulo", JOptionPane.QUESTION_MESSAGE);
                
                String artista = JOptionPane.showInputDialog(null, "Ingrese el nombre del artista: " ,
                "Artista", JOptionPane.QUESTION_MESSAGE);
                
                String duracionString = JOptionPane.showInputDialog(null, "Ingrese la duracion de la cancion (segundos): ",
                "Duracion" , JOptionPane.QUESTION_MESSAGE);
                int duracion = Integer.parseInt(duracionString);
                
                String genero = JOptionPane.showInputDialog(null, "Ingrese el genero de la cancion: ", 
                "Genero", JOptionPane.QUESTION_MESSAGE);
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Imagen de portada");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagen","jpg","png","jpeg","gif"));
                int imagenSeleccionada = fileChooser.showOpenDialog(null);
                
                if(imagenSeleccionada == JFileChooser.APPROVE_OPTION){
                    File fileSeleccionada = fileChooser.getSelectedFile();
                    ImageIcon icon = new ImageIcon(fileSeleccionada.getAbsolutePath());
                    
                    JFileChooser fileChooserMP3 = new JFileChooser();
                    fileChooserMP3.setDialogTitle("Archivo mp3 de la cancion");
                    fileChooserMP3.setAcceptAllFileFilterUsed(false);
                    fileChooserMP3.addChoosableFileFilter(new FileNameExtensionFilter("Canciones", "mp3"));

                    int cancionSubida = fileChooserMP3.showOpenDialog(null);
                    if(cancionSubida == JFileChooser.APPROVE_OPTION){
                        fileSeleccionada = fileChooserMP3.getSelectedFile();
                        cancion = new Cancion(titulo,artista,icon,genero,duracion,fileSeleccionada.getAbsolutePath());
                        listaEnlazada.agregarNodo(cancion);
                        modeloLista.addElement(cancion);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Por favor eliga un archivo mp3", "Error", JOptionPane.ERROR_MESSAGE);
                    } 
                    
                    
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor eliga un archivo png, jpg, jpeg o gif", "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
                
                
            }  catch (InputMismatchException x){
                   JOptionPane.showMessageDialog(null, "Escriba un dato valido", "Error", JOptionPane.ERROR_MESSAGE);
                } }
                
        });
        
        JButton btnBorrar = new JButton("Borrar cancion");
        btnBorrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cancion = cancionesLista.getSelectedValue();
                if(cancion!=null){
                    listaEnlazada.borrarNodo(cancion);
                    modeloLista.removeElement(cancion);
                    areaTexto.setText("");
                    if(reproductor!=null){
                        reproductor.stop();
                    }
                }
            }
        });
        
        JButton btnReproducir = new JButton("Reproducir");
        
        btnReproducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               cancion = cancionesLista.getSelectedValue();
            if(cancion != null){
                try {
                
                if(reproductor != null && !reproductor.isParado()){
                    reproductor.stop();
                }
                
                reproductor = new Reproductor(cancion.getDireccionArchivo());
                reproductor.play();
                
            } catch (JavaLayerException | FileNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        JButton btnPausar = new JButton("Pausar");
        
        btnPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reproductor != null && !reproductor.isParado()) {
                    reproductor.pause();
                }
            }
        });
        
        JButton btnSiguiente = new JButton("Siguiente cancion");
        
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               cancion = cancionesLista.getSelectedValue();
                if (cancion != null) {
                    Nodo nodoCancion = listaEnlazada.getNodo(cancion.getTitulo());
                    if (nodoCancion.getSiguiente() != null) {
                        try {
                            if (reproductor != null) {
                                reproductor.stop(); 
                            }
                            reproductor = new Reproductor(nodoCancion.getSiguiente().getCancion().getDireccionArchivo());
                            reproductor.play();
                        } catch (JavaLayerException | FileNotFoundException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe una cancion siguiente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        
        JButton btnSalir = new JButton("Salir");
        
        btnSalir.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        JPanel panel = new JPanel();
        panel.add(btnAgregar);
        panel.add(btnBorrar);
        panel.add(btnReproducir);
        panel.add(btnPausar);
        panel.add(btnSalir);
        panel.add(btnSiguiente);
        
        frame.add(new JScrollPane(cancionesLista), BorderLayout.CENTER);
        frame.add(panel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }
   
    
    private void mostrarInfo(Cancion cancionSeleccionada){
        String info = "Titulo: " +cancionSeleccionada.getTitulo() + "\nArtista: " + cancionSeleccionada.getArtista() +"\nGenero: " +
                cancionSeleccionada.getGenero() + "\nDuracion: " + cancionSeleccionada.getDuracion() + " segundos";
        areaTexto.setText(info);
        
        ImageIcon icon = cancionSeleccionada.getImg();
        if(icon!=null){
            Image img = icon.getImage();
            Image imgScaled = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon iconScaled = new ImageIcon(imgScaled);
            labelImg.setIcon(iconScaled);

        } else {
            labelImg.setIcon(null);
        }
    }
}

