package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {
	static final float BACKGROUND_WIDTH = Assets.backgroundRegion.getRegionWidth();
	static final float BACKGROUND_HEIGHT = Assets.backgroundRegion.getRegionHeight();
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		this.cam.position.set(BACKGROUND_WIDTH/2,BACKGROUND_HEIGHT/2,0);
		this.batch = batch;
	}

	public void render () {
		//batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();
		//batch.draw(Assets.backgroundRegion, cam.position.x - BACKGROUND_WIDTH / 2, cam.position.y - BACKGROUND_HEIGHT / 2, 0.3f*world.WORLD_WIDTH,
		//	0.25f*world.WORLD_HEIGHT);
		
		batch.draw(Assets.backgroundRegion, 0, 0, Assets.backgroundRegion.getRegionWidth(),
				Assets.backgroundRegion.getRegionHeight());
		
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

	private void renderWall () {
		
	}

	private void renderPaddle () {

			Paddle paddle = world.paddle;
			batch.draw(paddle.texture, paddle.position.x, paddle.position.y);
	}

	private void renderBricks () {

	}


	private void renderBall () {

	}
}
