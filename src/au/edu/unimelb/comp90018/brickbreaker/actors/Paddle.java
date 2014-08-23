package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Paddle extends DynamicGameObject{

	
	public static final float PADDLE_WIDTH = Assets.paddle.getRegionWidth();
	public static final float PADDLE_HEIGHT = Assets.paddle.getRegionHeight();
	
	
	public Paddle (TextureRegion texture, float x, float y, float velocityX) {
		super(texture, x, y);
		velocity.x = velocityX;
	}
	
	public void update (float deltaTime) {
			
	float coordPaddle = position.x + texture.getRegionWidth() / 2; // central position of paddle in x
		
		if(Gdx.input.isTouched()) { 
			
			float coordX = Gdx.input.getX(); 
			
			if(coordPaddle >= coordX - 5 && coordPaddle <= coordX + 5) 
				coordX = coordPaddle; 
			
			if (coordX > coordPaddle){
				moveRight();
			}

			if (coordX < coordPaddle){
				moveLeft();
			}
			
		}
		
	}
	
	public void moveLeft() {
		super.setX(super.getX() - (velocity.x * Gdx.graphics.getDeltaTime()));
		// correct, if paddle went ouside screen
		if (super.getX() < 0) {
			super.setX(0);
		}
	}

	public void moveRight() {
		super.setX(super.getX() + (velocity.x * Gdx.graphics.getDeltaTime()));
		// correct, if paddle went ouside screen
		if (super.getX() > 320 - super.getWidth()) {
			super.setX(320 - super.getWidth());
		}
	}
		
}
