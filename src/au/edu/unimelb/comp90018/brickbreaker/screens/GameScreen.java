package au.edu.unimelb.comp90018.brickbreaker.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldRenderer;
import au.edu.unimelb.comp90018.brickbreaker.framework.network.LevelDownloader;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class GameScreen extends ScreenAdapter implements TextInputListener {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int GAME_LIFE_LOST = 5;

	BrickBreaker game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	// Rectangle viewport;
	Rectangle resumeBounds, quitBounds, continueWin, playAgainGameOver, quitGameOver;
	boolean toggleSound;
	int lastScore;
	String scoreString;
	// String ipServer = "192.168.1.13";
	String ipServer = "10.9.163.225";

	public enum GameMode {
		Server, Client
	}

	GameMode myMode;

	public GameScreen(BrickBreaker game, GameMode mode, int level) {

		this.game = game;
		state = GAME_READY;

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		worldListener = new WorldListener() {

			@Override
			public void hitPaddle() {
				Assets.playSound(Assets.getBonusSound);
			}

			@Override
			public void hitBrick() {
				Assets.playSound(Assets.touchHardBrickSound);
			}

			@Override
			public void lifeLost() {
				Assets.playSound(Assets.lifeLostSound);
			}

			@Override
			public void gameOver() {
				Assets.playSound(Assets.gameOverSound);
			}
		};

		world = new World(worldListener, level);
		renderer = new WorldRenderer(game.batcher, world);

		resumeBounds = new Rectangle(90, 120, 60, 50);
		quitBounds = new Rectangle(180, 120, 60, 50);

		continueWin = new Rectangle(200, 120, 80, 50);
		playAgainGameOver = new Rectangle(90, 120, 60, 50);
		quitGameOver = new Rectangle(180, 120, 60, 50);

		toggleSound = true;
		lastScore = 0;
		scoreString = "SCORE: 0";		

		if (mode == GameMode.Server) {
			startServerThread();
		} else if (mode == GameMode.Client) {
			startClientThread();
		}
		
		myMode = mode;
	}

	public void startServerThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				ServerSocketHints serverSocketHint = new ServerSocketHints();

				// 0 means no timeout. Probably not the greatest idea in
				// production!
				serverSocketHint.acceptTimeout = 0;

				// Create the socket server using TCP protocol and listening on
				// 9021. Only one app can listen to a port at a time, keep in
				// mind many ports are reserved especially in the lower numbers
				// ( like 21, 80, etc )
				ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 9021, serverSocketHint);

				// Loop forever
				while (true) {

					// Create a socket
					Socket socket = serverSocket.accept(null);

					while (true) {
						StringBuffer sb = new StringBuffer();
						try {
							sb.append(String.valueOf(state)).append(";")
									.append(String.valueOf(world.paddle.position.x)).append(";")
									.append(String.valueOf(world.paddle.position.y)).append(";")
									.append(String.valueOf(world.ball.position.x)).append(";")
									.append(String.valueOf(world.ball.position.y)).append("\n");

							socket.getOutputStream().write(sb.toString().getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public void startClientThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				SocketHints socketHints = new SocketHints();

				// Socket will time our in 4 seconds
				socketHints.connectTimeout = 4000;

				// Create the socket and connect to the server on port 9021
				Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ipServer, 9021, socketHints);

				BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String msg = null;

				while (true) {
					try {
						msg = buffer.readLine();
						String[] parts = msg.split(";");

						state = Integer.parseInt(parts[0]);
						world.paddle.position.set(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
						world.ball.position.set(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void update(float deltaTime) {
		
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LIFE_LOST:
			updateLifeLost();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady() {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning(float deltaTime) {

		if (Gdx.input.justTouched()) {

			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);

			if (world.pauseButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}

			if (world.soundButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				
				Assets.playSound(Assets.clickSound);

				if (Settings.musicEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
				
				Settings.musicEnabled = !Settings.musicEnabled;
				Settings.soundEnabled = !Settings.soundEnabled;
				
				return;
			}
		}

		float accel = 0;

		if (Gdx.input.isTouched()) {

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);

			if (touchPoint.x < world.paddle.position.x) { // is moving to the
															// left
				accel = World.WORLD_WIDTH * 10f;
			} else if (touchPoint.x > world.paddle.position.x) { // is moving to
																	// the right
				accel = World.WORLD_WIDTH * -10f;
			}
		}

		if (Settings.accelerometerEnabled) {
			world.update(deltaTime, Gdx.input.getAccelerometerX() * 200f);
		} else {
			world.update(deltaTime, accel);
		}

		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			// if (lastScore >= Settings.highscores[4])
			// scoreString = "NEW HIGHSCORE: " + lastScore;
			// else
			// scoreString = "SCORE: " + lastScore;
			// Settings.addScore(lastScore);
			// Settings.save();
		} else if (world.state == World.WORLD_STATE_GAME_LOST_LIFE) {
			state = GAME_READY;
		} else if (world.state == World.WORLD_STATE_LEVEL_END) {
			state = GAME_LEVEL_END;
		}
	}

	private void updatePaused() {

		if (Gdx.input.justTouched()) {

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);

			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MenuScreen(game));
				return;
			}

		}
	}

	private void updateLifeLost() {
		if (Gdx.input.justTouched()) {
			state = GAME_LIFE_LOST;
		}
	}

	private void updateLevelEnd() {

		if (Gdx.input.justTouched()) {

			state = GAME_LEVEL_END;

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);

			if (continueWin.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new LevelScreen(game));
				return;
			}

		}
	}

	private void updateGameOver() {

		if (Gdx.input.justTouched()) {

			state = GAME_OVER;

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
					game.viewport.x, 
					game.viewport.y,
					game.viewport.width, 
					game.viewport.height
					);

			if (playAgainGameOver.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				// TODO: We should restart World here something like this:
				world = new World(worldListener, 1);
				renderer = new WorldRenderer(game.batcher, world);
				world.score = 0;
				state = GAME_RUNNING;
				return;
			}

			if (quitGameOver.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.toggleSound);
				// TODO: We should restart World here
				// Gdx.input.getTextInput(this, "Enter Name", "");
				game.setScreen(new MenuScreen(game));
				return;
			}
		}
	}

	public void draw() {

		GL20 gl = Gdx.gl;

		gl.glViewport(
				(int) game.viewport.x, 
				(int) game.viewport.y, 
				(int) game.viewport.width,
				(int) game.viewport.height
				);
		
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Render Objects in screen */
		renderer.render();

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();

		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LIFE_LOST:
			presentLostLife();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}

		game.batcher.end();
	}

	private void presentReady() {
		game.batcher.draw(Assets.ready, 0, 0, 320, 480);
	}

	private void presentRunning() {

	}

	private void presentPaused() {
		game.batcher.draw(Assets.pauseMenu, 0, 0, 320, 480);
	}

	private void presentLostLife() {
		game.batcher.draw(Assets.ready, 0, 0, 320, 480);
	}

	private void presentLevelEnd() {
		game.batcher.draw(Assets.win, 0, 0, 320, 480);
		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(game.batcher, String.valueOf(world.score), 175, 211);
	}

	private void presentGameOver() {
		game.batcher.draw(Assets.gameOver, 0, 0, 320, 480);
		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(game.batcher, String.valueOf(world.score), 175, 211);
	}

	@Override
	public void render(float delta) {
		if (myMode == GameMode.Server) {
			update(delta);
		}
		draw();
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
	}

	@Override
	public void input(String name) {
		if (name.length() > 0) {
			LevelDownloader ld = new LevelDownloader();
			try {
				ld.submitHighScore(name, world.score);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}