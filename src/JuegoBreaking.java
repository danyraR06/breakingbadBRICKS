
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danyrmz
 */
public class JuegoBreaking extends JFrame implements Runnable, KeyListener{
    private final int iMAXANCHO = 12; // maximo numero de personajes por ancho
    private final int iMAXALTO = 8;  // maxuimo numero de personajes por alto
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
    
    
    public JuegoBreaking() {
        
        iDireccion = 0;
        
        iContAnf = 0;
        
        bolPause = false;
        
        bolEnd = false;
        
        URL urlImagenBarrita = this.getClass().getResource("barrita");
        
        // se crea el objeto para principal de la barrita 
	maiBarrilla = new Main(0, 0, WIDTH / iMAXANCHO,
                HEIGHT / iMAXALTO,
                Toolkit.getDefaultToolkit().getImage(urlImagenBarrita));

        // se posiciona a principal  en la esquina superior izquierda del Applet 
        maiBarrilla.setX(WIDTH / 2 - maiBarrilla.getAncho() / 2);
        maiBarrilla.setY(HEIGHT - maiBarrilla.getAlto());
        
        // se crea el objeto para malo 
        int iPosX = (iMAXANCHO - 1) * WIDTH / iMAXANCHO;
        int iPosY = (iMAXALTO - 1) * HEIGHT / iMAXALTO;    
       
        addKeyListener(this);
        // Declaras un hilo
        Thread t = new Thread (this);
	// Empieza el hilo
	t.start ();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
