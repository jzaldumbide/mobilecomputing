package au.edu.unimelb.comp90018.brickbreaker.framework;

import au.edu.unimelb.comp90018.brickbreaker.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();

	public int getHeight();

	public PixmapFormat getFormat();

	public void dispose();
}
