/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Paint;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Westley
 */
public class WGKeyInputClickListener extends WGClickListener implements MouseMotionListener
{
    private Paint originalBackgroundColor;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGKeyInputClickListener() {}
    public WGKeyInputClickListener (WGDrawingObject parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        WGKeyInput parent = (WGKeyInput)getParentObject();
        if(isWithinBounds(e) && isParentShown())
        {
            if(!parent.isFocused() && !e.isConsumed())
            {
                parent.setFocused(true);
                e.consume();
                
                //Cursor:
                WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
            }
        }
        else
        {
            parent.setFocused(false);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        if(e.isConsumed())
        {
            return;
        }
        WGKeyInput parent = (WGKeyInput)getParentObject();
        if(isWithinBounds(e) && parent.isFocused())
        {
            if(!parent.isFocused())
            {
                parent.setFocused(true);
                e.consume();
                
                //Cursor:
                WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
            }
        }
        else
        {
            parent.setFocused(false);
        }
        WestGraphics.doRepaintJob(parent.getParent());
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        //Cursor:
        WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
        WestGraphics.doRepaintJob(getParentObject().getParent());
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e){}
    
    @Override
    protected Cursor getCursorType()
    {
        WGKeyInput parent = (WGKeyInput)getParentObject();
        return parent.isFocused() ? WestGraphics.getTextCursor() : WestGraphics.getHoverCursor();
    }
    
    //Getters:
    public Paint getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Paint originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
}
