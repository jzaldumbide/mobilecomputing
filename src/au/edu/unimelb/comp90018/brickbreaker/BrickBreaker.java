package au.edu.unimelb.comp90018.brickbreaker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import au.edu.unimelb.comp90018.brickbreaker.framework.network.LevelDownloader;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;
import au.edu.unimelb.comp90018.brickbreaker.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * BrickBreakerGame class that extends Game, which implements
 * ApplicationListener. It will be used as the "Main" libgdx class, the starting
 * point basically, in the core libgdx project.
 * 
 * @author Diego
 * 
 */
public class BrickBreaker extends Game {

	public SpriteBatch batcher;

	@Override
	public void create() {

		/* Initialize Settings and Assets */
		batcher = new SpriteBatch();
		Settings.load();
		Assets.load();

		/*Download all the levels and the highscores*/
		int maxLevels = 2;
		LevelDownloader ld = new LevelDownloader();
		try {
			for (int level = 1; level  <= maxLevels; level++){
				String gameLevel;
				gameLevel = ld.downloadGame("brickbreaker_level"+level+".xml");
				ld.persistGame("brickbreaker_level"+level+".xml", gameLevel);
			}
			String highScores = ld.downloadHighScores();
			ld.persistScores(highScores);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

		/*
		 * This is the call to the main screen. here you should call the
		 * MainMenuScreen and call GameScreen from there
		 */
		// setScreen(new MenuScreen(this));
		setScreen(new SplashScreen(this));// in order to call first to the
		// splash screen
	}

	@Override
	public void render() {
		super.render();
	}

	// @Override
	// public void dispose() {
	// batcher.dispose();
	// }

}
