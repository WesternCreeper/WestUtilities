/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;

/**
 *
 * @author Westley
 */
public class WGKeyInputClickListener extends WGClickListener
{
    private Paint originalBackgroundColor;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGKeyInputClickListener() {}
    public WGKeyInputClickListener (WGDrawingObject parentObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        WGKeyInput parent = (WGKeyInput)getParentObject();
        if(isWithinBounds(e) && isParentShown())
        {
            if(!parent.isFocused() && !e.isConsumed())
            {
                parent.setFocused(true);
                e.consume();
                
                //Cursor:
                WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
            }
        }
        else
        {
            parent.setFocused(false);
        }
    }

    public void mouseDragged(MouseEvent e) 
    {
        if(e.isConsumed())
        {
            return;
        }
        WGKeyInput parent = (WGKeyInput)getParentObject();
        if(isWithinBounds(e) && parent.isFocused())
        {
            if(!parent.isFocused())
            {
                parent.setFocused(true);
                e.consume();
                
                //Cursor:
                WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
            }
        }
        else
        {
            parent.setFocused(false);
        }
    }

    public void mouseMoved(MouseEvent e)
    {
        //Cursor:
        WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
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
    }
    
    protected Cursor getCursorType()
    {
        WGKeyInput parent = (WGKeyInput)getParentObject();
        return parent.isFocused() ? WestGraphics.getTextCursor() : WestGraphics.getHoverCursor();
    }
    
    //Getters:
    public Paint getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Paint originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
}
