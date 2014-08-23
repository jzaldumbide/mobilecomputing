package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ball extends DynamicGameObject {

	public static final float PADDLE_WIDTH = Assets.paddle.getRegionWidth();
	public static final float PADDLE_HEIGHT = Assets.paddle.getRegionHeight();

	public Ball(TextureRegion texture, float x, float y, float velocityX) {
		super(texture, x, y);
		velocity.x = velocityX;
	}

	public void update(float deltaTime) {

		

	}

	
}
