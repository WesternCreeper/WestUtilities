package graphicsUtilities;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class WGDragDropClickListener extends WGClickListener
{
	private WGDrawingObject dragObject;
	private boolean beginDragging = false;
	private double objectXOriginal = 0;
	private double objectYOriginal = 0;
	private double xOriginal = 0;
	private double yOriginal = 0;
	private Point2D dragDropOriginalPoint;
	private ArrayList<Point2D> componentOriginalPositions = new ArrayList<Point2D>();
    /**
     * This class allows for a drag and drop to work properly. Does nothing for any other type of object
	 * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGDragDropClickListener(WGDragDropBar parentObject, WGDrawingObject dragObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
        this.dragObject = dragObject;
    }


    @Override
    public void mousePressed(MouseEvent e)
    {
    	if(isWithinBounds(e))
    	{
    		WGPane pane = dragObject.getParentOwningPane();
    		if(pane != null)
    		{
    			pane.bringComponentToTop(dragObject);
    		}
    		beginDragging = true;
    		objectXOriginal = dragObject.getX();
    		objectYOriginal = dragObject.getY();
    		xOriginal = e.getSceneX();
			yOriginal = e.getSceneY();
			dragDropOriginalPoint = new Point2D(getParentObject().getX(), getParentObject().getY());
			if(dragObject instanceof WGPane)
			{
				componentOriginalPositions.removeAll(componentOriginalPositions);
				for(int i = 0 ; i < ((WGPane) dragObject).getComponentNumber() ; i++)
				{
					WGDrawingObject obj = ((WGPane) dragObject).getComponent(i);
					componentOriginalPositions.add(new Point2D(obj.getX(), obj.getY()));
				}
			}
    	}
    	else
    	{
    		beginDragging = false;
    	}
    }
    
    public void mouseDragged(MouseEvent e) 
    {
    	//This is the to-do code for movement
    	if(beginDragging)
    	{
    		double xDif = xOriginal - e.getSceneX();
    		double yDif = yOriginal - e.getSceneY();
    		dragObject.setX(objectXOriginal - xDif);
    		dragObject.setY(objectYOriginal - yDif);
    		getParentObject().setX(dragDropOriginalPoint.getX() - xDif);
    		getParentObject().setY(dragDropOriginalPoint.getY() - yDif);
			if(dragObject instanceof WGPane)
			{
				for(int i = 0 ; i < ((WGPane) dragObject).getComponentNumber() ; i++)
				{
					WGDrawingObject obj = ((WGPane) dragObject).getComponent(i);
					obj.setX(componentOriginalPositions.get(i).getX() - xDif);
					obj.setY(componentOriginalPositions.get(i).getY() - yDif);
				}
			}
    	}
    }
    

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	if(beginDragging)
    	{
    		//Make sure to tell the WestGraphics to update the positions, and to make the changes permanent:
    		getParentObject().getResizer().setXPercent(getParentObject().getX() / getParentObject().getParent().getWidth());
    		getParentObject().getResizer().setYPercent(getParentObject().getY() / getParentObject().getParent().getHeight());
    		dragObject.getResizer().setXPercent(dragObject.getX() / getParentObject().getParent().getWidth());
    		dragObject.getResizer().setYPercent(dragObject.getY() / getParentObject().getParent().getHeight());
    		
    		//Make them permanent:
    		WestGraphics.update(getParentObject());
    		WestGraphics.update(dragObject);
			if(dragObject instanceof WGPane)
			{
				for(int i = 0 ; i < ((WGPane) dragObject).getComponentNumber() ; i++)
				{
					WGDrawingObject obj = ((WGPane) dragObject).getComponent(i);
					obj.getResizer().setXPercent(obj.getX() / getParentObject().getParent().getWidth());
					obj.getResizer().setYPercent(obj.getY() / getParentObject().getParent().getHeight());
		    		WestGraphics.update(obj);
				}
			}
    	}
    }

    public void mouseMoved(MouseEvent e)
    {
        hoverEvent(e);
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
    
    public void hoverEvent(MouseEvent e)
    {
        //Cursor:
        WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
    }
}
