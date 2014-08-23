package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * Every GameObject has associated position, texture and bounds. Bounds helps to keep track of collisions
 * @author Diego
 *
 */
public class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	public final TextureRegion texture;

	public GameObject (float x, float y, float width, float height, TextureRegion texture) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
		this.texture = texture;
	}
}
