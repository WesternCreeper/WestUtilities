/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import java.awt.Color;
import graphicsUtilities.WGColorHelper;
import java.util.ArrayList;

/**
 *
 * @author Westley
 */
public class WGAColorAnimator extends WGAAnimator
{
    private ArrayList<Color> colors = new ArrayList<Color>(1);
    private ArrayList<Color> originalColors = new ArrayList<Color>(1);
    private ArrayList<Boolean> hasBounced = new ArrayList<Boolean>(1);
    public WGAColorAnimator(int tickMax, int tick, Color color)
    {
        super(tickMax, tick);
        colors.add(color);
        originalColors.add(color);
        hasBounced.add(false);
        setTick(tickMax);
    }
    public synchronized void addColor(Color color)
    {
        colors.add(color);
        originalColors.add(color);
        hasBounced.add(false);
    }
    public synchronized void animateColorInverse()
    {
        for(int i = 0 ; i < colors.size() ; i++)
        {
            colors.set(i, WGColorHelper.getInverseColor(colors.get(i)));
        }
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    public synchronized void animateColorGrayOut()
    {
        for(int i = 0 ; i < colors.size() ; i++)
        {
            colors.set(i, WGColorHelper.getGrayScaleColor(colors.get(i)));
        }
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    public synchronized void animateColorAdd(int redAdd, int greenAdd, int blueAdd, int alphaAdd, int overflowColorBehavior)
    {
        for(int i = 0 ; i < colors.size() ; i++)
        {
            colors.set(i, WGColorHelper.getGrayScaleColor(colors.get(i)));//So that the animation also bounces:
            boolean willBounce = false;
            if(!hasBounced.get(i) && WGColorHelper.willBounce(colors.get(i), redAdd, greenAdd, blueAdd, alphaAdd))
            {
                willBounce = true;
            }
            else if(hasBounced.get(i) && WGColorHelper.willBounce(colors.get(i), redAdd * -1, greenAdd * -1, blueAdd * -1, alphaAdd * -1))
            {
                willBounce = true;
            }
            if(hasBounced.get(i))
            {
                colors.set(i, WGColorHelper.addToColor(colors.get(i), redAdd * -1, greenAdd * -1, blueAdd * -1, alphaAdd * -1, overflowColorBehavior));
            }
            else
            {
                colors.set(i, WGColorHelper.addToColor(colors.get(i), redAdd, greenAdd, blueAdd, alphaAdd, overflowColorBehavior));
            }
            if(willBounce)
            {
                hasBounced.set(i, !hasBounced.get(i));
            }
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
        for(int i = 0 ; i < colors.size() ; i++)
        {
            colors.set(i, WGColorHelper.combineTwoColors(originalColors.get(i), 1 - ((double)getTick() / getTickMax()), fadeColor, ((double)getTick() / getTickMax())));
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
        for(int i = 0 ; i < colors.size() ; i++)
        {
            colors.set(i, WGColorHelper.combineTwoColors(originalColors.get(i), ((double)getTick() / getTickMax()), fadeColor, 1 - ((double)getTick() / getTickMax())));
        }
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    
    
    //Getters:
    public Color getColor(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index >= 0 && index < colors.size())
        {
            return colors.get(index);
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }
    public int getColorSize()
    {
        return colors.size();
    }

    
    //Setters:
    /**
     * This will set the color to whatever you want, however this will reset its animation
     * @param color The color to set
     * @param index The index of the color
     * @throws ArrayIndexOutOfBoundsException When the index is not within the Colors araylist bounds
     */
    public void setColor(Color color, int index) throws ArrayIndexOutOfBoundsException
    {
        if(index >= 0 && index < colors.size())
        {
            colors.set(index, color);
            originalColors.set(index, color);
            hasBounced.set(index, false);
            resetAnimation();
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }
    
    /**
     * This resets the color animation
     */
    @Override
    public void resetAnimation() 
    {
        setTick(0);
        for(int i = 0 ; i < originalColors.size() ; i++)
        {
            colors.set(i, originalColors.get(i));
            hasBounced.set(i, false);
        }
    }
}
