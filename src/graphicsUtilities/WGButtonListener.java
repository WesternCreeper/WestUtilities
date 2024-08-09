/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Westley
 */
public class WGButtonListener extends WGClickListener implements MouseMotionListener, MouseWheelListener
{
    private Color originalBackgroundColor;
    private boolean cursorSet = false;
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
    public WGButtonListener(WGButton parentObject) throws WGNullParentException
    {
        super(parentObject);
    }
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGButtonListener(WGButton parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Color originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
    
    //Getters:
    public Color getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e)
    {
        hoverEvent(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        hoverEvent(e);
    }
    
    public void hoverEvent(MouseEvent e)
    {
        if(isWithinBounds(e))
        {
            //The background
            WGButton button = (WGButton)getParentObject();
            button.setBackgroundColorNotClickListener(WGColorHelper.getDarkerOrLighter(originalBackgroundColor));
            button.getParent().repaint();
            
            //The cursor
            if(button.isIsShown())
            {
                getParentComponent().setCursor(WestGraphics.getHoverCursor());
                cursorSet = true;
                e.consume();
            }
        }
        else
        {
            //The background
            WGButton button = (WGButton)getParentObject();
            button.setBackgroundColorNotClickListener(originalBackgroundColor);
            button.getParent().repaint();
            
            //The cursor
            if(cursorSet && !e.isConsumed())
            {
                getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                cursorSet = false;
                e.consume();
            }
        }
    }
}