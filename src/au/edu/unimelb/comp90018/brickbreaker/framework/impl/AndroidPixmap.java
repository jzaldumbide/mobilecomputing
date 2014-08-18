package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import android.graphics.Bitmap;

import au.edu.unimelb.comp90018.brickbreaker.framework.Graphics.PixmapFormat;
import au.edu.unimelb.comp90018.brickbreaker.framework.Pixmap;

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public PixmapFormat getFormat() {
        return format;
    }

    public void dispose() {
        bitmap.recycle();
    }      
}
