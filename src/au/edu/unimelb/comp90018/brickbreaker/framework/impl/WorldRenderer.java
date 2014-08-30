package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.Brick;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	static final float FRUSTUM_WIDTH = 20;
	static final float FRUSTUM_HEIGHT = 30;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
	}

	public void render() {
		// if (world.bob.position.y > cam.position.y) cam.position.y =
		// world.bob.position.y;
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

}
