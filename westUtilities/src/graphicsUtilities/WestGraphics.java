/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import dataStructures.HashTable;
import dataStructures.Queue;
import dataStructures.WGObjectBoundList;
import dataStructures.WGObjectBoundNode;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.transform.Affine;
import utilities.FXFontMetrics;

import java.util.ArrayList;

/**
 *
 * @author Westley
 * 
 * This is the class that defines functions to draw various advanced components, such as rendering a loading bar or a scroll bar to float/double precision. This CANNOT do 3D though
 */
public class WestGraphics
{
    //Static Package wide important stuff:
	private static Canvas currentActiveParent;
    private static WGBox lastHoverOject;
    private static HashTable<MouseEvent> lastMouseEvents = new HashTable<MouseEvent>(5, HashTable.HASHING_OPTION_LINEAR);
    private static WGObjectBoundList allClickables = new WGObjectBoundList();
    private static Cursor defaultCursor = Cursor.DEFAULT;
    private static Cursor hoverCursor = Cursor.HAND;
    private static Cursor textCursor = Cursor.TEXT;
    private static boolean allowComponentsToRepaint = true;
    
    /**
     * This function sets up the cursors to the default values
     */
    public static void setUpCursors()
    {
    	defaultCursor = Cursor.DEFAULT;
    	hoverCursor = Cursor.HAND;
    	textCursor = Cursor.TEXT;
    }
    
    
    //Static methods:
    /**
     * Sets up the cursor to the given cursors
     * @param defaultCursor The default cursor
     * @param hoverCursor The cursor shown when hovering over an object that can be clicked
     * @param textCursor The cursor shown when hovering over a text input of some kind
     */
    public static void setUpCursors(Cursor defaultCursor, Cursor hoverCursor, Cursor textCursor)
    {
        WestGraphics.defaultCursor = defaultCursor;
        WestGraphics.hoverCursor = hoverCursor;
        WestGraphics.textCursor = textCursor;
    }
    
    public static void setUpMouseListener(Canvas parent)
    {
    	lastMouseEvents.insert(parent.toString(), null);
    	parent.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
    		lastMouseEvents.set(parent.toString(), e);
    	});
    }
    
    /**
     * This function adds a WGObject to the internal cursor array, and is used to verify that the cursor maintains the proper look at all times
     * @param obj The Object to be added
     */
    static void add(WGDrawingObject obj)
    {
        allClickables.addNode(new WGObjectBoundNode(obj));
    }
    
    /**
     * This function updates an already existing WGObject in the internal cursor array, and is used to verify that the cursor maintains the proper look at all times
     * @param obj The Object to be updated (MUST ALREADY EXIST)
     */
    static void update(WGDrawingObject obj)
    {
        allClickables.updateNode(new WGObjectBoundNode(obj));
    }
    
    /**
     * This function removes a WGObject to the internal cursor array, and is used to verify that the cursor maintains the proper look at all times
     * @param obj The Object to be removed
     */
    static void remove(WGDrawingObject obj)
    {
        allClickables.removeNode(new WGObjectBoundNode(obj));
    }
    
    static void setProperHoverDuringScroll(Canvas parent)
    {
    	MouseEvent lastMouseEvent = lastMouseEvents.find(parent.toString());
        if(lastMouseEvent == null)
        {
            return;
        }
        Point2D point = new Point2D(lastMouseEvent.getX(), lastMouseEvent.getY());
        WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(point, parent);
        if(lastHoverOject != null && absoluteClickCursor != null && absoluteClickCursor instanceof WGBox)
        {
            lastHoverOject.setHovered(false);
            WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
            absoluteBoxObject.setHovered(true);
            lastHoverOject = absoluteBoxObject;
        }
        else if(absoluteClickCursor != null && absoluteClickCursor instanceof WGBox)
        {
            WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
            lastHoverOject = absoluteBoxObject;
        }
        else if(absoluteClickCursor == null && lastHoverOject != null)
        {
            lastHoverOject.setHovered(false); //We went off the object, so make it so
        }
    }
    
    /**
     * This function checks the given point to verify that the cursor is in the right form. (This form uses the last mouse event)
     * @param parent The parent for the Utility to change the cursor of
     * @param sourceObject The source object that defines whether or not the correct listener does the operation
     */
    static void checkCursor(Canvas parent, WGDrawingObject sourceObject)
    {
    	MouseEvent lastMouseEvent = lastMouseEvents.find(parent.toString());
        if(lastMouseEvent == null || currentActiveParent != parent) //IF the active parent is not the current one, then the system will ignore the requests
        {
            //We have also already set the hover, so make sure to remember that:
            if(sourceObject instanceof WGBox)
            {
                WGBox boxObject = (WGBox)sourceObject;
                boxObject.setHovered(false);
            }
            return; //We have already set the cursor, no touch!
        }
        Point2D realPoint = new Point2D(lastMouseEvent.getSceneX(), lastMouseEvent.getSceneY());
        WGDrawingObject clickCursor = allClickables.contains(realPoint, parent);
        if(clickCursor != null)
        {
            if(sourceObject == clickCursor) //Makes sure that the correct clickListener tells us what to do
            {
                parent.setCursor(sourceObject.getClickListener().getCursorType());
                WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
                if(absoluteClickCursor instanceof WGBox)
                {
                    WGBox boxObject = (WGBox)clickCursor;
                    boxObject.setHovered(false);
                    WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
                    absoluteBoxObject.setHovered(true);
                    lastHoverOject = absoluteBoxObject;
                }
            }
            else
            {
                if(sourceObject instanceof WGBox)
                {
                    WGBox boxObject = (WGBox)sourceObject;
                    boxObject.setHovered(false);
                }
            }
        }
        else
        {
            if(sourceObject instanceof WGBox)
            {
                WGBox boxObject = (WGBox)sourceObject;
                boxObject.setHovered(false);
            }
            parent.setCursor(defaultCursor);
        }
    }
    
    /**
     * This function checks the given point to verify that the cursor is in the right form
     * @param e The mouseEvent that was used for the click process
     * @param parent The parent for the Utility to change the cursor of
     * @param sourceObject The source object that defines whether or not the correct listener does the operation
     */
    static void checkCursor(MouseEvent e, Canvas parent, WGDrawingObject sourceObject)
    {
    	MouseEvent lastMouseEvent = lastMouseEvents.find(parent.toString());
        if(lastMouseEvent == e || currentActiveParent != parent) //IF the active parent is not the current one, then the system will ignore the requests
        {
            //We have also already set the hover, so make sure to remember that:
            if(sourceObject instanceof WGBox)
            {
                //Verify that we can do this:
                Point2D realPoint = new Point2D(e.getSceneX(), e.getSceneY());
                WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
                if(absoluteClickCursor != null)
                {
                    WGBox boxObject = (WGBox)absoluteClickCursor;
                    boxObject.setHovered(true);
                    lastHoverOject = boxObject;
                }
                if(absoluteClickCursor != sourceObject)
                {
                    WGBox boxObject = (WGBox)sourceObject;
                    boxObject.setHovered(false);
                }
            }
            return; //We have already set the cursor, no touch!
        }
        Point2D realPoint = new Point2D(e.getSceneX(), e.getSceneY());
        WGDrawingObject clickCursor = allClickables.contains(realPoint, parent);
        if(clickCursor != null)
        {
            boolean onPane = clickCursor.getParentOwningPane() != null;
            if(sourceObject == clickCursor) //Makes sure that the correct clickListener tells us what to do
            {
            	lastMouseEvents.set(parent.toString(), e);
                if(!onPane)
                {
                    parent.setCursor(sourceObject.getClickListener().getCursorType());
                }
                WGDrawingObject absoluteClickCursor = allClickables.containsUseAbsolute(realPoint, parent);
                if(absoluteClickCursor instanceof WGBox)
                {
                    if(clickCursor != absoluteClickCursor)
                    {
                        WGBox boxObject = (WGBox)clickCursor;
                        boxObject.setHovered(false);
                        WGBox absoluteBoxObject = (WGBox)absoluteClickCursor;
                        absoluteBoxObject.setHovered(true);
                        lastHoverOject = absoluteBoxObject;
                    }
                    else
                    {
                        WGBox boxObject = (WGBox)clickCursor;
                        boxObject.setHovered(true);
                        lastHoverOject = boxObject;
                    }
                }
                if(onPane && absoluteClickCursor != null)
                {
                    //Then make sure to set the click cursor:
                    parent.setCursor(sourceObject.getClickListener().getCursorType());
                }
                else if(onPane)
                {
                    parent.setCursor(defaultCursor);
                }
            }
            else
            {
                if(sourceObject instanceof WGBox)
                {
                    WGBox boxObject = (WGBox)sourceObject;
                    boxObject.setHovered(false);
                }
            }
        }
        else
        {
            if(sourceObject instanceof WGBox)
            {
                WGBox boxObject = (WGBox)sourceObject;
                boxObject.setHovered(false);
            }
            parent.setCursor(defaultCursor);
        }
    }
    
    
    //Static setters:
    public static void setAllowComponentsToRepaint(boolean allowComponentsToRepaint) {
        WestGraphics.allowComponentsToRepaint = allowComponentsToRepaint;
    }

    public static void setCurrentActiveParent(Canvas currentActiveParent) {
        WestGraphics.currentActiveParent = currentActiveParent;
    }
    
    
    //Static getters
    public static boolean isAllowComponentsToRepaint() {
        return allowComponentsToRepaint;
    }
    
    public static Cursor getDefaultCursor() {
        return defaultCursor;
    }

    public static Cursor getHoverCursor() {
        return hoverCursor;
    }

    public static Cursor getTextCursor() {
        return textCursor;
    }
    
    
    
    
    
    //Individual drawing based stuff:
    private GraphicsContext g2;
    private Queue<WGToolTip> toolTipOrder = new Queue<WGToolTip>();
    private Queue<WGQueuedDrawing> dropDownOrder = new Queue<WGQueuedDrawing>();
    /**
     * This constructs a standard WestGraphics object that can then draw the advanced components on the canvas or whatever that the Graphics2D is from.
     * @param g The Graphics supplied from the paintComponent object. (Already casted to Graphics 2D BEFORE construction, as in you have to cast it!)
     */
    public WestGraphics(GraphicsContext g)
    {
        g2 = g;
    }
    /**
     * Draws the given WGDrawingObject based on internal functions
     * @param obj The object to be drawn
     */
    public void draw(WGDrawingObject obj)
    {
        draw(obj, true);
    }
    /**
     * Draws the given WGDrawingObject based on internal functions
     * @param obj The object to be drawn
     * @param drawToolTip Draw the tool tip associated with this object?
     */
    public void draw(WGDrawingObject obj, boolean drawToolTip)
    {
        if(obj.isShown() && !obj.isResizing())
        {
            if(obj instanceof WGButton)
            {
                drawButton((WGButton)obj);
            }
            else if(obj instanceof WGLoadingBar)
            {
                drawLoadingBar((WGLoadingBar)obj);
            }
            else if(obj instanceof WGAnnouncementCard)
            {
                drawAnnouncementCard((WGAnnouncementCard)obj);
            }
            else if(obj instanceof WGToolTip && !drawToolTip)
            {
                drawToolTip((WGToolTip)obj);
            }
            else if(obj instanceof WGPane)
            {
                drawPane((WGPane)obj, false, new Rectangle2D(0, 0, g2.getCanvas().getWidth(), g2.getCanvas().getHeight()));
            }
            else if(obj instanceof WGLabel)
            {
                drawLabel((WGLabel)obj);
            }
            else if(obj instanceof WGTextInput)
            {
                drawTextInput((WGTextInput)obj);
            }
            else if(obj instanceof WGTextArea)
            {
                drawTextArea((WGTextArea)obj);
            }
            else if(obj instanceof WGCheckBox)
            {
                drawCheckBox((WGCheckBox)obj);
            }
            else if(obj instanceof WGKeyInput)
            {
                drawKeyInput((WGKeyInput)obj);
            }
            else if(obj instanceof WGTextImage)
            {
                drawTextImage((WGTextImage)obj);
            }
            else if(obj instanceof WGDropDown)
            {
                WGDropDown dropdown = (WGDropDown)obj;
                Canvas parent = dropdown.getParent();
                dropDownOrder.enqueue(new WGQueuedDrawing(dropdown, new Rectangle2D(parent.getLayoutBounds().getMinX(), parent.getLayoutBounds().getMinY(), parent.getWidth(), parent.getHeight())));
            }
            
            //Now draw the drag and drop if it exists:
            WGDragDropBar dragAndDropBar = obj.getDragAndDropBar();
            if(dragAndDropBar != null)
            {
            	drawDragAndDropBar(dragAndDropBar);
            }
            
            //Now make sure to draw the tool tip that is part of this object:
            if(drawToolTip)
            {
                WGToolTip addedToolTip = obj.getToolTip();
                if(addedToolTip != null && addedToolTip.isShown())
                {
                    //Make sure to add it to the toolTip list so that it can be drawn correctly, over everything else:
                    toolTipOrder.enqueue(addedToolTip);
                }
            }
            
            //Draw the overlay accordingly:
            drawOverlay(obj.getOverlay(), obj);
        }
    }
    
    /**
     * This function draws all of the important objects at the very end of the drawing process, these are objects that were flagged for overlapping or maybe are tooltips.
     * Either way, by calling this function, this guarantees the object is seen and not covered by another object
     */
    public void drawAllEndDrawings()
    {
    	drawAllToolTips();
    	drawAllDropDowns();
    }
    
    /**
     * This function draws all of the dropdowns that were found during the drawing process. This allows for dropdowns to not be hidden by an overlapping object
     */
    private void drawAllDropDowns()
    {
        while(!dropDownOrder.isEmpty())
        {
        	WGQueuedDrawing drawing = dropDownOrder.dequeue();
        	Rectangle2D rect = drawing.getClip();
        	WGDropDown dropDown = (WGDropDown)drawing.getObject();
        	g2.save();
        	g2.beginPath();
        	g2.rect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
        	g2.clip();
            drawDropDown(dropDown);
            
            //Draw the overlay accordingly:
            drawOverlay(dropDown.getOverlay(), dropDown);
            
            g2.restore();
        }
    }
    
    /**
     * This function draws all of the tooltips that were found during the drawing process. This allows for tooltips to be on the correct layer
     */
    private void drawAllToolTips()
    {
        while(!toolTipOrder.isEmpty())
        {
        	WGToolTip toolTip = toolTipOrder.dequeue();
            drawToolTip(toolTip);
            
            //Draw the overlay accordingly:
            drawOverlay(toolTip.getOverlay(), toolTip);
        }
    }
    
    /**
     * This draws an image with a resize
     * @param image The image to be drawn
     * @param x The x location
     * @param y The y location
     * @param widthRescale The rescale of the width
     * @param heightRescale The rescale of the height
     */
    public void drawImage(Image image, int x, int y, double widthRescale, double heightRescale)
    {
        drawImage(image, x, y, widthRescale, heightRescale, 0);
    }
    
    /**
     * This draws an image with a resize
     * @param image The image to be drawn
     * @param x The x location
     * @param y The y location
     * @param widthRescale The rescale of the width
     * @param heightRescale The rescale of the height
     * @param rotation The rotation of the image
     */
    public void drawImage(Image image, int x, int y, double widthRescale, double heightRescale, double rotation)
    {
        Affine originalTransform = g2.getTransform();
        Affine currentTransform = new Affine(widthRescale,0, x, 0, heightRescale, y);
        currentTransform.appendRotation(rotation);
        g2.transform(currentTransform);
        
        g2.setImageSmoothing(false);
        g2.drawImage(image, 0, 0);
        g2.setImageSmoothing(true);
        g2.setTransform(originalTransform);
    }
    
    /**
     * This draws an image with a bunch of rescales using the WGRescaleOptions object
     * @param image The image to be drawn
     * @param x The x location
     * @param y The y location
     * @param rotation The rotation of the image
     * @param rescaleOptions The options for rescaling the image
     */
    public void drawImage(Image image, int x, int y, double rotation, WGRescaleOptions rescaleOptions)
    {
        drawImage(image, x, y, rescaleOptions.getWidthRescale(), rescaleOptions.getHeightRescale(), rotation);
    }
    
    /**
     * This draws an image with a resize
     * @param image The image to be drawn
     * @param Effect The effect for the image
     * @param x The x location
     * @param y The y location
     */
    public void drawImage(Image image, Effect effect, int x, int y)
    {
    	g2.setEffect(effect);
        g2.drawImage(image, x, y);
        g2.setEffect(null);
    }
    
    /**
     * This draws an image with a resize
     * @param image The image to be drawn
     * @param x The x location
     * @param y The y location
     * @param widthRescale The rescale of the width
     * @param heightRescale The rescale of the height
     */
    public void drawImage(Image image, Effect effect, int x, int y, double widthRescale, double heightRescale)
    {
    	g2.setEffect(effect);
        drawImage(image, x, y, widthRescale, heightRescale);
        g2.setEffect(null);
    }
    
    /**
     * This draws an image with a resize
     * @param image The image to be drawn
     * @param x The x location
     * @param y The y location
     * @param widthRescale The rescale of the width
     * @param heightRescale The rescale of the height
     * @param rotation The rotation of the image
     */
    public void drawImage(Image image, Effect effect, int x, int y, double widthRescale, double heightRescale, double rotation)
    {
    	g2.setEffect(effect);
        drawImage(image, x, y, widthRescale, heightRescale, rotation);
        g2.setEffect(null);
    }
    
    /**
     * This draws an image with a bunch of rescales using the WGRescaleOptions object
     * @param image The image to be drawn
     * @param x The x location
     * @param y The y location
     * @param rotation The rotation of the image
     * @param rescaleOptions The options for rescaling the image
     */
    public void drawImage(Image image, Effect effect, int x, int y, double rotation, WGRescaleOptions rescaleOptions)
    {
    	g2.setEffect(effect);
        drawImage(image, x, y, rotation, rescaleOptions);
        g2.setEffect(null);
    }
    
    /**
     * This function draws out a loading bar based on a WGLoadingBar Object, which allows for certain defaults to exist
     * @param loadingBar The Object representation of the loading bar to be drawn
     */
    private void drawLoadingBar(WGLoadingBar loadingBar)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
            
        FXFontMetrics titleFM = new FXFontMetrics(loadingBar.getTitleFont());
        g2.setFont(loadingBar.getTitleFont());
        Rectangle2D originalBarRect = loadingBar.getBounds();
        g2.setFill(loadingBar.getBackgroundColor());
        fill(originalBarRect);
        
        double percentWidth = originalBarRect.getWidth() * (loadingBar.isHorizontal() ? loadingBar.getPercentFilled() : 1);
        double percentHeight = originalBarRect.getHeight() * (loadingBar.isHorizontal() ? 1 : loadingBar.getPercentFilled());
        Rectangle2D percentRect = new Rectangle2D(originalBarRect.getMinX(), originalBarRect.getMinY(), percentWidth, percentHeight);
        g2.setFill(loadingBar.getBarColor());
        fill(percentRect);
        
        g2.setFill(loadingBar.getBorderColor());
        g2.setLineWidth(loadingBar.getBorderSize());
        draw(originalBarRect);
        
        g2.setFill(loadingBar.getTitleColor());
        String text = loadingBar.getTitle();
        g2.fillText(text, (originalBarRect.getMinX() + ((loadingBar.getWidth() - titleFM.stringWidth(text)) / 2.0)), (originalBarRect.getMinY() + ((originalBarRect.getHeight()) / 2)));
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    /**
     * This creates an announcement card based on the object WGAnnouncementCard given
     * @param announcementCard The Object representation of the announcement card
     */ 
    private void drawAnnouncementCard(WGAnnouncementCard announcementCard)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.BASELINE);
            
        //Find the width of the title and the subtitle, whichever is biggest is the total width of the card:
        String title = announcementCard.getTitle();
        String subTitle = announcementCard.getSubTitle();
        FXFontMetrics titleFM = new FXFontMetrics(announcementCard.getTitleFont());
        FXFontMetrics subTitleFM = new FXFontMetrics(announcementCard.getSubTitleFont());
        double titleWidth = titleFM.stringWidth(announcementCard.getTitle());
        double subTitleWidth = subTitleFM.stringWidth(announcementCard.getSubTitle());
        double totalWidth = (titleWidth > subTitleWidth ? titleWidth : subTitleWidth);
        
        //Draw the background if told to:
        if(announcementCard.getDrawBackground())
        {
            double totalHeight = titleFM.getHeight(title) + announcementCard.getSplitHeight() + subTitleFM.getHeight(subTitle);
            Rectangle2D backgroundRect = new Rectangle2D(announcementCard.getX(), announcementCard.getY(), totalWidth, totalHeight);
            g2.setFill(announcementCard.getBackgroundColor());
            fill(backgroundRect);
            g2.setFill(announcementCard.getBorderColor());
            g2.setLineWidth(announcementCard.getBorderSize());
            draw(backgroundRect);
        }
        
        //Now draw the title:
        double titleX = announcementCard.getX() + ((totalWidth - titleWidth) / 2);
        double titleY = announcementCard.getY() + titleFM.getHeight(title);
        g2.setFill(announcementCard.getTitleColor());
        g2.setFont(announcementCard.getTitleFont());
        g2.fillText(announcementCard.getTitle(), titleX, titleY);
        
        //Now draw the seperator:
        double splitY = titleY;
        if(announcementCard.getSplitHeight() > 0)
        {
            splitY += announcementCard.getSplitHeight();
            g2.setStroke(announcementCard.getSplitColor());
            g2.beginPath();
            g2.setLineWidth(announcementCard.getSplitHeight());
            g2.moveTo(announcementCard.getX(), splitY);
            g2.lineTo(announcementCard.getX() + totalWidth, splitY);
            g2.closePath();
            g2.stroke();
        }
        
        //Finish with the Subtitle:
        double subTitleX = announcementCard.getX() + ((totalWidth - subTitleWidth) / 2);
        double subTitleY = splitY + subTitleFM.getHeight(subTitle);
        g2.setFont(announcementCard.getSubTitleFont());
        g2.setFill(announcementCard.getSubTitleColor());
        g2.fillText(announcementCard.getSubTitle(), subTitleX, subTitleY);
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    /**
     * Creates a button based on the parameters given by the WGButton object
     * @param button A WGButton object that defines everything needed to define a button
     */
    private void drawButton(WGButton button)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
            
        //Draw the button
        Rectangle2D buttonRect = button.getBounds();
        if(button.isHovered())
        {
            g2.setFill(button.getHoverBackgroundColor());
        }
        else
        {
            g2.setFill(button.getBackgroundColor());
        }
        fill(buttonRect);
        g2.setStroke(button.getBorderColor());
        g2.setLineWidth(button.getBorderSize());
        draw(buttonRect);
        
        //Draw the contents:
        if(button.getDisplayedImage() != null)
        {
            //The image:
            Affine transformation = new Affine(button.getImageXScale(),0, button.getImageX(),0, button.getImageYScale(), button.getImageY());
            Affine oldTransform = g2.getTransform();
            g2.setTransform(transformation);
            g2.setImageSmoothing(false);
            g2.drawImage(button.getDisplayedImage(), 0, 0);
            g2.setImageSmoothing(true);
            g2.setTransform(oldTransform);
            //Draw a faint overlay of the background:
            Paint background = button.getBackgroundColor();
            if(background instanceof Color)
            {
                Color backgroundColor = (Color)background;
                if(button.isHovered())
                {
                    backgroundColor = (Color)button.getHoverBackgroundColor();
                }
                Paint pictureOverlay = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 70.0/255);
                g2.setFill(pictureOverlay);
                fill(buttonRect);
            }
        }
        else if(button.getTextColor() != null && button.getTextFont() != null && button.getText() != null)
        {
            //The text:
            g2.setFill(button.getTextColor());
            FXFontMetrics textFM = new FXFontMetrics(button.getTextFont());
            double textX = button.getX() + ((button.getWidth() - textFM.stringWidth(button.getText())) / 2);
            double textY = button.getY() + (button.getHeight() / 2);
            g2.setFont(button.getTextFont());
            g2.fillText(button.getText(), textX, textY);
        }
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    /**
     * The drawing method for a tooltip all it requires is the tooltip object then it can draw it from there
     * @param toolTip The tooltip object that tells the graphics how to draw it
     */
    private void drawToolTip(WGToolTip toolTip)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.TOP);
            
        //Draw the button
        Rectangle2D buttonRect = new Rectangle2D(toolTip.getX(), toolTip.getY(), toolTip.getWidth(), toolTip.getHeight());
        g2.setFill(toolTip.getBackgroundColor());
        fill(buttonRect);
        g2.setStroke(toolTip.getBorderColor());
        g2.setLineWidth(toolTip.getBorderSize());
        draw(buttonRect);
        
        //Now the text:
        g2.setFill(toolTip.getTextColor());
        FXFontMetrics textFM = new FXFontMetrics(toolTip.getToolTipFont());
        double textX = toolTip.getX();
        if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_LEFT)
        {
            textX = toolTip.getX() + toolTip.getBorderSize();
        }
        double textY = toolTip.getY() + toolTip.getBorderSize();
        g2.setFont(toolTip.getToolTipFont());
        String[] text = toolTip.getToolTipText();
        for(int i = 0 ; i < text.length ; i++)
        {
            if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
            {
                textX = toolTip.getX() + ((toolTip.getWidth() - textFM.stringWidth(text[i])) / 2);
            }
            else if(toolTip.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
            {
                textX = toolTip.getX() + toolTip.getWidth() - toolTip.getBorderSize() - textFM.stringWidth(text[i]);
            }
            g2.fillText(text[i], textX, textY);
            textY += textFM.getHeight(text[i]);
        }
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    /**
     * The drawing method for a pane that will draw the pane and all subsequent objects attached to the pane
     * @param pane The pane to be drawn and the components added to it that also draw
     */
    private void drawPane(WGPane pane, boolean withinAnotherPane, Rectangle2D oldClip)
    {
        if(!pane.isShown())
        {
            return;
        }
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
        
        //Save the original clip in case the user wanted that one:
        g2.save();
        
        //Draw the background:
        Rectangle2D buttonRect = new Rectangle2D(pane.getX(), pane.getY(), pane.getWidth(), pane.getHeight());
        if(pane.isHovered())
        {
            g2.setFill(pane.getHoverBackgroundColor());
        }
        else
        {
            g2.setFill(pane.getBackgroundColor());
        }
        fill(buttonRect);
        
        
        //To make sure nothing goes off the pane:
        Rectangle2D currentClip = oldClip;
        if(!withinAnotherPane || oldClip.contains(pane.getBounds())) //If not within another pane or the other pane's clip contains our clip, then we can redo the clip
        {
        	g2.beginPath();
        	Rectangle2D rect = pane.getBounds();
        	currentClip = rect;
        	g2.rect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
            g2.clip();
        }
        
        //Now the internal components:
        for(int i = 0 ; i < pane.getComponentNumber() ; i++)
        {
            WGDrawingObject obj = pane.getComponent(i);
            if(obj instanceof WGPane)
            {
                drawPane((WGPane)obj, true, pane.getBounds());
                
                //Draw the overlay accordingly:
                drawOverlay(obj.getOverlay(), obj);
                
                //Remember to collect the toolTip:
                WGToolTip addedToolTip = obj.getToolTip();
                if(addedToolTip != null && addedToolTip.isShown())
                {
                    //Make sure to add it to the toolTip list so that it can be drawn correctly, over everything else:
                    toolTipOrder.enqueue(addedToolTip);
                }
            }
            else
            {
            	//Make sure that if it is a dropDown, to collect the dropDown and the clip:
            	if(obj instanceof WGDropDown)
            	{
                    WGDropDown dropdown = (WGDropDown)obj;
                    dropDownOrder.enqueue(new WGQueuedDrawing(dropdown, currentClip));
            	}
            	else
            	{
            		draw(obj);
            	}
            }
            
            //Now draw the drag and drop if it exists:
            WGDragDropBar dragAndDropBar = obj.getDragAndDropBar();
            if(dragAndDropBar != null)
            {
            	drawDragAndDropBar(dragAndDropBar);
            }
        }
        
        //Draw the scrollBars
        drawScrollBar(pane.getVerticalScroll(), pane);
        drawScrollBar(pane.getHorizontalScroll(), pane);
        
        
        //Reload the old clip, as it is no longer useful:
        g2.restore();
        
        
        //Then draw in the border:
        g2.setStroke(pane.getBorderColor());
        g2.setLineWidth(pane.getBorderSize());
        draw(buttonRect);
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    private void drawScrollBar(WGScrollableListener scrollBar, WGPane pane)
    {
        //Find the scrollBar offset:
        double scrollBarX = 0;
        double scrollBarY = 0;
        double scrollBarWidth = 0;
        double scrollBarHeight = 0;
        if(pane.isScrollable() && scrollBar != null && scrollBar.isShown())
        {
            scrollBarX = pane.getX();
            scrollBarY = pane.getY();
            scrollBarWidth = pane.getBorderSize();
            scrollBarHeight = scrollBarWidth;
            if(scrollBar.isVertical())
            {
                scrollBarY += scrollBar.getScrollBarY();
                scrollBarHeight = scrollBar.getScrollBarHeight();
                scrollBarX += pane.getWidth() - scrollBarWidth - pane.getBorderSize();
            }
            else
            {
                scrollBarX += scrollBar.getScrollBarY();
                scrollBarWidth = scrollBar.getScrollBarHeight();
                scrollBarY += pane.getHeight() - scrollBarHeight - pane.getBorderSize();
            }
        }
        
        //Then draw in the scrollBar:
        if(pane.isScrollable() && scrollBar != null && scrollBar.isShown())
        {
            g2.setFill(pane.getScrollBarColor());
            Rectangle2D scrollBarRect =  new Rectangle2D(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);
            fill(scrollBarRect);
        }
    }
    private void drawScrollBar(WGTextScrollableListener scrollBar, WGTextArea textArea)
    {
        //Find the scrollBar offset:
        double scrollBarX = 0;
        double scrollBarY = 0;
        double scrollBarWidth = 0;
        double scrollBarHeight = 0;
        if(scrollBar != null && scrollBar.isShown())
        {
            scrollBarX = textArea.getX();
            scrollBarY = textArea.getY();
            scrollBarWidth = textArea.getBorderSize();
            scrollBarHeight = scrollBarWidth;
            
            scrollBarY += scrollBar.getScrollBarY();
            scrollBarHeight = scrollBar.getScrollBarHeight();
            scrollBarX += textArea.getWidth() - scrollBarWidth - textArea.getBorderSize();
        }
        
        //Then draw in the scrollBar:
        if(scrollBar != null && scrollBar.isShown())
        {
            g2.setFill(textArea.getScrollBarColor());
            Rectangle2D scrollBarRect =  new Rectangle2D(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);
            fill(scrollBarRect);
        }
    }
    /**
     * The drawing method for a label that will draw the text inside a box according to the methods given by the WGLabel
     * @param pane The label to be drawn
     */
    private void drawLabel(WGLabel label)
    {
        
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.BASELINE);
        
        //Draw the text:
        g2.setFill(label.getTextColor());
        FXFontMetrics textFM = new FXFontMetrics(label.getTextFont());
        double textX = label.getX();
        
        double textY = label.getY() + label.getBorderSize();
        g2.setFont(label.getTextFont());
        
        if(label.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
        {
            textX = label.getX() + ((label.getWidth() - textFM.stringWidth(label.getText())) / 2);
        }
        else if(label.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
        {
            textX = label.getX() + label.getWidth() - textFM.stringWidth(label.getText());
        }
        
        textY += textFM.getHeight(label.getText());
        g2.fillText(label.getText(), textX, textY);
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    /**
     * Creates a textInput based on the information given by the object
     * @param textInput The object that gives information about how a text input is supposed to be drawn
     */
    private void drawTextInput(WGTextInput textInput)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
            
        //Draw the Box
        Rectangle2D buttonRect = new Rectangle2D(textInput.getX(), textInput.getY(), textInput.getWidth(), textInput.getHeight());
        Paint backgroundColor = (textInput.isFocused()) ? textInput.getBackgroundOnFocusColor() : textInput.getBackgroundColor();
        if(textInput.isHovered() && !textInput.isFocused())
        {
            g2.setFill(textInput.getHoverBackgroundColor());
        }
        else
        {
            g2.setFill(backgroundColor);
        }
        fill(buttonRect);
        Paint borderColor = textInput.getBorderColor();
        g2.setStroke(borderColor);
        g2.setLineWidth(textInput.getBorderSize());
        draw(buttonRect);
            
        //Draw the highlight:
        if(textInput.isHighlightShown() && textInput.isFocused())
        {
            g2.setFill(textInput.getHighlightColor());
            fill(textInput.getHighlightBounds());
        }
        
        //Now the text:
        g2.setFill(textInput.getTextColor());
        FXFontMetrics textFM = new FXFontMetrics(textInput.getTextFont());
        double textX = textInput.getX() + ((textInput.getWidth() - textFM.stringWidth(textInput.getText())) / 2);
        double textY = textInput.getY() + ((textInput.getHeight()) / 2);
        g2.setFont(textInput.getTextFont());
        g2.fillText(textInput.getText(), textX, textY);
            
        //Draw the cursor
        if(textInput.isCursorShown() && textInput.isFocused())
        {
            g2.setFill(textInput.getCursorColor());
            fill(textInput.getCursorBounds());
        }
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    /**
     * 
     * @param textArea 
     */
    private void drawTextArea(WGTextArea textArea)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
        
        //Save the original clip in case the user wanted that one:
        g2.save();
        
        //To make sure nothing goes off the pane:
    	g2.beginPath();
    	Rectangle2D rect = textArea.getBounds();
    	g2.rect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
        g2.clip();
        
        //Draw the text:
        FXFontMetrics textFM = new FXFontMetrics(textArea.getTextFont());
        double textX = textArea.getX();
        double textY = textArea.getY() + textArea.getBorderSize();
        
        if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_LEFT)
        {
            textX = textArea.getX() + textArea.getBorderSize();
        }
        
        g2.setFont(textArea.getTextFont());
        ArrayList<String> allText = null;
        ArrayList<Paint> allTextColors;
        if(textArea.isTextWrapped())
        {
            allText = textArea.getFormatedText();
            allTextColors = textArea.getFormatedColors();
        }
        else
        {
            allText = textArea.getText();
            allTextColors = textArea.getTextColors();
        }
        if(allText != null)
        {
	        textY += textArea.getStringYOffset();
	        for(int i = 0 ; i < allText.size() ; i++)
	        {
	            if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_MIDDLE)
	            {
	                textX = textArea.getX() + ((textArea.getWidth() - textFM.stringWidth(allText.get(i))) / 2);
	            }
	            else if(textArea.getTextStyle() == WGToolTip.TEXT_STYLE_RIGHT)
	            {
	                double stringWidth = textFM.stringWidth(allText.get(i));
	                textX = textArea.getX() + textArea.getWidth() - stringWidth - textArea.getBorderSize();
	            }
	            g2.setFill(allTextColors.get(i));
	            g2.fillText(allText.get(i), textX, textY);
	            textY += textFM.getHeight(allText.get(i));
	        }
        }
        
        //Draw the scrollBars
        drawScrollBar(textArea.getVerticalScroll(), textArea);
        
        
        //Reload the old clip, as it is no longer useful:
        g2.restore();
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    public void drawCheckBox(WGCheckBox checkBox)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
            
        //Draw the button
        Rectangle2D buttonRect = checkBox.getBounds();
        if(checkBox.isHovered())
        {
            g2.setFill(checkBox.getHoverBackgroundColor());
        }
        else
        {
            g2.setFill(checkBox.getBackgroundColor());
        }
        fill(buttonRect);
        g2.setStroke(checkBox.getBorderColor());
        g2.setLineWidth(checkBox.getBorderSize());
        draw(buttonRect);
        
        //Draw the Check when needed:
        if(checkBox.isChecked())
        {
            g2.setStroke(checkBox.getCheckColor());
            g2.beginPath();
            g2.moveTo(buttonRect.getMinX(), buttonRect.getMinY());
            g2.lineTo(buttonRect.getMinX() + buttonRect.getWidth(), buttonRect.getMinY() + buttonRect.getHeight());
            g2.closePath();
            g2.stroke();
            g2.beginPath();
            g2.moveTo(buttonRect.getMinX() + buttonRect.getWidth(), buttonRect.getMinY());
            g2.lineTo(buttonRect.getMinX(), buttonRect.getMinY() + buttonRect.getHeight());
            g2.closePath();
            g2.stroke();
        }
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    private void drawKeyInput(WGKeyInput textInput)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
            
        //Draw the Box
        Rectangle2D buttonRect = new Rectangle2D(textInput.getX(), textInput.getY(), textInput.getWidth(), textInput.getHeight());
        Paint backgroundColor = (textInput.isFocused()) ? textInput.getBackgroundOnFocusColor() : textInput.getBackgroundColor();
        if(textInput.isHovered() && !textInput.isFocused())
        {
            g2.setFill(textInput.getHoverBackgroundColor());
        }
        else
        {
            g2.setFill(backgroundColor);
        }
        fill(buttonRect);
        Paint borderColor = textInput.getBorderColor();
        g2.setStroke(borderColor);
        g2.setLineWidth(textInput.getBorderSize());
        draw(buttonRect);
        
        //Now the text:
        g2.setFill(textInput.getTextColor());
        FXFontMetrics textFM = new FXFontMetrics(textInput.getTextFont());
        double textX = textInput.getX() + ((textInput.getWidth() - textFM.stringWidth(textInput.getText())) / 2);
        double textY = textInput.getY() + ((textInput.getHeight()) / 2);
        g2.setFont(textInput.getTextFont());
        g2.fillText(textInput.getText(), textX, textY);
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    private void drawTextImage(WGTextImage textImage)
    {
    	VPos lastpos = g2.getTextBaseline();
    	if(textImage.getTextPosition() == WGTextImage.TEXT_UPPER_LEFT_CORNER || textImage.getTextPosition() == WGTextImage.TEXT_UPPER_RIGHT_CORNER)
    	{
        	g2.setTextBaseline(VPos.TOP);
    	}
    	else
    	{
        	g2.setTextBaseline(VPos.BASELINE);
    	}
        //Draw the Image:
        Image image = textImage.getDisplayImage();
        if(image == null) //Don't draw something that will result in an error!
        {
            return;
        }
        Affine transformation = new Affine(textImage.getImageXScale(),0, textImage.getX() + textImage.getImageOffSetX(), 0, textImage.getImageYScale(), textImage.getY() + textImage.getImageOffSetY());
        Affine oldTransform = g2.getTransform();
        g2.setTransform(transformation);
        g2.setImageSmoothing(false);
        g2.drawImage(image, 0, 0);
        g2.setImageSmoothing(true);
        g2.setTransform(oldTransform);
        
        //Now the text:
        g2.setFill(textImage.getTextColor());
        double textX = textImage.getTextX();
        double textY = textImage.getTextY();
        g2.setFont(textImage.getTextFont());
        g2.fillText(textImage.getImageText(), (textImage.getX() + textX + textImage.getImageOffSetX()), (textImage.getY() + textY + textImage.getImageOffSetY()));
    	g2.setTextBaseline(lastpos);
    }
    private void drawDropDown(WGDropDown dropDown)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
        
        if(!dropDown.isDroppedDown())
        {
            //Simple to draw:

            //Draw the button
            Rectangle2D buttonRect = dropDown.getBounds();
            if(dropDown.isHovered())
            {
                g2.setFill(dropDown.getHoverBackgroundColor());
            }
            else
            {
                g2.setFill(dropDown.getBackgroundColor());
            }
            fill(buttonRect);
            g2.setStroke(dropDown.getBorderColor());
            g2.setLineWidth(dropDown.getBorderSize());
            draw(buttonRect);

            //Draw the text:
            g2.setFill(dropDown.getTextColor());
            FXFontMetrics textFM = new FXFontMetrics(dropDown.getTextFont());
            String text = dropDown.getSelectedChoiceText();
            double textX = dropDown.getX() + ((dropDown.getWidth() - textFM.stringWidth(text)) / 2);
            double textY = dropDown.getY() + ((dropDown.getHeight()) / 2);
            g2.setFont(dropDown.getTextFont());
            g2.fillText(text, textX, textY);
        }
        else
        {
            //More complex to draw:
            
            //Start by breaking the whole entire object into the individual buttons that make it up:
            double buttonX = dropDown.getX();
            double buttonY = dropDown.getY();
            String[] choices = dropDown.getChoices();
            Paint[] textColors = dropDown.getTextColors();
            for(int i = 0 ; i < choices.length ; i++)
            {
                Rectangle2D buttonBounds = new Rectangle2D(buttonX, buttonY, dropDown.getButtonWidth(), dropDown.getButtonHeight());
                
                //Draw the button:
                if(dropDown.isHovered() && dropDown.getHoveredIndex() == i)
                {
                    g2.setFill(dropDown.getHoverBackgroundColor());
                }
                else
                {
                    g2.setFill(dropDown.getBackgroundColor());
                }
                fill(buttonBounds);
                g2.setStroke(dropDown.getBorderColor());
                g2.setLineWidth(dropDown.getBorderSize());
                draw(buttonBounds);
                
                //Draw the text:
                g2.setFill(textColors[i]);
                FXFontMetrics textFM = new FXFontMetrics(dropDown.getTextFont());
                String text = choices[i];
                double textX = buttonBounds.getMinX() + ((dropDown.getButtonWidth() - textFM.stringWidth(text)) / 2);
                double textY = buttonBounds.getMinY() + ((dropDown.getButtonHeight()) / 2);
                g2.setFont(dropDown.getTextFont());
                g2.fillText(text, textX, textY);
                
                //Now set up the next button's starting point:
                buttonY += dropDown.getButtonHeight();
            }
        }
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
        g2.setTextBaseline(lastpos);
    }
    private void drawDragAndDropBar(WGDragDropBar bar)
    {
        //Save the original stroke in case the user wanted that one
        Double oldStroke = g2.getLineWidth();

        //Make the bar:
        if(bar.isHovered())
        {
        	//Make the whole background:
        	g2.setFill(bar.getHoverBackgroundColor());
        	fill(bar.getBounds());
        }
        
        //Now do the three dots:
        g2.setFill(bar.getBarColor());
        double diameter = bar.getBorderSize();
        double radius = diameter/2;
        double x = bar.getX() + radius;
        double y = bar.getY() + radius;
        g2.fillOval(x, y, diameter, diameter);
        g2.fillOval((bar.getX() + bar.getWidth()/2.0) - radius/2, y, diameter, diameter);
        g2.fillOval((bar.getX() + bar.getWidth()) - diameter, y, diameter, diameter);
        
        //Now in the case that this exists, we need to draw the drag drop line:
        if(bar.getClickListener() != null && ((WGDragDropClickListener)bar.getClickListener()).getDragDropLine() != null)
        {
        	g2.setLineWidth(bar.getBorderSize());
        	Rectangle2D line = ((WGDragDropClickListener)bar.getClickListener()).getDragDropLine();
            g2.setStroke(bar.getHoverBackgroundColor());
            g2.strokeRect(line.getMinX(), line.getMinY(), line.getWidth(), line.getHeight());
        }
        
        //And reload it at the end
        g2.setLineWidth(oldStroke);
    }
    
    private void drawOverlay(WGOverlay overlay, WGDrawingObject obj)
    {
    	if(overlay == null)
    	{
    		return;
    	}
        //Save the original stroke in case the user wanted that one
    	VPos lastpos = g2.getTextBaseline();
    	g2.setTextBaseline(VPos.CENTER);
    	
    	Rectangle2D rect = obj.getBounds();
    	
    	g2.setFill(overlay.getOverlayColor());
    	fill(rect);
    	
    	g2.setFill(overlay.getTextColor());
    	String text = overlay.getText();
    	g2.setFont(overlay.getTextFont());
    	FXFontMetrics fm = new FXFontMetrics(overlay.getTextFont());
    	g2.fillText(text, rect.getMinX() + (rect.getWidth() - fm.stringWidth(text))/2, rect.getMinY() + rect.getHeight()/2);

        //And reload it at the end
        g2.setTextBaseline(lastpos);
    }
    
    //Porting functions:
    public void fill(Rectangle2D rect)
    {
    	g2.fillRect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
    }
    public void fill(Ellipse oval)
    {
    	Double radiusX = oval.getRadiusX();
    	Double radiusY = oval.getRadiusY();
    	g2.fillOval(oval.getCenterX() - radiusX, oval.getCenterY() - radiusY, radiusX * 2, radiusY * 2);
    }
    public void draw(Rectangle2D rect)
    {
    	g2.strokeRect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
    }
    public void draw(Ellipse oval)
    {
    	Double radiusX = oval.getRadiusX();
    	Double radiusY = oval.getRadiusY();
    	g2.strokeOval(oval.getCenterX() - radiusX, oval.getCenterY() - radiusY, radiusX * 2, radiusY * 2);
    }
}
