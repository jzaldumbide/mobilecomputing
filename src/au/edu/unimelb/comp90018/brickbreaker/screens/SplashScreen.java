package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class SplashScreen extends ScreenAdapter {
	BrickBreaker game;
	private long startTime;
	private int counter;

	OrthographicCamera guiCam;

	TextureRegion helpRegion;
	public static Texture btnback;
	public int screenWidth, screenHeight, btnSize, btnSeparation;

	public SplashScreen(BrickBreaker game) {

		screenWidth = 320;
		screenHeight = 480;
		btnSize = 64;
		btnSeparation = btnSize / 2;

		this.game = game;
		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);

		startTime = TimeUtils.millis();
	}

	public void update() {
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.splashScreen, 0, 0, 320, 480);

		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		// game.batcher.draw(Assets., 320, 0, -64, 64);
		game.batcher.end();
		counter++;
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
		// helpImage.dispose();
		super.dispose();
	}
}
