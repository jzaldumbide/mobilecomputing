package au.edu.unimelb.comp90018.brickbreaker;

import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.screens.BreakBreakerScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;


public class BrickBreakerGame extends Game {

	public static float WIDTH = 800;
	public static float HEIGHT = 600;
	public static Vector2 scale = new Vector2();

	private BreakBreakerScreen breakoutScreen;
	private AssetManager manager;


	@Override
	public void create() {
		scale.x = Gdx.graphics.getWidth() / BrickBreakerGame.WIDTH;
		scale.y = Gdx.graphics.getHeight() / BrickBreakerGame.HEIGHT;

		manager = new AssetManager();
		breakoutScreen = new BreakBreakerScreen(this);
		Assets.instance.init(manager);
		setScreen(breakoutScreen);
	}

	public void finishedLoading() {
		breakoutScreen = new BreakBreakerScreen(this);
		setScreen(breakoutScreen);
	}

	@Override
	public void dispose() {
		manager.dispose();
		breakoutScreen.dispose();
	}

	public void startGame() {
		breakoutScreen.reset();
		setScreen(breakoutScreen);
	}

	public void endGame() {
		//setScreen(gameOverScreen);
	}

	public void pauseGame() {
		// setScreen(pauseScreen);
		//setScreen(mainMenuScreen);

	}

	public AssetManager getAssetManager() {
		return manager;
	}
}
