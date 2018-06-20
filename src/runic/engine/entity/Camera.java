package runic.engine.entity;

import runic.engine.util.math.Vec2f;
import runic.engine.util.timers.Time;
import runic.game.typeracer.Game;

public class Camera {

	public enum CameraType {
	    CAMERA_PAN,
	    CAMERA_PAN_LERP,
	    CAMERA_PER_SCENE
	}
	
	private Vec2f position;
	private CameraType style;

	public Camera(Vec2f position, CameraType style) {
		this.position = position;
		this.style = style;
	}

	public Camera(float x, float y, CameraType style) {
		this.position = new Vec2f(x, y);
		this.style = style;
	}

	// Make it so that it is based off of the players centerpoint, and so that it slides to the next position and not
	// teleport to it, make it smooth!
	public void update(Entity entity) {
		switch(style) {
			case CAMERA_PER_SCENE:
				cameraPerScene(entity);
				break;

			case CAMERA_PAN:
				cameraPan(entity);
				break;
			
			case CAMERA_PAN_LERP:
				cameraPanLerp(entity);
				break;
				
			default:
				style = CameraType.CAMERA_PAN;
				System.out.println("Camera type unspecified. Defaulting to type: CameraType.CAMERA_PAN");
		}
	}
	
	private void cameraPan(Entity entity) {
		position.setPosition(getCenterX(entity) - Game.WIDTH / 2/* + (Game.getFrame().getWidth() / 2 - (player.getWidth() / 2))*/, getCenterY(entity) - Game.HEIGHT / 2 /*+ (Game.getFrame().getHeight() / 2 - (player.getHeight() / 2))*/);
	}
	
	private float lerp = 0.01f;
	private void cameraPanLerp(Entity entity) {
		float xpos = position.getX() + (Game.WIDTH/2);
		float ypos = position.getY() + (Game.HEIGHT/2);
		
		if(Time.getDeltad() != 0) {
		xpos += (getCenterX(entity) - xpos) * lerp * ((float) Time.getDeltad());
		ypos += (getCenterY(entity) - ypos) * lerp * ((float) Time.getDeltad());

		}
		position.setPosition(xpos - (Game.WIDTH / 2), ypos - (Game.HEIGHT / 2));
	}
	
	private void cameraPerScene(Entity entity) {
		if (getCenterX(entity) >= (position.getX() + Game.getFrame().getWidth())) {
			position.setPosition((position.getX() + Game.getFrame().getWidth()), position.getY());
		} else if (getCenterX(entity) <= position.getX()) {
			position.setPosition((position.getX() - Game.getFrame().getWidth()), position.getY());
		}
		if (getCenterY(entity) >= (position.getY() + Game.getFrame().getHeight())) {
			position.setPosition(position.getX(), (position.getY() + Game.getFrame().getHeight()));
		} else if (getCenterY(entity) <= position.getY()) {
			position.setPosition(position.getX(), (position.getY() - Game.getFrame().getHeight()));
		}
	}

	public void setLerp(float lerp) {
		this.lerp = lerp;
	}
	
	public float getLerp() {
		return this.lerp;
	}
	
	public Vec2f getPosition() {
		return position;
	}

	public static float getCenterX(Entity entity) {
        return entity.getX() + (entity.getWidth() / 2);
    }
    public static float getCenterY(Entity entity) {
        return entity.getY() + (entity.getHeight() / 2);
    }

    
}
