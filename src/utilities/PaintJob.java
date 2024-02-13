/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;
import java.awt.Shape;
import java.awt.geom.RectangularShape;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
/**
 * Just an object that holds three values, color, shape, and whether or not to fill
 * 
 */
public class PaintJob 
{
    private Shape shape = null;
    private Color color = null;
    public PaintJob(Shape shape, Color color)
    {
        this.shape = shape;
        this.color = color;
    }
    public Color getColor()
    {
        return color;
    }
    public RectangularShape getRectangularShape()
    {
        if(shape instanceof RectangularShape)
        {
            return (RectangularShape)shape;
        }
        return new Rectangle2D.Double();
    }
    public Shape getShape()
    {
        return shape;
    }
    public String toString()
    {
        return color.toString() + " " + shape.toString();
    }
}
