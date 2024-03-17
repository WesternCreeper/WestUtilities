/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.Component;

/**
 *
 * @author Westley
 */
public class WGButton extends WGDrawingObject
{
    private String text;
    private Font textFont;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    /**
     * This creates a baseline button that cannot resize itself
     * @param x The X of the button
     * @param y The Y of the button
     * @param width The Width of the button
     * @param height The Height of the button
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The text that goes on the button
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     */
    public WGButton(double x, double y, double width, double height, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor)
    {
        super(x, y, width, height, borderSize, null);
        this.text = text;
        this.textFont = textFont;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
    }
    /**
     * This creates a baseline button that can and WILL resize itself. This will fully integrate it's resizing
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The text that goes on the button
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     */
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent)
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.text = text;
        this.textFont = textFont;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
        if(getParent() != null)
        {
            ButtonResizeListener resizer = new ButtonResizeListener(xPercent, yPercent, widthPercent, heightPercent);
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
        }
    }
    
    @Override
    public Rectangle2D.Double getBounds() 
    {
       Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
       return bounds;
    }
    

    //Setters
    public void setText(String text) {
        this.text = text;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    
    //Getters:
    public String getText() {
        return text;
    }

    public Font getTextFont() {
        return textFont;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getTextColor() {
        return textColor;
    }
    
    
    //classes or Listeners:
    private class ButtonResizeListener extends WGDrawingObjectResizeListener
    {
        private ButtonResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
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
            textFont = WGFontHelper.getFittedFontForBox(textFont, getWidth() - borderPadding, getHeight() - borderPadding, text.length());
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
