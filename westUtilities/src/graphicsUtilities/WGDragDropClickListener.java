package graphicsUtilities;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class WGDragDropClickListener extends WGClickListener
{
	private WGDrawingObject dragObject;
	private WGDrawingObject swapObject;
	private DragDropType dragType;
	private boolean beginDragging = false;
	private boolean xDir;
	private boolean left = false;
	private boolean top = false;
	private double objectXOriginal = 0;
	private double objectYOriginal = 0;
	private double xOriginal = 0;
	private double yOriginal = 0;
	private ArrayList<Point2D> componentOriginalPositions = new ArrayList<Point2D>();
	private ArrayList<WGDrawingObject> allComponents = new ArrayList<WGDrawingObject>();
	private Rectangle2D dragDropLine;
	private AutoScrollHandler autoScollHandler = new AutoScrollHandler();
	private Timeline autoscroller = new Timeline(new KeyFrame(Duration.millis(50), e -> autoScollHandler.autoscroll()));
    /**
     * This class allows for a drag and drop to work properly. Does nothing for any other type of object
	 * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGDragDropClickListener(DragDropType dragType, WGDragDropBar parentObject, WGDrawingObject dragObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
        this.dragType = dragType;
        this.dragObject = dragObject;
        autoscroller.setCycleCount(Animation.INDEFINITE);
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
    		if(dragType == DragDropType.FREE_DRAG_MODE) 
    		{
	    		objectXOriginal = dragObject.getX();
	    		objectYOriginal = dragObject.getY();
	    		xOriginal = e.getSceneX();
				yOriginal = e.getSceneY();
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
    		else if(dragType == DragDropType.REDORDER_DRAG_MODE)
    		{
    			//Manually copy the elements from the pane owner, which has to be set by now:
    			for(int i = 0 ; i < pane.getComponentNumber() ; i++)
    			{
    				allComponents.add(pane.getComponent(i));
    			}
    			autoscroller.play();
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
    		if(dragType == DragDropType.FREE_DRAG_MODE) 
    		{
	    		double xDif = xOriginal - e.getSceneX();
	    		double yDif = yOriginal - e.getSceneY();
	    		dragObject.setX(objectXOriginal - xDif);
	    		dragObject.setY(objectYOriginal - yDif);
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
    		else if(dragType == DragDropType.REDORDER_DRAG_MODE)
    		{
    			//Find the closest center:
    			int center = 0;
    			double x = e.getSceneX();
    			double y = e.getSceneY();
    			double minDis = Math.sqrt(Math.pow(x - allComponents.get(0).getX(), 2) + Math.pow(y - allComponents.get(0).getY(), 2));
    			for(int i = 1 ; i < allComponents.size() ; i++)
				{
    				if(allComponents.get(i).getDragAndDropBar() == null)
    				{
    					//continue;
    				}
    				double distance = Math.sqrt(Math.pow(x - allComponents.get(i).getX(), 2) + Math.pow(y - allComponents.get(i).getY(), 2));
    				if(distance < minDis)
    				{
    					center = i;
    					minDis = distance;
    				}
				}
    			
    			//Determine if we are going in the x direction, or the y.
    			WGDrawingObject obj = allComponents.get(center);
    			xDir = false;
    			if(y > obj.getY() && y < obj.getY() + obj.getHeight() && (x < obj.getX() || x > obj.getX() + obj.getWidth()))
				{
    				xDir = true;
				}
    			
    			WGPane pane = dragObject.getParentOwningPane();
    			double xMovement = pane.getHorizontalScroll().getScrollAmount();
    			double yMovement = pane.getVerticalScroll().getScrollAmount();
    			
    			if(!xDir)
    			{
    				//Determine if it is the top or the bottom surface:
        			double centerY = obj.getY() + obj.getHeight()/2;
    				if(y < centerY)
    				{
    					//Top Surface
    					dragDropLine = new Rectangle2D(obj.getX(), obj.getY(), obj.getWidth(), 0);
    					top = true;

    					//Find the object above:
    					Rectangle2D objArea = new Rectangle2D(dragDropLine.getMinX(), dragDropLine.getMinY() - dragObject.getHeight(), dragObject.getWidth(), dragObject.getHeight());
    					double testY = ((allComponents.get(0).getY()));
    					double closest = Math.abs(objArea.getMinY() - testY);
    					swapObject = allComponents.get(0);
    					for(int i = 1 ; i < allComponents.size() ; i++)
    					{
    						WGDrawingObject testObj = allComponents.get(i);
    						testY = ((testObj.getY()));
    						if(closest > Math.abs(objArea.getMinY() - testY)) //Double check
	    					{
    	    					swapObject = testObj;
    	    					closest = Math.abs(objArea.getMinY() - testY);
	    					}
	    					else if(closest == Math.abs(objArea.getMinY() - testY)) //There are two that are the same distance away, figure out which is a closer match...
	    					{
	    						if(testObj.getY() == objArea.getMinY()) //We have our answer...
	    						{
	    	    					swapObject = testObj;
	    						}
	    					}
    					}
    					
    					if(swapObject.getDragAndDropBar() == null) //We cannot swap with a non-drag drop compatible object, so we won't
    					{
    						swapObject = null; //So don't allow it
    					}
    				}
    				else
    				{
    					//Bottom Surface
    					dragDropLine = new Rectangle2D(obj.getX(), obj.getY() + obj.getHeight(), obj.getWidth(), 0);
    					top = false;
    	    			swapObject = obj;
    	    			
						//Find the object were are over:
    					Rectangle2D objArea = new Rectangle2D(dragDropLine.getMinX(), dragDropLine.getMinY() - dragObject.getHeight(), dragDropLine.getWidth(), dragDropLine.getHeight());
    					for(int i = 0 ; i < allComponents.size() ; i++)
    					{
    						WGDrawingObject testObj = allComponents.get(i);
	    					if(objArea.getMinY() < testObj.getY()) //Double check
	    					{
    	    					swapObject = allComponents.get(i);
    	    					break;
	    					}
    					}
    					
    					if(swapObject.getDragAndDropBar() == null) //We cannot swap with a non-drag drop compatible object, so we won't
    					{
    						swapObject = null; //So don't allow it
    					}
    				}
    			}
    			else
    			{
    				//Determine if it is the left or the right surface:
        			double centerX = obj.getX() + obj.getWidth()/2;
    				if(x < centerX)
    				{
    					//Left Surface
    					dragDropLine = new Rectangle2D(obj.getX(), obj.getY(), 0, obj.getHeight());
    					left = true;
    					
    					Rectangle2D objArea = new Rectangle2D(dragDropLine.getMinX() - dragObject.getWidth(), dragDropLine.getMinY(), dragObject.getWidth(), dragObject.getHeight());
    					double testX = ((allComponents.get(0).getX()));
    					double closest = Math.abs(objArea.getMinX() - testX);
    					swapObject = allComponents.get(0);
    					for(int i = 1 ; i < allComponents.size() ; i++)
    					{
    						WGDrawingObject testObj = allComponents.get(i);
    						testX = ((testObj.getX()));
    						if(closest > Math.abs(objArea.getMinX() - testX)) //Double check
	    					{
    	    					swapObject = testObj;
    	    					closest = Math.abs(objArea.getMinX() - testX);
	    					}
	    					else if(closest == Math.abs(objArea.getMinX() - testX)) //There are two that are the same distance away, figure out which is a closer match...
	    					{
	    						if(testObj.getX() == objArea.getMinX()) //We have our answer...
	    						{
	    	    					swapObject = testObj;
	    						}
	    					}
    					}
    					
    					if(swapObject.getDragAndDropBar() == null) //We cannot swap with a non-drag drop compatible object, so we won't
    					{
    						left = false;
        	    			swapObject = obj;
    					}
    				}
    				else
    				{
    					//Right Surface
    					dragDropLine = new Rectangle2D(obj.getX() + obj.getWidth(), obj.getY(), 0, obj.getHeight());
    					left = false;
    	    			swapObject = obj;
    					
    					if(swapObject.getDragAndDropBar() == null) //We cannot swap with a non-drag drop compatible object, so we won't
    					{
    						left = true;

        					//Find the object above:
        					Rectangle2D objArea = new Rectangle2D(dragDropLine.getMinX() - dragObject.getWidth(), dragDropLine.getMinY(), dragDropLine.getWidth(), dragDropLine.getHeight());
        					for(int i = 0 ; i < allComponents.size() ; i++)
        					{
        						WGDrawingObject testObj = allComponents.get(i);
    	    					if(objArea.getMinX() < testObj.getX()) //Double check
    	    					{
        	    					swapObject = allComponents.get(i);
        	    					break;
    	    					}
        					}
    					}
    				}
    			}
    			autoScollHandler.setMouseEvent(e);
    		}
    	}
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
    	if(beginDragging)
    	{
    		if(dragType == DragDropType.FREE_DRAG_MODE) 
    		{
	    		//Make sure to tell the WestGraphics to update the positions, and to make the changes permanent:
	    		dragObject.getResizer().setXPercent(dragObject.getX() / getParentObject().getParent().getWidth());
	    		dragObject.getResizer().setYPercent(dragObject.getY() / getParentObject().getParent().getHeight());
	    		
	    		//Make them permanent:
				if(dragObject instanceof WGPane)
				{
					for(int i = 0 ; i < ((WGPane) dragObject).getComponentNumber() ; i++)
					{
						WGDrawingObject obj = ((WGPane) dragObject).getComponent(i);
						obj.getResizer().setXPercent(obj.getX() / getParentObject().getParent().getWidth());
						obj.getResizer().setYPercent(obj.getY() / getParentObject().getParent().getHeight());
					}
				}
    		}
    		else if(dragType == DragDropType.REDORDER_DRAG_MODE)
    		{
    			if(swapObject == null)
    			{
    				return;
    			}
    			//Find the new position:
    			double originalX = dragObject.getX();
    			double originalY = dragObject.getY();
    			double newX;
    			double newY;
    			if(!xDir)
    			{
    				if(top)
    				{
    					//This object goes above the other one:
    					newX = dragDropLine.getMinX();
    					newY = dragDropLine.getMinY() - dragObject.getHeight();
    				}
    				else
    				{
    					//This object goes to the bottom:
    					newX = dragDropLine.getMinX();
    					newY = dragDropLine.getMaxY();
    				}
    			}
    			else
    			{
    				if(left)
    				{
    					//This object goes above the other one:
    					newX = dragDropLine.getMinX() - dragObject.getWidth();
    					newY = dragDropLine.getMinY();
    				}
    				else
    				{
    					//This object goes to the bottom:
    					newX = dragDropLine.getMaxX();
    					newY = dragDropLine.getMinY();
    				}
    			}
    			//Determine first if the new position is invalid because it pushes the object out of the pane:
        		WGPane pane = dragObject.getParentOwningPane();
    			if(!pane.isScrollable())
    			{
    				if(newX > pane.getX() || newX < pane.getX() + pane.getWidth())
    				{
    					return;
    				}
    				if(newY > pane.getY() || newY < pane.getY() + pane.getHeight())
    				{
    					return;
    				}
    			} //If we are scrollable then there is no issue.

    			
    			//Then move the object to that position:
    			dragObject.setX(newX);
    			dragObject.setY(newY);
    			
    			//Do the checking:
    			//First detect if there is something in its new position:
    			WGDrawingObject swappedObject = null;
    			boolean above = true;
				if(!xDir)
				{
					if(swapObject.getHeight() > dragObject.getHeight()) //There will not be any other ones in our way
					{
						if(swapObject.getY() < originalY)
						{
							above = false;
						}
						swappedObject = swapObject;
						swappedObject.setY(originalY);
					}
					else
					{
						swapObject.setY(originalY);
						originalY += swapObject.getHeight(); //Place them one after another in the vacated space
					}
				}
				else
				{
					if(swapObject.getWidth() > dragObject.getWidth()) //There will not be any other ones in our way
					{
						if(swapObject.getX() < originalX)
						{
							above = false;
						}
						swappedObject = swapObject;
						swappedObject.setX(originalX);
					}
					else
					{
						swapObject.setX(originalX);
						originalX += swapObject.getWidth(); //Place them one after another in the vacated space
					}
				}
    			
    			//Now for the big object, make sure that there are not other objects to be displaced:
    			if(swappedObject != null)
    			{
    				//Go through the entire array again, searching for objects that may need to be displaced:
    				double changeX = dragObject.getX();
    				double changeY = dragObject.getY();
    				if(!above)
    				{
    					changeY += dragObject.getHeight();
    				}
    				if(!above)
    				{
    					changeX += dragObject.getWidth();
    				}
        			for(int i = 0 ; i < allComponents.size() ; i++)
        			{
        				WGDrawingObject obj = allComponents.get(i);
        				if(dragObject == obj || swappedObject == obj) //Of course we are in our own space
        				{
        					continue;
        				}
        				if(obj.getBounds().intersects(swappedObject.getBounds())) //Found it!
        				{
        					if(!xDir)
        					{
	        					if(above)
	        					{
	            					obj.setY(changeY - obj.getHeight());
	            					changeY -= obj.getHeight();
	        					}
	        					else
	        					{
	            					obj.setY(changeY);
	            					changeY += obj.getHeight();
	        					}
        					}
        					else
        					{
	        					if(above)
	        					{
	            					obj.setX(changeX - obj.getWidth());
	            					changeX -= obj.getWidth();
	        					}
	        					else
	        					{
	            					obj.setX(changeX);
	            					changeX += obj.getWidth();
	        					}
        					}
        				}
        			}
    			}
    			
				for(int i = 0 ; i < allComponents.size() ; i++)
				{
					WGDrawingObject obj = allComponents.get(i);
					obj.getResizer().setXPercent((obj.getX()) / getParentObject().getParent().getWidth());
					obj.getResizer().setYPercent((obj.getY()) / getParentObject().getParent().getHeight());
				}
    			dragDropLine = null;
    			autoscroller.stop();
    		}
    	}
    }
    
    public void mouseMoved(MouseEvent e) {}
    
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
	
	
	//Getters:
	public Rectangle2D getDragDropLine() {
		return dragDropLine;
	}
	
	public DragDropType getDragType() {
		return dragType;
	}


	//Classes:
	public enum	DragDropType
	{
		REDORDER_DRAG_MODE,
		FREE_DRAG_MODE;
	}
	private class AutoScrollHandler
	{
		private MouseEvent e = null;
		public void setMouseEvent(MouseEvent e)
		{
			this.e = e;
		}

	    
	    private void autoscroll()
	    {
	    	if(e == null)
	    	{
	    		return;
	    	}
	    	double x = e.getSceneX();
	    	double y = e.getSceneY();
			WGPane pane = dragObject.getParentOwningPane();
			if(pane.isScrollable())
			{
				//Test the bottom/top surfaces: (Or the left/right)
				if(y <= pane.getY() + 15 && y >= pane.getY()) //Top
				{
					pane.getVerticalScroll().doScroll(-5, true);
				}
				else if(y <= pane.getY() + pane.getHeight() && y >= pane.getY() + pane.getHeight() -15) //Bottom surface
				{
					pane.getVerticalScroll().doScroll(5, true);
				}
				
				if(x <= pane.getX() && x >= pane.getX() + 15) //Left Surface
				{
					pane.getHorizontalScroll().doScroll(-5, true);
				}
				else if(x <= pane.getX() + pane.getWidth() - 15 && x >= pane.getX() + pane.getWidth()) //Right surface
				{
					pane.getHorizontalScroll().doScroll(5, true);
				}
			}
	    }
	}
}
