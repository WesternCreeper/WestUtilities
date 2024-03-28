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
     * Draws the given WGDrawingObject based on internal functions
     * @param obj The object to be drawn
     */
    public void draw(WGDrawingObject obj)
    {
        if(obj.isIsShown())
        {
            if(obj instanceof WGButton)
            {
                drawButton((WGButton)obj);
            }
            else if(obj instanceof WGLoadingBar)
            {
                drawLoadingBar((WGLoadingBar)obj);
            }
            else if(obj instanceof WGAnnouncementCard)
            {
                drawAnnouncementCard((WGAnnouncementCard)obj);
            }
            else if(obj instanceof WGToolTip)
            {
                drawToolTip((WGToolTip)obj);
            }
        }
    }
    
    /**
     * This function draws out a loading bar based on a WGLoadingBar Object, which allows for certain defaults to exist
     * @param loadingBar The Object representation of the loading bar to be drawn
     */
    private void drawLoadingBar(WGLoadingBar loadingBar)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
        FontMetrics titleFM = g2.getFontMetrics(loadingBar.getTitleFont());
        g2.setFont(loadingBar.getTitleFont());
        Rectangle2D.Double originalBarRect = loadingBar.getBounds();
        g2.setColor(loadingBar.getBarBackgroundColor());
        g2.fill(originalBarRect);
        
        double percentWidth = originalBarRect.getWidth() * (loadingBar.isHorizontal() ? loadingBar.getPercentFilled() : 1);
        double percentHeight = originalBarRect.getHeight() * (loadingBar.isHorizontal() ? 1 : loadingBar.getPercentFilled());
        Rectangle2D.Double percentRect = new Rectangle2D.Double(originalBarRect.getX(), originalBarRect.getY(), percentWidth, percentHeight);
        g2.setColor(loadingBar.getBarColor());
        g2.fill(percentRect);
        
        g2.setColor(loadingBar.getBarBorderColor());
        g2.setStroke(new BasicStroke((float)loadingBar.getBorderSize()));
        g2.draw(originalBarRect);
        
        g2.setColor(loadingBar.getTitleColor());
        String text = loadingBar.getTitle();
        g2.drawString(text, (float)(originalBarRect.getX() + ((loadingBar.getWidth() - titleFM.stringWidth(text)) / 2.0)), (float)(originalBarRect.getY() + ((titleFM.getAscent() - titleFM.getDescent() + originalBarRect.getHeight()) / 2)));
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    /**
     * This creates an announcement card based on the object WGAnnouncementCard given
     * @param announcementCard The Object representation of the announcement card
     */ 
    private void drawAnnouncementCard(WGAnnouncementCard announcementCard)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
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
            g2.setColor(announcementCard.getBackgroundColor());
            g2.fill(backgroundRect);
            g2.setColor(announcementCard.getBorderColor());
            g2.setStroke(new BasicStroke((float)announcementCard.getBorderSize()));
            g2.draw(backgroundRect);
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
            g2.setStroke(new BasicStroke((float)announcementCard.getSplitHeight()));
            Path2D.Double path = new Path2D.Double();
            path.moveTo(announcementCard.getX(), splitY);
            path.lineTo(announcementCard.getX() + totalWidth, splitY);
            path.closePath();
            g2.setColor(announcementCard.getSplitColor());
            g2.draw(path);
        }
        
        //Finish with the Subtitle:
        double subTitleX = announcementCard.getX() + ((totalWidth - subTitleWidth) / 2);
        double subTitleY = splitY + subTitleFM.getHeight();
        g2.setFont(announcementCard.getSubTitleFont());
        g2.setColor(announcementCard.getSubTitleColor());
        g2.drawString(announcementCard.getSubTitle(), (float)subTitleX, (float)subTitleY);
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    /**
     * Creates a button based on the parameters given by the WGButton object
     * @param button A WGButton object that defines everything needed to define a button
     */
    private void drawButton(WGButton button)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
        //Draw the button
        Rectangle2D.Double buttonRect = new Rectangle2D.Double(button.getX(), button.getY(), button.getWidth(), button.getHeight());
        g2.setColor(button.getBackgroundColor());
        g2.fill(buttonRect);
        g2.setColor(button.getBorderColor());
        g2.setStroke(new BasicStroke((float)button.getBorderSize()));
        g2.draw(buttonRect);
        
        //Now the text:
        g2.setColor(button.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(button.getTextFont());
        double textX = button.getX() + ((button.getWidth() - textFM.stringWidth(button.getText())) / 2);
        double textY = button.getY() + ((textFM.getAscent() - textFM.getDescent() + button.getHeight()) / 2);
        g2.setFont(button.getTextFont());
        g2.drawString(button.getText(), (float)textX, (float)textY);
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    private void drawToolTip(WGToolTip toolTip)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
        //Draw the button
        Rectangle2D.Double buttonRect = new Rectangle2D.Double(toolTip.getX(), toolTip.getY(), toolTip.getWidth(), toolTip.getHeight());
        g2.setColor(toolTip.getBackgroundColor());
        g2.fill(buttonRect);
        g2.setColor(toolTip.getBorderColor());
        g2.setStroke(new BasicStroke((float)toolTip.getBorderSize()));
        g2.draw(buttonRect);
        
        //Now the text:
        g2.setColor(toolTip.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(toolTip.getToolTipFont());
        double textX = toolTip.getX();
        if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_LEFT)
        {
            textX = toolTip.getX() + ((toolTip.getWidth() - toolTip.getLongestStringWidth()) / 2);
        }
        double textY = toolTip.getY() - textFM.getDescent() + toolTip.getBorderSize();
        g2.setFont(toolTip.getToolTipFont());
        String[] text = toolTip.getToolTipText();
        for(int i = 0 ; i < text.length ; i++)
        {
            if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
            {
                textX = toolTip.getX() + ((toolTip.getWidth() - textFM.stringWidth(text[i])) / 2);
            }
            else if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
            {
                textX = toolTip.getX() + toolTip.getWidth() - ((toolTip.getWidth() - toolTip.getLongestStringWidth()) / 2) - textFM.stringWidth(text[i]);
            }
            textY += textFM.getHeight();
            g2.drawString(text[i], (float)textX, (float)textY);
        }
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
}
