package au.edu.unimelb.comp90018.brickbreaker.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.WorldRenderer;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class GameScreen extends ScreenAdapter {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	BrickBreaker game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle viewport;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	boolean toggleSound;
	int lastScore;
	String scoreString;
	String ipAddress = null;
	String ipServer = "192.168.56.101";

	public enum GameMode {
		Server, Client
	}

	GameMode myMode;

	public GameScreen(BrickBreaker game, GameMode mode, int level) {

		this.game = game;	
		state = GAME_READY;

		// We need to have a target resolution, e.g. 320 x 480
		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		worldListener = new WorldListener() {

			@Override
			public void hitPaddle() {
				Assets.playSound(Assets.correctSound);
			}

			@Override
			public void hitBrick() {
				Assets.playSound(Assets.clickSound);
			}

			@Override
			public void loseLife() {
				Assets.playSound(Assets.incorrectSound);
			}
		};

		world = new World(worldListener, level);
		renderer = new WorldRenderer(game.batcher, world);

		resumeBounds = new Rectangle(85, 250, 150, 30);
		quitBounds = new Rectangle(122, 200, 76, 38);

		toggleSound = true;
		lastScore = 0;
		scoreString = "SCORE: 0";

		myMode = mode;

		if (mode == GameMode.Server) {
//			startServerNetwork();
		} else if (mode == GameMode.Client) {
//			sendMessage();
		}
	}

	public void startServerNetwork() {

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

					// Read data from the socket into a BufferedReader
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					try {
						while (true) {
							// Read to the next newline (\n) and display
							Gdx.app.log("Recibido: ", buffer.readLine());
							// synchronized (this) {
							// world.score =
							// Integer.parseInt(buffer.readLine());
							// }
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public String getmyipNetwork() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						Gdx.app.log("ip: ", inetAddress.getHostAddress().toString());
						ipAddress = inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			Gdx.app.log("Exception caught =", e.getMessage());
		}
		return ipAddress;

	}

	public void sendMessage() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				String textToSend = new String();
				
				SocketHints socketHints = new SocketHints();
				
				// Socket will time our in 4 seconds
				socketHints.connectTimeout = 4000;
				
				// Create the socket and connect to the server on port 9021
				Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ipServer, 9021, socketHints);
				
				while (true) {
					try {

						textToSend = String.valueOf(Gdx.input.getX());

						// write our entered message to the stream
						socket.getOutputStream().write(textToSend.getBytes());
						
						Gdx.app.log(ipAddress + " dice: ", textToSend);
						
					} catch (IOException e) {
						e.printStackTrace();
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
					viewport.x, viewport.y, viewport.width, viewport.height);

			if (world.pauseButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}

			if (world.soundButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
				return;
			}
		}

		float accel = 0;

		if (Gdx.input.isTouched()) {

			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0),
					viewport.x, viewport.y, viewport.width, viewport.height);

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

		// if (world.score != lastScore) {
		// lastScore = world.score;
		// scoreString = "SCORE: " + lastScore;
		// }
		// if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
		// //game.setScreen(new WinScreen(game));
		// }
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			// if (lastScore >= Settings.highscores[4])
			// scoreString = "NEW HIGHSCORE: " + lastScore;
			// else
			// scoreString = "SCORE: " + lastScore;
			// Settings.addScore(lastScore);
			// Settings.save();
		}
	}

	private void updatePaused() {

		if (Gdx.input.justTouched()) {

			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0),
					viewport.x, viewport.y, viewport.width, viewport.height);

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

	private void updateLevelEnd() {
		if (Gdx.input.justTouched()) {
			world = new World(worldListener, 1); //Fix this
			renderer = new WorldRenderer(game.batcher, world);
			// world.score = lastScore;
			state = GAME_READY;
		}
	}

	private void updateGameOver() {
		if (Gdx.input.justTouched()) {
			// TODO: this is just for testing
			state = GAME_LEVEL_END;
			// game.setScreen(new MainMenuScreen(game));
		}
	}

	public void draw() {

		GL20 gl = Gdx.gl;

		gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width,
				(int) viewport.height);
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
		// game.batcher.draw(Assets.soundOn, 320 - 64, 480 - 64, 64, 64);
		// Assets.font.draw(game.batcher, scoreString, 1, 480 - 5);
	}

	private void presentPaused() {
		game.batcher.draw(Assets.pauseMenu, 0, 0, 320, 480);
	}

	private void presentLevelEnd() {
		// String topText = "the princess is ...";
		// String bottomText = "in another castle!";
		// float topWidth = Assets.font.getBounds(topText).width;
		// float bottomWidth = Assets.font.getBounds(bottomText).width;
		// Assets.font.draw(game.batcher, topText, 160 - topWidth / 2, 480 -
		// 40);
		// Assets.font.draw(game.batcher, bottomText, 160 - bottomWidth / 2,
		// 40);
	}

	private void presentGameOver() {
		game.batcher.draw(Assets.gameOver, 0, 0, 320, 480);
		// game.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160,
		// 96);
		// float scoreWidth = Assets.font.getBounds(scoreString).width;
		// Assets.font.draw(game.batcher, scoreString, 160 - scoreWidth / 2, 480
		// - 20);
	}

	@Override
	public void render(float delta) {
		update(delta);
		if (myMode == GameMode.Server) {
			draw();
		} else if (myMode == GameMode.Client) {

			GL20 gl = Gdx.gl;

			// gl.glViewport((int) viewport.x, (int) viewport.y, (int)
			// viewport.width, (int) viewport.height);
			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void resize(int width, int height) {

		// calculate new viewport
		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if (aspectRatio > Settings.ASPECT_RATIO) {
			scale = (float) height / (float) Settings.TARGET_HEIGHT;
			crop.x = (width - Settings.TARGET_WIDTH * scale) / 2f;
		} else if (aspectRatio < Settings.ASPECT_RATIO) {
			scale = (float) width / (float) Settings.TARGET_WIDTH;
			crop.y = (height - Settings.TARGET_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) Settings.TARGET_WIDTH;
		}

		float w = (float) Settings.TARGET_WIDTH * scale;
		float h = (float) Settings.TARGET_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);
	}
}