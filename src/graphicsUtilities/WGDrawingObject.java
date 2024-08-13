/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 * This class defines the basic functions of a West Graphics object. Specifically it defines functions like: getBounds() and other important cross object functionalities
 */
public abstract class WGDrawingObject
{
    private Component parent;
    private WGClickListener clickListener;
    protected WGDrawingObjectResizeListener resizer;
    private Cursor shownCursor;
    private double x = 0;
    private double y = 0;
    private double width;
    private double height;
    private float borderSize = 1;
    private boolean isShown = true;
    /**
     * This defines a basic WGDrawingObject, which is another term for a shared commonality among different drawable objects. Specifically this defines the X, Y, Width, Height, and Border Size of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     */
    protected WGDrawingObject(double x, double y, double width, double height, float borderSize)
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
    protected WGDrawingObject(double x, double y, double width, double height, float borderSize, Component parent)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderSize  = borderSize;
        this.parent = parent;
    }
    
    //Methods:
    public abstract Rectangle2D.Double getBounds();
    
    public abstract void setUpBounds();
    
    public abstract void setBounds(Rectangle2D.Double newBounds);
    
    public abstract void removeListeners();
    
    
    //Setters:
    public void setIsShown(boolean isShown) 
    {
        setIsShown(isShown, true);
    }
    /**
     * This sets whether the component is shown or not and sets the cursor based on the second variable
     * @param isShown Shows or hides the component
     * @param setCursor Sets or doesn't the cursor
     */
    public void setIsShown(boolean isShown, boolean setCursor) 
    {
        this.isShown = isShown;
        if(setCursor)
        {
            if(isShown && shownCursor != null)
            {
                //Now make sure the last event was within the bounds:
                if(clickListener != null && WestGraphics.lastMouseEvent != null)
                {
                    if(clickListener.isWithinBounds(WestGraphics.lastMouseEvent))
                    {
                        parent.setCursor(shownCursor);
                    }
                }
            }
            else if(!isShown)
            {
                parent.setCursor(WestGraphics.getDefaultCursor());
            }
        }
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

    public void setBorderSize(float borderSize) {
        this.borderSize = borderSize;
    }

    public void setClickListener(WGClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setShownCursor(Cursor shownCursor) {
        this.shownCursor = shownCursor;
    }
    
    //Getters:
    public boolean isIsShown() {
        return isShown;
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

    public float getBorderSize() {
        return borderSize;
    }

    public Component getParent() {
        return parent;
    }
    
    public WGClickListener getClickListener() {
        return clickListener;
    }

    public WGDrawingObjectResizeListener getResizer() {
        return resizer;
    }
}
