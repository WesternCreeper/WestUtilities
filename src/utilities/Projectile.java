/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;
/**
 *
 * @author Westley
 */
public class Projectile 
{
    private int curX = 0;
    private int curY = 0;
    private int numTimes = 1;
    private int xChange = 0;
    private int yChange = 0;
    public Projectile(int currentX, int currentY, int destanationX, int destanationY, int numberOfTimes)
    {
        curX = currentX;
        curY = currentY;
        numTimes = numberOfTimes;
        xChange = (destanationX - curX) / numTimes;
        yChange = (destanationY - curY) / numTimes;
    }
    public float getX()
    {
        return (float)curX;
    }
    public float getY()
    {
        return (float)curY;
    }
    public int getNumTimes()
    {
        return numTimes;
    }
    public void setNumTimes(int num)
    {
        numTimes = num;
    }
    public int getXChange()
    {
        return xChange;
    }
    public int getYChange()
    {
        return yChange;
    }
    public void setX(int num)
    {
        curX = num;
    }
    public void setY(int num)
    {
        curY = num;
    }
}
