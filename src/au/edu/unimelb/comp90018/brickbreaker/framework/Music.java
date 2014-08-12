package au.edu.unimelb.comp90018.brickbreaker.framework;

/**
 * 
 * @author Oscar
 * 
 *         Music streams bigger music files from the disk to the audio card.
 */
public interface Music {

	public void play();

	public void stop();

	public void pause();

	public void setLooping(boolean looping);

	public void setVolume(float volume);

	public boolean isPlaying();

	public boolean isStopped();

	public boolean isLooping();

	public void dispose();
}
