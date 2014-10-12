package au.edu.unimelb.comp90018.brickbreaker.actors;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.Rectangle2;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.Rectangle2.RectangleSide;

import com.badlogic.gdx.math.Vector2;

/**
 * Ball class which has predefined dimensions. Its position, velocity and bounds
 * are updated from outside based on a delta time.
 */
public class Ball extends DynamicGameObject {

	public static final float BALL_WIDTH = 16;
	public static final float BALL_HEIGHT = 15;

	public Ball(float x, float y, Vector2 velocity) {
		super(x, y, BALL_WIDTH, BALL_HEIGHT);
		this.velocity.set(velocity);
	}

	/**
	 * Depending on a delta time, its new position is calculated by taking into
	 * account its current velocity. Collisions with the borders of the world
	 * are also controlled. Finally, its bounds are updated accordingly.
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);

		if (position.x < BALL_WIDTH / 2 || position.x > (World.WORLD_WIDTH - BALL_WIDTH / 2)) {
			if (position.x < BALL_WIDTH / 2)
				position.x = BALL_WIDTH / 2;
			if (position.x > (World.WORLD_WIDTH - BALL_WIDTH / 2))
				position.x = World.WORLD_WIDTH - BALL_WIDTH / 2;
			velocity.x *= -1;
		}

		if (position.y > (World.WORLD_HEIGHT - BALL_HEIGHT / 2)) {
			position.y = World.WORLD_HEIGHT - BALL_HEIGHT / 2;
			velocity.y *= -1;
		}

		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
	}

	/**
	 * This method is generally called when a collision with the paddle
	 * happened. It checks whether the collision was on the bottom of the ball.
	 * Thus, the ball bounces, otherwise, there is no action.
	 * 
	 * @param paddleBounds
	 * @param paddleVelX
	 * @return
	 */
	public boolean hitPaddle(Rectangle2 paddleBounds, float paddleVelX) {

		List<RectangleSide> sides = bounds.whichSidesOverlapMe(paddleBounds);

		if (sides.contains(RectangleSide.Bottom)) {
			velocity.x += paddleVelX;
			velocity.y *= -1;
			return true;
		}
		return false;
	}

	/**
	 * This method is generally called when a collision with a brick happened.
	 * It changes the direction of the ball's velocity vector either on the X or
	 * Y axis depending on the side of the ball where the collision occurred.
	 * 
	 * @param brickBounds
	 */
	public void hitBrick(Rectangle2 brickBounds) {

		List<RectangleSide> sides = bounds.whichSidesOverlapMe(brickBounds);

		if (sides.contains(RectangleSide.Right) || sides.contains(RectangleSide.Left))
			velocity.x *= -1;
		if (sides.contains(RectangleSide.Top) || sides.contains(RectangleSide.Bottom))
			velocity.y *= -1;
	}
}
