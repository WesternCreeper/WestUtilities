package graphicsUtilities;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * This class allows for other graphical components to be dragged and dropped.
 */
public class WGDragDropBar extends WGBox
{
	private Paint barColor;
	public WGDragDropBar(Rectangle2D bounds, double borderSize, Paint barColor, Paint hoverColor, WGDrawingObject object, Canvas parent)
	{
		super(borderSize, null, hoverColor, null, parent);
        resizer = new DragAndDropResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        getParent().widthProperty().addListener(resizer.getResizeListener());
        getParent().heightProperty().addListener(resizer.getResizeListener());
        super.setClickListener(new WGDragDropClickListener(this, object, parent));
        getParent().addEventHandler(MouseEvent.ANY, getClickListener());
        resizer.resizeComps();
		this.barColor = barColor;
		setHoverBackgroundColor(Color.RED);
        WestGraphics.add(this);
	}
	public WGDragDropBar(Rectangle2D bounds, double borderSize, WGDrawingObject object, Canvas parent, WGTheme theme)
	{
		this(bounds, borderSize, theme.getDragAndDropBarColor(), theme.getHoverBackgroundColor(), object, parent);
		setCurrentTheme(theme);
	}
    
    /**
     * This removes the listeners attached to this object:
     */
    public void removeListeners()
    {
        getParent().widthProperty().removeListener(resizer.getResizeListener());
        getParent().heightProperty().removeListener(resizer.getResizeListener());
        getParent().removeEventHandler(MouseEvent.ANY, getClickListener());
        if(getToolTip() != null)
        {
            getToolTip().removeListeners();
        }

        WestGraphics.remove(this);
    }
    
	
	//Setters:
	public void setBarColor(Paint barColor) {
		this.barColor = barColor;
	}
	
	
	//Getters:
	public Paint getBarColor() {
		return barColor;
	}
	
	
	//Classes
	private class DragAndDropResizeListener extends WGDrawingObjectResizeListener
    {
        private DragAndDropResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeCompsWithoutDelay()
        {
        	//Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            setX(getXPercent() * parentWidth);
            setY(getYPercent() * parentHeight);
            setWidth(getWidthPercent() * parentWidth);
            
            //Make sure the height is always the same as the size of the dots:
            double height = getHeightPercent() * parentHeight;
            double realHeight = getBorderSize() * 2;
            if(height != realHeight)
            {
            	height = realHeight;
            	//Make sure the height percent is updated:
            	setHeightPercent(realHeight / getParent().getHeight());
            }
            setHeight(height);
            

            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
            	barColor = fixPaintBounds(barColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.DRAG_AND_DROP_BAR_COLOR));
            	setHoverBackgroundColor(fixPaintBounds(getHoverBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.HOVER_BACKGROUND_COLOR)));
            }
        }
    }
}
