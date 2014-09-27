package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Assets {

	/* Textures - there must be less possible */
	public static TextureRegion gameBackground;
	public static Texture defaultBackground;
	public static Texture background;
	public static Texture items; //Main Texture with game sprites
	public static Texture itemsButtons; //temporary Texture for buttons only
	public static Texture itemsLevelsButtons; //temporary Texture for buttons only
	public static Texture backgroundHelp;// jp
	public static Texture backgroundMenu;// jp
	public static Texture backgroundOptions;// jp
	public static Texture backgroundScores;// jp
	public static Texture backgroundSplash;// jp

	/* Transparent Screens*/
	public static TextureRegion defaultNotification;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion pauseMenu;
	
	/*Here declare game Textures*/
	public static TextureRegion blueBall;
	public static TextureRegion redBall;
	public static TextureRegion paddle;
	public static TextureRegion smallGreenBrick;
	public static TextureRegion mediumGreenBrick;
	public static TextureRegion smallOrangeBrick;
	public static TextureRegion mediumOrangeBrick;
	public static TextureRegion smallBlueBrick;
	public static TextureRegion mediumBlueBrick;
	public static TextureRegion smallYellowBrick;
	public static TextureRegion smallPurpleBrick;
	public static TextureRegion lives;
	public static TextureRegion buttonMenu;
	
	/*Here declare buttons*/
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion musicOn;
	public static TextureRegion musicOff;
	public static TextureRegion accelOn;
	public static TextureRegion quit;
	public static TextureRegion back;
	public static TextureRegion pause;
	public static TextureRegion settings;
	public static TextureRegion help;
	public static TextureRegion multiplayer;
	public static TextureRegion connect;
	
	/*Here declare buttons for locked levels*/
	public static TextureRegion levelLocked_1;
	public static TextureRegion levelLocked_2;
	public static TextureRegion levelLocked_3;
	public static TextureRegion levelLocked_4;
	public static TextureRegion levelLocked_5;
	public static TextureRegion levelLocked_6;
	public static TextureRegion levelLocked_7;
	public static TextureRegion levelLocked_8;
	public static TextureRegion levelLocked_9;
	
	public static TextureRegion levelUnlocked_1;

	/*Here declare Screens*/
	public static TextureRegion defaultScreen;
	public static TextureRegion helpScreen;
	public static TextureRegion menuScreen;
	public static TextureRegion optionScreen;
	public static TextureRegion scoresScreen;
	public static TextureRegion splashScreen;

	/* Animations */
	// public static Animation coinAnim; //example

	/* TextureRegionSets -> Something similar to Animations */
	public static TextureRegionSet brickTypeI;
	public static TextureRegionSet brickTypeII;
	
	/* BitmapFonts */
	public static BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"�`'<>";

	/* Sounds & Music */
	public static Music music;

	public static Sound stepSound;
	public static Sound clickSound;
	public static Sound correctSound;
	public static Sound incorrectSound;
	public static Sound shakeSound;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	@SuppressWarnings("static-access")
	public static Texture loadTransparentTexture(int width, int height,
			String file) {

		Pixmap mask = new Pixmap(width, height, Pixmap.Format.Alpha);
		mask.fillRectangle(0, 0, width, height);

		Pixmap fg = new Pixmap(Gdx.files.internal(file));
		fg.drawPixmap(mask, fg.getWidth(), fg.getHeight());
		mask.setBlending(Pixmap.Blending.SourceOver);

		Texture texture = new Texture(fg);

		return texture;
	}

	public static void load() {
		
		/*Here declare screen backgrounds*/
		defaultBackground = loadTexture("backgrounds/screens/default_background.png");
		defaultScreen = new TextureRegion(defaultBackground, 0, 0, 800, 1280);
		
		backgroundHelp = loadTexture("backgrounds/screens/screen_help.png");
		helpScreen = new TextureRegion(backgroundHelp, 0, 0, 320, 480);

		backgroundMenu = loadTexture("backgrounds/screens/screen_menu.png");
		menuScreen = new TextureRegion(backgroundMenu, 0, 0, 800, 1280);

		backgroundOptions = loadTexture("backgrounds/screens/screen_options.png");
		optionScreen = new TextureRegion(backgroundOptions, 0, 0, 320, 480);

		backgroundScores = loadTexture("backgrounds/screens/screen_scores.png");
		scoresScreen = new TextureRegion(backgroundScores, 0, 0, 320, 480);

		backgroundSplash = loadTexture("backgrounds/screens/screen_splash.png");
		splashScreen = new TextureRegion(backgroundSplash, 0, 0, 800, 1280);

		background = loadTexture("backgrounds/background.png");
		gameBackground = new TextureRegion(background, 0, 0, 800, 1280);
		
		//Button template that goes behind text (used in menus):
		Texture buttonMenuR = loadTexture("buttons/menuButton.png");
		buttonMenu = new TextureRegion(buttonMenuR, 0, 0, 256, 64); //The button shown in the main menu
			
		/*Here you should put all textures that comes from items.png*/
		items = loadTexture("textures/items.png");
		blueBall = new TextureRegion(items, 0, 0, 64, 64);
		redBall = new TextureRegion(items, 0, 64, 64, 64);
		paddle = new TextureRegion(items, 96, 0, 128, 32);
		lives = new TextureRegion(items, 224, 0, 64, 64);
		smallGreenBrick = new TextureRegion(items, 64, 0, 32, 16);
		mediumGreenBrick = new TextureRegion(items, 80, 32, 32, 16);
		smallOrangeBrick = new TextureRegion(items, 64, 16, 32, 16);
		mediumOrangeBrick = new TextureRegion(items, 80, 48, 32, 16);
		smallBlueBrick = new TextureRegion(items, 64, 32, 32, 16);
		mediumBlueBrick  = new TextureRegion(items, 80, 64, 32, 16);
		smallYellowBrick = new TextureRegion(items, 64, 48, 32, 16);
		smallPurpleBrick = new TextureRegion(items, 64, 64, 32, 16);
		
		/*Here you should load textures for locked levels*/
		itemsLevelsButtons = loadTexture("textures/itemsLevelsButtons.png");
		levelLocked_1 = new TextureRegion(itemsLevelsButtons, 0, 0, 128, 128);
		levelLocked_2 = new TextureRegion(itemsLevelsButtons, 128, 0, 128, 128);
		levelLocked_3 = new TextureRegion(itemsLevelsButtons, 256, 0, 128, 128);
		levelLocked_4 = new TextureRegion(itemsLevelsButtons, 0, 128, 128, 128);
		levelLocked_5 = new TextureRegion(itemsLevelsButtons, 128, 128, 128, 128);
		levelLocked_6 = new TextureRegion(itemsLevelsButtons, 256, 128, 128, 128);
		levelLocked_7 = new TextureRegion(itemsLevelsButtons, 0, 256, 128, 128);
		levelLocked_8 = new TextureRegion(itemsLevelsButtons, 128, 256, 128, 128);
		levelLocked_9 = new TextureRegion(itemsLevelsButtons, 256, 256, 128, 128);
		
		levelUnlocked_1 = new TextureRegion(itemsLevelsButtons, 0, 384, 128, 128);
		
		/*Here you should put all textures that comes from itemsButtons.png*/
		itemsButtons = loadTexture("textures/itemsButtons.png");
		musicOn = new TextureRegion(itemsButtons, 0, 0, 128, 128);
		musicOff = new TextureRegion(itemsButtons, 128, 0, 128, 128);
		accelOn = new TextureRegion(itemsButtons, 256, 0, 128, 128);
		soundOn = new TextureRegion(itemsButtons, 384, 0, 128, 128);
		soundOff = new TextureRegion(itemsButtons, 512, 0, 128, 128);
		quit = new TextureRegion(itemsButtons, 640, 0, 128, 128);
		settings = new TextureRegion(itemsButtons, 0, 128, 128, 128);
		pause = new TextureRegion(itemsButtons, 128, 128, 128, 128);
		back = new TextureRegion(itemsButtons, 256, 128, 128, 128);
		help = new TextureRegion(itemsButtons, 384, 128, 128, 128);
		multiplayer = new TextureRegion(itemsButtons, 512, 128, 128, 128);
		connect = new TextureRegion(itemsButtons, 640, 128, 128, 128);
		
		
		/*Here load Transparent Textures*/
		Texture defaultNotificationR = loadTransparentTexture(320, 480, "backgrounds/default_notification.png");
		defaultNotification = new TextureRegion(defaultNotificationR, 0, 0, 320, 480);

		Texture readyR = loadTransparentTexture(320, 480, "backgrounds/ready.png");
		ready = new TextureRegion(readyR, 0, 0, 320, 480);

		Texture gameOverR = loadTransparentTexture(320, 480, "backgrounds/gameover.png");
		gameOver = new TextureRegion(gameOverR, 0, 0, 320, 480);

		Texture pauseMenuR = loadTransparentTexture(320, 480, "backgrounds/pausemenu.png");
		pauseMenu = new TextureRegion(pauseMenuR, 0, 0, 320, 480);		
		
		
		/* TextureRegionSets */
		brickTypeI = new TextureRegionSet(smallOrangeBrick);
		brickTypeII = new TextureRegionSet(smallOrangeBrick, smallYellowBrick);

		// Animation example
		// brakingPlatform = new Animation(0.2f, new TextureRegion(items, 64,
		// 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
		// new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items,
		// 64, 208, 64, 16));

		/* BitmapFont example */

		//font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), Gdx.files.internal("fonts/font.png"), false);
		
		 FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
		 Gdx.files.internal("fonts/junegull.ttf"));
		 FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		 parameter.size = 40;
		 font = generator.generateFont(parameter); // font size 12 pixels
		 font.setColor(new Color(Color.PURPLE));
		 generator.dispose();

		music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);

		if (Settings.soundEnabled)
			music.play();

		stepSound = Gdx.audio.newSound(Gdx.files.internal("sound/step.ogg"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/click.ogg"));
		correctSound = Gdx.audio.newSound(Gdx.files
				.internal("sound/correct.ogg"));
		incorrectSound = Gdx.audio.newSound(Gdx.files
				.internal("sound/incorrect.ogg"));
		shakeSound = Gdx.audio.newSound(Gdx.files.internal("sound/shake.ogg"));
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}
}
