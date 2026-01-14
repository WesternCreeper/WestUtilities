package graphicsUtilities;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;

public class ColoredRectangle
{
	private Rectangle2D rectangularArea;
	private Paint borderColor;
	private Paint backgroundColor;
	
	public ColoredRectangle(Rectangle2D rectangularArea, Paint borderColor, Paint backgroundColor)
	{
		this.rectangularArea = rectangularArea;
		this.borderColor = borderColor;
		this.backgroundColor = backgroundColor;
	}

	
	//Getters:
	public Rectangle2D getRectangularArea() {
		return rectangularArea;
	}

	public Paint getBorderColor() {
		return borderColor;
	}

	public Paint getBackgroundColor() {
		return backgroundColor;
	}

	
	//Setters:
	public void setRectangularArea(Rectangle2D rectangularArea) {
		this.rectangularArea = rectangularArea;
	}

	public void setBorderColor(Paint borderColor) {
		this.borderColor = borderColor;
	}

	public void setBackgroundColor(Paint backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}