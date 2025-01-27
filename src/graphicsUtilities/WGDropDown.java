/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Component;
import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

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
    
    public WGDropDown(Rectangle2D.Double bounds, float borderSize, String[] choices, int selectedChoice, Font textFont, Paint backgroundColor, Paint borderColor, Paint textColor, Component parent) throws WGNullParentException
    {
        super(borderSize, backgroundColor, WGTheme.getHoverBackgroundColor(backgroundColor), borderColor, parent);
        this.choices = choices;
        this.selectedChoice = selectedChoice;
        this.textFont = textFont;
        this.textColor = textColor;
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            
            super.setClickListener(new WGDropDownListener(this, parent));
            getParent().addMouseListener(getClickListener());
            getParent().addMouseMotionListener((WGDropDownListener)getClickListener());
            getParent().addMouseWheelListener((WGDropDownListener)getClickListener());
        }
        else
        {
            throw new WGNullParentException();
        }
    }
    
    public WGDropDown(Rectangle2D.Double bounds, String[] choices, int selectedChoice, Component parent, WGTheme theme) throws WGNullParentException
    {
        super(theme.getBorderSize(), theme.getBackgroundColor(), WGTheme.getHoverBackgroundColor(theme.getBackgroundColor()), theme.getBorderColor(), parent, theme);
        this.choices = choices;
        this.selectedChoice = selectedChoice;
        this.textFont = theme.getTextFont();
        this.textColor = theme.getTextColor();
        if(getParent() != null)
        {
            resizer = new ButtonResizeListener(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            getParent().addComponentListener(resizer);
            resizer.resizeComps();
            
            super.setClickListener(new WGDropDownListener(this, parent));
            getParent().addMouseListener(getClickListener());
            getParent().addMouseMotionListener((WGDropDownListener)getClickListener());
            getParent().addMouseWheelListener((WGDropDownListener)getClickListener());
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
        getParent().removeComponentListener(resizer);
        if(getToolTip() != null)
        {
            getToolTip().removeListeners();
        }
        
        WGButtonListener buttoner = (WGDropDownListener)(getClickListener());
        getParent().removeMouseListener(buttoner);
        getParent().removeMouseMotionListener(buttoner);
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
        this.droppedDown = droppedDown;
        ((ButtonResizeListener)resizer).resizeComps(false);
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
        return choices[selectedChoice];
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
        public void resizeComps()
        {
            resizeComps(true);
        }
        public void resizeComps(boolean doXYLocation)
        {
            //Find the parent width and height so that the x/y can be scaled accordingly
            double parentWidth = getParent().getSize().getWidth();
            double parentHeight = getParent().getSize().getHeight();
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
                setBackgroundColor(fixPaintBounds(getBackgroundColor(), getCurrentTheme().getGradientOrientationPreferences().find("BackgroundColor")));
                setBorderColor(fixPaintBounds(getBorderColor(), getCurrentTheme().getGradientOrientationPreferences().find("BorderColor")));
                if(!droppedDown)
                {
                    textColor = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find("TextColor"));
                }
                else
                {
                    textColors = new Paint[choices.length];
                    double x = getX();
                    double y = getY();
                    for(int i = 0 ; i < textColors.length ; i++)
                    {
                        textColors[i] = fixPaintBounds(textColor, getCurrentTheme().getGradientOrientationPreferences().find("TextColor"), null, x, y, buttonWidth, buttonHeight);
                        y += buttonHeight;
                    }
                }
            }
            else
            {
                if(!droppedDown)
                {
                    textColor = fixPaintBounds(textColor);
                }
                else
                {
                    textColors = new Paint[choices.length];
                    double x = getX();
                    double y = getY();
                    for(int i = 0 ; i < textColors.length ; i++)
                    {
                        textColors[i] = fixPaintBounds(textColor, WGDrawingObject.NO_GRADIENT_ORIENTATION_PREFERENCE, null, x, y, buttonWidth, buttonHeight);
                        y += buttonHeight;
                    }
                }
            }
            
            //Then repaint the parent to make sure the parent sees the change
            getParent().repaint();
        }
    }
}
