package au.edu.unimelb.comp90018.brickbreaker.framework;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ImageObject {
	TextureRegion getTexture();

	void setTexture(TextureRegion texture);

	void render(SpriteBatch batch);

	void dispose();
}
