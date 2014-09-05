package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import com.badlogic.gdx.math.Rectangle;
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

	/**
	 * Set the new position of the object and update the bounds coord.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
		updateBounds();
	}

	/**
	 * Set the new x position of the object and update the bounds coord.
	 * 
	 * @param x
	 */
	public void setX(float x) {
		position.x = x;
		updateBounds();
	}

	/**
	 * Set the new y position of the object and update the bounds coord.
	 * 
	 * @param y
	 */
	public void setY(float y) {
		position.y = y;
		updateBounds();
	}

	/**
	 * Set the bounds width parameter
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		bounds.width = width;
	}

	/**
	 * Set the bounds height parameter
	 * 
	 * @param height
	 */
	public void setHeight(float height) {
		bounds.height = height;
	}

	/**
	 * Return position x of the Object
	 * 
	 * @return position.x
	 */
	public float getX() {
		return position.x;
	}

	/**
	 * Return position y of the Object
	 * 
	 * @return position.y
	 */
	public float getY() {
		return position.y;
	}

	/**
	 * Return bounds width of the Rectangle
	 * 
	 * @return bounds.width
	 */
	public float getWidth() {
		return bounds.width;
	}

	/**
	 * Return bounds width of the Rectangle
	 * 
	 * @return position.y
	 */
	public float getHeight() {
		return bounds.height;
	}

	/**
	 * Returns the bounds rectangle
	 * 
	 * @return bounds
	 */
	public Rectangle getRectangle() {
		return bounds;
	}

	/**
	 * Adds new amount to the position x of the object
	 * 
	 * @param amount
	 */
	public void addToX(float amount) {
		this.position.x += amount;
		updateBounds();
	}

	/**
	 * Adds new amount to the position y of the object
	 * 
	 * @param amount
	 */
	public void addToY(float amount) {
		this.position.y += amount;
		updateBounds();
	}

	/**
	 * It's necessary to keep the x and y bounds synchronized with the position
	 * in x and y
	 */
	public void updateBounds() {
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
	}
}
