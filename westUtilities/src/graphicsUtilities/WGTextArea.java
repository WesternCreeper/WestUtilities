/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.util.ArrayList;

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
public class WGTextArea extends WGDrawingObject implements TextStyles
{
    private boolean textWrapped = false;
    private int textStyle = 0;
    private double stringYOffset = 0;
    private ArrayList<String> text;
    private ArrayList<Paint> textColors;
    private ArrayList<String> formatedText;
    private ArrayList<Paint> formatedColors;
    private Font textFont;
    private Paint textColor;
    private Paint scrollBarColor;
    private WGTextScrollableListener verticalScroll;
    
    /**
     * The standard non-scrollable constructor that holds multiple lines of text in an invisible box
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param textFont The font for the text
     * @param textStyle the style of the text that determines how the text is drawn
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(Rectangle2D bounds, float borderSize, String[] text, Font textFont, int textStyle, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.textStyle = textStyle;
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<String>(1);
        textColors = new ArrayList<Paint>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
            textColors.add(textColor);
        }
        resizer.resizeComps();
    }
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
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String[] text, Font textFont, int textStyle, Paint textColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textFont, textStyle, textColor, parent);
    }
    /**
     * The standard non-scrollable constructor that holds multiple lines of text in an invisible box
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(Rectangle2D bounds, String[] text, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), text, theme.getTextFont(), theme.getTextStyle(), theme.getTextColor(), parent);
        setCurrentTheme(theme);
    }
    /**
     * The standard scrollable constructor that holds multiple lines of text in an invisible box. NOTE: This function does NOT change the size of the font based on the size of the object. So make sure the Font object has the size you wanted
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param textFont The font for the text
     * @param textStyle the style of the text that determines how the text is drawn
     * @param textWrapped Whether or not the text is wrapped when the line becomes too long
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param scrollBarColor The color of the scrollBar
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(Rectangle2D bounds, float borderSize, String[] text, Font textFont, int textStyle, boolean textWrapped, Paint textColor, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        //Create the object
        super(0, 0, 0, 0, borderSize, parent);
        this.textWrapped = textWrapped;
        this.textStyle = textStyle;
        this.textFont = textFont;
        this.textColor = textColor;
        this.scrollBarColor = scrollBarColor;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            verticalScroll = new WGTextScrollableListener(this);
            getParent().addEventHandler(MouseEvent.ANY, verticalScroll);
            getParent().addEventHandler(ScrollEvent.ANY, verticalScroll);
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<String>(1);
        textColors = new ArrayList<Paint>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
            textColors.add(textColor);
        }
        resizer.resizeComps();
    }
    /**
     * The standard scrollable constructor that holds multiple lines of text in an invisible box. NOTE: This function does NOT change the size of the font based on the size of the object. So make sure the Font object has the size you wanted@
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param textFont The font for the text
     * @param textStyle the style of the text that determines how the text is drawn
     * @param textWrapped Whether or not the text is wrapped when the line becomes too long
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param scrollBarColor The color of the scrollBar
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String[] text, Font textFont, int textStyle, boolean textWrapped, Paint textColor, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textFont, textStyle, textWrapped, textColor, scrollBarColor, parent);
    }
    
    /**
     * The standard scrollable constructor that holds multiple lines of text in an invisible box. NOTE: This function does NOT change the size of the font based on the size of the object. So make sure the Font object has the size you wanted
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param textWrapped Whether or not the text is wrapped when the line becomes too long
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(Rectangle2D bounds, String[] text, boolean textWrapped, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), text, theme.getTextFont(), theme.getTextStyle(), textWrapped, theme.getTextColor(), theme.getScrollBarColor(), parent);
        setCurrentTheme(theme);
    }
    
    @Override
    public Rectangle2D getBounds() 
    {
        Rectangle2D bounds = new Rectangle2D(getX(), getY(), getWidth(), getHeight());
        return bounds;
    }

    @Override
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
        setBorderSize(theme.getBorderSize());
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        this.textStyle = theme.getTextStyle();
        this.scrollBarColor = theme.getScrollBarColor();
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().widthProperty().removeListener(resizer.getResizeListener());
        getParent().heightProperty().removeListener(resizer.getResizeListener());
        if(getToolTip() != null)
        {
            getToolTip().removeListeners();
        }
        
        WGTextScrollableListener scrollerV = getVerticalScroll();
        getParent().removeEventHandler(MouseEvent.ANY, scrollerV);
        getParent().removeEventHandler(ScrollEvent.ANY, scrollerV);
    }
    
    public void setTextLine(int index, String information) throws IndexOutOfBoundsException
    {
        text.set(index, information);
        resizer.resizeComps();
    }
    
    public void addTextLine(String information) throws IndexOutOfBoundsException
    {
        text.add(information);
        textColors.add(textColor);
        resizer.resizeComps();
    }
    
    public void addTextLine(String information, Paint textColor) throws IndexOutOfBoundsException
    {
        text.add(information);
        textColors.add(fixPaintBounds(textColor, WGDrawingObject.VERTICAL_GRADIENT_ORIENTATION_PREFERENCE));
        resizer.resizeComps();
    }
    
    public void removeAllText()
    {
        text.removeAll(text);
        textColors.removeAll(textColors);
        stringYOffset = 0;
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
    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTextColor(Paint textColor) {
        this.textColor = fixPaintBounds(textColor, WGDrawingObject.VERTICAL_GRADIENT_ORIENTATION_PREFERENCE);
    }

    public void setStringYOffset(double stringYOffset) {
        this.stringYOffset = stringYOffset;
    }
    
    
    //Getters:
    public ArrayList<String> getText() {
        return text;
    }

    public ArrayList<Paint> getTextColors() {
        return textColors;
    }

    public Font getTextFont() {
        return textFont;
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

    public Paint getScrollBarColor() {
        return scrollBarColor;
    }

    public ArrayList<String> getFormatedText() {
        return formatedText;
    }

    public ArrayList<Paint> getFormatedColors() {
        return formatedColors;
    }

    public boolean isTextWrapped() {
        return textWrapped;
    }
    
    
    //classes or Listeners:
    private class TextAreaResizeListener extends WGDrawingObjectResizeListener
    {
        private TextAreaResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeCompsWithoutDelay()
        {
        	setResizing(true);
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            //Allow for the scroll to happen if there is a pane that controls this
            if(verticalScroll != null)
            {
                if(textWrapped)
                {
                    ArrayList<String> newList = new ArrayList<String>(text.size());
                    ArrayList<Paint> newColorList = new ArrayList<Paint>(textColors.size());
                    FXFontMetrics textFM = new FXFontMetrics(textFont);
                    double objectWidth = getWidth();
                    //First copy over the strings:
                    for(int i = 0 ; i < text.size() ; i++)
                    {
                        newList.add(text.get(i));
                        newColorList.add(textColors.get(i));
                    }
                    //This requires that each line is split if too long:
                    for(int i = 0 ; i < newList.size() ; i++)
                    {
                        String str = newList.get(i);
                        double textLength = textFM.stringWidth(str);
                        if(textLength > objectWidth) //There is a problem, the string is too long, now wrap it!
                        {
                            //Now determine where in the string is the best location to split:
                            //Search Binarially:
                            int minimumSize = 0;
                            int maximumSize = str.length();
                            int sizeSplit = str.length();
                            while(true)
                            {
                                int currentSize = (int)((minimumSize + maximumSize) /2.0);
                                String test = str.substring(0, currentSize);
                                double testLength = textFM.stringWidth(test);
                                if(testLength >= objectWidth)
                                {
                                    maximumSize = currentSize;
                                }
                                else if(testLength < objectWidth)
                                {
                                    minimumSize = currentSize;
                                }
                                if(minimumSize == maximumSize)
                                {
                                    sizeSplit = minimumSize;
                                    break;
                                }
                                else if(minimumSize+1 == maximumSize) //Make sure to use the correct one:
                                {
                                    test = str.substring(0, maximumSize);
                                    testLength = textFM.stringWidth(test);
                                    if(testLength >= objectWidth)
                                    {
                                        sizeSplit = minimumSize;
                                    }
                                    if(testLength < objectWidth)
                                    {
                                        sizeSplit = maximumSize;
                                    }
                                    break;
                                }
                            }
                            //Now Do stuff:
                            String thisLine = str.substring(0, sizeSplit+1);
                            String newLine = str.substring(sizeSplit+1);
                            //Now try to keep words together:
                            if(thisLine.contains(" "))
                            {
                                //Split at the space instead if can:
                                sizeSplit = thisLine.lastIndexOf(" ");
                                thisLine = str.substring(0, sizeSplit+1);
                                newLine = str.substring(sizeSplit+1);
                            }
                            newList.set(i, thisLine);
                            newList.add(i+1, newLine.strip());
                            //Whenever a new line is added make sure to also add to the colors array:
                            newColorList.add(i+1, newColorList.get(i));
                        }
                    }
                    formatedText = newList;
                    formatedColors = newColorList;
                    verticalScroll.setScrollSpeed(formatedText.size());
                    verticalScroll.setUpScroll(formatedText);
                }
                else
                {
                    //Reset the width and height according to the size of the string:
                    textFont = WGFontHelper.getFittedFontForWidth(textFont, getParent(), getWidth() - borderPadding, getLongestString(), 100);
                    verticalScroll.setScrollSpeed(text.size());
                    verticalScroll.setUpScroll(text);
                }
            }
            else
            {
                textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, (double)(getHeight() - borderPadding) / text.size(), getLongestString(), 100);
            }
            stringYOffset = 0;

            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
                scrollBarColor = fixPaintBounds(scrollBarColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.SCROLL_BAR_COLOR));
            }
        	setResizing(false);
        }
    }
}
