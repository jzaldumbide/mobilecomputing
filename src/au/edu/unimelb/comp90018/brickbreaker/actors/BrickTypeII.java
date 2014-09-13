package au.edu.unimelb.comp90018.brickbreaker.actors;

public class BrickTypeII extends BrickAdapter implements Brick {

	static float WIDTH = 32;
	static float HEIGHT = 16;

	int hitsLeftToPulverise;
	
	public BrickTypeII(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		hitsLeftToPulverise = 2;
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
