package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import System.Camera;
import System.ImageLoader;
import System.Sprite;

public class RoamingEnemy extends Entity {

    private int direction = 1; // 1 for Right, -1 for Left
    private float roamSpeed = 2.0f;
    private boolean moving = true;
    
 // Inside RoamingEnemy.java
    private int timer = 0;
    private int state = 0; // 0: Left, 1: Pause, 2: Right, 3: Pause
    private float speed = 1.5f; // Slow speed as requested

    public int getTimer() { return timer; }
    public void setTimer(int timer) { this.timer = timer; }
    public int getState() { return state; }
    public void setState(int state) { this.state = state; }
    public float getRoamSpeed() { return speed; }

    public RoamingEnemy(float x, float y, float w, float h, int renderLayer) {
        super(x, y, w, h, renderLayer);
        // Enemies usually move slower than players
        this.setMaxSpeed(3.0f); 
    }

    @Override
    public void update() {
        super.update();
    }

    // A simple way to visualize him until you have sprites
    @Override
    public void render(Graphics2D g2, int scrW, int scrH) {
        g2.setColor(Color.RED);
        // Note: You'll want to subtract camera coordinates here eventually
        g2.fillRect((int)(getX() - getW()/2), (int)(getY() - getH()/2), (int)getW(), (int)getH());
    }
    
    public void turnAround() {
        direction *= -1;
    }
    
    @Override
    public Sprite genSprite(int scrW, int scrH, Camera camera) {
        // 1. Calculate screen coordinates based on world position and camera
        // This math ensures the enemy moves correctly relative to the player/camera
    	int renderX = (int) (this.getX() + (scrW / 2) - (this.getW() / 2) - camera.getX());
        int renderY = (int) (scrH - this.getY() - (scrH / 2) - (this.getH() / 2) - camera.getY());
        
        // 2. State-based Coloring
     // Inside RoamingEnemy's genSprite method
        Color enemyColor = Color.RED;

        // If we are moving faster than patrol speed, we are likely in Aggro mode
        if (Math.abs(getVx()) > getRoamSpeed()) {
            enemyColor = Color.MAGENTA; 
        } else if (!isGrounded()) {
            enemyColor = (this.getVy() >= 0) ? new Color(255, 100, 100) : new Color(150, 0, 0);
        }

        System.out.println(renderX + ", " + renderY + ", " + this.getW() + ", " + this.getH());
        // 3. Return the Sprite object using the color constructor
        // This matches the sprite rendering system in your Main.java drawGame() loop
        return new Sprite(this.getRenderLayer(), renderX, renderY, this.getW(), this.getH(), enemyColor);
    }
}