package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.impl.DynamicGameObject;
import au.edu.unimelb.comp90018.brickbreaker.framework.impl.World;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.Assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Brick extends DynamicGameObject {

	public static final float BRICK_WIDTH = Assets.paddle.getRegionWidth();
	public static final float BRICK_HEIGHT = Assets.paddle.getRegionHeight();

	public Brick(TextureRegion texture, float x, float y) {
		super(texture, x, y);
	}

	public void update(float deltaTime,World world) {


	}
	
	public void dispose(){
		this.dispose();
	}
	
}
