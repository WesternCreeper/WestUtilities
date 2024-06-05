/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This can only be used with a WGPane, however it makes it so that the pane can easily scroll anything, WGButtons or more complex items such as another WGPane
 * @author Westley
 */
public class WGScrollableListener implements MouseWheelListener, MouseMotionListener, MouseListener
{
    private WGPane parentPane;
    private double scrollY = 0;
    private double scrollSpeed = 1;
    private double totalArea = 0;
    private double seeableArea = 0;
    private double scrollBarHeight = 0;
    private double scrollBarY = 0;
    private double yStart = 0;
    private boolean vertical = true;
    private boolean shown = false;
    private boolean preferred = true;
    private boolean normalPreference = true;
    private boolean allowedToScroll = false;
    
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
        if(preferred && isWithinBounds(e) && !e.isConsumed())
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
            if(vertical)
            {
                double currentY = e.getPoint().getY();
                distance = currentY - yStart;
                yStart = currentY;
                distance /= seeableArea / totalArea;
                doScroll(distance, false);
            }
            else
            {
                double currentX = e.getPoint().getX();
                distance = currentX - yStart;
                yStart = currentX;
                distance /= seeableArea / totalArea;
                doScroll(distance, false);
            }
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
            if(vertical)
            {
                yStart = e.getPoint().getY();
                allowedToScroll = true;
            }
            else
            {
                yStart = e.getPoint().getX();
                allowedToScroll = true;
            }
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
     * @param vertical Tells wether it scrolls vertically or horizontally
     * @param components The list of components that are part of the pane
     */
    public void setUpScroll(boolean vertical, ArrayList<WGDrawingObject> components)
    {
        this.vertical = vertical;
        preferred = (vertical && normalPreference) || (!vertical && !normalPreference);
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
        else
        {
            seeableArea = parentPane.getWidth();
            //Finds the totalArea
            double smallestX = 0;
            double biggestX = 0;
            for(int i = 0 ; i < components.size() ; i++)
            {
                double newSmallestX = components.get(i).getX();
                double newbiggestX = newSmallestX + components.get(i).getWidth() - parentPane.getX();
                if(newSmallestX < smallestX)
                {
                    smallestX = newSmallestX;
                }
                if(newbiggestX > biggestX)
                {
                    biggestX = newbiggestX;
                }
            }
            totalArea = biggestX - smallestX;
            scrollBarHeight = seeableArea / totalArea * seeableArea;
            scrollY = 0;
            scrollBarY = 0;
        }
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
        Rectangle2D.Double objectBounds = (Rectangle2D.Double)parentPane.getBounds();
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
        scrollBoundsX = parentPane.getX();
        scrollBoundsY = parentPane.getY();
        scrollBoundsWidth = parentPane.getBorderSize();
        scrollBoundsHeight = scrollBoundsWidth;
        double scrollBarHeight = seeableArea / totalArea * seeableArea;
        if(vertical)
        {
            scrollBoundsY += scrollBarY;
            scrollBoundsHeight = scrollBarHeight;
            scrollBoundsX += parentPane.getWidth() - scrollBoundsWidth - parentPane.getBorderSize();
        }
        else
        {
            scrollBoundsX += scrollBarY;
            scrollBoundsWidth = scrollBarHeight;
            scrollBoundsY += parentPane.getHeight() - scrollBoundsHeight - parentPane.getBorderSize();
        }
        Rectangle2D.Double objectBounds = new Rectangle2D.Double(scrollBoundsX, scrollBoundsY, scrollBoundsWidth, scrollBoundsHeight);
        return objectBounds.contains(clickLoaction);
    }
    
    private void doScroll(double mouseMovement, boolean useScrollSpeed)
    {
        if(vertical)
        {
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
                int iEnd = parentPane.getComponentNumber();
                for(int i = 0 ; i < iEnd ; i++)
                {
                    WGDrawingObject obj = parentPane.getComponent(i);
                    obj.setY(obj.getY() + Math.abs(minY - scrollY));
                }
                scrollY = minY;
                scrollBarY = 0;
                return;
            }
            else if(scrollY + movement > maxY)
            {
                //Now set all of the components to the correct location:
                int iEnd = parentPane.getComponentNumber();
                for(int i = 0 ; i < iEnd ; i++)
                {
                    WGDrawingObject obj = parentPane.getComponent(i);
                    obj.setY(obj.getY() - Math.abs(maxY - scrollY));
                }
                scrollY = maxY;
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
        else
        {
            double movement = mouseMovement;
            if(useScrollSpeed)
            {
                movement *= scrollSpeed;
            }
            double minX = 0;
            double maxX = totalArea - seeableArea;
            //Makes sure not to "over-scroll"
            if(scrollY + movement < minX)
            {
                //Now set all of the components to the correct location:
                int iEnd = parentPane.getComponentNumber();
                for(int i = 0 ; i < iEnd ; i++)
                {
                    WGDrawingObject obj = parentPane.getComponent(i);
                    obj.setX(obj.getX() + Math.abs(minX - scrollY));
                }
                scrollY = minX;
                scrollBarY = 0;
                return;
            }
            else if(scrollY + movement > maxX)
            {
                //Now set all of the components to the correct location:
                int iEnd = parentPane.getComponentNumber();
                for(int i = 0 ; i < iEnd ; i++)
                {
                    WGDrawingObject obj = parentPane.getComponent(i);
                    obj.setX(obj.getX() - Math.abs(maxX - scrollY));
                }
                scrollY = maxX;
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
                obj.setX(obj.getX() - movement);
            }
        }
        shown = totalArea > seeableArea;
    }
    public void resetScroll()
    {
        if(vertical)
        {
            //Now set all of the components to the correct location:
            int iEnd = parentPane.getComponentNumber();
            for(int i = 0 ; i < iEnd ; i++)
            {
                WGDrawingObject obj = parentPane.getComponent(i);
                obj.setY(obj.getY() + Math.abs(scrollY));
            }
            scrollY = 0;
            scrollBarY = 0;
            return;
        }
        else
        {
            //Now set all of the components to the correct location:
            int iEnd = parentPane.getComponentNumber();
            for(int i = 0 ; i < iEnd ; i++)
            {
                WGDrawingObject obj = parentPane.getComponent(i);
                obj.setX(obj.getX() + Math.abs(scrollY));
            }
            scrollY = 0;
            scrollBarY = 0;
            return;
        }
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

    public void setPreference(boolean preference) {
        this.normalPreference = preference;
    }
}
