### COMP90018 Mobile Computing Systems Programming

Mobile Computing Game: BrickBreaker

### Description:
BrickBreaker is a breakout Android game developed with libgdx (openGL 2.0). 
Once installed and when the player starts the game you'll have to enter your name, the game detects if is the first time you are playing, 
so you only have to enter your name once. That's the way the game can know who you are in order to submit your scores and update your rank. 
The game will show you a splash screen while the levels are downloaded from a server in a background process (from a cloud or local server).
If something goes wrong, the game will let you know through error and info messages, however you'll be able to play with default configurations and
levels. Else, you are ready to play the downloaded levels!
You can go through the game options and menus where you can enable/disable sound, music or decide if you want to control your paddle with accelerometer 
or just touch. If you need help about some option of the game you can enter to the help menu. 
If you select Scores you will see a top 10 list of current ranked players, this list gets updated always. Check what is the player you want to beat and
start the game by touching the Play! menu. Then the game will let you select the level you want to play, if is the first time you only will have the level
1 enabled, the rest of them will open when you win each level. Then you can start playing the game. Each level has different configuration and difficulty which 
are defined in the XML files, if you want to make your own, just edit some of them and have fun! While you are playing, the game will give you free bonuses, some of
them give you extra points and other make you lose. Scores and Rankings are updated while you're playing you can check yours at the top of the screen.

### Developers:
* Andres Chaves (Networking - Scoring - Levels XML )
* Oscar Correa (Framework Architecture - Game play & physics)
* Diego Montufar (Graphic Design - Special Effects - Actions/Animations)
* Juan Zaldumbide (Networking - Screen Transitions - Player data & configuration)

### Group ID:  2

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
            │   ├── GameLevel.java 				: Represents a Level of the Game in terms of Paddle, Ball and Bricks
            │   └── Paddle.java 				: Object Paddle
            ├── framework
            │   ├── DynamicGameObject.java 		: Generic class for dynamic objects with accel and velocity
            │   ├── GameObject.java 			: Every GameObject has associated position and bounds. Bounds helps to keep track of collisions.
            │   ├── Rectangle2.java 			: Extension of Rectangle class in order to control which side(s) of the object were hit
            │   ├── World.java 					: Represents the world where the game is performed. It updates the states of every actor within it for each delta time.
            │   ├── WorldListener.java 			: Basic listeners while the game is running
            │   ├── WorldRenderer.java 			: We were trying to apply MVC model, thus this class is the VIEW part whereas the World is a kind of CONTROLLER.
            │   ├── network
            │   │   └── LevelDownloader.java 	: Manage access to the network, level and high score downlading and uploading and ile system reading. Manages also XML parsing.
            │   └── util
            │       ├── Assets.java 			: This class handles all assests like textures, sounds, animations and music.
            │       ├── Player.java 			: Creates and manages a file (brickbreaker.data) that contains player name, level score and status of each level.
            │       ├── Settings.java 			: Static class with some configuration parameters like sound enable, accelerometer, etc.
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
                ├── ScoreScreen.java 			: It shows the top ten players, this top ten players are loaded from the remote server.
                ├── SelectScreen.java 			:  It let us choose multiplayer/singleplayer (actually deprecated) 
                └── SplashScreen.java 			: This is the first screen that appears, downdload level runs on background.

### Other files used:

        ├── backgrounds
        │   ├── background-basic.png 			: Basic/Default background for the screens
        │   ├── background.png 					: World background
        │   ├── default_notification.png 		: Default background to show notifications
        │   ├── errorBackground.png 			: Used for error notifications
        │   ├── gameover.png 					: Game over message
        │   ├── infoBackground.png 				: Info message
        │   ├── pausemenu.png 					: Pause message
        │   ├── ready.png 						: Ready message
        │   ├── screens
        │   │   ├── default_background.png 		: Default background for screens
        │   │   ├── screen_menu.png 			: Default background for menus
        │   │   └── screen_splash.png 			: Splash Screen
        │   └── win.png 						: Win message
        ├── fonts
        │   └── font.ttf 						: The font used in texts of the game
        ├── helpscreens
        │   ├── help1.png 						: Help Screen
        │   ├── help2.png 						: Help Screen
        │   ├── help3.png 						: Help Screen
        │   ├── help4.png 						: Help Screen
        │   ├── help5.png 						: Help Screen
        │   └── helpbackground.png 				: Help Screen background
        ├── music
        │   └── music.mp3 						: Background music by Joe Jeremiah (Tribute to Daft Punk - A-bit of Daft Punk) https://www.youtube.com/channel/UCVUADDzjYdnGJTuEdUcRt9g
        ├── sound
        │   ├── click.wav 						: click sound (for buttons)
        │   ├── coin.wav 						: Bonus coin
        │   ├── gameOverSound.ogg 				: Game Over sound
        │   ├── getLifeBonus.ogg 				: Get extra life Bonus
        │   ├── lifeLost.ogg 					: Life Lost sound
        │   ├── toggle.ogg 						: Toggle options sound
        │   ├── touchBrick.ogg 					: Touch brick sound
        │   ├── touchHardBrick.wav 				: Touck double brick sound
        │   ├── touchPaddle.wav 				: Touch paddle sound
        │   ├── touchWall.ogg 					: Touch wall sound
        │   └── winnerSound.wav 				: Win Game sound
        └── textures
            └── items.png 						: This is the main Texture with all the main game objects. 


###Server files:
        .
        ├── brickbreaker.dmp 				: SQL Dump of the database
        ├── brickbreaker_level1.xml 		: Level 1 of the game
        ├── brickbreaker_level2.xml 		: Level 2 of the game
        ├── brickbreaker_level3.xml 		: Level 3 of the game
        ├── brickbreaker_level4.xml 		: Level 4 of the game
        ├── brickbreaker_test.xml 			: Test Level
        └── highScore.php 					: A php page to insert and show high scores