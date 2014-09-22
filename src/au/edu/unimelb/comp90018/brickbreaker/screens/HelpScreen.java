package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HelpScreen extends ScreenAdapter {
	BrickBreaker game;

	OrthographicCamera guiCam;
	Rectangle backBounds;
	Vector3 touchPoint;

	TextureRegion helpRegion;
	public static Texture btnback;
	public int screenWidth, screenHeight, btnSize, btnSeparation;
	String helpString;
	BitmapFont font;

	public HelpScreen(BrickBreaker game) {
		//
		font = new BitmapFont(Gdx.files.internal("fonts/test/Arial-12.fnt"),
				Gdx.files.internal("fonts/test/fontgame.png"), false);
		//

		screenWidth = 320;
		screenHeight = 480;
		btnSize = 64;
		btnSeparation = btnSize / 2;
		helpString = "Aqui texto de ayuda";
		this.game = game;
		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);
		guiCam.setToOrtho(false, 320, 480);

		touchPoint = new Vector3();

		backBounds = new Rectangle(10, 10, 32, 32);
		btnback = new Texture("buttons/back.png");
		// font.setScale(3, 3);

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {

				game.setScreen(new MenuScreen(game));
				Gdx.app.log("", "click para regresar");
				return;
			}

		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();

		game.batcher.setProjectionMatrix(guiCam.combined);
		// game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.helpScreen, 0, 0, 320, 480);

		game.batcher.draw(btnback, 10, 10, 32, 32);

		Assets.font.drawMultiLine(game.batcher, helpString, 10, 200);
		// font.draw(game.batcher, helpString, 10, 300);

		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.end();
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
