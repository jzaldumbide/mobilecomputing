package au.edu.unimelb.comp90018.brickbreaker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import au.edu.unimelb.comp90018.brickbreaker.framework.network.LevelDownloader;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;
import au.edu.unimelb.comp90018.brickbreaker.screens.MenuScreen;
import au.edu.unimelb.comp90018.brickbreaker.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

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
	public Rectangle viewport;
	public int orientation;

	@Override
	public void create() {

		/* Initialize Settings and Assets */
		batcher = new SpriteBatch();
		Assets.load();

		// Initialize a LoadViewTask object and call the execute() method
		new LoadViewTask().execute();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {

		// Calculate new viewport when the orientation is changed
		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if (aspectRatio > Settings.ASPECT_RATIO) {
			scale = (float) height / (float) Settings.TARGET_HEIGHT;
			crop.x = (width - Settings.TARGET_WIDTH * scale) / 2f;
		} else if (aspectRatio < Settings.ASPECT_RATIO) {
			scale = (float) width / (float) Settings.TARGET_WIDTH;
			crop.y = (height - Settings.TARGET_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) Settings.TARGET_WIDTH;
		}

		float w = (float) Settings.TARGET_WIDTH * scale;
		float h = (float) Settings.TARGET_HEIGHT * scale;
		
		viewport = new Rectangle(crop.x, crop.y, w, h);
		
		if (height > width)
			orientation = 0;
		else
			orientation = 1;
	}

	// @Override
	// public void dispose() {
	// batcher.dispose();
	// }

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
		// Before running code in separate thread
		@Override
		protected void onPreExecute() {
			setScreen(new SplashScreen(BrickBreaker.this));
		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			/*
			 * This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This is
			 * where the code that is going to be executed on a background
			 * thread must be placed.
			 */
			try {
				// Get the current thread's token
				synchronized (this) {
					// Wait 850 milliseconds
					// this.wait(10000);
					// Increment the counter
					// This value is going to be passed to the
					// onProgressUpdate() method.
					downloadLevels();
					// publishProgress(counter*25);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) {
			// set the current progress of the progress dialog
			// progressDialog.setProgress(values[0]);
		}

		// after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			// initialize the View
			setScreen(new MenuScreen(BrickBreaker.this));
			// setContentView(R.layout.main);
		}

		private void downloadLevels() {

			/* Download all the levels and the highscores */
			int maxLevels = 3;
			LevelDownloader ld = new LevelDownloader();
			boolean error = false;
			try {
				for (int level = 1; level <= maxLevels; level++) {
					String gameLevel;
					gameLevel = ld.downloadGame("brickbreaker_level" + level
							+ ".xml");
					ld.persistGame("brickbreaker_level" + level + ".xml",
							gameLevel);
				}
				String highScores = ld.downloadHighScores();
				ld.persistScores(highScores);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				error = true;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				error = true;
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				error = true;
				e.printStackTrace();
			}

			if (error){
				//Hande error here
			}
		}
	}

}
