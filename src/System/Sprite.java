package System;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents a renderable graphic, either a solid color or a 2D texture.
 */
public class Sprite {
    private float renderLayer;
    private float x, y;
    private float w, h;
    private Color color;
    private BufferedImage image;
    
    /**
     * Constructs a colored rectangle Sprite.
     */
    public Sprite(float renderLayer, float x, float y, float w, float h, Color color) {
        super();
        this.renderLayer = renderLayer;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }
    
    /**
     * Constructs an image-based Sprite.
     */
    public Sprite(float renderLayer, float x, float y, float w, float h, BufferedImage image) {
        super();
        this.renderLayer = renderLayer;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.image = image;
    }
    
    public float getRenderLayer() { return renderLayer; }
    public void setRenderLayer(float renderLayer) { this.renderLayer = renderLayer; }
    
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    
    public float getW() { return w; }
    public void setW(float w) { this.w = w; }
    
    public float getH() { return h; }
    public void setH(float h) { this.h = h; }
    
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    
    public BufferedImage getImage() { return image; }
    public void setImage(BufferedImage image) { this.image = image; }
    
    public void render(Graphics2D g2) {
		if (this.image == null) {
			g2.setColor(this.color);
			g2.fillRect((int)this.x, (int)this.y, (int)this.w, (int)this.h);
		}
		else {
			g2.drawImage(this.image, (int)this.x, (int)this.y, (int)this.w, (int)this.h, null);
		}
	}
}
