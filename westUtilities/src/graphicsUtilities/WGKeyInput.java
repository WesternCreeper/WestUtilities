/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import graphicsUtilities.WGAnimation.WGAAnimationManager;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author Westley
 */
public class WGKeyInput extends WGBox
{
    private String text;
    private Font textFont;
    private Paint textColor;
    private Paint backgroundOnFocusColor;
    private WGKeyInputClickListener clickListener;
    private WGKeyInputKeyListener keyListener;
    private boolean focused = false;
    private boolean shiftHeld = false;
    
    /**
     * This will create a normal baseline WGKeyInput, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(Rectangle2D bounds, double borderSize, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, WGColorHelper.getDarkerOrLighter((Color)backgroundColor), borderColor, parent);
        this.text = "";
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new KeyResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            resizer.resizeComps();
            this.clickListener = new WGKeyInputClickListener(this, parent);
            clickListener.setOriginalBackgroundColor(backgroundColor);
            setClickListener(clickListener);
            keyListener = new WGKeyInputKeyListener(this);
            getParent().addEventHandler(KeyEvent.ANY, keyListener);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * This will create a normal baseline WGKeyInput, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, textFont, backgroundColor, borderColor, textColor, parent);
    }
    /**
     * This will create a normal baseline WGKeyInput, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(Rectangle2D bounds, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), theme.getTextFont(), theme.getBackgroundColor(), theme.getBorderColor(), theme.getTextColor(), parent);
        setCurrentTheme(theme);
    }
    
    
    /**
     * This will create a normal baseline WGKeyInput, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @param textClickListener The click listener, overrides the basic version
     * @param textKeyListener The key listener, overrides the basic version
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(Rectangle2D bounds, double borderSize, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGKeyInputClickListener textClickListener, WGKeyInputKeyListener textKeyListener) throws WGNullParentException
    {
        super(borderSize, backgroundColor, WGColorHelper.getDarkerOrLighter((Color)backgroundColor), borderColor, parent);
        this.text = "";
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new KeyResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            resizer.resizeComps();
            this.clickListener = textClickListener;
            clickListener.setParentObject(this);
            clickListener.setParentComponent(parent);
            clickListener.setOriginalBackgroundColor(backgroundColor);
            setClickListener(clickListener);
            keyListener = textKeyListener;
            keyListener.setParent(this);
            getParent().addEventHandler(KeyEvent.ANY, keyListener);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * This will create a normal baseline WGKeyInput, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @param textClickListener The click listener, overrides the basic version
     * @param textKeyListener The key listener, overrides the basic version
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(double xPercent, double yPercent, double widthPercent, double heightPercent, double borderSize, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGKeyInputClickListener textClickListener, WGKeyInputKeyListener textKeyListener) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, textFont, backgroundColor, borderColor, textColor, parent, textClickListener, textKeyListener);
    }
    /**
     * This will create a normal baseline WGKeyInput, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @param textClickListener The click listener, overrides the basic version
     * @param textKeyListener The key listener, overrides the basic version
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(Rectangle2D bounds, Canvas parent, WGKeyInputClickListener textClickListener, WGKeyInputKeyListener textKeyListener, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), theme.getTextFont(), theme.getBackgroundColor(), theme.getBorderColor(), theme.getTextColor(), parent, textClickListener, textKeyListener);
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
    public WGDrawingObject cloneObject() throws WGNullParentException
    {
    	WGDrawingObject obj;

    	WGKeyInputClickListener clickListener = null;
		if(getClickListener() != null)
		{
			clickListener = (WGKeyInputClickListener)getClickListener();
		}
    	if(getCurrentTheme() != null)
    	{
			obj = new WGKeyInput(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), getParent(), clickListener, keyListener, getCurrentTheme());
    	}
    	else
    	{
			obj = new WGKeyInput(new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent()), getBorderSize(), textFont, getBackgroundColor(), getBorderColor(), textColor, getParent(), clickListener, keyListener);
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
        
        WGKeyInputKeyListener keyer = getKeyListener();
        getParent().addEventHandler(KeyEvent.ANY, keyer);

        WestGraphics.remove(this);
        if(getDragAndDropBar() != null)
        {
        	getDragAndDropBar().removeListeners();
        }
    }
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        resizer.resizeComps();
    }
    
    

    //Setters
    public void setText(String text) {
        this.text = text;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
        ((KeyResizeListener)resizer).setUpFont();
    }

    public void setBackgroundColor(Paint backgroundColor) 
    {
        super.setBackgroundColor(backgroundColor);
        if(backgroundColor instanceof Color)
        {
            backgroundOnFocusColor = WGColorHelper.getDarkerOrLighter((Color)backgroundColor, 1, WGColorHelper.PREFERRANCE_COLOR_DARKER);
        }
        else
        {
            backgroundOnFocusColor = backgroundColor;
        }
        
        if(clickListener != null)
        {
            clickListener.setOriginalBackgroundColor(backgroundColor);
        }
        
    }

    //Setters:
    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
    }

    public void setShiftHeld(boolean shiftHeld) {
        this.shiftHeld = shiftHeld;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
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
    
    public boolean isShiftHeld() {
        return shiftHeld;
    }

    public boolean isFocused() {
        return focused;
    }

    @Override
    public WGKeyInputClickListener getClickListener() {
        return clickListener;
    }

    public WGKeyInputKeyListener getKeyListener() {
        return keyListener;
    }

    public Paint getBackgroundOnFocusColor() {
        return backgroundOnFocusColor;
    }
    
    
    //classes or Listeners:
    private class KeyResizeListener extends WGDrawingObjectResizeListener
    {
        private KeyResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
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
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth + parentX);
            setY(getYPercent() * parentHeight + parentY);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            setUpFont();
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setHoverBackgroundColor(fixPaintBounds(getHoverBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.HOVER_BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
                backgroundOnFocusColor = fixPaintBounds(backgroundOnFocusColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.FOCUSED_BACKGROUND_COLOR));
            }
        	setResizing(false);
        }
        public void setUpFont()
        {
        	Platform.runLater(() -> {
	            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the input
	            textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
        	});
        }
    }
}
