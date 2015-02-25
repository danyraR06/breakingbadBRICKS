
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
    private static final int WIDTH = 1000;    //Ancho del JFrame
    private static final int HEIGHT = 600;    //Alto del JFrame
    private Base maiBarrilla;   //variable de la barrita 
    private Base maiFire;   //variable del proyectil
    private Base maiAnfetamina; // bloque
    private LinkedList <Base> lklAnfetaminas;   //lista de las anfetaminas
    
    private Image dbImage;   // Imagen a proyectar en Applet	
    private Image imaOver;  //imagen para proyectar al terminar el juego 
    private Graphics dbg;	// Objeto grafico
    
    
    public JuegoBreaking() {
        
        iDireccion = 0;
        
        iContAnf = 0;
        
        bolPause = false;
        
        bolEnd = false;
        
        URL urlImagenBarrita = this.getClass().getResource("barrita.jpg");
        
        // se crea el objeto para principal de la barrita 
	maiBarrilla = new Base (0, 0, WIDTH / iMAXANCHO,
                HEIGHT / iMAXALTO,
                Toolkit.getDefaultToolkit().getImage(urlImagenBarrita));

        // se posiciona a principal en el centro del applet
        maiBarrilla.setX(WIDTH / 2 - maiBarrilla.getAncho() / 2);
        maiBarrilla.setY(HEIGHT - maiBarrilla.getAlto());
        
        // se crea el objeto para las anfetamintas 
        int iPosX = (iMAXANCHO - 1) * WIDTH / iMAXANCHO;
        int iPosY = (iMAXALTO - 1) * HEIGHT / iMAXALTO;  
        
        //creo la lista de anfetaminas
        lklAnfetaminas = new LinkedList();
        
        for (int iI = 0; iI < 15; iI ++) {
            //la posición de x será un número aleatorio con un int negativo para que el juanillo
            //entre desde fuera del applet
            iPosX = (int) (Math.random() * (WIDTH));  
            //la posición de y será un número aleatorio 
            iPosY = (int) (0);   
            
            //se crea el url de la imagen de la anfetamina
            URL urlImagenJuanillo = this.getClass().getResource("blueMeth.jpg");
            // se crea el objeto anfetamina
            maiAnfetamina = new Base(iPosX,iPosY, WIDTH / iMAXANCHO,
                HEIGHT / iMAXALTO,
                Toolkit.getDefaultToolkit().getImage(urlImagenJuanillo));
            
            //agrego los fantasmas a la lista que estaba vacía
            lklAnfetaminas.add(maiAnfetamina);
        }
       
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
        for (Base basAnfetaminas : lklAnfetaminas) {
            
            //checo la colision entre nena y juanitos
            if (maiBarrilla.intersecta(basAnfetaminas)) {
                   
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
        URL urlImagenFondo = this.getClass().getResource("breakingBadBackground.jpg");
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
            if (maiBarrilla != null && lklAnfetaminas != null){
                //Dibuja la imagen de principal en el Applet
                    maiBarrilla.paint(graDibujo, this);
                    for (Base basAnfetaminas : lklAnfetaminas) {
                    //Dibuja la imagen de LOS fantasmitas en el Applet
                        basAnfetaminas.paint(graDibujo, this);
                    }
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
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha arriba
            iDireccion = 2;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha abajo
	    iDireccion = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha arriba
            iDireccion = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha abajo
	    iDireccion = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){  //si la boleana de esc falsa
            bolEnd = !bolEnd;
        }else if(e.getKeyCode() == KeyEvent.VK_P){  //si la boleana de pausa es falsa
            if (bolPause)
                bolPause = false;
            else
                bolPause = true;      
            }
        }
    }
