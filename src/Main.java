import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.FontMetrics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Screen extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int maxFPS = 30;
	public static long prevTimeInMillis = 0, timeInMillis;
	public static int frames = 0, currentFPS = 0;
	public static Font scoreFont = new Font("Silom", Font.BOLD, 100);
	public static Font titleFont = new Font("Silom", Font.BOLD, 60);
	public static Font highscoreFont = new Font("Silom", Font.BOLD, 30);
	public static boolean gLoaded = false;
	
	
	public void paintComponent(Graphics g) {
		
		if (!gLoaded) {
			g.setFont(scoreFont);
			g.setColor(Color.white);
			g.drawString(String.valueOf(Main.snake.getScore()), 10,100);
			gLoaded = true;
		}
		
		timeInMillis = System.currentTimeMillis();
		frames++;
		
		if (timeInMillis - prevTimeInMillis >= 1000) {
			prevTimeInMillis = System.currentTimeMillis();
			currentFPS = frames;
			frames = 0;
		}
		
		try {
			Thread.sleep(1000 / (maxFPS + 10));
		} catch (InterruptedException e) {e.printStackTrace();}
		
		if (Main.screen.equals("menu")) {
			setBackground(Color.black);
			g.setColor(Color.white);
			drawCenteredString(g, "Press [ENTER] To Start", new Rectangle(800,500), titleFont);
			drawCenteredString(g, "Highscore: " + String.valueOf(Main.snake.getHighscore()), new Rectangle(800,600), highscoreFont);
		}
		if (Main.screen.equals("game")) {
			setBackground(Color.black);
			
			if (Main.snake.getScore() > 0) {
				g.setFont(scoreFont);
				g.setColor(Color.white);
				drawCenteredString(g, String.valueOf(Main.snake.getScore()), new Rectangle(800,800), scoreFont);
			}
			
			for (Pellet pellet : Main.pellets) {
				if (Main.canRenderPellets) {
					pellet.render(g);
				}
			}
			
			Main.snake.render(g);
		}
		
		repaint();
	}
	
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
}
public class Main {
	public static JFrame frame = new JFrame();
	public static Screen scr = new Screen();
	public static Keyboard keys = new Keyboard();
	public static Mouse mouse = new Mouse(false);
	
	public static Snake snake = new Snake();
	public static ArrayList<Pellet> pellets = new ArrayList<Pellet>();
	
	public static int tps = 30;
	public static boolean running = true, loaded = false;;
	public static boolean canRenderPellets = true;
	public static String screen = "menu";
	
	public static void init() {
		frame.getContentPane().add(scr);
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Snake");
		frame.addKeyListener(keys);
		
		canRenderPellets = false;
		addPellet();
		canRenderPellets = true;
	}
	public static void run() {
		long lastTime = System.nanoTime(); //Init time var
	    final double ns = 1000000000.0 / tps; //Control ticks per second
	    double delta = 0;
	    while(running){ //Game loop boolean
	        long now = System.nanoTime(); //Far for now time
	        delta += (now - lastTime) / ns; //While in running loop, wait until two time vars are equal
	        lastTime = now; //Reset
	        while(delta >= 1){ //Wait until time is less than one
	        	mainLoop(); //Main game function
	            delta--; //Remove
	            }
	        } 

	    System.exit(0); //Once done with game loop, shut everything down
	}
	public static void main(String[] args) {
		System.out.println("Passed");
		init();
		run();
	}
	
	public static void addPellet() {
		int toX = ThreadLocalRandom.current().nextInt(-19,19);
		int toY = ThreadLocalRandom.current().nextInt(-19,19);
		
		pellets.add(new Pellet(toX,toY));
	}
	
	public static void mainLoop() {
		if (keys.ESCAPETYPED()) {
			running = false;
		}
		
		if (screen.equals("menu")) {
			loaded = false;
			snake.setDead(false);
			snake.setDirection('l');
			
			if (keys.ENTERTYPED()) {
				screen = "game";
			}
		}
		if (screen.equals("game")) {
			if (!loaded) {
				snake.setPos(0,0);
				snake.setLength(3);
				snake.setInitCtr();
			}
			loaded = true;
			
			snake.update(keys, pellets, canRenderPellets);
			if (snake.getDead()) {
				screen = "menu";
			}
		}
		
	}
}
