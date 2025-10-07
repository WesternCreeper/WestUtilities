/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Westley
 */
public class WGTextScrollableListener implements MouseWheelListener, MouseMotionListener, MouseListener
{
    private WGTextArea parentTextArea;
    private double scrollY = 0;
    private double scrollSpeed = 1;
    private double totalArea = 0;
    private double seeableArea = 0;
    private double scrollBarHeight = 0;
    private double scrollBarY = 0;
    private double yStart = 0;
    private boolean shown = false;
    private boolean allowedToScroll = false;
    
    /**
     * This creates a scrollBar for the given WGTextArea
     * @param parentTextArea The text area that is adding the scrollListener
     */
    public WGTextScrollableListener(WGTextArea parentTextArea)
    {
        this.parentTextArea = parentTextArea;
    }
    
    @Override
    /**
     * Scrolls the pane according to the setUp data
     */
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        if(isWithinBounds(e) && !e.isConsumed())
        {
            doScroll(e.getWheelRotation(), true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        if(allowedToScroll && isWithinBounds(e) && !e.isConsumed())
        {
            double distance = 0;
            double currentY = e.getPoint().getY();
            distance = currentY - yStart;
            yStart = currentY;
            distance /= seeableArea / totalArea;
            doScroll(distance, false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if(isWithinScrollBarBounds(e) && !e.isConsumed())
        {
            yStart = e.getPoint().getY();
            allowedToScroll = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        allowedToScroll = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    /**
     * Sets up the scrollBar based on the given information:
     * @param text The array of strings that are on the TextArea
     */
    public void setUpScroll(ArrayList<String> text)
    {
        seeableArea = parentTextArea.getHeight();
        //Finds the totalArea
        FontMetrics textFM = parentTextArea.getParent().getFontMetrics(parentTextArea.getTextFont());
        
        //The addition of descent is entirely redundent. However, it guarentees that the text shown does not have tails going off the page (Such as the letters "p", "g", etc.)
        totalArea = text.size() * (textFM.getHeight()) + textFM.getDescent(); 
        
        scrollBarHeight = seeableArea / totalArea * seeableArea;
        scrollY = 0;
        scrollBarY = 0;
        
        shown = totalArea > seeableArea;
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinBounds(MouseEvent e)
    {
        Point2D clickLoaction = e.getPoint();
        Rectangle2D.Double objectBounds = (Rectangle2D.Double)parentTextArea.getBounds();
        return objectBounds.contains(clickLoaction);
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinScrollBarBounds(MouseEvent e)
    {
        Point2D clickLoaction = e.getPoint();
        double scrollBoundsX = 0;
        double scrollBoundsY = 0;
        double scrollBoundsWidth = 0;
        double scrollBoundsHeight = 0;
        scrollBoundsX = parentTextArea.getX();
        scrollBoundsY = parentTextArea.getY();
        scrollBoundsWidth = parentTextArea.getBorderSize();
        scrollBoundsHeight = scrollBoundsWidth;
        double scrollBarHeight = seeableArea / totalArea * seeableArea;
        
        scrollBoundsY += scrollBarY;
        scrollBoundsHeight = scrollBarHeight;
        scrollBoundsX += parentTextArea.getWidth() - scrollBoundsWidth - parentTextArea.getBorderSize();
        
        Rectangle2D.Double objectBounds = new Rectangle2D.Double(scrollBoundsX, scrollBoundsY, scrollBoundsWidth, scrollBoundsHeight);
        return objectBounds.contains(clickLoaction);
    }
    
    private void doScroll(double mouseMovement, boolean useScrollSpeed)
    {
        if(!shown)
        {
            return;
        }
        Component parent = parentTextArea.getParent();
        double movement = mouseMovement;
        if(useScrollSpeed)
        {
            movement *= scrollSpeed;
        }
        double minY = 0;
        double maxY = totalArea - seeableArea;
        //Makes sure not to "over-scroll"
        if(scrollY + movement < minY)
        {
            //Now set all of the components to the correct location:
            parentTextArea.setStringYOffset(minY);
            scrollY = minY;
            scrollBarY = 0;
            WestGraphics.doRepaintJob(parent);
            return;
        }
        else if(scrollY + movement > maxY)
        {
            //Now set all of the components to the correct location:
            parentTextArea.setStringYOffset(maxY);
            scrollY = maxY;
            scrollBarY = seeableArea - scrollBarHeight;
            WestGraphics.doRepaintJob(parent);
            return;
        }
        //Now scroll:
        scrollY += movement;
        //Change into a form that the scrollBar understands:
        double scrollBarMovement = movement * seeableArea / totalArea;
        scrollBarY += scrollBarMovement;

        //Now set all of the components to the correct location:
        parentTextArea.setStringYOffset(scrollY);
        shown = totalArea > seeableArea;
        WestGraphics.doRepaintJob(parent);
    }
    
    //Getters:
    public double getScrollY() {
        return scrollY;
    }

    public double getScrollSpeed() {
        return scrollSpeed;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public double getSeeableArea() {
        return seeableArea;
    }

    public double getScrollBarHeight() {
        return scrollBarHeight;
    }

    public double getScrollBarY() {
        return scrollBarY;
    }
    public boolean isShown() {
        return shown;
    }
    
    //Setters:
    /**
     * This sets the scroll speed to the speed given 
     * @param speed The new speed
     */
    public void setScrollSpeed(double speed)
    {
        scrollSpeed = speed;
    }
    /**
     * This sets the scroll speed to a percentage of the total Area. Thus a number greater than one will scroll the whole page
     * @param interval The percent of the total area that the scrollSpeed is set to
     */
    public void setScrollInterval(double interval)
    {
        scrollSpeed = (totalArea - seeableArea) * interval;
    }
}
