/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author achaves
 *
 */
public class MessageScreen extends ScreenAdapter {

	BrickBreaker game;
	OrthographicCamera guiCam;
	ScreenAdapter nextScreen;
	String message;
	
	public MessageScreen (BrickBreaker game, String message, ScreenAdapter nextScreen){
		Gdx.app.log("Called","YES");
		this.game = game;
		this.nextScreen = nextScreen;
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);	
		this.message = message; 
	}
	public void update() {
		if (Gdx.input.justTouched()) {
			game.setScreen(this.nextScreen);
		}
	}
	@Override
	public void render(float delta) {
		draw();
		update();
	}

	public void draw() {

		GL20 gl = Gdx.gl;

		gl.glViewport((int) game.viewport.x, (int) game.viewport.y,
				(int) game.viewport.width, (int) game.viewport.height);

		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		guiCam.update();

		Assets.font.setScale(0.6f, 0.6f);
		Assets.font.setColor(new Color(Color.WHITE));

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		

		game.batcher.draw(Assets.errorMessage, 0, 0, 320, 480);
		Assets.font.draw(game.batcher, message, 120, 200);
		game.batcher.end();
	}
	
	@Override
	public void hide() {
		super.dispose();
	}

}
