package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import au.edu.unimelb.comp90018.brickbreaker.framework.Input.TouchEvent;
import au.edu.unimelb.comp90018.brickbreaker.framework.Pool.PoolObjectFactory;
import au.edu.unimelb.comp90018.brickbreaker.framework.Pool;

/**
 * 
 * @author Oscar
 * 
 *         The class implements the TouchHandler interface, which also means
 *         that we must implement the OnTouchListener interface.
 */
public class SingleTouchHandler implements TouchHandler {

	boolean isTouched;
	int touchX;
	int touchY;
	Pool<TouchEvent> touchEventPool;
	// Read pages 208 and 209...
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	// We use these to cope with different screen resolutions.
	float scaleX;
	float scaleY;

	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);

		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			TouchEvent touchEvent = touchEventPool.newObject();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = false;
				break;
			case MotionEvent.ACTION_MOVE:
				touchEvent.type = TouchEvent.TOUCH_DRAGGED;
				isTouched = true;
				break;
			}
			touchEvent.x = touchX = (int) (event.getX() * scaleX);
			touchEvent.y = touchY = (int) (event.getY() * scaleY);
			touchEventsBuffer.add(touchEvent);

			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			if (pointer == 0)
				return isTouched;
			else
				return false;
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			return touchX;
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			return touchY;
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// To fully understand this method, please read pages 208 and 209 of our
		// reference book.

		synchronized (this) {
			int len = touchEvents.size();
			// We could use for each construction but I think this is faster...
			for (int i = 0; i < len; i++)
				touchEventPool.free(touchEvents.get(i));

			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();

			return touchEvents;
		}
	}

}
