package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {

	/* Set here default game properties */
	public static boolean soundEnabled = true;
	public static boolean accelerometerEnabled = false;
	public final static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
	public final static String file = ".brickbreaker";

	public static void load() {

		try {
			FileHandle filehandle = Gdx.files.external(file);

			String[] strings = filehandle.readString().split("\n");

			soundEnabled = Boolean.parseBoolean(strings[0]);
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i + 1]);
			}
		} catch (Throwable e) { 
			// :( It's ok we have defaults
		}
	}

	public static void save() {

		try {
			FileHandle filehandle = Gdx.files.external(file);

			filehandle.writeString(Boolean.toString(soundEnabled) + "\n", false);
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[i]) + "\n", true);
			}
		} catch (Throwable e) {
		}

	}

	public static void addScore(int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;
			}
		}
	}

//	/* Set here screens Width and Heights */
//	public static int GAME_WIDTH = 320;
//	public static int GAME_HEIGHT = 480;
//	public static final float GAME_ASPECT_RATIO = (float) GAME_WIDTH / (float) GAME_HEIGHT;
//
//	public static int LEFT_WALL_SPAN = 20;
//	public static int RIGHT_WALL_SPAN = 20;
//	public static int TOP_WALL_SPAN = 40;
//
//	/* Define default paddle velocity */
//	public static enum PaddleSpeed {
//		SLOW(200), NORMAL(500), FAST(700), ULTRA(1000);
//		private int speed;
//
//		private PaddleSpeed(int s) {
//			speed = s;
//		}
//
//		public int getSpeed() {
//			return speed;
//		}
//
//	}
//
//	/* Define default ball velocity */
//	public static enum BallSpeed {
//		SLOW(200), NORMAL(400), FAST(600), ULTRA(1000);
//		private int speed;
//
//		private BallSpeed(int s) {
//			speed = s;
//		}
//
//		public int getSpeed() {
//			return speed;
//		}
//
//	}

}
