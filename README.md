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
            ├── AndroidLauncher.java 			:
            ├── BrickBreaker.java 				:
            ├── actors
            │   ├── Ball.java 					:
            │   ├── Bonus.java 					:
            │   ├── Brick.java 					:
            │   ├── BrickAdapter.java 			:
            │   ├── BrickTypeI.java 			:
            │   ├── BrickTypeII.java 			:
            │   ├── BrickTypeIII.java 			:
            │   ├── Button.java 				:
            │   ├── GameLevel.java 				:
            │   └── Paddle.java 				:
            ├── framework
            │   ├── DynamicGameObject.java 		:
            │   ├── GameObject.java 			:
            │   ├── Rectangle2.java 			:
            │   ├── World.java 					:
            │   ├── WorldListener.java 			:
            │   ├── WorldRenderer.java 			:
            │   ├── network
            │   │   └── LevelDownloader.java 	:
            │   └── util
            │       ├── Assets.java 			:
            │       ├── Player.java 			: It is bla bla bla
            │       ├── Settings.java 			:
            │       └── TextureRegionSet.java 	:
            └── screens
                ├── CreateUserScreen.java 		: 		
                ├── GameScreen.java 			:
                ├── HelpScreen.java 			:
                ├── LevelScreen.java 			:
                ├── MenuScreen.java 			:
                ├── MessageScreen.java 			:
                ├── MultiplayerScreen.java 		:
                ├── OptionScreen.java 			:
                ├── ScoreScreen.java 			:
                ├── SelectScreen.java 			:
                └── SplashScreen.java 			:

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
        │   └── font.ttf 						:
        ├── helpscreens
        │   ├── help1.png 						:
        │   ├── help2.png 						:
        │   ├── help3.png 						:
        │   ├── help4.png 						:
        │   ├── help5.png 						:
        │   └── helpbackground.png 				:
        ├── music
        │   └── music.mp3 						:
        ├── sound
        │   ├── click.wav 						:
        │   ├── coin.wav 						:
        │   ├── gameOverSound.ogg 				:
        │   ├── getLifeBonus.ogg 				:
        │   ├── lifeLost.ogg 					:
        │   ├── toggle.ogg 						:
        │   ├── touchBrick.ogg 					:
        │   ├── touchHardBrick.wav 				:
        │   ├── touchPaddle.wav 				:
        │   ├── touchWall.ogg 					:
        │   └── winnerSound.wav 				:
        └── textures
            └── items.png 						:


###Server files:
        .
        ├── brickbreaker.dmp 					:
        ├── brickbreaker_level1.xml 			:
        ├── brickbreaker_level2.xml 			:
        ├── brickbreaker_level3.xml 			:
        ├── brickbreaker_level4.xml 			:
        ├── brickbreaker_test.xml 			:
        └── highScore.php 					: