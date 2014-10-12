package au.edu.unimelb.comp90018.brickbreaker.actors;

/**
 * Concrete brick class which needs FOUR hits to be pulverised.
 * 
 */
public class BrickTypeIII extends BrickAdapter implements Brick {

	static float WIDTH = 32;
	static float HEIGHT = 16;

	public BrickTypeIII(float x, float y) {
		super(x, y, WIDTH, HEIGHT, 4);
	}

	@Override
	public boolean isPulverised() {
		return hitsLeftToPulverise == 0;
	}

	@Override
	public void hitMe() {
		hitsLeftToPulverise--;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

}