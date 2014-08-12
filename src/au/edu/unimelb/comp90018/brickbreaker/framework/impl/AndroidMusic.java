package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import au.edu.unimelb.comp90018.brickbreaker.framework.Music;

/**
 * 
 * @author Oscar
 * 
 *         You'll find in the implemented methods some synchronisation blocks
 *         since we are using the isPrepared flag, which might get set on a
 *         separate thread because we are implementing the OnCompletionListener
 *         interface.
 */
public class AndroidMusic implements Music, OnCompletionListener {

	MediaPlayer mediaPlayer;
	boolean isPrepared = false;

	public AndroidMusic(AssetFileDescriptor descriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void play() {
		if (mediaPlayer.isPlaying())
			return;
		try {
			synchronized (this) {
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
		}
	}

	@Override
	public void pause() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	@Override
	public void setLooping(boolean looping) {
		mediaPlayer.setLooping(looping);
	}

	@Override
	public void setVolume(float volume) {
		// There are two parameters: left and right volumes.
		mediaPlayer.setVolume(volume, volume);
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !isPrepared;
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized (this) {
			isPrepared = false;
		}
	}

}
