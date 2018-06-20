package runic.game.typeracer;

import java.awt.Graphics;

import runic.engine.components.Renderable;
import runic.engine.components.Updatable;
import runic.engine.util.math.MathUtil;
import runic.engine.util.math.Vec2f;
import runic.engine.util.timers.Time;

class WordObject implements Updatable, Renderable {

	private String word;
	private Vec2f position;
	public static float speed = 6.0f; // Default speed is 9.0f, static so that all words share the same speed
	
	public WordObject(String word, Vec2f pos) {
		this.word = word;
		this.position = pos;
	}

	/*public WordObject(String word)	{
		this.word = word;
		int xpos = -100;
		int ypos = GenRandom.randInt(100, Game.WINDOW_HEIGHT - 100);
		this.position = new Vec2f(xpos, ypos);
	}*/
	
	
	
	public WordObject(String word)	{
		this.word = word;
		int xpos = -100;
		int ypos = MathUtil.randInt(100, Game.HEIGHT - 100);
		this.position = new Vec2f(xpos, ypos);
	}
	
	@Override
	public void update() {
		position.setPosition(position.getX() +  ((float) (speed * (Time.getDeltad()/Game.TARGET_FRAME_RATE))), position.getY());
		//System.out.println((int)(speed * (Time.getDeltad()/Game.TARGET_FRAME_RATE)));
	}
	
	@Override
	public void render(Graphics g) {
		g.drawString(getWord(), getPosition().getXRounded(), getPosition().getYRounded());
	}
	
	public String getWord() {
		return word;
	}
	
	public Vec2f getPosition() {
		return position;
	}

/*	public void setSpeed(float speed) {
		this.speed = speed;
	}*/
	
}
