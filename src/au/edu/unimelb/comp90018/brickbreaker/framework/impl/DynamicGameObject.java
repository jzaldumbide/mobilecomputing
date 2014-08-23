package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import au.edu.unimelb.comp90018.brickbreaker.framework.ImageObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Some Objects may have movement characteristics as velocity and accel.
 * 
 * @author Diego
 *
 */
public class DynamicGameObject extends GameObject implements ImageObject {
	public final Vector2 velocity;
	public final Vector2 accel;

	public TextureRegion texture;

	public DynamicGameObject(TextureRegion texture, float x, float y) {
		super(x, y, texture.getRegionWidth(), texture.getRegionHeight());
		this.texture = texture;
		velocity = new Vector2();
		accel = new Vector2();
	}

	@Override
	public TextureRegion getTexture() {
		return texture;
	}

	@Override
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, super.getX(), super.getY(), super.getWidth(),
				super.getHeight());
	}

	@Override
	public void dispose() {

	}

}
