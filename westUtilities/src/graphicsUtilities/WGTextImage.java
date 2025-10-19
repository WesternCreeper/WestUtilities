/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.FXFontMetrics;

/**
 *
 * @author Westley
 */
public class WGTextImage extends WGDrawingObject
{
    public static final int RESIZE_OPTION_NONE = 0;
    public static final int RESIZE_OPTION_ASPECT_SCALED = 1;
    public static final int RESIZE_OPTION_FULL_RESIZE = 2;
    public static final int TEXT_LOWER_LEFT_CORNER = 0;
    public static final int TEXT_UPPER_LEFT_CORNER = 1;
    public static final int TEXT_UPPER_RIGHT_CORNER = 2;
    public static final int TEXT_LOWER_RIGHT_CORNER = 3;
    private Image displayImage;
    private String imageText;
    private Font textFont;
    private Paint textColor;
    private double imageOffSetX;
    private double imageOffSetY;
    private double textX;
    private double textY;
    private double textXSizePercent;
    private double textYSizePercent;
    private double imageXScale;
    private double imageYScale;
    private int textPosition;
    private int imageResizeOption;
    
    /**
     * This creates a standard image with text, in the lower left corner, and makes sure to stretch the image to fit the box given.
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param displayedImage The image to be displayed, this will be the bulk of the object
     * @param imageResizeOption This determines if the image is to be resized or not.
     * @param text The text placed on the image
     * @param textFont The font that will draw the text
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextImage(double xPercent, double yPercent, double widthPercent, double heightPercent, Image displayedImage, int imageResizeOption, String text, Font textFont, Paint textColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), displayedImage, imageResizeOption, text, textFont, textColor, parent);
    }
    /**
     * This creates a standard image with text, in the lower left corner, and makes sure to stretch the image to fit the box given.
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param displayedImage The image to be displayed, this will be the bulk of the object
     * @param imageResizeOption This determines if the image is to be resized or not.
     * @param text The text placed on the image
     * @param textFont The font that will draw the text
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextImage(Rectangle2D bounds, Image displayedImage, int imageResizeOption, String text, Font textFont, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, 0, parent);
        this.displayImage = displayedImage;
        imageText = text;
        this.textFont = textFont;
        this.textColor = textColor;
        this.imageResizeOption = imageResizeOption;
        textPosition = TEXT_LOWER_LEFT_CORNER;
        textXSizePercent = 0.25;
        textYSizePercent = 0.25;
        if(getParent() != null)
        {
            resizer = new ImageResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
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
     * This creates a standard image with text, in the lower left corner, and makes sure to stretch the image to fit the box given.
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param displayedImage The image to be displayed, this will be the bulk of the object
     * @param imageResizeOption This determines if the image is to be resized or not.
     * @param text The text placed on the image
     * @param textPosition This is the position of the text in relation to the image itself.
     * @param textXSizePercent The percent of the image that the text will take up, in the horizontal direction
     * @param textYSizePercent The percent of the image that the text will take up, in the vertical direction
     * @param textFont The font that will draw the text
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextImage(double xPercent, double yPercent, double widthPercent, double heightPercent, Image displayedImage, int imageResizeOption, String text, int textPosition, double textXSizePercent, double textYSizePercent, Font textFont, Paint textColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), displayedImage, imageResizeOption, text, textPosition, textXSizePercent, textYSizePercent, textFont, textColor, parent);
    }
    /**
     * This creates a standard image with text, in the lower left corner, and makes sure to stretch the image to fit the box given.
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param displayedImage The image to be displayed, this will be the bulk of the object
     * @param imageResizeOption This determines if the image is to be resized or not.
     * @param text The text placed on the image
     * @param textPosition This is the position of the text in relation to the image itself.
     * @param textXSizePercent The percent of the image that the text will take up, in the horizontal direction
     * @param textYSizePercent The percent of the image that the text will take up, in the vertical direction
     * @param textFont The font that will draw the text
     * @param textColor The color of the text
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextImage(Rectangle2D bounds, Image displayedImage, int imageResizeOption, String text, int textPosition, double textXSizePercent, double textYSizePercent, Font textFont, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, 0, parent);
        this.displayImage = displayedImage;
        imageText = text;
        this.textFont = textFont;
        this.textColor = textColor;
        this.imageResizeOption = imageResizeOption;
        this.textPosition = textPosition;
        this.textXSizePercent = textXSizePercent;
        this.textYSizePercent = textYSizePercent;
        if(getParent() != null)
        {
            resizer = new ImageResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
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
     * This creates a standard image with text, in the lower left corner, and makes sure to stretch the image to fit the box given.
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param displayedImage The image to be displayed, this will be the bulk of the object
     * @param imageResizeOption This determines if the image is to be resized or not.
     * @param text The text placed on the image
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGTextImage(Rectangle2D bounds, Image displayedImage, int imageResizeOption, String text, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, displayedImage, imageResizeOption, text, theme.getTextPosition(), theme.getTextXSizePercent(), theme.getTextYSizePercent(), theme.getTextFont(), theme.getTextColor(), parent);
        setCurrentTheme(theme);
    }
    
    @Override
    public Rectangle2D getBounds() 
    {
        Rectangle2D bounds = new Rectangle2D(getX(), getY(), getWidth(), getHeight());
        return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeComps();
    }
    public void setBounds(Rectangle2D newBounds)
    {
        resizer.setBounds(newBounds);
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
    }
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        this.textPosition = theme.getTextPosition();
        this.textXSizePercent = theme.getTextXSizePercent();
        this.textYSizePercent = theme.getTextYSizePercent();
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        resizer.resizeComps();
    }
    
    
    //Getters:
    public Image getDisplayImage() {
        return displayImage;
    }

    public String getImageText() {
        return imageText;
    }

    public Font getTextFont() {
        return textFont;
    }

    public Paint getTextColor() {
        return textColor;
    }

    public double getTextX() {
        return textX;
    }

    public double getTextY() {
        return textY;
    }

    public double getImageXScale() {
        return imageXScale;
    }

    public double getImageYScale() {
        return imageYScale;
    }

    public double getImageOffSetX() {
        return imageOffSetX;
    }

    public double getImageOffSetY() {
        return imageOffSetY;
    }
    
    
    //Setters:
    public void setDisplayImage(Image displayImage) {
        this.displayImage = displayImage;
        resizer.resizeComps();
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
        resizer.resizeComps();
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
        resizer.resizeComps();
    }

    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
        resizer.resizeComps();
    }
    
    
    //classes or Listeners:
    private class ImageResizeListener extends WGDrawingObjectResizeListener
    {
        private ImageResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
        	Platform.runLater(() -> {
	            if(displayImage == null) //If there is no image, then save time by not calculating anything
	            {
	                return;
	            }
	            //Find the parent width and height so that the x/y can be scaled accordingly
	            double parentWidth = getParent().getWidth();
	            double parentHeight = getParent().getHeight();
	            //Set up the x, y, width, and height components based on the percentages given and the parent's size
	            setX(getXPercent() * parentWidth);
	            setY(getYPercent() * parentHeight);
	            setWidth(getWidthPercent() * parentWidth);
	            setHeight(getHeightPercent() * parentHeight);
	            
	            //Resize the image as applicable
	            double width = (double)getWidth();
	            double height = (double)getHeight();
	            imageOffSetX = 0;
	            imageOffSetY = 0;
	            switch(imageResizeOption)
	            {
	                case RESIZE_OPTION_FULL_RESIZE:
	                    imageXScale = (double)getWidth() / displayImage.getWidth();
	                    imageYScale = (double)getHeight() / displayImage.getHeight();
	                    break;
	                case RESIZE_OPTION_ASPECT_SCALED:
	                    imageXScale = (double)getWidth() / displayImage.getWidth();
	                    imageYScale = (double)getHeight() / displayImage.getHeight();
	                    if(imageYScale > imageXScale)
	                    {
	                        imageYScale = imageXScale;
	                    }
	                    else
	                    {
	                        imageXScale = imageYScale;
	                    }
	                    imageOffSetX = (width - displayImage.getWidth() * imageXScale) / 2;
	                    imageOffSetY = (height - displayImage.getHeight() * imageYScale) / 2;
	                    width = displayImage.getWidth();
	                    height = displayImage.getHeight();
	                    break;
	                case RESIZE_OPTION_NONE:
	                    imageXScale = 1;
	                    imageYScale = 1;
	                    imageOffSetX = (width - displayImage.getWidth()) / 2;
	                    imageOffSetY = (height - displayImage.getHeight()) / 2;
	                    width = displayImage.getWidth();
	                    height = displayImage.getHeight();
	                    break;
	            }
	            
	            //Now make sure the image is in the correct location:
	            textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), width * textXSizePercent, height * textYSizePercent, imageText, 100);
	            FXFontMetrics textFM = new FXFontMetrics(textFont); 
	            
	            switch(textPosition)
	            {
	                case TEXT_LOWER_LEFT_CORNER:
	                    //Place in the lower left corner, relative to the position of the object:
	                    textX = 0;
	                    textY = height - textFM.getHeight(imageText);
	                    break;
	                case TEXT_UPPER_LEFT_CORNER:
	                    //Place in the upper left corner, relative to the position of the object:
	                    textX = 0;
	                    textY = 0;
	                    break;
	                case TEXT_UPPER_RIGHT_CORNER:
	                    //Place in the upper left corner, relative to the position of the object:
	                    textX = width - textFM.stringWidth(imageText);
	                    textY = 0;
	                    break;
	                case TEXT_LOWER_RIGHT_CORNER:
	                    //Place in the upper left corner, relative to the position of the object:
	                    textX = width - textFM.stringWidth(imageText);
	                    textY = height - textFM.getHeight(imageText);
	                    break;
	            }
	
	            //Now fix the colors of this object:
	            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
	            {
	                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
	            }
        	});
        }
    }
}
