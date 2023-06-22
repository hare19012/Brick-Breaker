import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class GuiILogika extends JPanel implements KeyListener, ActionListener 
{
	private boolean igraj = false;
	private int poeni = 0;
	
	private int ukupno = 48;
	
	private Timer vrijeme;
	private int delay=8;
	
	private int playerX = 310;
	
	private int loptaX = 120;
	private int loptaY = 350;
	private int LOPTAXdir = -1;
	private int LOPTAYdir = -2;
	
	private Podloga mapa;
	/**
	 * @author Student
	 *
	 */
	
	public GuiILogika()
	{		
		mapa = new Podloga(4, 12);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        vrijeme=new Timer(delay,this);
		vrijeme.start();
	}
	/**
	 *ovo crta pozadinu, border, skor bricck od kojeg se odbija loptica
	 *lopticu, zatim provjere kada smo pobjedili i kada smo izgubili
	 *
	 */
	public void paint(Graphics g)
	{    		
		/**
		 *pozadina 
		 *
		 */
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// d
		mapa.canvas((Graphics2D) g);
		
		/** borders
		 * 
		 */
		g.setColor(Color.red);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		/**skor
		 *  		
		 */
		g.setColor(Color.blue);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+poeni, 590,30);
		
		/**brick od kojeg se odbija loptica
		 * 
		 */
		g.setColor(Color.yellow);
		g.fillRect(playerX, 550, 100, 8);
		
		/**loptica
		 * 
		 */
		g.setColor(Color.yellow);
		g.fillOval(loptaX, loptaY, 20, 20);
	
		/**kada pobijedim igricu
		 * 
		 */
		if(ukupno <= 0)
		{
			 igraj = false;
             LOPTAXdir = 0;
     		 LOPTAYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Pobijedili ste", 260,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Kliknite enter za restartovanje", 230,350);  
		}
		
		/**kad izgubim igricu
		 * 
		 */
		if(loptaY > 570)
        {
			 igraj = false;
             LOPTAXdir = 0;
     		 LOPTAYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Kraj igre, Skor: "+poeni, 190,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Kliknite (Enter) za restartovanje", 230,350);        
        }
		
		g.dispose();
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				PomjeriDesno();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{        
			/**Ovdje sam mogao posebno napraviti u logici funkciju za ovu provjeru, to jeste mogao sam odvojiti gui i logiku
			 *
			 */
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				PomjeriLijevo();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!igraj)
			{
				igraj = true;
				loptaX = 120;
				loptaY = 350;
				LOPTAXdir = -1;
				LOPTAYdir = -2;
				playerX = 310;
				poeni = 0;
				ukupno = 21;
				mapa = new Podloga(3, 7);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void PomjeriDesno()
	{
		igraj = true;
		playerX+=20;	
	}
	
	public void PomjeriLijevo()
	{
		igraj = true;
		playerX-=20;	 	
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		vrijeme.start();
		if(igraj)
		{			
			if(new Rectangle(loptaX, loptaY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
			{
				LOPTAYdir = -LOPTAYdir;
				LOPTAXdir = -2;
			}
			else if(new Rectangle(loptaX, loptaY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
			{
				LOPTAYdir = -LOPTAYdir;
				LOPTAXdir = LOPTAXdir + 1;
			}
			else if(new Rectangle(loptaX, loptaY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
			{
				LOPTAYdir = -LOPTAYdir;
			}
			
			/**provjera da li je lopta udarila u ciglu
			 * 		
			 */
			A: for(int i = 0; i<mapa.mapa.length; i++)
			{
				for(int j =0; j<mapa.mapa[0].length; j++)
				{				
					if(mapa.mapa[i][j] > 0)
					{
						/**povecavanje skora
						 * 
						 */
						int brickX = j * mapa.sirina + 80;
						int brickY = i * mapa.visina + 50;
						int brickWidth = mapa.sirina;
						int brickHeight = mapa.visina;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(loptaX, loptaY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							mapa.Postavi(0, i, j);
							poeni+=5;	
							ukupno--;
							
							/**kada loptica udari desno ili lijevo od bricka
							 * 
							 */
							if(loptaX + 19 <= brickRect.x || loptaX + 1 >= brickRect.x + brickRect.width)	
							{
								LOPTAXdir = -LOPTAXdir;
							}
							/**kada loprica udari gore ili dole od bricka
							 * 
							 */
							else
							{
								LOPTAYdir = -LOPTAYdir;				
							}
							
							break A;
						}
					}
				}
			}
			
			loptaX += LOPTAXdir;
			loptaY += LOPTAYdir;
			
			if(loptaX < 0)
			{
				LOPTAXdir = -LOPTAXdir;
			}
			if(loptaY < 0)
			{
				LOPTAYdir = -LOPTAYdir;
			}
			if(loptaX > 670)
			{
				LOPTAXdir = -LOPTAXdir;
			}		
			
			repaint();		
		}
	}
}
