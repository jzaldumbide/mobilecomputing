package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends ScreenAdapter {
	
	BrickBreaker game;
	OrthographicCamera guiCam;
	
	private Button playButton, scoresButton, optionsButton, helpButton, quitButton;
	Vector3 touchPoint;

	public MenuScreen(BrickBreaker game) {
				
		this.game = game;
		
		touchPoint = new Vector3();
		
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		playButton = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_RECTANGLE);
		scoresButton = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2-45,ButtonSize.XLARGE_RECTANGLE);
		optionsButton = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2-90,ButtonSize.XLARGE_RECTANGLE);
		helpButton = new Button(50 + ButtonSize.MEDIUM_SQUARE.getButtonWidth(),50,ButtonSize.MEDIUM_SQUARE);
		quitButton = new Button(Settings.TARGET_WIDTH - ButtonSize.MEDIUM_SQUARE.getButtonWidth() - 50,50,ButtonSize.MEDIUM_SQUARE);
	}

	public void update() {
		if (Gdx.input.justTouched()) {
			
			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);
			
			if (playButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new SelectScreen(game));
				return;
			}

			if (scoresButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new ScoreScreen(game));
				return;
			}
			if (optionsButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new OptionScreen(game));
				return;
			}
			if (helpButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			if (quitButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Gdx.app.exit();
				return;
			}
		}
	}

	public void draw() {
		
		GL20 gl = Gdx.gl;
		
		gl.glViewport(
				(int) game.viewport.x, 
				(int) game.viewport.y, 
				(int) game.viewport.width,
				(int) game.viewport.height
				);
		
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		
		Assets.font.setScale(0.7f, 0.7f);
		Assets.font.setColor(new Color(Color.WHITE));
		

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.menuScreen, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		game.batcher.draw(Assets.buttonMenu,playButton.position.x-ButtonSize.XLARGE_RECTANGLE.getButtonWidth()/2,playButton.position.y-ButtonSize.XLARGE_RECTANGLE.getButtonHeight()/2,ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),ButtonSize.XLARGE_RECTANGLE.getButtonHeight());
		game.batcher.draw(Assets.buttonMenu,scoresButton.position.x-ButtonSize.XLARGE_RECTANGLE.getButtonWidth()/2,scoresButton.position.y-ButtonSize.XLARGE_RECTANGLE.getButtonHeight()/2,ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),ButtonSize.XLARGE_RECTANGLE.getButtonHeight());
		game.batcher.draw(Assets.buttonMenu,optionsButton.position.x-ButtonSize.XLARGE_RECTANGLE.getButtonWidth()/2,optionsButton.position.y-ButtonSize.XLARGE_RECTANGLE.getButtonHeight()/2,ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),ButtonSize.XLARGE_RECTANGLE.getButtonHeight());
		
		game.batcher.draw(Assets.help,helpButton.position.x-ButtonSize.MEDIUM_SQUARE.getButtonWidth()/2,helpButton.position.y-ButtonSize.MEDIUM_SQUARE.getButtonHeight()/2,ButtonSize.MEDIUM_SQUARE.getButtonWidth(),ButtonSize.MEDIUM_SQUARE.getButtonHeight());
		game.batcher.draw(Assets.quit,quitButton.position.x-ButtonSize.MEDIUM_SQUARE.getButtonWidth()/2,quitButton.position.y-ButtonSize.MEDIUM_SQUARE.getButtonHeight()/2,ButtonSize.MEDIUM_SQUARE.getButtonWidth(),ButtonSize.MEDIUM_SQUARE.getButtonHeight());
		
		float posX = Settings.TARGET_WIDTH/2;
		float posY = Settings.TARGET_HEIGHT/2;
		
		Assets.font.draw(game.batcher, "Play!", posX-32,posY+10);
		Assets.font.draw(game.batcher, "Scores", posX-45,posY-37);
		Assets.font.draw(game.batcher, "Options", posX-52,posY-80);
		game.batcher.end();

	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	@Override
	public void pause() {
		// Settings.save();
	}
}
