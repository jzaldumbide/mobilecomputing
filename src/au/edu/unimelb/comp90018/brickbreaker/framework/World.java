package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickAdapter;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeI;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickTypeII;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.actors.GameLevel;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.network.LevelDownloader;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class World {

	public static final float WORLD_WIDTH = 320;
	public static final float WORLD_HEIGHT = 480;

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_LEVEL_END = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_STATE_GAME_LOST_LIFE = 3;

	public Ball ball;
	public Paddle paddle;
	public List<BrickAdapter> bricks;
	public List<Button> lives;
	public Button pauseButton, soundButton;

	public final WorldListener listener;
	public int level;
	public int score;
	public int state;
	public int rank;

	public World(WorldListener listener, int gameLevel) {

		level = gameLevel;
		// TODO: Ball's initial velocity must be a world's parameter. Even the
		// initial position of the ball and paddle.

		bricks = new ArrayList<BrickAdapter>();
		lives = new ArrayList<Button>();

		soundButton = new Button(ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2 + 5,
				ButtonSize.MEDIUM_SQUARE.getButtonHeight() / 2 + 2, ButtonSize.MEDIUM_SQUARE);

		pauseButton = new Button(WORLD_WIDTH - 0.5f * ButtonSize.MEDIUM_SQUARE.getButtonWidth() - 5,
				ButtonSize.MEDIUM_SQUARE.getButtonHeight() / 2 + 2, ButtonSize.MEDIUM_SQUARE);

		this.listener = listener;

		/*
		 * Generates the level, this method should include the call to a xml
		 * loader file
		 */
		generateLevel();

		this.score = 0;
		this.rank = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel() {

		Random rand = new Random();
		boolean error = false;
		/*Set lives*/
		float x = 77;
		float y = 453;
		for (int i = 1; i <= 3; i++) {
			lives.add(new Button(x, y,ButtonSize.SMALL_SQUARE));
			x += 19;
		}

		LevelDownloader ld = new LevelDownloader();
		GameLevel gameLevel = null;
		try {
			gameLevel = ld.loadLevel("brickbreaker_level"+this.level+".xml", WORLD_WIDTH, WORLD_HEIGHT);
			/*Here you should generate the game level using the configurations
		 loaded from XMl file
			 * note that all parameters from paddle example are constants, some of
		 them could be part of the xml file.
			 */
			paddle = gameLevel.getPaddle();
			ball = gameLevel.getBall();
			Gdx.app.log("We will play with "+gameLevel.getBricks().size()+"Bricks", "We will play with "+gameLevel.getBricks().size()+"Bricks");
			bricks = gameLevel.getBricks();

		} catch (IOException e) {
			e.printStackTrace();
			error = true;
		} catch (XmlPullParserException ex){
			ex.printStackTrace();
			error = true;

		}
		if(error){
			paddle = new Paddle(WORLD_WIDTH / 2, WORLD_HEIGHT * 0.15f);

			ball = new Ball(WORLD_WIDTH / 2, paddle.position.y + Paddle.PADDLE_HEIGHT / 2 + Ball.BALL_HEIGHT / 2,
					new Vector2(WORLD_WIDTH * 0.4f, WORLD_HEIGHT * 0.4f));

			/*Set bricks*/
			x = 36;
			y = 300;
			for (int i = 1; i <= 3; i++) {
				for (int j = 1; j <= 8; j++) {
					if (rand.nextInt(2) == 1)
						bricks.add(new BrickTypeI(x, y));
					else
						bricks.add(new BrickTypeII(x, y));
					//				bricks.add(new Brick(x, y));

					x += 35;
				}
				x = 36;
				y += 24;
			}
		}
	}

	public void update(float deltaTime, float accelX) {
		updateBall(deltaTime);
		updatePaddle(deltaTime, accelX);
		checkCollisions();
		checkLostLife();
		checkGameOver();
		checkNextLevel();
	}

	private void updateBall(float deltaTime) {
		ball.update(deltaTime);
	}

	private void updatePaddle(float deltaTime, float accelX) {
		// TODO: Review factor of normalisation of the acceleration
		paddle.update(deltaTime, -accelX);
	}

	
	private void checkNextLevel(){
		if (this.bricks.size()<1){
			listener.gameWin();//play sound
			this.state = WORLD_STATE_LEVEL_END;
			level++;
			Player.unlockLevel(level);
			Player.updateScore(level, score);
		}
	}
	
	private void checkLostLife() {
		if (ball.position.y <= 0){
			listener.lifeLost();//play sound
			int len = lives.size() - 1;
			if (len != -1){
				lives.remove(len); //life lost
				score --; //reduce 1 point in score
				this.state = WORLD_STATE_GAME_LOST_LIFE;
			}else{
				this.state = WORLD_STATE_GAME_OVER;
			}
			
		}
	}
	
	private void checkGameOver() {
		if (this.lives.size()<1){
			listener.gameOver();//play sound
			this.state = WORLD_STATE_GAME_OVER;
		}
	}

	private void checkCollisions() {
		checkWallCollision();
		checkPaddleCollision();
		checkBrickCollision();
	}

	private void checkWallCollision() {
		if(ball.position.x < ball.bounds.getWidth()/2 || ball.position.x > WORLD_WIDTH - ball.bounds.getWidth()/2 || ball.position.y > WORLD_HEIGHT - ball.bounds.getHeight()/2){
			listener.hitWall();//play sound
		}
	}

	
	private void checkPaddleCollision() {

		if (ball.velocity.y > 0)
			return;

		if (ball.bounds.overlaps(paddle.bounds)) {			
			//			List<RectangleSide> sides = ball.bounds.whichSidesOverlapMe(paddle.bounds);			
			ball.hitPaddle(paddle.velocity.x);
			listener.hitPaddle();//play sound
			// listener.jump();
			// if (rand.nextFloat() > 0.5f) {
			// platform.pulverize();
			// }

		}
	}

	private void checkBrickCollision() {

		int len = bricks.size();
		for (int i = 0; i < len; i++) {
			if (ball.bounds.overlaps(bricks.get(i).bounds)) {
				listener.hitBrick();//play sound
				ball.hitBrick(bricks.get(i).bounds);

				// TODO: Don't know if this line needs to be included in ball.hitBrick
				bricks.get(i).hitMe();

				if (bricks.get(i).isPulverised())
					bricks.remove(i);
					score ++;
				break;
			}
		}
	}
}
