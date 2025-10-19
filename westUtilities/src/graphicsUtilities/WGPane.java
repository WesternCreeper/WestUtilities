/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;

/**
 * This serves as a grouping tool, with a cool rectangle surrounding it. By no means is this a JPanel or Window or other actual heavy-weight components. All this is, is a way to easily group WGDrawingObjects together, such as in menuing.
 * @author Westley
 */
public class WGPane extends WGBox
{
    public final static boolean VERTICAL_SCROLL_PREFERED = true;
    public final static boolean HORIZONTAL_SCROLL_PREFERED = false;
    private final boolean scrollable;
    private ArrayList<WGDrawingObject> containedObjects = new ArrayList<WGDrawingObject>(1);
    private Paint scrollBarColor;
    private WGScrollableListener verticalScroll;
    private WGScrollableListener horizontalScroll;
    
    /**
     * This creates a standard WGPane that holds numerous other objects. This constructor allows for the creation of a non-clickable, yet scrollable pane that has many robust features
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param scrollable The boolean that determines whether the pane is scrollable or not. This is a final variable so there is only one time to determine this
     * @param backgroundColor The color of the background of the pane
     * @param borderColor The border color of the pane
     * @param scrollBarColor The color of the scrollBar
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(Rectangle2D bounds, float borderSize, boolean scrollable, Paint backgroundColor, Paint borderColor, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, backgroundColor, borderColor, parent);
        this.scrollBarColor = scrollBarColor;
        this.scrollable = scrollable;
        if(getParent() != null)
        {
            resizer = new PaneResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
            if(scrollable)
            {
                verticalScroll = new WGScrollableListener(this);
                getParent().addEventHandler(MouseEvent.ANY, verticalScroll);
                getParent().addEventHandler(ScrollEvent.ANY, verticalScroll);
                horizontalScroll = new WGScrollableListener(this);
                getParent().addEventHandler(MouseEvent.ANY, horizontalScroll);
                getParent().addEventHandler(ScrollEvent.ANY, horizontalScroll);
            }
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    /**
     * This creates a standard WGPane that holds numerous other objects. This constructor allows for the creation of a non-clickable, yet scrollable pane that has many robust features
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param scrollable The boolean that determines whether the pane is scrollable or not. This is a final variable so there is only one time to determine this
     * @param backgroundColor The color of the background of the pane
     * @param borderColor The border color of the pane
     * @param scrollBarColor The color of the scrollBar
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, boolean scrollable, Paint backgroundColor, Paint borderColor, Paint scrollBarColor, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, scrollable, backgroundColor, borderColor, scrollBarColor, parent);
    }
    
    /**
     * This creates a standard WGPane that holds numerous other objects. This constructor allows for the creation of a non-clickable, yet scrollable pane that has many robust features
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param scrollable The boolean that determines whether the pane is scrollable or not. This is a final variable so there is only one time to determine this
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(Rectangle2D bounds, boolean scrollable, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), scrollable, theme.getBackgroundColor(), theme.getBorderColor(), theme.getScrollBarColor(), parent);
        setCurrentTheme(theme);
    }
    
    /**
     * This creates a clickable WGPane that can hold numerous other objects. This constructor allows for the creation of a clickable, but not scrollable pane that has many robust features
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param backgroundColor The color of the background of the pane
     * @param borderColor The border color of the pane
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(Rectangle2D bounds, float borderSize, Paint backgroundColor, Paint borderColor, Canvas parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(bounds, borderSize, false, backgroundColor, borderColor, null, parent);
        
        //Now make the clickListener
        if(getParent() != null)
        {
            super.setClickListener(clickListener);
            getClickListener().setParentComponent(getParent());
            getClickListener().setParentObject(this);
            getParent().addEventHandler(MouseEvent.ANY, getClickListener());
            getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
            WestGraphics.add(this);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    /**
     * This creates a clickable WGPane that can hold numerous other objects. This constructor allows for the creation of a clickable, but not scrollable pane that has many robust features
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param backgroundColor The color of the background of the pane
     * @param borderColor The border color of the pane
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, Paint backgroundColor, Paint borderColor, Canvas parent, WGButtonListener clickListener) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, backgroundColor, borderColor, parent, clickListener);
    }
    
    /**
     * This creates a clickable WGPane that can hold numerous other objects. This constructor allows for the creation of a clickable, but not scrollable pane that has many robust features
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @param clickListener The WGClickListener that defines what will happen when the object has been clicked on. This is fully set up with baseline parameter before use so no need to set up base parameters
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(Rectangle2D bounds, Canvas parent, WGButtonListener clickListener, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), theme.getBackgroundColor(), theme.getBorderColor(), parent, clickListener);
        setCurrentTheme(theme);
    }
    
    
    //Methods:
    @Override
    public Rectangle2D getBounds() 
    {
       Rectangle2D bounds = new Rectangle2D(getX(), getY(), getWidth(), getHeight());
       return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeComps();
    }
    public void setBounds(Rectangle2D newBounds)
    {
        resizer.setBounds(newBounds);
    }
    @Override
    public void setShown(boolean isShown) 
    {
        //Do our own checking:
        super.setShown(isShown);
        //Now do it for all of the objects within this one:
        for(int i = 0 ; i < containedObjects.size() ; i++)
        {
            containedObjects.get(i).setShown(isShown);
        }
    }
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        this.scrollBarColor = theme.getScrollBarColor();
        resizer.resizeComps();
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().widthProperty().removeListener(resizer.getResizeListener());
        getParent().heightProperty().removeListener(resizer.getResizeListener());
        if(getToolTip() != null)
        {
            getToolTip().removeListeners();
        }
        
        WGScrollableListener scrollerH = getHorizontalScroll();
        WGScrollableListener scrollerV = getVerticalScroll();
        if(scrollerH != null)
        {
	        getParent().removeEventHandler(MouseEvent.ANY, scrollerH);
	        getParent().removeEventHandler(ScrollEvent.ANY, scrollerH);
        }
        if(scrollerV != null)
        {
            getParent().removeEventHandler(MouseEvent.ANY, scrollerV);
            getParent().removeEventHandler(ScrollEvent.ANY, scrollerV);
        }
        
        WGClickListener clickListener = getClickListener();
        if(clickListener != null)
        {
	        getParent().removeEventHandler(MouseEvent.ANY, getClickListener());
	        getParent().removeEventHandler(ScrollEvent.ANY, getClickListener());
        }
        WestGraphics.remove(this);
    }
    
    /**
     * A wrapper for the ArrayList. Does the same thing as ArrayList.add(Object e), only these "Objects" are WGDrawingObjects. Also keep in mind that the order this draws in is the same as the order that the components are added to the object so add tooltips to the end of the list
     * @param obj The component that groups with this one, preferably smaller that this object, however that is not a requirement
     */
    public void addDrawableObject(WGDrawingObject obj)
    {
        containedObjects.add(obj);
        WGClickListener objListener = obj.getClickListener();
        if(objListener != null)
        {
            objListener.setParentOwningPane(this);
        }
        setUpScroll();
    }
    /**
     * A wrapper for the ArrayList. Does the same thing as ArrayList.removeAll(Collection(?) c), only these "Objects" are WGDrawingObjects. This removes the listeners of the individual objects
     */
    public void removeAllDrawableObjects()
    {
        if(scrollable)
        {
            verticalScroll.resetScroll();
            horizontalScroll.resetScroll();
        }
        
        //Remove all listeners, as they will still listen even after the object disappears
        int count = 0;
        for(int i = containedObjects.size()-1 ; i >= 0 ; i--)
        {
            WGDrawingObject obj = containedObjects.remove(i);
            if(obj instanceof WGPane)
            {
            	WGPane pane = (WGPane)obj;
            	pane.removeAllDrawableObjects();
            }
            obj.removeListeners();
            if(obj instanceof WGBox)
            {
                count++;
            }
        }
    }
    /**
     * A wrapper for the ArrayList. Does the same thing as ArrayList.removeAll(Collection(?) c), only these "Objects" are WGDrawingObjects. Doesn't remove the listeners of the removed objects
     */
    public void removeAllDrawableObjectsNotListeners()
    {
        verticalScroll.resetScroll();
        horizontalScroll.resetScroll();
        
        //Now remove the actual objects now that the listeners have been removed
        containedObjects.removeAll(containedObjects);
    }
    /**
     * This function will go through every single component added to it and make the stroke size be the newStrokeSize and itself
     * @param newStrokeSize The stroke size that every component in the ArrayList will become
     */
    public void standardizeStrokeSize(float newStrokeSize)
    {
        setBorderSize(newStrokeSize);
        for(int i = 0 ; i < containedObjects.size() ; i++)
        {
            containedObjects.get(i).setBorderSize(newStrokeSize);
        }
    }
    
    /**
     * This sets up the scroll so that the current area can scroll properly
     */
    public void setUpScroll()
    {
        if(scrollable && verticalScroll != null && horizontalScroll != null)
        {
            verticalScroll.setUpScroll(true, containedObjects);
            horizontalScroll.setUpScroll(false, containedObjects);
        }
    }
    
    /**
     * This sets up the scroll so that the current area can scroll properly
     * @param expanding Whether the new bounds are larger or smaller than the old ones
     */
    public void changeScrollBounds(boolean expanding)
    {
        if(scrollable && verticalScroll != null && horizontalScroll != null)
        {
            verticalScroll.changeScrollBounds(true, containedObjects, expanding);
            horizontalScroll.changeScrollBounds(false, containedObjects, expanding);
        }
    }
    /**
     * This sets the preference of the scrollbars. Either true or false based on the preference
     * @param preferred The preferred scrollbar. True is the vertical one, while false is the horizontal one
     */
    public void setScrollBarPreferrance(boolean preferred)
    {
        if(scrollable && verticalScroll != null && horizontalScroll != null)
        {
            verticalScroll.setPreference(preferred);
            horizontalScroll.setPreference(preferred);
        }
    }
    
    /**
     * Finds out if the object given would be visible on this pane based on its current position.
     * @param drawableObject The object needing to be tested
     * @return Boolean (true if on, else false)
     */
    public boolean isDrawableObjectVisible(WGDrawingObject drawableObject)
    {
        if(drawableObject.getY() + drawableObject.getHeight() > getY() && drawableObject.getY() < getY() + getHeight())
        {
            if(drawableObject.getX() + drawableObject.getWidth() > getX() && drawableObject.getX() < getX() + getWidth())
            {
                return true;
            }
        }
        return false;
    }
    
    
    //Setters:

    public void setScrollBarColor(Paint scrollBarColor) {
        this.scrollBarColor = scrollBarColor;
    }
    
    
    //Getters:
    public WGScrollableListener getVerticalScroll() {
        return verticalScroll;
    }

    public WGScrollableListener getHorizontalScroll() {
        return horizontalScroll;
    }

    public Paint getScrollBarColor() {
        return scrollBarColor;
    }

    public boolean isScrollable() {
        return scrollable;
    }
    
    /**
     * This is a wrapper for the ArrayList. All this does is get the size of the ArrayList of WGDrawingObjects inside of this pane
     * @return The size of the ArrayList
     */
    public int getComponentNumber()
    {
        return containedObjects.size();
    }
    
    /**
     * A wrapper for the ArrayList. Does the same thing as ArrayList.get(int index), only these "Objects" are WGDrawingObjects
     * @param index The index of the object that one wants to get
     * @return The object gotten
     * @throws IndexOutOfBoundsException Thrown when the index is not within the bounds of the arrayList
     */
    public WGDrawingObject getComponent(int index) throws IndexOutOfBoundsException
    {
        return containedObjects.get(index);
    }
    
    
    //classes or Listeners:
    private class PaneResizeListener extends WGDrawingObjectResizeListener
    {
        protected PaneResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
        	Platform.runLater(() -> {
	            //Find the parent width and height so that the x/y can be scaled accordingly
	            double parentWidth = getParent().getWidth();
	            double parentHeight = getParent().getHeight();
	            //Set up the x, y, width, and height components based on the percentages given and the parent's size
	            setX(getXPercent() * parentWidth);
	            setY(getYPercent() * parentHeight);
	            setWidth(getWidthPercent() * parentWidth);
	            setHeight(getHeightPercent() * parentHeight);
	            
	            //Now fix the colors of this object:
	            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
	            {
	                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
	                setHoverBackgroundColor(fixPaintBounds(getHoverBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.HOVER_BACKGROUND_COLOR)));
	                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
	                scrollBarColor = fixPaintBounds(scrollBarColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.SCROLL_BAR_COLOR));
	            }
	            
	            //Wait for all of the components added to this object to finish setting up:
	            for(int i = 0 ; i < containedObjects.size() ; i++)
	            {
	                containedObjects.get(i).setUpBounds();
	            }
	            setUpScroll();
        	});
        }
    }
}
