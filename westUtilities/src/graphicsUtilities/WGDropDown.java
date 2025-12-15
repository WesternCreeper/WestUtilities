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
import javafx.scene.text.Font;

/**
 *
 * @author Westley
 */
public class WGDropDown extends WGBox
{
    private String[] choices;
    private Font textFont;
    private Paint textColor;
    private Paint[] textColors;
    private double buttonWidth;
    private double buttonHeight;
    private int selectedChoice = -1;
    private int hoveredIndex = -1;
    private boolean droppedDown = false;
    
    public WGDropDown(Rectangle2D bounds, float borderSize, String[] choices, int selectedChoice, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Canvas parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, backgroundColor, borderColor, parent);
        this.choices = choices;
        this.selectedChoice = selectedChoice;
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
            
            super.setClickListener(new WGDropDownListener(this, parent));
            getParent().addEventHandler(MouseEvent.ANY, getClickListener());
            getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
            WestGraphics.add(this);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    public WGDropDown(Rectangle2D bounds, String[] choices, int selectedChoice, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        super(theme.getBorderSize(), theme.getBackgroundColor(), theme.getHoverBackgroundColor(), theme.getBorderColor(), parent, theme);
        this.choices = choices;
        this.selectedChoice = selectedChoice;
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
            
            super.setClickListener(new WGDropDownListener(this, parent));
            getParent().addEventHandler(MouseEvent.ANY, getClickListener());
            getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
            WestGraphics.add(this);
        }
        else
        {
            throw new WGNullParentException();
        }
        setCurrentTheme(theme);
    }
    /**
     * For the custom drop down listener
     * @param bounds
     * @param choices
     * @param selectedChoice
     * @param listener
     * @param parent
     * @param theme
     * @throws WGNullParentException 
     */
    public WGDropDown(Rectangle2D bounds, String[] choices, int selectedChoice, WGDropDownListener listener, Canvas parent, WGTheme theme) throws WGNullParentException
    {
        super(theme.getBorderSize(), theme.getBackgroundColor(), theme.getHoverBackgroundColor(), theme.getBorderColor(), parent, theme);
        this.choices = choices;
        this.selectedChoice = selectedChoice;
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            getParent().widthProperty().addListener(resizer.getResizeListener());
            getParent().heightProperty().addListener(resizer.getResizeListener());
            resizer.resizeComps();
            
            listener.setParentComponent(parent);
            listener.setParentObject(this);
            super.setClickListener(listener);
            getParent().addEventHandler(MouseEvent.ANY, getClickListener());
            getParent().addEventHandler(ScrollEvent.ANY, getClickListener());
            WestGraphics.add(this);
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    
    //Methods:
    @Override
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
    
    public void setTheme(WGTheme theme)
    {
        super.setTheme(theme);
        textFont = theme.getTextFont();
        textColor = theme.getTextColor();
    }
    
    
    //Setters
    public void setChoices(String[] choices) {
        this.choices = choices;
        resizer.resizeComps();
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
        resizer.resizeComps();
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
        resizer.resizeComps();
    }

    public void setSelectedChoice(int selectedChoice) {
        this.selectedChoice = selectedChoice;
        ((ButtonResizeListener)resizer).resizeComps(false);
    }

    public void setDroppedDown(boolean droppedDown) {
    	boolean previousState = this.droppedDown;
        this.droppedDown = droppedDown;
        if(previousState != droppedDown)
        {
        	((ButtonResizeListener)resizer).resizeComps(false);
        }
    }

    public void setHoveredIndex(int hoveredIndex) {
        this.hoveredIndex = hoveredIndex;
    }
    
    
    //Getter:
    public String[] getChoices() {
        return choices;
    }

    public Font getTextFont() {
        return textFont;
    }

    public Paint getTextColor() {
        return textColor;
    }

    public int getSelectedChoice() {
        return selectedChoice;
    }

    public boolean isDroppedDown() {
        return droppedDown;
    }

    public double getButtonWidth() {
        return buttonWidth;
    }

    public double getButtonHeight() {
        return buttonHeight;
    }

    public int getHoveredIndex() {
        return hoveredIndex;
    }
    
    public String getSelectedChoiceText()
    {
    	if(selectedChoice < choices.length)
    	{
            return choices[selectedChoice];
    	}
    	return "";
    }
    
    public Paint[] getTextColors()
    {
        return textColors;
    }
    
    
    //classes or Listeners:
    private class ButtonResizeListener extends WGDrawingObjectResizeListener
    {
        private ButtonResizeListener(double xPercent, double yPercent, double widthPercent, double heightPercent)
        {
            super(xPercent, yPercent, widthPercent, heightPercent);
        }
        public void resizeComps(boolean doXYLocation)
        {
        	Platform.runLater(() -> {
            	resizeCompsWithoutDelay(doXYLocation);
        	});
        }
        public void resizeCompsWithoutDelay()
        {
        	resizeCompsWithoutDelay(true);
        }
        public void resizeCompsWithoutDelay(boolean doXYLocation)
        {
        	setResizing(true);
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getWidth();
            double parentHeight = getParent().getHeight();
            double borderPadding = getBorderSize() * 2.0; //This is to make sure that the border does not interefere with the text that is drawn on the button
            //Set up the x, y, width, and height components based on the percentages given and the parent's size
            if(doXYLocation)
            {
                setX(getXPercent() * parentWidth);
                setY(getYPercent() * parentHeight);
            }
            buttonWidth = getWidthPercent() * parentWidth;
            buttonHeight = getHeightPercent() * parentHeight;
            if(!droppedDown)
            {
                setWidth(getWidthPercent() * parentWidth);
                setHeight(getHeightPercent() * parentHeight);
            }
            else //Set the size to the total size of the object, this is for the dropdown listener to work properly:
            {
                setWidth(getWidthPercent() * parentWidth);
                setHeight(getHeightPercent() * parentHeight * choices.length);
            }
            
            if(selectedChoice >= 0 && selectedChoice < choices.length)
            {
                textFont = WGFontHelper.getFittedFontForBox(textFont, getParent(), buttonWidth - borderPadding, buttonHeight - borderPadding, choices[selectedChoice], 100);
            }
            
            //Now fix the colors of this object:
            if(getCurrentTheme() != null && getCurrentTheme().getGradientOrientationPreferences() != null)
            {
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BACKGROUND_COLOR)));
                setHoverBackgroundColor(fixPaintBounds(getHoverBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.HOVER_BACKGROUND_COLOR)));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.BORDER_COLOR)));
                
                //For the single instance
                textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR));
                 
                //For the dropped down instance
                textColors = new Paint[choices.length];
                double x = getX();
                double y = getY();
                for(int i = 0 ; i < textColors.length ; i++)
                {
                    textColors[i] = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find(WGTheme.TEXT_COLOR), null, x, y, buttonWidth, buttonHeight);
                    y += buttonHeight;
                }
            }
            else
            {
            	//For the single instance:
                textColor = fixPaintBounds(textColor);
                   
                //For the dropped down instance:
                textColors = new Paint[choices.length];
                double x = getX();
                double y = getY();
                for(int i = 0 ; i < textColors.length ; i++)
                {
                    textColors[i] = fixPaintBounds(textColor, WGDrawingObject.NO_GRADIENT_ORIENTATION_PREFERENCE, null, x, y, buttonWidth, buttonHeight);
                    y += buttonHeight;
                }
            }
        	setResizing(false);
        }
    }
}
