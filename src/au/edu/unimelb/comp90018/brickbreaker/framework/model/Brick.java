/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.framework.model;

/**
 * @author achaves
 *
 */
public class Brick {
	private int width;
	private int x;
	private int y;
	private String type;
	
	/**
	 * @param width
	 * @param x
	 * @param y
	 * @param type
	 */
	public Brick(int width, int x, int y, String type) {
		super();
		this.width = width;
		this.x = x;
		this.y = y;
		this.type = type;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		Brick brick = (Brick)o;
		if (brick.getWidth()!=this.width || brick.getX() != this.x || brick.getY() != this.y || brick.getType() != this.type){
			return false;
		}
		return true;
	}

	
	
	
}
