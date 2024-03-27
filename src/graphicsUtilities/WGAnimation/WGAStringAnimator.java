/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

/**
 *
 * @author Westley
 * A Simple Animator class that defines different ways to manipulate a string per tick
 */
public class WGAStringAnimator extends WGAAnimator
{
    private String originalText;
    private String text;
    public WGAStringAnimator(int tickMax, int tick, String text)
    {
        super(tickMax, tick);
        this.text = text;
        this.originalText = text;
    }
    public synchronized void animateStringAddition(String textAddition)
    {
        text += textAddition;
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    
    
    //Getters:
    public String getText() {
        return text;
    }

    
    //Setters:
    /**
     * This will set the text to whatever you want, however this will reset its animation
     * @param text The text to set
     */
    public void setText(String text) 
    {
        this.text = text;
        this.originalText = text;
        resetAnimation();
    }
    
    /**
     * This resets the string animation
     */
    @Override
    public void resetAnimation() 
    {
        setTick(0);
        text = originalText;
    }
}
