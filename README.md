COMP90018 Mobile Computing Systems Programming

Mobile Computing Game: BrickBreaker

Developers:

* Andres Chaves (Networking - Scoring - Levels XML )

* Oscar Correa (Framework Architecture - Game play & physics)

* Diego Montufar (Graphic Design - Special Effects - Actions)

* Juan Zaldumbide (Screen Transitions - Player data & configuration)

### Framework: 
Android (min vers. 8)

### Folder structure:
        
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

