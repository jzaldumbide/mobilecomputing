package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;

import com.badlogic.gdx.math.Vector2;

public class Ball extends DynamicGameObject {

	public static final float BALL_WIDTH = 1;
	public static final float BALL_HEIGHT = 1;

	public Ball(float x, float y, Vector2 velocity) {
		super(x, y, BALL_WIDTH, BALL_HEIGHT);
		this.velocity.set(velocity);
	}

	public void update(float deltaTime) {

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);

		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;

		if (position.x < BALL_WIDTH / 2 || position.x > (World.WORLD_WIDTH - BALL_WIDTH / 2)) {
			velocity.x *= -1;
		}
		
		if (position.y > (World.WORLD_HEIGHT - BALL_HEIGHT / 2)) {
			velocity.y *= -1;
		}
	}

	public void hitPaddle(float paddleVelX) {
		velocity.x += paddleVelX;
		// TODO: Consider side of the collision
		velocity.y *= -1;
	}

	public void hitBrick() {
//		velocity.x += paddleVelX;
//		velocity.y *= -1;		
	}
}
