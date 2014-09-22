package au.edu.unimelb.comp90018.brickbreaker.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class MultiplayerScreen extends ScreenAdapter {
	BrickBreaker game;
	OrthographicCamera guiCam;

	Rectangle serverBounds, joinBounds, backBounds;

	Vector3 touchPoint;

	public static Texture btnserver, btnjoin, btnback;
	public int screenWidth, screenHeight, btnsizeWidth, btnsizeHeight,
			btnseparation;
	String ipAddress = null;
	String ipServer = "192.168.56.101";

	BitmapFont font;

	/* BUTTONS */

	public MultiplayerScreen(BrickBreaker game) {
		this.game = game;
		screenWidth = 320;
		screenHeight = 480;
		btnsizeWidth = 300;
		btnsizeHeight = 32;
		btnseparation = 8;

		font = new BitmapFont(Gdx.files.internal("fonts/test/Arial-12.fnt"),
				Gdx.files.internal("fonts/test/fontgame.png"), false);

		// network
		// NetworkServer ns = new NetworkServer();
		// Thread t = new Thread(ns);
		// t.start();
		startServerNetwork();
		getmyipNetwork();
		//

		guiCam = new OrthographicCamera(screenWidth, screenHeight);
		guiCam.position.set(screenWidth / 2, screenHeight / 2, 0);
		serverBounds = new Rectangle(10, 264, 300, 30);
		joinBounds = new Rectangle(10, 220, 300, 30);
		// gyrosBounds = new Rectangle(10, 176, 300, 30);
		backBounds = new Rectangle(10, 10, 32, 32);

		touchPoint = new Vector3();

		btnserver = new Texture("buttons/btn_server.png");
		btnjoin = new Texture("buttons/btn_join.png");
		btnback = new Texture("buttons/back.png");

	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (serverBounds.contains(touchPoint.x, touchPoint.y)) {
				Gdx.app.log("starting server", "starting server");

				// Gdx.app.log("sever running?", "server running?");

				return;
			}

			if (joinBounds.contains(touchPoint.x, touchPoint.y)) {
				// Gdx.app.log("joining", "joining");
				sendMessage();
				return;
			}

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {

				game.setScreen(new SelectScreen(game));
				return;
			}

		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		// game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.menuScreen, 0, 0, 320, 480);
		game.batcher.draw(btnserver, serverBounds.x, serverBounds.y,
				serverBounds.width, serverBounds.height);
		game.batcher.draw(btnjoin, joinBounds.x, joinBounds.y,
				joinBounds.width, joinBounds.height);

		game.batcher.draw(btnback, backBounds.x, backBounds.y,
				backBounds.width, backBounds.height);
		Assets.font.drawMultiLine(game.batcher, ipAddress, 10, 200);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		// sendMessage();
		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	@Override
	public void pause() {
		// Settings.save();
	}

	public String getmyipNetwork() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						Gdx.app.log("ip: ", inetAddress.getHostAddress()
								.toString());
						ipAddress = inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			Gdx.app.log("Exception caught =", e.getMessage());
		}
		return ipAddress;

	}

	// The server network
	public void startServerNetwork() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				ServerSocketHints serverSocketHint = new ServerSocketHints();
				// 0 means no timeout. Probably not the greatest idea in
				// production!
				serverSocketHint.acceptTimeout = 0;

				// Create the socket server using TCP protocol and listening on
				// 9021
				// Only one app can listen to a port at a time, keep in mind
				// many ports are reserved
				// especially in the lower numbers ( like 21, 80, etc )

				ServerSocket serverSocket = Gdx.net.newServerSocket(
						Protocol.TCP, 9021, serverSocketHint);

				// Loop forever
				while (true) {
					// Create a socket
					Socket socket = serverSocket.accept(null);

					// Read data from the socket into a BufferedReader
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));

					try {
						// Read to the next newline (\n) and display that text
						// on labelMessage
						// labelMessage.setText(buffer.readLine());
						Gdx.app.log("Recibido: ", buffer.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// The client network
	public void clientNetwork() {
	}

	public void sendMessage() {

		String textToSend = new String();
		String coords = new String();
		coords = "x: " + Gdx.input.getX() + " " + "y: " + Gdx.input.getY();
		textToSend = ipAddress + " dice hola y esta en " + coords + ("\n");

		SocketHints socketHints = new SocketHints();
		// Socket will time our in 4 seconds
		socketHints.connectTimeout = 4000;
		// create the socket and connect to the server entered in the text box (
		// x.x.x.x format ) on port 9021
		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ipServer, 9021,
				socketHints);
		try {
			// write our entered message to the stream
			socket.getOutputStream().write(textToSend.getBytes());
			// Gdx.app.log(ipAddress + " dice: ", textToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String updatemessage() {
		return "testing";
	}

	// The server Bluetooth
	public void startServerBluetooth() {
	}

	// The client Bluetooth
	public void startClientBluetooth() {
	}
}
