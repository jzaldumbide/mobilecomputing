package au.edu.unimelb.comp90018.brickbreaker.framework;

public interface Game {

	public FileIO getFileIO();

	public Audio getAudio();
	
	public Graphics getGraphics();
	
    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}
