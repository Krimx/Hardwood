package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.studiohartman.jamepad.ControllerState;

import Objects.Block;
import System.Animation; 
import System.Camera;
import System.Controls;
import System.ImageLoader;
import System.KeyHandler;
import System.Sound;
import System.Sprite;

/**
 * The user-controlled character in the game world. Contains player-specific animations, sounds,
 * input polling physics logic, and collision handling.
 */
public class Player extends Entity {

    // 1. Define the animations
    private Animation idleAnim;
    private Animation runAnim;
    private Color jumpColor;
    private BufferedImage spritesheet;
    private int direction;
    private boolean isCrouching = false;
    private float normalHeight = 66f; 
    private float crouchHeight = 33f; 

    // Audio owned strictly by the Player instance
    private Sound jumpSound;
    private Sound landSound;

    public Player(float x, float y, float w, float h, int renderLayer) {
        super(x, y, w, h, renderLayer);
        
        // Setup the "Frames" (Colors) for placeholder logic
        Color[] idleFrames = { Color.BLUE, Color.CYAN };
        idleAnim = new Animation(0.5f, 2); 

        Color[] runFrames = { Color.ORANGE, Color.RED };
        runAnim = new Animation(0.1f, 4); 
        
        jumpColor = Color.GREEN;
        
        this.spritesheet = ImageLoader.loadImage("/sprites/player.png");
        this.direction = 1;
        
        // Bind the specific sounds for this character
        this.jumpSound = new Sound("/sounds/jumpWoosh.wav");
        this.landSound = new Sound("/sounds/footsteps/footstep1.wav");
    }

    /**
     * Advances the animation timer depending on what state the player is in.
     * * @param dt Delta time for the frame.
     */
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
        // Prevents generating sprites if the player is technically off-camera
        if (renderX + this.getW() < 0 || renderX > scrW || 
            renderY + this.getH() < 0 || renderY > scrH) {
            return null; 
        }
        
        Color colorToRender = null;
        BufferedImage sprite = ImageLoader.getSlice(spritesheet, 0, 0, 20, 33);
        if (this.getVx() != 0) this.direction = (this.getVx() > 0) ? 1 : -1;
        
        if (!isGrounded()) {
            if (this.getVy() >= 0) colorToRender = Color.green;
            else colorToRender = Color.blue;
        } else if (Math.abs(getVx()) > 0.1f) {
            sprite = ImageLoader.getSlice(this.spritesheet, (this.runAnim.getCurrentFrame() * 20), 33, 20, 33);
        } 

        // Return sprite with the calculated color or the sliced spritesheet
        if (colorToRender != null) return new Sprite(this.getRenderLayer(), renderX, renderY, this.getW(), this.getH(), colorToRender);
        else return new Sprite(this.getRenderLayer(), renderX + ((this.direction == -1) ? this.getW() : 0), renderY, this.getW() * this.direction, this.getH(), sprite);
    }

    @Override
    public void render(Graphics2D g2, int scrW, int scrH) {
        // NOTE: This render method is strictly for debugging/direct drawing.
        super.render(g2, scrW, scrH); 
    }

    @Override
    public void update() {
        super.getBoundingBox().update(super.getX() - (super.getW()/2), super.getY() - (super.getH()/2), super.getW(), super.getH());
    }
    
    /**
     * Safely changes the player's collision bounds while ensuring their feet stay anchored
     * to the floor, preventing accidental teleportation/clipping through geometry.
     * * @param crouching True if crouching, False if attempting to stand.
     */
    public void setCrouching(boolean crouching) {
        if (this.isCrouching == crouching) return;
        
        // 1. Record where the feet are RIGHT NOW
        float feetY = this.getY() - (this.getH() / 2f); 
        
        this.isCrouching = crouching;
        
        // 2. Change the height
        if (isCrouching) {
            this.setH(crouchHeight);
        } else {
            this.setH(normalHeight);
        }
        
        // 3. Move the center Y so the feet stay at the EXACT same spot
        this.setY(feetY + (this.getH() / 2f));
        
        // 4. Force an immediate BoundingBox sync
        this.update(); 
    }
    
    public boolean isCrouching() {
        return this.isCrouching;
    }

    /**
     * Calculates a projection above the player's current bounding box to verify 
     * if standing up will cause them to clip into a ceiling block.
     * * @param blocks The array of world blocks to check against.
     * @return True if a block exists directly above the player's head.
     */
    private boolean isCeilingAbove(ArrayList<Block> blocks) {
        float heightDiff = 33f; 
        
        java.awt.geom.Rectangle2D.Float headCheck = new java.awt.geom.Rectangle2D.Float(
            this.getX() - (this.getW() / 2f),
            this.getY() + (this.getH() / 2f), 
            this.getW(),
            heightDiff
        );

        for (Block block : blocks) {
            // Check intersection using Java's built-in Rectangle2D
            if (headCheck.intersects(block.getBoundingBox().getBounds2D())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Complete logic polling step for the Player object. Resolves inputs into 
     * velocity and perfectly solves map block collisions in both X and Y axes.
     */
    @Override
    public void logic(float dt, ControllerState curr, KeyHandler keys, Controls controls, ArrayList<Block> blocks, ArrayList<Entity> entities) {
        this.updateAnimation(dt);
        
        boolean inputtingCrouch = controls.crouch(keys, curr);
        
        if (inputtingCrouch && this.isGrounded()) {
            this.setCrouching(true);
        } else {
            // Only stop crouching if the button is released AND there is no ceiling above
            if (!isCeilingAbove(blocks)) {
                this.setCrouching(false);
            }
        }
        
        float speedMultiplier = this.isCrouching() ? 0.5f : 1.0f;
        float accel = this.isGrounded() ? this.getAcceleration() : this.getAccelerationInAir();
        accel *= speedMultiplier;
        
        // --- X AXIS MOVEMENT ---
        if (controls.moveRight(keys, curr)) {
            if (!this.isGrounded()) this.setVx(this.getVx() + this.getAccelerationInAir());
            else this.setVx(this.getVx() + accel);
        } else if (controls.moveLeft(keys, curr)) {
            if (!this.isGrounded()) this.setVx(this.getVx() - this.getAccelerationInAir());
            else this.setVx(this.getVx() - accel);
        } else {
            this.setVx(this.getVx() * 0.8f); 
            if (Math.abs(this.getVx()) < 0.1f) this.setVx(0);
        }
        
        float maxSpeed = this.isGrounded() ? this.getMaxSpeed() : this.getMaxSpeedInAir();
        if (this.getVx() > maxSpeed) this.setVx(maxSpeed);
        if (this.getVx() < -maxSpeed) this.setVx(-maxSpeed);

        // --- JUMPING ---
        if ((controls.jump(keys, curr)) && this.isGrounded() && controls.jumpable) {
            if (this.jumpSound != null) this.jumpSound.play(); 
            this.setVy(this.getJumpPower()); 
            this.setGrounded(false);
            controls.jumpable = false;
        }

        // --- GRAVITY ---
        this.setVy(this.getVy() - 0.5f); 
        if (this.getVy() < -this.getGravity()) this.setVy(-this.getGravity());

        // --- X AXIS COLLISIONS ---
        this.addX(this.getVx());
        this.update(); 
        
        int collisionDirection = 0;

        for (Block block : blocks) {
            if (this.getBoundingBox().intersects(block.getBoundingBox())) {
                if (this.getVx() > 0) {
                    this.setX(block.getX() - (block.getW() / 2) - (this.getW() / 2));
                    collisionDirection = 1;
                } else if (this.getVx() < 0) {
                    this.setX(block.getX() + (block.getW() / 2) + (this.getW() / 2));
                    collisionDirection = -1;
                }
                this.setVx(0);
                this.update(); 
            }
        }
        
        // --- WALL JUMPING ---
        if (collisionDirection != 0 && (controls.moveRight(keys, curr) || controls.moveLeft(keys, curr))) {
            this.setGravity(5.0f);
            if (controls.jumpable) {
                if (controls.moveRight(keys, curr) && collisionDirection == 1 && controls.jump(keys, curr)) {
                    this.setVx(-this.getWallJumpPower());
                    this.setVy(this.getJumpPower());
                    controls.jumpable = false;
                }
                if (controls.moveLeft(keys, curr) && collisionDirection == -1 && controls.jump(keys, curr)) {
                    this.setVx(this.getWallJumpPower());
                    this.setVy(this.getJumpPower());
                    controls.jumpable = false;
                }
            }
        } else {
            this.setGravity(15.0f);
        }

        // --- Y AXIS COLLISIONS ---
        this.addY(this.getVy());
        this.update(); 

        for (Block block : blocks) {
            if (this.getBoundingBox().intersects(block.getBoundingBox())) {
                if (this.getVy() > 0) {
                    this.setY(block.getY() - (block.getH() / 2) - (this.getH() / 2));
                    this.setVy(0); 
                    this.setGrounded(false);
                } else if (this.getVy() < 0) {
                    this.setY(block.getY() + (block.getH() / 2) + (this.getH() / 2));
                    this.setVy(0); 
                    
                    if (!this.isGrounded() && this.landSound != null) this.landSound.play();
                    
                    this.setGrounded(true); 
                }
                this.update();
            }
        }
    }
}