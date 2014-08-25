package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Paddle extends DynamicGameObject {

	public static final float PADDLE_WIDTH = Assets.paddle.getRegionWidth();
	public static final float PADDLE_HEIGHT = Assets.paddle.getRegionHeight();

	public Paddle(TextureRegion texture, float x, float y, float velocityX) {
		super(texture, x, y);
		velocity.x = velocityX;
	}

	public void update(float deltaTime, OrthographicCamera guiCam) {

		/* the central position of the paddle on the screen */
		float coordPaddle = position.x + texture.getRegionWidth() / 2; 
																		
		if (Gdx.input.isTouched()) {

			/*
			 * This is important! We need it to update the camera coordinates in
			 * order to calculate the relative position of paddle
			 */
			Vector3 input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			guiCam.unproject(input);

			float coordX = input.x; // this is touched coordinate in x

			if (coordPaddle >= coordX - 5 && coordPaddle <= coordX + 5)
				coordX = coordPaddle;

			if (coordX > coordPaddle + texture.getRegionWidth() / 2) {
				moveRight();
			}

			if (coordX < coordPaddle - texture.getRegionWidth() / 2) {
				moveLeft();
			}

		}

	}

	/**
	 * Move paddle to the left
	 */
	public void moveLeft() {
		super.setX(super.getX() - (velocity.x * Gdx.graphics.getDeltaTime())); // x=v*t
																				
		// correct, if paddle went ouside screen
		if (super.getX() < 0) {
			super.setX(0);
		}
	}

	/**
	 * Move paddle to the right
	 */
	public void moveRight() {
		super.setX(super.getX() + (velocity.x * Gdx.graphics.getDeltaTime())); // x=v*t

		// correct, if paddle went ouside screen
		if (super.getX() > Settings.GAME_WIDTH - super.getWidth()) {
			super.setX(Settings.GAME_WIDTH - super.getWidth());
		}
	}

}
