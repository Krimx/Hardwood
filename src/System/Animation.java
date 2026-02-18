package System;

public class Animation {
    private int totalFrames;  // The total number of frames in this animation
    private int currentFrame; // The current frame index (0 to totalFrames - 1)
    
    private float timeSinceLastFrame; 
    private float delay;      // How long each frame stays on screen (in seconds)

    public Animation(float delay, int totalFrames) {
        this.delay = delay;
        this.totalFrames = totalFrames;
        this.currentFrame = 0;
        this.timeSinceLastFrame = 0;
    }

    public void update(float dt) {
        timeSinceLastFrame += dt;

        // If enough time has passed, move to the next frame
        if (timeSinceLastFrame > delay) {
            currentFrame++;
            timeSinceLastFrame = 0;
        }

        // Loop back to the start if we reach the end
        if (currentFrame >= totalFrames) {
            currentFrame = 0;
        }
    }

    /**
     * Returns the current frame number to render.
     * Use this as the index for your BufferedImage arrays!
     */
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    // Call this to restart animation (e.g. when switching from IDLE to RUNNING)
    public void reset() {
        currentFrame = 0;
        timeSinceLastFrame = 0;
    }
}