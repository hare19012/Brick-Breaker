import java.awt.Color;

import javax.swing.JFrame;
public class IgrajBB {
	public IgrajBB() {}
	public void Igraj() {
		JFrame obj=new JFrame();
		GuiILogika gamePlay = new GuiILogika();
		obj.setSize(710,600);
		obj.setTitle("Brick Breaker");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
                obj.setVisible(true);
	}
}
