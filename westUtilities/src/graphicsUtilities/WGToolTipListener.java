/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Westley
 */
public class WGToolTipListener extends WGClickListener
{
    private static final int Y_MOUSE_OFFSET = 16;
    public static final int BASE_WAIT_TIME = 2500;
    private WGToolTip toolTipObject;
    private Timer waitTimer;
    private boolean entered = false;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     * @param waitTime The time that has to pass before the component shows itself
     */
    public WGToolTipListener(int waitTime) 
    {
        waitTimer = new Timer(waitTime, new WaitListener());
        waitTimer.setRepeats(false);
    }
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * ONLY USE THIS DEFINITION IF YOU KNOW 100% THAT THE WGOBJECT HAS THE PARENT
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param waitTime The time this class waits before showing the tooltip
     * @throws WGNullParentException When the WGObject does not have a parent, then it will throw a WGNullParentException so that this object can be supplied a parent Object
     */
    public WGToolTipListener(int waitTime, WGDrawingObject parentObject) throws WGNullParentException
    {
        super(parentObject);
        waitTimer = new Timer(waitTime, new WaitListener());
        waitTimer.setRepeats(false);
    }
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     * @param waitTime The time this class waits before showing the tooltip
     */
    public WGToolTipListener(int waitTime, WGDrawingObject parentObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
        waitTimer = new Timer(waitTime, new WaitListener());
        waitTimer.setRepeats(false);
    }
	@Override
	public void handle(Event e) 
	{
    	Platform.runLater(() -> {
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
    	});
	}

    public void mouseDragged(MouseEvent e) 
    {
        mouseOps(e);
    }

    public synchronized void mouseMoved(MouseEvent e)
    {
        mouseOps(e);
    }
    
    public synchronized void mouseOps(MouseEvent e)
    {
        if(isWithinBounds(e) && !toolTipObject.getLongestString().isEmpty())
        {
            if(!entered)
            {
                waitTimer.start();
                entered = true;
            }
            toolTipObject.setX(e.getX());
            toolTipObject.setY(e.getY() + Y_MOUSE_OFFSET);
        }
        else
        {
            if(entered)
            {
                waitTimer.stop();
                entered = false;
            }
            toolTipObject.setShown(false);
        }
    }
    
    //Setter:
    public void setToolTipObject(WGToolTip toolTipObject) {
        this.toolTipObject = toolTipObject;
        toolTipObject.setShown(false);
    }
    
    
    //Getter:
    public WGToolTip getToolTipObject() {
        return toolTipObject;
    }
    
    
    //classes or Listeners:
    private class WaitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(entered)
            {
                toolTipObject.setShown(true);
            }
        }
    }
}
