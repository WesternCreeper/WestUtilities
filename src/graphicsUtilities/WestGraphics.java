/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 *
 * @author Westley
 * 
 * This is the class that defines functions to draw various advanced components, such as rendering a loading bar or a scroll bar to float/double precision. This CANNOT do 3D though
 */
public class WestGraphics
{
    private Graphics2D g2;
    /**
     * This constructs a standard WestGraphics object that can then draw the advanced components on the canvas or whatever that the Graphics2D is from.
     * @param g The Graphics supplied from the paintComponent object. (Already casted to Graphics 2D BEFORE construction, as in you have to cast it!)
     */
    public WestGraphics(Graphics2D g)
    {
        g2 = g;
    }
    /**
     * This function draws out a loading bar based on all of the information given in parameters. This function gives the most control over the drawing process, other than using the object parameter type
     * @param barX The X for where the bar should be
     * @param barY The Y for where the bar should be
     * @param barWidth The width of the bar
     * @param barHeight The height of the bar
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
    public void drawLoadingBar(double barX, double barY, double barWidth, double barHeight, String title, boolean showPercentage, Font titleFont, double percentFilled, boolean isHorizontal, Color barBackgroundColor, Color barBorderColor, Color titleColor, Color barColor)
    {
        WGLoadingBar bar = new WGLoadingBar(barX, barY, barWidth, barHeight, title, showPercentage, titleFont, percentFilled, isHorizontal, barBackgroundColor, barBorderColor, titleColor, barColor);
        drawLoadingBar(bar);
    }
    /**
     * This function draws out a loading bar based on a WGLoadingBar Object, which allows for certain defaults to exist
     * @param loadingBar The Object representation of the loading bar to be drawn
     */
    public void drawLoadingBar(WGLoadingBar loadingBar)
    {
        FontMetrics titleFM = g2.getFontMetrics(loadingBar.getTitleFont());
        g2.setFont( loadingBar.isHorizontal() ? loadingBar.getTitleFont() : loadingBar.getTitleFont().deriveFont(AffineTransform.getRotateInstance(Math.PI/2)) );
        Rectangle2D.Double originalBarRect = loadingBar.getBounds();
        g2.setColor(loadingBar.getBarBorderColor());
        g2.draw(originalBarRect);
        g2.setColor(loadingBar.getBarBackgroundColor());
        g2.fill(originalBarRect);
        
        double percentWidth = originalBarRect.getWidth() * (loadingBar.isHorizontal() ? loadingBar.getPercentFilled() : 1);
        double percentHeight = originalBarRect.getHeight() * (loadingBar.isHorizontal() ? 1 : loadingBar.getPercentFilled());
        Rectangle2D.Double percentRect = new Rectangle2D.Double(originalBarRect.getX(), originalBarRect.getY(), percentWidth, percentHeight);
        g2.setColor(loadingBar.getBarColor());
        g2.fill(percentRect);
        
        g2.setColor(loadingBar.getTitleColor());
        String text = loadingBar.getTitle() + (loadingBar.getShowPercentage() ? " " + (int)(loadingBar.getPercentFilled() * 100) + "%" : "");
        if(loadingBar.isHorizontal())
        {
            g2.drawString(text, (float)(originalBarRect.getCenterX() - (titleFM.stringWidth(text) / 2D)), (float)(originalBarRect.getY() + titleFM.getHeight()));
        }
        else
        {
            g2.drawString(text, (float)(originalBarRect.getCenterX() - (titleFM.getDescent())), (float)(originalBarRect.getCenterY() - (titleFM.stringWidth(text) / 2D)));
        }
    }
    /**
     * This creates an announcement card, which just means a Title separated by a line and a subtitle. The separation line is option however. Also there can be an enclosing box if wanted
     * @param x The x that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param y The y that starts the box, this may or may not be where the Title/Subtitle text starts due to the function packing and formatting the text
     * @param splitHeight The height of the split that goes between the two texts
     * @param title The Title for the announcement
     * @param titleFont The font of that title
     * @param subTitle The Subtitle for the announcement
     * @param subTitleFont The font of that Subtitle
     * @param titleColor The color of the title
     * @param splitColor The color of the line that splits the title and the subtitle
     * @param subTitleColor The color of the subtitle
     * @param backgroundColor The color of the background of the box, can be null if the drawBackground boolean is false
     * @param borderColor The color of the border of the box, can be null if the drawBackground boolean is false
     */
    public void drawAnouncementCard(double x, double y, double splitHeight, String title, Font titleFont, String subTitle, Font subTitleFont, Color titleColor, Color splitColor, Color subTitleColor, Color backgroundColor, Color borderColor)
    {
        WGAnnouncementCard card = new WGAnnouncementCard(x, y, splitHeight, title, titleFont, subTitle, subTitleFont, titleColor, splitColor, subTitleColor, backgroundColor, borderColor);
        drawAnouncementCard(card);
    }
    /**
     * This creates an announcement card based on the object WGAnnouncementCard given
     * @param announcementCard The Object representation of the announcement card
     */ 
    public void drawAnouncementCard(WGAnnouncementCard announcementCard)
    {
        //Find the width of the title and the subtitle, whichever is biggest is the total width of the card:
        FontMetrics titleFM = g2.getFontMetrics(announcementCard.getTitleFont());
        FontMetrics subTitleFM = g2.getFontMetrics(announcementCard.getSubTitleFont());
        double titleWidth = titleFM.stringWidth(announcementCard.getTitle());
        double subTitleWidth = subTitleFM.stringWidth(announcementCard.getSubTitle());
        double totalWidth = (titleWidth > subTitleWidth ? titleWidth : subTitleWidth);
        
        //Draw the background if told to:
        if(announcementCard.getDrawBackground())
        {
            double totalHeight = titleFM.getHeight() + announcementCard.getSplitHeight() + subTitleFM.getHeight();
            if(announcementCard.getSplitHeight() == 0)
            {
                totalHeight -= titleFM.getDescent();
            }
            Rectangle2D.Double backgroundRect = new Rectangle2D.Double(announcementCard.getX(), announcementCard.getY(), totalWidth, totalHeight);
            g2.setColor(announcementCard.getBorderColor());
            g2.draw(backgroundRect);
            g2.setColor(announcementCard.getBackgroundColor());
            g2.fill(backgroundRect);
        }
        
        //Now draw the title:
        double titleX = announcementCard.getX() + ((totalWidth - titleWidth) / 2);
        double titleY = announcementCard.getY() + titleFM.getHeight() - subTitleFM.getDescent() - subTitleFM.getLeading() - titleFM.getDescent() - titleFM.getLeading();
        g2.setColor(announcementCard.getTitleColor());
        g2.setFont(announcementCard.getTitleFont());
        g2.drawString(announcementCard.getTitle(), (float)titleX, (float)titleY);
        
        //Now draw the seperator:
        double splitY = titleY;
        if(announcementCard.getSplitHeight() > 0)
        {
            splitY += titleFM.getDescent() + announcementCard.getSplitHeight();
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke((float)announcementCard.getSplitHeight()));
            Path2D.Double path = new Path2D.Double();
            path.moveTo(announcementCard.getX(), splitY);
            path.lineTo(announcementCard.getX() + totalWidth, splitY);
            path.closePath();
            g2.setColor(announcementCard.getSplitColor());
            g2.draw(path);
            g2.setStroke(oldStroke);
        }
        
        //Finish with the Subtitle:
        double subTitleX = announcementCard.getX() + ((totalWidth - subTitleWidth) / 2);
        double subTitleY = splitY + subTitleFM.getHeight();
        g2.setFont(announcementCard.getSubTitleFont());
        g2.setColor(announcementCard.getSubTitleColor());
        g2.drawString(announcementCard.getSubTitle(), (float)subTitleX, (float)subTitleY);
    }
}
