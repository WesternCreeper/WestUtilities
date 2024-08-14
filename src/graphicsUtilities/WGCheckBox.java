/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Westley
 */
public class WGCheckBox extends WGBox
{
    private Color checkColor;
    private boolean checked;
    /**
     * 
     * @param xPercent The percentage of the parent component that is where the x starts. As in 0.3 would mean that the x starts at 30% of the parent's width. And if it has a width of 0.4 then the component would always be in the middle of the screen
     * @param yPercent The percentage of the parent component that is where the y starts. Same idea as above but with the y and height
     * @param widthPercent The percentage of the parent component that the width of this object. As in 0.4 would mean this object stretches 40% of the screen
     * @param heightPercent The percentage of the parent component that the height of this object. Same idea as the width but with the height component.
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param checked The state of the checkBox, whether checked or unchecked
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param checkColor The Color of the check on the checkBox
     * @param clickListener The click manager that allows for the checkbox to be set up. This is automatically set up so use the no param definition
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
    */
    public WGCheckBox(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, boolean checked, Color backgroundColor, Color borderColor, Color checkColor, WGCheckBoxClickListener clickListener, Component parent) throws WGNullParentException
    {
        this(new Rectangle2D.Double(xPercent, yPercent, widthPercent, heightPercent), borderSize, checked, backgroundColor, borderColor, checkColor, clickListener, parent);
    }
    /**
     * 
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param checked The state of the checkBox, whether checked or unchecked
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param checkColor The Color of the check on the checkBox
     * @param clickListener The click manager that allows for the checkbox to be set up. This is automatically set up so use the no param definition
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
    */
    public WGCheckBox(Rectangle2D.Double bounds, float borderSize, boolean checked, Color backgroundColor, Color borderColor, Color checkColor, WGCheckBoxClickListener clickListener, Component parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, borderColor, parent);
        this.checked = checked;
        this.checkColor = checkColor;
        if(getParent() != null)
        {
            resizer = new CheckBoxResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            setClickListener(clickListener);
            getClickListener().setParentComponent(parent);
            getClickListener().setParentObject(this);
            ((WGCheckBoxClickListener)getClickListener()).setOriginalBackgroundColor(backgroundColor);
            getParent().addMouseListener(getClickListener());
            getParent().addMouseMotionListener((WGCheckBoxClickListener)getClickListener());
            getParent().addMouseWheelListener((WGCheckBoxClickListener)getClickListener());
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().removeComponentListener(resizer);
        
        WGCheckBoxClickListener buttoner = (WGCheckBoxClickListener)(getClickListener());
        getParent().removeMouseListener(buttoner);
        getParent().removeMouseMotionListener(buttoner);
        getParent().removeMouseWheelListener(buttoner);
    }
    
    //Setters:
    public void setChecked(boolean checked) {
        this.checked = checked;
        getParent().repaint();
    }

    public void setCheckColor(Color checkColor) {
        this.checkColor = checkColor;
    }

    public void setBackgroundColor(Color backgroundColor) 
    {
        super.setBackgroundColorNotClickListener(backgroundColor);
        if(getClickListener() != null)
        {
            ((WGCheckBoxClickListener)getClickListener()).setOriginalBackgroundColor(backgroundColor);
        }
    }

    //Getters:
    public boolean isChecked() {
        return checked;
    }

    public Color getCheckColor() {
        return checkColor;
    }
    
    
    //classes or Listeners:
    private class CheckBoxResizeListener extends WGDrawingObjectResizeListener
    {
        private CheckBoxResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps()
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
