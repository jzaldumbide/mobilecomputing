package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;

public class Paddle extends DynamicGameObject {

	public static final int HEALTHY = 0;
	public static final int INFECTED = 1;
	
	public static final float PADDLE_HEIGHT = 16;	
	public float width = 96;

	public int state;
	
	public Paddle(float x, float y, float width) {
		
		super(x, y, width, PADDLE_HEIGHT);
		this.width = width;
		
		state = HEALTHY;
	}

	public void update(float deltaTime, float accelX) {
		
		if ((velocity.x < 0 && accelX >= 0) || (velocity.x > 0 && accelX <= 0)) {
			velocity.x = 0;
		} else {
			velocity.add(accelX * deltaTime, 0);
		}

		position.add(velocity.x * deltaTime, 0);

		if (position.x < width / 2) {
			position.x = width / 2;
			velocity.x = 0;
		}

		if (position.x > (World.WORLD_WIDTH - width / 2)) {
			position.x = World.WORLD_WIDTH - width / 2;
			velocity.x = 0;
		}

		bounds.x = position.x - bounds.width / 2;
	}	

	public void infectMe() {
				
		width = width * 0.8f;
		bounds.width = bounds.width * 0.8f;
		
		state = INFECTED;		
	}
	
	public void healMe() {
		
		width = width * 1.25f;
		bounds.width = bounds.width * 1.25f;
		
		state = HEALTHY;
	}
}
