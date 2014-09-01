package au.edu.unimelb.comp90018.brickbreaker;

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
