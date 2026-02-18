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
public class WGTextArea extends WGDrawingObject
{
    private boolean textWrapped = false;
    private TextStyles textStyle;
    private double stringYOffset = 0;
    private ArrayList<ColoredString> text;
    private ArrayList<ColoredString> formatedText;
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
    public WGTextArea(Rectangle2D bounds, double borderSize, String[] text, Font textFont, TextStyles textStyle, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.textStyle = textStyle;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<ColoredString>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(new ColoredString(text[i], textFont, textColor));
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
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, String[] text, Font textFont, TextStyles textStyle, Paint textColor, Canvas parent) throws WGNullParentException
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
    public WGTextArea(Rectangle2D bounds, double borderSize, String[] text, Font textFont, TextStyles textStyle, boolean textWrapped, Paint textColor, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        //Create the object
        super(0, 0, 0, 0, borderSize, parent);
        this.textWrapped = textWrapped;
        this.textStyle = textStyle;
        this.scrollBarColor = scrollBarColor;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            verticalScroll = new WGTextScrollableListener(this);
            super.setVerticalScrollListener(verticalScroll);
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<ColoredString>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(new ColoredString(text[i], textFont, textColor));
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
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, String[] text, Font textFont, TextStyles textStyle, boolean textWrapped, Paint textColor, Paint scrollBarColor, Canvas parent) throws WGNullParentException
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
    public WGTextArea(Rectangle2D bounds, double borderSize, ColoredString[] text, TextStyles textStyle, Canvas parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.textStyle = textStyle;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<ColoredString>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
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
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, ColoredString[] text, TextStyles textStyle, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textStyle, parent);
    }
    /**
     * The standard non-scrollable constructor that holds multiple lines of text in an invisible box
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The actual text. This is an array as more than one line is allowed to fit on the screen
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextArea(Rectangle2D bounds, ColoredString[] text, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), text, theme.getTextStyle(), parent);
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
    public WGTextArea(Rectangle2D bounds, double borderSize, ColoredString[] text, TextStyles textStyle, boolean textWrapped, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        //Create the object
        super(0, 0, 0, 0, borderSize, parent);
        this.textWrapped = textWrapped;
        this.textStyle = textStyle;
        this.scrollBarColor = scrollBarColor;
        if(getParent() != null)
        {
            resizer = new TextAreaResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            verticalScroll = new WGTextScrollableListener(this);
            super.setVerticalScrollListener(verticalScroll);
        }
        else
        {
            throw new WGNullParentException();
        }
        //Set the arrayList up:
        this.text = new ArrayList<ColoredString>(1);
        for(int i = 0 ; i < text.length ; i++)
        {
            this.text.add(text[i]);
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
    public WGTextArea(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, ColoredString[] text, TextStyles textStyle, boolean textWrapped, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textStyle, textWrapped, scrollBarColor, parent);
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
    public WGTextArea(Rectangle2D bounds, ColoredString[] text, boolean textWrapped, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), text, theme.getTextStyle(), textWrapped, theme.getScrollBarColor(), parent);
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
        this.textStyle = theme.getTextStyle();
        this.scrollBarColor = theme.getScrollBarColor();
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
    public WGDrawingObject cloneObject() throws WGNullParentException
    {
    	WGDrawingObject obj;

    	if(getCurrentTheme() != null)
    	{
    		if(getVerticalScroll() != null)
    		{
    			obj = new WGTextArea(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), (ColoredString[])text.toArray(), textWrapped, getParent(), getCurrentTheme());
    		}
    		else
    		{
    			obj = new WGTextArea(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), (ColoredString[])text.toArray(), getParent(), getCurrentTheme());
    		}
    	}
    	else
    	{
    		if(getVerticalScroll() != null)
    		{
    			obj = new WGTextArea(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), getBorderSize(), (ColoredString[])text.toArray(), textStyle, getParent());
    		}
    		else
    		{
    			obj = new WGTextArea(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), getBorderSize(), (ColoredString[])text.toArray(), textStyle, textWrapped, scrollBarColor, getParent());
    		}
    	}
    	return obj;
    }
    
    public void setTextLine(int index, ColoredString information) throws IndexOutOfBoundsException
    {
        text.set(index, information);
        resizer.resizeComps();
    }
    
    public void setTextLine(int index, String information) throws IndexOutOfBoundsException
    {
    	if(getCurrentTheme() == null)
    	{
    		return;
    	}
        text.set(index, new ColoredString(information, getCurrentTheme().getTextFont(), getCurrentTheme().getTextColor()));
        resizer.resizeComps();
    }
    
    public void addTextLine(ColoredString information) throws IndexOutOfBoundsException
    {
        text.add(information);
        resizer.resizeComps();
    }
    
    public void addTextLine(String information) throws IndexOutOfBoundsException
    {
    	if(getCurrentTheme() == null)
    	{
    		return;
    	}
        text.add(new ColoredString(information, getCurrentTheme().getTextFont(), getCurrentTheme().getTextColor()));
        resizer.resizeComps();
    }
    
    public void addTextLine(String information, Paint color) throws IndexOutOfBoundsException
    {
    	if(getCurrentTheme() == null)
    	{
    		return;
    	}
        text.add(new ColoredString(information, getCurrentTheme().getTextFont(), color));
        resizer.resizeComps();
    }
    
    public void removeAllText()
    {
        text.removeAll(text);
        resizer.resizeComps();
    }
    public void removeAllTextButKeepScroll()
    {
        text.removeAll(text);
    }
    
    public synchronized ColoredString getLongestString()
    {
        int large = 0;
        for(int i = 1 ; i < text.size() ; i++)
        {
            if(FXFontMetrics.stringWidth(text.get(i)) > FXFontMetrics.stringWidth(text.get(large)))
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
            return new ColoredString("", null, null);
        }
    }
    
    
    //Setters:
    public void setStringYOffset(double stringYOffset) {
        this.stringYOffset = stringYOffset;
    }
    
    
    //Getters:
    public ArrayList<ColoredString> getText() {
        return text;
    }
    public TextStyles getTextStyle() {
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

    public ArrayList<ColoredString> getFormatedText() {
        return formatedText;
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
            //Allow for the scroll to happen if there is a pane that controls this
            if(verticalScroll != null)
            {
                if(textWrapped)
                {
                    ArrayList<ColoredString> newList = new ArrayList<ColoredString>(text.size());
                    double objectWidth = getWidth();
                    //This requires that each line is split if too long:
                    for(int i = 0 ; i < text.size() ; i++)
                    {
                    	ColoredString str = text.get(i);
                    	ArrayList<ColoredString> splitLine = FXFontMetrics.wrapLine(str, objectWidth);
                    	for(int j = 0 ; j < splitLine.size() ; j++)
                    	{
                    		newList.add(splitLine.get(j));
                    	}
                    }
                    formatedText = newList;
                    verticalScroll.setScrollSpeed(formatedText.size());
                    verticalScroll.setUpScroll(formatedText);
                }
                else
                {
                    //Reset the width and height according to the size of the string:
                    verticalScroll.setScrollSpeed(text.size());
                    verticalScroll.setUpScroll(text);
    	            for(int i = 0 ; i < text.size(); i++)
    	            {
    	            	WGFontHelper.getFittedFontForWidth(getParent(), getWidth() - borderPadding, text.get(i), 100);
    	            }
                }
            }
            else
            {
	            for(int i = 0 ; i < text.size(); i++)
	            {
	            	WGFontHelper.getFittedFontForBox(getParent(), getWidth() - borderPadding, (double)(getHeight() - borderPadding) / text.size(), text.get(i), 100);
	            }
            }

            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                scrollBarColor = fixPaintBounds(scrollBarColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.SCROLL_BAR_COLOR));
            }
        	setResizing(false);
        }
    }
}
