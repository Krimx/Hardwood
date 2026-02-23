package System;

/**
 * Handles sprite animation by tracking the current frame and updating it based on a timer.
 */
public class Animation {
    private int totalFrames;  // The total number of frames in this animation
    private int currentFrame; // The current frame index (0 to totalFrames - 1)
    
    private float timeSinceLastFrame; 
    private float delay;      // How long each frame stays on screen (in seconds)

    /**
     * Constructs a new Animation.
     * * @param delay       How long each frame stays on screen (in seconds).
     * @param totalFrames The total number of frames in this animation.
     */
    public Animation(float delay, int totalFrames) {
        this.delay = delay;
        this.totalFrames = totalFrames;
        this.currentFrame = 0;
        this.timeSinceLastFrame = 0;
    }

    /**
     * Updates the animation timer and advances the current frame if enough time has passed.
     * * @param dt The delta time (time elapsed since the last frame) in seconds.
     */
    public void update(float dt) {
        timeSinceLastFrame += dt;

        // Advance to the next frame when the accumulated time exceeds the delay
        if (timeSinceLastFrame > delay) {
            currentFrame++;
            timeSinceLastFrame = 0;
        }

        // Loop back to the first frame once the animation sequence completes
        if (currentFrame >= totalFrames) {
            currentFrame = 0;
        }
    }

    /**
     * Returns the current frame number to render.
     * Use this as the index for your BufferedImage arrays!
     * * @return The current frame index (0 to totalFrames - 1).
     */
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    /**
     * Resets the animation back to the first frame and clears the timer.
     * Useful when switching states (e.g., from IDLE to RUNNING).
     */
    public void reset() {
        currentFrame = 0;
        timeSinceLastFrame = 0;
    }
}