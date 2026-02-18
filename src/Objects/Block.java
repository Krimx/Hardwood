package Objects;
import java.awt.Color;
import java.awt.Graphics2D;

import System.BoundingBox;
import System.Camera;
import System.Sprite;

public class Block {
	private float x,y,w,h;
	private int renderLayer;
	private BoundingBox boundingBox;
	public Block(float x, float y, float w, float h, int renderLayer) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.renderLayer = renderLayer;
		this.boundingBox = new BoundingBox(x - (w/2), y - (h/2), w, h);
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public Sprite genSprite(int scrW, int scrH, Camera camera) {
		int renderX = (int) (this.getX() + (scrW / 2) - (this.getW() / 2) - camera.getX());
		int renderY = (int) (scrH - this.getY() - (scrH / 2) - (this.getH() / 2) - camera.getY());
		
		return new Sprite(this.renderLayer, renderX, renderY, this.getW(), this.getH(), Color.black);
	}
	
	public void render(Graphics2D g2, int scrW, int scrH) {
		int renderX = (int) (this.getX() + (scrW / 2) - (this.getW() / 2));
		int renderY = (int) (scrH - this.getY() - (scrH / 2) - (this.getH() / 2));
		
		g2.setColor(Color.black);
		g2.fillRect(renderX, renderY, (int) this.getW(), (int) this.getH());
	}
}
