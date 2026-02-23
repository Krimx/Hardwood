package System;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a navigational node in the UI. 
 * Acts as a directed graph node for controller-based and mouse menu navigation.
 */
public class MenuButton {
    private int x, y, w, h;
    private boolean hovering;
    private int padding;
    private String text;
    
    // Directional neighbors for controller mapping
    public MenuButton up, down, left, right;
    private boolean selected;

    /**
     * Constructs a new MenuButton node.
     */
    public MenuButton(int x, int y, int w, int h, int padding, String text) {
        this.padding = padding;
        this.x = x;
        this.y = y;
        this.w = w + padding;
        this.h = h + padding;
        this.hovering = false;
        this.text = text;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    
    public int getW() { return w; }
    public void setW(int w) { this.w = w; }
    
    public int getH() { return h; }
    public void setH(int h) { this.h = h; }
    
    /**
     * Checks if the mouse cursor is currently within the button's bounds.
     * * @param mouse The MouseHandler containing current cursor coordinates.
     * @return true if hovering, false otherwise.
     */
    public boolean isHovering(MouseHandler mouse) {
        this.hovering = mouse.x >= x && mouse.x <= x + w && mouse.y >= y && mouse.y <= y + h;
        return this.hovering;
    }
    
    /**
     * Sets the controller selection state.
     * * @param selected true if highlighted via D-Pad/Stick.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }

    /**
     * Renders the button with appropriate styling depending on its hover or selected state.
     * * @param g2 The Graphics2D context to draw to.
     */
    public void render(Graphics2D g2) {
        // Visual feedback for selection / hovering
        if (this.selected || this.hovering) g2.setColor(new Color(80, 80, 80)); 
        else g2.setColor(new Color(0, 0, 0));
        
        g2.fillRect(x, y, w, h);
        
        if (this.selected || this.hovering) g2.setColor(Color.WHITE);
        else g2.setColor(new Color(200, 200, 200));
        
        g2.drawString(this.text, x + this.padding / 2, y + this.padding / 2 + (g2.getFont().getSize() * .75f));
    }
}