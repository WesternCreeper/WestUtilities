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
public class WGTextInputClickListener extends WGClickListener implements MouseMotionListener
{
    private Color originalBackgroundColor;
    public WGTextInputClickListener (WGDrawingObject parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(e.isConsumed())
        {
            return;
        }
        WGTextInput parent = (WGTextInput)getParentObject();
        if(isWithinBounds(e) && parent.isIsShown())
        {
            parent.setFocused(true);
            e.consume();
        }
        else
        {
            parent.setFocused(false);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if(isWithinBounds(e))
        {
            WGTextInput textInput = (WGTextInput)getParentObject();
            textInput.setBackgroundColorNotClickListener(WGColorHelper.getDarkerOrLighter(originalBackgroundColor, 1, WGColorHelper.PREFERRANCE_COLOR_LIGHTER));
        }
        else
        {
            WGTextInput textInput = (WGTextInput)getParentObject();
            textInput.setBackgroundColorNotClickListener(originalBackgroundColor);
        }
    }
    
    //Getters:
    public Color getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Color originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
}
