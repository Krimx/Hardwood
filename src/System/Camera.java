package System;

/**
 * Handles viewport movement using Linear Interpolation (Lerp) to smoothly follow a target.
 */
public class Camera {
    private float x, y;
    
    // The "Tightness" of the camera (Range: 0.01f to 1.0f)
    // 1.0f = Instant snap (No smoothing)
    // 0.1f = Smooth, buttery glide
    // 0.03f = Very floaty and dream-like
    private float lerpFactor = 0.2f; 

    /**
     * Constructs a new Camera starting at the given coordinates.
     * * @param x The initial X coordinate.
     * @param y The initial Y coordinate.
     */
    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the camera's position smoothly towards the target coordinates.
     * Must be called every frame in the main game loop.
     * * @param targetX The X coordinate the camera should move towards.
     * @param targetY The Y coordinate the camera should move towards.
     */
    public void tick(float targetX, float targetY) {
        // The Lerp Formula: current = current + (target - current) * factor
        // This makes the camera move faster when far from the target, and slow down as it gets closer.
        x += (targetX - x) * lerpFactor;
        y += (targetY - y) * lerpFactor;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    
    /**
     * Optional: Allow changing the smoothing dynamically (e.g., during a boss fight).
     * * @param lerpFactor The new interpolation factor.
     */
    public void setLerpFactor(float lerpFactor) { this.lerpFactor = lerpFactor; }
}