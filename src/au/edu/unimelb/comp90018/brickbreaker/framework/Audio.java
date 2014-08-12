package au.edu.unimelb.comp90018.brickbreaker.framework;

/**
 * 
 * @author Oscar
 * 
 *         Audio is responsible for creating Sound and Music instances from
 *         asset files.
 */
public interface Audio {

	public Music newMusic(String filename);

	public Sound newSound(String filename);
}
