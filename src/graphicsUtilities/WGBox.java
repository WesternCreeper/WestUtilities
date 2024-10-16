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
    public void setTheme(WGTheme theme)
    {
        setBorderSize(theme.getBorderSize());
        setBackgroundColor(theme.getBackgroundColor());
        setBorderColor(theme.getBorderColor());
    }
    @Override
    /**
     * This sets whether the component is shown or not and sets the cursor based on the second variable
     * @param isShown Shows or hides the component
     * @param setCursor Sets or doesn't the cursor
     */
    public void setShown(boolean isShown, boolean setCursor) 
    {
        super.setShown(isShown, setCursor);
        if(setCursor)
        {
            //Now make sure that these do the correct events as needed
            WGClickListener clickListener = getClickListener();
            //Make sure it has been set up to do this:
            if(clickListener != null && WestGraphics.lastMouseEvent != null)
            {
                if(clickListener instanceof WGButtonListener) //Standard Listeners:
                {
                    WGButtonListener actualListener = (WGButtonListener)clickListener;
                    actualListener.hoverEvent(WestGraphics.lastMouseEvent);
                }
                else if(clickListener instanceof WGCheckBoxClickListener) //Checkbox listeners:
                {
                    WGCheckBoxClickListener actualListener = (WGCheckBoxClickListener)clickListener;
                    actualListener.hoverEvent(WestGraphics.lastMouseEvent);
                }
                else if(clickListener instanceof WGTextInputClickListener) //Text Listeners:
                {
                    WGTextInputClickListener actualListener = (WGTextInputClickListener)clickListener;
                    actualListener.mouseMoved(WestGraphics.lastMouseEvent);
                }
                else if(clickListener instanceof WGKeyInputClickListener) //Key Listeners:
                {
                    WGKeyInputClickListener actualListener = (WGKeyInputClickListener)clickListener;
                    actualListener.mouseMoved(WestGraphics.lastMouseEvent);
                }
            }
        }
    }
    
    //Regular Functions:

    //Setters:
    public void setBackgroundColor(Color backgroundColor) 
    {
        this.backgroundColor = backgroundColor;
        if(getClickListener() != null)
        {
            ((WGButtonListener)getClickListener()).setOriginalBackgroundColor(backgroundColor);
        }
        getParent().repaint();
    }

    public void setBackgroundColorNotClickListener(Color backgroundColor) 
    {
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
