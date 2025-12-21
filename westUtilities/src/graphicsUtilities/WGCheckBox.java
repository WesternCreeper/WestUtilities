/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;

/**
 *
 * @author Westley
 */
public class WGCheckBox extends WGBox
{
    private Paint checkColor;
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
     * @param checkColor The Paint of the check on the checkBox
     * @param clickListener The click manager that allows for the checkbox to be set up. This is automatically set up so use the no param definition
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
    */
    public WGCheckBox(double xPercent, double yPercent, double widthPercent, double heightPercent, float borderSize, boolean checked, Paint backgroundColor, Paint borderColor, Paint checkColor, WGCheckBoxClickListener clickListener, Canvas parent) throws WGNullParentException
    {
        this(new Rectangle2D(xPercent, yPercent, widthPercent, heightPercent), borderSize, checked, backgroundColor, borderColor, checkColor, clickListener, parent);
    }
    /**
     * 
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param borderSize The size of the borders of the rectangular objects, vastly important to calculating the size of the text and internal components
     * @param checked The state of the checkBox, whether checked or unchecked
     * @param backgroundColor The color of the background of the font
     * @param borderColor The border color of the box
     * @param checkColor The Paint of the check on the checkBox
     * @param clickListener The click manager that allows for the checkbox to be set up. This is automatically set up so use the no param definition
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
    */
    public WGCheckBox(Rectangle2D bounds, float borderSize, boolean checked, Paint backgroundColor, Paint borderColor, Paint checkColor, WGCheckBoxClickListener clickListener, Canvas parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, backgroundColor, borderColor, parent);
        this.checked = checked;
        this.checkColor = checkColor;
        if(getParent() != null)
        {
            resizer = new CheckBoxResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
            setClickListener(clickListener);
            getClickListener().setParentComponent(parent);
            getClickListener().setParentObject(this);
            ((WGCheckBoxClickListener)getClickListener()).setOriginalBackgroundColor(backgroundColor);
            getParent().addEventHandler(MouseEvent.ANY, getClickListener());
            getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
            WestGraphics.add(this);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    /**
     * 
     * @param bounds The percentage of the parent component, in a rectangle form
     * @param checked The state of the checkBox, whether checked or unchecked
     * @param clickListener The click manager that allows for the checkbox to be set up. This is automatically set up so use the no param definition
     * @param parent The component that the button is on, and is used to determine how big this object is
     * @param theme The theme being used to define a bunch of standard values. This makes a bunch of similar objects look the same, and reduces the amount of effort required to create one of these objects
     * @throws WGNullParentException If the parent is non-existent, as in the parent is supplied as null, then this object cannot construct and will throw this exception
    */
    public WGCheckBox(Rectangle2D bounds, boolean checked, WGCheckBoxClickListener clickListener, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        this(bounds, theme.getBorderSize(), checked, theme.getBackgroundColor(), theme.getBorderColor(), theme.getCheckColor(), clickListener, parent);
        setCurrentTheme(theme);
    }
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().widthProperty().removeListener(resizer.getResizeListener());
        getParent().heightProperty().removeListener(resizer.getResizeListener());
        if(getToolTip() != null)
        {
            getToolTip().removeListeners();
        }

        getParent().removeEventHandler(MouseEvent.ANY, getClickListener());
        getParent().removeEventHandler(ScrollEvent.ANY, getClickListener());
        WestGraphics.remove(this);
    }
    
    //Setters:
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setCheckColor(Paint checkColor) {
        this.checkColor = checkColor;
    }

    public void setBackgroundColor(Paint backgroundColor) 
    {
        super.setBackgroundColor(backgroundColor);
        if(getClickListener() != null)
        {
            ((WGCheckBoxClickListener)getClickListener()).setOriginalBackgroundColor(backgroundColor);
        }
    }
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        checkColor = theme.getCheckColor();
        resizer.resizeComps();
    }

    //Getters:
    public boolean isChecked() {
        return checked;
    }

    public Paint getCheckColor() {
        return checkColor;
    }
    
    
    //classes or Listeners:
    private class CheckBoxResizeListener extends WGDrawingObjectResizeListener
    {
        private CheckBoxResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeCompsWithoutDelay()
        {
        	setResizing(true);
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double parentX = 0;
            double parentY = 0;
            WGPane pane = getParentOwningPane();
            if(pane != null && pane.isUseRelativePositions())
            {
            	parentWidth = pane.getWidth();
                parentHeight = pane.getHeight();
                parentX = pane.getX();
                parentY = pane.getY();
            }
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth + parentX);
            setY(getYPercent() * parentHeight + parentY);
            setWidth(getWidthPercent() * parentWidth);
            setHeight(getHeightPercent() * parentHeight);
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setHoverBackgroundColor(fixPaintBounds(getHoverBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.HOVER_BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                checkColor = fixPaintBounds(checkColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.CHECK_COLOR));
            }
        	setResizing(false);
        }
    }
}
