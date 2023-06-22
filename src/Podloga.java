import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Podloga 
{
	public int mapa[][];
	public int sirina;
	public int visina;
	/**
	 * @author Student
	 *
	 */
	
	public Podloga (int red, int kolona)
	{
		mapa = new int[red][kolona];		
		for(int i = 0; i<mapa.length; i++)
		{
			for(int j =0; j<mapa[0].length; j++)
			{
				mapa[i][j] = 1;
			}			
		}
		
		sirina = 540/kolona;
		visina = 150/red;
	}	
	public void Postavi(int v, int r, int k)
	{
		mapa[r][k] = v;
	}
	/**
	 * crta podlogu
	 * 
	 */
	public void canvas(Graphics2D g)
	{
		for(int i = 0; i<mapa.length; i++)
		{
			for(int j =0; j<mapa[0].length; j++)
			{
				if(mapa[i][j] > 0)
				{
					g.setColor(Color.red);
					g.fillRect(j * sirina + 80, i * visina + 50, sirina, visina);
					
					/**prikaz cigli odvojeno
					 * 
					 */
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * sirina + 80, i * visina + 50, sirina, visina);				
				}
			}
		}
	}
	
	
}
