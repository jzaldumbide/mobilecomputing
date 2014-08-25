package au.edu.unimelb.comp90018.brickbreaker.actors;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends DynamicGameObject {

	public static final float BALL_WIDTH = Assets.red_ball.getRegionWidth();
	public static final float BALL_HEIGHT = Assets.red_ball.getRegionHeight();

	private int directionX, directionY;

	public Ball(TextureRegion texture, float x, float y, float xyVelocity) {
		super(texture, x, y);
		velocity.x = xyVelocity;
		velocity.y = xyVelocity;

		directionX = directionY = 1;
	}

	public void update(float deltaTime, World world) {

		if (hasHitPaddle(world.paddle.getRectangle()))
			directionY = directionY * -1; // Change y direction
		if (hasHitWalls()) {
			directionX = directionX * -1; // Change x direction
			Assets.incorrectSound.play();
		}/*if (hasHitBrick(world.bricks)) {
			directionY = directionY * -1; // Change x direction
		}*/

		super.setX(super.getX() + velocity.x * deltaTime * directionX);
		super.setY(super.getY() + velocity.y * deltaTime * directionY);

	}

	/**
	 * Check for collisions with walls
	 * 
	 * @return
	 */
	private boolean hasHitWalls() {

		if (super.getX() + BALL_WIDTH >= Settings.GAME_WIDTH) { // Has hit the right wall
			super.setX(Settings.GAME_WIDTH - BALL_WIDTH);
			return true;
		} else if (super.getX() <= 0) { // Has hit the left wall
			super.setX(0);
			return true;
		} else if (super.getY() + BALL_HEIGHT >= Settings.GAME_HEIGHT) { // Has hit the top wall
			super.setY(Settings.GAME_HEIGHT - BALL_HEIGHT);
			directionY = directionY * -1;
			return true;
		}

		else
			return false;
	}

	/**
	 * Check for collisions with paddle
	 * 
	 * @return
	 */
	private boolean hasHitPaddle(Rectangle paddle) {
		if (bounds.overlaps(paddle)) { // If overlaps with paddle
			super.setY(paddle.y + paddle.getHeight());
			return true;
		} else
			return false;
	}
	
	/**
	 * Check for collisions with brick
	 * 
	 * @return
	 */
	private boolean hasHitBrick(List<Brick> bricks) {
		
		for (int i=0; i<bricks.size();i++){
			
			Rectangle brickBounds = bricks.get(i).getRectangle();
			
			if (bounds.overlaps(brickBounds)) { // If overlaps with paddle
				super.setY(brickBounds.y + brickBounds.getHeight());
				bricks.get(i).dispose();
				return true;
			} else
				return false;
		}
		
		return false;

	}

}
