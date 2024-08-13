/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Westley
 */
public class WGKeyInputClickListener extends WGClickListener implements MouseMotionListener
{
    private Color originalBackgroundColor;
    private boolean cursorSet = false;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGKeyInputClickListener() {}
    public WGKeyInputClickListener (WGDrawingObject parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
        
        //Make sure to set the cursor to the correct one when shown:
        parentObject.setShownCursor(WestGraphics.getHoverCursor());
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        setLastMouseEvent(e);
        WGKeyInput parent = (WGKeyInput)getParentObject();
        if(isWithinBounds(e) && isParentShown())
        {
            if(!parent.isFocused() && !e.isConsumed())
            {
                parent.setFocused(true);
                e.consume();
                
                //Now set the cursor
                getParentComponent().setCursor(WestGraphics.getTextCursor());
                getParentObject().setShownCursor(WestGraphics.getTextCursor());
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
        setLastMouseEvent(e);
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
                
                //Now set the cursor
                getParentComponent().setCursor(WestGraphics.getTextCursor());
                getParentObject().setShownCursor(WestGraphics.getTextCursor());
            }
        }
        else
        {
            parent.setFocused(false);
        }
        parent.getParent().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        setLastMouseEvent(e);
        if(isWithinBounds(e))
        {
            WGKeyInput textInput = (WGKeyInput)getParentObject();
            textInput.setBackgroundColorNotClickListener(WGColorHelper.getDarkerOrLighter(originalBackgroundColor, 1, WGColorHelper.PREFERRANCE_COLOR_LIGHTER));
            
            //The cursor
            if(isParentShown())
            {
                if(!textInput.isFocused()) //To press on cursor
                {
                    getParentComponent().setCursor(WestGraphics.getHoverCursor());
                    getParentObject().setShownCursor(WestGraphics.getHoverCursor());
                }
                else //To type cursor
                {
                    getParentComponent().setCursor(WestGraphics.getTextCursor());
                    getParentObject().setShownCursor(WestGraphics.getTextCursor());
                }
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
            WGKeyInput textInput = (WGKeyInput)getParentObject();
            textInput.setBackgroundColorNotClickListener(originalBackgroundColor);
            
            //The cursor
            if(cursorSet && !e.isConsumed())
            {
                getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                cursorSet = false;
                e.consume();
            }
        }
        getParentObject().getParent().repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e){}
    
    //Getters:
    public Color getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Color originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
    
    @Override
    public void setParentObject(WGDrawingObject obj)
    {
        super.setParentObject(obj);
        
        //Make sure to set the cursor to the correct one when shown:
        obj.setShownCursor(WestGraphics.getHoverCursor());
    }
}
