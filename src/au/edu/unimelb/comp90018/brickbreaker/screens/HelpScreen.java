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

public class HelpScreen extends ScreenAdapter {
	BrickBreaker game;

	OrthographicCamera guiCam;
	Vector3 touchPoint;

	String textString;
	private Button btnBack;

	public HelpScreen(BrickBreaker game) {

		this.game = game;
		
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);

		textString = "Under Construction...";

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

		game.batcher.draw(Assets.defaultScreen, 0, 0, Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		game.batcher.draw(Assets.defaultNotification, 0, 0, Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);

		game.batcher.draw(Assets.back, btnBack.position.x
				- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnBack.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight()
						/ 2, ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());

		Assets.font.setScale(0.6f, 0.6f);
		Assets.font.drawMultiLine(game.batcher, textString, 60, 350);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		draw();
		update();
	}

	@Override
	public void hide() {
		super.dispose();
	}
}
