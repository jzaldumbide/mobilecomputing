package au.edu.unimelb.comp90018.brickbreaker.screens;


import java.util.ArrayList;
import java.util.Iterator;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreakerGame;
import au.edu.unimelb.comp90018.brickbreaker.actors.BallActor;
import au.edu.unimelb.comp90018.brickbreaker.actors.BrickActor;
import au.edu.unimelb.comp90018.brickbreaker.actors.PaddleActor;
import au.edu.unimelb.comp90018.brickbreaker.actors.Wall;
import au.edu.unimelb.comp90018.brickbreaker.framework.Collideable;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.BreakoutContactListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
//import au.edu.unimelb.comp90018.brickbreaker.framework.Game;
//import au.edu.unimelb.comp90018.brickbreaker.framework.Screen;

//import com.eyeofmidas.breakout.collisions.BreakoutContactListener;

public class BreakBreakerScreen implements Screen {

	private Stage breakoutStage;
	private BallActor ball;
	private PaddleActor paddle;
	private ArrayList<BrickActor> bricks = new ArrayList<BrickActor>();
	
	/*The world object is basically what holds all your physics objects/bodies and simulates the reactions between them.*/
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private boolean debug = false;
	private boolean[] keys = new boolean[4];
	private BreakoutContactListener contactListener;
		
	private BrickBreakerGame game;
	private int lives;
	private Vector2 worldToScreen;
	
	public BreakBreakerScreen(final BrickBreakerGame game) {
		this.game = game;

		worldToScreen = new Vector2();
		breakoutStage = new Stage();
				
		//Implements the Input Listeners
		breakoutStage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				paddle.setX(x);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				paddle.setX(x);
				return true;
			}

			@Override
			public boolean keyDown(InputEvent event, int keyCode) {
				switch (keyCode) {
				case Input.Keys.A:
				case Input.Keys.LEFT:
					keys[0] = true;
					break;
				case Input.Keys.D:
				case Input.Keys.RIGHT:
					keys[1] = true;
					break;
				case Input.Keys.BACK:
				case Input.Keys.ESCAPE:
					game.pauseGame();
					break;
				}
				return false;
			}

			@Override
			public boolean keyUp(InputEvent event, int keyCode) {
				switch (keyCode) {
				case Input.Keys.A:
				case Input.Keys.LEFT:
					keys[0] = false;
					break;
				case Input.Keys.D:
				case Input.Keys.RIGHT:
					keys[1] = false;
					break;
				}
				return false;
			}
		});
		
		
		
		
		/*The first argument we supply is a 2D vector containing the gravity: 0 to indicate no gravity in the horizontal direction, 
		 * and -10 is a downwards force like in real life (assuming your y axis points upwards). These values can be anything you like, 
		 * but remember to stick to a constant scale. In Box2D 1 unit = 1 metre.*/
		world = new World(new Vector2(0f, 0f), true);
		
		
		contactListener = new BreakoutContactListener();
		world.setContactListener(contactListener);
		
		/*For testing purposes*/
		debugRenderer = new Box2DDebugRenderer();

		ball = new BallActor(world, worldToScreen);
		breakoutStage.addActor(ball);

		paddle = new PaddleActor(world, game, worldToScreen);
		breakoutStage.addActor(paddle);

		
		Color[] brickColors = new Color[3];
		brickColors[0] = new Color(241 / 255f, 92 / 255f, 92 / 255f, 1f);
		brickColors[1] = new Color(240 / 255f, 209 / 255f, 40 / 255f, 1f);
		brickColors[2] = new Color(102 / 255f, 213 / 255f, 110 / 255f, 1f);
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 12; x++) {
				BrickActor brick = new BrickActor(world, game, worldToScreen);
				brick.setPosition(4f + (x * 6.5f), 49 - (y * 4));
				brick.setColor(brickColors[y]);
				bricks.add(brick);
				breakoutStage.addActor(brick);
			}
		}
		

		Wall leftWall = new Wall(world, game);
		leftWall.setPosition(-1.0f, 25f);
		leftWall.setSize(1.0f, 31f);
		leftWall.create();

		Wall rightWall = new Wall(world, game);
		rightWall.setPosition(81f, 25f);
		rightWall.setSize(1.0f, 31f);
		rightWall.create();

		Wall ceiling = new Wall(world, game);
		ceiling.setPosition(0f, 56f);
		ceiling.setSize(80f, 1.0f);
		ceiling.create();

		this.reset();
	}

	public void reset() {
		ball.reset();
		paddle.reset();
		lives = 3;
	}

	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1137f, 0.16f, 0.145f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handlePaddleInput();
		breakoutStage.act(Gdx.graphics.getDeltaTime());
		breakoutStage.draw();

		if (debug) {
		    //It is recommended to render all graphics before physics step, otherwise it will be out of sync.
			debugRenderer.render(world, breakoutStage.getCamera().combined.scl(worldToScreen.x, worldToScreen.y, 1f));
		}
		
		//Stepping basically updates the world objects through time.
		world.step(1 / 45f, 6, 2);

		
		if (ball.isDying()) {
			Assets.instance.sounds.incorrect.play();
			lives--;
			//if (lives <= 0) {
			//	game.endGame();
			//} else {
				ball.reset();
			//}
		}
		
		
		deleteDeadBodies();
		Iterator<BrickActor> iterator = bricks.iterator();
		while (iterator.hasNext()) {
			BrickActor brick = iterator.next();
			if (brick.isDying()) {
				iterator.remove();
			}
		}

		if (bricks.size() <= 0) {
			Assets.instance.sounds.shake.play();
			game.endGame();
		}
	
	
	} 


	private void deleteDeadBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		Body node = null;
		while (bodies.size > 0) {
			node = bodies.pop();
			Body oBj = node;
			if (((Collideable) oBj.getUserData()).isDying()) {
				removeBodySafely(oBj);
			}
		}
	}

	private void removeBodySafely(Body body) {
		final Array<JointEdge> list = body.getJointList();
		while (list.size > 0) {
			world.destroyJoint(list.get(0).joint);
		}
		world.destroyBody(body);
	}

	private void handlePaddleInput() {
		if (keys[0]) {
			paddle.moveLeft();
		} else if (keys[1]) {
			paddle.moveRight();
		} else {
			paddle.stop();
		}

	}

	
	@Override
	public void resize(int width, int height) {
		breakoutStage.getViewport().update(width, height, true);
		worldToScreen.x = 10f * BrickBreakerGame.scale.x;
		worldToScreen.y = 10f * BrickBreakerGame.scale.y;
	}
	

	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(breakoutStage);
		Gdx.input.setCatchBackKey(true);
		this.reset();
		//breakoutStage.addActor(game.header);
	}
	

	
	@Override
	public void hide() {
	}
	

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		breakoutStage.dispose();
		world.dispose();
	}


}
