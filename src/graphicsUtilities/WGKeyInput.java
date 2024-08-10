/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import graphicsUtilities.WGAnimation.WGAAnimationManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGKeyInput extends WGBox
{
    private String text;
    private Font textFont;
    private Color textColor;
    private Color BackgroundOnFocusColor;
    private WGKeyInputClickListener clickListener;
    private WGKeyInputKeyListener keyListener;
    private boolean focused = false;
    private boolean shiftHeld = false;
    
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param cursorColor The color of the cursor
     * @param highlightColor The color of the highlight
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(Rectangle2D.Double bounds, float borderSize, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Color cursorColor, Color highlightColor, Component parent, WGAAnimationManager parentAnimationManager) throws WGNullParentException
    {
        super(borderSize, backgroundColor, borderColor, parent);
        this.text = "";
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new KeyResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            this.clickListener = new WGKeyInputClickListener(this, parent);
            getParent().addMouseListener(this.clickListener);
            getParent().addMouseMotionListener(this.clickListener);
            clickListener.setOriginalBackgroundColor(backgroundColor);
            keyListener = new WGKeyInputKeyListener(this);
            getParent().addKeyListener(keyListener);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param cursorColor The color of the cursor
     * @param highlightColor The color of the highlight
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Color cursorColor, Color highlightColor, Component parent, WGAAnimationManager parentAnimationManager) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, textFont, backgroundColor, borderColor, textColor, cursorColor, highlightColor, parent, parentAnimationManager);
    }
    
    
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param cursorColor The color of the cursor
     * @param highlightColor The color of the highlight
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @param textClickListener The click listener, overrides the basic version
     * @param textKeyListener The key listener, overrides the basic version
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(Rectangle2D.Double bounds, float borderSize, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Color cursorColor, Color highlightColor, Component parent, WGAAnimationManager parentAnimationManager, WGKeyInputClickListener textClickListener, WGKeyInputKeyListener textKeyListener) throws WGNullParentException
    {
        super(borderSize, backgroundColor, borderColor, parent);
        this.text = "";
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new KeyResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            this.clickListener = textClickListener;
            clickListener.setParentObject(this);
            clickListener.setParentComponent(parent);
            getParent().addMouseListener(this.clickListener);
            getParent().addMouseMotionListener(this.clickListener);
            clickListener.setOriginalBackgroundColor(backgroundColor);
            keyListener = textKeyListener;
            keyListener.setParent(this);
            getParent().addKeyListener(keyListener);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param cursorColor The color of the cursor
     * @param highlightColor The color of the highlight
     * @param parent The component that the text input is on, and is used to determine how big this object is
     * @param parentAnimationManager The needed animation manager to get the text cursor to blink
     * @param textClickListener The click listener, overrides the basic version
     * @param textKeyListener The key listener, overrides the basic version
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGKeyInput(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Color cursorColor, Color highlightColor, Component parent, WGAAnimationManager parentAnimationManager, WGKeyInputClickListener textClickListener, WGKeyInputKeyListener textKeyListener) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, textFont, backgroundColor, borderColor, textColor, cursorColor, highlightColor, parent, parentAnimationManager, textClickListener, textKeyListener);
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
        
        WGKeyInputKeyListener keyer = getKeyListener();
        WGKeyInputClickListener clicker = getClickListener();
        getParent().removeKeyListener(keyer);
        getParent().removeMouseListener(clicker);
        getParent().removeMouseMotionListener(clicker);
    }
    
    

    //Setters
    public void setText(String text) {
        this.text = text;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
        ((KeyResizeListener)resizer).setUpFont();
    }

    public void setBackgroundColor(Color backgroundColor) 
    {
        super.setBackgroundColor(backgroundColor);
        BackgroundOnFocusColor = WGColorHelper.getDarkerOrLighter(backgroundColor, 1, WGColorHelper.PREFERRANCE_COLOR_DARKER);
        if(clickListener != null)
        {
            clickListener.setOriginalBackgroundColor(backgroundColor);
        }
        
    }

    //Setters:
    public void setBackgroundColorNotClickListener(Color backgroundColor) 
    {
        super.setBackgroundColor(backgroundColor);
    }

    public void setTextColor(Color textColor) {
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

    public Color getTextColor() {
        return textColor;
    }
    
    public boolean isShiftHeld() {
        return shiftHeld;
    }

    public boolean isFocused() {
        return focused;
    }

    public WGKeyInputClickListener getClickListener() {
        return clickListener;
    }

    public WGKeyInputKeyListener getKeyListener() {
        return keyListener;
    }

    public Color getBackgroundOnFocusColor() {
        return BackgroundOnFocusColor;
    }
    
    
    //classes or Listeners:
    private class KeyResizeListener extends WGDrawingObjectResizeListener
    {
        private KeyResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            setUpFont();
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
        public void setUpFont()
        {
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the input
            textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
        }
    }
}
