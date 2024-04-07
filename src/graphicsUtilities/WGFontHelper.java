/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package graphicsUtilities;

import java.awt.Font;
import java.awt.Component;
import java.awt.FontMetrics;

/**
 *
 * @author Westley
 */
public class WGFontHelper 
{
    /**
     * This function returns a new Font that is the same as the original font given, but it can now fit the box that is created with the height and width variables given. 
     * Essentially this means that you will get a font that can 100% fit into a box of the given size no matter where it is on the screen (As long as you use this font and not the old one)
     * This is an exact formula, therefore it will take longer than the original one that used approximations 
     * @param originalFont The original font that determines the fontName and fontStyle (see java.awt.font for more information)
     * @param parent The component that the text is being drawn on. This is use to create FontMetrics that allow for the font to fit in the box
     * @param boxWidth The width of the box that this font is going into
     * @param boxHeight The height of the box that this font is going into
     * @param text The text that is being drawn in the box
     * @param maxSize The absolute maximum size of the text, this is used to have a starting point to determine if the font will fit or not.
     * @return
     */
    public static Font getFittedFontForBox(Font originalFont, Component parent, double boxWidth, double boxHeight, String text, int maxSize)
    {
        int minimumSize = 1;
        int maximumSize = maxSize;
        int currentSize;
        Font newFont;
        while(true)
        {
            currentSize = (int)((minimumSize + maximumSize) /2.0);
            newFont = new Font(originalFont.getFontName(), originalFont.getStyle(), currentSize);
            FontMetrics testFM = parent.getFontMetrics(newFont);
            if(testFM.getHeight() > boxHeight || testFM.stringWidth(text) > boxWidth)
            {
                maximumSize = currentSize;
            }
            else if(testFM.getHeight() < boxHeight || testFM.stringWidth(text) < boxWidth)
            {
                minimumSize = currentSize;
            }
            if(minimumSize == maximumSize)
            {
                break;
            }
            else if(minimumSize+1 == maximumSize)
            {
                newFont = new Font(originalFont.getFontName(), originalFont.getStyle(), minimumSize);
                break;
            }
        }
        return newFont;
    }
    protected WGFontHelper(){}
}
