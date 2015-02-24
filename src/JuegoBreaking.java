
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danyrmz
 */
public class JuegoBreaking {
    private int iPosX;  //posición de x
    private int iPosY;   //posición de y
    private int iContAnf;   //contador de anfetaminas
    private int iDireccion;  //direccion de la pelotilla o fuego 
    private boolean bolPause;   //boleana para pausar
    private boolean bolEnd;   //boleana para terminar el juego
    private static final int iAltoJ = 1000;   //alto del jframe
    private static final int iAnchoJ = 800;   //ancho del jframe
    private Main maiBarrilla;   //variable de la barrita 
    private Main maiFire;   //variable del proyectil
    private LinkedList <Main> lklAnfetaminas;   //lista de las anfetaminas
    
    private Image dbImage;   // Imagen a proyectar en Applet	
    private Image imaOver;  //imagen para proyectar al terminar el juego 
    private Graphics dbg;	// Objeto grafico
    
}
