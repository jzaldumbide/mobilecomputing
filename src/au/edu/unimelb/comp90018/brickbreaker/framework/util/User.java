package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class User {

	public final static String file = Settings.playerDataFile;

	public String username;

	public static void create(String username) {

		try {
			FileHandle filehandle = Gdx.files.external(file);
			if (!filehandle.exists()) {
				filehandle.writeString(username + "\n", true);
				// levels
				filehandle.writeString(Integer.toString(1) + "\n", true);
				for (int i = 0; i < 8; i++) {
					filehandle.writeString(Integer.toString(0) + "\n", true);
				}
				// scores
				for (int i = 0; i < 9; i++) {
					filehandle.writeString(Integer.toString(0) + "\n", true);
				}
				Gdx.app.log("file created: ", file);
			}
		} catch (Throwable e) {
		}

	}

	/**
	 * Update level score
	 * 
	 * @param level
	 * @param score
	 */
	public static void updateScore(int level, int score) {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			String[] strings = filehandle.readString().split("\n");
			strings[level + 9] = Integer.toString(score);

			savePlayerDataInfo(strings);
		} catch (Throwable e) {
		}

	}

	/**
	 * Unlock some Level
	 * 
	 * @param level
	 */
	public static void unlockLevel(int level) {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			String[] strings = filehandle.readString().split("\n");
			strings[level] = Integer.toString(1);

			savePlayerDataInfo(strings);
		} catch (Throwable e) {
		}
	}

	/**
	 * Save player info
	 * 
	 * @param strings
	 */
	public static void savePlayerDataInfo(String[] strings) {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			filehandle.writeString("", false);
			for (int i = 0; i < 19; i++) {
				filehandle.writeString(strings[i] + "\n", true);
				Gdx.app.log("grabado en archivo: ", strings[i] + "\n");
			}

		} catch (Throwable e) {
		}

	}

	/**
	 * Return player score
	 * 
	 * @param level
	 */
	public static int getLevelScore(int level) {
		int score = 0;
		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");
		score = Integer.parseInt(strings[level + 9]);
		Gdx.app.log("Level score: ", Integer.toString(score));
		return score;
	}

	/**
	 * Return level state
	 * 
	 * @param level
	 * @return true or false
	 */
	public static boolean isLevelUnlocked(int level) {
		int levelunlocked = 0;
		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");
		levelunlocked = Integer.parseInt(strings[level]);
		Gdx.app.log("Unlocked level: ", Integer.toString(levelunlocked));

		if (levelunlocked == 0)
			return true;

		return false;
	}

	/**
	 * Return player Name
	 * 
	 * @return playerName
	 */
	public static String getPlayerName() {

		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");
		String playerName = strings[0];
		Gdx.app.log("Unlocked level: ", playerName);

		return playerName;
	}

	/**
	 * Get total score
	 */
	public static int getTotalScore() {
		int totalscore = 0;
		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");

		for (int i = 0; i < 9; i++) {
			totalscore = totalscore + Integer.parseInt(strings[i + 10]);

		}
		Gdx.app.log("totalscore: ", Integer.toString(totalscore));
		return totalscore;
	}
}
