package graphicsUtilities;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * A simple class that can be used to draw over WGComponents completely, without having to put any additional logic
 * These are immutable once constructed
 */
public class WGOverlay 
{
	private Paint textColor;
	private Paint overlayColor;
	private String text;
	private Font textFont;
	public WGOverlay(String text, Font textFont, Paint overlayColor, Paint textColor) 
	{
		this.text = text;
		this.overlayColor = overlayColor;
		this.textColor = textColor;
		this.textFont = textFont;
	}
	
	
	//Getters:
	public String getText() {
		return text;
	}
	
	public Paint getOverlayColor() {
		return overlayColor;
	}
	
	public Paint getTextColor() {
		return textColor;
	}

	public Font getTextFont() {
		return textFont;
	}
}
