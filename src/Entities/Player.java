package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import System.Animation; // Import the new class
import System.Camera;
import System.ImageLoader;
import System.Sprite;

public class Player extends Entity {

    // 1. Define the animations
    private Animation idleAnim;
    private Animation runAnim;
    private Color jumpColor;
    private BufferedImage spritesheet;
    private int direction;

    public Player(float x, float y, float w, float h, int renderLayer) {
        super(x, y, w, h, renderLayer);
        
        // 2. Setup the "Frames" (Colors)
        // Idle: Pulsing slowly between Blue and Cyan
        Color[] idleFrames = { Color.BLUE, Color.CYAN };
        idleAnim = new Animation(0.5f, 2); // Change every 0.5 seconds

        // Run: Flashing rapidly between Orange and Red
        Color[] runFrames = { Color.ORANGE, Color.RED };
        runAnim = new Animation(0.1f, 4); // Change every 0.1 seconds
        
        // Jump: Solid Green
        jumpColor = Color.GREEN;
        
        this.spritesheet = ImageLoader.loadImage("/sprites/player.png");
        this.direction = 1;
    }

    // 3. New method to advance the animation timer
    public void updateAnimation(float dt) {
        // We only care about updating the animation that is currently valid
        if (Math.abs(getVx()) > 0.1f) {
            runAnim.update(dt);
        } else {
            idleAnim.update(dt);
        }
    }

    @Override
    public Sprite genSprite(int scrW, int scrH, Camera camera) {
        int renderX = (int) (this.getX() + (scrW / 2) - (this.getW() / 2) - camera.getX());
        int renderY = (int) (scrH - this.getY() - (scrH / 2) - (this.getH() / 2) - camera.getY());
        
        // --- VIEW CULLING ---
        if (renderX + this.getW() < 0 || renderX > scrW || 
            renderY + this.getH() < 0 || renderY > scrH) {
            return null; // Don't process anything else
        }
        
        // 4. Decide which Color to use right now
        Color colorToRender = null;
        BufferedImage sprite = ImageLoader.getSlice(spritesheet, 0, 0, 20, 33);
        if (this.getVx() != 0) this.direction = (this.getVx() > 0) ? 1 : -1;
        
        if (!isGrounded()) {
            if (this.getVy() >= 0) colorToRender = Color.green;
            else colorToRender = Color.blue;
        } else if (Math.abs(getVx()) > 0.1f) {
            sprite = ImageLoader.getSlice(this.spritesheet, (this.runAnim.getCurrentFrame() * 20), 33, 20, 33);
        } else {
//                colorToRender = idleAnim.getCurrentColor();
        }

        // 5. Return sprite with the calculated color
        if (colorToRender != null) return new Sprite(this.getRenderLayer(), renderX, renderY, this.getW(), this.getH(), colorToRender);
        else return new Sprite(this.getRenderLayer(), renderX + ((this.direction == -1) ? this.getW() : 0), renderY, this.getW() * this.direction, this.getH(), sprite);
    }
    
    // Keep your existing overrides...
    @Override
    public void render(Graphics2D g2, int scrW, int scrH) {
        // NOTE: This render method is strictly for debugging/direct drawing.
        // Since you are using the Sprite system now, you might not strictly need this 
        // unless you are drawing without the sprite list.
        super.render(g2, scrW, scrH); 
    }

    @Override
    public void update() {
        super.getBoundingBox().update(super.getX() - (super.getW()/2), super.getY() - (super.getH()/2), super.getW(), super.getH());
    }
}