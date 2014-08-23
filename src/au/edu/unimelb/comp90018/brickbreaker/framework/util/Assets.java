package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets  {

	public static Texture background; 
	public static TextureRegion backgroundRegion;

	
	/*Textures*/
	public static Texture items;
	
	/*Texture regions*/
	public static TextureRegion blue_ball;
	public static TextureRegion grey_ball;
	public static TextureRegion orange_ball;
	public static TextureRegion red_ball;
	public static TextureRegion yellow_ball;
	public static TextureRegion paddle;
	public static TextureRegion brick;

	/*Animations*/
	//public static Animation coinAnim; //example
	
	/*BitmapFonts*/
	//public static BitmapFont font; //example

	/*Sounds & Music*/
	public static Music music;
	public static Sound stepSound;
	public static Sound clickSound;
	public static Sound correctSound;
	public static Sound incorrectSound;
	public static Sound shakeSound;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("backgrounds/background.png"); 
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480); 

		items = loadTexture("textures/items.png");
		red_ball = new TextureRegion(items, 0, 0, 32, 32);
		brick = new TextureRegion(items, 32, 0, 32, 32);
		paddle = new TextureRegion(items, 64, 0, 64, 16);

		//Animation example
		//brakingPlatform = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
		//new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));

		/*BitmapFont example*/
		//font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), Gdx.files.internal("fonts/font.png"), false);

		music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		
		if (Settings.soundEnabled) music.play();
		
		stepSound = Gdx.audio.newSound(Gdx.files.internal("sound/step.ogg"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/click.ogg"));
		correctSound = Gdx.audio.newSound(Gdx.files.internal("sound/correct.ogg"));
		incorrectSound = Gdx.audio.newSound(Gdx.files.internal("sound/incorrect.ogg"));
		shakeSound = Gdx.audio.newSound(Gdx.files.internal("sound/shake.ogg"));
	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}
}
