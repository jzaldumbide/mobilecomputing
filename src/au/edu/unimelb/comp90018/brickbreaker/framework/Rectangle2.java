package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

/**
 * Extension of Rectangle class in order to control which side(s) of the object
 * was(were) hit. This is particularly important for the collision between the
 * ball and the brick as ball's direction depends deeply on its hit side.
 * 
 */
public class Rectangle2 extends Rectangle {

	private static final long serialVersionUID = 1954831445984396570L;

	public enum RectangleSide {
		Right, Top, Left, Bottom
	}

	public Rectangle2(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public List<RectangleSide> whichSidesOverlapMe(Rectangle r) {

		List<RectangleSide> sides = new ArrayList<RectangleSide>();

		if (x < r.x && x + width > r.x && r.x + r.width > x + width)
			sides.add(RectangleSide.Right);
		if (y < r.y && y + height > r.y && r.y + r.height > y + height)
			sides.add(RectangleSide.Top);
		if (r.x < x && r.x + r.width > x && x + width > r.x + r.width)
			sides.add(RectangleSide.Left);
		if (r.y < y && r.y + r.height > y && y + height > r.y + r.height)
			sides.add(RectangleSide.Bottom);

		return sides;
	}

}
