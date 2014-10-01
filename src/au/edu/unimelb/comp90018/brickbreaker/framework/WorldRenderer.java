package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickAdapter;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeI;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		batch.draw(Assets.gameBackground, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		batch.end();
	}

	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderBricks();
		
		if (world.state == World.WORLD_STATE_GAME_LOST_LIFE){
			resetPaddleBallPosition();
			world.state = World.WORLD_STATE_RUNNING;
		}
		
		renderBall();
		renderPaddle();
		renderScore();
		renderSoundButton();
		renderPauseButton();
		renderLives();
		renderLevelNumber();
		renderRank();
		batch.end();
	}

	private void renderBall() {

		Ball ball = world.ball;
		batch.draw(Assets.blueBall, 
				ball.position.x - Ball.BALL_WIDTH / 2, 
				ball.position.y - Ball.BALL_HEIGHT / 2,
				Ball.BALL_WIDTH, 
				Ball.BALL_HEIGHT
				);		
	}

	private void renderPaddle() {

		Paddle paddle = world.paddle;
		batch.draw(Assets.paddleSmall, 
				paddle.position.x - Paddle.PADDLE_WIDTH / 2, 
				paddle.position.y - Paddle.PADDLE_HEIGHT / 2, 
				Paddle.PADDLE_WIDTH, 
				Paddle.PADDLE_HEIGHT
				);
	}

	private void renderBricks() {

		List<BrickAdapter> bricks = world.bricks;
		
		int len = bricks.size();

		for (int i = 0; i < len; i++) {
			TextureRegion brickTexture;
			// TODO: review the index of these TextureRegionSets
			if (bricks.get(i) instanceof BrickTypeI){
				brickTexture = Assets.brickTypeI.getTexture(bricks.get(i).hitsLeftToPulverise - 1);
			} else {
				brickTexture = Assets.brickTypeII.getTexture(bricks.get(i).hitsLeftToPulverise - 1);
			}
			
			batch.draw(brickTexture, 
					bricks.get(i).position.x - bricks.get(i).width / 2, 
					bricks.get(i).position.y - bricks.get(i).height / 2, 
					bricks.get(i).width,
					bricks.get(i).height
					);
		}
	}
	
	private void renderPauseButton() {
		
		int buttonWidth = ButtonSize.MEDIUM_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.MEDIUM_SQUARE.getButtonHeight();

		Button pauseButton = world.pauseButton;
		batch.draw(Assets.pauseGame,
				pauseButton.position.x - buttonWidth / 2,
				pauseButton.position.y - buttonHeight / 2,
				buttonWidth, 
				buttonHeight
				);

	}
	
	private void renderSoundButton() {
		
		int buttonWidth = ButtonSize.MEDIUM_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.MEDIUM_SQUARE.getButtonHeight();
		Button musicButton = world.soundButton;

		if (Settings.musicEnabled){
			batch.draw(Assets.soundGameOn,
				musicButton.position.x - buttonWidth / 2,
				musicButton.position.y - buttonHeight / 2,
				buttonWidth, 
				buttonHeight
				);
		}else{
			batch.draw(Assets.soundGameOff,
					musicButton.position.x - buttonWidth / 2,
					musicButton.position.y - buttonHeight / 2,
					buttonWidth, 
					buttonHeight
					);
		}
	}
	
	private void renderScore() {
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "SCORE: " + world.score, 5, World.WORLD_HEIGHT - 5);
	}
	
	private void renderLevelNumber() {
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "LEVEL: " + world.level, World.WORLD_WIDTH-80, World.WORLD_HEIGHT - 5);
	}
	
	private void renderRank() {
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "RANK: " + world.rank, World.WORLD_WIDTH-80, World.WORLD_HEIGHT - 20);
	}
	
	private void renderLives() {
		
		int buttonWidth = ButtonSize.SMALL_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.SMALL_SQUARE.getButtonHeight();
		
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "LIVES: ", 5, World.WORLD_HEIGHT - 20);
		
		List<Button> lives = world.lives;
		int len = lives.size();

		for (int i = 0; i < len; i++) {
			batch.draw(Assets.lives, 
					lives.get(i).position.x - buttonWidth / 2, 
					lives.get(i).position.y - buttonHeight / 2, 
					buttonWidth,
					buttonHeight
					);
		}
	}

	private void resetPaddleBallPosition(){
		
		world.paddle.position.x = World.WORLD_WIDTH/2;
		world.paddle.position.y = World.WORLD_HEIGHT * 0.15f;
		
		world.ball.position.x = World.WORLD_WIDTH/2;
	    world.ball.position.y =	world.paddle.position.y + Paddle.PADDLE_HEIGHT/2 + Ball.BALL_HEIGHT/2;
	    world.ball.accel.x = 0.0f;
	    world.ball.accel.y = 0.0f;

	    
	}
	
}
