package graphicsUtilities.formatting;

import graphicsUtilities.ColoredString;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.FXFontMetrics;

public class PositionedString extends ColoredString
{
	private double x;
	private double y;
	private double width;
	private double height;
	public PositionedString(double x, double y, String str, Font font, Paint color)
	{
		super(str, font, color);
		this.x = x;
		this.y = y;
		//Determine the size of the string:
		this.width = FXFontMetrics.stringWidth(this);
		this.height = FXFontMetrics.getHeight(this);
	}
	public PositionedString(double x, double y, ColoredString str)
	{
		super(str);
		this.x = x;
		this.y = y;
		this.width = FXFontMetrics.stringWidth(this);
		this.height = FXFontMetrics.getHeight(this);
	}
	
	
	//Method:
	public Rectangle2D getBounds()
	{
		return new Rectangle2D(x, y, width, height);
	}
	
	
	//Setters:
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	
	//Getters:
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
}
