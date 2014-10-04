package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.User;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class CreateUserScreen extends ScreenAdapter {
	BrickBreaker game;

	OrthographicCamera guiCam;
	Vector3 touchPoint;

	String textString;
	String username = null;
	private Button btnBack, btnnewPlayer;
	User user;

	public CreateUserScreen(BrickBreaker game) {

		this.game = game;
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);
		btnnewPlayer = new Button(80, 80, ButtonSize.LARGE_RECTANGLE);

		textString = "Please input yur name";

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (btnBack.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				// game.setScreen(new MenuScreen(game));//ok
				User.unlocklevels(2);
				User.unlocklevels(3);
				User.unlocklevels(4);
				User.unlocklevels(5);
				User.unlocklevels(6);
				// User.unlocklevels(7);
				User.unlocklevels(8);
				User.unlocklevels(9);
				User.updatescore(1, 100);
				User.updatescore(2, 200);
				User.updatescore(3, 300);

				User.updatescore(4, 400);
				User.updatescore(5, 500);
				User.updatescore(6, 600);
				// User.updatescore(7, 700);
				User.updatescore(8, 800);
				User.updatescore(9, 900);

				User.getlevelscore(8);
				User.gettotalscore();
				User.getlevelunlocked(3);

				return;
			}
			if (btnnewPlayer.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				// game.setScreen(new MenuScreen(game));
				getName();// call dialog bog and create file brickbreacker.data

				return;
			}

		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		Assets.font.setScale(0.7f, 0.7f);
		Assets.font.setColor(new Color(Color.WHITE));

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.draw(Assets.defaultScreen, 0, 0, Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		game.batcher.draw(Assets.defaultNotification, 0, 0,
				Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);

		game.batcher.draw(Assets.back, btnBack.position.x
				- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnBack.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight()
						/ 2, ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());

		game.batcher.draw(
				Assets.back,
				btnnewPlayer.position.x
						- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnnewPlayer.position.y
						- ButtonSize.MEDIUM_SQUARE.getButtonHeight() / 2,
				ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
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

	public void getName() {
		// String cname = null;

		Gdx.input.getTextInput(new TextInputListener() {
			@Override
			public void input(String text) {
				String cname = text;
				User.create(cname);// archivo creado
			}

			@Override
			public void canceled() {
				String message = "canceled by user";

			}
		}, "Please enter your Name", "");
		// textString = cname;
		// Gdx.app.log("chu", cname);

	}

	// public String Name(String name) {
	// Gdx.app.log("jp", name);
	// return name;
	// }
}
