package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OptionScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;
	
	private Button soundButton, musicButton, gyroscopeButton, touchButton , btnBack;
	Vector3 touchPoint;

	public OptionScreen(BrickBreaker game) {
		
		touchPoint = new Vector3();
		this.game = game;
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);
		soundButton = new Button(Settings.TARGET_WIDTH/2-50,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_SQUARE);
		musicButton = new Button(Settings.TARGET_WIDTH/2+50,Settings.TARGET_HEIGHT/2-67,ButtonSize.XLARGE_SQUARE);
		gyroscopeButton = new Button(Settings.TARGET_WIDTH/2-50,Settings.TARGET_HEIGHT/2-135,ButtonSize.XLARGE_SQUARE);
	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));

			if (soundButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				
				return;
			}

			if (musicButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				
				return;
			}
			if (gyroscopeButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				
				return;
			}
			if (btnBack.bounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new MenuScreen(game));
				return;
			}
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.menuScreen, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		game.batcher.draw(Assets.soundOn,soundButton.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,soundButton.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		game.batcher.draw(Assets.musicOn,musicButton.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,musicButton.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		game.batcher.draw(Assets.accelOn,gyroscopeButton.position.x-ButtonSize.XLARGE_SQUARE.getButtonWidth()/2,gyroscopeButton.position.y-ButtonSize.XLARGE_SQUARE.getButtonHeight()/2,ButtonSize.XLARGE_SQUARE.getButtonWidth(),ButtonSize.XLARGE_SQUARE.getButtonHeight());
		
		game.batcher.draw(Assets.back, btnBack.position.x
				- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnBack.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight()
						/ 2, ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
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
