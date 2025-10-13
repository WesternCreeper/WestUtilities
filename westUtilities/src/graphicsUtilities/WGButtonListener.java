/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 *
 * @author Westley
 */
public class WGButtonListener extends WGClickListener
{
    protected boolean cursorSet = false;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGButtonListener() {}
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * ONLY USE THIS DEFINITION IF YOU KNOW 100% THAT THE WGOBJECT HAS THE PARENT
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @throws WGNullParentException When the WGObject does not have a parent, then it will throw a WGNullParentException so that this object can be supplied a parent Object
     */
    public WGButtonListener(WGBox parentObject) throws WGNullParentException
    {
        super(parentObject);
    }
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGButtonListener(WGBox parentObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
    }

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e)
    {
        hoverEvent(e);
    }

    public void mouseWheelMoved(ScrollEvent e) 
    {
        hoverEvent(e);
    }
    
    @Override
    public void handle(Event e)
    {
		if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
		{
			mouseClicked((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED))
		{
			mousePressed((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
		{
			mouseReleased((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_ENTERED))
		{
			mouseEntered((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_EXITED))
		{
			mouseExited((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
		{
			mouseDragged((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_MOVED))
		{
			mouseMoved((MouseEvent)e);
		}
		else if(e.getEventType().equals(ScrollEvent.SCROLL))
		{
			mouseWheelMoved((ScrollEvent)e);
		}
    }
    
    public void hoverEvent(MouseEvent e)
    {
        //Cursor:
        WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
    }
    
    public void hoverEvent(ScrollEvent e)
    {
        //Cursor:
        WestGraphics.checkCursor(getParentComponent(), getParentObject());
    }
}