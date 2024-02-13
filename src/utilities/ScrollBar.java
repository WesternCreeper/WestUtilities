/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Westley
 */
public class ScrollBar 
{
    private double scrollLocation = 0.0;
    public ScrollBar(){}
    public ScrollBar(double scrollLocation)
    {
        this.scrollLocation = scrollLocation;
    }
    public double getScrollLocation()
    {
        return scrollLocation;
    }
    public void setScrollLocation(double num)
    {
        scrollLocation = num;
    }
}
