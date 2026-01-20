import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Snake {
	private int x,y,initLength;
	private char direction;
	private ArrayList<TailPiece> tail;
	private int initCtr = 0;
	private boolean dead;
	private boolean canRender;
	private int moveTick;
	
	private int score, highscore;
	
	public Snake() {
		x = 0;
		y = 0;
		direction='l';
		tail = new ArrayList<TailPiece>();
		initLength = 1;
		dead = false;
		canRender = true;
		moveTick = 3;
		score = 0;
		highscore = 0;
	}
	
	public void update(Keyboard keys, ArrayList<Pellet> pellets, boolean canRenderPellets) {
		moveTick--;
		
		if (initCtr < initLength) {
			initCtr++;
			addTailPiece();
		}
		
		boolean canMove = true;
		
		if (keys.A() || keys.LEFT()) {
			if (direction != 'r') {
				direction = 'l';
			}
		}
		if (keys.D() || keys.RIGHT()) {
			if (direction != 'l') {
				direction = 'r';
			}
		}
		if (keys.W() || keys.UP()) {
			if (direction != 'd') {
				direction = 'u';
			}
		}
		if (keys.S() || keys.DOWN()) {
			if (direction != 'u') {
				direction = 'd';
			}
		}
		
		if (moveTick == 0) {
			if (tail.size() > 0) {
				tail.add(0, new TailPiece(x,y));
				tail.remove(tail.size() - 1);
			}
		}
		
		if (direction == 'l') {
			for (TailPiece p : tail) {
				if (p.getX() == x - 1 && p.getY() == y) {
					dead = true;
				}
			}
			if (!dead) {
				if (moveTick == 0) {
					if (canMove) x--;
					if (x == -20) x = 19;
				}
			}
		}
		if (direction == 'r') {
			for (TailPiece p : tail) {
				if (p.getX() == x + 1 && p.getY() == y) {
					dead = true;
				}
			}
			if (!dead) {
				if (moveTick == 0) {
					if (canMove) x++;
					if (x == 20) x = -19;
				}
			}
		}
		if (direction == 'u') {
			for (TailPiece p : tail) {
				if (p.getX() == x && p.getY() == y - 1) {
					dead = true;
				}
			}
			if (!dead) {
				if (moveTick == 0) {
					if (canMove) y--;
					if (y == -20) y = 19;
				}
			}
		}
		if (direction == 'd') {
			for (TailPiece p : tail) {
				if (p.getX() == x && p.getY() == y + 1) {
					dead = true;
				}
			}
			if (!dead) {
				if (moveTick == 0) {
					if (canMove) y++;
					if (y == 20) y = -19;
				}
			}
		}

		if (dead) {
			highscore = score;
		}
		
		if (moveTick == 0) {
			for (int i = 0; i < pellets.size(); i++) {
				if (x == pellets.get(i).getX() && y == pellets.get(i).getY()) {
					addTailPiece();
					canRenderPellets = false;
					pellets.remove(i);
					addPellet(pellets);
					canRenderPellets = true;
				}
			}
		}

		score = tail.size() - 1;
		
		
		if (moveTick == 0) moveTick = 3;
		
	}
	
	public void render(Graphics g) {
		if (canRender) {
			int renderX = x * 20 + 400;
			int renderY = y * 20 + 400;
			
			g.setColor(Color.green);
			g.fillRect(renderX - 9, renderY - 9, 18, 18);
			for (int i = 0; i < tail.size() + 0; i++) {
				renderX = tail.get(i).getX() * 20 + 400;
				renderY = tail.get(i).getY() * 20 + 400;
				
				g.setColor(Color.green);
				g.fillRect(renderX - 9, renderY - 9, 18, 18);
			}
		}
		
	}
	
	public void addTailPiece() {
		canRender = false;
		if (tail.size() > 0) {
			tail.add(new TailPiece(tail.get(tail.size() - 1).getX(), tail.get(tail.size() - 1).getY()));
		}
		else {
			tail.add(new TailPiece(x,y));
		}
		canRender = true;
	}
	
	public static void addPellet(ArrayList<Pellet> pellets) {
		int toX = ThreadLocalRandom.current().nextInt(-19,19);
		int toY = ThreadLocalRandom.current().nextInt(-19,19);
		
		pellets.add(new Pellet(toX,toY));
	}
	
	public void setScore(int in) {score = in;}
	public void setPos(int xIn, int yIn) {
		x = xIn;
		y = yIn;
	}
	public void setLength(int in) {
		tail.clear();
		initLength = in;
	}
	public void setDead(boolean in) {dead = in;}
	public void setDirection(char in) {direction = in;}
	public void setInitCtr() {initCtr = 0;}

	public int getScore() {return score;}
	public boolean getDead() {return dead;}
	public int getHighscore() {return highscore;}
}
