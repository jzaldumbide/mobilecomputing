package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;

	public AssetImages images;
	
	public AssetSounds sounds;
	
	public AssetMusic music;

	// singleton: prevent instantiation from other classes
	private Assets() {
	}

	public class AssetImages {
		//public final Texture xxx;

		public AssetImages() {
			//xxx = new Texture(Gdx.files.internal("xxx.png"));
		}
	}

	public class AssetSounds {
		public final Sound step,click,correct,incorrect,shake;

		public AssetSounds(AssetManager am) {
			step = am.get("sound/step.ogg", Sound.class);
			click = am.get("sound/click.ogg", Sound.class);
			correct = am.get("sound/correct.ogg", Sound.class);
			incorrect = am.get("sound/incorrect.ogg", Sound.class);
			shake = am.get("sound/shake.ogg", Sound.class);
		}
	}

	public class AssetMusic {
		//public final Music yyy;

		public AssetMusic(AssetManager am) {
			//yyy = am.get("yyy.mp3", Music.class);
		}
	}

	
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// Set asset manager error handler
		assetManager.setErrorListener(this);

		/* Here load all Textures: */
		//assetManager.load("xxx.png", Texture.class);
		
		/* Here load all Sounds: */
		assetManager.load("sound/step.ogg", Sound.class);
		assetManager.load("sound/click.ogg", Sound.class);
		assetManager.load("sound/correct.ogg", Sound.class);
		assetManager.load("sound/incorrect.ogg", Sound.class);
		assetManager.load("sound/shake.ogg", Sound.class);
		
		/* Here load all Music: */
		//assetManager.load("yyy.mp3", Music.class);

		/* Start loading assets and wait until finished, this method is very important, 
		allows to load assets otherwise the "Asset not loaded" will ceash the app.*/
		assetManager.finishLoading();

		Gdx.app.debug(TAG,
				"# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		// Create game resource objects
		images = new AssetImages();
		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset + "'",
				(Exception) throwable);

	}

	@Override
	public void dispose() {
		System.out.println("Assets disposing...");
		assetManager.dispose();
	}

}
