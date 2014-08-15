package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import au.edu.unimelb.comp90018.brickbreaker.framework.Input.TouchEvent;
import au.edu.unimelb.comp90018.brickbreaker.framework.Pool;
import au.edu.unimelb.comp90018.brickbreaker.framework.Pool.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler {

	private static final int MAX_TOUCHPOINTS = 10;

	boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
	int[] touchX = new int[MAX_TOUCHPOINTS];
	int[] touchY = new int[MAX_TOUCHPOINTS];
	int[] id = new int[MAX_TOUCHPOINTS];
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;

	public MultiTouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
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

			// This part is hard to understand. When a device supports
			// multitouch, the onTouch event receives information not only about
			// the type of touch, i.e. down, up, move, etc., but also the finger
			// (pointer) which is doing the action.
			// This dual information comes from the event.getAction() method and
			// therefore we need to "break" it. By using bitwise AND operator
			// (to mask part of the information) and shifting the bits (when the
			// info is in the most significant part of the bytes) you will get
			// that dual info... :)

			int action = event.getAction() & MotionEvent.ACTION_MASK;

			// Just in case, pointer index and pointer id are different. :(
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

			int pointerCount = event.getPointerCount();

			TouchEvent touchEvent;

			for (int i = 0; i < MAX_TOUCHPOINTS; i++) {

				if (i >= pointerCount) {

					// Clean the remaining spots in the arrays.

					isTouched[i] = false;
					id[i] = -1;
					continue;
				}

				// You get the pointer id based on the pointer index. That is
				// why we bothered getting this index before.
				int pointerId = event.getPointerId(pointerIndex);

				// TODO: I don't catch this IF yet :(

				// Why do we use event.getAction() when we've got the action
				// variable?
				// It is clear that we do not have to work with the for
				// iteration when the pointer index is other than the one which
				// did the action... BUT, why to filter the MOVE action?

				if (event.getAction() != MotionEvent.ACTION_MOVE
						&& i != pointerIndex) {
					continue;
				}

				// There are ACTION_DOWN and ACTION_POINTER_DOWN because the
				// former is used with the first pointer and the latter with the
				// rest.

				switch (action) {

				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DOWN;

					touchEvent.pointer = pointerId; // This field is not used in
													// SingleTouchHandler

					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					touchEventsBuffer.add(touchEvent);
					break;

				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_UP;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					isTouched[i] = false;
					id[i] = -1;
					touchEventsBuffer.add(touchEvent);
					break;

				case MotionEvent.ACTION_MOVE:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					touchEventsBuffer.add(touchEvent);
					break;
				}
			}
			return true;
		}

	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS)
				return false;
			else
				return isTouched[index];
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if (index < 0 || index >= MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized (this) {
			int len = touchEvents.size();
			for (int i = 0; i < len; i++)
				touchEventPool.free(touchEvents.get(i));
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

	// Helper method.
	private int getIndex(int pointerId) {
		for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
			if (id[i] == pointerId) {
				return i;
			}
		}
		return -1;
	}

}
