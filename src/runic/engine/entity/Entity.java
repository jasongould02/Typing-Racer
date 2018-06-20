package runic.engine.entity;

import runic.engine.util.math.Size;
import runic.engine.util.math.Vec2f;

public class Entity {

	private Vec2f position;
	private Size size;
	
	public Entity(Vec2f position, Size size) {
		this.position = position;
		this.size = size;
	}
	
	/*public Entity(float x, float y, int size) {
		position = new Vec2f(x, y);
		this.size = new Size(size);
	}
	
	public Entity(float x, float y, int width, int height) {
		position = new Vec2f(x, y);
		size = new Size(width, height);
	}*/
	
	public float xvelocity = 0, yvelocity = 0;
	public void applyForce(float xvel, float yvel) {
		xvelocity += xvel;
		yvelocity += yvel;
	}
	
	public void setVelocity(float xvel, float yvel) {
		xvelocity = xvel;
		yvelocity = yvel;
	}
	
	public Vec2f getVelocityVector() {
		Vec2f velVector = new Vec2f(xvelocity, yvelocity);
		return velVector;
	}
	
	public float getX() {
		return position.getX();
	}

	public float getY() {
		return position.getY();
	}

	public int getWidth() {
		return size.getWidth();
	}

	public int getHeight() {
		return size.getHeight();
	}

	public Vec2f getPosition() {
		return position;
	}

	public Size getSize() {
		return size;
	}

	public void setX(float xpos) {
		position.setPosition(xpos, getY());
	}

	public void setY(float ypos) {
		position.setPosition(getX(), ypos);
	}

	public void setPosition(float x, float y) {
		position.setPosition(x, y);
	}

	public void setPosition(Vec2f position) {
		this.position.setPosition(position);
	}
	
	public void setSize(Size size) {
		this.size = size;
	}

	public void setSize(int width, int height) {
		this.size.setSize(width, height);
	}

	public void setWidth(int width) {
		this.size.setWidth(width);
	}
	
	public void setHeight(int height) {
		this.size.setHeight(height);
	}
	
	public Vec2f getMidPosition() {
		Vec2f vec = new Vec2f(getPosition().getX() + ((float)(size.getWidth()/2)), getPosition().getY() + ((float)(size.getHeight()/2)));
		return vec;
	}
	
}
