package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;

import com.badlogic.gdx.math.Vector2;

public class ExtraLife extends DynamicGameObject {

	public static final float EXTRA_LIFE_WIDTH = 25;
	public static final float EXTRA_LIFE_HEIGHT = 25;

	public ExtraLife(float x, float y, Vector2 velocity) {
		super(x, y, EXTRA_LIFE_WIDTH, EXTRA_LIFE_HEIGHT);
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
		position.y = -10000;
		bounds.y = position.y - bounds.height / 2;
	}
}
