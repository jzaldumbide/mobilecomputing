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

public class SelectScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;
	
	private Button singlePlayerButton, multiPlayerButton, btnBack;
	Vector3 touchPoint;

	public SelectScreen(BrickBreaker game) {
		
		touchPoint = new Vector3();
		this.game = game;
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);
		singlePlayerButton = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2,ButtonSize.XLARGE_RECTANGLE);
		multiPlayerButton = new Button(Settings.TARGET_WIDTH/2,Settings.TARGET_HEIGHT/2-45,ButtonSize.XLARGE_RECTANGLE);
	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));

			if (singlePlayerButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new LevelScreen(game));
				return;
			}

			if (multiPlayerButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new MultiplayerScreen(game));
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
		Assets.font.setScale(0.6f, 0.6f);
		Assets.font.setColor(new Color(Color.WHITE));
		

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.menuScreen, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		game.batcher.draw(Assets.buttonMenu,singlePlayerButton.position.x-ButtonSize.XLARGE_RECTANGLE.getButtonWidth()/2,singlePlayerButton.position.y-ButtonSize.XLARGE_RECTANGLE.getButtonHeight()/2,ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),ButtonSize.XLARGE_RECTANGLE.getButtonHeight());
		game.batcher.draw(Assets.buttonMenu,multiPlayerButton.position.x-ButtonSize.XLARGE_RECTANGLE.getButtonWidth()/2,multiPlayerButton.position.y-ButtonSize.XLARGE_RECTANGLE.getButtonHeight()/2,ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),ButtonSize.XLARGE_RECTANGLE.getButtonHeight());
		
		float posX = Settings.TARGET_WIDTH/2;
		float posY = Settings.TARGET_HEIGHT/2;
		
		Assets.font.draw(game.batcher, "Single player", posX-65,posY+10);
		Assets.font.draw(game.batcher, "Multi player", posX-65,posY-37);
		
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
