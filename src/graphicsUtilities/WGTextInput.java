/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import graphicsUtilities.WGAnimation.WGAAnimationManager;
import graphicsUtilities.WGAnimation.WGAStringAnimator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGTextInput extends WGDrawingObject
{
    private static final double CURSOR_WIDTH = 2.5;
    private String text;
    private Font textFont;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private Color cursorColor;
    private Color highlightColor;
    private Color BackgroundOnFocusColor;
    private TextResizeListener resizer;
    private WGTextInputClickListener clickListener;
    private WGTextInputKeyListener keyListener;
    private CursorAnimator cursorAnimator = new CursorAnimator();
    private WGAAnimationManager parentAnimationManager;
    private Rectangle2D.Double cursorBounds;
    private Rectangle2D.Double highlightBounds;
    private int cursorPosition = 0;
    private int highlightStart = 0;
    private int highlightEnd = 0;
    private boolean focused = false;
    private boolean cursorShown = false;
    private boolean highlightShown = false;
    private boolean beingTypedOn = false;
    private boolean shiftHeld = false;
    
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
    public WGTextInput(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Color cursorColor, Color highlightColor, Component parent, WGAAnimationManager parentAnimationManager) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.text = "";
        this.textFont = textFont;
        this.borderColor = borderColor;
        this.textColor = textColor;
        this.cursorColor = cursorColor;
        this.highlightColor = highlightColor;
        setBackgroundColor(backgroundColor);
        if(getParent() != null)
        {
            resizer = new TextResizeListener(xPercent, yPercent, widthPercent, heightPercent);
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            this.clickListener = new WGTextInputClickListener(this, parent);
            getParent().addMouseListener(this.clickListener);
            getParent().addMouseMotionListener(this.clickListener);
            clickListener.setOriginalBackgroundColor(backgroundColor);
            keyListener = new WGTextInputKeyListener(this);
            getParent().addKeyListener(keyListener);
        }
        else
        {
            throw new WGNullParentException();
        }
        this.parentAnimationManager = parentAnimationManager;
        this.parentAnimationManager.addTimer(600, cursorAnimator);
        setUpCursorBounds();
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
    
    

    //Setters
    public void setText(String text) {
        this.text = text;
        beingTypedOn = true;
        cursorAnimator.reset();
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
        resizer.resizeComps();
    }

    public void setBackgroundColor(Color backgroundColor) 
    {
        this.backgroundColor = backgroundColor;
        BackgroundOnFocusColor = WGColorHelper.getDarkerOrLighter(backgroundColor, 1, WGColorHelper.PREFERRANCE_COLOR_DARKER);
        if(clickListener != null)
        {
            clickListener.setOriginalBackgroundColor(backgroundColor);
        }
        
    }
    public void setUpCursorBounds()
    {
        FontMetrics textFM = getParent().getFontMetrics(textFont);
        double totalStringWidth = textFM.stringWidth(text.substring(0, text.length()));
        double cursorX = getX() + (getWidth() - totalStringWidth) / 2.0;
        if(cursorPosition > text.length())
        {
            cursorX += totalStringWidth;
        }
        else
        {
            cursorX += textFM.stringWidth(text.substring(0, cursorPosition));
        }
        double cursorWidth = CURSOR_WIDTH;
        double cursorHeight = textFM.getHeight();
        double cursorY = getY() + (getHeight() - cursorHeight) / 2;
                                
        cursorBounds = new Rectangle2D.Double(cursorX, cursorY, cursorWidth, cursorHeight);
    }
    public void setUpHighlightBounds()
    {
        //Basics
        FontMetrics textFM = getParent().getFontMetrics(textFont);
        double totalStringWidth = textFM.stringWidth(text.substring(0, text.length()));
        
        //Find the start X
        double highlightStartX = getX() + (getWidth() - totalStringWidth) / 2.0;
        if(highlightStart > text.length())
        {
            highlightStartX += totalStringWidth;
        }
        else
        {
            highlightStartX += textFM.stringWidth(text.substring(0, highlightStart));
        }
        
        //The end X
        double highlightEndX = getX() + (getWidth() - totalStringWidth) / 2.0;
        if(highlightEnd > text.length())
        {
            highlightEndX += totalStringWidth;
        }
        else
        {
            highlightEndX += textFM.stringWidth(text.substring(0, highlightEnd));
        }
        
        //The width, height, and Y
        double highlightWidth = Math.abs(highlightEndX - highlightStartX);
        double highlightHeight = textFM.getHeight();
        double highlightY = getY() + (getHeight() - highlightHeight) / 2;
        double highlightX = (highlightStartX < highlightEndX) ? highlightStartX : highlightEndX;
                    
        //Set it up:
        highlightBounds = new Rectangle2D.Double(highlightX, highlightY, highlightWidth, highlightHeight);
    }

    //Setters:
    public void setBackgroundColorNotClickListener(Color backgroundColor) 
    {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
        this.cursorShown = focused;
    }

    public void setCursorColor(Color cursorColor) {
        this.cursorColor = cursorColor;
    }

    public void setCursorPosition(int cursorPosition) {
        this.cursorPosition = cursorPosition;
        beingTypedOn = true;
        cursorAnimator.reset();
        setUpCursorBounds();
    }

    public void setHighlightShown(boolean highlightShown) {
        this.highlightShown = highlightShown;
        setUpHighlightBounds();
    }

    public void setHighlightStart(int highlightStart) {
        this.highlightStart = highlightStart;
    }

    public void setHighlightEnd(int highlightEnd) {
        this.highlightEnd = highlightEnd;
    }

    public void setShiftHeld(boolean shiftHeld) {
        this.shiftHeld = shiftHeld;
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

    public Color getCursorColor() {
        return cursorColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public Color getBackgroundOnFocusColor() {
        return BackgroundOnFocusColor;
    }

    public boolean isFocused() {
        return focused;
    }

    public Rectangle2D.Double getCursorBounds() {
        return cursorBounds;
    }

    public Rectangle2D.Double getHighlightBounds() {
        return highlightBounds;
    }

    public boolean isCursorShown() {
        return cursorShown;
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public int getHighlightStart() {
        return highlightStart;
    }

    public int getHighlightEnd() {
        return highlightEnd;
    }

    public boolean isHighlightShown() {
        return highlightShown;
    }

    public boolean isShiftHeld() {
        return shiftHeld;
    }
    
    
    //classes or Listeners:
    private class TextResizeListener extends WGDrawingObjectResizeListener
    {
        private TextResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the input
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
            setUpCursorBounds();
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
    private class CursorAnimator implements ActionListener
    {
        private final int tickMax = 0;
        private int tick = 0;
        public void actionPerformed(ActionEvent e) 
        {
            if(beingTypedOn)
            {
                if(tick == tickMax)
                {
                    beingTypedOn = false;
                    tick = 0;
                    return;
                }
                tick++;
            }
            else
            {
                cursorShown = !cursorShown;
            }
        }
        public void reset()
        {
            cursorShown = true;
            tick = 0;
        }
    }
}
