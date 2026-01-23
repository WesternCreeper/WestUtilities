/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

/**
 *
 * @author Westley
 * Defines a simple class that can help do a variety of things depending on the subclass
 */
public abstract class WGAAnimator 
{
    private final int tickMax;
    private int tick = 0;
    /**
     * The basic definition of an Animator. Defines the top bound and assumes start from zero
     * @param tickMax The top bound, or how many times the thing will repeat
     */
    protected WGAAnimator(int tickMax)
    {
        this.tickMax = tickMax;
    }
    /**
     * The most advanced Animator. Defines the top bound and the starting point (Note this is the starting point for the first time, not subsequent uses)
     * @param tickMax The top bound, or how many times the thing will repeat
     * @param tick The starting point, cannot be greater than the tickMax since that would be out of bounds
     */
    protected WGAAnimator(int tickMax, int tick)
    {
        this.tickMax = tickMax;
        this.tick = tick;
    }
    
    public abstract void animate();
    
    
    //Getters:
    public int getTickMax() 
    {
        return tickMax;
    }

    public int getTick() 
    {
        return tick;
    }
    public boolean isDone()
    {
        return tick == tickMax;
    }
    
    
    //Setters:
    public void setTick(int tick) 
    {
        this.tick = tick;
    }
    /**
     * This is an overridable function that sets tick to zero. Use this to reset the animation. At this level there is nothing else to reset other than the tick so it is just a fancy setTick(0);
     */
    public void resetAnimation() 
    {
        tick = 0;
    }
}
