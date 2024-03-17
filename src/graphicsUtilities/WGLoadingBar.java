/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 * This defines a loading bar, which can then be drawn using the WestGraphics method. This is used for drawing and bound creation!!
 */
public class WGLoadingBar extends WGDrawingObject
{
    private String title;
    private boolean showPercentage; 
    private Font titleFont;
    private double percentFilled;
    private boolean isHorizontal;
    private Color barBackgroundColor;
    private Color barBorderColor;
    private Color titleColor;
    private Color barColor;
    /**
     * The most basic version of the constructor, with the least amount of information to construct a LoadingBar Object. This constructor assumes a horizontal bar, the percentage should not be shown, default colors (White, Gray, Red, and Black), and No title
     * @param barX The X for where the bar should be
     * @param barY The Y for where the bar should be
     * @param barWidth The width of the bar
     * @param barHeight The height of the bar
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param percentFilled The percent the bar should be filled
     */
    public WGLoadingBar(double barX, double barY, double barWidth, double barHeight, float borderSize, double percentFilled)
    {
        this(barX, barY, barWidth, barHeight, borderSize, "", new Font("Sanserif", Font.BOLD, 10), percentFilled);
    }
    /**
     * This constructor assumes a horizontal bar, the percentage should not be shown, and default colors (White, Gray, Red, and Black)
     * @param barX The X for where the bar should be
     * @param barY The Y for where the bar should be
     * @param barWidth The width of the bar
     * @param barHeight The height of the bar
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param title The text written on the bar
     * @param titleFont The Font object for the title String, a FontMeterics is already supplied by the function based on the Font, no need to give such a thing here
     * @param percentFilled The percent the bar should be filled
     */
    public WGLoadingBar(double barX, double barY, double barWidth, double barHeight, float borderSize, String title, Font titleFont, double percentFilled)
    {
        this(barX, barY, barWidth, barHeight, borderSize, title, titleFont, percentFilled, new Color(255, 255, 255), new Color(127, 127, 127), new Color(255, 0, 0), new Color(0, 0, 0));
    }
    /**
     * The constructor with the second most amount of options, which assumes that this is a horizontal bar and that the percentage should not be shown
     * @param barX The X for where the bar should be
     * @param barY The Y for where the bar should be
     * @param barWidth The width of the bar
     * @param barHeight The height of the bar
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param title The text written on the bar
     * @param titleFont The Font object for the title String, a FontMeterics is already supplied by the function based on the Font, no need to give such a thing here
     * @param percentFilled The percent the bar should be filled
     * @param barBackgroundColor The background Color of the bar. This is the back of the bar, thus this is the color that represents the percent incomplete.
     * @param barBorderColor The color of the borders around the bar
     * @param titleColor The color of the text that is written on the bar
     * @param barColor The color of the percentage that is filled on the bar. This is the representation of the percent complete
     */
    public WGLoadingBar(double barX, double barY, double barWidth, double barHeight, float borderSize, String title, Font titleFont, double percentFilled, Color barBackgroundColor, Color barBorderColor, Color titleColor, Color barColor)
    {
        this(barX, barY, barWidth, barHeight, borderSize, title, false, titleFont, percentFilled, true, barBackgroundColor, barBorderColor, titleColor, barColor);
    }
    /**
     * The most complete version of the WGLoadingBar object, which allows complete control over all of the options
     * @param barX The X for where the bar should be
     * @param barY The Y for where the bar should be
     * @param barWidth The width of the bar
     * @param barHeight The height of the bar
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param title The text written on the bar
     * @param showPercentage The option for a percentage to be written after the title
     * @param titleFont The Font object for the title String, a FontMeterics is already supplied by the function based on the Font, no need to give such a thing here
     * @param percentFilled The percent the bar should be filled
     * @param isHorizontal The option to make the bar display everything horizontal or vertical. The bounds are NOT changed based on this option, so make sure the bar can fit the text!
     * @param barBackgroundColor The background Color of the bar. This is the back of the bar, thus this is the color that represents the percent incomplete.
     * @param barBorderColor The color of the borders around the bar
     * @param titleColor The color of the text that is written on the bar
     * @param barColor The color of the percentage that is filled on the bar. This is the representation of the percent complete
     */
    public WGLoadingBar(double barX, double barY, double barWidth, double barHeight, float borderSize, String title, boolean showPercentage, Font titleFont, double percentFilled, boolean isHorizontal, Color barBackgroundColor, Color barBorderColor, Color titleColor, Color barColor)
    {
        super(barX, barY, barWidth, barHeight, borderSize);
        this.title = title;
        this.showPercentage = showPercentage;
        this.titleFont = titleFont;
        this.percentFilled = percentFilled;
        this.isHorizontal = isHorizontal;
        this.barBackgroundColor = barBackgroundColor;
        this.barBorderColor = barBorderColor;
        this.titleColor = titleColor;
        this.barColor = barColor;
    }
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
     * @param barBackgroundColor The background Color of the bar. This is the back of the bar, thus this is the color that represents the percent incomplete.
     * @param barBorderColor The color of the borders around the bar
     * @param titleColor The color of the text that is written on the bar
     * @param barColor The color of the percentage that is filled on the bar. This is the representation of the percent complete
     */
    public WGLoadingBar(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, String title, boolean showPercentage, Font titleFont, double percentFilled, boolean isHorizontal, Color barBackgroundColor, Color barBorderColor, Color titleColor, Color barColor, Component parent)
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.title = title;
        this.showPercentage = showPercentage;
        this.titleFont = titleFont;
        this.percentFilled = percentFilled;
        this.isHorizontal = isHorizontal;
        this.barBackgroundColor = barBackgroundColor;
        this.barBorderColor = barBorderColor;
        this.titleColor = titleColor;
        this.barColor = barColor;
        if(getParent() != null)
        {
            BarResizeListener resizer = new BarResizeListener(xPercent, yPercent, widthPercent, heightPercent);
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
        }
    }

    
    @Override
    public Rectangle2D.Double getBounds() 
    {
       Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
       return bounds;
    }
    
    
    //Setters:
    public void setTitle(String title) {
        this.title = title;
    }

    public void setShowPercentage(boolean showPercentage) {
        this.showPercentage = showPercentage;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public void setPercentFilled(double percentFilled) {
        this.percentFilled = percentFilled;
    }

    public void setHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public void setBarBackgroundColor(Color barBackgroundColor) {
        this.barBackgroundColor = barBackgroundColor;
    }

    public void setBarBorderColor(Color barBorderColor) {
        this.barBorderColor = barBorderColor;
    }

    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
    }

    public void setBarColor(Color barColor) {
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

    public Color getBarBackgroundColor() {
        return barBackgroundColor;
    }

    public Color getBarBorderColor() {
        return barBorderColor;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public Color getBarColor() {
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
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            //This is to make sure that the font is the correct size for the width
            int extraSpacing = 2;
            if(showPercentage)
            {
                extraSpacing += 4;
            }
            titleFont = WGFontHelper.getFittedFontForBox(titleFont, getWidth() - borderPadding, getHeight() - borderPadding, title.length() + extraSpacing);
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
