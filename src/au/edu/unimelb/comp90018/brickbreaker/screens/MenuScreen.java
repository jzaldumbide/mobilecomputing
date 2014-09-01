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

public class MenuScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;
	Rectangle soundBounds;
	Rectangle playBounds, levelsBounds, scoresBounds, optionsBounds,
			helpBounds, exitBounds;
	Rectangle highscoresBounds;

	Vector3 touchPoint;
	/* Textures for buttons */

	public static Texture btnback, btnexit, btnhelp, btnlevels, btnoptions,
			btnplay, btnscores;
	public int screenWidth, screenHeight, btnsizeWidth, btnsizeHeight,
			btnseparation;

	/* BUTTONS */

	public MenuScreen(BrickBreaker game) {
		this.game = game;
		// screenWidth = Gdx.graphics.getWidth();
		// screenHeight = Gdx.graphics.getHeight();
		screenWidth = 320;
		screenHeight = 480;
		btnsizeWidth = 300;
		btnsizeHeight = 36;
		btnseparation = btnsizeHeight / 4;

		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);
		soundBounds = new Rectangle(0, 0, 64, 64);

		playBounds = new Rectangle((screenWidth / 2) - (btnsizeWidth / 2),
				300 - (btnsizeHeight), btnsizeWidth, btnsizeHeight);

		levelsBounds = new Rectangle(playBounds.x, playBounds.y - btnsizeHeight
				- btnseparation, btnsizeWidth, btnsizeHeight);

		scoresBounds = new Rectangle(playBounds.x, levelsBounds.y
				- btnsizeHeight - btnseparation, btnsizeWidth, btnsizeHeight);

		optionsBounds = new Rectangle(playBounds.x, scoresBounds.y
				- btnsizeHeight - btnseparation, btnsizeWidth, btnsizeHeight);

		helpBounds = new Rectangle(playBounds.x, optionsBounds.y
				- btnsizeHeight - btnseparation, btnsizeWidth, btnsizeHeight);

		exitBounds = new Rectangle(playBounds.x, helpBounds.y - btnsizeHeight
				- btnseparation, btnsizeWidth, btnsizeHeight);

		touchPoint = new Vector3();

		btnplay = new Texture("buttons/btn_play.png");
		btnlevels = new Texture("buttons/btn_levels.png");
		btnscores = new Texture("buttons/btn_scores.png");
		btnoptions = new Texture("buttons/btn_options.png");
		btnhelp = new Texture("buttons/btn_help.png");
		btnexit = new Texture("buttons/btn_exit.png");

		btnback = new Texture("buttons/btn_back.png");

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
				return;
			}

			if (levelsBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new LevelScreen(game));
				return;
			}
			if (scoresBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new ScoreScreen(game));
				return;
			}
			if (optionsBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new OptionScreen(game));
				return;
			}
			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			if (exitBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);

				Gdx.app.exit();

				return;
			}

			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				// Assets.playSound(Assets.clickSound);
				// Settings.soundEnabled = !Settings.soundEnabled;
				// if (Settings.soundEnabled)
				// Assets.music.play();
				// else
				// Assets.music.pause();
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
		game.batcher.draw(Assets.menuScreen, 0, 0, 320, 480);
		game.batcher.draw(btnplay, playBounds.x, playBounds.y,
				playBounds.width, playBounds.height);
		game.batcher.draw(btnlevels, levelsBounds.x, levelsBounds.y,
				levelsBounds.width, levelsBounds.height);
		game.batcher.draw(btnscores, scoresBounds.x, scoresBounds.y,
				scoresBounds.width, scoresBounds.height);
		game.batcher.draw(btnoptions, optionsBounds.x, optionsBounds.y,
				optionsBounds.width, optionsBounds.height);
		game.batcher.draw(btnhelp, helpBounds.x, helpBounds.y,
				helpBounds.width, helpBounds.height);
		game.batcher.draw(btnexit, exitBounds.x, exitBounds.y,
				exitBounds.width, exitBounds.height);

		// Gdx.app.log("tanano de la panatalla", "height: " + screenHeight
		// + " width: " + screenWidth);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		// game.batcher.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274,
		// 142);
		// game.batcher.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
		// game.batcher.draw(Settings.soundEnabled ? Assets.soundOn
		// : Assets.soundOff, 0, 0, 64, 64);
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
