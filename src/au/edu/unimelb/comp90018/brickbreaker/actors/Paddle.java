package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Paddle extends DynamicGameObject{

	
	public static final float PADDLE_WIDTH = Assets.paddle.getRegionWidth();
	public static final float PADDLE_HEIGHT = Assets.paddle.getRegionHeight();
	
	
	public Paddle (TextureRegion texture, float x, float y, float velocityX) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT, texture);
		velocity.x = velocityX;
	}
	
	public void update (float deltaTime) {
			
		float coordPaddle = bounds.x + texture.getRegionWidth() / 2; // central position of paddle in x
		
		if(Gdx.input.isTouched()) { 
			
			float coordX = 320 - Gdx.input.getX(); 
			
			if(coordPaddle >= coordX - 5 && coordPaddle <= coordX + 5) 
				coordX = coordPaddle; 
			
			if(coordX > coordPaddle) { //move to the right
				if(hitRight()) 
					//bounds.x = 320 - texture.getRegionWidth();
					position.x = 320 - texture.getRegionWidth();
				else
					//bounds.x = bounds.x + velocity.x * deltaTime; 
					position.x = position.x + velocity.x * deltaTime; 
			}
			
			
			if(coordX < coordPaddle) { //move to the left
				if(hitLeft()) 
					//bounds.x = 0;
					position.x = 0;
				else
					//bounds.x = bounds.x - velocity.x * deltaTime; 
					position.x = position.x - velocity.x * deltaTime; 
			}
			
//			position.add(velocity.x * deltaTime, 0);
//			bounds.x = position.x - texture.getRegionWidth() / 2;
//			bounds.y = position.y - PADDLE_HEIGHT / 2;
//
//			if(Gdx.input.isTouched()) { 
//				
//				if (position.x < texture.getRegionWidth() / 2) {
//					velocity.x = -velocity.x;
//					position.x = texture.getRegionWidth() / 2;
//				}
//				if (position.x > 320 - texture.getRegionWidth() / 2) {
//					velocity.x = -velocity.x;
//					position.x = 320 - texture.getRegionWidth() / 2;
//				}
//				
//			}
			
		}
		
	}

		protected boolean hitRight() { 
			return position.x + texture.getRegionWidth()/2 >= 320;
		}
		protected boolean hitLeft() { 
			return position.x <= 0;
		}
}
