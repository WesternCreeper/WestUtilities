/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

/**
 *
 * @author Westley
 */
public class WGRescaleOptions 
{
    private double xRescale;
    private double yRescale;
    private double widthRescale;
    private double heightRescale;
    public WGRescaleOptions(double rescaleAmount)
    {
        xRescale = rescaleAmount;
        yRescale = rescaleAmount;
        widthRescale = rescaleAmount;
        heightRescale = rescaleAmount;
    }
    public WGRescaleOptions(double xyRescaleAmount, double widthHeightRescaleAmount)
    {
        xRescale = xyRescaleAmount;
        yRescale = xyRescaleAmount;
        widthRescale = widthHeightRescaleAmount;
        heightRescale = widthHeightRescaleAmount;
    }
    public WGRescaleOptions(double xRescale, double yRescale, double widthRescale, double heightRescale)
    {
        this.xRescale = xRescale;
        this.yRescale = yRescale;
        this.widthRescale = widthRescale;
        this.heightRescale = heightRescale;
    }
    
    
    //Getters:
    public double getXRescale() {
        return xRescale;
    }

    public double getYRescale() {
        return yRescale;
    }

    public double getWidthRescale() {
        return widthRescale;
    }

    public double getHeightRescale() {
        return heightRescale;
    }
    
    
    //Setters:
    public void setXRescale(double xRescale) {
        this.xRescale = xRescale;
    }

    public void setYRescale(double yRescale) {
        this.yRescale = yRescale;
    }

    public void setWidthRescale(double widthRescale) {
        this.widthRescale = widthRescale;
    }

    public void setHeightRescale(double heightRescale) {
        this.heightRescale = heightRescale;
    }
    
}
