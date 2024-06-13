/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public abstract class WGBox extends WGDrawingObject
{
    private Color backgroundColor;
    private Color borderColor;
    protected WGDrawingObjectResizeListener resizer;
    
    
    protected WGBox(float borderSize, Color backgroundColor, Color borderColor, Component parent)
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }
    
    @Override
    public Rectangle2D.Double getBounds() 
    {
        Rectangle2D.Double bounds = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeComps();
    }
    public void setBounds(Rectangle2D.Double newBounds)
    {
        resizer.setBounds(newBounds);
    }
    
    //Regular Functions:

    //Setters:
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        getParent().repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        getParent().repaint();
    }

    
    //Getters:
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }
    
}
