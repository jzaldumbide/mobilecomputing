package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;

public class Paddle extends DynamicGameObject {

	public static final float PADDLE_WIDTH = 4;
	public static final float PADDLE_HEIGHT = 0.5f;

	public Paddle(float x, float y) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
	}

	public void update(float deltaTime, float accelX) {

		if ((velocity.x < 0 && accelX >= 0) || (velocity.x > 0 && accelX <= 0)) {
			velocity.x = 0;
		} else {
			velocity.add(accelX * deltaTime, 0);
		}

		position.add(velocity.x * deltaTime, 0);

		if (position.x < PADDLE_WIDTH / 2) {
			position.x = PADDLE_WIDTH / 2;
			velocity.x = 0;
		}

		if (position.x > (World.WORLD_WIDTH - PADDLE_WIDTH / 2)) {
			position.x = World.WORLD_WIDTH - PADDLE_WIDTH / 2;
			velocity.x = 0;
		}

		bounds.x = position.x - bounds.width / 2;		
	}

}
