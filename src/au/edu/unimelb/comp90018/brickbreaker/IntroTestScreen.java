package au.edu.unimelb.comp90018.brickbreaker;

import au.edu.unimelb.comp90018.brickbreaker.framework.Game;
import au.edu.unimelb.comp90018.brickbreaker.framework.Graphics;
import au.edu.unimelb.comp90018.brickbreaker.framework.Graphics.PixmapFormat;
import au.edu.unimelb.comp90018.brickbreaker.framework.Screen;

public class IntroTestScreen extends Screen {
    
	public IntroTestScreen(Game game) {
        super(game);
    }

	@Override
	public void update(float deltaTime) {
		
		Graphics g = game.getGraphics();
        Assets.intro = g.newPixmap("intro.png", PixmapFormat.RGB565);

	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
        g.drawPixmap(Assets.intro, 0, 0);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
