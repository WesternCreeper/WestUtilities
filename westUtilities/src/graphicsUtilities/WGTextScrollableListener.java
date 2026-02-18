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
import utilities.FXFontMetrics;

/**
 *
 * @author Westley
 */
public class WGTextScrollableListener implements EventHandler<Event>
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
	public void handle(Event e) 
	{
    	Platform.runLater(() -> {
			if(e.getEventType().equals(ScrollEvent.SCROLL))
			{
				mouseWheelMoved((ScrollEvent)e);
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
			{
				mouseDragged((MouseEvent)e);
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
			{
				mouseReleased((MouseEvent)e);
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED))
			{
				mousePressed((MouseEvent)e);
			}
    	});
	}
	
    /**
     * Scrolls the pane according to the setUp data
     */
    public void mouseWheelMoved(ScrollEvent e) 
    {
        if(isWithinBounds(e) && !e.isConsumed())
        {
            doScroll(-e.getDeltaY(), false);
        }
    }

    public void mouseDragged(MouseEvent e) 
    {
        if(allowedToScroll && isWithinBounds(e) && !e.isConsumed())
        {
            double distance = 0;
            double currentY = e.getY();
            distance = currentY - yStart;
            yStart = currentY;
            distance /= seeableArea / totalArea;
            doScroll(distance, false);
        }
    }

    public void mousePressed(MouseEvent e) 
    {
        if(isWithinScrollBarBounds(e) && !e.isConsumed())
        {
            yStart = e.getY();
            allowedToScroll = true;
        }
    }

    public void mouseReleased(MouseEvent e) 
    {
        allowedToScroll = false;
    }
    
    /**
     * Sets up the scrollBar based on the given information:
     * @param text The array of strings that are on the TextArea
     */
    public void setUpScroll(ArrayList<ColoredString> text)
    {
    	double oldTotalArea = totalArea;
    	double oldScrollY = scrollY;
        seeableArea = parentTextArea.getHeight();
        //Finds the totalArea
        
        //Add up the height of each line
        totalArea = 0.0; 
        for(int i = 0 ; i < text.size(); i++) 
        {
        	totalArea += FXFontMetrics.getHeight(text.get(i));
        }
        
        scrollBarHeight = seeableArea / totalArea * seeableArea;
        
        shown = totalArea > seeableArea;
        
        if(shown && oldTotalArea > 0)
        {
            scrollY = (oldScrollY / oldTotalArea) * totalArea;
            scrollBarY = (scrollY / totalArea) * (seeableArea);
            
        }
        else
        {
        	scrollY = 0;
        	scrollBarY = 0;
        }
        parentTextArea.setStringYOffset(-scrollY);
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinBounds(ScrollEvent e)
    {
        Point2D clickLoaction = new Point2D(e.getX(), e.getY());
        Rectangle2D objectBounds = (Rectangle2D)parentTextArea.getBounds();
        return objectBounds.contains(clickLoaction);
    }
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinBounds(MouseEvent e)
    {
        Point2D clickLoaction = new Point2D(e.getX(), e.getY());
        Rectangle2D objectBounds = (Rectangle2D)parentTextArea.getBounds();
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
        scrollBoundsX = parentTextArea.getX();
        scrollBoundsY = parentTextArea.getY();
        scrollBoundsWidth = parentTextArea.getBorderSize();
        scrollBoundsHeight = scrollBoundsWidth;
        double scrollBarHeight = seeableArea / totalArea * seeableArea;
        
        scrollBoundsY += scrollBarY;
        scrollBoundsHeight = scrollBarHeight;
        scrollBoundsX += parentTextArea.getWidth() - scrollBoundsWidth - parentTextArea.getBorderSize();
        
        Rectangle2D objectBounds = new Rectangle2D(scrollBoundsX, scrollBoundsY, scrollBoundsWidth, scrollBoundsHeight);
        return objectBounds.contains(clickLoaction);
    }
    
    private void doScroll(double mouseMovement, boolean useScrollSpeed)
    {
        if(!shown)
        {
            return;
        }
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
            parentTextArea.setStringYOffset(-minY);
            scrollY = minY;
            scrollBarY = 0;
            return;
        }
        else if(scrollY + movement > maxY)
        {
            //Now set all of the components to the correct location:
            parentTextArea.setStringYOffset(-maxY);
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
        parentTextArea.setStringYOffset(-scrollY);
        shown = totalArea > seeableArea;
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
