package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;

public class World {

//	public static final Vector2 gravity = new Vector2(0, -12);
	
	/*Extents of our world horizontally and vertically*/
	public static final float WORLD_WIDTH = Gdx.graphics.getWidth();
	public static final float WORLD_HEIGHT = Gdx.graphics.getHeight();
	

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
//
//	public final BallActor ball;
//	public final List<BrickActor> bricks;
	public Paddle paddle;
//	public final Wall wall;
//	
	public final WorldListener listener;
//	//public final Random rand;
//
//	public float heightSoFar;
//	public int score;
//	public int state;
//
	
	public World (WorldListener listener) {
		
		/*Initialize Actors*/
		//this.ball = new BallActor(5, 1);
		//this.bricks = new ArrayList<BrickActor>();
		//this.paddle = new Paddle(0,0);
		//this.wall = new Wall();
		this.listener = listener;
		
		/*Generates the level, this method should include the call to a xml loader file */
		generateLevel();

		//this.heightSoFar = 0;
		//this.score = 0;
		//this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel () {
		
		/*Here you should generate the game level using the configurations loaded from XMl file
		 * note that all parameters from paddle example are constants, some of them could be part of the xml file.
		 */
		this.paddle = new Paddle(Assets.paddle,320/2-Assets.paddle.getRegionWidth()/2,40,500);

	}

	public void update (float deltaTime, float accelX) {
		//updateBall(deltaTime, accelX);
		updatePaddle(deltaTime);
		//updateBricks();
		//checkGameOver();
	}

//	private void updateBall (float deltaTime, float accelX) {
//
//	}

	private void updatePaddle (float deltaTime) {
			paddle.update(deltaTime);
	}

//	private void updateBricks (float deltaTime) {
//
//	}


//	private void checkCollisions () {
//		checkPlatformCollisions();
//		checkSquirrelCollisions();
//		checkItemCollisions();
//		checkCastleCollisions();
//	}

}
