package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import au.edu.unimelb.comp90018.brickbreaker.framework.Audio;
import au.edu.unimelb.comp90018.brickbreaker.framework.Music;
import au.edu.unimelb.comp90018.brickbreaker.framework.Sound;

public class AndroidAudio implements Audio {

	AssetManager assets;
	SoundPool soundPool;

	/**
	 * 
	 * @param activity
	 *            If you compare this constructor with {@code AndroidFileIO}
	 *            constructor, you'll notice the latter has
	 *            {@code android.content.Context} instead of
	 *            {@code android.app.Activity} as parameter since the volume
	 *            control is related with the current Activity. It is worth to
	 *            say that {@code android.app.Activity} is basically a
	 *            {@code android.content.Context}
	 * 
	 */
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public Music newMusic(String filename) {
		AssetFileDescriptor assetDescriptor;
		try {
			assetDescriptor = assets.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}

	@Override
	public Sound newSound(String filename) {
		// Recall that there is something called SoundPool which is that... a
		// pool. So you first need to load the sound file in this pool and get
		// an id which is sent to the AndroidSound class constructor.
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + filename + "'");
		}
	}

}
