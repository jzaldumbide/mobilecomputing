package au.edu.unimelb.comp90018.brickbreaker.actors;

/**
 * Brick is an interface since we are trying to make the behaviour of the bricks
 * customisable when it gets hit.
 */
public interface Brick {

	public boolean isPulverised();

	public void hitMe();

	public void update(float deltaTime);

}
