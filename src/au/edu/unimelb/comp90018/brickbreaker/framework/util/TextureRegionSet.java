package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class particularly used when rendering the bricks. It stores a sequence of
 * TextureRegion which correspond to the sequence of bricks shown depending on
 * the number of hits a brick gets hit to be pulverised.
 * 
 */
public class TextureRegionSet {

	final TextureRegion[] set;

	public TextureRegionSet(TextureRegion... set) {
		this.set = set;
	}

	public TextureRegion getTexture(int index) {
		return set[index];
	}

}
