/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import graphicsUtilities.WGAnimation.WGAAnimationManager;
import graphicsUtilities.WGAnimation.WGAColorAnimator;
import graphicsUtilities.WGAnimation.WGAPopupAnimationListener;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
    private boolean usesText = true;
    private double imageX;
    private double imageY;
    private double imageXScale;
    private double imageYScale;
    private int imagePlacementPeference;
    private int imageScalePeference;
    private String text;
    private Paint textColor;
    private Font textFont;
    private Image displayedImage;
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
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Color backgroundColor, Paint borderColor, Paint textColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textFont, backgroundColor, borderColor, textColor, parent);
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
    public WGButton(Rectangle2D bounds, float borderSize, String text, Font textFont, Color backgroundColor, Paint borderColor, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, WGColorHelper.getDarkerOrLighter((Color)backgroundColor), borderColor, parent);
        this.text = text;
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
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
     * This creates a baseline button that can and WILL resize itself. This will fully integrate it's resizing
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The text that goes on the button
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(Rectangle2D bounds, String text, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), text, theme.getTextFont(), Color.RED, theme.getBorderColor(), theme.getTextColor(), parent);
        setCurrentTheme(theme);
        resizer.resizeComps();
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
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String text, Font textFont, Color backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, text, textFont, backgroundColor, borderColor, textColor, parent, clickListener);
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
    public WGButton(Rectangle2D bounds, float borderSize, String text, Font textFont, Color backgroundColor, Paint borderColor, Paint textColor, Canvas parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(bounds, borderSize, text, textFont, backgroundColor, borderColor, textColor, parent);
        if(getParent() != null)
        {
            if(clickListener != null)
            {
                super.setClickListener(clickListener);
                getClickListener().setParentComponent(getParent());
                getClickListener().setParentObject(this);
                getParent().addEventHandler(MouseEvent.ANY, getClickListener());
                getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
                WestGraphics.add(this);
            }
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param text The text that goes on the button
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(Rectangle2D bounds, String text, Canvas parent, WGButtonListener clickListener, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), text, theme.getTextFont(), Color.RED, theme.getBorderColor(), theme.getTextColor(), parent);
        if(getParent() != null)
        {
            super.setClickListener(clickListener);
            getClickListener().setParentComponent(getParent());
            getClickListener().setParentObject(this);
            getParent().addEventHandler(MouseEvent.ANY, getClickListener());
            getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
            WestGraphics.add(this);
        }
        else
        {
            throw new WGNullParentException();
        }
        setCurrentTheme(theme);
        resizer.resizeComps();
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
    public WGButton(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, Image buttonImage, int imagePlacementPeference, int imageScalePeference, Color backgroundColor, Paint borderColor, Canvas parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, buttonImage, imagePlacementPeference, imageScalePeference, backgroundColor, borderColor, parent, clickListener);
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
    public WGButton(Rectangle2D bounds, float borderSize, Image buttonImage, int imagePlacementPeference, int imageScalePeference, Color backgroundColor, Paint borderColor, Canvas parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(bounds, borderSize, null, null, backgroundColor, borderColor, null, parent, clickListener);
        this.imagePlacementPeference = imagePlacementPeference;
        this.imageScalePeference = imageScalePeference;
        displayedImage = buttonImage;
        usesText = false;
        resizer.resizeComps();
    }
    /**
     * This will create a normal baseline WGButton, but can fully resize itself and will set up the WGClickListener before it adds it to the component so that it can be added as a parameter, and not after the fact
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param buttonImage The image that will be displayed on the button
     * @param imagePlacementPeference The preferred location of the image
     * @param imageScalePeference The preferred scale of the image
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGButton(Rectangle2D bounds, Image buttonImage, int imagePlacementPeference, int imageScalePeference, Canvas parent, WGButtonListener clickListener, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), null, null, Color.RED, theme.getBorderColor(), null, parent, clickListener);
        this.imagePlacementPeference = imagePlacementPeference;
        this.imageScalePeference = imageScalePeference;
        displayedImage = buttonImage;
        usesText = false;
        setCurrentTheme(theme);
        resizer.resizeComps();
    }
    
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
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        textColor = theme.getTextColor();
        textFont = theme.getTextFont();
        resizer.resizeComps();
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
        
        getParent().removeEventHandler(MouseEvent.ANY, getClickListener());
        getParent().removeEventHandler(ScrollEvent.ANY, getClickListener());
        WestGraphics.remove(this);
    }
    
    public void setClickListner(WGClickListener clickListener)
    {
        if(getClickListener() == null) //Check that this object has not been added already
        {
            WestGraphics.add(this);
        }
        getParent().removeEventHandler(MouseEvent.ANY, getClickListener());
        getParent().removeEventHandler(ScrollEvent.ANY, getClickListener());
        super.setClickListener(clickListener);
        clickListener.setParentComponent(getParent());
        clickListener.setParentObject(this);
        getParent().addEventHandler(MouseEvent.ANY, getClickListener());
        getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
    }
    private void setUpImage()
    {
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
        //Set up the x and y of the image, based on preferance:
        switch(imagePlacementPeference)
        {
            case IMAGE_CENTER_PLACEMENT:
                imageX = getX() + 0.5 * (getWidth() - displayedImage.getWidth() * imageXScale); //X + 1/2(width - imageWidth)
                imageY = getY() + 0.5 * (getHeight() - displayedImage.getHeight() * imageYScale); //Y + 1/2(height - imageHeight)
                break;
            case IMAGE_UPPER_LEFT_CORNER_PLACEMENT:
                imageX = getX();
                imageY = getY();
                break;
            case IMAGE_BOTTOM_RIGHT_CORNER_PLACEMENT:
                imageX = getX() + getWidth() - displayedImage.getWidth() * imageXScale; //X + Width - imageWidth
                imageY = getY() + getHeight() - displayedImage.getHeight() * imageYScale; //Y + Height - imageHeight
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
        //First we must test the colors to make sure that they are indeed Colors and not some other kind of paint:
        if(getBackgroundColor() instanceof Color && textColor instanceof Color && getBorderColor() instanceof Color)
        {
            parentAnimationManager = manager;
            animator = new WGAColorAnimator(tickMax, startingTick, (Color)getBackgroundColor());
            animator.addColor((Color)textColor);
            animator.addColor((Color)getBorderColor());
            popper = new WGAPopupAnimationListener(animator, this);
            parentAnimationManager.addTimer(new KeyFrame(Duration.millis(30), e -> popper.actionPerformed()));
        }
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

    public void setDisplayedImage(Image displayedImage) {
        this.displayedImage = displayedImage;
        resizer.resizeComps();
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
        resizer.resizeComps();
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

    public Paint getTextColor() {
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

    public Image getDisplayedImage() {
        return displayedImage;
    }
    
    
    //classes or Listeners:
    private class ButtonResizeListener extends WGDrawingObjectResizeListener
    {
        private ButtonResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeCompsWithoutDelay()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            if(!usesText) //If there is an image on the button instead of text:
            {
                if(displayedImage != null)
                {
                    setUpImage();
                }
            }
            else if(textFont != null)
            {
                textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, text, 100);
            }
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setHoverBackgroundColor(fixPaintBounds(getHoverBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.HOVER_BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
            }
        }
    }
}
