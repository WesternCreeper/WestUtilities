/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;


import dataStructures.Stack;
import graphicsUtilities.WGDragDropClickListener.DragDropType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;

/**
 *
 * This class defines the basic functions of a West Graphics object. Specifically it defines functions like: getBounds() and other important cross object functionalities
 * @author Westley
 */
public abstract class WGDrawingObject
{ 
    public final static int NO_GRADIENT_ORIENTATION_PREFERENCE = 0;
    public final static int VERTICAL_GRADIENT_ORIENTATION_PREFERENCE = 1;
    public final static int RADIAL_CENTER_GRADIENT_ORIENTATION_PREFERENCE = 2;
    public final static int RADIAL_CENTER_GRADIENT_ORIENTATION_WITH_MOUSE_MOVE_PREFERENCE = 3;
    public final static int HORIZONTAL_GRADIENT_ORIENTATION_PREFERENCE = 4;
    public final static int DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT_GRADIENT_ORIENTATION_PREFERENCE = 5;
    public final static int DIAGONAL_BOTTOM_LEFT_TO_TOP_RIGHT_GRADIENT_ORIENTATION_PREFERENCE = 6;
    
    
    private Canvas parent;
    private WGClickListener clickListener;
    private EventHandler<Event> verticalScrollListener;
    private EventHandler<Event> horizontalScrollListener;
    protected WGDrawingObjectResizeListener resizer;
    private WGToolTip toolTip;
    private Cursor shownCursor;
    private int order = -1;
    private double x = 0;
    private double y = 0;
    private double width;
    private double height;
    private double borderSize = 1;
    private boolean isResizing = false;
    private boolean isShown = true;
    private WGPane parentOwningPane;
    private WGDragDropBar dragAndDropBar;
    private WGTheme currentTheme;
    private Stack<WGOverlay> overlays = new Stack<WGOverlay>();
    /**
     * This defines a basic WGDrawingObject, which is another term for a shared commonality among different drawable objects. Specifically this defines the X, Y, Width, Height, and Border Size of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     */
    protected WGDrawingObject(double x, double y, double width, double height, double borderSize)
    {
        this(x, y, width, height, borderSize, null);
    }
    /**
     * This defines a more advanced and capable WGDrawingObject. Specifically this defines the X, Y, Width, Height, Border Size, and the Parent Component of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param parent The component that the object is on, and is used to determine how big this object is
     */
    protected WGDrawingObject(double x, double y, double width, double height, double borderSize, Canvas parent)
    {
        this(x, y, width, height, borderSize, parent, null);
    }
    /**
     * This defines a more advanced and capable WGDrawingObject. Specifically this defines the X, Y, Width, Height, Border Size, and the Parent Component of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param parent The component that the object is on, and is used to determine how big this object is
     * @param currentTheme The theme that is currently being used. This is for background operations, like gradients to work properly
     */
    protected WGDrawingObject(double x, double y, double width, double height, double borderSize, Canvas parent, WGTheme currentTheme)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderSize  = borderSize;
        this.parent = parent;
        this.currentTheme = currentTheme;
        WestGraphics.add(this);
        order = WestGraphics.assignOrder();
    }
    
    //Methods:
    public abstract Rectangle2D getBounds();
    
    public Rectangle2D getRelativeBounds()
    {
    	if(resizer == null)
    	{
    		return new Rectangle2D(0,0,0,0);
    	}
        return new Rectangle2D(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent());
    }
    
    public abstract WGDrawingObject cloneObject() throws WGNullParentException;
    
    public abstract void setUpBounds();
    
    public abstract void setBounds(Rectangle2D newBounds);
    
    public abstract void removeListeners();
    
    public void setTheme(WGTheme theme)
    {
        currentTheme = theme;
    }
    
    protected Paint fixPaintBounds(Paint paint)
    {
        return fixPaintBounds(paint, NO_GRADIENT_ORIENTATION_PREFERENCE, null);
    }
    protected Paint fixPaintBounds(Paint paint, Object gradientOrientationPreference)
    {
        return fixPaintBounds(paint, gradientOrientationPreference, null);
    }
    protected Paint fixPaintBounds(Paint paint, Object gradientOrientationPreference, MouseEvent e)
    {
        return fixPaintBounds(paint, gradientOrientationPreference, e, getX(), getY(), getWidth(), getHeight());
    }
    protected Paint fixPaintBounds(Paint paint, Object gradientOrientationPreference, MouseEvent e, double x, double y, double width, double height)
    {
        //First convert the gradientOrientation into a usable form:
        int gradOrient = 0;
        if(gradientOrientationPreference != null)
        {
            gradOrient = (int)gradientOrientationPreference;
        }
        
        Paint newPaint;
        if(paint instanceof LinearGradient)
        {
        	LinearGradient oldPaint = (LinearGradient)paint;
            Point2D[] points = getGradientPoints(gradOrient, e, x, y, width, height);
            newPaint = new LinearGradient(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), false, oldPaint.getCycleMethod(), oldPaint.getStops());
        }
        else if(paint instanceof RadialGradient)
        {
        	RadialGradient oldPaint = (RadialGradient)paint;
            Point2D[] points = getGradientPoints(gradOrient, e, x, y, width, height);
            newPaint = new RadialGradient(0, 0, points[0].getX(), points[0].getY(), points[1].getX(), false, oldPaint.getCycleMethod(), oldPaint.getStops());
        }
        else if(paint instanceof ImagePattern)
        {
        	ImagePattern oldPaint = (ImagePattern)paint;
            Image image = oldPaint.getImage();
            newPaint = new ImagePattern(image);
        }
        else
        {
            newPaint = paint;
        }
        return newPaint;
    }
    /**
     * This function gets the points relative to this drawing object for the gradient to render correctly
     * @param gradientOrientationPreference The gradient orientation that determines how the points are created relative to the location of this object
     * @param width The width of the object
     * @param height The height of the object
     * @return the points that help define this gradient so that it follows the proper orientation.
     */
    private Point2D[] getGradientPoints(int gradientOrientationPreference, MouseEvent e, double x, double y, double width, double height)
    {
        Point2D[] points = new Point2D[2];
        
        switch(gradientOrientationPreference)
        {
            case VERTICAL_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D(x, y);
                points[1] = new Point2D(x, y + height);
                break;
            case RADIAL_CENTER_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D(x + (width/2), y + (height/2));
                
                double radius = (width/2);
                double heightRadius = (height/2);
                if(radius > heightRadius)
                {
                    radius = heightRadius;
                }
                if(radius <= 0)
                {
                    radius = 1;
                }
                
                points[1] = new Point2D(radius, 0);
                break;
            case RADIAL_CENTER_GRADIENT_ORIENTATION_WITH_MOUSE_MOVE_PREFERENCE:
                if(e != null)
                {
                    points[0] = new Point2D(e.getX(), e.getY());
                }
                else
                {
                    points[0] = new Point2D(x + (width/2), y + (height/2));
                }
                
                radius = (width/2);
                heightRadius = (height/2);
                if(radius > heightRadius)
                {
                    radius = heightRadius;
                }
                if(radius <= 0)
                {
                    radius = 1;
                }
                
                points[1] = new Point2D(radius, 0);
                break;
            case HORIZONTAL_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D(x, y);
                points[1] = new Point2D(x + width, y);
                break;
            case DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D(x, y);
                points[1] = new Point2D(x + width, y + height);
                break;
            case DIAGONAL_BOTTOM_LEFT_TO_TOP_RIGHT_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D(x, y + height);
                points[1] = new Point2D(x + width, y);
                break;
            default:
                points[0] = new Point2D(x, y);
                points[1] = new Point2D(x, y);
                break;
        }
        
        return points;
    }
    
    public void allowDragAndDrop(WGTheme theme, DragDropType dragType) throws WGRDMNoParentException, WGDragDropNonRelativePaneException
    {
    	if(this instanceof WGDragDropBar)
    	{
    		return; //Don't allow a drag and drop bar to be dragged and dropped
    	}
    	if(dragType == DragDropType.REDORDER_DRAG_MODE && this.parentOwningPane == null)
    	{
    		throw new WGRDMNoParentException();
    	}
    	if(this instanceof WGPane && !((WGPane)this).isUseRelativePositions())
    	{
    		throw new WGDragDropNonRelativePaneException();
    	}
    	if(dragAndDropBar == null)
    	{
    		double barWidth = 1.0 / 10;
			double barHeight = 1.0 / 20;
			Rectangle2D bounds = new Rectangle2D(0.45, 0, barWidth, barHeight);
			dragAndDropBar = new WGDragDropBar(bounds, dragType, this, parent, theme);
    	}
    }
    
    public void addOverlay(WGOverlay overlay)
    {
    	overlays.push(overlay);
    }
    
    public void removeCurrentOverlay()
    {
    	overlays.pop();
    }
    
    public void removeOverlay(WGOverlay overlay)
    {
    	overlays.pop(overlay);
    }
    
    public WGOverlay getOverlay()
    {
    	return overlays.top();
    }
	
	public void bringToTop() 
	{
		this.order = WestGraphics.assignOrder();
	}
    
    
    //Setters:
    public void setShown(boolean isShown) 
    {
        this.isShown = isShown;
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    protected void setWidth(double width) {
        this.width = width;
    }

    protected void setHeight(double height) {
        this.height = height;
    }

    public void setBorderSize(double borderSize) {
        this.borderSize = borderSize;
    }

    public void setClickListener(WGClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setShownCursor(Cursor shownCursor) {
        this.shownCursor = shownCursor;
    }

    public void setParentOwningPane(WGPane parentOwningPane) {
        this.parentOwningPane = parentOwningPane;
    }

    /**
     * A very important not about this:
     * Do NOT use this function if this object is a tool tip!
     * This is ONLY used when the tool tip is created but later needs to be changed, when there is no longer a reference to the original tool tip. This is the reference to that original tool tip
     * This is also useful in the drawing routine
     * @param toolTip The Tool Tip that is added to this object, important when the tool tip no longer has a reference but needs to be changed
    */ 
    public void setToolTip(WGToolTip toolTip) {
    	if(this instanceof WGToolTip) //This is for safety, to prevent tooltips from having tooltips, which is impossible
    	{
    		return; 
    	}
        this.toolTip = toolTip;
    }

    protected void setCurrentTheme(WGTheme currentTheme) {
        this.currentTheme = currentTheme;
        setTheme(currentTheme);
    }
    
	public void setDragAndDropBar(WGDragDropBar dragAndDropBar) {
		this.dragAndDropBar = dragAndDropBar;
	}
	
	public void setResizing(boolean isResizing) {
		this.isResizing = isResizing;
	}

	public void setVerticalScrollListener(EventHandler<Event> verticalScrollListener) {
		this.verticalScrollListener = verticalScrollListener;
	}
	
	public void setHorizontalScrollListener(EventHandler<Event> horizontalScrollListener) {
		this.horizontalScrollListener = horizontalScrollListener;
	}
	
	
	//Getters:
    public boolean isShown() 
    {
        boolean shown = isShown;
        
        //Check that if the pane above us is shown and add that to the result:
        if(parentOwningPane != null)
        {
        	shown = shown && parentOwningPane.isShown();
        }
        
        return shown;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public Canvas getParent() {
        return parent;
    }
    
    public WGClickListener getClickListener() {
        return clickListener;
    }

    public WGDrawingObjectResizeListener getResizer() {
        return resizer;
    }

    public Cursor getShownCursor() {
        return shownCursor;
    }

    public WGToolTip getToolTip() {
        return toolTip;
    }

    public WGTheme getCurrentTheme() {
        return currentTheme;
    }

    public WGPane getParentOwningPane() {
        return parentOwningPane;
    }
    
    public WGDragDropBar getDragAndDropBar() {
		return dragAndDropBar;
	}
    
	public boolean isResizing() {
		return isResizing;
	}
	
	public EventHandler<Event> getVerticalScrollListener() {
		return verticalScrollListener;
	}
	
	public EventHandler<Event> getHorizontalScrollListener() {
		return horizontalScrollListener;
	}
	
	public int getOrder() {
		return order;
	}
}
