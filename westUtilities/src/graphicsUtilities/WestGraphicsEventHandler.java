package graphicsUtilities;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class WestGraphicsEventHandler implements EventHandler<Event>
{
	private Canvas parent;
	private WGDrawingObject dragObject;
	private WGBox previousHover;
	public WestGraphicsEventHandler(Canvas parent)
	{
		this.parent = parent;
	}
	@Override
	public void handle(Event e) 
	{
		//Find the event type first:
		if(e instanceof MouseEvent)
		{
			if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED) || e.getEventType().equals(MouseEvent.MOUSE_RELEASED) || e.getEventType().equals(MouseEvent.DRAG_DETECTED))
			{
				if(dragObject != null)
				{
					dragObject.getClickListener().handle(e);
				}
				return; //We already know the object, so skip the finding object phase
			}
			else if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED) && dragObject != null)
			{
				dragObject = null; //The mouse released so forget the object
				return;
			}
			
			//Find its position
			MouseEvent me = (MouseEvent)e;
			WGDrawingObject clickObject = contains(WestGraphics.getAllClickables(), new Point2D(me.getSceneX(), me.getSceneY()), parent);
			
			//Now do the clicking
			if(clickObject != null && clickObject.getClickListener() != null)
			{
				clickObject.getClickListener().handle(me);
                parent.setCursor(clickObject.getClickListener().getCursorType());
                if(clickObject instanceof WGBox)
                {
    	            if(previousHover != null)
    	            {
    	            	previousHover.setHovered(false);
    	            }
                    WGBox boxObject = (WGBox)clickObject;
                    previousHover = boxObject;
                    boxObject.setHovered(true);
                }
			}
			else
			{
	            parent.setCursor(WestGraphics.getDefaultCursor());
	            if(previousHover != null)
	            {
	            	previousHover.setHovered(false);
	            }
			}
			
			if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED) && clickObject instanceof WGDragDropBar)
			{
				dragObject = clickObject;
			}
		}
		else if(e instanceof ScrollEvent)
		{
			//Find its position
			ScrollEvent se = (ScrollEvent)e;
			WGDrawingObject clickObject = contains(WestGraphics.getAllClickables(), new Point2D(se.getSceneX(), se.getSceneY()), parent);
			WGDrawingObject originalObject = clickObject;
			
			//Now do the scrolling
			if(clickObject != null)
			{
				//Verify that the scroll object is a pane, if not search for it:
				if(!(clickObject instanceof WGTextArea))
				{
					while(clickObject.getParentOwningPane() != null)
					{
						if(clickObject.getVerticalScrollListener() != null || clickObject.getHorizontalScrollListener() != null)
						{
							break;
						}
						clickObject = clickObject.getParentOwningPane();
					}
				}

				if(clickObject.getVerticalScrollListener() != null)
				{
					clickObject.getVerticalScrollListener().handle(se);
					if(originalObject.getClickListener() != null)
					{
						parent.setCursor(originalObject.getClickListener().getCursorType());
					}
				}
				if(clickObject.getHorizontalScrollListener() != null)
				{
					clickObject.getHorizontalScrollListener().handle(se);
					if(originalObject.getClickListener() != null)
					{
						parent.setCursor(originalObject.getClickListener().getCursorType());
					}
				}
				
				//Now check the current position, post scroll and see if the cursor can be set:
				clickObject = contains(WestGraphics.getAllClickables(), new Point2D(se.getSceneX(), se.getSceneY()), parent);
	            if(clickObject instanceof WGBox)
	            {
		            if(previousHover != null)
		            {
		            	previousHover.setHovered(false);
		            }
	                WGBox boxObject = (WGBox)clickObject;
	                previousHover = boxObject;
	                boxObject.setHovered(true);
	            }
			}
			else
			{
	            parent.setCursor(WestGraphics.getDefaultCursor());
	            if(previousHover != null)
	            {
	            	previousHover.setHovered(false);
	            }
			}
		}
	}
	
	private WGDrawingObject contains(ArrayList<WGDrawingObject> objects, Point2D point, Canvas parent)
	{
		WGDrawingObject obj = null;
		
		int highestOrder = -1;
		for(int i = 0 ; i < objects.size(); i++)
		{
			//Skip all non-shown objects:
			if(!objects.get(i).isShown())
			{
				continue;
			}
			//Skip all objects not on this pane:
			if(objects.get(i).getParent() != parent)
			{
				continue;
			}
			//Skip unclickable objects:
			if(objects.get(i).getClickListener() == null && objects.get(i).getVerticalScrollListener() == null && objects.get(i).getHorizontalScrollListener() == null)
			{
				continue;
			}
			
			//Figure out if the point is within the object for each of the objects:
			if(point.getX() >= objects.get(i).getX() && point.getX() <= objects.get(i).getX() + objects.get(i).getWidth())
			{
				if(point.getY() >= objects.get(i).getY() && point.getY() <= objects.get(i).getY() + objects.get(i).getHeight())
				{
					//Double check that this object's order is the highest:
					if(objects.get(i).getOrder() > highestOrder)
					{
						//Possibly found it!
						obj = objects.get(i);
						//Keep checking to make sure this is the highest order:
						highestOrder = obj.getOrder();
					}
				}
			}
		}
		
		return obj;
	}
}
