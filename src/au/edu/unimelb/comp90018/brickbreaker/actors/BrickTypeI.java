package au.edu.unimelb.comp90018.brickbreaker.actors;

/**
 * Concrete brick class which needs ONE hit to be pulverised.
 * 
 */
public class BrickTypeI extends BrickAdapter {

	static float WIDTH = 32;
	static float HEIGHT = 16;

	public BrickTypeI(float x, float y) {
		super(x, y, WIDTH, HEIGHT, 1);
	}

	@Override
	public void hitMe() {
		hitsLeftToPulverise--;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPulverised() {
		return hitsLeftToPulverise == 0;
	}

}
