package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.util.List;

/**
 * 
 * @author Oscar
 * 
 *         This interface is designed to handle any kind of Input, e.g. keyboard
 *         events, touch events. For the time being, we are going to deal only
 *         with touch events.
 */
public interface Input {

	// Recall that a static class is similar to a top-level class, yet it is
	// located within another class due to packaging convenience. Nevertheless,
	// as it is static, it cannot access instance members. It can access them by
	// using their object references.
	
	public static class TouchEvent {
		public static final int TOUCH_DOWN = 0;
		public static final int TOUCH_UP = 1;
		public static final int TOUCH_DRAGGED = 2;

		public int type;
		public int x, y;
		public int pointer;
	}

	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public float getAccelX();

	public float getAccelY();

	public float getAccelZ();

	public List<TouchEvent> getTouchEvents();
}
