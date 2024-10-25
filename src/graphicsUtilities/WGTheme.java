/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

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
    private int textStyle;
    private int textPosition;
    private double textXSizePercent;
    private double textYSizePercent;
    private float borderSize;
    private Paint backgroundColor;
    private Paint borderColor;
    private Paint textColor;
    private Paint checkColor;
    private Paint cursorColor;
    private Paint highlightColor;
    private Paint scrollBarColor;
    private Paint barColor;
    private Font textFont;
    public WGTheme(float borderSize, int textStyle, int textPosition, double textXSizePercent, double textYSizePercent, Paint backgroundColor, Paint borderColor, Paint textColor, Paint scrollBarColor, Paint cursorColor, Paint highlightColor, Paint barColor, Paint checkColor, Font textFont)
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
    }
    /**
     * This is the more advanced constructor, which allows for using an old theme as a palette to creating new themes
     * @param oldTheme The old theme from which to copy
     */
    public WGTheme(WGTheme oldTheme)
    {
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
}
