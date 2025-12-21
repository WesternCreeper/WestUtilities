/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * This can only be used with a WGPane, however it makes it so that the pane can easily scroll anything, WGButtons or more complex items such as another WGPane
 * @author Westley
 */
public class WGScrollableListener implements EventHandler<Event>
{
    private WGPane parentPane;
    private double scrollY = 0;
    private double scrollSpeed = 1;
    private double totalArea = 0;
    private double seeableArea = 0;
    private double scrollBarHeight = 0;
    private double scrollBarY = 0;
    private double yStart = 0;
    private double oldTotalArea = 0;
    private double oldScrollY = 0;
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
	public void handle(Event e) 
	{
    	Platform.runLater(() -> {
			if(e.getEventType().equals(ScrollEvent.SCROLL))
			{
				mouseWheelMoved((ScrollEvent)e);
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED))
			{
				mousePressed((MouseEvent)e);
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
			{
				mouseReleased((MouseEvent)e);
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
			{
				mouseDragged((MouseEvent)e);
			}
    	});
	}
    
    /**
     * Scrolls the pane according to the setUp data
     */
    public void mouseWheelMoved(ScrollEvent e) 
    {
        if(preferred && isWithinBounds(e) && !e.isConsumed())
        {
            doScroll(-e.getDeltaY(), true);
            //Cursor:
            WestGraphics.setProperHoverDuringScroll(parentPane.getParent());
        }
    }

    public void mouseDragged(MouseEvent e) 
    {
        if(allowedToScroll && isWithinBounds(e) && !e.isConsumed())
        {
            double distance = 0;
            if(vertical)
            {
                double currentY = e.getY();
                distance = currentY - yStart;
                yStart = currentY;
                distance /= seeableArea / totalArea;
                doScroll(distance, false);
            }
            else
            {
                double currentX = e.getX();
                distance = currentX - yStart;
                yStart = currentX;
                distance /= seeableArea / totalArea;
                doScroll(distance, false);
            }
            //Cursor:
            WestGraphics.checkCursor(e, parentPane.getParent(), parentPane);
        }
    }

    public void mousePressed(MouseEvent e) 
    {
        if(isWithinScrollBarBounds(e) && !e.isConsumed())
        {
            if(vertical)
            {
                yStart = e.getY();
                allowedToScroll = true;
            }
            else
            {
                yStart = e.getX();
                allowedToScroll = true;
            }
            //Cursor:
            WestGraphics.checkCursor(e, parentPane.getParent(), parentPane);
        }
    }

    public void mouseReleased(MouseEvent e) 
    {
        allowedToScroll = false;
    }
    
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
     * Forces the bounds of the scrollbar to change, but does not update positions or where everything is
     * @param vertical Tells wether it scrolls vertically or horizontally
     * @param components The list of components that are part of the pane
     * @param expanding Whether the new bounds are larger or smaller than the old ones
     */
    public void changeScrollBounds(boolean vertical, ArrayList<WGDrawingObject> components, boolean expanding)
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
            double oldTotalArea2 = totalArea;
            totalArea = biggestY - smallestY;
            if(expanding)
            {
                oldTotalArea = oldTotalArea2;
                oldScrollY = scrollY;
                totalArea = biggestY - smallestY + scrollY;
                //Update the total area to factor in the fact that we have already moved all of these y-blocks up, so push it down
                scrollBarHeight = seeableArea / totalArea * seeableArea;
                //Update the old scrollY into the new one:
                scrollBarY = scrollBarY / oldTotalArea2 * totalArea;
            }
            else //Now the problem occures, when the new area is smaller than the old. In this case we will just move everything down by the amount moved over the total area:
            {
                //We move ourselves up by the amount we are over:
                double overAmount = scrollY - oldScrollY;
                //Then update everything with the proper areas:
                totalArea = oldTotalArea;
                //Update the total area to factor in the fact that we have already moved all of these y-blocks up, so push it down
                scrollBarHeight = seeableArea / totalArea * seeableArea;
                //Update the old scrollY into the new one:
                scrollBarY = scrollY * seeableArea / totalArea;
                doScroll(parentPane, overAmount * -1, false);
                
                oldTotalArea = 0;
                oldScrollY = 0;
            }
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
            double oldTotalArea2 = totalArea;
            totalArea = biggestX - smallestX;
            if(totalArea >= oldTotalArea2)
            {
                oldTotalArea = oldTotalArea2;
                oldScrollY = scrollY;
                totalArea = biggestX - smallestX + scrollY;
                //Update the total area to factor in the fact that we have already moved all of these y-blocks up, so push it down
                scrollBarHeight = seeableArea / totalArea * seeableArea;
                //Update the old scrollY into the new one:
                scrollBarY = scrollBarY / oldTotalArea2 * totalArea;
            }
            else //Now the problem occures, when the new area is smaller than the old. In this case we will just move everything down by the amount moved over the total area:
            {
                //We move ourselves up by the amount we are over:
                double overAmount = scrollY - oldScrollY;
                //Then update everything with the proper areas:
                totalArea = oldTotalArea;
                //Update the total area to factor in the fact that we have already moved all of these y-blocks up, so push it down
                scrollBarHeight = seeableArea / totalArea * seeableArea;
                //Update the old scrollY into the new one:
                scrollBarY = scrollY * seeableArea / totalArea;
                doScroll(parentPane, overAmount * -1, false);
                
                oldTotalArea = 0;
                oldScrollY = 0;
            }
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
        Point2D clickLoaction = new Point2D(e.getX(), e.getY());
        Rectangle2D objectBounds = (Rectangle2D)parentPane.getBounds();
        return objectBounds.contains(clickLoaction);
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinBounds(ScrollEvent e)
    {
        Point2D clickLoaction = new Point2D(e.getX(), e.getY());
        Rectangle2D objectBounds = (Rectangle2D)parentPane.getBounds();
        return objectBounds.contains(clickLoaction);
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinScrollBarBounds(MouseEvent e)
    {
        Point2D clickLoaction = new Point2D(e.getX(), e.getY());
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
        Rectangle2D objectBounds = new Rectangle2D(scrollBoundsX, scrollBoundsY, scrollBoundsWidth, scrollBoundsHeight);
        return objectBounds.contains(clickLoaction);
    }
    
    public void doScroll(double mouseMovement, boolean useScrollSpeed)
    {
        doScroll(parentPane, mouseMovement, useScrollSpeed);
    }
    private void doScroll(WGPane parentPane, double mouseMovement, boolean useScrollSpeed)
    {
        if(!shown)
        {
            return;
        }
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
                    
                    //If it is a pane then we will need to go additional work:
                    if(obj instanceof WGPane)
                    {
                        doScroll((WGPane)obj, mouseMovement, useScrollSpeed);
                    }
                    
                    //Make sure to move the drag and drops too:
                    WGDragDropBar bar = obj.getDragAndDropBar();
                    if(bar != null)
                	{
                    	bar.setY(bar.getY() + Math.abs(minY - scrollY));
                	}
                }
                if(parentPane == this.parentPane)
                {
                    scrollY = minY;
                    scrollBarY = 0;
                }
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
                    
                    //If it is a pane then we will need to go additional work:
                    if(obj instanceof WGPane)
                    {
                        doScroll((WGPane)obj, mouseMovement, useScrollSpeed);
                    }
                    
                    //Make sure to move the drag and drops too:
                    WGDragDropBar bar = obj.getDragAndDropBar();
                    if(bar != null)
                	{
                    	bar.setY(bar.getY() - Math.abs(maxY - scrollY));
                	}
                }
                if(parentPane == this.parentPane)
                {
                    scrollY = maxY;
                    scrollBarY = seeableArea - scrollBarHeight;
                }
                return;
            }

            //Now set all of the components to the correct location:
            int iEnd = parentPane.getComponentNumber();
            for(int i = 0 ; i < iEnd ; i++)
            {
                WGDrawingObject obj = parentPane.getComponent(i);
                obj.setY(obj.getY() - movement);
                    
                //If it is a pane then we will need to go additional work:
                if(obj instanceof WGPane)
                {
                    doScroll((WGPane)obj, mouseMovement, useScrollSpeed);
                }
                
                //Make sure to move the drag and drops too:
                WGDragDropBar bar = obj.getDragAndDropBar();
                if(bar != null)
            	{
                	bar.setY(bar.getY() - movement);
            	}
            }
            if(parentPane == this.parentPane)
            {
                //Now scroll:
                scrollY += movement;
                //Change into a form that the scrollBar understands:
                double scrollBarMovement = movement * seeableArea / totalArea;
                scrollBarY += scrollBarMovement;
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
                    
                    //If it is a pane then we will need to go additional work:
                    if(obj instanceof WGPane)
                    {
                        doScroll((WGPane)obj, mouseMovement, useScrollSpeed);
                    }
                    
                    //Make sure to move the drag and drops too:
                    WGDragDropBar bar = obj.getDragAndDropBar();
                    if(bar != null)
                	{
                    	bar.setX(bar.getX() + Math.abs(minX - scrollY));
                	}
                }
                if(parentPane == this.parentPane)
                {
                    scrollY = minX;
                    scrollBarY = 0;
                }
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
                    
                    //If it is a pane then we will need to go additional work:
                    if(obj instanceof WGPane)
                    {
                        doScroll((WGPane)obj, mouseMovement, useScrollSpeed);
                    }
                    
                    //Make sure to move the drag and drops too:
                    WGDragDropBar bar = obj.getDragAndDropBar();
                    if(bar != null)
                	{
                    	bar.setX(bar.getX() - Math.abs(maxX - scrollY));
                	}
                }
                if(parentPane == this.parentPane)
                {
                    scrollY = maxX;
                    scrollBarY = seeableArea - scrollBarHeight;
                }
                return;
            }

            //Now set all of the components to the correct location:
            int iEnd = parentPane.getComponentNumber();
            for(int i = 0 ; i < iEnd ; i++)
            {
                WGDrawingObject obj = parentPane.getComponent(i);
                obj.setX(obj.getX() - movement);
                    
                //If it is a pane then we will need to go additional work:
                if(obj instanceof WGPane)
                {
                    doScroll((WGPane)obj, mouseMovement, useScrollSpeed);
                }
                
                //Make sure to move the drag and drops too:
                WGDragDropBar bar = obj.getDragAndDropBar();
                if(bar != null)
            	{
                	bar.setX(bar.getX() - movement);
            	}
            }
            if(parentPane == this.parentPane)
            {
                //Now scroll:
                scrollY += movement;
                //Change into a form that the scrollBar understands:
                double scrollBarMovement = movement * seeableArea / totalArea;
                scrollBarY += scrollBarMovement;
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
                
                //Make sure to move the drag and drops too:
                WGDragDropBar bar = obj.getDragAndDropBar();
                if(bar != null)
            	{
                	bar.setX(bar.getY() + Math.abs(scrollY));
            	}
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
                
                //Make sure to move the drag and drops too:
                WGDragDropBar bar = obj.getDragAndDropBar();
                if(bar != null)
            	{
                	bar.setX(bar.getX() + Math.abs(scrollY));
            	}
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
    
    public double getScrollAmount()
    {
    	return scrollY;
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
