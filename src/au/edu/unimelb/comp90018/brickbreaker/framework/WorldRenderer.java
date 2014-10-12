package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.util.List;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.Bonus;
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

/**
 * We were trying to apply MVC model, thus this class is the VIEW part whereas
 * the World is a kind of CONTROLLER. libGDX concepts like FRUSTRUM and
 * Orthographic camera are managed within this class. We made the dimensions of
 * the World and the dimensions of the TARGET device coincide only for reasons
 * of convenience.
 * 
 * FRUSTRUM: it is like a layout where the world is projected through the
 * camera. VIEWPORT: it is the section of the device where the FRUSTRUM is
 * displayed.
 * 
 */
public class WorldRenderer {

	static final float FRUSTUM_WIDTH = 320;
	static final float FRUSTUM_HEIGHT = 480;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	String scoreLabel;
	int randomTime;

	/**
	 * Initialise the camera's position on the center of the FRUSTRUM.
	 * 
	 * @param batch
	 * @param world
	 */
	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.randomTime = (int) world.timeCounter;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
	}

	/**
	 * Render background and world objects by separate.
	 */
	public void render() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	/**
	 * Render the background with blending deactivated (no transparency).
	 */
	public void renderBackground() {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.gameBackground, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		batch.end();
	}

	/**
	 * Render the world objects with blending activated (transparency is
	 * needed).
	 */
	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderBricks();

		if (world.state == World.WORLD_STATE_GAME_LOST_LIFE) {
			world.resetPaddleBallPosition();
			world.state = World.WORLD_STATE_RUNNING;
		}

		renderBall();
		renderPaddle();
		renderBonuses();
		renderScore();
		renderSoundButton();
		renderPauseButton();
		renderLives();
		renderLevelNumber();
		renderRank();
		renderTotalScore();
		renderNextScore();
		batch.end();
	}

	/**
	 * By using flags, the renderer draws the bonuses.
	 */
	private void renderBonuses() {

		if (world.showCoin) {

			Bonus bonus = world.coin;
			batch.draw(Assets.coin, bonus.position.x - bonus.getType().getBonusWidth() / 2, bonus.position.y
					- bonus.getType().getBonusHeight() / 2, bonus.getType().getBonusWidth(), bonus.getType()
					.getBonusHeight());

		}

		if (world.showVirus) {

			Bonus virus = world.virus;
			batch.draw(Assets.virus, virus.position.x - virus.getType().getBonusWidth() / 2, virus.position.y
					- virus.getType().getBonusHeight() / 2, virus.getType().getBonusWidth(), virus.getType()
					.getBonusHeight());

		}

		if (world.showExtraLife) {

			Bonus extraLife = world.extraLife;
			batch.draw(Assets.lives, extraLife.position.x - extraLife.getType().getBonusWidth() / 2,
					extraLife.position.y - extraLife.getType().getBonusHeight() / 2, extraLife.getType()
							.getBonusWidth(), extraLife.getType().getBonusHeight());
		}

	}

	private void renderBall() {

		Ball ball = world.ball;
		batch.draw(Assets.blueBall, ball.position.x - Ball.BALL_WIDTH / 2, ball.position.y - Ball.BALL_HEIGHT / 2,
				Ball.BALL_WIDTH, Ball.BALL_HEIGHT);
	}

	private void renderPaddle() {

		Paddle paddle = world.paddle;
		batch.draw(Assets.paddleSmall, paddle.position.x - paddle.width / 2, paddle.position.y - Paddle.PADDLE_HEIGHT
				/ 2, paddle.width, Paddle.PADDLE_HEIGHT);
	}

	private void renderBricks() {

		List<BrickAdapter> bricks = world.bricks;

		int len = bricks.size();

		for (int i = 0; i < len; i++) {
			TextureRegion brickTexture;
			if (bricks.get(i) instanceof BrickTypeI) {
				brickTexture = Assets.brickTypeI.getTexture(bricks.get(i).hitsLeftToPulverise - 1);
			} else {
				brickTexture = Assets.brickTypeII.getTexture(bricks.get(i).hitsLeftToPulverise - 1);
			}

			batch.draw(brickTexture, bricks.get(i).position.x - bricks.get(i).width / 2, bricks.get(i).position.y
					- bricks.get(i).height / 2, bricks.get(i).width, bricks.get(i).height);
		}
	}

	private void renderPauseButton() {

		int buttonWidth = ButtonSize.MEDIUM_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.MEDIUM_SQUARE.getButtonHeight();

		Button pauseButton = world.pauseButton;
		batch.draw(Assets.pauseGame, pauseButton.position.x - buttonWidth / 2, pauseButton.position.y - buttonHeight
				/ 2, buttonWidth, buttonHeight);

	}

	private void renderSoundButton() {

		int buttonWidth = ButtonSize.MEDIUM_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.MEDIUM_SQUARE.getButtonHeight();
		Button musicButton = world.soundButton;

		if (!Settings.musicEnabled) {

			batch.draw(Assets.soundGameOff, musicButton.position.x - buttonWidth / 2, musicButton.position.y
					- buttonHeight / 2, buttonWidth, buttonHeight);
		} else {
			batch.draw(Assets.soundGameOn, musicButton.position.x - buttonWidth / 2, musicButton.position.y
					- buttonHeight / 2, buttonWidth, buttonHeight);
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
		Assets.font.draw(batch, "LEVEL: " + world.level, World.WORLD_WIDTH - 80, World.WORLD_HEIGHT - 5);
	}

	private void renderRank() {
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "RANK: " + (world.rank > 10 ? "-" : world.rank), World.WORLD_WIDTH - 80,
				World.WORLD_HEIGHT - 20);
	}

	private void renderTotalScore() {
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "TOTAL: " + world.totalScore, World.WORLD_WIDTH - 180, World.WORLD_HEIGHT - 5);
	}

	private void renderNextScore() {
		Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		Assets.font.draw(batch, "NEXT: " + world.nextScore, World.WORLD_WIDTH - 180, World.WORLD_HEIGHT - 20);
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
			batch.draw(Assets.lives, lives.get(i).position.x - buttonWidth / 2, lives.get(i).position.y - buttonHeight
					/ 2, buttonWidth, buttonHeight);
		}
	}
}
