package au.edu.unimelb.comp90018.brickbreaker;

import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;
//import au.edu.unimelb.comp90018.brickbreaker.screens.BrickBreakerScreen;
import au.edu.unimelb.comp90018.brickbreaker.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * BrickBreakerGame  class that extends Game, which implements ApplicationListener. 
 * It will be used as the "Main" libgdx class, the starting point basically, in the core libgdx project. 
 *  
 * @author Diego
 *
 */
public class BrickBreakerGame extends Game {

	public static float WIDTH = Settings.GAME_WIDTH;
	public static float HEIGHT = Settings.GAME_HEIGHT;
	public static Vector2 scale = new Vector2();
	public SpriteBatch batcher;

	@Override
	public void create() {
		
		/*Get the screen resolution*/
		scale.x = Gdx.graphics.getWidth() / BrickBreakerGame.WIDTH;
		scale.y = Gdx.graphics.getHeight() / BrickBreakerGame.HEIGHT;
		
		/*Initialize Settings and Assets*/
		batcher = new SpriteBatch();
		Settings.load();
		Assets.load();
		
		/* This is the call to the main screen. here you should call the MainMenuScreen and call GameScreen from there */
		//setScreen(new MainMenuScreen(this));
		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batcher.dispose();
	}
	
}
