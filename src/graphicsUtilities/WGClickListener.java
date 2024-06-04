/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 * The WG specific click listener. AKA a mouseListener that is specific to WG Objects
 */
public class WGClickListener implements MouseListener
{
    private WGDrawingObject parentObject;
    private WGPane parentOwningPane;
    private Component parentComponent;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    protected WGClickListener() {}
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * ONLY USE THIS DEFINITION IF YOU KNOW 100% THAT THE WGOBJECT HAS THE PARENT
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @throws WGNullParentException When the WGObject does not have a parent, then it will throw a WGNullParentException so that this object can be supplied a parent Object
     */
    public WGClickListener(WGDrawingObject parentObject) throws WGNullParentException
    {
        this.parentObject = parentObject;
        this.parentComponent = parentObject.getParent();
        if(parentComponent == null)
        {
            throw new WGNullParentException();
        }
    }
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGClickListener(WGDrawingObject parentObject, Component parentComponent)
    {
        this.parentObject = parentObject;
        this.parentComponent = parentComponent;
    }
    /**
     * When you make a sub-class of this object make sure to use the "isWithinBounds" function with the MouseEvent e being the parameter so that this function can verify that it is within the bounds of the WGDrawingObject and not any part of the parent object.
     * Also to be able to provide function to this class make sure to sub-class this object, otherwise it don't work. This is just like it's superclass, but has additional WG specific functions
     */
    public void mouseClicked(MouseEvent e) 
    {
        if(isWithinBounds(e) && !e.isConsumed() && parentObject.isIsShown())
        {
            clickEvent(e);
            e.consume();
        }
    }

    @Override
    /**
     * See mouseClick Javadoc on how to use
     */
    public void mousePressed(MouseEvent e) {}

    @Override
    /**
     * See mouseClick Javadoc on how to use
     */
    public void mouseReleased(MouseEvent e) {}

    @Override
    /**
     * See mouseClick Javadoc on how to use
     */
    public void mouseEntered(MouseEvent e) {}

    @Override
    /**
     * See mouseClick Javadoc on how to use
     */
    public void mouseExited(MouseEvent e) {}
    
    /**
     * Figures out if the point given by the mouseEvent is within the given object's bounds
     * @param e
     * @return 
     */
    protected boolean isWithinBounds(MouseEvent e)
    {
        Point2D clickLoaction = e.getPoint();
        Rectangle2D.Double objectBounds = parentObject.getBounds();
        if(parentOwningPane != null) //If there is a pane then mkae sure it is within both bounds!
        {
            return objectBounds.contains(clickLoaction) && parentOwningPane.getBounds().contains(clickLoaction);
        }
        return objectBounds.contains(clickLoaction);
    }
    
    public Component getParentComponent() {
        return parentComponent;
    }

    public WGDrawingObject getParentObject() {
        return parentObject;
    }

    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public void setParentObject(WGDrawingObject parentObject) {
        this.parentObject = parentObject;
    }

    public void setParentOwningPane(WGPane parentOwningPane) {
        this.parentOwningPane = parentOwningPane;
    }
    
    public void clickEvent(MouseEvent e) {}
    
}
