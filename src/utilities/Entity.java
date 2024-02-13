package utilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Used to represent an entity on an x-y plane. Follows common physics with x-y velocities. Requires an ID, can be used for any purpose
 * Make subclasses to obtain best results in regard to specialization
 */
public abstract class Entity 
{
    private double x = 0;
    private double y = 0;
    private double xVelocity = 0;
    private double yVelocity = 0;
    private int id = 0;
    protected Entity(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * Uses sin and cos from a radian angle to determine the x and y velocities
     * @param x
     * @param y
     * @param velocity
     * @param angleRad
     * @param id 
     */
    protected Entity(double x, double y, double velocity, double angleRad, int id)
    {
        this.x = x;
        this.y = y;
        xVelocity = velocity * Math.cos(angleRad);
        yVelocity = velocity * Math.sin(angleRad);
        this.id = id;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public void setX(double num)
    {
        x = num;
    }
    public void setY(double num)
    {
        y = num;
    }
    public void setYVelocity(double num)
    {
        yVelocity = num;
    }
    public double getYVelocity()
    {
        return yVelocity;
    }
    public void setXVelocity(double num)
    {
        xVelocity = num;
    }
    public double getXVelocity()
    {
        return xVelocity;
    }
    public void setID(int num)
    {
        id = num;
    }
    public int getID()
    {
        return id;
    }
}
