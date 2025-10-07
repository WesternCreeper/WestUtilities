/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public abstract class WGDrawingObjectResizeListener implements ComponentListener
{
    private double xPercent = 0;
    private double yPercent = 0;
    private double widthPercent = 0;
    private double heightPercent = 0;
    protected WGDrawingObjectResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
    {
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
    }
    public void componentHidden(ComponentEvent e){}
    public void componentMoved(ComponentEvent e){}
    public void componentShown(ComponentEvent e)
    {
        resizeComps();
    }
    public void componentResized(ComponentEvent e)
    {
        resizeComps();
    }
    
    public void setBounds(Rectangle2D.Double newBounds)
    {
        xPercent = newBounds.getX();
        yPercent = newBounds.getY();
        widthPercent = newBounds.getWidth();
        heightPercent = newBounds.getHeight();
        resizeComps();
    }
    
    public abstract void resizeComps();

    //Getters:
    public double getXPercent() {
        return xPercent;
    }

    public double getYPercent() {
        return yPercent;
    }

    public double getWidthPercent() {
        return widthPercent;
    }

    public double getHeightPercent() {
        return heightPercent;
    }
    
    //Setters:
    public void setXPercent(double xPercent) {
        this.xPercent = xPercent;
        resizeComps();
    }

    public void setYPercent(double yPercent) {
        this.yPercent = yPercent;
        resizeComps();
    }

    public void setWidthPercent(double widthPercent) {
        this.widthPercent = widthPercent;
        resizeComps();
    }

    public void setHeightPercent(double heightPercent) {
        this.heightPercent = heightPercent;
        resizeComps();
    }
    
}
