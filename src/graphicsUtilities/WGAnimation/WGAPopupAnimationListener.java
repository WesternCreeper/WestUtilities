/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import graphicsUtilities.WGDrawingObject;
import graphicsUtilities.WGButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Westley
 */
public class WGAPopupAnimationListener implements ActionListener
{
    private final Color fullTransparentColor = new Color(0, 0, 0, 0);
    private boolean popOut = false;
    private boolean isWorking = false;
    private WGAColorAnimator colorAnimator;
    private Component parent;
    private WGDrawingObject parentComp;
    public WGAPopupAnimationListener(WGAColorAnimator colorAnimator, Component parent, WGDrawingObject parentObject)
    {
        this.colorAnimator = colorAnimator;
        this.parent = parent;
        parentComp = parentObject;
    }
    public synchronized void actionPerformed(ActionEvent e)
    {
        isWorking = !colorAnimator.isDone();
        if(!colorAnimator.isDone())
        {
            if(popOut)
            {
                colorAnimator.fadeTo(fullTransparentColor);
                setColorsForParent();
            }
            else
            {
                if(!parentComp.isIsShown())
                {
                    parentComp.setIsShown(true);
                }
                colorAnimator.fadeFrom(fullTransparentColor);
                setColorsForParent();
            }
            parent.repaint();
        }
        else
        {
            if(popOut)
            {
                parentComp.setIsShown(false);
            }
        }
    }
    private synchronized void setColorsForParent()
    {
        if(parentComp instanceof WGButton)
        {
            WGButton button = (WGButton)parentComp;
            button.setBackgroundColorNotClickListener(colorAnimator.getColor(0));
            button.setTextColor(colorAnimator.getColor(1));
            button.setBorderColor(colorAnimator.getColor(2));
        }
    }
    public synchronized void reset()
    {
        colorAnimator.resetAnimation();
    }
    
    //setters:
    public void setPopOut(boolean popOut) {
        this.popOut = popOut;
    }
    
    //getters:

    public boolean isPopOut() {
        return popOut;
    }

    public boolean isIsWorking() {
        return isWorking;
    }
    
}
