package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class SplashScreen extends ScreenAdapter {
	BrickBreaker game;
	private long startTime;

	OrthographicCamera guiCam;

	public SplashScreen(BrickBreaker game) {

		this.game = game;
		guiCam = new OrthographicCamera(900, 1024);
		guiCam.position.set(900 / 2, 1024 / 2, 0);

		startTime = TimeUtils.millis();
	}

	public void update() {
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.splashScreen, 0, 0, 900, 1024);
		game.batcher.end();

		if (TimeUtils.millis() > (startTime + 3000))
			game.setScreen(new MenuScreen(game));
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
