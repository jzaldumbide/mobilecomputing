package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SplashScreen extends ScreenAdapter {
	BrickBreaker game;

	OrthographicCamera guiCam;

	public SplashScreen(BrickBreaker game) {

		this.game = game;
		guiCam = new OrthographicCamera(900, 1024);
		guiCam.position.set(900 / 2, 1024 / 2, 0);
	}

	public void update() {
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

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.splashScreen, 0, 0, 900, 1024);
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
