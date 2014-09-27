package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.screens.GameScreen.GameMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelScreen extends ScreenAdapter {
	BrickBreaker game;

	OrthographicCamera guiCam;
	Rectangle backBounds, lvl1Bs, lvl2Bs, lvl3Bs, lvl4Bs, lvl5Bs, lvl6Bs,
			lvl7Bs, lvl8Bs, lvl9Bs;
	Vector3 touchPoint;
	Texture btnlvl1, btnlvl2, btnlvl3, btnlvl4, btnlvl5, btnlvl6, btnlvl7,
			btnlvl8, btnlvl9;
	TextureRegion helpRegion;
	public static Texture btnback;
	public int screenWidth, screenHeight, btnSize, btnSeparation;

	public LevelScreen(BrickBreaker game) {
		screenWidth = 320;
		screenHeight = 480;
		btnSize = 64;
		btnSeparation = 32;

		this.game = game;
		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);

		// guiCam.setToOrtho(false, 320, 480);
		// nextBounds = new Rectangle(320 - 64, 0, 64, 64);
		touchPoint = new Vector3();

		backBounds = new Rectangle(10, 10, 32, 32);
		btnback = new Texture("buttons/back.png");
		lvl1Bs = new Rectangle(32, 400, btnSize, btnSize);
		lvl2Bs = new Rectangle(32 + (btnSeparation + btnSize), 400, btnSize,
				btnSize);
		lvl3Bs = new Rectangle(32 + 2 * (btnSeparation + btnSize), 400,
				btnSize, btnSize);
		lvl4Bs = new Rectangle(32, 400 - (btnSeparation + btnSize), btnSize,
				btnSize);
		lvl5Bs = new Rectangle(32 + (btnSeparation + btnSize),
				400 - (btnSeparation + btnSize), btnSize, btnSize);
		lvl6Bs = new Rectangle(32 + 2 * (btnSeparation + btnSize),
				400 - (btnSeparation + btnSize), btnSize, btnSize);
		lvl7Bs = new Rectangle(32, 400 - 2 * (btnSeparation + btnSize),
				btnSize, btnSize);
		lvl8Bs = new Rectangle(32 + (btnSeparation + btnSize),
				400 - 2 * (btnSeparation + btnSize), btnSize, btnSize);
		lvl9Bs = new Rectangle(32 + 2 * (btnSeparation + btnSize),
				400 - 2 * (btnSeparation + btnSize), btnSize, btnSize);

		btnlvl1 = new Texture("levelbtn/1.png");
		btnlvl2 = new Texture("levelbtn/2.png");
		btnlvl3 = new Texture("levelbtn/3.png");
		btnlvl4 = new Texture("levelbtn/4.png");
		btnlvl5 = new Texture("levelbtn/5.png");
		btnlvl6 = new Texture("levelbtn/6.png");
		btnlvl7 = new Texture("levelbtn/7.png");
		btnlvl8 = new Texture("levelbtn/8.png");
		btnlvl9 = new Texture("levelbtn/9.png");

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (lvl1Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 1
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 1", "level 1");
				game.setScreen(new GameScreen(game, GameMode.Server));
				return;
			}
			if (lvl2Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 2
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 2", "level 2");
				game.setScreen(new GameScreen(game, GameMode.Client));
				return;
			}
			if (lvl3Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 3
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 3", "level 3");
				return;
			}
			if (lvl4Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 4
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 4", "level 4");
				return;
			}
			if (lvl5Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 5
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 5", "level 5");
				return;
			}
			if (lvl6Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 6
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 6", "level 6");
				return;
			}
			if (lvl7Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 7
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 7", "level 7");
				return;
			}
			if (lvl8Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 8
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 8", "level 8");
				return;
			}
			if (lvl9Bs.contains(touchPoint.x, touchPoint.y)) {

				// llamar al level 9
				// game.setScreen(new MenuScreen(game));
				Gdx.app.log("level 9", "level 9");
				return;
			}

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {

				game.setScreen(new SelectScreen(game));
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
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.defaultScreen, 0, 0, 320, 480);

		game.batcher.draw(btnback, 10, 10, 32, 32);
		// level buttons
		game.batcher.draw(btnlvl1, lvl1Bs.x, lvl1Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl2, lvl2Bs.x, lvl2Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl3, lvl3Bs.x, lvl3Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl4, lvl4Bs.x, lvl4Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl5, lvl5Bs.x, lvl5Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl6, lvl6Bs.x, lvl6Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl7, lvl7Bs.x, lvl7Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl8, lvl8Bs.x, lvl8Bs.y, btnSize, btnSize);
		game.batcher.draw(btnlvl9, lvl9Bs.x, lvl9Bs.y, btnSize, btnSize);

		//
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		// game.batcher.draw(Assets., 320, 0, -64, 64);
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
