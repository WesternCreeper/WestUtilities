/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This serves as a grouping tool, with a cool rectangle surrounding it. By no means is this a JPanel or Window or other actual heavy-weight components. All this is, is a way to easily group WGDrawingObjects together, such as in menuing.
 * @author Westley
 */
public class WGPane extends WGDrawingObject
{
    private final boolean scrollable;
    private ArrayList<WGDrawingObject> containedObjects = new ArrayList<WGDrawingObject>(1);
    private Color backgroundColor;
    private Color borderColor;
    private Color scrollBarColor;
    protected PaneResizeListener resizer;
    private WGScrollableListener verticalScroll;
    private WGScrollableListener horizontalScroll;
    
    /**
     * 
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param scrollable The boolean that determines whether the pane is scrollable or not. This is a final variable so there is only one time to determine this
     * @param backgroundColor The color of the background of the pane
     * @param borderColor The border color of the pane
     * @param scrollBarColor The color of the scrollBar
     * @param parent The component that the pane is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
     */
    public WGPane(Rectangle2D.Double bounds, float borderSize, boolean scrollable, Color backgroundColor, Color borderColor, Color scrollBarColor, Component parent) throws WGNullParentException
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.scrollBarColor = scrollBarColor;
        this.scrollable = scrollable;
        if(getParent() != null)
        {
            resizer = new PaneResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            if(scrollable)
            {
                verticalScroll = new WGScrollableListener(this);
                getParent().addMouseWheelListener(verticalScroll);
                getParent().addMouseListener(verticalScroll);
                getParent().addMouseMotionListener(verticalScroll);
                horizontalScroll = new WGScrollableListener(this);
                getParent().addMouseWheelListener(horizontalScroll);
                getParent().addMouseListener(horizontalScroll);
                getParent().addMouseMotionListener(horizontalScroll);
            }
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    /**
     * 
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
    public WGPane(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, boolean scrollable, Color backgroundColor, Color borderColor, Color scrollBarColor, Component parent) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, scrollable, backgroundColor, borderColor, scrollBarColor, parent);
    }
    
    
    //Methods:
    @Override
    public Rectangle2D.Double getBounds() 
    {
       Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
       return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeComps();
    }
    public void setBounds(Rectangle2D.Double newBounds)
    {
        resizer.setBounds(newBounds);
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
    }
    /**
     * A wrapper for the ArrayList. Does the same thing as ArrayList.remove(int index), only these "Objects" are WGDrawingObjects
     * @param index The index of the object that one wants to remove
     * @return The object removed
     * @throws IndexOutOfBoundsException Thrown when the index is not within the bounds of the arrayList
     */
    public WGDrawingObject removeDrawableObject(int index) throws IndexOutOfBoundsException
    {
        return containedObjects.remove(index);
    }
    /**
     * A wrapper for the ArrayList. Does the same thing as ArrayList.removeAll(Collection(?) c), only these "Objects" are WGDrawingObjects
     */
    public void removeAllDrawableObjects()
    {
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
     * This sets the preference of the scrollbars. Either true or false based on the preference
     * @param preferred The preferred scrollbar. True is the vertical one, while false is the horizontal one
     */
    public void setScrollBarPreferrance(boolean preferred)
    {
        if(scrollable && verticalScroll != null && horizontalScroll != null)
        {
            verticalScroll.setPreference(preferred);
            verticalScroll.setPreference(!preferred);
        }
    }
    
    
    //Setters:
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setScrollBarColor(Color scrollBarColor) {
        this.scrollBarColor = scrollBarColor;
    }
    
    
    //Getters:
    public WGScrollableListener getVerticalScroll() {
        return verticalScroll;
    }

    public WGScrollableListener getHorizontalScroll() {
        return horizontalScroll;
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getScrollBarColor() {
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
    protected class PaneResizeListener extends WGDrawingObjectResizeListener
    {
        protected PaneResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            
            //Wait for all of the components added to this object to finish setting up:
            for(int i = 0 ; i < containedObjects.size() ; i++)
            {
                containedObjects.get(i).setUpBounds();
            }
            setUpScroll();
            
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
