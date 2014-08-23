package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import com.badlogic.gdx.math.Rectangle;


/**
 * Every GameObject has associated position, texture and bounds. Bounds helps to keep track of collisions
 * @author Diego
 *
 */
public class GameObject {
	public final Rectangle position;
	//public final Rectangle bounds;

	public GameObject () {	
		//this.position = new Vector2();
		this.position = new Rectangle();
	}
	
	public GameObject (float x, float y, float width, float height) {
		
		this.position = new Rectangle(x, y,width, height);
		//this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}	
	
	public void setWidth(float width) {
		position.width = width;
	}
	
	public void setHeight(float height) {
		position.height = height;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}	
	
	public float getWidth() {
		return position.width;
	}
	
	public float getHeight() {
		return position.height;
	}	
	
	public Rectangle getRectangle() {
		return position;
	}
	
	public void addToX(float amount) {
		this.position.x += amount;
	}
	
	public void addToY(float amount) {
		this.position.y += amount;
	}
}
