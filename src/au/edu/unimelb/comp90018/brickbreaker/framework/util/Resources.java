package au.edu.unimelb.comp90018.brickbreaker.framework.util;

public class Resources {
	
	public static enum PaddleSpeed {
		SLOW(200),
		NORMAL(500),
		FAST(700),
		ULTRA(1000);
		private int speed;
		private PaddleSpeed(int s) { speed = s; }
		public int getSpeed() { return speed; }

	}
	
	public static enum BallSpeed {
		SLOW(200),
		NORMAL(400),
		FAST(600),
		ULTRA(1000);
		private int speed;
		private BallSpeed(int s) { speed = s; }
		public int getSpeed() { return speed; }

	}
	
	public static final int SCREEN_WIDTH = 480;
	public static final int SCREEN_HEIGHT = 320;
	public static final float ASPECT_RATIO = (float)SCREEN_WIDTH/(float)SCREEN_WIDTH;
	
	/**
	 * percentage the images are scaled down on initial display.
	 * (so images look alright when screen size is increased. 
	 * images in assets folder and corresponding INIT_IMAGE_SCALEDOWN value: 
	 * small: 1f
	 * medium: 0.5f
	 * big: 0.25f)
	 */
	public static final float INIT_IMAGE_SCALEDOWN = 0.5f;
	
	public static final int NUMBER_HIGHEST_LEVEL = 5;
	public static final int NUMBER_BALLS_START = 5;

}
