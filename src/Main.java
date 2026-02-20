import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JFrame;

import com.studiohartman.jamepad.*;

import Entities.Entity;
import Entities.Player;
import Objects.Block;
import System.Camera;
import System.Controls;
import System.FontLoader;
import System.ImageLoader;
import System.KeyHandler;
import System.MapLoader;
import System.MenuButton;
import System.MouseHandler;
import System.Sound;
import System.Sprite;

enum Menu {
	MAIN,
	PAUSE,
	GAMEPLAY
}

public class Main {
    public static JFrame frame = new JFrame();
    public static Canvas canvas = new Canvas(); 
    public static KeyHandler keys = new KeyHandler();
    public static MouseHandler mouse = new MouseHandler();
    public static Camera camera = new Camera(0,0);
    public static Random random = new Random();
    public static Menu menu = Menu.GAMEPLAY;
    public static Controls controls = new Controls();
    public static Font menuFont = FontLoader.loadFont("/fonts/KiwiSoda.ttf", 32f);
    public static BufferedImage playerSpritesheet = ImageLoader.loadImage("/sprites/player.png");
 // Add to your static fields in Main.java
    public static ControllerManager controllers = new ControllerManager();

    public static BufferedImage background1 = ImageLoader.loadImage("/sprites/country-platform-back.png");
    public static BufferedImage background2 = ImageLoader.loadImage("/sprites/country-platform-forest.png");
    public static BufferedImage background3 = ImageLoader.loadImage("/sprites/country-platform-tiles-example.png");
    public static float bgFactor = 0.2f;

    public static boolean running = true;
    public static final double TARGET_TPS = 60.0; 
    public static final double NS_PER_TICK = 1000000000.0 / TARGET_TPS;
 // --- FPS LIMITER SETTINGS ---
    public static boolean limitFPS = true; 
    // Automatically gets your monitor's refresh rate (e.g., 60, 144)
    public static int targetFPS = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    // ----------------------------
    
    public static int scrW = 600, scrH = 600;

    public static ArrayList<Entity> entities = new ArrayList<>();
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static ArrayList<Sprite> sprites = new ArrayList<>();
    public static MenuButton startButton = new MenuButton(10, 10, FontLoader.getTextDimensions(menuFont, "Start Game").width, menuFont.getSize(), 10, "Start Game");
    public static MenuButton exitButton = new MenuButton(10, 58, FontLoader.getTextDimensions(menuFont, "Exit Game").width, menuFont.getSize(), 10, "Exit Game");
    
    public static Sound jumpSound;
    public static Sound[] stepSounds = {null, null, null, null};
    public static float lastStepTime = 0, stepSoundInterval = 0.3f;
    
    public static boolean jumpedFromRun = false;

    // ==========================================
    // MAIN
    // ==========================================
    public static void main(String[] args) throws Exception {
        init();
        initJFrame();
        runGameLoop();
    }

    // ==========================================
    // INIT JFRAME
    // ==========================================
    public static void initJFrame() {
        frame.setSize(scrW, scrH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        canvas.setSize(scrW, scrH);
        canvas.setFocusable(true);
        canvas.addKeyListener(keys);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
    }
    
    // ==========================================
    // INIT
    // ==========================================
    public static void init() {
    	controllers.initSDLGamepad(); // Start Jamepad
    	
        entities.add(new Player(0,0,40,66,1));
        blocks = MapLoader.loadMap("/maps/testMap.map");
//        blocks.add(new Block(40, -80, 3000, 40, 2));
//        blocks.add(new Block(60, -40, 40, 40, 2));
        
        jumpSound = new Sound("/sounds/jumpWoosh.wav");
        stepSounds[0] = new Sound("/sounds/footsteps/footstep1.wav");
        stepSounds[1] = new Sound("/sounds/footsteps/footstep2.wav");
        stepSounds[2] = new Sound("/sounds/footsteps/footstep3.wav");
        stepSounds[3] = new Sound("/sounds/footsteps/footstep4.wav");
    }

    // ==========================================
    // RUN GAME LOOP
    // ==========================================
    public static void runGameLoop() {
        new Thread(() -> {
            long lastTime = System.nanoTime();
            long lastRenderTime = System.nanoTime(); // Track render time separately
            double delta = 0; 
            
            long timer = System.currentTimeMillis();
            int frames = 0;
            
            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / NS_PER_TICK;
                lastTime = now;

                boolean didTick = false;

                // 1. PHYSICS UPDATE (Still strictly 60 TPS)
                while (delta >= 1) {
                    tick((float) (1.0 / TARGET_TPS));
                    delta--;
                    didTick = true;
                }
                
                long nsPerFrame = 1000000000L / targetFPS;

                // 2. RENDER UPDATE
                boolean shouldRender = false;
                
                if (!limitFPS) {
                    // If uncapped, ALWAYS render
                    shouldRender = true; 
                } else {
                    // If capped, check if enough time has passed for the next frame
                    long renderDelta = now - lastRenderTime;
                    nsPerFrame = 1000000000L / targetFPS; 
                    
                    if (renderDelta >= nsPerFrame) {
                        shouldRender = true;
                    }
                }

                // 3. EXECUTE RENDER & SLEEP
                if (shouldRender) {
                    render();
                    lastRenderTime += nsPerFrame;
                    frames++;
                } else if (!didTick) {
                    // THE COIL WHINE SAVER
                    // Only sleep if we didn't tick and didn't render.
                    try {
                        Thread.sleep(1); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                // 4. PRINT FPS
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println("FPS: " + frames + " | Target: " + (limitFPS ? targetFPS : "Uncapped"));
                    frames = 0; 
                }
            }
            System.exit(0);
        }, "GameThread").start();
    }

    // ==========================================
    // TICK
    // ==========================================
    public static void tick(float dt) {
    	if (menu == Menu.MAIN) {
    		mainMenu();
    	}
    	else if (menu == Menu.GAMEPLAY) {
    		mainGame(dt);
    	}
        
        updateSystem();
    }

    // ==========================================
    // RENDER
    // ==========================================
    public static void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics g = null;
        try {
            g = bs.getDrawGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, scrW, scrH);
            if (menu == Menu.MAIN) drawMainMenu(g);
        	else if (menu == Menu.GAMEPLAY) drawGame(g);
        } finally {
            if (g != null) g.dispose();
        }

        if (!bs.contentsLost()) {
            bs.show();
        }
    }
    
    // ==========================================
    // DRAW GAME
    // ==========================================
    public static void drawGame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        
        for (Entity entity : entities) {
            sprites.add(entity.genSprite(scrW, scrH, camera));
        }
        for (Block block : blocks) {
            Sprite s = block.genSprite(scrW, scrH, camera);
            if (s != null) sprites.add(s);
        }
        sprites.add(new Sprite(0.0f, -background1.getWidth() - (camera.getX() * bgFactor),   -100 - (camera.getY() * bgFactor / 2), 1152, 672, background1));
        sprites.add(new Sprite(0.1f, -background2.getWidth() - (camera.getX() * bgFactor*2), -100 - (camera.getY() * bgFactor / 2)*2, 1152, 672, background2));
        sprites.add(new Sprite(0.2f, -background3.getWidth() - (camera.getX() * bgFactor*3), -100 - (camera.getY() * bgFactor / 2)*3, 1152, 672, background3));
        
        sprites.sort(Comparator.comparing(Sprite::getRenderLayer));
        
        for (Sprite sprite : sprites) {
            sprite.render(g2);
        }
        sprites.clear();
    }

    // ==========================================
    // DRAW MAIN MENU
    // ==========================================
    public static void drawMainMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(menuFont);
        startButton.render(g2);
        exitButton.render(g2);
    }

    // ==========================================
    // UPDATE SYSTEM
    // ==========================================
    public static void updateSystem() {
        if (canvas.getWidth() > 0) {
             scrW = canvas.getWidth();
             scrH = canvas.getHeight();
        }
    }
    
    // ==========================================
    // MAIN GAME LOGIC
    // ==========================================
    public static void mainGame(float dt) {
    	controllers.update();
        ControllerState curr = controllers.getState(0);
    	if (!controls.jump(keys, curr)) controls.jumpable = true;
    	if (keys.keys[KeyEvent.VK_ESCAPE]) {
			menu = Menu.MAIN;
		}
        playerLogic(dt, curr);
    	Player player = (Player)entities.get(0);
        
        if (lastStepTime >= stepSoundInterval && player.isGrounded() && player.getVx() != 0) {
        	lastStepTime = 0;
        	int stepSound = random.nextInt(1,stepSounds.length - 1);
        	stepSounds[stepSound].play();
        }
    }
    
    // ==========================================
    // MAIN MENU LOGIC
    // ==========================================
    public static void mainMenu() {
    	if (keys.keys[KeyEvent.VK_ENTER]) {
			menu = Menu.GAMEPLAY;
		}
    	if (exitButton.isHovering(mouse) && mouse.buttons[1]) {
    		running = false;
    	}
    	if (startButton.isHovering(mouse) && mouse.buttons[1]) {
    		menu = Menu.GAMEPLAY;
    	}
    }
    
    public static void playerLogic(float dt, ControllerState curr) {
    	Player player = (Player)entities.get(0);
        player.updateAnimation(dt);
        
        if (controls.moveRight(keys, curr)) {
    		if (!player.isGrounded()) player.setVx(player.getVx() + player.getAccelerationInAir());
    		else player.setVx(player.getVx() + player.getAcceleration());
        } else if (controls.moveLeft(keys, curr)) {
        	if (!player.isGrounded()) player.setVx(player.getVx() - player.getAccelerationInAir());
    		else player.setVx(player.getVx() - player.getAcceleration());
        } else {
            player.setVx(player.getVx() * 0.8f); 
            if (Math.abs(player.getVx()) < 0.1f) player.setVx(0);
        }
        
        
        float maxSpeed = player.isGrounded() ? player.getMaxSpeed() : player.getMaxSpeedInAir();
        if (player.getVx() > maxSpeed) player.setVx(maxSpeed);
        if (player.getVx() < -maxSpeed) player.setVx(-maxSpeed);

        if ((controls.jump(keys, curr)) && player.isGrounded() && controls.jumpable) {
        	if (jumpSound != null) jumpSound.play();
            player.setVy(player.getJumpPower()); 
            player.setGrounded(false);
            controls.jumpable = false;
        }

        player.setVy(player.getVy() - 0.5f); 
        if (player.getVy() < -player.getGravity()) player.setVy(-player.getGravity());

        player.addX(player.getVx());
        
        player.update(); 
        int collisionDirection = 0;

        for (Block block : blocks) {
            if (player.getBoundingBox().intersects(block.getBoundingBox())) {
                if (player.getVx() > 0) {
                    player.setX(block.getX() - (block.getW() / 2) - (player.getW() / 2));
                    collisionDirection = 1;
                } else if (player.getVx() < 0) {
                    player.setX(block.getX() + (block.getW() / 2) + (player.getW() / 2));
                    collisionDirection = -1;
                }
                player.setVx(0);
                player.update(); 
            }
        }
        if (collisionDirection != 0 && (controls.moveRight(keys, curr) || controls.moveLeft(keys, curr))) {
        	player.setGravity(5.0f);
        	if (controls.jumpable) {
        		if (controls.moveRight(keys, curr) && collisionDirection == 1 && controls.jump(keys, curr)) {
            		player.setVx(-player.getWallJumpPower());
            		player.setVy(player.getJumpPower());
            		controls.jumpable = false;
            	}
            	if (controls.moveLeft(keys, curr) && collisionDirection == -1 && controls.jump(keys, curr)) {
            		player.setVx(player.getWallJumpPower());
            		player.setVy(player.getJumpPower());
            		controls.jumpable = false;
            	}
        	}
        	
        }
        else {
        	player.setGravity(15.0f);
        }

		 float targetCamX = player.getX();
		 float targetCamY = -player.getY(); 
		
		 camera.tick(targetCamX, targetCamY);

        player.addY(player.getVy());
        player.update(); 

        for (Block block : blocks) {
            if (player.getBoundingBox().intersects(block.getBoundingBox())) {
                if (player.getVy() > 0) {
                    player.setY(block.getY() - (block.getH() / 2) - (player.getH() / 2));
                    player.setVy(0); 
                    player.setGrounded(false);
                } else if (player.getVy() < 0) {
                    player.setY(block.getY() + (block.getH() / 2) + (player.getH() / 2));
                    player.setVy(0); 
                    if (!player.isGrounded()) stepSounds[0].play();
                    player.setGrounded(true); 
                }
                player.update();
            }
        }
        
        if (player.isGrounded()) {
        	lastStepTime += dt;
        }
        else {
        	lastStepTime = 0;
        }
    }
}