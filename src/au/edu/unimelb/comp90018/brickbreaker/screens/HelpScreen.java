package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HelpScreen extends ScreenAdapter {
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

	public HelpScreen(BrickBreaker game) {
		screenWidth = 320;
		screenHeight = 480;
		btnSize = 64;
		btnSeparation = btnSize / 2;

		this.game = game;
		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);

		// guiCam.setToOrtho(false, 320, 480);
		// nextBounds = new Rectangle(320 - 64, 0, 64, 64);
		touchPoint = new Vector3();

		backBounds = new Rectangle(0, 0, 320, 50);
		btnback = new Texture("buttons/btn_back.png");
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
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.levelScreen, 0, 0, 320, 480);

		game.batcher.draw(btnback, 0, 0, 320, 50);

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
