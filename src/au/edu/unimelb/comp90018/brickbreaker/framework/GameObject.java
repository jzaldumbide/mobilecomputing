package au.edu.unimelb.comp90018.brickbreaker.framework;


import com.badlogic.gdx.math.Vector2;

/**
 * Every GameObject has associated position and bounds. Bounds helps to keep
 * track of collisions.
 * 
 * @author Diego
 * 
 */
public class GameObject {
	public final Vector2 position;
	public final Rectangle2 bounds;

	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle2(x - width / 2, y - height / 2, width, height);
	}

}
