package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Player;
import au.edu.unimelb.comp90018.brickbreaker.screens.GameScreen.GameMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class LevelScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;
	
	private Button levelLockedButton_1,levelLockedButton_2,levelLockedButton_3,levelLockedButton_4,levelLockedButton_5,levelLockedButton_6,levelLockedButton_7,levelLockedButton_8,levelLockedButton_9, btnBack;
	private Button levelUnlockedButton_1,levelUnlockedButton_2,levelUnlockedButton_3,levelUnlockedButton_4,levelUnlockedButton_5,levelUnlockedButton_6,levelUnlockedButton_7,levelUnlockedButton_8,levelUnlockedButton_9;
	Vector3 touchPoint;

	public LevelScreen(BrickBreaker game) {
				
		this.game = game;
		
		touchPoint = new Vector3();
		
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		levelLockedButton_1 = new Button(Settings.TARGET_WIDTH/2 - 32 - 64,Settings.TARGET_HEIGHT/2 +32 + 64,ButtonSize.XLARGE_SQUARE);
		levelLockedButton_2 = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2 + 32 + 64,ButtonSize.XLARGE_SQUARE);
		levelLockedButton_3 = new Button(Settings.TARGET_WIDTH/2 + 32 + 64,Settings.TARGET_HEIGHT/2 + 32 + 64,ButtonSize.XLARGE_SQUARE);
	
		levelLockedButton_4 = new Button(Settings.TARGET_WIDTH/2 - 32 - 64,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);
		levelLockedButton_5 = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);
		levelLockedButton_6 = new Button(Settings.TARGET_WIDTH/2 + 32 + 64,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);
				
		levelLockedButton_7 = new Button(Settings.TARGET_WIDTH/2 - 32 - 64,Settings.TARGET_HEIGHT/2 -32 - 64,ButtonSize.XLARGE_SQUARE);
		levelLockedButton_8 = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2 - 32 - 64,ButtonSize.XLARGE_SQUARE);
		levelLockedButton_9 = new Button(Settings.TARGET_WIDTH/2 + 32 + 64,Settings.TARGET_HEIGHT/2 - 32 - 64,ButtonSize.XLARGE_SQUARE);

		levelUnlockedButton_1 = new Button(Settings.TARGET_WIDTH/2 - 32 - 64,Settings.TARGET_HEIGHT/2 + 32 + 64,ButtonSize.XLARGE_SQUARE);
		levelUnlockedButton_2 = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2 + 32 + 64,ButtonSize.XLARGE_SQUARE);
		levelUnlockedButton_3 = new Button(Settings.TARGET_WIDTH/2 + 32 + 64,Settings.TARGET_HEIGHT/2 + 32 + 64,ButtonSize.XLARGE_SQUARE);
		
		levelUnlockedButton_4 = new Button(Settings.TARGET_WIDTH/2 - 32 - 64,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);
		levelUnlockedButton_5 = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);
		levelUnlockedButton_6 = new Button(Settings.TARGET_WIDTH/2 + 32 + 64,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);

		levelUnlockedButton_7 = new Button(Settings.TARGET_WIDTH/2 - 32 - 64,Settings.TARGET_HEIGHT/2 -32 - 64,ButtonSize.XLARGE_SQUARE);
		levelUnlockedButton_8 = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2 - 32 - 64,ButtonSize.XLARGE_SQUARE);
		levelUnlockedButton_9 = new Button(Settings.TARGET_WIDTH/2 + 32 + 64,Settings.TARGET_HEIGHT/2 - 32 - 64,ButtonSize.XLARGE_SQUARE);

		
		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);
	}

	public void update() {
		
		if (Gdx.input.justTouched()) {
			
			int selectedLevel = 0;
			
			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);

			if (levelUnlockedButton_1.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				selectedLevel = 1;
				game.setScreen(new GameScreen(game, GameMode.Server, selectedLevel));
			}
			if (levelLockedButton_2.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				selectedLevel = 2;
				game.setScreen(new GameScreen(game, GameMode.Server, selectedLevel));
			}
			if (levelLockedButton_3.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				selectedLevel = 3;
				game.setScreen(new GameScreen(game, GameMode.Server, selectedLevel));
			}
			if (btnBack.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MenuScreen(game));
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
		game.batcher.draw(Assets.defaultBackground, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		
	
		if (Player.isLevelUnlocked(1)){
			game.batcher.draw(Assets.levelLocked_1,levelLockedButton_1.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_1.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}else{
			game.batcher.draw(Assets.levelUnlocked_1,levelUnlockedButton_1.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_1.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}
		
		if (Player.isLevelUnlocked(2)){
			game.batcher.draw(Assets.levelLocked_2,levelLockedButton_2.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_2.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}else{
			game.batcher.draw(Assets.levelUnlocked_2,levelUnlockedButton_2.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_2.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}
		
		if (Player.isLevelUnlocked(3)){
			game.batcher.draw(Assets.levelLocked_3,levelLockedButton_3.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_3.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}else{
			game.batcher.draw(Assets.levelUnlocked_3,levelUnlockedButton_3.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_3.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}
				
		if (Player.isLevelUnlocked(4)) {
			game.batcher.draw(Assets.levelLocked_4,levelLockedButton_4.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_4.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		} else {
			game.batcher.draw(Assets.levelUnlocked_4,levelUnlockedButton_4.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_4.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}

		if (Player.isLevelUnlocked(5)) {
			game.batcher.draw(Assets.levelLocked_5,levelLockedButton_5.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_5.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		} else {
			game.batcher.draw(Assets.levelUnlocked_5,levelUnlockedButton_5.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_5.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}

		if (Player.isLevelUnlocked(6)) {
			game.batcher.draw(Assets.levelLocked_6,levelLockedButton_6.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_6.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		} else {
			game.batcher.draw(Assets.levelUnlocked_6,levelUnlockedButton_6.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_6.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}

		if (Player.isLevelUnlocked(7)) {
			game.batcher.draw(Assets.levelLocked_7,levelLockedButton_7.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_7.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		} else {
			game.batcher.draw(Assets.levelUnlocked_7,levelUnlockedButton_7.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_7.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}

		if (Player.isLevelUnlocked(8)) {
			game.batcher.draw(Assets.levelLocked_8,levelLockedButton_8.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_8.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		} else {
			game.batcher.draw(Assets.levelUnlocked_8,levelUnlockedButton_8.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_8.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}

		if (Player.isLevelUnlocked(9)) {
			game.batcher.draw(Assets.levelLocked_9,levelLockedButton_9.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelLockedButton_9.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		} else {
			game.batcher.draw(Assets.levelUnlocked_9,levelUnlockedButton_9.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,levelUnlockedButton_9.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		}
		
		game.batcher.draw(Assets.back, 
				btnBack.position.x - ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnBack.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight() / 2,
				ButtonSize.MEDIUM_SQUARE.getButtonWidth(), 
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());
		
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
