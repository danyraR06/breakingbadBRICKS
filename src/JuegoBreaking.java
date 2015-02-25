
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

        // se posiciona a principal en el centro del applet
        maiBarrilla.setX(WIDTH / 2 - maiBarrilla.getAncho() / 2);
        maiBarrilla.setY(HEIGHT - maiBarrilla.getAlto());
        
        // se crea el objeto para las anfetamintas 
        int iPosX = (iMAXANCHO - 1) * WIDTH / iMAXANCHO;
        int iPosY = (iMAXALTO - 1) * HEIGHT / iMAXALTO;    
       
        addKeyListener(this);
        // Declaras un hilo
        Thread t = new Thread (this);
	// Empieza el hilo
	t.start ();
    }
    
    public void run () {
        while (!bolEnd) {
            if(!bolPause){
                actualiza();    //actualiza la posicion del raton.
                checaColision();    //checa colision del elefante y raton ademas de con el JFrane.
                repaint();    // Se actualiza el <code>JFrame</code> repintando el contenido. 
            }
            try	{
            // El thread se duerme.
                    Thread.sleep (20);
            }
                catch (InterruptedException ex)	{
                    System.out.println("Error en " + ex.toString());
            }
        }          
    }
    public void actualiza(){
        switch(iDireccion){  //en base a la direccion
            case 1: {    //se mueve hacia la izquierda
                maiBarrilla.setX(maiBarrilla.getX() - 2);
                break;
            }
            case 2: {    //se mueve hacia la derecha
                maiBarrilla.setX(maiBarrilla.getX() + 2);
                break;
            }
        }
    }
    
    public void checaColision(){
        switch(iDireccion){
            case 1: {
                if(maiBarrilla.getX() < 0) { // y se sale del applet
                    iDireccion = 0;       // se para
                }
                break;    	
            }    
            case 2: { // si se mueve hacia derecha 
                // si se esta saliendo del applet
                if(maiBarrilla.getX() + maiBarrilla.getAncho() > getWidth()) { 
                    iDireccion = 0;       // se para
                }
                break;  	
            }
        }
    }
    public void paint(Graphics g) {
        if (dbImage == null) {
	dbImage = createImage(this.getSize().width, this.getSize().height);
	dbg = dbImage.getGraphics ();
	}
        URL urlImagenFondo = this.getClass().getResource("breakingBadBackground.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        dbg.drawImage(imaImagenFondo, 0, 0, getWidth(), getHeight(), this);
		
	// Actualiza el Foreground.
	dbg.setColor(getForeground());
	paint1(dbg);
	// Dibuja la imagen actualizada
	g.drawImage(dbImage, 0, 0, this);
    }
    public void paint1(Graphics graDibujo) {
        // si la imagen ya se cargo
        if(!bolEnd){  //si el juego aun continúa
            if (maiBarrilla != null){
                //Dibuja la imagen de principal en el Applet
                    maiBarrilla.paint(graDibujo, this);
            }
            else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
            }
        }else {
                graDibujo.drawImage(imaOver,150,0,this); 
            }  
    }
    
    public static void main(String[] args) {
    	// TODO code application logic here
    	JuegoBreaking score = new JuegoBreaking();
    	score.setSize(WIDTH, HEIGHT);
    	score.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	score.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha arriba
            iDireccion = 1;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha abajo
	    iDireccion = 2;
        }else if(e.getKeyCode() == KeyEvent.VK_P){  //si la boleana de pausa es falsa
            if (bolPause)
                bolPause = false;
            else
                bolPause = true;      
            }
        }
    }
