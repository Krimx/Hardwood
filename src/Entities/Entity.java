package Entities;

import java.awt.Color;
import java.awt.Graphics2D;

import System.BoundingBox;
import System.Camera;
import System.Sprite;

public class Entity {
	private float x, y;
	private float w, h;
	private float vx, vy;
	private BoundingBox boundingBox;
	private int renderLayer;
	private boolean grounded = false;
	private float
			maxSpeed = 8.0f, maxSpeedInAir = 10.0f, acceleration = 5.0f,
			accelerationInAir = 1.0f, jumpPower = 11.0f, wallJumpPower = 8.0f,
			gravity = 15.0f;
	public Entity(float x, float y, float w, float h, int renderLayer) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.renderLayer = renderLayer;
		this.boundingBox = new BoundingBox(x - (w/2), y - (h/2), w, h);
		this.vx = 0;
		this.vy = 0;
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
	public void addX(float dx) {
		this.x += dx;
	}
	public void addY(float dy) {
		this.y += dy;
	}
	public float getVx() {
		return vx;
	}
	public void setVx(float vx) {
		this.vx = vx;
	}
	public float getVy() {
		return vy;
	}
	public void setVy(float vy) {
		this.vy = vy;
	}
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public float getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	public float getJumpPower() {
		return jumpPower;
	}
	public void setJumpPower(float jumpPower) {
		this.jumpPower = jumpPower;
	}
	public float getMaxSpeedInAir() {
		return maxSpeedInAir;
	}
	public void setMaxSpeedInAir(float maxSpeedInAir) {
		this.maxSpeedInAir = maxSpeedInAir;
	}
	public float getAccelerationInAir() {
		return accelerationInAir;
	}
	public void setAccelerationInAir(float accelerationInAir) {
		this.accelerationInAir = accelerationInAir;
	}
	public float getWallJumpPower() {
		return wallJumpPower;
	}
	public void setWallJumpPower(float wallJumpPower) {
		this.wallJumpPower = wallJumpPower;
	}
	public float getGravity() {
		return gravity;
	}
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public int getRenderLayer() {
		return renderLayer;
	}
	public void setRenderLayer(int renderLayer) {
		this.renderLayer = renderLayer;
	}
	public boolean isGrounded() {
		return grounded;
	}
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}
	public void render(Graphics2D g2, int scrW, int scrH) {
		
	}
	public Sprite genSprite(int scrW, int scrH, Camera camera) {
		return new Sprite(0,0,0,0,0,Color.black);
	}

	public void update() {
		this.boundingBox.update(x - (w/2), y - (h/2), w, h);
	}
}
