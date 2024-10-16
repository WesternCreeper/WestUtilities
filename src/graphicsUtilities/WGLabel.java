/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * This is a simple component that just has a text inside of box that isn't drawn. That's it, nothing else interesting to note here
 * @author Westley
 */
public class WGLabel extends WGDrawingObject implements TextStyles
{
    private String text;
    private Font textFont;
    private Color textColor;
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
    public WGLabel(Rectangle2D.Double bounds, float borderSize, int textStyle, String text, Font textFont, Color textColor, Component parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.text = text;
        this.textFont = textFont;
        this.textColor = textColor;
        this.textStyle = textStyle;
        if(getParent() != null)
        {
            resizer = new LabelResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
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
    public WGLabel(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, int textStyle, String text, Font textFont, Color textColor, Component parent) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, textStyle, text, textFont, textColor, parent);
    }
    
    /**
     * This sets up a label, which resets its font size to fit the box created by the percents given
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The text that is the label
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGLabel(Rectangle2D.Double bounds, String text, Component parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), theme.getTextStyle(), text, theme.getTextFont(), theme.getTextColor(), parent);
    }
    
    
    //Methods:
    @Override
    public Rectangle2D.Double getBounds() 
    {
       Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
       return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeComps();
    }
    public void setBounds(Rectangle2D.Double newBounds)
    {
        resizer.setBounds(newBounds);
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().removeComponentListener(resizer);
    }
    
    
    //Setters:
    public void setText(String text) {
        this.text = text;
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTextColor(Color textColor) {
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

    public Color getTextColor() {
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
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
