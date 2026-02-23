package System;

import java.awt.geom.Rectangle2D;

/**
 * Represents an AABB (Axis-Aligned Bounding Box) used for 2D collision detection.
 */
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

    /**
     * Constructs a fully defined BoundingBox.
     */
    public BoundingBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Empty constructor for pooling or later assignment.
     */
    public BoundingBox() {
        this(0, 0, 0, 0);
    }

    // Edge Helpers (Makes math easier to read)
    public float getMinX() { return x; }
    public float getMaxX() { return x + width; }
    public float getMinY() { return y; }
    public float getMaxY() { return y + height; }
    
    public float getCenterX() { return x + (width / 2f); }
    public float getCenterY() { return y + (height / 2f); }

    @Override
    public String toString() {
        return "Box [x=" + x + ", y=" + y + ", w=" + width + ", h=" + height + "]";
    }
    
    /**
     * Updates the position and dimensions of the bounding box.
     */
    public void update(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    /**
     * Checks if this bounding box overlaps with another bounding box.
     * * @param other The other BoundingBox to check against.
     * @return true if the boxes overlap, false otherwise.
     */
    public boolean intersects(BoundingBox other) {
        // AABB collision logic: Checks if there is a gap between any of the 4 sides. 
        // If there is no gap on any side, the boxes are colliding.
        return this.x < other.x + other.width && 
               this.x + this.width > other.x && 
               this.y < other.y + other.height && 
               this.height + this.y > other.y;
    }

    /**
     * Calculates the side of collision relative to this bounding box.
     * * @param other The other box colliding with this one.
     * @return The CollisionSide indicating where the other box hit this one.
     */
    public CollisionSide getCollisionDirection(BoundingBox other) {
        float dx = this.getCenterX() - other.getCenterX();
        float dy = this.getCenterY() - other.getCenterY();

        float combinedHalfW = (this.width / 2) + (other.width / 2);
        float combinedHalfH = (this.height / 2) + (other.height / 2);

        if (Math.abs(dx) >= combinedHalfW || Math.abs(dy) >= combinedHalfH) {
            return CollisionSide.NONE;
        }

        float overlapX = combinedHalfW - Math.abs(dx);
        float overlapY = combinedHalfH - Math.abs(dy);

        if (overlapX < overlapY) {
            if (dx > 0) {
                return CollisionSide.LEFT; 
            } else {
                return CollisionSide.RIGHT;
            }
        } else {
            if (dy > 0) {
                return CollisionSide.BOTTOM;
            } else {
                return CollisionSide.TOP;
            }
        }
    }
    
    /**
     * Converts this BoundingBox to a standard Java Rectangle2D object.
     * Useful for interfacing with Java's built-in geometry methods.
     * * @return A Rectangle2D.Float representation of this box.
     */
    public Rectangle2D.Float getBounds2D() {
        return new Rectangle2D.Float(x, y, width, height);
    }
}