package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Rectangle2 extends Rectangle {

	private static final long serialVersionUID = 1954831445984396570L;

	public enum RectangleSide {
		Right, Top, Left, Bottom, None, All
	}
	
//	public RectangleSide overlapsSides(Rectangle r) {
//		Rectangle intersection = new Rectangle();                  
//		Intersector.intersectRectangles(this, r, intersection);     
//		if(intersection.x > this.x)                                  
//			return RectangleSide.Right;
//		if(intersection.y > this.y)                                  
//			return RectangleSide.Top;                                
//		if(intersection.x + intersection.width < this.x + this.width)  
//			return RectangleSide.Left;                               
//		if(intersection.y + intersection.height < this.y + this.height)
//			return RectangleSide.Bottom;
//	}
}
