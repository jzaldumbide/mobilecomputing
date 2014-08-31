package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import com.badlogic.gdx.math.Vector2;

/**
 * Some Objects may have movement characteristics as velocity and accel.
 * 
 * @author Diego
 * 
 */
public class DynamicGameObject extends GameObject {

	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}

	// @Override
	// public void dispose() {
	//
	// }

}
