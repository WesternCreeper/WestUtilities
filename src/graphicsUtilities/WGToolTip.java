/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGToolTip extends WGBox
{
    public static final int TEXT_STYLE_LEFT = 0;
    public static final int TEXT_STYLE_MIDDLE = 1;
    public static final int TEXT_STYLE_RIGHT = 2;
    private String[] toolTipText;
    private Font toolTipFont;
    private WGToolTipListener toolTipListener;
    private Color textColor;
    private double longestStringWidth = 0;
    private int textStyle = 0;
    /**
     * This creates a basic tooltip with a string given to it. This will turn that string into multiple lines of strings if newLine characters are in the original string
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the border around the object
     * @param text The text that will be written on the tooltip, this will be turned into an array when new line characters are found in the string
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The parent that the object is being drawn on, useful for any click operation
     * @param listener The listener that makes sure this object is being displayed in the correct location, This is set up for you so no need to set it up (Use the most basic definition)
     * @param toolTipOwner The owner of this toolTip, aka the object that this toolTip is locked to
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGToolTip(double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent, WGToolTipListener listener, WGDrawingObject toolTipOwner) throws WGNullParentException
    {
        super(borderSize, backgroundColor, borderColor, parent);
        toolTipText = text.split("\n");
        toolTipFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            listener.setParentComponent(parent);
            listener.setParentObject(toolTipOwner);
            listener.setToolTipObject(this);
            getParent().addMouseListener(listener);
            getParent().addMouseMotionListener(listener);
            toolTipListener = listener;
            resizer = new ToolTipResizeListener(0, 0, widthPercent, heightPercent);
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * This creates a basic tooltip with a string given to it. This will turn that string into multiple lines of strings if newLine characters are in the original string. And has the ability to specify where the text goes
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the border around the object
     * @param textStyle the style of the text that determines how the text is drawn
     * @param text The text that will be written on the tooltip, this will be turned into an array when new line characters are found in the string
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The parent that the object is being drawn on, useful for any click operation
     * @param listener The listener that makes sure this object is being displayed in the correct location, This is set up for you so no need to set it up (Use the most basic definition)
     * @param toolTipOwner The owner of this toolTip, aka the object that this toolTip is locked to
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGToolTip(double widthPercent, double heightPercent, float borderSize, int textStyle, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent, WGToolTipListener listener, WGDrawingObject toolTipOwner) throws WGNullParentException
    {
        this(widthPercent, heightPercent, borderSize, text, textFont, backgroundColor, borderColor, textColor, parent, listener, toolTipOwner);
        this.textStyle = textStyle;
    }
    
    //Methods
    
    /**
     * Returns a rectangular representation of the object
     * @return Rectangl2D.Double - bounds of the object
     */
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
    public String getLongestString()
    {
        FontMetrics textFM = getParent().getFontMetrics(toolTipFont);
        int longest = 0;
        for(int i = 1 ; i < toolTipText.length ; i++)
        {
            if(textFM.stringWidth(toolTipText[i]) > textFM.stringWidth(toolTipText[longest]))
            {
                longest = i;
            }
        }
        return toolTipText[longest];
    }
    
    
    //Setters:
    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText.split("\n");
    }

    public void setToolTipFont(Font toolTipFont) {
        this.toolTipFont = toolTipFont;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }
    
    
    //Getters:
    public String[] getToolTipText() {
        return toolTipText;
    }

    public Font getToolTipFont() {
        return toolTipFont;
    }

    public Color getTextColor() {
        return textColor;
    }

    public double getLongestStringWidth() {
        return longestStringWidth;
    }

    public int getTextStyle() {
        return textStyle;
    }

    public WGToolTipListener getToolTipListener() {
        return toolTipListener;
    }
    
    
    //classes or Listeners:
    private class ToolTipResizeListener extends WGDrawingObjectResizeListener
    {
        private ToolTipResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            double borderPadding = getBorderSize(); //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            String longestString = getLongestString();
            toolTipFont = WGFontHelper.getFittedFontForBox(toolTipFont, getParent(), getWidth() - borderPadding, (getHeight() / toolTipText.length) - borderPadding, longestString, 100);
            
            //Then Find the best X place so the drawing is faster and smoother:
            FontMetrics textFM = getParent().getFontMetrics(toolTipFont);
            longestStringWidth = textFM.stringWidth(longestString);
            
            //Now make the height and width "fit" to the text:
            double textWidth = textFM.stringWidth(longestString);
            double textHeight = textFM.getHeight() * toolTipText.length;
            if(getWidth() > textWidth) //IF the actual width is greater than the text's width, THEN the it needs to be fixed
            {
                setWidth(textWidth + borderPadding * 2);
            }
            if(getHeight() > textHeight) //IF the actual height is greater than the text's height, THEN the it needs to be fixed
            {
                setHeight(textHeight + borderPadding * 2);
            }
            
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
