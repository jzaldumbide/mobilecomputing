package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.GameObject;

public class Brick extends GameObject {

	public static final float BRICK_WIDTH = 16;
	public static final float BRICK_HEIGHT = 16;

	public Brick(float x, float y) {
		super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
	}

	public void update(float deltaTime) {

		
	}

}
