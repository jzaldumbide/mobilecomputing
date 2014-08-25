package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.Brick;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	static final float DEVICE_WIDTH = Gdx.graphics.getWidth();
	static final float DEVICE_HEIGHT = Gdx.graphics.getHeight();
	
	static final float GAME_WIDTH = Assets.backgroundRegion.getRegionWidth();
	static final float GAME_HEIGHT = Assets.backgroundRegion.getRegionHeight();
	
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(DEVICE_WIDTH, DEVICE_HEIGHT);
		this.cam.position.set(DEVICE_WIDTH/2,DEVICE_HEIGHT/2,0);
		this.batch = batch;
	}

	public void render () {
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundRegion, 0, 0, GAME_WIDTH,GAME_HEIGHT);
		batch.end();
	}

	public void renderObjects () {
		batch.enableBlending();
		batch.begin();
		renderWall();
		renderPaddle();
		renderBricks();
		renderBall();
		batch.end();
	}

	private void renderBall() {

		Ball ball = world.ball;
		batch.draw(ball.texture, ball.position.x, ball.position.y);
	}

	private void renderPaddle() {

		Paddle paddle = world.paddle;
		batch.draw(paddle.texture, paddle.position.x, paddle.position.y);
	}

	private void renderBricks () {
		
		List<Brick> bricks = world.bricks;

		for (int x = 0; x < 24; x++) {

			Brick brick = bricks.get(x);
			batch.draw(brick.texture, brick.position.x, brick.position.y);

		}
	}


	private void renderWall () {

	}
}
