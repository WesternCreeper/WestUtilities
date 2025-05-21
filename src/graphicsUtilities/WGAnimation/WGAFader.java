/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import graphicsUtilities.WestGraphics;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Westley
 */
public abstract class WGAFader implements ActionListener
{
    private Color fadeColor;
    private Color fadeOverlay;
    private WGAColorAnimator colorAnimator;
    private Component parent;
    private boolean isDone = false;
    public WGAFader(int tickMax, Color fadeColor, Color fadeToColor, Component parent)
    {
        this.parent = parent;
        this.fadeColor = fadeToColor;
        colorAnimator = new WGAColorAnimator(tickMax, 0, fadeColor);
        
        //Start stopped:
        isDone = true;
        colorAnimator.setTick(tickMax);
    }
    public synchronized void actionPerformed(ActionEvent e)
    {
        if(!colorAnimator.isDone())
        {
            colorAnimator.fadeTo(fadeColor);
            fadeOverlay = colorAnimator.getColor(0);
        }
        else
        {
            if(!isDone)
            {
                onCompletion();
                isDone = true;
            }
        }
        WestGraphics.doRepaintJob(parent);
    }
    public synchronized void reset()
    {
        colorAnimator.resetAnimation();
        isDone = false;
        fadeOverlay = colorAnimator.getColor(0);
    }
    public abstract void onCompletion();
    
    
    public Color getFadeOverlay() {
        return fadeOverlay;
    }

    protected Color getFadeColor() {
        return fadeColor;
    }
    
    public boolean isDone() {
        return isDone;
    }
    
    public void setAnimator(int tickMax, Color fadeColor, Color fadeToColor)
    {
        this.fadeColor = fadeToColor;
        colorAnimator = new WGAColorAnimator(tickMax, 0, fadeColor);
        
        //Start stopped:
        isDone = true;
        colorAnimator.setTick(tickMax);
    }
}
