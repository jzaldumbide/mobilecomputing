package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import android.content.Context;
import android.os.Build;
import android.view.View;
import au.edu.unimelb.comp90018.brickbreaker.framework.Input;

/**
 * 
 * @author Oscar
 * 
 *         The Input implementation of our game framework ties together all the
 *         handlers we have developed. Any method calls are delegated to the
 *         corresponding handler. The only interesting part of this
 *         implementation is choosing which TouchHandler implementation to use,
 *         based on the Android version the device is running.
 */
public class AndroidInput implements Input {

	AccelerometerHandler accelHandler;
	TouchHandler touchHandler;

	public AndroidInput(Context context, View view, float scaleX, float scaleY) {

		accelHandler = new AccelerometerHandler(context);

		// Not sure if ECLAIR corresponds to SDK = 5 (Android version 2.0)
		// This technique is called FRAGMENTATION (a sophisticated name for a
		// simple selection of the appropriate handler :) )

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR) {
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		} else {
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		return accelHandler.getAccelX();
	}

	@Override
	public float getAccelY() {
		return accelHandler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		return accelHandler.getAccelZ();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

}
