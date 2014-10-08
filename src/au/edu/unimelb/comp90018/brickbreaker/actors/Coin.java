package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;

import com.badlogic.gdx.math.Vector2;

public class Coin extends DynamicGameObject {

	public static final float COIN_WIDTH = 20;
	public static final float COIN_HEIGHT = 20;

	public Coin(float x, float y, Vector2 velocity) {
		super(x, y, COIN_WIDTH, COIN_HEIGHT);
		this.velocity.set(velocity);
	}

	public void update(float deltaTime) {

		position.add(0, -9.8f * deltaTime * velocity.y);
		bounds.y = position.y - bounds.height / 2;
		
		if (position.y < 0)
			pulverize();
		
	}

	public void pulverize() {
		//disapear
		position.x = 10000;
		position.y = 10000;
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
	}
}
