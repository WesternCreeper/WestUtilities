/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author Westley
 */
public abstract class WGDrawingObjectResizeListener
{
    private double xPercent = 0;
    private double yPercent = 0;
    private double widthPercent = 0;
    private double heightPercent = 0;
    private ChangeListener<Number> resizeListener;
    protected WGDrawingObjectResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
    {
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
    }
    public void setBounds(Rectangle2D newBounds)
    {
        xPercent = newBounds.getMinX();
        yPercent = newBounds.getMinY();
        widthPercent = newBounds.getWidth();
        heightPercent = newBounds.getHeight();
        resizeComps();
    }
    
    public final void resizeComps()
    {
    	Platform.runLater(() -> {
    		resizeCompsWithoutDelay();
    	});
    }
    
    public abstract void resizeCompsWithoutDelay();

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

	public ChangeListener<Number> getResizeListener() {
		return resizeListener;
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
    
	public void setResizeListener(ChangeListener<Number> resizeListener) {
		this.resizeListener = resizeListener;
	}
    
}
