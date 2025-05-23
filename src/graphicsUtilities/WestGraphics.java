/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import dataStructures.Queue;
import dataStructures.WGObjectBoundList;
import dataStructures.WGObjectBoundNode;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Paint;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;

/**
 *
 * @author Westley
 * 
 * This is the class that defines functions to draw various advanced components, such as rendering a loading bar or a scroll bar to float/double precision. This CANNOT do 3D though
 */
public class WestGraphics
{
    //Static Package wide important stuff:
    private static Component currentActiveParent;
    private static WGBox lastHoverOject;
    private static MouseEvent lastMouseEvent;
    private static WGObjectBoundList allClickables = new WGObjectBoundList();
    private static Cursor defaultCursor = Cursor.getDefaultCursor();
    private static Cursor hoverCursor = new Cursor(Cursor.HAND_CURSOR);
    private static Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);
    private static boolean allowComponentsToRepaint = true;
    
    /**
     * This function sets up the cursors to the default values
     */
    public static void setUpCursors()
    {
        defaultCursor = Cursor.getDefaultCursor();
        hoverCursor = new Cursor(Cursor.HAND_CURSOR);
        textCursor = new Cursor(Cursor.TEXT_CURSOR);
    }
    
    
    //Static methods:
    /**
     * Sets up the cursor to the given cursors
     * @param defaultCursor The default cursor
     * @param hoverCursor The cursor shown when hovering over an object that can be clicked
     * @param textCursor The cursor shown when hovering over a text input of some kind
     */
    public static void setUpCursors(Cursor defaultCursor, Cursor hoverCursor, Cursor textCursor)
    {
        WestGraphics.defaultCursor = defaultCursor;
        WestGraphics.hoverCursor = hoverCursor;
        WestGraphics.textCursor = textCursor;
    }
    
    /**
     * This is an internal method that allows for the given parent to be repainted, assuming West Graphics is allowed to repaint.
     * @param parent
     */
    public static void doRepaintJob(Component parent)
    {
        if(allowComponentsToRepaint)
        {
            parent.repaint();
        }
    }
    
    /**
     * This function adds a WGObject to the internal cursor array, and is used to verify that the cursor maintains the proper look at all times
     * @param obj The Object to be added
     */
    static void add(WGDrawingObject obj)
    {
        allClickables.addNode(new WGObjectBoundNode(obj));
    }
    
    /**
     * This function removes a WGObject to the internal cursor array, and is used to verify that the cursor maintains the proper look at all times
     * @param obj The Object to be removed
     */
    static void remove(WGDrawingObject obj)
    {
        allClickables.removeNode(new WGObjectBoundNode(obj));
    }
    
    static void setProperHoverDuringScroll(Component parent)
    {
        if(lastMouseEvent == null)
        {
            return;
        }
        Point point = lastMouseEvent.getPoint();
        Point2D.Double realPoint = new Point2D.Double(point.getX(), point.getY());
        WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
        if(lastHoverOject != null && absoluteClickCursor != null && absoluteClickCursor instanceof WGBox)
        {
            lastHoverOject.setHovered(false);
            WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
            absoluteBoxObject.setHovered(true);
            lastHoverOject = absoluteBoxObject;
        }
        else if(absoluteClickCursor != null && absoluteClickCursor instanceof WGBox)
        {
            WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
            lastHoverOject = absoluteBoxObject;
        }
        else if(absoluteClickCursor == null && lastHoverOject != null)
        {
            lastHoverOject.setHovered(false); //We went off the object, so make it so
        }
    }
    
    /**
     * This function checks the given point to verify that the cursor is in the right form. (This form uses the last mouse event)
     * @param parent The parent for the Utility to change the cursor of
     * @param sourceObject The source object that defines whether or not the correct listener does the operation
     */
    static void checkCursor(Component parent, WGDrawingObject sourceObject)
    {
        if(lastMouseEvent == null || currentActiveParent != parent) //IF the active parent is not the current one, then the system will ignore the requests
        {
            //We have also already set the hover, so make sure to remember that:
            if(sourceObject instanceof WGBox)
            {
                WGBox boxObject = (WGBox)sourceObject;
                boxObject.setHovered(false);
            }
            return; //We have already set the cursor, no touch!
        }
        Point point = lastMouseEvent.getPoint();
        Point2D.Double realPoint = new Point2D.Double(point.getX(), point.getY());
        WGDrawingObject clickCursor = allClickables.contains(realPoint, parent);
        if(clickCursor != null)
        {
            if(sourceObject == clickCursor) //Makes sure that the correct clickListener tells us what to do
            {
                parent.setCursor(sourceObject.getClickListener().getCursorType());
                WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
                if(absoluteClickCursor instanceof WGBox)
                {
                    WGBox boxObject = (WGBox)clickCursor;
                    boxObject.setHovered(false);
                    WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
                    absoluteBoxObject.setHovered(true);
                    lastHoverOject = absoluteBoxObject;
                }
            }
            else
            {
                if(sourceObject instanceof WGBox)
                {
                    WGBox boxObject = (WGBox)sourceObject;
                    boxObject.setHovered(false);
                }
            }
        }
        else
        {
            if(sourceObject instanceof WGBox)
            {
                WGBox boxObject = (WGBox)sourceObject;
                boxObject.setHovered(false);
            }
            parent.setCursor(defaultCursor);
        }
    }
    
    /**
     * This function checks the given point to verify that the cursor is in the right form
     * @param e The mouseEvent that was used for the click process
     * @param parent The parent for the Utility to change the cursor of
     * @param sourceObject The source object that defines whether or not the correct listener does the operation
     */
    static void checkCursor(MouseEvent e, Component parent, WGDrawingObject sourceObject)
    {
        if(lastMouseEvent == e || currentActiveParent != parent) //IF the active parent is not the current one, then the system will ignore the requests
        {
            //We have also already set the hover, so make sure to remember that:
            if(sourceObject instanceof WGBox)
            {
                //Verify that we can do this:
                Point point = e.getPoint();
                Point2D.Double realPoint = new Point2D.Double(point.getX(), point.getY());
                WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
                if(absoluteClickCursor != null)
                {
                    WGBox boxObject = (WGBox)absoluteClickCursor;
                    boxObject.setHovered(true);
                    lastHoverOject = boxObject;
                }
                if(absoluteClickCursor != sourceObject)
                {
                    WGBox boxObject = (WGBox)sourceObject;
                    boxObject.setHovered(false);
                }
            }
            return; //We have already set the cursor, no touch!
        }
        Point point = e.getPoint();
        Point2D.Double realPoint = new Point2D.Double(point.getX(), point.getY());
        WGDrawingObject clickCursor = allClickables.contains(realPoint, parent);
        if(clickCursor != null)
        {
            boolean onPane = clickCursor.getClickListener().getParentOwningPane() != null;
            if(sourceObject == clickCursor) //Makes sure that the correct clickListener tells us what to do
            {
                lastMouseEvent = e;
                if(!onPane)
                {
                    parent.setCursor(sourceObject.getClickListener().getCursorType());
                }
                WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
                if(absoluteClickCursor instanceof WGBox)
                {
                    if(clickCursor != absoluteClickCursor)
                    {
                        WGBox boxObject = (WGBox)clickCursor;
                        boxObject.setHovered(false);
                        WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
                        absoluteBoxObject.setHovered(true);
                        lastHoverOject = absoluteBoxObject;
                    }
                    else
                    {
                        WGBox boxObject = (WGBox)clickCursor;
                        boxObject.setHovered(true);
                        lastHoverOject = boxObject;
                    }
                }
                if(onPane && absoluteClickCursor != null)
                {
                    //Then make sure to set the click cursor:
                    parent.setCursor(sourceObject.getClickListener().getCursorType());
                }
                else if(onPane)
                {
                    parent.setCursor(defaultCursor);
                }
            }
            else
            {
                if(sourceObject instanceof WGBox)
                {
                    WGBox boxObject = (WGBox)sourceObject;
                    boxObject.setHovered(false);
                }
            }
        }
        else
        {
            if(sourceObject instanceof WGBox)
            {
                WGBox boxObject = (WGBox)sourceObject;
                boxObject.setHovered(false);
            }
            parent.setCursor(defaultCursor);
        }
    }
    
    
    //Static setters:
    public static void setAllowComponentsToRepaint(boolean allowComponentsToRepaint) {
        WestGraphics.allowComponentsToRepaint = allowComponentsToRepaint;
    }

    public static void setCurrentActiveParent(Component currentActiveParent) {
        WestGraphics.currentActiveParent = currentActiveParent;
    }
    
    
    //Static getters
    public static boolean isAllowComponentsToRepaint() {
        return allowComponentsToRepaint;
    }
    
    public static Cursor getDefaultCursor() {
        return defaultCursor;
    }

    public static Cursor getHoverCursor() {
        return hoverCursor;
    }

    public static Cursor getTextCursor() {
        return textCursor;
    }
    
    
    
    
    
    //Individual drawing based stuff:
    private Graphics2D g2;
    private Queue<WGToolTip> toolTipOrder = new Queue<WGToolTip>();
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
        draw(obj, true);
    }
    /**
     * Draws the given WGDrawingObject based on internal functions
     * @param obj The object to be drawn
     * @param drawToolTip Draw the tool tip associated with this object?
     */
    public void draw(WGDrawingObject obj, boolean drawToolTip)
    {
        if(obj.isShown())
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
                drawPane((WGPane)obj, false);
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
            else if(obj instanceof WGCheckBox)
            {
                drawCheckBox((WGCheckBox)obj);
            }
            else if(obj instanceof WGKeyInput)
            {
                drawKeyInput((WGKeyInput)obj);
            }
            else if(obj instanceof WGTextImage)
            {
                drawTextImage((WGTextImage)obj);
            }
            else if(obj instanceof WGDropDown)
            {
                drawDropDown((WGDropDown)obj);
            }
            
            //Now make sure to draw the tool tip that is part of this object:
            if(drawToolTip)
            {
                WGToolTip addedToolTip = obj.getToolTip();
                if(addedToolTip != null && addedToolTip.isShown())
                {
                    //Make sure to add it to the toolTip list so that it can be drawn correctly, over everything else:
                    toolTipOrder.enqueue(addedToolTip);
                }
            }
        }
    }
    
    /**
     * This function draws all of the tooltips that were found during the drawing process. This allows for tooltips to be on the correct layer
     */
    public void drawAllToolTips()
    {
        while(!toolTipOrder.isEmpty())
        {
            drawToolTip(toolTipOrder.dequeue());
        }
    }
    
    /**
     * This draws an image with both a shader and a resize
     * @param image The image to be drawn
     * @param imageOps The shader
     * @param x The x location
     * @param y The y location
     * @param widthRescale The rescale of the width
     * @param heightRescale The rescale of the height
     */
    public void drawImage(BufferedImage image, BufferedImageOp imageOps, int x, int y, double widthRescale, double heightRescale)
    {
        drawImage(image, imageOps, x, y, widthRescale, heightRescale, 0);
    }
    
    /**
     * This draws an image with both a shader and a resize
     * @param image The image to be drawn
     * @param imageOps The shader
     * @param x The x location
     * @param y The y location
     * @param widthRescale The rescale of the width
     * @param heightRescale The rescale of the height
     * @param rotation The rotation of the image
     */
    public void drawImage(BufferedImage image, BufferedImageOp imageOps, int x, int y, double widthRescale, double heightRescale, double rotation)
    {
        AffineTransform originalTransform = g2.getTransform();
        AffineTransform currentTransform = new AffineTransform(widthRescale,0,0,heightRescale, x * widthRescale, y * heightRescale);
        currentTransform.rotate(rotation);
        g2.transform(currentTransform);
        
        g2.drawImage(image, imageOps, 0, 0);
        g2.setTransform(originalTransform);
    }
    
    /**
     * This draws an image with a shader along with a bunch of rescales using the WGRescaleOptions object
     * @param image The image to be drawn
     * @param imageOps The shader
     * @param x The x location
     * @param y The y location
     * @param rotation The rotation of the image
     * @param rescaleOptions The options for rescaling the image
     */
    public void drawImage(BufferedImage image, BufferedImageOp imageOps, int x, int y, double rotation, WGRescaleOptions rescaleOptions)
    {
        AffineTransform originalTransform = g2.getTransform();
        AffineTransform currentTransform = new AffineTransform(rescaleOptions.getWidthRescale(),0,0,rescaleOptions.getHeightRescale(), x * rescaleOptions.getXRescale(), y * rescaleOptions.getYRescale());
        currentTransform.rotate(rotation);
        g2.transform(currentTransform);
        
        g2.drawImage(image, imageOps, 0, 0);
        g2.setTransform(originalTransform);
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
        g2.setPaint(loadingBar.getBackgroundColor());
        g2.fill(originalBarRect);
        
        double percentWidth = originalBarRect.getWidth() * (loadingBar.isHorizontal() ? loadingBar.getPercentFilled() : 1);
        double percentHeight = originalBarRect.getHeight() * (loadingBar.isHorizontal() ? 1 : loadingBar.getPercentFilled());
        Rectangle2D.Double percentRect = new Rectangle2D.Double(originalBarRect.getX(), originalBarRect.getY(), percentWidth, percentHeight);
        g2.setPaint(loadingBar.getBarColor());
        g2.fill(percentRect);
        
        g2.setPaint(loadingBar.getBorderColor());
        g2.setStroke(new BasicStroke((float)loadingBar.getBorderSize()));
        g2.draw(originalBarRect);
        
        g2.setPaint(loadingBar.getTitleColor());
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
            g2.setPaint(announcementCard.getBackgroundColor());
            g2.fill(backgroundRect);
            g2.setPaint(announcementCard.getBorderColor());
            g2.setStroke(new BasicStroke((float)announcementCard.getBorderSize()));
            g2.draw(backgroundRect);
        }
        
        //Now draw the title:
        double titleX = announcementCard.getX() + ((totalWidth - titleWidth) / 2);
        double titleY = announcementCard.getY() + titleFM.getHeight() - subTitleFM.getDescent() - subTitleFM.getLeading() - titleFM.getDescent() - titleFM.getLeading();
        g2.setPaint(announcementCard.getTitleColor());
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
            g2.setPaint(announcementCard.getSplitColor());
            g2.draw(path);
        }
        
        //Finish with the Subtitle:
        double subTitleX = announcementCard.getX() + ((totalWidth - subTitleWidth) / 2);
        double subTitleY = splitY + subTitleFM.getHeight();
        g2.setFont(announcementCard.getSubTitleFont());
        g2.setPaint(announcementCard.getSubTitleColor());
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
        if(button.isHovered())
        {
            g2.setPaint(button.getHoverBackgroundColor());
        }
        else
        {
            g2.setPaint(button.getBackgroundColor());
        }
        g2.fill(buttonRect);
        g2.setPaint(button.getBorderColor());
        g2.setStroke(new BasicStroke((float)button.getBorderSize()));
        g2.draw(buttonRect);
        
        //Draw the contents:
        if(button.getDisplayedImage() != null)
        {
            //The image:
            AffineTransform transformation = new AffineTransform(button.getImageXScale(),0,0,button.getImageYScale(), button.getImageX(), button.getImageY());
            g2.drawImage(button.getDisplayedImage(), transformation, null);
            //Draw a faint overlay of the background:
            Paint background = button.getBackgroundColor();
            if(background instanceof Color)
            {
                Color backgroundColor = (Color)background;
                if(button.isHovered())
                {
                    backgroundColor = (Color)button.getHoverBackgroundColor();
                }
                Paint pictureOverlay = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 70);
                g2.setPaint(pictureOverlay);
                g2.fill(buttonRect);
            }
        }
        else if(button.getTextColor() != null && button.getTextFont() != null && button.getText() != null)
        {
            //The text:
            g2.setPaint(button.getTextColor());
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
        g2.setPaint(toolTip.getBackgroundColor());
        g2.fill(buttonRect);
        g2.setPaint(toolTip.getBorderColor());
        g2.setStroke(new BasicStroke((float)toolTip.getBorderSize()));
        g2.draw(buttonRect);
        
        //Now the text:
        g2.setPaint(toolTip.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(toolTip.getToolTipFont());
        double textX = toolTip.getX();
        if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_LEFT)
        {
            textX = toolTip.getX() + toolTip.getBorderSize();
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
                textX = toolTip.getX() + toolTip.getWidth() - toolTip.getBorderSize() - textFM.stringWidth(text[i]);
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
    private void drawPane(WGPane pane, boolean withinAnotherPane)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
        
        //Save the original clip in case the user wanted that one:
        Shape oldClip = g2.getClip();
        
        //Draw the background:
        Rectangle2D.Double buttonRect = new Rectangle2D.Double(pane.getX(), pane.getY(), pane.getWidth(), pane.getHeight());
        if(pane.isHovered())
        {
            g2.setPaint(pane.getHoverBackgroundColor());
        }
        else
        {
            g2.setPaint(pane.getBackgroundColor());
        }
        g2.fill(buttonRect);
        
        
        //To make sure nothing goes off the pane:
        if(!withinAnotherPane || oldClip.contains(pane.getBounds())) //If not within another pane or the other pane's clip contains our clip, then we can redo the clip
        {
            g2.setClip(pane.getBounds());
        }
        
        //Now the internal components:
        for(int i = 0 ; i < pane.getComponentNumber() ; i++)
        {
            WGDrawingObject obj = pane.getComponent(i);
            if(obj instanceof WGPane)
            {
                drawPane((WGPane)obj, true);
                //Remember to collect the toolTip:
                WGToolTip addedToolTip = obj.getToolTip();
                if(addedToolTip != null && addedToolTip.isShown())
                {
                    //Make sure to add it to the toolTip list so that it can be drawn correctly, over everything else:
                    toolTipOrder.enqueue(addedToolTip);
                }
            }
            else
            {
                draw(obj);
            }
        }
        
        //Draw the scrollBars
        drawScrollBar(pane.getVerticalScroll(), pane);
        drawScrollBar(pane.getHorizontalScroll(), pane);
        
        
        //Reload the old clip, as it is no longer useful:
        g2.setClip(oldClip);
        
        
        //Then draw in the border:
        g2.setPaint(pane.getBorderColor());
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
            g2.setPaint(pane.getScrollBarColor());
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
            g2.setPaint(textArea.getScrollBarColor());
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
        g2.setPaint(label.getTextColor());
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
        Paint backgroundColor = (textInput.isFocused()) ? textInput.getBackgroundOnFocusColor() : textInput.getBackgroundColor();
        if(textInput.isHovered() && !textInput.isFocused())
        {
            g2.setPaint(textInput.getHoverBackgroundColor());
        }
        else
        {
            g2.setPaint(backgroundColor);
        }
        g2.fill(buttonRect);
        Paint borderColor = textInput.getBorderColor();
        g2.setPaint(borderColor);
        g2.setStroke(new BasicStroke((float)textInput.getBorderSize()));
        g2.draw(buttonRect);
            
        //Draw the highlight:
        if(textInput.isHighlightShown() && textInput.isFocused())
        {
            g2.setPaint(textInput.getHighlightColor());
            g2.fill(textInput.getHighlightBounds());
        }
        
        //Now the text:
        g2.setPaint(textInput.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(textInput.getTextFont());
        double textX = textInput.getX() + ((textInput.getWidth() - textFM.stringWidth(textInput.getText())) / 2);
        double textY = textInput.getY() + ((textFM.getAscent() - textFM.getDescent() + textInput.getHeight()) / 2);
        g2.setFont(textInput.getTextFont());
        g2.drawString(textInput.getText(), (float)textX, (float)textY);
            
        //Draw the cursor
        if(textInput.isCursorShown() && textInput.isFocused())
        {
            g2.setPaint(textInput.getCursorColor());
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
        
        if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_LEFT)
        {
            textX = textArea.getX() + textArea.getBorderSize();
        }
        
        g2.setFont(textArea.getTextFont());
        textY += textFM.getHeight() - textArea.getStringYOffset();
        ArrayList<String> allText;
        ArrayList<Paint> allTextColors;
        if(textArea.isTextWrapped())
        {
            allText = textArea.getFormatedText();
            allTextColors = textArea.getFormatedColors();
        }
        else
        {
            allText = textArea.getText();
            allTextColors = textArea.getTextColors();
        }
        for(int i = 0 ; i < allText.size() ; i++)
        {
            if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
            {
                textX = textArea.getX() + ((textArea.getWidth() - textFM.stringWidth(allText.get(i))) / 2);
            }
            else if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
            {
                int stringWidth = textFM.stringWidth(allText.get(i));
                textX = textArea.getX() + textArea.getWidth() - stringWidth - textArea.getBorderSize();
            }
            g2.setPaint(allTextColors.get(i));
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
    public void drawCheckBox(WGCheckBox checkBox)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
        //Draw the button
        Rectangle2D.Double buttonRect = checkBox.getBounds();
        if(checkBox.isHovered())
        {
            g2.setPaint(checkBox.getHoverBackgroundColor());
        }
        else
        {
            g2.setPaint(checkBox.getBackgroundColor());
        }
        g2.fill(buttonRect);
        g2.setPaint(checkBox.getBorderColor());
        g2.setStroke(new BasicStroke((float)checkBox.getBorderSize()));
        g2.draw(buttonRect);
        
        //Draw the Check when needed:
        if(checkBox.isChecked())
        {
            g2.setPaint(checkBox.getCheckColor());
            Path2D.Double path = new Path2D.Double();
            path.moveTo(buttonRect.getX(), buttonRect.getY());
            path.lineTo(buttonRect.getX() + buttonRect.getWidth(), buttonRect.getY() + buttonRect.getHeight());
            g2.draw(path);
            path.reset();
            path.moveTo(buttonRect.getX() + buttonRect.getWidth(), buttonRect.getY());
            path.lineTo(buttonRect.getX(), buttonRect.getY() + buttonRect.getHeight());
            g2.draw(path);
        }
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    private void drawKeyInput(WGKeyInput textInput)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
            
        //Draw the Box
        Rectangle2D.Double buttonRect = new Rectangle2D.Double(textInput.getX(), textInput.getY(), textInput.getWidth(), textInput.getHeight());
        Paint backgroundColor = (textInput.isFocused()) ? textInput.getBackgroundOnFocusColor() : textInput.getBackgroundColor();
        if(textInput.isHovered() && !textInput.isFocused())
        {
            g2.setPaint(textInput.getHoverBackgroundColor());
        }
        else
        {
            g2.setPaint(backgroundColor);
        }
        g2.fill(buttonRect);
        Paint borderColor = textInput.getBorderColor();
        g2.setPaint(borderColor);
        g2.setStroke(new BasicStroke((float)textInput.getBorderSize()));
        g2.draw(buttonRect);
        
        //Now the text:
        g2.setPaint(textInput.getTextColor());
        FontMetrics textFM = g2.getFontMetrics(textInput.getTextFont());
        double textX = textInput.getX() + ((textInput.getWidth() - textFM.stringWidth(textInput.getText())) / 2);
        double textY = textInput.getY() + ((textFM.getAscent() - textFM.getDescent() + textInput.getHeight()) / 2);
        g2.setFont(textInput.getTextFont());
        g2.drawString(textInput.getText(), (float)textX, (float)textY);
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
    private void drawTextImage(WGTextImage textImage)
    {
        //Draw the Image:
        BufferedImage image = textImage.getDisplayImage();
        if(image == null) //Don't draw something that will result in an error!
        {
            return;
        }
        AffineTransform transformation = new AffineTransform(textImage.getImageXScale(),0,0,textImage.getImageYScale(), textImage.getX() + textImage.getImageOffSetX(), textImage.getY() + textImage.getImageOffSetY());
        g2.drawImage(image, transformation, null);
        
        //Now the text:
        g2.setPaint(textImage.getTextColor());
        double textX = textImage.getTextX();
        double textY = textImage.getTextY();
        g2.setFont(textImage.getTextFont());
        g2.drawString(textImage.getImageText(), (float)(textImage.getX() + textX + textImage.getImageOffSetX()), (float)(textImage.getY() + textY + textImage.getImageOffSetY()));
    }
    private void drawDropDown(WGDropDown dropDown)
    {
        //Save the original stroke in case the user wanted that one
        Stroke oldStroke = g2.getStroke();
        
        if(!dropDown.isDroppedDown())
        {
            //Simple to draw:

            //Draw the button
            Rectangle2D.Double buttonRect = dropDown.getBounds();
            if(dropDown.isHovered())
            {
                g2.setPaint(dropDown.getHoverBackgroundColor());
            }
            else
            {
                g2.setPaint(dropDown.getBackgroundColor());
            }
            g2.fill(buttonRect);
            g2.setPaint(dropDown.getBorderColor());
            g2.setStroke(new BasicStroke((float)dropDown.getBorderSize()));
            g2.draw(buttonRect);

            //Draw the text:
            g2.setPaint(dropDown.getTextColor());
            FontMetrics textFM = g2.getFontMetrics(dropDown.getTextFont());
            String text = dropDown.getSelectedChoiceText();
            double textX = dropDown.getX() + ((dropDown.getWidth() - textFM.stringWidth(text)) / 2);
            double textY = dropDown.getY() + ((textFM.getAscent() - textFM.getDescent() + dropDown.getHeight()) / 2);
            g2.setFont(dropDown.getTextFont());
            g2.drawString(text, (float)textX, (float)textY);
        }
        else
        {
            //More complex to draw:
            
            //Start by breaking the whole entire object into the individual buttons that make it up:
            double buttonX = dropDown.getX();
            double buttonY = dropDown.getY();
            String[] choices = dropDown.getChoices();
            Paint[] textColors = dropDown.getTextColors();
            for(int i = 0 ; i < choices.length ; i++)
            {
                Rectangle2D.Double buttonBounds = new Rectangle2D.Double(buttonX, buttonY, dropDown.getButtonWidth(), dropDown.getButtonHeight());
                
                //Draw the button:
                if(dropDown.isHovered() && dropDown.getHoveredIndex() == i)
                {
                    g2.setPaint(dropDown.getHoverBackgroundColor());
                }
                else
                {
                    g2.setPaint(dropDown.getBackgroundColor());
                }
                g2.fill(buttonBounds);
                g2.setPaint(dropDown.getBorderColor());
                g2.setStroke(new BasicStroke((float)dropDown.getBorderSize()));
                g2.draw(buttonBounds);
                
                //Draw the text:
                g2.setPaint(textColors[i]);
                FontMetrics textFM = g2.getFontMetrics(dropDown.getTextFont());
                String text = choices[i];
                double textX = buttonBounds.getX() + ((dropDown.getButtonWidth() - textFM.stringWidth(text)) / 2);
                double textY = buttonBounds.getY() + ((textFM.getAscent() - textFM.getDescent() + dropDown.getButtonHeight()) / 2);
                g2.setFont(dropDown.getTextFont());
                g2.drawString(text, (float)textX, (float)textY);
                
                //Now set up the next button's starting point:
                buttonY += dropDown.getButtonHeight();
            }
        }
        
        //And reload it at the end
        g2.setStroke(oldStroke);
    }
}
