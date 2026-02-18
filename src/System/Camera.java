package System;

public class Camera {
    private float x, y;
    
    // The "Tightness" of the camera (Range: 0.01f to 1.0f)
    // 1.0f = Instant snap (No smoothing)
    // 0.1f = Smooth, buttery glide
    // 0.03f = Very floaty and dream-like
    private float lerpFactor = 0.2f; 

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Call this every frame in Main.tick()
    public void tick(float targetX, float targetY) {
        // The Lerp Formula: current = current + (target - current) * factor
        x += (targetX - x) * lerpFactor;
        y += (targetY - y) * lerpFactor;
    }

    // Getters & Setters
    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    
    // Optional: Allow changing the smoothing dynamically (e.g., during a boss fight)
    public void setLerpFactor(float lerpFactor) { this.lerpFactor = lerpFactor; }
}