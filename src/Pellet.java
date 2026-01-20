import java.awt.Color;
import java.awt.Graphics;

public class Pellet {
	private int x,y;
	
	
	public Pellet(int xIn, int yIn) {
		x = xIn;
		y = yIn;
	}
	
	public void render(Graphics g) {
		int renderX = x * 20 + 400;
		int renderY = y * 20 + 400;
		
		g.setColor(Color.red);
		g.fillRect(renderX - 10, renderY - 10, 20, 20);
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
}
