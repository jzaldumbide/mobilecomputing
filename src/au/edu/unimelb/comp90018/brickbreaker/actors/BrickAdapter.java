package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.GameObject;

public abstract class BrickAdapter extends GameObject implements Brick {

	public float width;
	public float height;
	
	public int hitsLeftToPulverise;

	public BrickAdapter(float x, float y, float width, float height, int hitsLeftToPulverise) {
		super(x, y, width, height);
		
		this.width = width;
		this.height = height;
		this.hitsLeftToPulverise = hitsLeftToPulverise;
	}

}
