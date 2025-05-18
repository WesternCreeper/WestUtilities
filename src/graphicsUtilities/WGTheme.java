/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import dataStructures.HashTable;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Font;

/**
 *
 * @author Westley
 * This is a pretty simple class on the surface, but it takes a lot of variables to construct. However once it is constructed it will make creating any new WGObjects significantly easier.
 * Basically think of this class like a theme on a web page, there are certain key components that are shared, like color, font type, font style, etc.
 * There is an additional constructor here that allows for a WGTheme, use this if you want to make another theme based on an old one
 */
public class WGTheme implements TextStyles
{
    public static final String TEXT_FONT = "Text Font";
    public static final String TEXT_POSITION = "Text Position";
    public static final String TEXT_STYLE = "Text Style";
    public static final String TEXT_X_SIZE_PERCENT = "Text X Size Percent";
    public static final String TEXT_Y_SIZE_PERCENT = "Text Y Size Percent";
    public static final String BORDER_SIZE = "Border Size";
    public static final String TEXT_COLOR = "Text Color";
    public static final String BORDER_COLOR = "Border Color";
    public static final String BACKGROUND_COLOR = "Background Color";
    public static final String SCROLL_BAR_COLOR = "Scrollbar Color";
    public static final String TITLE_COLOR = "Title Color";
    public static final String SPLIT_COLOR = "Split Color";
    public static final String SUBTITLE_COLOR = "Subtitle Color";
    public static final String CHECK_COLOR = "Check Color";
    public static final String FOCUSED_BACKGROUND_COLOR = "Focused Background Color";
    public static final String HOVER_BACKGROUND_COLOR = "Hover Background Color";
    public static final String BAR_COLOR = "Bar Color";
    public static final String CURSOR_COLOR = "Cursor Color";
    public static final String HIGHLIGHT_COLOR = "Highlight Color";
    public static final HashTable DEFAULT_PREFERRENCES = new HashTable(20, HashTable.HASHING_OPTION_LINEAR, 22);
    //Create the preferrences from above:
    static
    {
        DEFAULT_PREFERRENCES.insert(TEXT_COLOR, WGDrawingObject.VERTICAL_GRADIENT_ORIENTATION_PREFERENCE);
    }
    
    private int textStyle;
    private int textPosition;
    private double textXSizePercent;
    private double textYSizePercent;
    private float borderSize;
    private Paint backgroundColor;
    private Paint hoverBackgroundColor;
    private Paint focusedBackgroundColor;
    private Paint borderColor;
    private Paint textColor;
    private Paint checkColor;
    private Paint cursorColor;
    private Paint highlightColor;
    private Paint scrollBarColor;
    private Paint barColor;
    private Font textFont;
    private HashTable gradientOrientationPreferences;
    public WGTheme(float borderSize, int textStyle, int textPosition, double textXSizePercent, double textYSizePercent, Paint backgroundColor, Paint borderColor, Paint textColor, Paint scrollBarColor, Paint cursorColor, Paint highlightColor, Paint barColor, Paint checkColor, Font textFont)
    {
        this(borderSize, textStyle, textPosition, textXSizePercent, textYSizePercent, backgroundColor, borderColor, textColor, scrollBarColor, cursorColor, highlightColor, barColor, checkColor, textFont, DEFAULT_PREFERRENCES);
    }
    public WGTheme(float borderSize, int textStyle, int textPosition, double textXSizePercent, double textYSizePercent, Paint backgroundColor, Paint borderColor, Paint textColor, Paint scrollBarColor, Paint cursorColor, Paint highlightColor, Paint barColor, Paint checkColor, Font textFont, HashTable gradientOrientationPreferences)
    {
        this.textFont = textFont;
        this.textPosition = textPosition;
        this.textXSizePercent = textXSizePercent;
        this.textYSizePercent = textYSizePercent;
        this.textStyle = textStyle;
        this.borderSize = borderSize;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
        this.scrollBarColor = scrollBarColor;
        this.cursorColor = cursorColor;
        this.highlightColor = highlightColor;
        this.barColor = barColor;
        this.checkColor = checkColor;
        this.gradientOrientationPreferences = gradientOrientationPreferences;
    }
    public WGTheme(HashTable themeData, HashTable gradientOrientationPreferences)
    {
        this.textFont = toFont(themeData.find(WGTheme.TEXT_FONT));
        this.textPosition = toInt(themeData.find(WGTheme.TEXT_POSITION));
        this.textXSizePercent = toDouble(themeData.find(WGTheme.TEXT_X_SIZE_PERCENT));
        this.textYSizePercent = toDouble(themeData.find(WGTheme.TEXT_Y_SIZE_PERCENT));
        this.textStyle = toInt(themeData.find(WGTheme.TEXT_STYLE));
        this.borderSize = toFloat(themeData.find(WGTheme.BORDER_SIZE));
        this.backgroundColor = toPaint(themeData.find(WGTheme.BACKGROUND_COLOR));
        this.borderColor = toPaint(themeData.find(WGTheme.BORDER_COLOR));
        this.textColor = toPaint(themeData.find(WGTheme.TEXT_COLOR));
        this.scrollBarColor = toPaint(themeData.find(WGTheme.SCROLL_BAR_COLOR));
        this.cursorColor = toPaint(themeData.find(WGTheme.CURSOR_COLOR));
        this.highlightColor = toPaint(themeData.find(WGTheme.HIGHLIGHT_COLOR));
        this.barColor = toPaint(themeData.find(WGTheme.BAR_COLOR));
        this.checkColor = toPaint(themeData.find(WGTheme.CHECK_COLOR));
        this.hoverBackgroundColor = toPaint(themeData.find(WGTheme.HOVER_BACKGROUND_COLOR));
        this.focusedBackgroundColor = toPaint(themeData.find(WGTheme.FOCUSED_BACKGROUND_COLOR));
        this.gradientOrientationPreferences = gradientOrientationPreferences;
        
        if(hoverBackgroundColor == null)
        {
            if(backgroundColor instanceof Color)
            {
                hoverBackgroundColor = WGColorHelper.getDarkerOrLighter((Color)backgroundColor);
            }
            else
            {
                hoverBackgroundColor = backgroundColor;
            }
        }
    }
    /**
     * This is the more advanced constructor, which allows for using an old theme as a palette to creating new themes
     * @param oldTheme The old theme from which to copy
     */
    public WGTheme(WGTheme oldTheme)
    {
        this.textPosition = oldTheme.getTextPosition();
        this.textXSizePercent = oldTheme.getTextXSizePercent();
        this.textYSizePercent = oldTheme.getTextYSizePercent();
        this.textFont = oldTheme.getTextFont();
        this.textStyle = oldTheme.getTextStyle();
        this.borderSize = oldTheme.getBorderSize();
        this.backgroundColor = oldTheme.getBackgroundColor();
        this.borderColor = oldTheme.getBorderColor();
        this.textColor = oldTheme.getTextColor();
        this.scrollBarColor = oldTheme.getScrollBarColor();
        this.cursorColor = oldTheme.getCursorColor();
        this.highlightColor = oldTheme.getHighlightColor();
        this.barColor = oldTheme.getBarColor();
        this.checkColor = oldTheme.getCheckColor();
        this.hoverBackgroundColor = oldTheme.getHoverBackgroundColor();
        this.focusedBackgroundColor = oldTheme.getFocusedBackgroundColor();
        this.gradientOrientationPreferences = oldTheme.getGradientOrientationPreferences();
    }
    
    //Methods:
    private Font toFont(Object obj)
    {
        if(obj != null)
        {
            return (Font)obj;
        }
        return null;
    }
    private int toInt(Object obj)
    {
        if(obj != null)
        {
            return (int)obj;
        }
        return 0;
    }
    private double toDouble(Object obj)
    {
        if(obj != null)
        {
            return (double)obj;
        }
        return 0;
    }
    private float toFloat(Object obj)
    {
        if(obj != null)
        {
            if(obj instanceof Float)
            {
                return (float)obj;
            }
            else if(obj instanceof Integer)
            {
                return (int)obj;
            }
        }
        return 0;
    }
    private Paint toPaint(Object obj)
    {
        if(obj != null)
        {
            return (Paint)obj;
        }
        return null;
    }
    
    
    //Setters:
    public void setBorderSize(float borderSize) {
        this.borderSize = borderSize;
    }

    public void setBackgroundColor(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Paint borderColor) {
        this.borderColor = borderColor;
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
    }

    public void setCheckColor(Paint checkColor) {
        this.checkColor = checkColor;
    }

    public void setCursorColor(Paint cursorColor) {
        this.cursorColor = cursorColor;
    }

    public void setHighlightColor(Paint highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setScrollBarColor(Paint scrollBarColor) {
        this.scrollBarColor = scrollBarColor;
    }

    public void setBarColor(Paint barColor) {
        this.barColor = barColor;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
    }

    public void setTextXSizePercent(double textXSizePercent) {
        this.textXSizePercent = textXSizePercent;
    }

    public void setTextYSizePercent(double textYSizePercent) {
        this.textYSizePercent = textYSizePercent;
    }

    public void setGradientOrientationPreferences(HashTable gradientOrientationPreferences) {
        this.gradientOrientationPreferences = gradientOrientationPreferences;
    }

    public void setHoverBackgroundColor(Paint hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public void setFocusedBackgroundColor(Paint focusedBackgroundColor) {
        this.focusedBackgroundColor = focusedBackgroundColor;
    }
    
    
    //Getters:
    public float getBorderSize() {
        return borderSize;
    }

    public Paint getBackgroundColor() {
        return backgroundColor;
    }

    public Paint getBorderColor() {
        return borderColor;
    }

    public Paint getTextColor() {
        return textColor;
    }

    public Paint getCheckColor() {
        return checkColor;
    }

    public Paint getCursorColor() {
        return cursorColor;
    }

    public Paint getHighlightColor() {
        return highlightColor;
    }

    public Paint getScrollBarColor() {
        return scrollBarColor;
    }

    public Paint getBarColor() {
        return barColor;
    }

    public int getTextStyle() {
        return textStyle;
    }

    public Font getTextFont() {
        return textFont;
    }

    public int getTextPosition() {
        return textPosition;
    }

    public double getTextXSizePercent() {
        return textXSizePercent;
    }

    public double getTextYSizePercent() {
        return textYSizePercent;
    }

    public HashTable getGradientOrientationPreferences() {
        return gradientOrientationPreferences;
    }

    public Paint getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public Paint getFocusedBackgroundColor() {
        return focusedBackgroundColor;
    }
}
