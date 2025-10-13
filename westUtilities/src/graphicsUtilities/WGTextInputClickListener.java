/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import utilities.FXFontMetrics;

/**
 *
 * @author Westley
 */
public class WGTextInputClickListener extends WGClickListener
{
    private Paint originalBackgroundColor;
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGTextInputClickListener() {}
    public WGTextInputClickListener (WGDrawingObject parentObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
    }
    
    @Override
    public void handle(Event e)
    {
		if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED))
		{
			mouseClicked((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED))
		{
			mousePressed((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
		{
			mouseReleased((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_ENTERED))
		{
			mouseEntered((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_EXITED))
		{
			mouseExited((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
		{
			mouseDragged((MouseEvent)e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_MOVED))
		{
			mouseMoved((MouseEvent)e);
		}
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        WGTextInput parent = (WGTextInput)getParentObject();
        if(isWithinBounds(e) && isParentShown())
        {
            if(!e.isConsumed())
            {
                if(!parent.isFocused())
                {
                    parent.setFocused(true);
                    e.consume();
                    
                    //Cursor:
                    WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
                }
                else
                {
                    if(!parent.isShiftHeld())
                    {
                        if(parent.isHighlightShown())
                        {
                            parent.setHighlightShown(false);
                        }
                        parent.setCursorPosition(getCursorPosition(e));
                    }
                    else
                    {
                        parent.setHighlightStart(parent.getCursorPosition());
                        parent.setHighlightEnd(getCursorPosition(e));
                        parent.setHighlightShown(true);
                    }
                }
            }
        }
        else
        {
            parent.setFocused(false);
        }
    }

    public void mouseDragged(MouseEvent e) 
    {
        if(e.isConsumed())
        {
            return;
        }
        WGTextInput parent = (WGTextInput)getParentObject();
        if(isWithinBounds(e) && parent.isFocused())
        {
            parent.setHighlightEnd(getCursorPosition(e));
            parent.setHighlightShown(true);
        }
    }

    public void mouseMoved(MouseEvent e)
    {
        //Cursor:
        WestGraphics.checkCursor(e, getParentComponent(), getParentObject());
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        if(e.isConsumed())
        {
            return;
        }
        WGTextInput parent = (WGTextInput)getParentObject();
        if(isWithinBounds(e) && parent.isFocused())
        {
            if(parent.isHighlightShown())
            {
                parent.setHighlightShown(false);
            }
            int cursorPos = getCursorPosition(e);
            parent.setHighlightStart(cursorPos);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e){}
    
    @Override
    protected Cursor getCursorType()
    {
        WGTextInput parent = (WGTextInput)getParentObject();
        return parent.isFocused() ? WestGraphics.getTextCursor() : WestGraphics.getHoverCursor();
    }
    
    //Getters:
    public Paint getOriginalBackgroundColor() {
        return originalBackgroundColor;
    }
    
    //Setters:
    public void setOriginalBackgroundColor(Paint originalBackgroundColor) {
        this.originalBackgroundColor = originalBackgroundColor;
    }
    
    public int getCursorPosition(MouseEvent e)
    {
        WGTextInput parent = (WGTextInput)getParentObject();
        //Figure out where the cursor should go in relation to the text:
        FXFontMetrics textFM = new FXFontMetrics(parent.getTextFont());
        String text = parent.getText();
        double totalStringWidth = textFM.stringWidth(text);
        double textStartX = parent.getX() + (parent.getWidth() - totalStringWidth) / 2.0;
                
        //If empty then obviously return 0:
        if(text.length() == 0)
        {
            return 0;
        }
        
        
        //Now find out which side of the text it is on:
        double clickX = e.getX();
        int i = 0;
        //This looks at each part of the string and determines where the cursor should go by searching binarily:
        int iMax = text.length();
        int iMin = 0;
        while(true) 
        {
            i = (int)((iMax + iMin) / 2.0 + .5);
            double substringWidth = textFM.stringWidth(text.substring(0, i)) + textStartX;
            if(substringWidth > clickX)
            {
                iMax = i;
            }
            else
            {
                iMin = i;
            }
            if(iMax == iMin || iMax == iMin+1)
            {
                //Which side?
                if(iMin > 0)
                {
                    double middleWidth = textFM.stringWidth(text.substring(0, iMax-1)) + (textFM.stringWidth(text.substring(iMax-1, iMax)) / 2.0) + textStartX;
                    if(middleWidth >= clickX) //IF it STILL is larger then set to i-1, else i
                    {
                        return iMax-1;
                    }
                    else
                    {
                        return iMax;
                    }
                }
                else
                {
                    double middleWidth = (textFM.stringWidth(text.substring(0, 1)) / 2.0) + textStartX;
                    if(middleWidth >= clickX) //IF it STILL is larger then set to i-1, else i
                    {
                        return 0;
                    }
                    else
                    {
                        return 1;
                    }
                }
            }
        }
    }
}
