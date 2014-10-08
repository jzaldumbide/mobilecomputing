package au.edu.unimelb.comp90018.brickbreaker.framework.util;


public class Settings {

	public static final int TARGET_WIDTH = 320;
	public static final int TARGET_HEIGHT = 480;
	public static final float ASPECT_RATIO = (float) TARGET_WIDTH / (float) TARGET_HEIGHT;
	
	/* Set here default game properties */
	public static boolean musicEnabled = false;
	public static boolean soundEnabled = true;
	public static boolean accelerometerEnabled = false;
	public static String playerDataFile = "brickbreaker.data";

}
