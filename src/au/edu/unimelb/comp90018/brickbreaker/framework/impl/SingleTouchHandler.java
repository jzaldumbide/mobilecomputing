package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import au.edu.unimelb.comp90018.brickbreaker.framework.Input.TouchEvent;

public class SingleTouchHandler implements TouchHandler {

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
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
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
