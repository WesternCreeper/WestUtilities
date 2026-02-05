/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * This is a simple component that just has a text inside of box that isn't drawn. That's it, nothing else interesting to note here
 * @author Westley
 */
public class WGLabel extends WGDrawingObject implements TextStyles
{
    private String text;
    private Font textFont;
    private Paint textColor;
    private int textStyle = 0;
    
    /**
     * This sets up a label, which resets its font size to fit the box created by the percents given
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textStyle the style of the text that determines how the text is drawn
     * @param text The text that is the label
     * @param textFont The font that will draw the text
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGLabel(Rectangle2D bounds, double borderSize, int textStyle, String text, Font textFont, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.text = text;
        this.textFont = textFont;
        this.textColor = textColor;
        this.textStyle = textStyle;
        if(getParent() != null)
        {
            resizer = new LabelResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            resizer.resizeComps();
        }
        else
        {
            throw new WGNullParentException();
        }
    }

    /**
     * This sets up a label, which resets its font size to fit the box created by the percents given
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textStyle the style of the text that determines how the text is drawn
     * @param text The text that is the label
     * @param textFont The font that will draw the text
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGLabel(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, int textStyle, String text, Font textFont, Paint textColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, textStyle, text, textFont, textColor, parent);
    }
    
    /**
     * This sets up a label, which resets its font size to fit the box created by the percents given
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The text that is the label
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGLabel(Rectangle2D bounds, String text, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), theme.getTextStyle(), text, theme.getTextFont(), theme.getTextColor(), parent);
        setCurrentTheme(theme);
    }
    
    
    //Methods:
    @Override
    public Rectangle2D getBounds() 
    {
       Rectangle2D bounds = new Rectangle2D(getX(), getY(), getWidth(), getHeight());
       return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeCompsWithoutDelay();
    }
    public void setBounds(Rectangle2D newBounds)
    {
        resizer.setBounds(newBounds);
    }
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        this.textStyle = theme.getTextStyle();
    }
    public WGDrawingObject cloneObject() throws WGNullParentException
    {
    	WGDrawingObject obj;

    	if(getCurrentTheme() != null)
    	{
			obj = new WGLabel(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), text, getParent(), getCurrentTheme());
    	}
    	else
    	{
			obj = new WGLabel(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), getBorderSize(), textStyle, text, textFont, textColor, getParent());
    	}
    	return obj;
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        if(getToolTip() != null)
        {
            getToolTip().removeListeners();
        }

        WestGraphics.remove(this);
        if(getDragAndDropBar() != null)
        {
        	getDragAndDropBar().removeListeners();
        }
    }
    
    
    //Setters:
    public void setText(String text) {
        this.text = text;
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }
    
    
    //Getters:
    public String getText() {
        return text;
    }

    public Font getTextFont() {
        return textFont;
    }

    public Paint getTextColor() {
        return textColor;
    }

    public int getTextStyle() {
        return textStyle;
    }
    
    
    //classes or Listeners:
    private class LabelResizeListener extends WGDrawingObjectResizeListener
    {
        private LabelResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeCompsWithoutDelay()
        {
        	setResizing(true);
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double parentX = 0;
            double parentY = 0;
            WGPane pane = getParentOwningPane();
            if(pane != null && pane.isUseRelativePositions())
            {
            	parentWidth = pane.getWidth();
                parentHeight = pane.getHeight();
                parentX = pane.getX();
                parentY = pane.getY();
            }
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth + parentX);
            setY(getYPercent() * parentHeight + parentY);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
            }
        	setResizing(false);
        }
    }
}
