package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;

public class Paddle extends DynamicGameObject {

//	public static final float PADDLE_WIDTH = 96;
	public static final float PADDLE_HEIGHT = 16;
	
	public float width = 96;

//	public final Random rand;
	public int state;
//	float stateTime;
	
	public Paddle(float x, float y, float width) {
		
		super(x, y, width, PADDLE_HEIGHT);
		this.width = width;
		
		state = 0;
//		stateTime = 0;
		
//		rand = new Random();
	}

	public void update(float deltaTime, float accelX) {
		
////		if (rand.nextFloat() > 0.9f && stateTime > 5000) {
//		if (stateTime > 5000) {
////		if (rand.nextFloat() > 0.9f) {
//			if (state == 0) {
//				width = width * 0.8f;
//				bounds.width = bounds.width * 0.8f;
//				state = 1;
//			} else if (state == 1) {
//				width = width * 1.25f;
//				bounds.width = bounds.width * 1.25f;
//				state = 0;				
//			}
//			stateTime = 0;
//		}
		
		if ((velocity.x < 0 && accelX >= 0) || (velocity.x > 0 && accelX <= 0)) {
			velocity.x = 0;
		} else {
			velocity.add(accelX * deltaTime, 0);
		}

		position.add(velocity.x * deltaTime, 0);

		if (position.x < width / 2) {
			position.x = width / 2;
			velocity.x = 0;
		}

		if (position.x > (World.WORLD_WIDTH - width / 2)) {
			position.x = World.WORLD_WIDTH - width / 2;
			velocity.x = 0;
		}

		bounds.x = position.x - bounds.width / 2;
		
//		stateTime += deltaTime;
	}	

}
