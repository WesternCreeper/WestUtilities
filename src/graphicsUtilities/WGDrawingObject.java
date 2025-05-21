/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * This class defines the basic functions of a West Graphics object. Specifically it defines functions like: getBounds() and other important cross object functionalities
 * @author Westley
 */
public abstract class WGDrawingObject
{ 
    public final static int NO_GRADIENT_ORIENTATION_PREFERENCE = 0;
    public final static int VERTICAL_GRADIENT_ORIENTATION_PREFERENCE = 1;
    public final static int RADIAL_CENTER_GRADIENT_ORIENTATION_PREFERENCE = 2;
    public final static int RADIAL_CENTER_GRADIENT_ORIENTATION_WITH_MOUSE_MOVE_PREFERENCE = 3;
    public final static int HORIZONTAL_GRADIENT_ORIENTATION_PREFERENCE = 4;
    public final static int DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT_GRADIENT_ORIENTATION_PREFERENCE = 5;
    public final static int DIAGONAL_BOTTOM_LEFT_TO_TOP_RIGHT_GRADIENT_ORIENTATION_PREFERENCE = 6;
    
    
    private Component parent;
    private WGClickListener clickListener;
    protected WGDrawingObjectResizeListener resizer;
    private WGToolTip toolTip;
    private Cursor shownCursor;
    private double x = 0;
    private double y = 0;
    private double width;
    private double height;
    private float borderSize = 1;
    private boolean isShown = true;
    private WGTheme currentTheme;
    /**
     * This defines a basic WGDrawingObject, which is another term for a shared commonality among different drawable objects. Specifically this defines the X, Y, Width, Height, and Border Size of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     */
    protected WGDrawingObject(double x, double y, double width, double height, float borderSize)
    {
        this(x, y, width, height, borderSize, null);
    }
    /**
     * This defines a more advanced and capable WGDrawingObject. Specifically this defines the X, Y, Width, Height, Border Size, and the Parent Component of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param parent The component that the object is on, and is used to determine how big this object is
     */
    protected WGDrawingObject(double x, double y, double width, double height, float borderSize, Component parent)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderSize  = borderSize;
        this.parent = parent;
    }
    /**
     * This defines a more advanced and capable WGDrawingObject. Specifically this defines the X, Y, Width, Height, Border Size, and the Parent Component of a drawable object
     * @param x The X that starts the object
     * @param y The Y that starts the object
     * @param width The width of the object (In general this is the total width of the object because there may be other widths further defined inside the object)
     * @param height The height of the object (In general this is the total height of the object because there may be other heights further defined inside the object)
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param parent The component that the object is on, and is used to determine how big this object is
     * @param currentTheme The theme that is currently being used. This is for background operations, like gradients to work properly
     */
    protected WGDrawingObject(double x, double y, double width, double height, float borderSize, Component parent, WGTheme currentTheme)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderSize  = borderSize;
        this.parent = parent;
        this.currentTheme = currentTheme;
    }
    
    //Methods:
    public abstract Rectangle2D.Double getBounds();
    
    public Rectangle2D.Double getRelativeBounds()
    {
        return new Rectangle2D.Double(resizer.getXPercent(), resizer.getYPercent(), resizer.getWidthPercent(), resizer.getHeightPercent());
    }
    
    public abstract void setUpBounds();
    
    public abstract void setBounds(Rectangle2D.Double newBounds);
    
    public abstract void removeListeners();
    
    public void setTheme(WGTheme theme)
    {
        currentTheme = theme;
    }
    
    protected Paint fixPaintBounds(Paint paint)
    {
        return fixPaintBounds(paint, NO_GRADIENT_ORIENTATION_PREFERENCE, null);
    }
    protected Paint fixPaintBounds(Paint paint, Object gradientOrientationPreference)
    {
        return fixPaintBounds(paint, gradientOrientationPreference, null);
    }
    protected Paint fixPaintBounds(Paint paint, Object gradientOrientationPreference, MouseEvent e)
    {
        return fixPaintBounds(paint, gradientOrientationPreference, e, getX(), getY(), getWidth(), getHeight());
    }
    protected Paint fixPaintBounds(Paint paint, Object gradientOrientationPreference, MouseEvent e, double x, double y, double width, double height)
    {
        //First convert the gradientOrientation into a usable form:
        int gradOrient = 0;
        if(gradientOrientationPreference != null)
        {
            gradOrient = (int)gradientOrientationPreference;
        }
        
        Paint newPaint;
        if(paint instanceof GradientPaint)
        {
            GradientPaint oldPaint = (GradientPaint)paint;
            Point2D.Double[] points = getGradientPoints(gradOrient, e, x, y, width, height);
            newPaint = new GradientPaint(points[0], oldPaint.getColor1(), points[1], oldPaint.getColor2());
        }
        else if(paint instanceof RadialGradientPaint)
        {
            RadialGradientPaint oldPaint = (RadialGradientPaint)paint;
            Point2D.Double[] points = getGradientPoints(gradOrient, e, x, y, width, height);
            newPaint = new RadialGradientPaint(points[0], (float)points[1].getX(), oldPaint.getFractions(), oldPaint.getColors());
        }
        else if(paint instanceof TexturePaint)
        {
            TexturePaint oldPaint = (TexturePaint)paint;
            Point2D.Double[] points = getGradientPoints(gradOrient, e, x, y, width, height);
            BufferedImage image = oldPaint.getImage();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            newPaint = new TexturePaint(image, new Rectangle2D.Double(points[0].getX(), points[0].getY(), imageWidth, imageHeight));
        }
        else
        {
            newPaint = paint;
        }
        return newPaint;
    }
    /**
     * This function gets the points relative to this drawing object for the gradient to render correctly
     * @param gradientOrientationPreference The gradient orientation that determines how the points are created relative to the location of this object
     * @param width The width of the object
     * @param height The height of the object
     * @return the points that help define this gradient so that it follows the proper orientation.
     */
    private Point2D.Double[] getGradientPoints(int gradientOrientationPreference, MouseEvent e, double x, double y, double width, double height)
    {
        Point2D.Double[] points = new Point2D.Double[2];
        
        switch(gradientOrientationPreference)
        {
            case VERTICAL_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D.Double(x, y);
                points[1] = new Point2D.Double(x, y + height);
                break;
            case RADIAL_CENTER_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D.Double(x + (width/2), y + (height/2));
                
                double radius = (width/2);
                double heightRadius = (height/2);
                if(radius > heightRadius)
                {
                    radius = heightRadius;
                }
                if(radius <= 0)
                {
                    radius = 1;
                }
                
                points[1] = new Point2D.Double(radius, 0);
                break;
            case RADIAL_CENTER_GRADIENT_ORIENTATION_WITH_MOUSE_MOVE_PREFERENCE:
                if(e != null)
                {
                    points[0] = new Point2D.Double(e.getX(), e.getY());
                }
                else
                {
                    points[0] = new Point2D.Double(x + (width/2), y + (height/2));
                }
                
                radius = (width/2);
                heightRadius = (height/2);
                if(radius > heightRadius)
                {
                    radius = heightRadius;
                }
                if(radius <= 0)
                {
                    radius = 1;
                }
                
                points[1] = new Point2D.Double(radius, 0);
                break;
            case HORIZONTAL_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D.Double(x, y);
                points[1] = new Point2D.Double(x + width, y);
                break;
            case DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D.Double(x, y);
                points[1] = new Point2D.Double(x + width, y + height);
                break;
            case DIAGONAL_BOTTOM_LEFT_TO_TOP_RIGHT_GRADIENT_ORIENTATION_PREFERENCE:
                points[0] = new Point2D.Double(x, y + height);
                points[1] = new Point2D.Double(x + width, y);
                break;
            default:
                points[0] = new Point2D.Double(x, y);
                points[1] = new Point2D.Double(x, y);
                break;
        }
        
        return points;
    }
    
    
    //Setters:
    public void setShown(boolean isShown) 
    {
        this.isShown = isShown;
        //Cursor:
        WestGraphics.checkCursor(parent, this);
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    protected void setWidth(double width) {
        this.width = width;
    }

    protected void setHeight(double height) {
        this.height = height;
    }

    public void setBorderSize(float borderSize) {
        this.borderSize = borderSize;
    }

    public void setClickListener(WGClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setShownCursor(Cursor shownCursor) {
        this.shownCursor = shownCursor;
    }

    /**
     * A very important not about this:
     * Do NOT use this function if this object is a tool tip!
     * This is ONLY used when the tool tip is created but later needs to be changed, when there is no longer a reference to the original tool tip. This is the reference to that original tool tip
     * This is also useful in the drawing routine
     * @param toolTip The Tool Tip that is added to this object, important when the tool tip no longer has a reference but needs to be changed
    */ 
    public void setToolTip(WGToolTip toolTip) {
        this.toolTip = toolTip;
    }

    protected void setCurrentTheme(WGTheme currentTheme) {
        this.currentTheme = currentTheme;
        setTheme(currentTheme);
    }
    
    //Getters:
    public boolean isShown() {
        return isShown;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public float getBorderSize() {
        return borderSize;
    }

    public Component getParent() {
        return parent;
    }
    
    public WGClickListener getClickListener() {
        return clickListener;
    }

    public WGDrawingObjectResizeListener getResizer() {
        return resizer;
    }

    public Cursor getShownCursor() {
        return shownCursor;
    }

    public WGToolTip getToolTip() {
        return toolTip;
    }

    public WGTheme getCurrentTheme() {
        return currentTheme;
    }
}
