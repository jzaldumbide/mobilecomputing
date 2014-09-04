package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SelectScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;
	Rectangle soundBounds;
	Rectangle oneBounds, multiBounds, backBounds;

	Vector3 touchPoint;

	public static Texture btnone, btnback, btnmulti;
	public int screenWidth, screenHeight, btnsizeWidth, btnsizeHeight,
			btnseparation;

	/* BUTTONS */

	public SelectScreen(BrickBreaker game) {
		this.game = game;
		screenWidth = 320;
		screenHeight = 480;
		btnsizeWidth = 300;
		btnsizeHeight = 32;
		btnseparation = 8;

		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);
		oneBounds = new Rectangle(10, 264, 300, 30);
		multiBounds = new Rectangle(10, 220, 300, 30);
		backBounds = new Rectangle(10, 10, 32, 32);

		touchPoint = new Vector3();

		btnone = new Texture("buttons/btn_oneplayer.png");
		btnmulti = new Texture("buttons/btn_multiplayer.png");
		btnback = new Texture("buttons/btn_back.png");

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (oneBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);

				game.setScreen(new LevelScreen(game));
				return;
			}

			if (multiBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new MultiplayerScreen(game));
				return;
			}
			if (backBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new MenuScreen(game));
				return;
			}

			// if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
			// // Assets.playSound(Assets.clickSound);
			// // Settings.soundEnabled = !Settings.soundEnabled;
			// // if (Settings.soundEnabled)
			// // Assets.music.play();
			// // else
			// // Assets.music.pause();
			// }
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
		game.batcher.draw(Assets.menuScreen, 0, 0, 320, 480);
		game.batcher.draw(btnone, oneBounds.x, oneBounds.y, oneBounds.width,
				oneBounds.height);
		game.batcher.draw(btnmulti, multiBounds.x, multiBounds.y,
				multiBounds.width, multiBounds.height);
		game.batcher.draw(btnback, backBounds.x, backBounds.y,
				backBounds.width, backBounds.height);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

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
