package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 
 * @author Oscar
 * 
 *         Note that we do not need to perform any synchronization here, even
 *         though the onSensorChanged() method might be called in a different
 *         thread. The Java memory model guarantees that writes and reads, to
 *         and from, primitive types such as Boolean, int, or byte are atomic.
 *         In this case, it’s OK to rely on this fact since we aren’t doing
 *         anything more complex than assigning a new value. We’d need to have
 *         proper synchronization if this were not the case (for example, if we
 *         did something with the member variables in the onSensorChanged()
 *         method).
 */
public class AccelerometerHandler implements SensorEventListener {

	float accelX;
	float accelY;
	float accelZ;

	public AccelerometerHandler(Context context) {
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			Sensor accelerometer = manager.getSensorList(
					Sensor.TYPE_ACCELEROMETER).get(0);
			manager.registerListener(this, accelerometer,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		accelX = event.values[0];
		accelY = event.values[1];
		accelZ = event.values[2];
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public float getAccelX() {
		return accelX;
	}

	public float getAccelY() {
		return accelY;
	}

	public float getAccelZ() {
		return accelZ;
	}

}
