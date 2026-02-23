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

/**
 * Enum representing the current state of the game.
 */
enum Menu {
	MAIN,
	PAUSE,
	GAMEPLAY
}

/**
 * The core engine wrapper class for the game. Manages global variables,
 * the primary game loop (TPS & FPS constraints), routing of rendering calls, 
 * and initializing all subsystems.
 */
public class Main {
    // --- Core Window & Input Components ---
    public static JFrame frame = new JFrame();
    public static Canvas canvas = new Canvas(); 
    public static KeyHandler keys = new KeyHandler();
    public static MouseHandler mouse = new MouseHandler();
    public static Camera camera = new Camera(0,0);
    public static Random random = new Random();
    
    // --- Game State & UI ---
    public static Menu menu = Menu.GAMEPLAY;
    public static Controls controls = new Controls();
    public static Font menuFont = FontLoader.loadFont("/fonts/KiwiSoda.ttf", 32f);
    public static BufferedImage playerSpritesheet = ImageLoader.loadImage("/sprites/player.png");
    public static ControllerManager controllers = new ControllerManager();
    public static boolean dpadReset = true;

    // --- Background Assets & Parallax ---
    public static BufferedImage background1 = ImageLoader.loadImage("/sprites/country-platform-back.png");
    public static BufferedImage background2 = ImageLoader.loadImage("/sprites/country-platform-forest.png");
    public static BufferedImage background3 = ImageLoader.loadImage("/sprites/country-platform-tiles-example.png");
    public static float bgFactor = 0.2f;

    // --- Game Loop Timing & FPS ---
    public static boolean running = true;
    public static final double TARGET_TPS = 60.0; 
    public static final double NS_PER_TICK = 1000000000.0 / TARGET_TPS;
    public static boolean limitFPS = true; 
    public static int targetFPS = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    // ----------------------------
    
    // --- Resolution ---
    public static int scrW = 600, scrH = 600;

    // --- Game World Data ---
    public static ArrayList<Entity> entities = new ArrayList<>();
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static ArrayList<Sprite> sprites = new ArrayList<>();
    
    // --- Main Menu Buttons ---
    public static MenuButton startButton = new MenuButton(10, 10, FontLoader.getTextDimensions(menuFont, "Start Game").width, menuFont.getSize(), 10, "Start Game");
    public static MenuButton exitButton = new MenuButton(10, 58, FontLoader.getTextDimensions(menuFont, "Exit Game").width, menuFont.getSize(), 10, "Exit Game");
    public static MenuButton currentBtn = startButton;
    
    // --- Audio ---
    public static Sound jumpSound;
    public static Sound[] stepSounds = {null, null, null, null};
    public static float lastStepTime = 0, stepSoundInterval = 0.3f;
    
    public static boolean jumpedFromRun = false;

    // ==========================================
    // MAIN
    // ==========================================
    /**
     * The primary entry point for the application.
     * Initializes the game data, sets up the window, and starts the game loop.
     */
    public static void main(String[] args) throws Exception {
        init();
        initJFrame();
        runGameLoop();
    }

    // ==========================================
    // INIT JFRAME
    // ==========================================
    /**
     * Prepares and assembles the base JFrame window, tying the global Canvas
     * to the input listeners and display handlers.
     */
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
    /**
     * Initializes controllers, parses the maps, loads base entities, stitches 
     * together the UI navigation graph, and loads audio.
     */
    public static void init() {
    	controllers.initSDLGamepad(); // Start Jamepad
    	
        entities.add(new Player(0,0,40,66,1));
        blocks = MapLoader.loadMap("/maps/testMap.map");
//        blocks.add(new Block(40, -80, 3000, 40, 2));
//        blocks.add(new Block(60, -40, 40, 40, 2));
        
        // Stitch the Menu Node Graph together
        startButton.down = exitButton;
        exitButton.up = startButton;
        currentBtn = startButton; // Default selection
        currentBtn.setSelected(true);
        
        // Load global audio assets
        jumpSound = new Sound("/sounds/jumpWoosh.wav");
        stepSounds[0] = new Sound("/sounds/footsteps/footstep1.wav");
        stepSounds[1] = new Sound("/sounds/footsteps/footstep2.wav");
        stepSounds[2] = new Sound("/sounds/footsteps/footstep3.wav");
        stepSounds[3] = new Sound("/sounds/footsteps/footstep4.wav");
    }

    // ==========================================
    // RUN GAME LOOP
    // ==========================================
    /**
     * The master game loop threading implementation. Responsible for capping frame rates,
     * maintaining exactly 60 logical Ticks Per Second (TPS), and tracking performance.
     */
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
                // Consume accumulated delta time in fixed steps to ensure consistent physics
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
                    // If capped, check if enough time has passed for the next frame based on target refresh rate
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
                    // Only sleep if we didn't tick and didn't render to prevent 100% CPU usage.
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
    /**
     * The primary dispatcher for logical game progression based on the current Menu State.
     * * @param dt the delta time passed into logic systems (usually 1/60s).
     */
    public static void tick(float dt) {
    	ControllerState curr = controllers.getState(0);
    	if (controls.debug(keys, curr)) entities.get(0).setPos(0,0);
    	if (menu == Menu.MAIN) {
    		mainMenu(curr);
    	}
    	else if (menu == Menu.GAMEPLAY) {
    		mainGame(dt, curr);
    	}
        
        updateSystem();
        keys.update();
    }

    // ==========================================
    // RENDER
    // ==========================================
    /**
     * Prepares the graphics context, clears the screen, and routes drawing calls
     * based on the active game state. Uses BufferStrategy to prevent screen tearing.
     */
    public static void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics g = null;
        try {
            g = bs.getDrawGraphics();
            // CLEAR SCREEN
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, scrW, scrH);
            
            // Route rendering based on state
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
    /**
     * Translates abstract object positions into view-specific pixel space, 
     * handles parallax background rendering, and dispatches the Sprite List.
     * * @param g The graphics context.
     */
    public static void drawGame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        
        // Generate Sprites for Entities
        for (Entity entity : entities) {
            Sprite s = entity.genSprite(scrW, scrH, camera);
            if (s != null) {
                sprites.add(s);
            }
        }
        
        // Generate Sprites for Blocks (Tile/World Geometry)
        for (Block block : blocks) {
            Sprite s = block.genSprite(scrW, scrH, camera);
            if (s != null) sprites.add(s);
        }
        
        // Generate parallax background sprites (offset by camera position and bgFactor)
        sprites.add(new Sprite(0.0f, -background1.getWidth() - (camera.getX() * bgFactor),   -100 - (camera.getY() * bgFactor / 2), 1152, 672, background1));
        sprites.add(new Sprite(0.1f, -background2.getWidth() - (camera.getX() * bgFactor*2), -100 - (camera.getY() * bgFactor / 2)*2, 1152, 672, background2));
        sprites.add(new Sprite(0.2f, -background3.getWidth() - (camera.getX() * bgFactor*3), -100 - (camera.getY() * bgFactor / 2)*3, 1152, 672, background3));
        
        // Sort to ensure correct Z-Ordering based on render layer
        sprites.sort(Comparator.comparing(Sprite::getRenderLayer));
        
        // Commit all mapped Sprites to the Graphics Object
        for (Sprite sprite : sprites) {
            sprite.render(g2);
        }
        sprites.clear();
    }

    // ==========================================
    // DRAW MAIN MENU
    // ==========================================
    /**
     * Renders the main menu UI elements to the screen.
     * * @param g The graphics context.
     */
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
    /**
     * Keeps track of canvas dimension changes in case the window is resized.
     */
    public static void updateSystem() {
        if (canvas.getWidth() > 0) {
             scrW = canvas.getWidth();
             scrH = canvas.getHeight();
        }
    }
    
    // ==========================================
    // MAIN GAME LOGIC
    // ==========================================
    /**
     * Logic loop exclusively for the active gameplay phase. 
     * Iterates all loaded entities, passing injected dependencies (blocks, controllers, keys).
     * * @param dt   Delta time for physics logic.
     * @param curr The current controller state.
     */
    public static void mainGame(float dt, ControllerState curr) {
    	controllers.update();
    	if (!controls.jump(keys, curr)) controls.jumpable = true;
    	
    	// Allow backing out to the menu
    	if (controls.escape(keys, curr)) {
			menu = Menu.MAIN;
		}
    	
    	// Delegate complex logic execution cleanly to the objects themselves via OOP
    	for (Entity entity : entities) {
    		entity.logic(dt, curr, keys, controls, blocks);
    	}
//        playerLogic(dt, curr);
    	Player player = (Player)entities.get(0);
    	
    	// Ensure camera smoothly follows the Player
    	camera.tick(player.getX(), -player.getY());
        
    	// Process procedural sound generation based on Player state (e.g., footsteps)
        if (lastStepTime >= stepSoundInterval && player.isGrounded() && player.getVx() != 0) {
        	lastStepTime = 0;
        	int stepSound = random.nextInt(1,stepSounds.length - 1);
        	stepSounds[stepSound].play();
        }
    }
    
    // ==========================================
    // MAIN MENU LOGIC
    // ==========================================
    /**
     * Logic loop exclusively for the Main Menu screen. Bridges hybrid 
     * controller and mouse/keyboard navigation.
     * * @param curr The current controller state.
     */
    public static void mainMenu(ControllerState curr) {
    	// Temporary shortcut to hop back to the game if needed
    	if (controls.escape(keys, curr)) {
			menu = Menu.GAMEPLAY;
		}
        // 1. Handle Navigation (Controller/Keyboard)
        updateMenuNavigation(curr);

        // 2. Sync Mouse Hover with Selection
        // If the mouse moves, it should take priority over the controller selection
        if (startButton.isHovering(mouse)) {
            currentBtn.setSelected(false);
            currentBtn = startButton;
            currentBtn.setSelected(true);
        } else if (exitButton.isHovering(mouse)) {
            currentBtn.setSelected(false);
            currentBtn = exitButton;
            currentBtn.setSelected(true);
        }

        // 3. Handle "Accept" Action (Start Game)
        boolean acceptInput = keys.keys[KeyEvent.VK_ENTER] || curr.a;
        if ((acceptInput && currentBtn == startButton) || (startButton.isHovering(mouse) && mouse.buttons[1])) {
            menu = Menu.GAMEPLAY;
        }

        // 4. Handle "Exit" Action
        if ((acceptInput && currentBtn == exitButton) || (exitButton.isHovering(mouse) && mouse.buttons[1])) {
            running = false;
        }
    }
    
    /**
     * Safely reads generic directional inputs (stick, d-pad, arrow keys) and moves
     * the menu pointer according to the connected directional graph in MenuButton.
     * * @param curr The current controller state.
     */
    public static void updateMenuNavigation(ControllerState curr) {
        // Check for directional input
        boolean up = keys.keys[KeyEvent.VK_UP] || curr.dpadUp || curr.leftStickY > 0.5;
        boolean down = keys.keys[KeyEvent.VK_DOWN] || curr.dpadDown || curr.leftStickY < -0.5;
        boolean left = keys.keys[KeyEvent.VK_LEFT] || curr.dpadLeft || curr.leftStickX < -0.5;
        boolean right = keys.keys[KeyEvent.VK_RIGHT] || curr.dpadRight || curr.leftStickX > 0.5;

        if (dpadReset) {
            MenuButton next = null;
            if (up) next = currentBtn.up;
            if (down) next = currentBtn.down;
            if (left) next = currentBtn.left;
            if (right) next = currentBtn.right;

            if (next != null) {
                currentBtn.setSelected(false);
                currentBtn = next;
                currentBtn.setSelected(true);
                dpadReset = false; // Lock movement until keys/stick released
            }
        }

        // Reset the lock when no directional input is detected (allows moving again)
        if (!up && !down && !left && !right) {
            dpadReset = true;
        }
    }
}