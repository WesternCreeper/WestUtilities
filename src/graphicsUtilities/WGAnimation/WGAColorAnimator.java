/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import java.awt.Color;
import graphicsUtilities.WGColorHelper;

/**
 *
 * @author Westley
 */
public class WGAColorAnimator extends WGAAnimator
{
    private Color originalColor;
    private Color color;
    private boolean hasBounced = false;
    public WGAColorAnimator(int tickMax, int tick, Color color)
    {
        super(tickMax, tick);
        this.color = color;
        this.originalColor = color;
    }
    public synchronized void animateColorInverse()
    {
        color = WGColorHelper.getInverseColor(color);
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    public synchronized void animateColorGrayOut()
    {
        color = WGColorHelper.getGrayScaleColor(color);
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    public synchronized void animateColorAdd(int redAdd, int greenAdd, int blueAdd, int alphaAdd, int overflowColorBehavior)
    {
        //So that the animation also bounces:
        boolean willBounce = false;
        if(!hasBounced && WGColorHelper.willBounce(color, redAdd, greenAdd, blueAdd, alphaAdd))
        {
            willBounce = true;
        }
        else if(hasBounced && WGColorHelper.willBounce(color, redAdd * -1, greenAdd * -1, blueAdd * -1, alphaAdd * -1))
        {
            willBounce = true;
        }
        if(hasBounced)
        {
            color = WGColorHelper.addToColor(color, redAdd * -1, greenAdd * -1, blueAdd * -1, alphaAdd * -1, overflowColorBehavior);
        }
        else
        {
            color = WGColorHelper.addToColor(color, redAdd, greenAdd, blueAdd, alphaAdd, overflowColorBehavior);
        }
        if(willBounce)
        {
            hasBounced = !hasBounced;
        }
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    /**
     * This is a specific version of the fadeTo function that fades to black. This is only because this is a common function
     */
    public synchronized void fadeToBalck()
    {
        fadeTo(Color.black);
    }
    /**
     * This "fades" to whichever color one wishes to fade to. Make sure to have a fast timer, with a high tick count for optimal results
     * @param fadeColor The color that the animation fades to
     */
    public synchronized void fadeTo(Color fadeColor)
    {
        color = WGColorHelper.combineTwoColors(originalColor, 1 - ((double)getTick() / getTickMax()), fadeColor, ((double)getTick() / getTickMax()));
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    /**
     * This is a specific version of the fadeTo function that fades to black. This is only because this is a common function
     */
    public synchronized void fadeFromBalck()
    {
        fadeFrom(Color.black);
    }
    /**
     * This "fades" from whichever color one wishes to fade from. Make sure to have a fast timer, with a high tick count for optimal results. This is just a fade in function
     * @param fadeColor The color that the animation fades from (Fades from Black into the game, or whatever)
     */
    public synchronized void fadeFrom(Color fadeColor)
    {
        color = WGColorHelper.combineTwoColors(originalColor, ((double)getTick() / getTickMax()), fadeColor, 1 - ((double)getTick() / getTickMax()));
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    
    
    //Getters:
    public Color getColor() {
        return color;
    }

    
    //Setters:
    /**
     * This will set the color to whatever you want, however this will reset its animation
     * @param color The color to set
     */
    public void setColor(Color color) 
    {
        this.color = color;
        this.originalColor = color;
        resetAnimation();
    }
    
    /**
     * This resets the color animation
     */
    @Override
    public void resetAnimation() 
    {
        setTick(0);
        color = originalColor;
        hasBounced = false;
    }
}
