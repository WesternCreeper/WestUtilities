/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Westley
 * This is a simple utility that converts milliseconds into whatever the largest unit is that is needed to be displayed. Additional options can be done in the constructor
 */
public class Time 
{
    private long timeLength;
    public Time(long timeLength)
    {
        this.timeLength = timeLength;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        long currentTimeLeft = timeLength;
        
        if(currentTimeLeft >= 1000) //MS to S
        {
            currentTimeLeft /= 1000;
            if(currentTimeLeft >= 60) //S to Min
            {
                currentTimeLeft /= 60;
                if(currentTimeLeft >= 60) //Min to Hr
                {
                    currentTimeLeft /= 60;
                    if(currentTimeLeft >= 24) //Hr to Day
                    {
                        currentTimeLeft /= 24;
                        if(currentTimeLeft >= 365) //Day to Year
                        {
                            currentTimeLeft /= 365;
                            str = currentTimeLeft + " Year";
                            if(currentTimeLeft != 1)
                            {
                                str += "s";
                            }
                        }
                        else
                        {
                            str = currentTimeLeft + " Day";
                            if(currentTimeLeft != 1)
                            {
                                str += "s";
                            }
                        }
                    }
                    else
                    {
                        str = currentTimeLeft + " Hr";
                    }
                }
                else
                {
                    str = currentTimeLeft + " Min";
                }
            }
            else
            {
                str = currentTimeLeft + " Sec";
            }
        }
        else
        {
            str = currentTimeLeft + " MS";
        }
        
        return str;
    }
}
