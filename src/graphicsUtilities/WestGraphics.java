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
        FontMetrics titleFM = g2.getFontMetrics(titleFont);
        g2.setFont( isHorizontal ? titleFont : titleFont.deriveFont(AffineTransform.getRotateInstance(Math.PI/2)) );
        Rectangle2D.Double originalBarRect = new Rectangle2D.Double(barX, barY, barWidth, barHeight);
        g2.setColor(barBackgroundColor);
        g2.fill(originalBarRect);
        g2.setColor(barBorderColor);
        g2.draw(originalBarRect);
        
        double percentWidth = originalBarRect.getWidth() * (isHorizontal ? percentFilled : 1);
        double percentHeight = originalBarRect.getHeight() * (isHorizontal ? 1 : percentFilled);
        Rectangle2D.Double percentRect = new Rectangle2D.Double(originalBarRect.getX(), originalBarRect.getY(), percentWidth, percentHeight);
        g2.setColor(barColor);
        g2.fill(percentRect);
        
        g2.setColor(titleColor);
        String text = title + (showPercentage ? (int)(percentFilled * 100) + " %" : "");
        if(isHorizontal)
        {
            g2.drawString(text, (float)(originalBarRect.getCenterX() - (titleFM.stringWidth(text) / 2D)), (float)(originalBarRect.getY() + titleFM.getHeight()));
        }
        else
        {
            g2.drawString(text, (float)(originalBarRect.getCenterX() - (titleFM.getDescent())), (float)(originalBarRect.getCenterY() - (titleFM.stringWidth(text) / 2D)));
        }
    }
}
