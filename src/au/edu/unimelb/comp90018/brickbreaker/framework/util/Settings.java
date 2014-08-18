package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	private static final String PREFS_NAME = "blockdestroyer_settings";
	// sound
    private static final String PREF_MUSIC_VOLUME = "music.volume";
//    private static final String PREF_SOUND_VOLUME = "sound.volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
//    private static final String PREF_SOUND_ENABLED = "sound.enabled"; 
    
    // video
    private static final String PREF_FULLSCREEN = "video.fullscreen";
    private static final String PREF_VIEW = "video.view";
    
    private static final String PREF_DEBUG = "debug";

    private static Preferences getPrefs()
    {
        return Gdx.app.getPreferences( PREFS_NAME );
    }
 
//    public static boolean isSoundEffectsEnabled()
//    {
//        return getPrefs().getBoolean( PREF_SOUND_ENABLED, true );
//    }
// 
//    public static void setSoundEffectsEnabled(
//        boolean soundEffectsEnabled )
//    {
//        getPrefs().putBoolean( PREF_SOUND_ENABLED, soundEffectsEnabled );
//        getPrefs().flush();
//    }
 
    public static boolean isMusicEnabled()
    {
        return getPrefs().getBoolean( PREF_MUSIC_ENABLED, true );
    }
 
    public static void setMusicEnabled(
        boolean musicEnabled )
    {
        getPrefs().putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
        getPrefs().flush();
    }
 
    public static float getMusicVolume()
    {
        return getPrefs().getFloat( PREF_MUSIC_VOLUME, 0.2f );
    }
 
    public static void setMusicVolume(
        float volume )
    {
        getPrefs().putFloat( PREF_MUSIC_VOLUME, volume );
        getPrefs().flush();
    }
    
//    public static float getSoundEffectsVolume()
//    {
//        return getPrefs().getFloat( PREF_SOUND_VOLUME, 0.5f );
//    }
// 
//    public static void setSoundEffectsVolume(
//        float volume )
//    {
//        getPrefs().putFloat( PREF_SOUND_VOLUME, volume );
//        getPrefs().flush();
//    }
       
    
    public static boolean isFullScreen()
    {
        return getPrefs().getBoolean( PREF_FULLSCREEN, false );
    }
 
    public static void setFullscreen(
        boolean fullscreen )
    {
        getPrefs().putBoolean( PREF_FULLSCREEN, fullscreen );
        getPrefs().flush();
    }
    
    public static String getSelectedView() {
    	// TODO fix images not updating (or restarting or something...)
    	//return "data_color2";
    	return getPrefs().getString(PREF_VIEW, "data_color2");
    }
    
    public static void setSelectedView(String view) {
        getPrefs().putString( PREF_VIEW, view );
        getPrefs().flush();
    }
    
    public static boolean isDebug()
    {
        return getPrefs().getBoolean( PREF_DEBUG, true );
    }
 
    public static void setDebug(
        boolean debug )
    {
        getPrefs().putBoolean( PREF_DEBUG, debug );
        getPrefs().flush();
    }
    
}
