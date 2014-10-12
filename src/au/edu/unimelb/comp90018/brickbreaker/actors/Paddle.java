package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;

/**
 * Paddle class... what a surprise!. However, it has a customisable width
 * through its constructor. It is particularly useful when generating levels
 * from an XML file.
 * 
 */
public class Paddle extends DynamicGameObject {

	public static final int HEALTHY = 0;
	public static final int INFECTED = 1;

	public static final float PADDLE_HEIGHT = 16;
	public float width = 96; // Default width just in case there is a failure!

	public int state; // Could get HEALTHY or INFECTED states!

	public Paddle(float x, float y, float width) {

		super(x, y, width, PADDLE_HEIGHT);
		this.width = width;

		state = HEALTHY;
	}

	/**
	 * Updates the velocity, position and bounds of the paddle according to the
	 * acceleration on X axis and the elapsed time.
	 * 
	 * @param deltaTime
	 * @param accelX
	 * @param touchX
	 *            Informs how to update paddle's position: (a) if touchX <> -1
	 *            then paddle's position on X = touchX (touch on the screen);
	 *            (b) otherwise, compute the position based on velocity and
	 *            time.
	 */
	public void update(float deltaTime, float accelX, float touchX) {

		if ((velocity.x < 0 && accelX >= 0) || (velocity.x > 0 && accelX <= 0)) {
			velocity.x = 0;
		} else {
			velocity.add(accelX * deltaTime, 0);
		}

		if (touchX == -1) {
			position.add(velocity.x * deltaTime, 0);
		} else {
			position.x = touchX;
		}

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

	/**
	 * When a virus collides with a paddle, the latter gets infected. Then, its
	 * width is reduced to 80%. :(
	 */
	public void infectMe() {

		width = width * 0.8f;
		bounds.width = bounds.width * 0.8f;

		state = INFECTED;
	}

	/**
	 * But, when the paddle collides with a heart, it comes back to life... Its
	 * width is increased by 25%.
	 */
	public void healMe() {

		width = width * 1.25f;
		bounds.width = bounds.width * 1.25f;

		state = HEALTHY;
	}
}
