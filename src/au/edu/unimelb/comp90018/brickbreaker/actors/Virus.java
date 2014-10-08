package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;

import com.badlogic.gdx.math.Vector2;

public class Virus extends DynamicGameObject {

	public static final float VIRUS_WIDTH = 25;
	public static final float VIRUS_HEIGHT = 25;

	public Virus(float x, float y, Vector2 velocity) {
		super(x, y, VIRUS_WIDTH, VIRUS_HEIGHT);
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
