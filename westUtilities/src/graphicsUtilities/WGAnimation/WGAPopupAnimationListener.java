/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import graphicsUtilities.WGButton;
import graphicsUtilities.WGDrawingObject;
import graphicsUtilities.WestGraphics;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 *
 * @author Westley
 */
public class WGAPopupAnimationListener
{
    private final Color fullTransparentColor = new Color(0, 0, 0, 0);
    private boolean popOut = false;
    private boolean isWorking = false;
    private WGAColorAnimator colorAnimator;
    private WGDrawingObject parentComp;
    public WGAPopupAnimationListener(WGAColorAnimator colorAnimator, WGDrawingObject parentObject)
    {
        this.colorAnimator = colorAnimator;
        parentComp = parentObject;
    }
    public synchronized void actionPerformed()
    {
        isWorking = !colorAnimator.isDone();
        if(!colorAnimator.isDone())
        {
            if(!parentComp.isShown())
            {
                parentComp.setShown(true);
            }
            if(popOut)
            {
                colorAnimator.fadeTo(fullTransparentColor);
                setColorsForParent();
            }
            else
            {
                if(!parentComp.isShown())
                {
                    parentComp.setShown(true);
                }
                colorAnimator.fadeFrom(fullTransparentColor);
                setColorsForParent();
            }
        }
        else
        {
            if(popOut && parentComp.isShown())
            {
                parentComp.setShown(false);
            }
        }
    }
    private synchronized void setColorsForParent()
    {
        if(parentComp instanceof WGButton)
        {
            WGButton button = (WGButton)parentComp;
            button.setBackgroundColor(colorAnimator.getColor(0));
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
