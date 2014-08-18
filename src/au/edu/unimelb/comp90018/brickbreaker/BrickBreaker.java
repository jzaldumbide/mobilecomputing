package au.edu.unimelb.comp90018.brickbreaker;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.os.Bundle;
import au.edu.unimelb.comp90018.brickbreaker.framework.Screen;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.AndroidGame;

//public class BrickBreaker extends AndroidGame {
//
//	/* Main method*/
//	public Screen getStartScreen() {
//		
//		/*Here you call the first/main screen*/
//        return new BreakoutScreen(this); //IntroTestScreen is an example
//    }
//
//}

public class BrickBreaker extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new BrickBreaker(), config);
		//initialize(new PhysicsDemo(), config);
		initialize(new BrickBreakerGame(), config);
	}
}
