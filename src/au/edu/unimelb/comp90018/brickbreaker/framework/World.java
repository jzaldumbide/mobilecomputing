package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.Bonus;
import au.edu.unimelb.comp90018.brickbreaker.actors.Bonus.BonusType;
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
	
	public static final float xPosLife = 70;
	public static final float yPosLife = 453;
	public static final float xSpanLife = 19;

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_LEVEL_END = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_STATE_GAME_LOST_LIFE = 3;

	public Ball ball;
	public Paddle paddle;
	public List<BrickAdapter> bricks;
	public List<Button> lives;
	public Bonus coin;
	public Bonus virus;
	public Bonus extraLife;
	public Button pauseButton, soundButton;
	public float timeCounter;
	public int coinShowTime;
	public int virusShowTime;
	public int extraLifeShowTime;
	public boolean showCoin;
	public boolean showVirus;
	public boolean showExtraLife;

	public final WorldListener listener;
	public int level;
	public int score;
	public int state;
	public int rank;
	public int nextScore;
	public int totalScore;
	public List<Integer> rankings; //List with top 10 rankings to beat
	Player player; //Object to handle total score

	
	public World(WorldListener listener, int gameLevel) {

		timeCounter = 0;
		coinShowTime = 15; //first coin will appear after 15sec
		virusShowTime = 25; //first virus will appear after 25sec
		extraLifeShowTime = 40; //first extra life will appear after 40sec
		level = gameLevel;
		showCoin=false;
		showVirus=false;
		showExtraLife=false;
		// TODO: Ball's initial velocity must be a world's parameter. Even the
		// initial position of the ball and paddle.
	
		bricks = new ArrayList<BrickAdapter>();
		lives = new ArrayList<Button>();

		/*Create here Bonuses*/
		coin = new Bonus(WORLD_WIDTH/2,WORLD_HEIGHT/2+40,BonusType.COIN,new Vector2(0,10));
		virus = new Bonus(WORLD_WIDTH/2,WORLD_HEIGHT/2+40,BonusType.VIRUS,new Vector2(0,15));
		extraLife = new Bonus(WORLD_WIDTH/2,WORLD_HEIGHT/2+40,BonusType.EXTRA_LIFE,new Vector2(0,10));

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
		//Download and persist high scores
		LevelDownloader ld = new LevelDownloader();
		String highScores;
		try {
			highScores = ld.downloadHighScores();
			ld.persistScores(highScores);
			this.rankings = ld.loadHighScoresAsList();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		//Test whats the current rank of the user based on the general score
		this.totalScore = player.getTotalScore()-player.getLevelScore(this.level);
		updateRanking(this.totalScore);

		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	/**
	 * 
	 */
	private void updateRanking(int totalScore) {
		this.rank = 1;
		
		for (Iterator<Integer> i = rankings.iterator(); i.hasNext(); ){
			int score = i.next();
			if ( score>totalScore ){
				this.rank++;
				this.nextScore = score;
			}
		}
	}

	private void generateLevel() {

		Random rand = new Random();
		boolean error = false;
		/*Set lives*/
		float x = xPosLife;
		for (int i = 1; i <= 3; i++) {
			lives.add(new Button(x, yPosLife,ButtonSize.SMALL_SQUARE));
			x += xSpanLife;
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
			// TODO: paddle width is hard-coded
			paddle = new Paddle(WORLD_WIDTH / 2, WORLD_HEIGHT * 0.15f, 96f);

			ball = new Ball(WORLD_WIDTH / 2, paddle.position.y + Paddle.PADDLE_HEIGHT / 2 + Ball.BALL_HEIGHT / 2,
					new Vector2(WORLD_WIDTH * 0.4f, WORLD_HEIGHT * 0.4f));

			/*Set bricks*/
			float xTemp = 36;
			float yTemp = 300;
			for (int i = 1; i <= 3; i++) {
				for (int j = 1; j <= 8; j++) {
					if (rand.nextInt(2) == 1)
						bricks.add(new BrickTypeI(xTemp, yTemp));
					else
						bricks.add(new BrickTypeII(xTemp, yTemp));
					//				bricks.add(new Brick(x, y));

					xTemp += 35;
				}
				xTemp = 36;
				yTemp += 24;
			}
		}
	}

	public void update(float deltaTime, float accelX) {
				
		updateBall(deltaTime);
		updatePaddle(deltaTime, accelX);
		
		if (showCoin){ //start coin animation
			updateCoin(deltaTime);
		}
		
		if (showVirus){ //start virus animation
			updateVirus(deltaTime);
		}
		
		checkCollisions();
		checkLostLife();
		
		if (showExtraLife){ //start virus animation
			updateExtraLife(deltaTime);
		}
		
		checkGameOver();
		checkNextLevel();
	}
	
	private void updateCoin(float deltaTime){
		coin.update(deltaTime);
	}
	
	private void updateVirus(float deltaTime){
		virus.update(deltaTime);
	}
	
	private void updateExtraLife(float deltaTime){
		extraLife.update(deltaTime);
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
			
			if (lives.size() >= 3){ //if player has 3 lives you get 3 bonus points!!
				score += 3;
				this.totalScore+=3;
				updateRanking(this.totalScore);
			}
		}
	}
	
	private void checkLostLife() {
		if (ball.position.y <= 0){
			listener.lifeLost();//play sound
			int len = lives.size() - 1;
			if (len != -1){
				lives.remove(len); //life lost
				score --; //reduce 1 point in score
				this.totalScore--;
				updateRanking(this.totalScore);
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
		checkCoinCollision();
		checkVirusColision();
		checkExtraLifeCollision();
		checkBrickCollision();
	}

	private void checkWallCollision() {
		if(ball.position.x - Ball.BALL_WIDTH/2 <= 0 || ball.position.x + Ball.BALL_WIDTH/2 >= WORLD_WIDTH || ball.position.y + Ball.BALL_HEIGHT/2 >= WORLD_HEIGHT){
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
		}
	}
		
	private void checkCoinCollision(){
	if (coin.bounds.overlaps(paddle.bounds)){
			coin.pulverize();
			score++;
			this.totalScore++;
			updateRanking(this.totalScore);
			listener.getBonusCoins();//play sound
		}
	}
	
	private void checkVirusColision(){
		if (virus.bounds.overlaps(paddle.bounds)) {
			virus.pulverize();
			
			paddle.width = paddle.width * 0.8f;
			paddle.bounds.width = paddle.bounds.width * 0.8f;
			
			score--;
			this.totalScore--;
			updateRanking(this.totalScore);
			listener.getBonusBad();// play sound
		}
	}
	
	private void checkExtraLifeCollision(){
		if (extraLife.bounds.overlaps(paddle.bounds)) {
			extraLife.pulverize();
			score++;
			totalScore++;			
			
			int nLives = lives.size();
			float xPos = nLives * ButtonSize.SMALL_SQUARE.getButtonWidth();
			float spanTemp = nLives * xSpanLife;
			
			lives.add(new Button(xPosLife+spanTemp, yPosLife,ButtonSize.SMALL_SQUARE));
			listener.getBonusLife();// play sound
			updateRanking(this.totalScore);
		}
	}


	private void checkBrickCollision() {

		int len = bricks.size();
		for (int i = 0; i < len; i++) {
			if (ball.bounds.overlaps(bricks.get(i).bounds)) {
				score++;
				totalScore++;
				listener.hitBrick();//play sound
				ball.hitBrick(bricks.get(i).bounds);

				// TODO: Don't know if this line needs to be included in ball.hitBrick
				bricks.get(i).hitMe();
				updateRanking(this.totalScore);
				
				if (bricks.get(i).isPulverised()){
					bricks.remove(i);
				}
					
				break;
			}
		}
	}
}

