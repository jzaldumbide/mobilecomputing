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

ÃÄÄ AndroidLauncher.java
ÃÄÄ BrickBreaker.java
ÃÄÄ actors
³ÿÿ ÃÄÄ Ball.java
³ÿÿ ÃÄÄ Bonus.java
³ÿÿ ÃÄÄ Brick.java
³ÿÿ ÃÄÄ BrickAdapter.java
³ÿÿ ÃÄÄ BrickTypeI.java
³ÿÿ ÃÄÄ BrickTypeII.java
³ÿÿ ÃÄÄ Button.java
³ÿÿ ÃÄÄ GameLevel.java
³ÿÿ ÀÄÄ Paddle.java
ÃÄÄ framework
³ÿÿ ÃÄÄ DynamicGameObject.java
³ÿÿ ÃÄÄ GameObject.java
³ÿÿ ÃÄÄ Rectangle2.java
³ÿÿ ÃÄÄ World.java
³ÿÿ ÃÄÄ WorldListener.java
³ÿÿ ÃÄÄ WorldRenderer.java
³ÿÿ ÃÄÄ network
³ÿÿ ³ÿÿ ÀÄÄ LevelDownloader.java
³ÿÿ ÀÄÄ util
³ÿÿ     ÃÄÄ Assets.java
³ÿÿ     ÃÄÄ Player.java
³ÿÿ     ÃÄÄ Settings.java
³ÿÿ     ÀÄÄ TextureRegionSet.java
ÀÄÄ screens
ÃÄÄ CreateUserScreen.java
ÃÄÄ GameScreen.java
ÃÄÄ LevelScreen.java
ÃÄÄ MenuScreen.java
ÃÄÄ MessageScreen.java
ÃÄÄ MultiplayerScreen.java
ÃÄÄ OptionScreen.java
ÃÄÄ ScoreScreen.java
ÃÄÄ SelectScreen.java
ÀÄÄ SplashScreen.java