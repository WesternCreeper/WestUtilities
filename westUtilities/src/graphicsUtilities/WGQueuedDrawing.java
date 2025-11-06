package graphicsUtilities;

import javafx.geometry.Rectangle2D;

/**
 * This is a class to hold a drawing object and a clip so that when the object is needed to be drawn later, it is drawn with the correct clip
 */
public class WGQueuedDrawing 
{
	WGDrawingObject object;
	Rectangle2D clip;
	
	WGQueuedDrawing(WGDrawingObject object, Rectangle2D clip)
	{
		this.object = object;
		this.clip = clip;
	}

	
	//Getters:
	public WGDrawingObject getObject() {
		return object;
	}

	public Rectangle2D getClip() {
		return clip;
	}
}
