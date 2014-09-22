package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionSet {

	final TextureRegion[] set;

	public TextureRegionSet(TextureRegion... set) {
		this.set = set;
	}

	public TextureRegion getTexture(int index) {
		return set[index];
	}

}
