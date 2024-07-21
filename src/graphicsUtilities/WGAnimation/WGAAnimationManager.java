/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities.WGAnimation;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * This is a robust class that allows for multiple higher level animations to be done with different options.
 * Also, allows for making different simultaneous animations easier
 * @author Westley
 */
public class WGAAnimationManager 
{
    private ArrayList<Timer> allTimers = new ArrayList<Timer>(1);
    
    public WGAAnimationManager() {}
    /**
     * The standard constructor that makes sure that there are no duplicate timers in the timers arrayList
     * @param timers An ArrayList of timers that define different intervals
     */
    public WGAAnimationManager(ArrayList<Timer> timers)
    {
        for(int i = 0 ; i < timers.size() ; i++)
        {
            addTimer(timers.get(i).getDelay(), timers.get(i).getActionListeners()[0]);
        }
    }
    
    /**
     * The way to add a listener to the animation manager, this will make sure that a proper timer is used, and results in no duplicate timers, which will help with synchronization
     * @param milliseconds The interval wait time between animations (In ms)
     * @param listener The actionListener that defines what happens when the interval has elapsed
     */
    public synchronized final void addTimer(int milliseconds, ActionListener listener)
    {
        boolean intervalExists = false;
        //Make sure that no timer already exists with that said time
        for(int i = 0 ; i < allTimers.size() ; i++)
        {
            if(allTimers.get(i).getDelay() == milliseconds) //If there is then add the listener to it
            {
                allTimers.get(i).addActionListener(listener);
                intervalExists = true;
                break;
            }
        }
        
        if(!intervalExists) //Else just make a new Timer for that interval
        {
            allTimers.add(new Timer(milliseconds, listener));
        }
    }
    
    /**
     * This stops the timer with the interval specified
     * @param milli the interval
     */
    public synchronized void stop(int milli)
    {
        for(int i = 0 ; i < allTimers.size() ; i++)
        {
            Timer timer = allTimers.get(i);
            if(timer.getDelay() == milli)
            {
                timer.stop();
                break;
            }
        }
    }
    
    /**
     * This starts the timer with the interval specified
     * @param milli the interval
     */
    public synchronized void start(int milli)
    {
        for(int i = 0 ; i < allTimers.size() ; i++)
        {
            Timer timer = allTimers.get(i);
            if(timer.getDelay() == milli)
            {
                timer.start();
                break;
            }
        }
    }
    
    /**
     * Please be careful with this function as different Listeners will all correspond to the same timer. Stops a timer based on the given index
     * @param index The index of the timer
     * @throws ArrayIndexOutOfBoundsException when the index is not valid
     */
    public synchronized void stopTimer(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index >= 0 && index < allTimers.size())
        {
            allTimers.get(index).stop();
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }
    
    /**
     * Please be careful with this function as different Listeners will all correspond to the same timer. Starts a timer based on the given index
     * @param index The index of the timer
     * @throws ArrayIndexOutOfBoundsException when the index is not valid
     */
    public synchronized void startTimer(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index >= 0 && index < allTimers.size())
        {
            allTimers.get(index).start();
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }
    
    /**
     * Use this function to stop all of the timers 
     */
    public synchronized void stopAllTimers()
    {
        for(int i = 0 ; i < allTimers.size() ; i++)
        {
            allTimers.get(i).stop();
        }
    }
    
    /**
     * Use this function to start all of the timers 
     */
    public synchronized void startAllTimers()
    {
        for(int i = 0 ; i < allTimers.size() ; i++)
        {
            allTimers.get(i).start();
        }
    }
}
