### COMP90018 Mobile Computing Systems Programming

Mobile Computing Game: BrickBreaker

### Developers:

* Andres Chaves (Networking - Scoring - Levels XML )

* Oscar Correa (Framework Architecture - Game play & physics)

* Diego Montufar (Graphic Design - Special Effects - Actions)

* Juan Zaldumbide (Screen Transitions - Player data & configuration)

### Framework: 
Android (min vers. 8)

### Folder structure:

                
        └── brickbreaker
            ├── AndroidLauncher.java 			: Main android activity
            ├── BrickBreaker.java 				: Game Activity that starts the main thread
            ├── actors
            │   ├── Ball.java 					: Object ball
            │   ├── Bonus.java 					: Object Bonus, could be a coin, extra life, etc.
            │   ├── Brick.java 					: Object brick
            │   ├── BrickAdapter.java 			: Interface used for Bricks
            │   ├── BrickTypeI.java 			: Basic type of Brick
            │   ├── BrickTypeII.java 			: Double brick break
            │   ├── BrickTypeIII.java 			: Quadruple brick break
            │   ├── Button.java 				: Generic touch button
            │   ├── GameLevel.java 				: 
            │   └── Paddle.java 				: Object Paddle
            ├── framework
            │   ├── DynamicGameObject.java 		: Generic class for dynamic objects with accel
            │   ├── GameObject.java 			: Generic class for objects with position and velocity
            │   ├── Rectangle2.java 			: Implements object Bounds 
            │   ├── World.java 					: Implements 
            │   ├── WorldListener.java 			: Basic listeners while the game is running
            │   ├── WorldRenderer.java 			: Renders testures on the screen every 6fps
            │   ├── network
            │   │   └── LevelDownloader.java 	:
            │   └── util
            │       ├── Assets.java 			: This class handles all asstes like textures, sounds, animations and music.
            │       ├── Player.java 			: Creates a file that contains player name, level score and status of each level.
            │       ├── Settings.java 			: Static clas with some parameters for configuration
            │       └── TextureRegionSet.java 	: Used for asign textures to Objects
            └── screens
                ├── CreateUserScreen.java 		: It let us create the user with an input (actually deprecated) 
                ├── GameScreen.java 			: It loads the main game
                ├── HelpScreen.java 			: It show us the how-to-play guide
                ├── LevelScreen.java 			: It let us select the levels unlocked
                ├── MenuScreen.java 			: It show us the main menu option
                ├── MessageScreen.java 			: Message Screen to display errors
                ├── MultiplayerScreen.java 		: It lets select from server or client (currently disabled).
                ├── OptionScreen.java 			: This screen let us activate/unactivate sound, music and accelerometer.
                ├── ScoreScreen.java 			: It shows the top ten players, this top ten palyers are loaded from the remote server.
                ├── SelectScreen.java 			:  It let us choose multiplayer/singleplayer (actually deprecated) 
                └── SplashScreen.java 			: This is the first screen that appears, downdload level runs on background.

### Other files used:

        ├── backgrounds
        │   ├── background-basic.png 			:
        │   ├── background.png 					:
        │   ├── default_notification.png 		:
        │   ├── errorBackground.png 			:
        │   ├── gameover.png 					:
        │   ├── infoBackground.png 				:
        │   ├── pausemenu.png 					:
        │   ├── ready.png 						:
        │   ├── screens
        │   │   ├── default_background.png 		:
        │   │   ├── screen_menu.png 			:
        │   │   └── screen_splash.png 			:
        │   └── win.png 						:      
        ├── fonts
        │   └── font.ttf 						: The font used in texts of the game
        ├── helpscreens
        │   ├── help1.png 					:
        │   ├── help2.png 					:
        │   ├── help3.png 					:
        │   ├── help4.png 					:
        │   ├── help5.png 					:
        │   └── helpbackground.png 			:
        ├── music
        │   └── music.mp3 					: 
        ├── sound
        │   ├── click.wav 					:
        │   ├── coin.wav 					:
        │   ├── gameOverSound.ogg 			:
        │   ├── getLifeBonus.ogg 			:
        │   ├── lifeLost.ogg 				:
        │   ├── toggle.ogg 					:
        │   ├── touchBrick.ogg 				:
        │   ├── touchHardBrick.wav 			:
        │   ├── touchPaddle.wav 			:
        │   ├── touchWall.ogg 				:
        │   └── winnerSound.wav 			:
        └── textures
            └── items.png 					:


###Server files:
        .
        ├── brickbreaker.dmp 				:
        ├── brickbreaker_level1.xml 		:
        ├── brickbreaker_level2.xml 		:
        ├── brickbreaker_level3.xml 		:
        ├── brickbreaker_level4.xml 		:
        ├── brickbreaker_test.xml 			:
        └── highScore.php 					: