package au.edu.unimelb.comp90018.brickbreaker.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

	// BitmapFont font;

	/* BUTTONS */
	// bluetooth variables
	private BluetoothAdapter mBluetoothAdapter;
	private ServerThread myServer;
	private boolean isServer = true;
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB"); // UUID for
																	// Bluetooth
																	// Connections
	private final static int REQUEST_ENABLE_BT = 1;

	public MultiplayerScreen(BrickBreaker game) {

		this.game = game;

		screenWidth = 320;
		screenHeight = 480;
		btnsizeWidth = 300;
		btnsizeHeight = 32;
		btnseparation = 8;

		// font = new BitmapFont(Gdx.files.internal("fonts/test/Arial-12.fnt"),
		// Gdx.files.internal("fonts/test/fontgame.png"), false);

		// network
		// startBluetooth();

		// startServerNetwork();
		ipAddress = getmyipNetwork();
		// sendMessage();
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

			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0),
					game.viewport.x, game.viewport.y, game.viewport.width,
					game.viewport.height);

			if (serverBounds.contains(touchPoint.x, touchPoint.y)) {
				Gdx.app.log("starting server", "starting server");

				// Gdx.app.log("sever running?", "server running?");

				return;
			}

			if (joinBounds.contains(touchPoint.x, touchPoint.y)) {
				// Gdx.app.log("joining", "joining");
				// sendMessage();
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

		gl.glViewport((int) game.viewport.x, (int) game.viewport.y,
				(int) game.viewport.width, (int) game.viewport.height);

		gl.glClearColor(0, 0, 0, 1);
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

	// Networking
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

	public void sendMessage() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				String textToSend = new String();
				String coords = new String();
				coords = "x: " + Gdx.input.getX() + " " + "y: "
						+ Gdx.input.getY();
				textToSend = ipAddress + " dice hola y esta en " + coords
						+ ("\n");

				SocketHints socketHints = new SocketHints();
				// Socket will time our in 4 seconds
				socketHints.connectTimeout = 4000;
				// create the socket and connect to the server entered in the
				// text box (
				// x.x.x.x format ) on port 9021
				Socket socket = Gdx.net.newClientSocket(Protocol.TCP, ipServer,
						9021, socketHints);
				while (true) {
					try {

						coords = "x: " + Gdx.input.getX() + " " + "y: "
								+ Gdx.input.getY();
						textToSend = ipAddress + " dice hola y esta en "
								+ coords + ("\n");

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

	// Bluetooth

	public void startBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			// startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		// startActivity(discoverableIntent);

		// IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		// registerReceiver(mReceiver, filter);

		myServer = new ServerThread();
		myServer.start();

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				String deviceName = device.getName();
				if (deviceName.equals("Brickbreaker Game")) {
					isServer = false;
					// worldView.onScreen = false;
					ConnectThread myConnection = new ConnectThread(device);
					myConnection.start();
					// worldView.connected = true;
				}

				Log.d("Bluetooth Device:", deviceName);
			}
		}
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String name = device.getName();
				Gdx.app.log("Bluetooth Device:", name);
			}
		}
	};

	private class ServerThread extends Thread {
		private final BluetoothServerSocket myServSocket;

		public ServerThread() {
			BluetoothServerSocket tmp = null;

			try {
				tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
						"myServer", MY_UUID);
			} catch (IOException e) {
				Log.e("Bluetooth", "Server establishing failed");
			}

			myServSocket = tmp;
		}

		public void run() {
			Log.e("Bluetooth", "Begin waiting for connection");
			BluetoothSocket connectSocket = null;
			InputStream inStream = null;
			OutputStream outStream = null;
			String line = "";

			while (true) {
				try {
					connectSocket = myServSocket.accept();
					mBluetoothAdapter.cancelDiscovery();
				} catch (IOException e) {
					Log.e("Bluetooth", "Connection failed");
					break;
				}

				try {
					inStream = connectSocket.getInputStream();
					outStream = connectSocket.getOutputStream();
					// worldView.outputStream = outStream;
					// worldView.connected = true;
					BufferedReader br = new BufferedReader(
							new InputStreamReader(inStream));

					while ((line = br.readLine()) != null) {
						try {
							if (line.startsWith("ShowOnScreen")) {
								/*
								 * worldView.onScreen = true; List<String>
								 * coords = Arrays.asList(line .split(","));
								 * 
								 * float screenWidth = Float.parseFloat(coords
								 * .get(1)); float screenHeight =
								 * Float.parseFloat(coords .get(2)); float x =
								 * Float.parseFloat(coords.get(3)); float y =
								 * Float.parseFloat(coords.get(4)); float xSpeed
								 * = Float.parseFloat(coords.get(5)); float
								 * ySpeed = Float.parseFloat(coords.get(6));
								 * 
								 * worldView.ball.resetCoords(screenWidth,
								 * screenHeight, x, y, xSpeed, ySpeed);
								 */
								Gdx.app.log("Input: ", inStream.toString());
								Gdx.app.log("Output: ", outStream.toString());
							}

							Log.e("Bluetooth", "Received: " + line);
						} catch (Exception e3) {
							Log.e("Bluetooth", "Disconnected");
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}

			}
		}
	}

	private class ConnectThread extends Thread {
		private final BluetoothSocket mySocket;

		public ConnectThread(BluetoothDevice device) {
			BluetoothSocket tmp = null;

			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
				Log.e("Bluetooth", "Could not connect");
			}
			mySocket = tmp;
		}

		public void run() {
			InputStream inStream = null;
			OutputStream outStream = null;
			mBluetoothAdapter.cancelDiscovery();

			try {
				mySocket.connect();
			} catch (IOException e) {
				Log.e("Bluetooth", this.getName()
						+ ": Could not establish connection with device");
				try {
					mySocket.close();
				} catch (IOException e1) {
					Log.e("Bluetooth", this.getName()
							+ ": could not close socket", e1);
					this.destroy();
				}
			}

			String line = "";
			try {
				inStream = mySocket.getInputStream();
				outStream = mySocket.getOutputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						inStream));
				// worldView.outputStream = outStream;

				while ((line = br.readLine()) != null) {
					try {
						Log.e("Bluetooth", "Received: " + line);

						if (line.startsWith("ShowOnScreen")) {
							/*
							 * worldView.onScreen = true; List<String> coords =
							 * Arrays .asList(line.split(","));
							 * 
							 * float screenWidth =
							 * Float.parseFloat(coords.get(1)); float
							 * screenHeight = Float .parseFloat(coords.get(2));
							 * float x = Float.parseFloat(coords.get(3)); float
							 * y = Float.parseFloat(coords.get(4)); float xSpeed
							 * = Float.parseFloat(coords.get(5)); float ySpeed =
							 * Float.parseFloat(coords.get(6));
							 * 
							 * worldView.ball.resetCoords(screenWidth,
							 * screenHeight, x, y, xSpeed, ySpeed);
							 */
							Gdx.app.log("Input: ", inStream.toString());
							Gdx.app.log("Output: ", outStream.toString());
						}
					} catch (Exception e3) {
						Log.e("Bluetooth", "Disconnected");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void cancel() {
			try {
				mySocket.close();
			} catch (IOException e) {
				Log.e("Bluetooth", this.getName() + ": Could not close socket");
			}
		}
	}

}
