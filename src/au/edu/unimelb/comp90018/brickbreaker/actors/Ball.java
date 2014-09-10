package au.edu.unimelb.comp90018.brickbreaker.actors;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.Rectangle2;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.Rectangle2.RectangleSide;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;

import com.badlogic.gdx.math.Vector2;

public class Ball extends DynamicGameObject {

	public static final float BALL_WIDTH = 16;
	public static final float BALL_HEIGHT = 15;

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

	public void hitBrick(Rectangle2 brickBounds) {

		List<RectangleSide> sides = bounds.whichSidesOverlapMe(brickBounds);

		if (sides.contains(RectangleSide.Right) || sides.contains(RectangleSide.Left))
			velocity.x *= -1;
		if (sides.contains(RectangleSide.Top) || sides.contains(RectangleSide.Bottom))
			velocity.y *= -1;
	}
}
