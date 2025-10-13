/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.FXFontMetrics;

/**
 *
 * @author Westley
 */
public class WGAnnouncementCard extends WGBox
{
    private double splitHeight;
    private boolean drawBackground;
    private String title;
    private Font titleFont;
    private String subTitle;
    private Font subTitleFont;
    private Paint titleColor;
    private Paint splitColor;
    private Paint subTitleColor;
    /**
     * This constructor allows for the announcement card to fully resize itself and it's components based off of a set sizes and widths.
     * NOTE: This component acts slightly differently than most other WGObjects act, since the x and y variables have been replaced with the centerX and centerY variables, which define the where the center of the object goes
     * @param xCenter This is the percentage of the screen where the center of the object will be placed horizontally (x-plane) at 0.5 this is at the center of the screen horizontally
     * @param yCenter This is the percentage of the screen where the center of the object will be placed vertically (y-plane) at 0.5 this is at the center of the screen vertically
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component. (Recommendation: to get the best results make sure that splitPercentage + titleHeight + subTitleHeight = 1)
     * @param titleHeightPercentage The percentage of the parent's height that is used for the calculation of the size of the title font
     * @param subTitleHeightPercentage The percentage of the parent's height that is used for the calculation of the size of the subtitle font
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param splitPercentage The percentage of the parent's height that is used for the calculation of the size of the split
     * @param title The Title for the announcement
     * @param titleFont The font of that title
     * @param subTitle The Subtitle for the announcement
     * @param subTitleFont The font of that Subtitle
     * @param titleColor The color of the title
     * @param splitColor The color of the line that splits the title and the subtitle
     * @param subTitleColor The color of the subtitle
     * @param backgroundColor The color of the background of the box, if null then drawBackground is automatically set to false
     * @param borderColor The color of the border of the box, if null then drawBackground is automatically set to false
     * @param parent The component that the object is on, and is used to determine how big this object is
     */
    public WGAnnouncementCard(double xCenter, double yCenter, double widthPercent, double heightPercent, double titleHeightPercentage, double subTitleHeightPercentage, float borderSize, double splitPercentage, String title, Font titleFont, String subTitle, Font subTitleFont, Paint titleColor, Paint splitColor, Paint subTitleColor, Paint backgroundColor, Paint borderColor, Canvas parent)
    {
        super(borderSize, backgroundColor, null, borderColor, parent);
        this.splitHeight = 0;
        this.title = title;
        this.titleFont = titleFont;
        this.subTitle = subTitle;
        this.subTitleFont = subTitleFont;
        this.titleColor = titleColor;
        this.splitColor = splitColor;
        this.subTitleColor = subTitleColor;
        drawBackground = true;
        if(backgroundColor == null || borderColor == null)
        {
            drawBackground = false;
        }
        if(getParent() != null)
        {
            resizer = new AnnouncementResizeListener(xCenter, yCenter, widthPercent, heightPercent, titleHeightPercentage, subTitleHeightPercentage, splitPercentage);
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
        }
    }
    /**
     * This constructor allows for the announcement card to fully resize itself and it's components based off of a set sizes and widths.
     * NOTE: This component acts slightly differently than most other WGObjects act, since the x and y variables have been replaced with the centerX and centerY variables, which define the where the center of the object goes
     * @param xCenter This is the percentage of the screen where the center of the object will be placed horizontally (x-plane) at 0.5 this is at the center of the screen horizontally
     * @param yCenter This is the percentage of the screen where the center of the object will be placed vertically (y-plane) at 0.5 this is at the center of the screen vertically
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component. (Recommendation: to get the best results make sure that splitPercentage + titleHeight + subTitleHeight = 1)
     * @param titleHeightPercentage The percentage of the parent's height that is used for the calculation of the size of the title font
     * @param subTitleHeightPercentage The percentage of the parent's height that is used for the calculation of the size of the subtitle font
     * @param splitPercentage The percentage of the parent's height that is used for the calculation of the size of the split
     * @param title The Title for the announcement
     * @param subTitle The Subtitle for the announcement
     * @param splitColor The color of the line that splits the title and the subtitle
     * @param parent The component that the object is on, and is used to determine how big this object is
     * @param theme The theme that is being used for this object
     */
    public WGAnnouncementCard(double xCenter, double yCenter, double widthPercent, double heightPercent, double titleHeightPercentage, double subTitleHeightPercentage, double splitPercentage, String title, String subTitle, Paint splitColor, Canvas parent, WGTheme theme)
    {
        super(theme.getBorderSize(), theme.getBackgroundColor(), null, theme.getBorderColor(), parent, theme);
        this.splitHeight = 0;
        this.title = title;
        this.titleFont = theme.getTextFont();
        this.subTitle = subTitle;
        this.subTitleFont = theme.getTextFont();
        this.titleColor = theme.getTextColor();
        this.splitColor = splitColor;
        this.subTitleColor = theme.getTextColor();
        drawBackground = true;
        if(getBackgroundColor() == null || getBorderColor() == null)
        {
            drawBackground = false;
        }
        if(getParent() != null)
        {
            resizer = new AnnouncementResizeListener(xCenter, yCenter, widthPercent, heightPercent, titleHeightPercentage, subTitleHeightPercentage, splitPercentage);
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
        }
    }

    
    //Methods:
    @Override
    /**
     * This gives the x, y, width, and height bounds
     * @return The bounds
     */
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
        this.subTitleFont = theme.getTextFont();
        this.titleColor = theme.getTextColor();
        this.subTitleColor = theme.getTextColor();
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
    /**
     * This setter will set the drawBackground boolean, however if the backgroundColor or borderColor variables are null then it will automatically go back to the false setting to prevent any problems from occurring
     * @param drawBackground 
     */
    public synchronized void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
        if(getBackgroundColor() == null || getBorderColor() == null)
        {
            this.drawBackground = false;
            resizer.resizeComps();
        }
    }

    public void setTitle(String title) {
        this.title = title;
        resizer.resizeComps();
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
        resizer.resizeComps();
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        resizer.resizeComps();
    }

    public void setSubTitleFont(Font subTitleFont) {
        this.subTitleFont = subTitleFont;
        resizer.resizeComps();
    }

    public void setTitleColor(Paint titleColor) {
        this.titleColor = titleColor;
    }

    public void setSplitColor(Paint splitColor) {
        this.splitColor = splitColor;
    }

    public void setSubTitleColor(Paint subTitleColor) {
        this.subTitleColor = subTitleColor;
    }
    
    
    //Getters:
    public double getSplitHeight() {
        return splitHeight;
    }

    public boolean getDrawBackground() {
        return drawBackground;
    }

    public String getTitle() {
        return title;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Font getSubTitleFont() {
        return subTitleFont;
    }

    public Paint getTitleColor() {
        return titleColor;
    }

    public Paint getSplitColor() {
        return splitColor;
    }

    public Paint getSubTitleColor() {
        return subTitleColor;
    }
    
    
    //classes or Listeners:
    private class AnnouncementResizeListener extends WGDrawingObjectResizeListener
    {
        private double titleHeightPercentage = 0;
        private double subTitleHeightPercentage = 0;
        private double splitPercentage = 0;
        private AnnouncementResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent, double titleHeightPercentage, double subTitleHeightPercentage, double splitPercentage)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
            this.titleHeightPercentage = titleHeightPercentage;
            this.subTitleHeightPercentage = subTitleHeightPercentage;
            this.splitPercentage = splitPercentage;
        }
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double borderPadding = getBorderSize(); //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            double optimalWidth = parentWidth * getWidthPercent();
            double optimalHeight = parentHeight * getHeightPercent();
            double titleHeight = titleHeightPercentage * optimalHeight;
            double subTitleHeight = subTitleHeightPercentage * optimalHeight;
            splitHeight = splitPercentage * optimalHeight;
            titleFont = WGFontHelper.getFittedFontForBox(titleFont, getParent(), optimalWidth - (borderPadding * 2), titleHeight - borderPadding, title, 100);
            subTitleFont = WGFontHelper.getFittedFontForBox(subTitleFont, getParent(), optimalWidth - (borderPadding * 2), subTitleHeight - borderPadding, subTitle, 100);
            
            //Now that the height, width, and fonts have been set, now use the xCenter and yCenter to make the component centered on that area:
            FXFontMetrics titleFM = new FXFontMetrics(titleFont);
            FXFontMetrics subTitleFM = new FXFontMetrics(subTitleFont);
            double xTitlePlace = (getXPercent() * parentWidth) - (titleFM.stringWidth(title)/ 2);
            double xSubTitlePlace = (getXPercent() * parentWidth) - (subTitleFM.stringWidth(subTitle)/ 2);
            double xPlace = ((xTitlePlace < xSubTitlePlace) ? xTitlePlace : xSubTitlePlace);
            setX(xPlace);
            double yPlace = (getYPercent() * parentHeight) - (((titleFM.getHeight() + subTitleFM.getHeight()) + splitHeight) / 2);
            setY(yPlace);
            double titleWidth = titleFM.stringWidth(title);
            double subTitleWidth = subTitleFM.stringWidth(subTitle) + borderPadding * 2;
            double totalWidth = (titleWidth > subTitleWidth ? titleWidth : subTitleWidth);
            setWidth(totalWidth);
            
            double totalHeight = titleFM.getHeight() - subTitleFM.getDescent() - subTitleFM.getLeading() - titleFM.getLeading() + subTitleFM.getHeight() + borderPadding * 2 + splitHeight;
            setHeight(totalHeight);
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                titleColor = fixPaintBounds(titleColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TITLE_COLOR));
                splitColor = fixPaintBounds(splitColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.SPLIT_COLOR));
                subTitleColor = fixPaintBounds(subTitleColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.SUBTITLE_COLOR));
            }
        }
    }
}
