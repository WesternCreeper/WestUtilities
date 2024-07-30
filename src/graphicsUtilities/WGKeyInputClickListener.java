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
        if(isWithinBounds(e) && parent.isIsShown())
        {
            if(!parent.isFocused() && !e.isConsumed())
            {
                parent.setFocused(true);
                e.consume();
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
        if(isWithinBounds(e))
        {
            WGKeyInput textInput = (WGKeyInput)getParentObject();
            textInput.setBackgroundColorNotClickListener(WGColorHelper.getDarkerOrLighter(originalBackgroundColor, 1, WGColorHelper.PREFERRANCE_COLOR_LIGHTER));
        }
        else
        {
            WGKeyInput textInput = (WGKeyInput)getParentObject();
            textInput.setBackgroundColorNotClickListener(originalBackgroundColor);
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
}
