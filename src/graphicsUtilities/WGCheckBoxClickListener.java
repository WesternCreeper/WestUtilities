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
public class WGCheckBoxClickListener extends WGClickListener implements MouseMotionListener, MouseWheelListener
{
    private Paint originalBackgroundColor;
    private boolean cursorSet = false;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public  WGCheckBoxClickListener() {}
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * ONLY USE THIS DEFINITION IF YOU KNOW 100% THAT THE WGOBJECT HAS THE PARENT
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @throws WGNullParentException When the WGObject does not have a parent, then it will throw a WGNullParentException so that this object can be supplied a parent Object
     */
    public WGCheckBoxClickListener(WGDrawingObject parentObject) throws WGNullParentException
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
    public WGCheckBoxClickListener(WGDrawingObject parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
        
        //Make sure to set the cursor to the correct one when shown:
        parentObject.setShownCursor(WestGraphics.getHoverCursor());
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
            WGCheckBox parent = (WGCheckBox)getParentObject();
            if(originalBackgroundColor instanceof Color)
            {
                parent.setBackgroundColorNotClickListener(WGColorHelper.getDarkerOrLighter((Color)originalBackgroundColor));
                parent.getParent().repaint();
            }
            else if(originalBackgroundColor instanceof RadialGradientPaint)
            {
                parent.setBackgroundColorNotClickListener(parent.fixPaintBounds(originalBackgroundColor, WGDrawingObject.RADIAL_CENTER_GRADIENT_ORIENTATION_WITH_MOUSE_MOVE_PREFERENCE, e));
                parent.getParent().repaint();
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
            WGCheckBox parent = (WGCheckBox)getParentObject();
            parent.setBackgroundColorNotClickListener(originalBackgroundColor);
            parent.getParent().repaint();
            
            //The cursor
            if(cursorSet && !e.isConsumed())
            {
                getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                cursorSet = false;
                e.consume();
            }
        }
    }
    
    public void clickEvent(MouseEvent e) 
    {
        WGCheckBox parent = (WGCheckBox)getParentObject();
        parent.setChecked(!parent.isChecked());
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
    public void setParentObject(WGDrawingObject obj)
    {
        super.setParentObject(obj);
        
        //Make sure to set the cursor to the correct one when shown:
        obj.setShownCursor(WestGraphics.getHoverCursor());
    }
}
