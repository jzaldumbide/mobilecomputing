package au.edu.unimelb.comp90018.brickbreaker.framework;

/**
 * 
 * @author Diego
 * 
 *         Collideable is responsible for dealing with collisions between objects
 */
public interface Collideable {
	public boolean isDying();

	public void contact(Collideable other);

	public void playHitSound();
}
