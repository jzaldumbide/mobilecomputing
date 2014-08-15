package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import android.content.Context;
import android.os.Build;
import android.view.View;
import au.edu.unimelb.comp90018.brickbreaker.framework.Input;

public class AndroidInput implements Input {

	AccelerometerHandler accelHandler;
	TouchHandler touchHandler;

	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
		accelHandler = new AccelerometerHandler(context);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR) {
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		} else {
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAccelX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAccelY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAccelZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
