/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.framework.model;

/**
 * @author achaves
 *
 */
public class Ball {
	int radius;
	int speed;
	/**
	 * @param radius
	 * @param speed
	 */
	public Ball(int radius, int speed) {
		super();
		this.radius = radius;
		this.speed = speed;
	}
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
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
		Ball ball = (Ball)o;
		if (ball.getRadius()!=this.radius || ball.getSpeed()!=this.speed)
			return false;
		return true;
	}
	
	
}
