package System;
public class BoundingBox {
    // 1. Core Data
    public float x, y;
    public float width, height;
    
    public enum CollisionSide {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    // 2. Constructors
    public BoundingBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    // Empty constructor for pooling or later assignment
    public BoundingBox() {
        this(0, 0, 0, 0);
    }

    // 3. Edge Helpers (Makes math easier to read)
    public float getMinX() { return x; }
    public float getMaxX() { return x + width; }
    public float getMinY() { return y; }
    public float getMaxY() { return y + height; }
    
    public float getCenterX() { return x + (width / 2f); }
    public float getCenterY() { return y + (height / 2f); }

    // 4. Debugging
    @Override
    public String toString() {
        return "Box [x=" + x + ", y=" + y + ", w=" + width + ", h=" + height + "]";
    }
    
    public void update(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    
    /**
     * Checks if this bounding box overlaps with another.
     * @param other The other box to check against.
     * @return true if they are colliding, false otherwise.
     */
    public boolean intersects(BoundingBox other) {
        return this.x < other.x + other.width &&    // My Left < Their Right
               this.x + this.width > other.x &&     // My Right > Their Left
               this.y < other.y + other.height &&   // My Bottom < Their Top
               this.y + this.height > other.y;      // My Top > Their Bottom
    }
    
    /**
     * Determines which side of THIS box is colliding with the other box.
     * Logic is based on the "Axis of Least Penetration".
     */
    public CollisionSide getCollisionSide(BoundingBox other) {
        // 1. Calculate the distance between centers
        float dx = (this.x + this.width / 2) - (other.x + other.width / 2);
        float dy = (this.y + this.height / 2) - (other.y + other.height / 2);

        // 2. Calculate the combined half-widths and half-heights
        float combinedHalfW = (this.width / 2) + (other.width / 2);
        float combinedHalfH = (this.height / 2) + (other.height / 2);

        // 3. Check if they are actually colliding first
        if (Math.abs(dx) >= combinedHalfW || Math.abs(dy) >= combinedHalfH) {
            return CollisionSide.NONE;
        }

        // 4. Calculate overlap on both axes
        float overlapX = combinedHalfW - Math.abs(dx);
        float overlapY = combinedHalfH - Math.abs(dy);

        // 5. Determine the direction
        // We assume the collision is on the axis with the LEAST overlap.
        if (overlapX < overlapY) {
            // Horizontal Collision (Left or Right)
            if (dx > 0) {
                // "This" is to the right of "Other", so "Other" hit our LEFT side
                return CollisionSide.LEFT; 
            } else {
                return CollisionSide.RIGHT;
            }
        } else {
            // Vertical Collision (Top or Bottom)
            if (dy > 0) {
                // "This" is above "Other" (assuming Y-Up), so "Other" hit our BOTTOM
                // Note: If your coordinate system is Y-Down (0 at top), swap these returns!
                return CollisionSide.BOTTOM;
            } else {
                return CollisionSide.TOP;
            }
        }
    }
}