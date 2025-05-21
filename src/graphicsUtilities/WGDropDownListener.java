/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 *
 * @author Westley
 */
public class WGDropDownListener extends WGButtonListener
{
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGDropDownListener(WGBox parentObject, Component parentComponent)
    {
        super(parentObject, parentComponent);
        
        //Make sure to set the cursor to the correct one when shown:
        parentObject.setShownCursor(WestGraphics.getHoverCursor());
    }
    
    /**
     * When you make a sub-class of this object make sure to use the "isWithinBounds" function with the MouseEvent e being the parameter so that this function can verify that it is within the bounds of the WGDrawingObject and not any part of the parent object.
     * Also to be able to provide function to this class make sure to sub-class this object, otherwise it don't work. This is just like it's superclass, but has additional WG specific functions
     */
    public void mouseClicked(MouseEvent e) 
    {
        super.mouseClicked(e);
        
        //Check to see if the object needs to go back into the original form, without all of the choices
        WGDropDown parent = (WGDropDown)getParentObject();
        if(parent.isDroppedDown() && !isWithinBounds(e))
        {
            parent.setDroppedDown(false);
            //Force the pane owner to update!
            getParentOwningPane().changeScrollBounds(false);
        }
    }
    
    public void clickEvent(MouseEvent e)
    {
        WGDropDown parent = (WGDropDown)getParentObject();
        
        if(!parent.isDroppedDown())
        {
            //Tell the drop down that it is focused:
            parent.setDroppedDown(true);
            parent.setHoveredIndex(0);
            //Force the pane owner to update!
            getParentOwningPane().changeScrollBounds(true);
        }
        else
        {
            //We have been clicked when there are choices availible, we need to figure out what to change to:
            double mouseY = e.getY() - parent.getY();
            
            //Now all we need to do is remove each button from consideration one-by-one:
            int choice = 0;
            while(mouseY > parent.getButtonHeight())
            {
                mouseY -= parent.getButtonHeight();
                choice++;
            }
            
            parent.setDroppedDown(false);
            parent.setSelectedChoice(choice);
            //Force the pane owner to update!
            getParentOwningPane().changeScrollBounds(false);
            
            //Reset the cursor and the background:
            if(choice > 0)
            {
                parent.setHovered(false);
                if(cursorSet && !e.isConsumed())
                {
                    getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                    cursorSet = false;
                    e.consume();
                }
            }
        }
    }
    public void hoverEvent(MouseEvent e)
    {
        if(isWithinBounds(e))
        {
            //The background
            WGDropDown parent = (WGDropDown)getParentObject();
            
            parent.setHovered(true);

            //The cursor
            if(isParentShown())
            {
                getParentComponent().setCursor(WestGraphics.getHoverCursor());
                cursorSet = true;
                e.consume();
            }
            else if(cursorSet && !e.isConsumed())
            {
                getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                cursorSet = false;
                e.consume();
            }
            
            //Now if this is dropped down, make sure to make the corrected box be hovered:
            if(parent.isDroppedDown())
            {
                double mouseY = e.getY() - parent.getY();

                //Now all we need to do is remove each button from consideration one-by-one:
                int index = 0;
                while(mouseY > parent.getButtonHeight())
                {
                    mouseY -= parent.getButtonHeight();
                    index++;
                }

                parent.setHoveredIndex(index);
            }
        }
        else
        {
            //The background
            WGBox button = (WGBox)getParentObject();
            button.setHovered(false);
            
            //The cursor
            if(cursorSet && !e.isConsumed())
            {
                getParentComponent().setCursor(WestGraphics.getDefaultCursor());
                cursorSet = false;
                e.consume();
            }
        }
        WestGraphics.doRepaintJob(getParentObject().getParent());
    }
}
