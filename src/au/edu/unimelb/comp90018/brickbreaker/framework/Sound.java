package au.edu.unimelb.comp90018.brickbreaker.framework;

/**
 * 
 * @author Oscar
 * 
 *         Sound lets us play back sound effects that are stored in RAM.
 */
public interface Sound {

	public void play(float volume);

	public void dispose();

}
