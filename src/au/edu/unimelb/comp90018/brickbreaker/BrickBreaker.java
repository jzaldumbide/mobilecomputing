package au.edu.unimelb.comp90018.brickbreaker;

import au.edu.unimelb.comp90018.brickbreaker.framework.Screen;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.AndroidGame;

public class BrickBreaker extends AndroidGame {

	/* Main method*/
	public Screen getStartScreen() {
		
		/*Here you call the first/main screen*/
        return new IntroTestScreen(this); //IntroTestScreen is an example
    }

}
