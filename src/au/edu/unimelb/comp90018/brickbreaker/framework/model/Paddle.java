/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.framework.model;

/**
 * @author achaves
 *
 */
public class Paddle {
	int x;
	int y;
	int width;
	int speed;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param speed
	 */
	public Paddle(int width, int x, int y, int speed) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.speed = speed;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		Paddle paddle = (Paddle)o;
		if (paddle.getSpeed()!=this.speed || paddle.getX()!=this.x || paddle.getY()!=this.y)
			return false;
		return true;
	}
	
	
}
