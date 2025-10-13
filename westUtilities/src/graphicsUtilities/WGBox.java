/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Westley
 */
public abstract class WGBox extends WGDrawingObject
{
    private Paint backgroundColor;
    private Paint borderColor;
    private boolean isHovered = false;
    private Paint hoverBackgroundColor;
    
    protected WGBox(double borderSize, Paint backgroundColor, Paint hoverBackgroundColor, Paint borderColor, Canvas parent)
    {
        super(0, 0, 0, 0, borderSize, parent);
        this.backgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.borderColor = borderColor;
    }
    
    protected WGBox(double borderSize, Paint backgroundColor, Paint hoverBackgroundColor, Paint borderColor, Canvas parent, WGTheme theme)
    {
        super(0, 0, 0, 0, borderSize, parent, theme);
        this.backgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.borderColor = borderColor;
    }
    
    @Override
    public Rectangle2D getBounds() 
    {
        Rectangle2D bounds = new Rectangle2D(getX(), getY(), getWidth(), getHeight());
        return bounds;
    }
    public void setUpBounds()
    {
        resizer.resizeComps();
    }
    public void setBounds(Rectangle2D newBounds)
    {
        resizer.setBounds(newBounds);
    }
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        setBorderSize(theme.getBorderSize());
        setBackgroundColor(theme.getBackgroundColor());
        hoverBackgroundColor = theme.getHoverBackgroundColor();
        setBorderColor(theme.getBorderColor());
    }
    
    //Regular Functions:

    //Setters:
    public void setBackgroundColor(Paint backgroundColor) 
    {
        this.backgroundColor = backgroundColor;
        if(backgroundColor instanceof Color)
        {
            hoverBackgroundColor = WGColorHelper.getDarkerOrLighter((Color)backgroundColor);
        }
    }

    public void setBorderColor(Paint borderColor) {
        this.borderColor = borderColor;
    }

    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    public void setHoverBackgroundColor(Paint hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    
    //Getters:
    public Paint getBackgroundColor() {
        return backgroundColor;
    }

    public Paint getBorderColor() {
        return borderColor;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public Paint getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }
    
}
