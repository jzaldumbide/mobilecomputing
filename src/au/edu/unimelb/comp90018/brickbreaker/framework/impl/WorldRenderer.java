package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.Brick;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	static final float FRUSTUM_WIDTH = 320;
	static final float FRUSTUM_HEIGHT = 480;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	String scoreLabel;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
//		this.cam.zoom = 2f;
		this.batch = batch;
	}

	public void render() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground() {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundRegion, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		batch.end();
	}

	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderBricks();
		renderBall();
		renderPaddle();
		renderScore();
		renderSoundButton();
		renderPauseButton();
//		renderSettingsButton();
		batch.end();
	}

	private void renderBall() {

		Ball ball = world.ball;
		batch.draw(Assets.redBall, ball.position.x - Ball.BALL_WIDTH / 2, ball.position.y - Ball.BALL_HEIGHT / 2,
				Ball.BALL_WIDTH, Ball.BALL_HEIGHT);
	}

	private void renderPaddle() {

		Paddle paddle = world.paddle;
		batch.draw(Assets.paddle, paddle.position.x - Paddle.PADDLE_WIDTH / 2, paddle.position.y - Paddle.PADDLE_HEIGHT
				/ 2, Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT);
	}

	private void renderBricks() {

		List<Brick> bricks = world.bricks;
		int len = bricks.size();

		for (int i = 0; i < len; i++) {
			batch.draw(Assets.brick1, bricks.get(i).position.x, bricks.get(i).position.y, Brick.BRICK_WIDTH,
					Brick.BRICK_HEIGHT);
		}
	}
	
	private void renderPauseButton() {

		Button pauseButton = world.pauseButton;
		batch.draw(Assets.pauseMenu,
				pauseButton.position.x - Button.BUTTON_WIDTH / 2,
				pauseButton.position.y - Button.BUTTON_HEIGHT / 2,
				Button.BUTTON_WIDTH, Button.BUTTON_HEIGHT);

	}
	
	private void renderSoundButton() {

		Button soundButton = world.soundButton;
		batch.draw(Assets.soundOn,
				soundButton.position.x - Button.BUTTON_WIDTH / 2,
				soundButton.position.y - Button.BUTTON_HEIGHT / 2,
				Button.BUTTON_WIDTH, Button.BUTTON_HEIGHT);

	}
	
//	private void renderSettingsButton() {
//
//		Button settingsButton = world.settingsButton;
//		batch.draw(Assets.settingsMenu,
//				settingsButton.position.x - Button.BUTTON_WIDTH / 2,
//				settingsButton.position.y - Button.BUTTON_HEIGHT / 2,
//				Button.BUTTON_WIDTH, Button.BUTTON_HEIGHT);
//
//	}
	
	private void renderScore() {
		Assets.font.setScale(0.6f, 0.6f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "SCORE: " + world.score, 5, World.WORLD_HEIGHT-5);
	}

}
