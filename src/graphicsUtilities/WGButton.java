/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import graphicsUtilities.WGAnimation.WGAAnimationManager;
import graphicsUtilities.WGAnimation.WGAColorAnimator;
import graphicsUtilities.WGAnimation.WGAPopupAnimationListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.Component;
import java.awt.image.BufferedImage;

/**
 *
 * @author Westley
 */
public class WGButton extends WGBox
{
    public final static int IMAGE_CENTER_PLACEMENT = 0;
    public final static int IMAGE_UPPER_LEFT_CORNER_PLACEMENT = 1;
    public final static int IMAGE_BOTTOM_RIGHT_CORNER_PLACEMENT = 4;
    public final static int IMAGE_NORMAL_SCALE = 0;
    public final static int IMAGE_SCALE_TO_FIT = 1;
    private double imageX;
    private double imageY;
    private double imageXScale;
    private double imageYScale;
    private int imagePlacementPeference;
    private int imageScalePeference;
    private String text;
    private Color textColor;
    private Font textFont;
    private BufferedImage displayedImage;
    private WGAColorAnimator animator;
    private WGAPopupAnimationListener popper;
    private WGAAnimationManager parentAnimationManager;
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
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textFont, backgroundColor, borderColor, textColor, parent);
    }
    /**
     * This creates a baseline button that can and WILL resize itself. This will fully integrate it's resizing
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The text that goes on the button
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(Rectangle2D.Double bounds, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, borderColor, parent);
        this.text = text;
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
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
     * @param text The text that goes on the button
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textFont, backgroundColor, borderColor, textColor, parent, clickListener);
    }
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param text The text that goes on the button
     * @param textFont The font that will draw the text
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(Rectangle2D.Double bounds, float borderSize, String text, Font textFont, Color backgroundColor, Color borderColor, Color textColor, Component parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(bounds, borderSize, text, textFont, backgroundColor, borderColor, textColor, parent);
        if(getParent() != null)
        {
            super.setClickListener(clickListener);
            getClickListener().setParentComponent(getParent());
            getClickListener().setParentObject(this);
            ((WGButtonListener)getClickListener()).setOriginalBackgroundColor(getBackgroundColor());
            getParent().addMouseListener(getClickListener());
            getParent().addMouseMotionListener((WGButtonListener)getClickListener());
            getParent().addMouseWheelListener((WGButtonListener)getClickListener());
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
     * @param buttonImage The image that will be displayed on the button
     * @param imagePlacementPeference The preferred location of the image
     * @param imageScalePeference The preferred scale of the image
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, BufferedImage buttonImage, int imagePlacementPeference, int imageScalePeference, Color backgroundColor, Color borderColor, Component parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, buttonImage, imagePlacementPeference, imageScalePeference, backgroundColor, borderColor, parent, clickListener);
    }
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param buttonImage The image that will be displayed on the button
     * @param imagePlacementPeference The preferred location of the image
     * @param imageScalePeference The preferred scale of the image
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(Rectangle2D.Double bounds, float borderSize, BufferedImage buttonImage, int imagePlacementPeference, int imageScalePeference, Color backgroundColor, Color borderColor, Component parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(bounds, borderSize, null, null, backgroundColor, borderColor, null, parent, clickListener);
        this.imagePlacementPeference = imagePlacementPeference;
        this.imageScalePeference = imageScalePeference;
        displayedImage = buttonImage;
        resizer.resizeComps();
    }
    
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
    public void setClickListner(WGClickListener clickListener)
    {
        super.setClickListener(clickListener);
        clickListener.setParentComponent(getParent());
        clickListener.setParentObject(this);
        ((WGButtonListener)clickListener).setOriginalBackgroundColor(getBackgroundColor());
        getParent().addMouseListener(clickListener);
        getParent().addMouseMotionListener((WGButtonListener)clickListener);
        getParent().addMouseWheelListener((WGButtonListener)clickListener);
    }
    private void setUpImage()
    {
        //Set up the x and y of the image, based on preferance:
        switch(imagePlacementPeference)
        {
            case IMAGE_CENTER_PLACEMENT:
                imageX = getX() + 0.5 * (getWidth() - displayedImage.getWidth()); //X + 1/2(width - imageWidth)
                imageY = getY() + 0.5 * (getHeight() - displayedImage.getHeight()); //Y + 1/2(height - imageHeight)
                break;
            case IMAGE_UPPER_LEFT_CORNER_PLACEMENT:
                imageX = getX();
                imageY = getY();
                break;
            case IMAGE_BOTTOM_RIGHT_CORNER_PLACEMENT:
                imageX = getX() + getWidth() - displayedImage.getWidth(); //X + Width - imageWidth
                imageY = getY() + getHeight() - displayedImage.getHeight(); //Y + Height - imageHeight
                break;
        }
        //Set up the width and height of the image based on the scale preferrence
        switch(imageScalePeference)
        {
            case IMAGE_NORMAL_SCALE:
                imageXScale = 1;
                imageYScale = 1;
                break;
            case IMAGE_SCALE_TO_FIT:
                imageXScale = (double)getWidth() / displayedImage.getWidth();
                imageYScale = (double)getHeight() / displayedImage.getHeight();
                break;
        }
    }
    
    /**
     * Use this function to setup any internal animation functions and the animator if one wants to use that as a shortcut way to get a color animator for all of the colors
     * @param tickMax The maximum tick, sets the length of the animation
     * @param startingTick The starting tick, tells where in the animation to start. Some functions don't work properly if this is not 0
     * @param manager The AnimationManager that the parent component uses. This is only to make sure that the animations are set up and ready to roll. This will NEVER be used to stop all timers, but MAY be used to start all timers
     */
    public void setUpColorAnimator(int tickMax, int startingTick, WGAAnimationManager manager)
    {
        parentAnimationManager = manager;
        animator = new WGAColorAnimator(tickMax, startingTick, getBackgroundColor());
        animator.addColor(textColor);
        animator.addColor(getBorderColor());
        popper = new WGAPopupAnimationListener(animator, getParent(), this);
        parentAnimationManager.addTimer(30, popper);
    }
    
    /**
     * This pops the component out of existance. Starts all timers on the parent AnimationManager
     * @throws WGNotSetUpException 
     */
    public void popOutOfExistance() throws WGNotSetUpException
    {
        if(popper != null)
        {
            popper.setPopOut(true);
            popper.reset();
            parentAnimationManager.startAllTimers();
        }
        else
        {
            throw new WGNotSetUpException();
        }
    }
    
    /**
     * This pops the component into existence.  Starts all timers on the parent AnimationManager
     * @throws WGNotSetUpException 
     */
    public void popIntoExistance() throws WGNotSetUpException
    {
        if(popper != null)
        {
            popper.setPopOut(false);
            popper.reset();
            parentAnimationManager.startAllTimers();
        }
        else
        {
            throw new WGNotSetUpException();
        }
    }
    

    //Setters
    public void setText(String text) {
        if(text == null)
        {
            return;
        }
        this.text = text;
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        if(textFont == null)
        {
            return;
        }
        this.textFont = textFont;
        resizer.resizeComps();
    }

    public void setBackgroundColor(Color backgroundColor) 
    {
        super.setBackgroundColor(backgroundColor);
        if(getClickListener() != null)
        {
            ((WGButtonListener)getClickListener()).setOriginalBackgroundColor(backgroundColor);
        }
    }

    public void setBackgroundColorNotClickListener(Color backgroundColor) 
    {
        super.setBackgroundColor(backgroundColor);
    }

    public void setDisplayedImage(BufferedImage displayedImage) {
        this.displayedImage = displayedImage;
        resizer.resizeComps();
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setImagePlacementPeference(int imagePlacementPeference) {
        this.imagePlacementPeference = imagePlacementPeference;
        resizer.resizeComps();
    }

    public void setImageScalePeference(int imageScalePeference) {
        this.imageScalePeference = imageScalePeference;
        resizer.resizeComps();
    }
    
    @Override
    public void setX(double x)
    {
        super.setX(x);
        if(displayedImage != null) //If there is an image on the button instead of text:
        {
            setUpImage();
        }
    }
    
    @Override
    public void setY(double y)
    {
        super.setY(y);
        if(displayedImage != null) //If there is an image on the button instead of text:
        {
            setUpImage();
        }
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

    public WGAColorAnimator getAnimator() {
        return animator;
    }

    public double getImageX() {
        return imageX;
    }

    public double getImageY() {
        return imageY;
    }

    public double getImageXScale() {
        return imageXScale;
    }

    public double getImageYScale() {
        return imageYScale;
    }

    public BufferedImage getDisplayedImage() {
        return displayedImage;
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
            if(displayedImage != null) //If there is an image on the button instead of text:
            {
                setUpImage();
            }
            else if(textFont != null) 
            {
                textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
            }
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
