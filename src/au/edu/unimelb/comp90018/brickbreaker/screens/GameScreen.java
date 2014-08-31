package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.WorldRenderer;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;


public class GameScreen extends ScreenAdapter {
	
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	BrickBreaker game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;	
	boolean toggleSound;
	int lastScore;
	String scoreString;

	public GameScreen (BrickBreaker game) {
		this.game = game;

		state = GAME_READY;
		
		// We need to have a target resolution, e.g. 320 x 480
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		
		worldListener = new WorldListener() {
			@Override
			public void hitWall () {
				Assets.playSound(Assets.stepSound);
			}

			@Override
			public void hitPaddle () {
				Assets.playSound(Assets.correctSound);
			}

			@Override
			public void hitBrick () {
				Assets.playSound(Assets.clickSound);
			}
			
			@Override
			public void loseLife () {
				Assets.playSound(Assets.incorrectSound);
			}

		};
		
		world = new World(worldListener);
		renderer = new WorldRenderer(game.batcher, world);
		
		toggleSound = true;
		lastScore = 0;
		scoreString = "SCORE: 0";
	}

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
	
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady () {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning (float deltaTime) {
		
		if (Gdx.input.justTouched()) {
			
			//Ensure we are working in the same world coordinate system:
			guiCam.setToOrtho(false,World.WORLD_WIDTH,World.WORLD_HEIGHT);
  		    guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (world.pauseButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
			
			if (world.soundButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
		}
		
		//Update the World
		float accel = 0;
		
		if (Settings.accelerometerEnabled){
			world.update(deltaTime, Gdx.input.getAccelerometerX());
		}else{
			
			
			if (Gdx.input.isTouched()) {
				
				//Ensure we are working in the same world coordinate system:
				guiCam.setToOrtho(false,World.WORLD_WIDTH,World.WORLD_HEIGHT);
	  		    guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
	  		    
	  		    if (touchPoint.x < world.paddle.position.x){ //is moving to the left
	  		    	accel = 6f;
	  		    }else if(touchPoint.x > world.paddle.position.x){ //is moving to the right
	  		    	accel = -6f;
	  		    }	  		    
  		 
			}

		}
		
		world.update(deltaTime, accel);
		
		
		if (world.score != lastScore) {
			lastScore = world.score;
			scoreString = "SCORE: " + lastScore;
		}
		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			//game.setScreen(new WinScreen(game));
		}
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			if (lastScore >= Settings.highscores[4])
				scoreString = "NEW HIGHSCORE: " + lastScore;
			else
				scoreString = "SCORE: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save();
		}
	}


	private void updatePaused () {
		if (Gdx.input.justTouched()) {
			
			//Ensure we are working in the same world coordinate system:
			guiCam.setToOrtho(false,World.WORLD_WIDTH,World.WORLD_HEIGHT);
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		
			if (world.pauseButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
			
			if (world.soundButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

//			if (world.settingsButton.bounds.contains(touchPoint.x, touchPoint.y)) {
//				Assets.playSound(Assets.clickSound);
//				game.setScreen(new SettingsMenuScreen(game));
//				return;
//			}

		}
	}

	private void updateLevelEnd () {
		if (Gdx.input.justTouched()) {
			world = new World(worldListener);
			renderer = new WorldRenderer(game.batcher, world);
			world.score = lastScore;
			state = GAME_READY;
		}
	}

	private void updateGameOver () {
		if (Gdx.input.justTouched()) {
			//TODO: this is just for testing
			state = GAME_LEVEL_END;
			//game.setScreen(new MainMenuScreen(game));
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*Render Objects in screen*/
		renderer.render();

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		
		game.batcher.end();
	}

	private void presentReady () {
		game.batcher.draw(Assets.ready, 0, 0, 320, 480);
	}

	private void presentRunning () {
		//game.batcher.draw(Assets.soundOn, 320 - 64, 480 - 64, 64, 64);
		//Assets.font.draw(game.batcher, scoreString, 1, 480 - 5);
	}

	private void presentPaused () {
		//game.batcher.draw(Assets.soundOn, World.WORLD_WIDTH-1, World.WORLD_HEIGHT-1, 2, 2);
//		game.batcher.draw(Assets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
		//Assets.font.draw(game.batcher, scoreString, 1, 480 - 5);
	}

	private void presentLevelEnd () {
//		String topText = "the princess is ...";
//		String bottomText = "in another castle!";
//		float topWidth = Assets.font.getBounds(topText).width;
//		float bottomWidth = Assets.font.getBounds(bottomText).width;
//		Assets.font.draw(game.batcher, topText, 160 - topWidth / 2, 480 - 40);
//		Assets.font.draw(game.batcher, bottomText, 160 - bottomWidth / 2, 40);
	}

	private void presentGameOver () {
		game.batcher.draw(Assets.gameOver, 0, 0, 320, 480);
//		game.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
//		float scoreWidth = Assets.font.getBounds(scoreString).width;
//		Assets.font.draw(game.batcher, scoreString, 160 - scoreWidth / 2, 480 - 20);
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause () {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}
}