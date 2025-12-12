/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.FXFontMetrics;

/**
 *
 * @author Westley
 */
public class WGToolTip extends WGBox implements TextStyles
{
    private String[] toolTipText;
    private Font toolTipFont;
    private WGToolTipListener toolTipListener;
    private Paint textColor;
    private double longestStringWidth = 0;
    private int textStyle = 0;
    private boolean autoResizeText = true;
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
    public WGToolTip(double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGToolTipListener listener, WGDrawingObject toolTipOwner) throws WGNullParentException
    {
        super(borderSize, backgroundColor, backgroundColor, borderColor, parent);
        toolTipText = text.split("\n");
        toolTipFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            listener.setParentComponent(parent);
            listener.setParentObject(toolTipOwner);
            listener.setToolTipObject(this);
            getParent().addEventHandler(MouseEvent.ANY, listener);
            toolTipListener = listener;
            resizer = new ToolTipResizeListener(0, 0, widthPercent, heightPercent);
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
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
    public WGToolTip(double widthPercent, double heightPercent, float borderSize, int textStyle, String text, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGToolTipListener listener, WGDrawingObject toolTipOwner) throws WGNullParentException
    {
        this(widthPercent, heightPercent, borderSize, text, textFont, backgroundColor, borderColor, textColor, parent, listener, toolTipOwner);
        this.textStyle = textStyle;
    }
    /**
     * This creates a basic tooltip with a string given to it. This will turn that string into multiple lines of strings if newLine characters are in the original string. And has the ability to specify where the text goes
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param text The text that will be written on the tooltip, this will be turned into an array when new line characters are found in the string
     * @param parent The parent that the object is being drawn on, useful for any click operation
     * @param listener The listener that makes sure this object is being displayed in the correct location, This is set up for you so no need to set it up (Use the most basic definition)
     * @param toolTipOwner The owner of this toolTip, aka the object that this toolTip is locked to
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGToolTip(double widthPercent, double heightPercent, String text, Canvas parent, WGToolTipListener listener, WGDrawingObject toolTipOwner, WGTheme theme) throws WGNullParentException
    {
        this(widthPercent, heightPercent, theme.getBorderSize(), text, theme.getTextFont(), theme.getBackgroundColor(), theme.getBorderColor(), theme.getTextColor(), parent, listener, toolTipOwner);
        this.textStyle = theme.getTextStyle();
        setCurrentTheme(theme);
    }
    /**
     * This creates a basic tooltip with a string given to it. This will turn that string into multiple lines of strings if newLine characters are in the original string. And has the ability to specify where the text goes
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param autoResizeText Tells the tool tip to resize or not resize the text (This may lead to the preferred size to not be taken into account)
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
    public WGToolTip(double widthPercent, double heightPercent, boolean autoResizeText, float borderSize, int textStyle, String text, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGToolTipListener listener, WGDrawingObject toolTipOwner) throws WGNullParentException
    {
        super(borderSize, backgroundColor, backgroundColor, borderColor, parent);
        this.autoResizeText = autoResizeText;
        toolTipText = text.split("\n");
        toolTipFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            listener.setParentComponent(parent);
            listener.setParentObject(toolTipOwner);
            listener.setToolTipObject(this);
            getParent().addEventHandler(MouseEvent.ANY, listener);
            toolTipListener = listener;
            resizer = new ToolTipResizeListener(0, 0, widthPercent, heightPercent);
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
        }
        else
        {
            throw new WGNullParentException();
        }
        this.textStyle = textStyle;
    }
    /**
     * This creates a basic tooltip with a string given to it. This will turn that string into multiple lines of strings if newLine characters are in the original string. And has the ability to specify where the text goes
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param autoResizeText Tells the tool tip to resize or not resize the text (This may lead to the preferred size to not be taken into account)
     * @param text The text that will be written on the tooltip, this will be turned into an array when new line characters are found in the string
     * @param parent The parent that the object is being drawn on, useful for any click operation
     * @param listener The listener that makes sure this object is being displayed in the correct location, This is set up for you so no need to set it up (Use the most basic definition)
     * @param toolTipOwner The owner of this toolTip, aka the object that this toolTip is locked to
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGToolTip(double widthPercent, double heightPercent, boolean autoResizeText, String text, Canvas parent, WGToolTipListener listener, WGDrawingObject toolTipOwner, WGTheme theme) throws WGNullParentException
    {
        this(widthPercent, heightPercent, autoResizeText, theme.getBorderSize(), theme.getTextStyle(), text, theme.getTextFont(), theme.getBackgroundColor(), theme.getBorderColor(), theme.getTextColor(), parent, listener, toolTipOwner);
        setCurrentTheme(theme);
    }
    
    //Methods
    
    /**
     * Returns a rectangular representation of the object
     * @return Rectangl2D.Double - bounds of the object
     */
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
        this.toolTipFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        this.textStyle = theme.getTextStyle();
        resizer.resizeComps();
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().widthProperty().removeListener(resizer.getResizeListener());
        getParent().heightProperty().removeListener(resizer.getResizeListener());
        
        WGToolTipListener toolTip = getToolTipListener();
        getParent().removeEventHandler(MouseEvent.ANY, toolTip);
    }
    
    public String getLongestString()
    {
        if(toolTipText == null || toolTipText.length == 0)
        {
            return "";
        }
        FXFontMetrics textFM = new FXFontMetrics(toolTipFont);
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
        
        //Now make sure that if the object was being shown but now should not because of the new text to update that:
        if(toolTipText.isEmpty() && isShown())
        {
            setShown(false);
        }
        
        resizer.resizeComps();
    }

    public void setToolTipFont(Font toolTipFont) {
        this.toolTipFont = toolTipFont;
        resizer.resizeComps();
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
        resizer.resizeComps();
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
        resizer.resizeComps();
    }
    
    
    //Getters:
    public String[] getToolTipText() {
        return toolTipText;
    }

    public Font getToolTipFont() {
        return toolTipFont;
    }

    public Paint getTextColor() {
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
        public void resizeCompsWithoutDelay()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double borderPadding = getBorderSize(); //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            String longestString = getLongestString();
            if(autoResizeText)
            {
                toolTipFont = WGFontHelper.getFittedFontForBox(toolTipFont, getParent(), getWidth() - borderPadding, (getHeight() / toolTipText.length) - borderPadding, longestString, 100);
            }
            
            //Then Find the best X place so the drawing is faster and smoother:
            FXFontMetrics textFM = new FXFontMetrics(toolTipFont);
            longestStringWidth = textFM.stringWidth(longestString);
            
            //Now make the height and width "fit" to the text:
            double textWidth = textFM.stringWidth(longestString);
            double textHeight = textFM.getHeight(longestString) * toolTipText.length;
            
            if(!autoResizeText)
            {
                //Fit the text into the box (No matter if it is too big or too small):
                setWidth(textWidth + borderPadding * 2);
                setHeight(textHeight + borderPadding * 2);
            }
            else
            {
                if(getWidth() > textWidth) //IF the actual width is greater than the text's width, THEN the it needs to be fixed
                {
                    setWidth(textWidth + borderPadding * 2);
                }
                if(getHeight() > textHeight) //IF the actual height is greater than the text's height, THEN the it needs to be fixed
                {
                    setHeight(textHeight + borderPadding * 2);
                }
            }

            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
            }
        }
    }
}
