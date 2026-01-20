import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class TailPiece {
	private int x,y;
	private Color color;
	
	public TailPiece(int xIn, int yIn) {
		x = xIn;
		y = yIn;
		color = new Color(ThreadLocalRandom.current().nextInt(1,254),ThreadLocalRandom.current().nextInt(1,254),ThreadLocalRandom.current().nextInt(1,254));
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Color getColor() {return color;}

	public void setX(int in) {x = in;}
	public void setY(int in) {y = in;}
}
