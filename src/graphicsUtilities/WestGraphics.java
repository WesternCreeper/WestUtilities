/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;

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
            else if(obj instanceof WGPane)
            {
                drawPane((WGPane)obj);
            }
            else if(obj instanceof WGLabel)
            {
                drawLabel((WGLabel)obj);
            }
            else if(obj instanceof WGTextInput)
            {
                drawTextInput((WGTextInput)obj);
            }
            else if(obj instanceof WGTextArea)
            {
                drawTextArea((WGTextArea)obj);
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
        Rectangle2D.Double buttonRect = button.getBounds();
        g2.setColor(button.getBackgroundColor());
        g2.fill(buttonRect);
        g2.setColor(button.getBorderColor());
        g2.setStroke(new BasicStroke((float)button.getBorderSize()));
        g2.draw(buttonRect);
        
        //Draw the contents:
        if(button.getDisplayedImage() != null)
        {
            //The image:
            g2.drawImage(button.getDisplayedImage(), (int)button.getImageX(), (int)button.getImageY(), null);
        }
        else if(button.getTextColor() != null && button.getTextFont() != null && button.getText() != null)
        {
            //The text:
            g2.setColor(button.getTextColor());
            FontMetrics textFM = g2.getFontMetrics(button.getTextFont());
            double textX = button.getX() + ((button.getWidth() - textFM.stringWidth(button.getText())) / 2);
            double textY = button.getY() + ((textFM.getAscent() - textFM.getDescent() + button.getHeight()) / 2);
            g2.setFont(button.getTextFont());
            g2.drawString(button.getText(), (float)textX, (float)textY);
        }
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    /**
     * The drawing method for a tooltip all it requires is the tooltip object then it can draw it from there
     * @param toolTip The tooltip object that tells the graphics how to draw it
     */
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
    /**
     * The drawing method for a pane that will draw the pane and all subsequent objects attached to the pane
     * @param pane The pane to be drawn and the components added to it that also draw
     */
    private void drawPane(WGPane pane)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
        
        //Save the original clip in case the user wanted that one:
        Shape oldClip = g2.getClip();
        
        //Draw the background:
        Rectangle2D.Double buttonRect = new Rectangle2D.Double(pane.getX(), pane.getY(), pane.getWidth(), pane.getHeight());
        g2.setColor(pane.getBackgroundColor());
        g2.fill(buttonRect);
        
        
        //To make sure nothing goes off the pane:
        g2.setClip(pane.getBounds());
        
        //Now the internal components:
        for(int i = 0 ; i < pane.getComponentNumber() ; i++)
        {
            draw(pane.getComponent(i));
        }
        
        //Draw the scrollBars
        drawScrollBar(pane.getVerticalScroll(), pane);
        drawScrollBar(pane.getHorizontalScroll(), pane);
        
        
        //Reload the old clip, as it is no longer useful:
        g2.setClip(oldClip);
        
        
        //Then draw in the border:
        g2.setColor(pane.getBorderColor());
        g2.setStroke(new BasicStroke((float)pane.getBorderSize()));
        g2.draw(buttonRect);
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    private void drawScrollBar(WGScrollableListener scrollBar, WGPane pane)
    {
        //Find the scrollBar offset:
        double scrollBarX = 0;
        double scrollBarY = 0;
        double scrollBarWidth = 0;
        double scrollBarHeight = 0;
        if(pane.isScrollable() && scrollBar != null && scrollBar.isShown())
        {
            scrollBarX = pane.getX();
            scrollBarY = pane.getY();
            scrollBarWidth = pane.getBorderSize();
            scrollBarHeight = scrollBarWidth;
            if(scrollBar.isVertical())
            {
                scrollBarY += scrollBar.getScrollBarY();
                scrollBarHeight = scrollBar.getScrollBarHeight();
                scrollBarX += pane.getWidth() - scrollBarWidth - pane.getBorderSize();
            }
            else
            {
                scrollBarX += scrollBar.getScrollBarY();
                scrollBarWidth = scrollBar.getScrollBarHeight();
                scrollBarY += pane.getHeight() - scrollBarHeight - pane.getBorderSize();
            }
        }
        
        //Then draw in the scrollBar:
        if(pane.isScrollable() && scrollBar != null && scrollBar.isShown())
        {
            g2.setColor(pane.getScrollBarColor());
            Rectangle2D.Double scrollBarRect =  new Rectangle2D.Double(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);
            g2.fill(scrollBarRect);
        }
    }
    private void drawScrollBar(WGTextScrollableListener scrollBar, WGTextArea textArea)
    {
        //Find the scrollBar offset:
        double scrollBarX = 0;
        double scrollBarY = 0;
        double scrollBarWidth = 0;
        double scrollBarHeight = 0;
        if(scrollBar != null && scrollBar.isShown())
        {
            scrollBarX = textArea.getX();
            scrollBarY = textArea.getY();
            scrollBarWidth = textArea.getBorderSize();
            scrollBarHeight = scrollBarWidth;
            
            scrollBarY += scrollBar.getScrollBarY();
            scrollBarHeight = scrollBar.getScrollBarHeight();
            scrollBarX += textArea.getWidth() - scrollBarWidth - textArea.getBorderSize();
        }
        
        //Then draw in the scrollBar:
        if(scrollBar != null && scrollBar.isShown())
        {
            g2.setColor(textArea.getScrollBarColor());
            Rectangle2D.Double scrollBarRect =  new Rectangle2D.Double(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);
            g2.fill(scrollBarRect);
        }
    }
    /**
     * The drawing method for a label that will draw the text inside a box according to the methods given by the WGLabel
     * @param pane The label to be drawn
     */
    private void drawLabel(WGLabel label)
    {
        
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
        
        //Draw the text:
        g2.setColor(label.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(label.getTextFont());
        double textX = label.getX();
        
        double textY = label.getY() - textFM.getDescent() + label.getBorderSize();
        g2.setFont(label.getTextFont());
        
        if(label.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
        {
            textX = label.getX() + ((label.getWidth() - textFM.stringWidth(label.getText())) / 2);
        }
        else if(label.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
        {
            textX = label.getX() + label.getWidth() - textFM.stringWidth(label.getText());
        }
        
        textY += textFM.getHeight();
        g2.drawString(label.getText(), (float)textX, (float)textY);
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    /**
     * Creates a textInput based on the information given by the object
     * @param textInput The object that gives information about how a text input is supposed to be drawn
     */
    private void drawTextInput(WGTextInput textInput)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
        //Draw the Box
        Rectangle2D.Double buttonRect = new Rectangle2D.Double(textInput.getX(), textInput.getY(), textInput.getWidth(), textInput.getHeight());
        Color backgroundColor = (textInput.isFocused()) ? textInput.getBackgroundOnFocusColor() : textInput.getBackgroundColor();
        g2.setColor(backgroundColor);
        g2.fill(buttonRect);
        Color borderColor = textInput.getBorderColor();
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke((float)textInput.getBorderSize()));
        g2.draw(buttonRect);
            
        //Draw the highlight:
        if(textInput.isHighlightShown() && textInput.isFocused())
        {
            g2.setColor(textInput.getHighlightColor());
            g2.fill(textInput.getHighlightBounds());
        }
        
        //Now the text:
        g2.setColor(textInput.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(textInput.getTextFont());
        double textX = textInput.getX() + ((textInput.getWidth() - textFM.stringWidth(textInput.getText())) / 2);
        double textY = textInput.getY() + ((textFM.getAscent() - textFM.getDescent() + textInput.getHeight()) / 2);
        g2.setFont(textInput.getTextFont());
        g2.drawString(textInput.getText(), (float)textX, (float)textY);
            
        //Draw the cursor
        if(textInput.isCursorShown() && textInput.isFocused())
        {
            g2.setColor(textInput.getCursorColor());
            g2.fill(textInput.getCursorBounds());
        }
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    /**
     * 
     * @param textArea 
     */
    private void drawTextArea(WGTextArea textArea)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
        
        //Save the original clip in case the user wanted that one:
        Shape oldClip = g2.getClip();
        
        //To make sure nothing goes off the pane:
        g2.setClip(textArea.getBounds());
        
        //Draw the text:
        FontMetrics textFM = g2.getFontMetrics(textArea.getTextFont());
        double textX = textArea.getX();
        double textY = textArea.getY() - textFM.getDescent() + textArea.getBorderSize();
        
        double longestStringWidth = textFM.stringWidth(textArea.getLongestString());
        
        if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_LEFT)
        {
            textX = textArea.getX() + ((textArea.getWidth() - longestStringWidth) / 2);
        }
        
        g2.setFont(textArea.getTextFont());
        textY += textFM.getHeight() - textArea.getStringYOffset();
        ArrayList<String> allText = textArea.getText();
        ArrayList<Color> allTextColors = textArea.getTextColors();
        for(int i = 0 ; i < allText.size() ; i++)
        {
            if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
            {
                textX = textArea.getX() + ((textArea.getWidth() - textFM.stringWidth(allText.get(i))) / 2);
            }
            else if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
            {
                int stringWidth = textFM.stringWidth(allText.get(i));
                textX = textArea.getX() + textArea.getWidth() - ((textArea.getWidth() - longestStringWidth) / 2) - stringWidth;
            }
            g2.setColor(allTextColors.get(i));
            g2.drawString(allText.get(i), (float)textX, (float)textY);
            textY += textFM.getHeight();
        }
        
        //Draw the scrollBars
        drawScrollBar(textArea.getVerticalScroll(), textArea);
        
        
        //Reload the old clip, as it is no longer useful:
        g2.setClip(oldClip);
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
}
