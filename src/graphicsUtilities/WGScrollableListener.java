/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This can only be used with a WGPane, however it makes it so that the pane can easily scroll anything, WGButtons or more complex items such as another WGPane
 * @author Westley
 */
public class WGScrollableListener implements MouseWheelListener
{
    private WGPane parentPane;
    private double scrollY = 0;
    private double scrollSpeed = 1;
    private double totalArea = 0;
    private double seeableArea = 0;
    private double scrollBarHeight = 0;
    private double scrollBarY = 0;
    private boolean vertical = true;
    
    /**
     * This creates a scrollBar for the given WGPane
     * @param parentPane The pane that is adding the scrollListener
     */
    public WGScrollableListener(WGPane parentPane)
    {
        this.parentPane = parentPane;
    }
    
    @Override
    /**
     * Scrolls the pane according to the setUp data
     */
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        if(isWithinBounds(e))
        {
            if(vertical)
            {
                double movement = e.getWheelRotation() * scrollSpeed;
                double minY = 0;
                double maxY = totalArea - seeableArea;
                //Makes sure not to "over-scroll"
                if(scrollY + movement < minY)
                {
                    scrollY = minY;
                    //Now set all of the components to the correct location:
                    int iEnd = parentPane.getComponentNumber();
                    for(int i = 0 ; i < iEnd ; i++)
                    {
                        WGDrawingObject obj = parentPane.getComponent(i);
                        obj.setY(obj.getY() + Math.abs(minY - scrollY));
                    }
                    scrollBarY = 0;
                    return;
                }
                else if(scrollY + movement > maxY)
                {
                    scrollY = maxY;
                    //Now set all of the components to the correct location:
                    int iEnd = parentPane.getComponentNumber();
                    for(int i = 0 ; i < iEnd ; i++)
                    {
                        WGDrawingObject obj = parentPane.getComponent(i);
                        obj.setY(obj.getY() - Math.abs(maxY - scrollY));
                    }
                    scrollBarY = seeableArea - scrollBarHeight;
                    return;
                }
                //Now scroll:
                scrollY += movement;
                //Change into a form that the scrollBar understands:
                double scrollBarMovement = movement * seeableArea / totalArea;
                scrollBarY += scrollBarMovement;
                
                //Now set all of the components to the correct location:
                int iEnd = parentPane.getComponentNumber();
                for(int i = 0 ; i < iEnd ; i++)
                {
                    WGDrawingObject obj = parentPane.getComponent(i);
                    obj.setY(obj.getY() - movement);
                }
            }
        }
    }
    
    /**
     * Sets up the scrollBar based on the given information:
     * @param vertical Tells wether it scrolls vertically or horizontally
     * @param components The list of components that are part of the pane
     */
    public void setUpScroll(boolean vertical, ArrayList<WGDrawingObject> components)
    {
        this.vertical = vertical;
        if(vertical)
        {
            seeableArea = parentPane.getHeight();
            //Finds the totalArea
            double smallestY = 0;
            double biggestY = 0;
            for(int i = 0 ; i < components.size() ; i++)
            {
                double newSmallestY = components.get(i).getY();
                double newbiggestY = newSmallestY + components.get(i).getHeight() - parentPane.getY();
                if(newSmallestY < smallestY)
                {
                    smallestY = newSmallestY;
                }
                if(newbiggestY > biggestY)
                {
                    biggestY = newbiggestY;
                }
            }
            totalArea = biggestY - smallestY;
            scrollBarHeight = seeableArea / totalArea * seeableArea;
            scrollY = 0;
            scrollBarY = 0;
        }
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinBounds(MouseEvent e)
    {
        Point2D clickLoaction = e.getPoint();
        Rectangle2D.Double objectBounds = (Rectangle2D.Double)parentPane.getBounds();
        return objectBounds.contains(clickLoaction);
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

    public boolean isVertical() {
        return vertical;
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
        scrollSpeed = totalArea * interval;
    }
}
