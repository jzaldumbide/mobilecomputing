package au.edu.unimelb.comp90018.brickbreaker.screens;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreaker;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button;
import au.edu.unimelb.comp90018.brickbreaker.actors.Button.ButtonSize;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class HelpScreen extends ScreenAdapter {
	BrickBreaker game;

	OrthographicCamera guiCam;
	Vector3 touchPoint;

	String textHeadline = "1/5";
	String textHint = "Hint1";
	
	Texture screenshot,screenshot1,screenshot2,screenshot3,screenshot4,screenshot5, helpbackground;
	
	String hint, hint1, hint2,hint3,hint4,hint5;
	
	private Button btnBack;
	private Button btnLeft;
	private Button btnRight;
	int counter = 1;

	public HelpScreen(BrickBreaker game) {

		this.game = game;

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH,
				Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2,
				Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		btnBack = new Button(20, 20, ButtonSize.MEDIUM_SQUARE);
		btnLeft = new Button( -20+Settings.TARGET_WIDTH / 2,80, ButtonSize.MEDIUM_SQUARE);
		btnRight = new Button( 20+Settings.TARGET_WIDTH / 2,80, ButtonSize.MEDIUM_SQUARE);
		
		helpbackground = new Texture("helpscreens/helpbackground.png");
		
		screenshot1 = new Texture("helpscreens/help1.png");
		screenshot2 = new Texture("helpscreens/help2.png");
		screenshot3 = new Texture("helpscreens/help3.png");
		screenshot4 = new Texture("helpscreens/help4.png");
		screenshot5 = new Texture("helpscreens/help5.png");
		screenshot5 = new Texture("helpscreens/help5.png");
		screenshot=screenshot1;
		
		hint1= "To start a game just push \non Play! button";
		hint2="You can see your levels \nlocked and unlocked";
		hint3="In the Options screen \nyou can enable/disable \nsounds, music and \naccelerometer";
		hint4="You can destroy the bricks \nusing a paddle and a ball.\nPush down or use the accel.\nto move the paddle";
		hint5="You have some bonus items.. \ncatch them!!\nand have fun!!\n                               Unimelb 2014";
		hint=hint1;
		
		
	}

	public void update() {
		if (Gdx.input.justTouched()) {

			guiCam.unproject(
					touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0),
					game.viewport.x, game.viewport.y, game.viewport.width,
					game.viewport.height);

			if (btnBack.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MenuScreen(game));
				return;
			}
			if (btnLeft.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				
				if(counter==1){
					counter=6;
				}
				counter = counter - 1;
				
				textHeadline = Integer.toString(counter) + "/5";
				//textHint = "Hint" + Integer.toHexString(counter);
				
				
				if (counter==1) {hint=hint1;screenshot = screenshot1;}
				if (counter==2) {hint=hint2;screenshot = screenshot2;}
				if (counter==3) {hint=hint3;screenshot = screenshot3;}
				if (counter==4) {hint=hint4;screenshot = screenshot4;}
				if (counter==5) {hint=hint5;screenshot = screenshot5;}

				return;
			}
			
			if (btnRight.bounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				// game.setScreen(new MenuScreen(game));
				counter = counter + 1;
				if (counter == 6) {
					counter = 1;
					screenshot = screenshot1;
					
					}
				textHeadline = Integer.toString(counter) + "/5";
				textHint = "Hint" + Integer.toHexString(counter);
				if (counter==1) {hint=hint1;screenshot = screenshot1;}
				if (counter==2) {hint=hint2;screenshot = screenshot2;}
				if (counter==3) {hint=hint3;screenshot = screenshot3;}
				if (counter==4) {hint=hint4;screenshot = screenshot4;}
				if (counter==5) {hint=hint5;screenshot = screenshot5;}


				return;
			}


		}
	}

	public void draw() {

GL20 gl = Gdx.gl;
		
		gl.glViewport(
				(int) game.viewport.x, 
				(int) game.viewport.y, 
				(int) game.viewport.width,
				(int) game.viewport.height
				);
		
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		
		Assets.font.setScale(0.7f, 0.7f);
		Assets.font.setColor(new Color(Color.WHITE));
		

		
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.draw(helpbackground, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		game.batcher.draw(screenshot, Settings.TARGET_WIDTH/4, Settings.TARGET_HEIGHT*5/12, Settings.TARGET_WIDTH/2, Settings.TARGET_HEIGHT/2);
		
		//game.batcher.draw(Assets.defaultNotification, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);

		game.batcher.draw(Assets.back, btnBack.position.x
				- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnBack.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight()
						/ 2, ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());

		
		game.batcher.draw(Assets.arrowLeft, btnLeft.position.x
				- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnLeft.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight()
						/ 2, ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());
		
		
		game.batcher.draw(Assets.arrowRight, btnRight.position.x
				- ButtonSize.MEDIUM_SQUARE.getButtonWidth() / 2,
				btnRight.position.y - ButtonSize.MEDIUM_SQUARE.getButtonHeight()
						/ 2, ButtonSize.MEDIUM_SQUARE.getButtonWidth(),
				ButtonSize.MEDIUM_SQUARE.getButtonHeight());
		
		
		game.batcher.end();
		game.batcher.begin();
		game.batcher.enableBlending();
		Assets.font.setScale(0.6f, 0.6f);
		Assets.font.drawMultiLine(game.batcher, textHeadline,- Settings.TARGET_WIDTH/32 +	Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT*23/24);
		Assets.font.drawMultiLine(game.batcher, hint, Settings.TARGET_WIDTH/32, Settings.TARGET_HEIGHT*19/48);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		draw();
		update();
	}

	@Override
	public void hide() {
		super.dispose();
	}
}
