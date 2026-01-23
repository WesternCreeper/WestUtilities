/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import graphicsUtilities.ColoredString;

/**
 *
 * @author Westley
 * A Simple Animator class that defines different ways to manipulate a string per tick
 */
public class WGAStringAnimator extends WGAAnimator
{
    private ColoredString originalText;
    private ColoredString text;
    private String textAddition;
    private double translationAngle;
    private double translationAmount;
    private double xDif = 0;
    private double yDif = 0;
    private WGAStringAnimatorAnimationType type;
    public WGAStringAnimator(int tickMax, int tick, ColoredString text, WGAStringAnimatorAnimationType type)
    {
        super(tickMax, tick);
        this.text = text;
        this.originalText = new ColoredString(text);
        this.type = type;
    }
    
    public void animate() 
    {
    	if(type == WGAStringAnimatorAnimationType.ADD_TEXT)
    	{
            text.concat(textAddition, 0);
    	}
    	else if(type == WGAStringAnimatorAnimationType.TRANSLATE_TEXT)
    	{
    		//Do the math:
    		xDif += translationAmount * Math.cos(translationAngle);
    		yDif += translationAmount * Math.sin(translationAngle);
    	}
        setTick(getTick()+1);
        if(getTick() == getTickMax()+1)
        {
            resetAnimation();
        }
    }
    
    
    //Getters:
    public ColoredString getText() {
        return text;
    }
    
    public double getXDif() {
		return xDif;
	}

	public double getYDif() {
		return yDif;
	}

    
    //Setters:
    /**
     * This will set the text to whatever you want, however this will reset its animation
     * @param text The text to set
     */
    public void setText(ColoredString text) 
    {
        this.text = text;
        this.originalText = new ColoredString(text);
        resetAnimation();
    }

	public void setTextAddition(String textAddition) {
		this.textAddition = textAddition;
        resetAnimation();
	}

	public void setTranslationAngle(double translationAngle) {
		this.translationAngle = translationAngle;
        resetAnimation();
	}

	public void setTranslationAmount(double translationAmount) {
		this.translationAmount = translationAmount;
        resetAnimation();
	}

	/**
     * This resets the string animation
     */
    @Override
    public void resetAnimation() 
    {
        setTick(0);
        text = new ColoredString(originalText);
        xDif = 0;
        yDif = 0;
    }
}
