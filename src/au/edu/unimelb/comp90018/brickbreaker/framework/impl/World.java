package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import au.edu.unimelb.comp90018.brickbreaker.actors.Ball;
import au.edu.unimelb.comp90018.brickbreaker.actors.Brick;
import au.edu.unimelb.comp90018.brickbreaker.actors.Paddle;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.model.GameLevel;
import au.edu.unimelb.comp90018.brickbreaker.framework.network.LevelDownloader;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

public class World {

//	public static final Vector2 gravity = new Vector2(0, -12);
	
	static final float GAME_WIDTH = Assets.backgroundRegion.getRegionWidth();
	static final float GAME_HEIGHT = Assets.backgroundRegion.getRegionHeight();	

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
//
	public Ball ball;
	public List<Brick> bricks;
	public Paddle paddle;
	
//	public final Wall wall;
//	
	public final WorldListener listener;
	public final ShapeRenderer shapeRenderer;
//	//public final Random rand;
	
	
//
//	public float heightSoFar;
//	public int score;
//	public int state;
//
	
	public World (WorldListener listener) {
		this.listener = listener;
		this.bricks = new ArrayList<Brick>();
		this.shapeRenderer = new ShapeRenderer();
		
		/*Generates the level, this method should include the call to a xml loader file */
		generateLevel();

		//this.heightSoFar = 0;
		//this.score = 0;
		//this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel () {
		
		this.ball = new Ball(Assets.red_ball,GAME_WIDTH/2 - Assets.red_ball.getRegionWidth()/2,40+Assets.paddle.getRegionHeight()-3,100);
		this.paddle = new Paddle(Assets.paddle,GAME_WIDTH/2 - Assets.paddle.getRegionWidth()/2,40,600);
		//Generate example bricks
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 8; x++) {
				Brick brick = new Brick(Assets.brick,5*(x+1)+(x*Assets.brick.getRegionWidth()), GAME_HEIGHT - ((10*(y+1))+(y+1)*Assets.brick.getRegionHeight()));
				this.bricks.add(brick);				
			}
		}
	
//		LevelDownloader ld = new LevelDownloader();
//		GameLevel gameLevel = null;
//		try {
//			gameLevel = ld.downloadGame("brickbreaker_level1.xml");
//			/*Here you should generate the game level using the configurations loaded from XMl file
//			 * note that all parameters from paddle example are constants, some of them could be part of the xml file.
//			 */
//			this.ball = new Ball(Assets.red_ball,GAME_WIDTH/2 - Assets.red_ball.getRegionWidth()/2,40+Assets.paddle.getRegionHeight()-3,gameLevel.getBall().getSpeed());
//			this.paddle = new Paddle(Assets.paddle,GAME_WIDTH/2 - Assets.paddle.getRegionWidth()/2,40,gameLevel.getPaddle().getSpeed());
//			
//			au.edu.unimelb.comp90018.brickbreaker.framework.model.Brick modelBricks[][] = gameLevel.getBricks();
//			
//			for ( int i = 0; i < modelBricks.length; i++ ){
//				for ( int j = 0; j < modelBricks[i].length; j++ ){
//					if (modelBricks[i][j]!=null){
//						int x = modelBricks[i][j].getX();
//						int y = modelBricks[i][j].getY();
//						Brick brick = new Brick(Assets.brick,5*(x+1)+(x*Assets.brick.getRegionWidth()), 
//								GAME_HEIGHT - ((10*(y+1))+(y+1)*Assets.brick.getRegionHeight()));
//						this.bricks.add(brick);
//					}
//				}
//			}
//
//		} catch (IOException | XmlPullParserException  | NullPointerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			this.ball = new Ball(Assets.red_ball,GAME_WIDTH/2 - Assets.red_ball.getRegionWidth()/2,40+Assets.paddle.getRegionHeight()-3,500);
//			this.paddle = new Paddle(Assets.paddle,GAME_WIDTH/2 - Assets.paddle.getRegionWidth()/2,40,600);
//			//Generate example bricks
//			
//			for (int y = 0; y < 3; y++) {
//				for (int x = 0; x < 8; x++) {
//					Brick brick = new Brick(Assets.brick,5*(x+1)+(x*Assets.brick.getRegionWidth()), GAME_HEIGHT - ((10*(y+1))+(y+1)*Assets.brick.getRegionHeight()));
//					this.bricks.add(brick);				
//				}
//			}
//
//		}		
	}

	public void update (float deltaTime, OrthographicCamera guiCam) {
		updateBall(deltaTime);
		updatePaddle(deltaTime,guiCam);
		//updateBricks();
		//checkGameOver();
	}

	private void updateBall (float deltaTime) {
			ball.update(deltaTime, this);
	}

	private void updatePaddle (float deltaTime , OrthographicCamera guiCam) {
			paddle.update(deltaTime, guiCam);
	}

//	private void updateBricks (float deltaTime) {
//
//	}

}
