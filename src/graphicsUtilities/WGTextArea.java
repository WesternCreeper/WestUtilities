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
import java.util.ArrayList;

/**
 *
 * @author Westley
 */
public class WGTextArea extends WGDrawingObject
{
    public static final int TEXT_STYLE_LEFT = 0;
    public static final int TEXT_STYLE_MIDDLE = 1;
    public static final int TEXT_STYLE_RIGHT = 2;
    private int textStyle = 0;
    private double stringYOffset = 0;
    private ArrayList<String> text;
    private Font textFont;
    private Color textColor;
    private Color scrollBarColor;
    private WGTextScrollableListener verticalScroll;
    private TextAreaResizeListener resizer;
    
    /**
     * The standard non-scrollable constructor that holds multiple lines of text in an invisible box
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param textFont The font for the text
     * @param textStyle the style of the text that determines how the text is drawn
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String[] text, Font textFont, int textStyle, Color textColor, Component parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.textStyle = textStyle;
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(xPercent, yPercent, widthPercent, heightPercent);
            getParent().addComponentListener(resizer);
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<String>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
        }
        resizer.resizeComps();
    }
    /**
     * The standard scrollable constructor that holds multiple lines of text in an invisible box. NOTE: This function does NOT change the size of the font based on the size of the object. So make sure the Font object has the size you wanted@
     * Remember to call g3.draw(thisObject.getOwningPane) to draw this properly
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param textFont The font for the text
     * @param textStyle the style of the text that determines how the text is drawn
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param backgroundColor The color of the background of the pane
     * @param borderColor The border color of the pane
     * @param scrollBarColor The color of the scrollBar
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String[] text, Font textFont, int textStyle, Color textColor, Color backgroundColor, Color borderColor, Color scrollBarColor, Component parent) throws WGNullParentException
    {
        //Create the object
        super(0, 0, 0, 0, borderSize, parent);
        this.textStyle = textStyle;
        this.textFont = textFont;
        this.textColor = textColor;
        this.scrollBarColor = scrollBarColor;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(xPercent, yPercent, widthPercent, heightPercent);
            getParent().addComponentListener(resizer);
            verticalScroll = new WGTextScrollableListener(this);
            getParent().addMouseWheelListener(verticalScroll);
            getParent().addMouseListener(verticalScroll);
            getParent().addMouseMotionListener(verticalScroll);
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<String>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
        }
        resizer.resizeComps();
    }
    
    @Override
    public Rectangle2D.Double getBounds() 
    {
        Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        return bounds;
    }

    @Override
    public void setUpBounds() {
        resizer.resizeComps();
    }
    
    public void setTextLine(int index, String information) throws IndexOutOfBoundsException
    {
        text.set(index, information);
        resizer.resizeComps();
    }
    
    public void addTextLine(String information) throws IndexOutOfBoundsException
    {
        text.add(information);
        resizer.resizeComps();
    }
    
    public String getLongestString()
    {
        int large = 0;
        for(int i = 1 ; i < text.size() ; i++)
        {
            if(text.get(i).length() > text.get(large).length())
            {
                large = i;
            }
        }
        if(large < text.size())
        {
            return text.get(large);
        }
        else
        {
            return "";
        }
    }
    
    
    //Setters:
    public void setText(String[] text) {
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
        }
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setStringYOffset(double stringYOffset) {
        this.stringYOffset = stringYOffset;
    }
    
    
    //Getters:
    public ArrayList<String> getText() {
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

    public double getStringYOffset() {
        return stringYOffset;
    }

    public WGTextScrollableListener getVerticalScroll() {
        return verticalScroll;
    }

    public Color getScrollBarColor() {
        return scrollBarColor;
    }
    
    
    //classes or Listeners:
    private class TextAreaResizeListener extends WGDrawingObjectResizeListener
    {
        private TextAreaResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
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
            //Allow for the scroll to happen if there is a pane that controls this
            if(verticalScroll != null)
            {
                //Reset the width and height according to the size of the string:
                textFont = WGFontHelper.getFittedFontForWidth(textFont, getParent(), getWidth() - borderPadding, getLongestString(), 100);
                verticalScroll.setScrollSpeed(text.size());
                verticalScroll.setUpScroll(text);
            }
            else
            {
                textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, (double)(getHeight() - borderPadding) / text.size(), getLongestString(), 100);
            }
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
