/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author Westley
 * This defines a loading bar, which can then be drawn using the WestGraphics method. This is used for drawing and bound creation!!
 */
public class WGLoadingBar extends WGBox
{
    private String originalTitle;
    private String title;
    private boolean showPercentage; 
    private Font titleFont;
    private double percentFilled;
    private boolean isHorizontal;
    private Paint titleColor;
    private Paint barColor;
    /**
     * The most complete version of the WGLoadingBar object, which allows complete control over all of the options
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param title The text written on the bar
     * @param showPercentage The option for a percentage to be written after the title
     * @param titleFont The Font object for the title String, a FontMeterics is already supplied by the function based on the Font, no need to give such a thing here
     * @param percentFilled The percent the bar should be filled
     * @param isHorizontal The option to make the bar display everything horizontal or vertical. The bounds are NOT changed based on this option, so make sure the bar can fit the text!
     * @param barBackgroundColor The background Paint of the bar. This is the back of the bar, thus this is the color that represents the percent incomplete.
     * @param barBorderColor The color of the borders around the bar
     * @param titleColor The color of the text that is written on the bar
     * @param barColor The color of the percentage that is filled on the bar. This is the representation of the percent complete
     * @param parent The parent component upon which this object is being drawn on
     */
    public WGLoadingBar(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String title, boolean showPercentage, Font titleFont, double percentFilled, boolean isHorizontal, Paint barBackgroundColor, Paint barBorderColor, Paint titleColor, Paint barColor, Canvas parent)
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, title, showPercentage, titleFont, percentFilled, isHorizontal, barBackgroundColor, barBorderColor, titleColor, barColor, parent);
    }
    /**
     * The most complete version of the WGLoadingBar object, which allows complete control over all of the options
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param title The text written on the bar
     * @param showPercentage The option for a percentage to be written after the title
     * @param titleFont The Font object for the title String, a FontMeterics is already supplied by the function based on the Font, no need to give such a thing here
     * @param percentFilled The percent the bar should be filled
     * @param isHorizontal The option to make the bar display everything horizontal or vertical. The bounds are NOT changed based on this option, so make sure the bar can fit the text!
     * @param barBackgroundColor The background Paint of the bar. This is the back of the bar, thus this is the color that represents the percent incomplete.
     * @param barBorderColor The color of the borders around the bar
     * @param titleColor The color of the text that is written on the bar
     * @param barColor The color of the percentage that is filled on the bar. This is the representation of the percent complete
     * @param parent The parent component upon which this object is being drawn on
     */
    public WGLoadingBar(Rectangle2D bounds, float borderSize, String title, boolean showPercentage, Font titleFont, double percentFilled, boolean isHorizontal, Paint barBackgroundColor, Paint barBorderColor, Paint titleColor, Paint barColor, Canvas parent)
    {
        super(borderSize, barBackgroundColor, barBackgroundColor, barBorderColor, parent);
        originalTitle = title;
        this.title = originalTitle + (showPercentage ? " " + (int)(percentFilled * 100) + "%" : "");
        this.showPercentage = showPercentage;
        this.titleFont = titleFont;
        this.percentFilled = percentFilled;
        this.isHorizontal = isHorizontal;
        this.titleColor = titleColor;
        this.barColor = barColor;
        if(getParent() != null)
        {
            resizer = new BarResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
        }
    }
    /**
     * The most complete version of the WGLoadingBar object, which allows complete control over all of the options
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param title The text written on the bar
     * @param showPercentage The option for a percentage to be written after the title
     * @param percentFilled The percent the bar should be filled
     * @param isHorizontal The option to make the bar display everything horizontal or vertical. The bounds are NOT changed based on this option, so make sure the bar can fit the text!
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @param parent The parent component upon which this object is being drawn on
     */
    public WGLoadingBar(Rectangle2D bounds, String title, boolean showPercentage, double percentFilled, boolean isHorizontal, Canvas parent, WGTheme theme)
    {
        this(bounds, theme.getBorderSize(), title, showPercentage, theme.getTextFont(), percentFilled, isHorizontal, theme.getBackgroundColor(), theme.getBorderColor(), theme.getTextColor(), theme.getBarColor(), parent);
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
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        this.titleFont = theme.getTextFont();
        this.titleColor = theme.getTextColor();
        this.barColor = theme.getBarColor();
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
    }
    
    
    //Setters:
    public void setTitle(String title) 
    {
        originalTitle = title;
        this.title = originalTitle + (showPercentage ? " " + (int)(percentFilled * 100) + "%" : "");
        resizer.resizeComps();
    }

    public void setShowPercentage(boolean showPercentage) {
        this.showPercentage = showPercentage;
        resizer.resizeComps();
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
        resizer.resizeComps();
    }

    public void setPercentFilled(double percentFilled) {
        this.percentFilled = percentFilled;
        resizer.resizeComps();
    }

    public void setHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public void setTitleColor(Paint titleColor) {
        this.titleColor = titleColor;
    }

    public void setBarColor(Paint barColor) {
        this.barColor = barColor;
    }
    
    
    //Getters:
    public String getTitle() {
        return title;
    }

    public boolean getShowPercentage() {
        return showPercentage;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public double getPercentFilled() {
        return percentFilled;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public Paint getTitleColor() {
        return titleColor;
    }

    public Paint getBarColor() {
        return barColor;
    }
    
    
    //classes or Listeners:
    private class BarResizeListener extends WGDrawingObjectResizeListener
    {
        private BarResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
            title = originalTitle + (showPercentage ? " " + (int)(percentFilled * 100) + "%" : "");
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            //This is to make sure that the font is the correct size for the width
            titleFont = WGFontHelper.getFittedFontForBox(titleFont, getParent(), getWidth() - borderPadding, getHeight() - borderPadding, title, 100);
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                titleColor = fixPaintBounds(titleColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
                barColor = fixPaintBounds(barColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BAR_COLOR));
            }
        }
    }
}
