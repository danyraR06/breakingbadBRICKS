
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
    private int iPosXBol;  //posición de x de la bolita
    private int iPosYBol;   //posición de y de la bolita
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
    private int iMovBol; //direccion bolita
    private boolean bCorre; //booleana para iniciar
    
    
    public JuegoBreaking() {
        
        iDireccion = 0;
        
        iContAnf = 0;
        
        bolPause = false;
        
        bolEnd = false;
        
        bCorre = false;
        
        iMovBol = 1;
       
        
        URL urlImagenBarrita = this.getClass().getResource("barrita.png");
        
        // se crea el objeto para principal de la barrita 
	maiBarrilla = new Base (0, 0, (WIDTH / iMAXANCHO) + 80,
                (HEIGHT / iMAXALTO) - 30,
                Toolkit.getDefaultToolkit().getImage(urlImagenBarrita));

        // se posiciona a principal en el centro del applet
        maiBarrilla.setX(WIDTH / 2 - maiBarrilla.getAncho() / 2);
        maiBarrilla.setY(HEIGHT - maiBarrilla.getAlto());
        
        int posXBol = (WIDTH / 2 - maiBarrilla.getAncho() / 2);
        int posYBol = (HEIGHT - maiBarrilla.getAlto());
        
        URL urlImagenBolita = this.getClass().getResource("gomez.png");
        
        maiFire = new Base (posXBol, posYBol, WIDTH / iMAXANCHO,
                HEIGHT / iMAXALTO,
                Toolkit.getDefaultToolkit().getImage(urlImagenBolita));
        
        
        // se crea el objeto para las anfetamintas 
        int iPosX = 30;
        int iPosY = 40;
        
        //creo la lista de anfetaminas
        lklAnfetaminas = new LinkedList();
        
        for (int iI = 0; iI < 11; iI ++) {
            for (int iJ = 0; iJ < 3; iJ++){
                URL urlImagenMeth = this.getClass().getResource("Meth.png");
            // se crea el objeto anfetamina
            maiAnfetamina = new Base(iPosX,iPosY, (WIDTH / iMAXANCHO) - 40,
            (HEIGHT / iMAXALTO)-40,
                Toolkit.getDefaultToolkit().getImage(urlImagenMeth));
                lklAnfetaminas.add(maiAnfetamina);
                iPosY = iPosY+80;
            }
            iPosX = iPosX+80;
            iPosY = 40;
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
                maiBarrilla.setX(maiBarrilla.getX() - 4);
                if (!bCorre) {
                    maiFire.setX(maiFire.getX()- 4);
                }
                break;
            }
            case 2: {    //se mueve hacia la derecha
                maiBarrilla.setX(maiBarrilla.getX() + 4);
                if (!bCorre) {
                    maiFire.setX(maiFire.getX()+ 4);
                }
                break;
            }
            case 3: {// se deja de mover
                maiBarrilla.setX(maiBarrilla.getX());
                if (!bCorre) {
                    maiFire.setX(maiFire.getX());
                }
                break;
            }
        }
        if (bCorre) {
            switch(iMovBol){
                case 1: { // si se mueve hacia arriba 
                    maiFire.setX(maiFire.getX()+ 3);
                    maiFire.setY(maiFire.getY()- 3);
                    break;    	
                }     
                case 2: { // si se mueve hacia abajo y sale de la ventana
                    // del cuadrante 3             
                        maiFire.setX(maiFire.getX()-3);
                        maiFire.setY(maiFire.getY()+ 3);
                    // se queda en su lugar sin salirse del applet
  
                break;    	
                } 
                case 3: {// si se mueve hacia abajo cuadrante 4
                    maiFire.setY(maiFire.getY()+ 3);
                    maiFire.setX(maiFire.getX()+ 3);
                    
                    break;
                }    
                case 4: { // si se hacia arriba cuadrante 2
                        maiFire.setX(maiFire.getX()- 3);
                        maiFire.setY(maiFire.getY()- 3);                  
                    break;    	
                }
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
        if(maiFire.getY() < 0) { // y esta pasando el limite
                    
            // se queda en su lugar sin salirse del applet                  
            if(maiFire.getX() <= (getWidth()/2)) {
                iMovBol = 3;                     
            }
            else
            {
                iMovBol = 2;
            } 
        }
       // si se mueve hacia abajo
        // y se esta saliendo del applet
        else if(maiFire.getY() + maiFire.getAlto() > getHeight()) {
            // se queda en su lugar sin salirse del applet
            bCorre = false;
            posInicial();               
        }

        else if(maiFire.getX() < 0) { // y se sale del applet
            // se queda en su lugar sin salirse del applet
            if(iMovBol == 2){                    
                 iMovBol = 3;
            }
            else if(iMovBol == 3 ){  
                iMovBol = 2;
            }
            else if(iMovBol == 1) {
                iMovBol = 4;
            }
            else if(iMovBol == 4){
                iMovBol = 1;
            }


        }

        // si se esta saliendo del applet
        else if(maiFire.getX() + maiFire.getAncho() > getWidth()) { 
            // se queda en su lugar sin salirse del applet
            if(iMovBol== 2){
                 iMovBol = 3;
            }
            else if(iMovBol == 3 ){
                iMovBol = 2;
            }
            else if(iMovBol == 1) {
                iMovBol = 4;
            }
            else if(iMovBol == 4){
                iMovBol = 1;
            }
        }
        else if(maiFire.intersecta(maiBarrilla)){

            if(maiFire.getX() <= (maiBarrilla.getX()+ (maiBarrilla.getAncho()/2))){

                iMovBol = 4;
            }
            else
            {  
                iMovBol=1;
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
                    maiFire.paint(graDibujo, this);
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
    
    public void posInicial() {
        // indica las nuevas posiciones de la galleta
        maiBarrilla.setX((getWidth()/2) - (maiBarrilla.getAncho()/2));
        maiBarrilla.setY((getHeight())- (maiBarrilla.getAlto()));
        maiFire.setX((getWidth()/2)- (maiFire.getAncho()/2));
        maiFire.setY((getHeight()-maiBarrilla.getAlto())-(maiFire.getAlto()));
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
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            bCorre = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha arriba
            iDireccion = 3;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha abajo
	    iDireccion = 3;
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
