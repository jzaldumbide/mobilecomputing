COMP90018 Mobile Computing Systems Programming
===============
Mobile Computing Game: BrickBreaker

Developers:
1 Andres Chaves (Networking - Scoring - Levels XML )

2 Oscar Correa (Framework Architecture - Game play & physics)

3 Diego Montufar (Graphic Design - Special Effects - Actions)

4 Juan Zaldumbide (Screen Transitions - Player data & configuration)

Framework: Android (min vers. 8)

Folders

src    : Source Files
 
server : Server side files
server/brickbreaker_level1.xml : GameLevel 1
server/brickbreaker_level2.xml : GameLevel 2
server/brickbreaker_level3.xml : GameLevel 3
server/brickbreaker.dmp : Database dump
server/highScore.php : High Score PHP Managing


└── brickbreaker
    ├── AndroidLauncher.java
    ├── BrickBreaker.java
    ├── actors
    │   ├── Ball.java
    │   ├── Bonus.java
    │   ├── Brick.java
    │   ├── BrickAdapter.java
    │   ├── BrickTypeI.java
    │   ├── BrickTypeII.java
    │   ├── BrickTypeIII.java
    │   ├── Button.java
    │   ├── GameLevel.java
    │   └── Paddle.java
    ├── framework
    │   ├── DynamicGameObject.java
    │   ├── GameObject.java
    │   ├── Rectangle2.java
    │   ├── World.java
    │   ├── WorldListener.java
    │   ├── WorldRenderer.java
    │   ├── network
    │   │   └── LevelDownloader.java
    │   └── util
    │       ├── Assets.java
    │       ├── Player.java
    │       ├── Settings.java
    │       └── TextureRegionSet.java
    └── screens
        ├── CreateUserScreen.java
        ├── GameScreen.java
        ├── HelpScreen.java
        ├── LevelScreen.java
        ├── MenuScreen.java
        ├── MessageScreen.java
        ├── MultiplayerScreen.java
        ├── OptionScreen.java
        ├── ScoreScreen.java
        ├── SelectScreen.java
        └── SplashScreen.java
