/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGAnnouncementCard extends WGDrawingObject
{
    private double splitHeight;
    private boolean drawBackground;
    private String title;
    private Font titleFont;
    private String subTitle;
    private Font subTitleFont;
    private Color titleColor;
    private Color splitColor;
    private Color subTitleColor;
    private Color backgroundColor;
    private Color borderColor;
    /**
     * This constructor assumes: no background, and title, split and subtitle all share the same color
     * @param x The x that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param y The y that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param splitHeight The height of the split that goes between the two texts
     * @param title The Title for the announcement
     * @param titleFont The font of that title
     * @param subTitle The Subtitle for the announcement
     * @param subTitleFont The font of that Subtitle
     * @param color The color of the title, subtitle, and split
     */
    public WGAnnouncementCard(double x, double y, float borderSize, double splitHeight, String title, Font titleFont, String subTitle, Font subTitleFont, Color color)
    {
        this(x, y, borderSize, splitHeight, title, titleFont, subTitle, subTitleFont, color, color, color);
    }
    /**
     * The second most complex constructor with a large set of options. Constructor assumes: no background
     * @param x The x that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param y The y that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param splitHeight The height of the split that goes between the two texts
     * @param title The Title for the announcement
     * @param titleFont The font of that title
     * @param subTitle The Subtitle for the announcement
     * @param subTitleFont The font of that Subtitle
     * @param titleColor The color of the title
     * @param splitColor The color of the line that splits the title and the subtitle
     * @param subTitleColor The color of the subtitle
     */
    public WGAnnouncementCard(double x, double y, float borderSize, double splitHeight, String title, Font titleFont, String subTitle, Font subTitleFont, Color titleColor, Color splitColor, Color subTitleColor)
    {
        this(x, y, borderSize, splitHeight, title, titleFont, subTitle, subTitleFont, titleColor, splitColor, subTitleColor, null, null);
    }
    /**
     * The most complex constructor with the largest set of options that allow a multitude of different combinations of announcement cards
     * @param x The x that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param y The y that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param splitHeight The height of the split that goes between the two texts
     * @param title The Title for the announcement
     * @param titleFont The font of that title
     * @param subTitle The Subtitle for the announcement
     * @param subTitleFont The font of that Subtitle
     * @param titleColor The color of the title
     * @param splitColor The color of the line that splits the title and the subtitle
     * @param subTitleColor The color of the subtitle
     * @param backgroundColor The color of the background of the box, if null then drawBackground is automatically set to false
     * @param borderColor The color of the border of the box, if null then drawBackground is automatically set to false
     */
    public WGAnnouncementCard(double x, double y, float borderSize, double splitHeight, String title, Font titleFont, String subTitle, Font subTitleFont, Color titleColor, Color splitColor, Color subTitleColor, Color backgroundColor, Color borderColor)
    {
        super(x, y, 0, 0, borderSize);
        this.splitHeight = splitHeight;
        this.title = title;
        this.titleFont = titleFont;
        this.subTitle = subTitle;
        this.subTitleFont = subTitleFont;
        this.titleColor = titleColor;
        this.splitColor = splitColor;
        this.subTitleColor = subTitleColor;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        drawBackground = true;
        if(backgroundColor == null || borderColor == null)
        {
            drawBackground = false;
        }
    }
    /**
     * This constructor allows for the announcement card to fully resize itself and it's components based off of a set sizes and widths
     * @param xCenter The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yCenter The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
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
    public WGAnnouncementCard(double xCenter, double yCenter, double widthPercent, double heightPercent, double titleHeightPercentage, double subTitleHeightPercentage, float borderSize, double splitPercentage, String title, Font titleFont, String subTitle, Font subTitleFont, Color titleColor, Color splitColor, Color subTitleColor, Color backgroundColor, Color borderColor, Component parent)
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.splitHeight = 0;
        this.title = title;
        this.titleFont = titleFont;
        this.subTitle = subTitle;
        this.subTitleFont = subTitleFont;
        this.titleColor = titleColor;
        this.splitColor = splitColor;
        this.subTitleColor = subTitleColor;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        drawBackground = true;
        if(backgroundColor == null || borderColor == null)
        {
            drawBackground = false;
        }
        if(getParent() != null)
        {
            AnnouncementResizeListener resizer = new AnnouncementResizeListener(xCenter, yCenter, widthPercent, heightPercent, titleHeightPercentage, subTitleHeightPercentage, splitPercentage);
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
        }
    }

    
    //Methods:
    @Override
    /**
     * This gives the x and y bounds, but does not know about the Height and Width bounds. Use the FontMetrics version of this to get those values
     * @return The bounds with 0 for Height and Width
     */
    public Rectangle2D.Double getBounds() 
    {
        Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        return bounds;
    }
    /**
     * This gives the x, y, width, and height bounds
     * @param titleFM The title FontMetrics that helps define the height and width of that object
     * @param subTitleFM The subtitle FontMetrics that helps define the height and width of that object
     * @return All of the bounds
     */
    public Rectangle2D.Double getBounds(FontMetrics titleFM, FontMetrics subTitleFM) 
    {
        //Grab the width
        double titleWidth = titleFM.stringWidth(title);
        double subTitleWidth = subTitleFM.stringWidth(subTitle);
        double totalWidth = (titleWidth > subTitleWidth ? titleWidth : subTitleWidth);
        
        //Grab the height:
        double totalHeight = titleFM.getHeight() + splitHeight + subTitleFM.getHeight();
        
        //Put it together and exit
        Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), totalWidth, totalHeight);
        return bounds;
    }

    
    //Setters:
    public void setSplitHeight(double splitHeight) {
        this.splitHeight = splitHeight;
    }

    /**
     * This setter will set the drawBackground boolean, however if the backgroundColor or borderColor variables are null then it will automatically go back to the false setting to prevent any problems from occurring
     * @param drawBackground 
     */
    public synchronized void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
        if(backgroundColor == null || borderColor == null)
        {
            this.drawBackground = false;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setSubTitleFont(Font subTitleFont) {
        this.subTitleFont = subTitleFont;
    }

    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
    }

    public void setSplitColor(Color splitColor) {
        this.splitColor = splitColor;
    }

    public void setSubTitleColor(Color subTitleColor) {
        this.subTitleColor = subTitleColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
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

    public Color getTitleColor() {
        return titleColor;
    }

    public Color getSplitColor() {
        return splitColor;
    }

    public Color getSubTitleColor() {
        return subTitleColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
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
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            double borderPadding = getBorderSize(); //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            double titleHeight = titleHeightPercentage * getHeight();
            double subTitleHeight = subTitleHeightPercentage * getHeight();
            splitHeight = splitPercentage * getHeight();
            titleFont = WGFontHelper.getFittedFontForBox(titleFont, getWidth() - (borderPadding * 2), titleHeight - borderPadding, title.length());
            subTitleFont = WGFontHelper.getFittedFontForBox(subTitleFont, getWidth() - (borderPadding * 2), subTitleHeight - borderPadding, subTitle.length());
            
            //Now that the height, width, and fonts have been set, now use the xCenter and yCenter to make the component centered on that area:
            double xTitlePlace = (getXPercent() * parentWidth) - (WGFontHelper.getFontWidthApproximation(titleFont.getSize(), title.length()) / 2);
            double xSubTitlePlace = (getXPercent() * parentWidth) - (WGFontHelper.getFontWidthApproximation(subTitleFont.getSize(), subTitle.length()) / 2);
            double xPlace = ((xTitlePlace < xSubTitlePlace) ? xTitlePlace : xSubTitlePlace);
            setX(xPlace);
            double yPlace = (getYPercent() * parentHeight) - ((WGFontHelper.getFontHeightApproximation(titleFont.getSize()) + WGFontHelper.getFontHeightApproximation(subTitleFont.getSize()) + splitHeight) / 2);
            setY(yPlace);
            
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
