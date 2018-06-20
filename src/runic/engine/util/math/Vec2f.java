package runic.engine.util.math;

public class Vec2f {

	float x, y;

	public Vec2f(int x, int y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public Vec2f(float x, float y) {
		this.x = y;
		this.y = y;
	}
	
	public Vec2f(Vec2f pos) {
		new Vec2f(pos.getX(), pos.getY());
	}

	public void multiply(Vec2f vector) {
		x *= vector.getX();
		y *= vector.getY();
	}

	public void add(Vec2f vector) {
		x += vector.getX();
		y += vector.getY();
	}

	public float getX() {
		return x;
	}

	public int getIntX() {
		return Math.round(x);
	}

	public float getY() {
		return y;
	}

	public int getIntY() {
		return Math.round(y);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Vec2f newposition) {
		this.x = newposition.getX();
		this.y = newposition.getY();
	}
	
	public void normalize() {
		double length = Math.sqrt(this.x * this.x + this.y * this.y);
		this.x /= length;
		this.y /= length;
	}
	
	/**
	 * @return X-value rounded to nearest integer with Math.round(float a);
	 */
	public int getXRounded() {
		return Math.round(x);
	}
	
	/**
	 * @return Y-value rounded to nearest integer with Math.round(float a);
	 */
	public int getYRounded() {
		return Math.round(y);
	}

}
