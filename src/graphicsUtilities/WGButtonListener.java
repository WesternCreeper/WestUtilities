/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Component;
import java.awt.RadialGradientPaint;
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
    private Paint originalBackgroundColor;
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
    public WGButtonListener(WGBox parentObject) throws WGNullParentException
    {
        super(parentObject);
        
        //Make sure to set the cursor to the correct one when shown:
        parentObject.setShownCursor(WestGraphics.getHoverCursor());
    }
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGButtonListener(WGBox parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
        
        //Make sure to set the cursor to the correct one when shown:
        parentObject.setShownCursor(WestGraphics.getHoverCursor());
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Paint originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
    
    //Getters:
    public Paint getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        setLastMouseEvent(e);
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        setLastMouseEvent(e);
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
            WGBox button = (WGBox)getParentObject();
            
            boolean canDoIt = true;
            //If it is a Pane, make sure this is not hovering over another object on it
            if(button instanceof WGPane)
            {
                WGPane paneParent = (WGPane)button;
                for(int i = 0 ; i < paneParent.getComponentNumber() ; i++)
                {
                    WGDrawingObject ownedObject = paneParent.getComponent(i);

                    //Now if it has a underling object make sure to test if it is within bounds:
                    WGClickListener clickListener = ownedObject.getClickListener();
                    if(clickListener != null)
                    {
                        if(clickListener.isWithinBounds(e))
                        {
                            canDoIt = false;
                            break;
                        }
                    }
                }
            }
            
            if(canDoIt)
            {
                if(originalBackgroundColor instanceof Color)
                {
                    button.setBackgroundColorNotClickListener(WGColorHelper.getDarkerOrLighter((Color)originalBackgroundColor));
                    button.getParent().repaint();
                }
                else if(originalBackgroundColor instanceof RadialGradientPaint)
                {
                    button.setBackgroundColorNotClickListener(button.fixPaintBounds(originalBackgroundColor, WGDrawingObject.RADIAL_CENTER_GRADIENT_ORIENTATION_WITH_MOUSE_MOVE_PREFERENCE, e));
                    button.getParent().repaint();
                }

                //The cursor
                if(isParentShown())
                {
                    getParentComponent().setCursor(WestGraphics.getHoverCursor());
                    cursorSet = true;
                    e.consume();
                }
                else if(cursorSet && !e.isConsumed())
                {
                    getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                    cursorSet = false;
                    e.consume();
                }
            }
            else
            {
                button.setBackgroundColorNotClickListener(originalBackgroundColor);
                button.getParent().repaint();
            }
        }
        else
        {
            //The background
            WGBox button = (WGBox)getParentObject();
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
    @Override
    public void setParentObject(WGDrawingObject obj)
    {
        super.setParentObject(obj);
        
        //Make sure to set the cursor to the correct one when shown:
        obj.setShownCursor(WestGraphics.getHoverCursor());
    }
}