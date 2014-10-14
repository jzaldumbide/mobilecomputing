package au.edu.unimelb.comp90018.brickbreaker.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

@SuppressWarnings("unused")
public class MultiplayerScreen extends ScreenAdapter {

	BrickBreaker game;
	OrthographicCamera guiCam;

	private Button joinButton, serverButton, btnBack;
	Vector3 touchPoint;
	
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

		touchPoint = new Vector3();

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2, 0);

		serverButton = new Button(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2, ButtonSize.XLARGE_RECTANGLE);
		
		joinButton = new Button(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2 - 45, ButtonSize.XLARGE_RECTANGLE);

		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);

		// network
		 startBluetooth();

		// startServerNetwork();
		ipAddress = getmyipNetwork();
		// sendMessage();
		//
	}

	public void update() {

		if (Gdx.input.justTouched()) {

			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0),
					game.viewport.x, game.viewport.y, game.viewport.width,
					game.viewport.height);

			if (serverButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Gdx.app.log("starting server", "starting server");
				Assets.playSound(Assets.clickSound);

				// Gdx.app.log("sever running?", "server running?");

				return;
			}

			if (joinButton.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				// Gdx.app.log("joining", "joining");
				// sendMessage();
				return;
			}

			if (btnBack.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MenuScreen(game));
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

		Assets.font.setScale(0.7f, 0.7f);
		Assets.font.setColor(new Color(Color.WHITE));

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.menuScreen, 0, 0, Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);

		game.batcher.draw(
				Assets.buttonMenu,
				serverButton.position.x
						- ButtonSize.XLARGE_RECTANGLE.getButtonWidth() / 2,
						serverButton.position.y
						- ButtonSize.XLARGE_RECTANGLE.getButtonHeight() / 2,
				ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),
				ButtonSize.XLARGE_RECTANGLE.getButtonHeight());
		
		game.batcher.draw(
				Assets.buttonMenu,
				joinButton.position.x
						- ButtonSize.XLARGE_RECTANGLE.getButtonWidth() / 2,
				joinButton.position.y
						- ButtonSize.XLARGE_RECTANGLE.getButtonHeight() / 2,
				ButtonSize.XLARGE_RECTANGLE.getButtonWidth(),
				ButtonSize.XLARGE_RECTANGLE.getButtonHeight());

		game.batcher.draw(Assets.back, 
				btnBack.position.x - ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnBack.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight() / 2,
				ButtonSize.MEDIUM_SQUARE.getButtonWidth(), 
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());		

		float posX = Settings.TARGET_WIDTH / 2;
		float posY = Settings.TARGET_HEIGHT / 2;

		Assets.font.draw(game.batcher, "Server", posX - 45, posY + 10);
		Assets.font.draw(game.batcher, "Join", posX - 32, posY - 37);
		Assets.font.drawMultiLine(game.batcher, ipAddress, 70, 120);

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
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	//	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
		Intent discoverableIntent = new
				Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			//	startActivity(discoverableIntent);
				
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		//registerReceiver(mReceiver, filter); 
		
        myServer = new ServerThread();
        myServer.start();
		
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
		    for (BluetoothDevice device : pairedDevices) {
		    	String deviceName = device.getName();
		    	if(deviceName.equals("Anthony Quattrone (GT-")) {
		    		isServer = false;
		    //		worldView.onScreen = false;
		    		ConnectThread myConnection = new ConnectThread(device);
		            myConnection.start();
		      //      worldView.connected = true;
		    	}
		    	
		    	Log.d("Bluetooth Device:", deviceName);
		    }
		}
	}
	
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            String name = device.getName();
	            Log.d("Bluetooth Device:", name);
	        }
	    }
	};	
	
	
	
	private class ServerThread extends Thread {
        private final BluetoothServerSocket myServSocket;

        public ServerThread() {
            BluetoothServerSocket tmp = null;

            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("myServer", MY_UUID);
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
                    //inStream.toString();
                   // worldView.outputStream = outStream;
                    Gdx.app.log("inStream: ", inStream.toString());
                    Gdx.app.log("outStream: ", outStream.toString());
                   // worldView.connected = true;
                    BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                    
                    while ((line = br.readLine()) != null) {
                        try {
                            if(line.startsWith("ShowOnScreen")) {
                            	//worldView.onScreen = true;
                            	List<String> coords = Arrays.asList(line.split(","));
                            	
                            	float screenWidth = Float.parseFloat(coords.get(1));
                            	float screenHeight = Float.parseFloat(coords.get(2));
                            	float x = Float.parseFloat(coords.get(3));
                            	float y = Float.parseFloat(coords.get(4));
                            	float xSpeed = Float.parseFloat(coords.get(5));
                            	float ySpeed = Float.parseFloat(coords.get(6));
                            	
                            	//worldView.ball.resetCoords(screenWidth, screenHeight, x, y, xSpeed, ySpeed);
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

        @SuppressWarnings("deprecation")
		public void run() {
            InputStream inStream = null;
            OutputStream outStream = null;
            mBluetoothAdapter.cancelDiscovery();

            try {
                mySocket.connect();
            } catch (IOException e) {
                Log.e("Bluetooth", this.getName() + ": Could not establish connection with device");
                try {
                    mySocket.close();
                } catch (IOException e1) {
                    Log.e("Bluetooth", this.getName() + ": could not close socket", e1);
                    this.destroy();
                }
            }

            String line = "";
            try {
                inStream = mySocket.getInputStream();
                outStream = mySocket.getOutputStream();
                
                BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
              //  worldView.outputStream = outStream;
                
                while ((line = br.readLine()) != null) {
                    try {
                        Log.e("Bluetooth", "Received: " + line);
                        
                        if(line.startsWith("ShowOnScreen")) {
                     //   	worldView.onScreen = true;
                        	List<String> coords = Arrays.asList(line.split(","));
                        	
                        	float screenWidth = Float.parseFloat(coords.get(1));
                        	float screenHeight = Float.parseFloat(coords.get(2));
                        	float x = Float.parseFloat(coords.get(3));
                        	float y = Float.parseFloat(coords.get(4));
                        	float xSpeed = Float.parseFloat(coords.get(5));
                        	float ySpeed = Float.parseFloat(coords.get(6));
                        	
                        	//worldView.ball.resetCoords(screenWidth, screenHeight, x, y, xSpeed, ySpeed);
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
	
	

}}
