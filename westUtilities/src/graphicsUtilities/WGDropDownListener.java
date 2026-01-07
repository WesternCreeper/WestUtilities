/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Westley
 */
public class WGDropDownListener extends WGButtonListener
{
	private boolean didDragEvent = false;
    /**
     * The necessary components needed to make this object versatile for anything needed to be clicked on. This could be a button, although there is a specific class for those, or any WGDrawingObject, a loading bar or an announcement card. Whatever the need is, this class will be  
     * @param parentObject The WGDrawingObject that allows for certain functions to work
     * @param parentComponent The parent of the WGDrawingObject. This definition is needed if the parentObject returns null
     */
    public WGDropDownListener(WGBox parentObject, Canvas parentComponent)
    {
        super(parentObject, parentComponent);
    }
    
    /**
     * When you make a sub-class of this object make sure to use the "isWithinBounds" function with the MouseEvent e being the parameter so that this function can verify that it is within the bounds of the WGDrawingObject and not any part of the parent object.
     * Also to be able to provide function to this class make sure to sub-class this object, otherwise it don't work. This is just like it's superclass, but has additional WG specific functions
     */
    public void mouseClicked(MouseEvent e) 
    {
        super.mouseClicked(e);
        
        //To prevent a nasty scrollbar bug:
        if(didDragEvent)
        {
        	didDragEvent = false;
        	return;
        }
        
        //Check to see if the object needs to go back into the original form, without all of the choices
        WGDropDown parent = (WGDropDown)getParentObject();
        if(!e.isConsumed() && parent.isDroppedDown() && !isWithinBounds(e))
        {
            parent.setDroppedDown(false);
            //Force the pane owner to update!
            getParentObject().getParentOwningPane().getResizer().resizeComps();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e)
    {
    	didDragEvent = true;
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
            getParentObject().getParentOwningPane().getResizer().resizeComps();
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
            getParentObject().getParentOwningPane().getResizer().resizeComps();
        }
    }
    public void hoverEvent(MouseEvent e)
    {
        if(isWithinBounds(e))
        {
            //The background
            WGDropDown parent = (WGDropDown)getParentObject();
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
    }
}
