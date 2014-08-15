package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import android.view.View.OnTouchListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.Input.TouchEvent;

/**
 * 
 * @author Oscar
 * 
 *         Multitouch is only supported on Android versions greater than 1.6. We
 *         write two handlers, one using the single-touch API in Android 1.5,
 *         and another using the multitouch API in Android 2.0 and above. This
 *         is safe as long as we don’t execute the multitouch handler code on an
 *         Android device lower than version 2.0. The VM won’t load the code,
 *         and it won’t throw exceptions continuously. All we need to do is find
 *         out which Android version the device is running and instantiate the
 *         proper handler. In order to use our two handler classes
 *         interchangeably, we need to define this common interface. All
 *         TouchHandlers must implement the OnTouchListener interface, which is
 *         used to register the handler with a View. The methods of the
 *         interface correspond to the respective methods of the Input
 *         interface. The first three are for polling the state of a specific
 *         pointer ID, and the last is for getting TouchEvents with which to
 *         perform event-based input handling. Note that the polling methods
 *         take pointer IDs that can be any number and are given by the touch
 *         event.
 */
public interface TouchHandler extends OnTouchListener {

	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<TouchEvent> getTouchEvents();
}
