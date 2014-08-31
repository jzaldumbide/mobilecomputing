package au.edu.unimelb.comp90018.brickbreaker;

import android.os.Bundle;
//import au.edu.unimelb.comp90018.brickbreaker.test.TextureDemo;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BrickBreaker(), config);
		//initialize(new TextureDemo(), config);
	}
}

