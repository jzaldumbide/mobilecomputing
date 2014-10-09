package au.edu.unimelb.comp90018.brickbreaker.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldRenderer;
import au.edu.unimelb.comp90018.brickbreaker.framework.network.LevelDownloader;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Player;
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
	Rectangle resumeBounds, quitBounds, continueWin, playAgainGameOver, quitGameOver;
	boolean toggleSound;
	int lastScore;
	String scoreString;
	LevelDownloader ld;
	// String ipServer = "192.168.1.13";
	String ipServer = "10.9.163.225";

	public enum GameMode {
		Server, Client
	}

	GameMode myMode;

	public GameScreen(BrickBreaker game, GameMode mode, int level) {
		this.ld = new LevelDownloader();
		this.game = game;
		state = GAME_READY;

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		worldListener = new WorldListener() {

			@Override
			public void getBonusLife() {
				Assets.playSound(Assets.lifeBonusSound);
			}

			@Override
			public void getBonusCoins() {
				Assets.playSound(Assets.coinBonusSound);
			}

			@Override
			public void getBonusBad() {
				Assets.playSound(Assets.lifeLostSound);
			}

			@Override
			public void hitPaddle() {
				Assets.playSound(Assets.touchPaddleSound);
			}

			@Override
			public void hitHardBrick() {
				Assets.playSound(Assets.touchHardBrickSound);
			}

			@Override
			public void hitWall() {
				Assets.playSound(Assets.touchWallSound);
			}

			@Override
			public void hitBrick() {
				Assets.playSound(Assets.touchBrickSound);
			}

			@Override
			public void lifeLost() {
				Assets.playSound(Assets.lifeLostSound);
			}

			@Override
			public void gameOver() {
				Assets.playSound(Assets.gameOverSound);
			}

			@Override
			public void gameWin() {
				Assets.playSound(Assets.winnerSound);
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

		// if (mode == GameMode.Server) {
		// startServerThread();
		// } else if (mode == GameMode.Client) {
		// startClientThread();
		// }

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
			
			world.timeCounter += deltaTime;

			updateRunning(deltaTime);

			createRandomCoin();
			createRandomVirus();
			createRandomExtraLife();

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

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), 
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

				Settings.musicEnabled = !Settings.musicEnabled;

				if (Settings.musicEnabled)
					Assets.music.play();
				else
					Assets.music.pause();

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

			// It is moving to the left
//			world.paddle.position.x = touchPoint.x;
			
//			if (touchPoint.x < world.paddle.position.x) {				
//				accel = World.WORLD_WIDTH * 10f;
//			} 
//			// It is moving to the right
//			else if (touchPoint.x > world.paddle.position.x) { 
//				accel = World.WORLD_WIDTH * -10f;
//			}
		}

		if (Settings.accelerometerEnabled) {
			if (game.orientation == 0)
				accel = Gdx.input.getAccelerometerX() * 200f;
			else if (game.orientation == 1)
				accel = Gdx.input.getAccelerometerY() * -200f;
		}

		world.update(deltaTime, accel);
		

		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;

			// Automatically send results if score is higher than the 10th
			if (Player.getTotalScore() > world.rankings.get(world.rankings.size() - 1))
				uploadScore();

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
			Player.updateScore(world.level, world.score);
			world.level++;
			Player.unlockLevel(world.level);
			

			// Automatically send results if score is higher than the 10th
			if (world.rankings!=null && world.rankings.size() > 0  && Player.getTotalScore() > world.rankings.get(world.rankings.size() - 1))
				uploadScore();
			if (world.rankings!=null && world.rankings.size()==0)
				uploadScore();

		}
	}

	private void uploadScore() {
		try {
			ld.submitHighScore(Player.getPlayerName(), Player.getTotalScore());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updatePaused() {

		if (Gdx.input.justTouched()) {

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), game.viewport.x, game.viewport.y,
					game.viewport.width, game.viewport.height);

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

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), game.viewport.x, game.viewport.y,
					game.viewport.width, game.viewport.height);

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

			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0), game.viewport.x, game.viewport.y,
					game.viewport.width, game.viewport.height);

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

		gl.glViewport((int) game.viewport.x, (int) game.viewport.y, (int) game.viewport.width,
				(int) game.viewport.height);

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

		// if (myMode == GameMode.Server) {
		update(delta);
		// }
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

	public void createRandomCoin() {
		if (world.timeCounter > world.coinShowTime) {
			world.showCoin = true;
		} else {
			world.showCoin = false;
		}

		if (world.coin.position.y < 0 || world.coin.position.y == -10000) {
			world.coinShowTime = (int) world.timeCounter + randInt(10, 20); // show extra life every 10-20 seconds

			world.coin.position.x = World.WORLD_WIDTH / 2 + randInt(-80, 80);
			world.coin.position.y = World.WORLD_HEIGHT / 2;
			world.coin.bounds.x = world.coin.position.x;
			world.coin.bounds.y = world.coin.position.y;

		}
	}

	public void createRandomVirus() {
		if (world.timeCounter > world.virusShowTime) {
			world.showVirus = true;
		} else {
			world.showVirus = false;
		}

		if (world.virus.position.y < 0 || world.virus.position.y == -10000) {
			world.virusShowTime = (int) world.timeCounter + randInt(10, 30); // show extra life every 10-30 seconds
			world.virus.position.x = World.WORLD_WIDTH / 2 + randInt(-100, 100);
			world.virus.position.y = World.WORLD_HEIGHT / 2;
			world.virus.bounds.x = world.virus.position.x;
			world.virus.bounds.y = world.virus.position.y;

		}
	}

	public void createRandomExtraLife() {
		if (world.timeCounter > world.extraLifeShowTime) {
			world.showExtraLife = true;
		} else {
			world.showExtraLife = false;
		}

		if (world.extraLife.position.y < 0 || world.extraLife.position.y == -10000) {
			world.extraLifeShowTime = (int) world.timeCounter + randInt(10, 40); // show extra life every 10-40 seconds
			world.extraLife.position.x = World.WORLD_WIDTH / 2 + randInt(-100, 100);
			world.extraLife.position.y = World.WORLD_HEIGHT / 2;
			world.extraLife.bounds.x = world.extraLife.position.x;
			world.extraLife.bounds.y = world.extraLife.position.y;
		}
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}