package System;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuButton {
	private int x,y,w,h;
	private boolean hovering;
	private int padding;
	private String text;

	public MenuButton(int x, int y, int w, int h, int padding, String text) {
		this.padding = padding;
		this.x = x;
		this.y = y;
		this.w = w + padding;
		this.h = h + padding;
		this.hovering = false;
		this.text = text;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public boolean isHovering(MouseHandler mouse) {
		this.hovering = mouse.x >= x && mouse.x <= x + w && mouse.y >= y && mouse.y <= y + h;
		return this.hovering;
	}
	public void render(Graphics2D g2) {
		if (this.hovering) g2.setColor(new Color(50,50,50));
		else g2.setColor(new Color(0,0,0));
		g2.fillRect(x, y, w, h);
		if (this.hovering) g2.setColor(new Color(225,225,225));
		else g2.setColor(new Color(200,200,200));
		g2.drawString(this.text, x + this.padding / 2, y + this.padding / 2 + (g2.getFont().getSize() * .75f));
	}
}
