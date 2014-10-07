package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class User {

	public final static String file = "brickbreaker.data";

	public String username;

	public static void create(String username) {

		try {
			FileHandle filehandle = Gdx.files.external(file);
			if (!filehandle.exists()) {
				filehandle.writeString(username + "\n", true);
				// levels
				filehandle.writeString(Integer.toString(1) + "\n", true);// only
																			// the
																			// first
																			// level
																			// unlocked
				for (int i = 0; i < 8; i++) {
					filehandle.writeString(Integer.toString(0) + "\n", true);// all
																				// levels
																				// locked
				}
				// scores
				for (int i = 0; i < 9; i++) {
					filehandle.writeString(Integer.toString(0) + "\n", true);// all
																				// scores
																				// 0
				}
				Gdx.app.log("file created: ", file);
			}
		} catch (Throwable e) {
		}

	}

	public static void updatescore(int level, int score) {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			String[] strings = filehandle.readString().split("\n");
			strings[level + 9] = Integer.toString(score);

			save(strings);
		} catch (Throwable e) {
		}

	}

	public static void unlocklevels(int level) {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			String[] strings = filehandle.readString().split("\n");
			strings[level] = Integer.toString(1);

			save(strings);
		} catch (Throwable e) {
		}
	}

	public static void save(String[] strings) {
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

	public static void getlevelscore(int level) {
		int score = 0;
		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");
		score = Integer.parseInt(strings[level + 9]);
		Gdx.app.log("Level score: ", Integer.toString(score));
		// return score;
	}

	public static void getlevelunlocked(int level) {
		int levelunlocked = 0;
		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");
		levelunlocked = Integer.parseInt(strings[level]);
		Gdx.app.log("Unlocked level: ", Integer.toString(levelunlocked));

		// return levelunlocked;
	}

	public static void user() {

		FileHandle filehandle = Gdx.files.external(file);
		String[] strings = filehandle.readString().split("\n");
		String user = strings[0];
		Gdx.app.log("Unlocked level: ", user);

		// return user;
	}

	public static int gettotalscore() {
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
