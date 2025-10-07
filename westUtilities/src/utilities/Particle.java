/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author Westley
 */
public class Particle extends Entity
{
    private double airResistance = 0.0;
    public Particle(double x, double y, int id)
    {
        super(x, y, 0, 0, id);
    }
    public Particle(double x, double y, double velocity, double angleRad, int id, double airResistance)
    {
        super(x, y, velocity, angleRad, id);
        this.airResistance = airResistance;
    }
    public double getPercentGravityEffect()
    {
        return (1 - airResistance);
    }
    public void setAirResistance(double percent)
    {
        airResistance = percent;
    }
}
