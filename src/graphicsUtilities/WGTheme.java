/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
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
    private float borderSize;
    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;
    private Color checkColor;
    private Color cursorColor;
    private Color highlightColor;
    private Color scrollBarColor;
    private Color barColor;
    private Font textFont;
    public WGTheme(float borderSize, int textStyle, Color backgroundColor, Color borderColor, Color textColor, Color scrollBarColor, Color cursorColor, Color highlightColor, Color barColor, Color checkColor, Font textFont)
    {
        this.textFont = textFont;
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

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setCheckColor(Color checkColor) {
        this.checkColor = checkColor;
    }

    public void setCursorColor(Color cursorColor) {
        this.cursorColor = cursorColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setScrollBarColor(Color scrollBarColor) {
        this.scrollBarColor = scrollBarColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }
    
    
    //Getters:
    public float getBorderSize() {
        return borderSize;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getCheckColor() {
        return checkColor;
    }

    public Color getCursorColor() {
        return cursorColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public Color getScrollBarColor() {
        return scrollBarColor;
    }

    public Color getBarColor() {
        return barColor;
    }

    public int getTextStyle() {
        return textStyle;
    }

    public Font getTextFont() {
        return textFont;
    }
}
