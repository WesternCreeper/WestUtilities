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
     * The idea behind this is that a font of X size should be scalable to any other size. Thus if we know about how much the font changes every increase in font size we could multiply by that an add it to the base amount
     * This will work for every single font size 1-1000 however 972 of those fonts will be over-approximations. Anything larger than 1000 may be an extreme over-approximation. However who uses a font that big?
     * @param wantedFontSize The size of the font you want
     * @return The size of the font, as in the height it will take up approximately
     */
    public static double getFontHeightApproximation(int wantedFontSize)
    {
        double slopeFit = 9.0/7; 
        double slopeYInt = 1.3; 
        double size = (slopeFit * wantedFontSize) + slopeYInt;
        return size;
    }
    /**
     * This function is just the inverse of the fontHeightApproximation,as it most often is we know the height and the font size is unknown, this will solve for that value. However not every height will work and any non-positive answers will automatically become zero
     * @param boxHeight The height of whatever you want the font to fit into. In other words this will find the font that can fit inside of the height, approximately
     * @return The font size in double precision
     */
    public static double getApproximateMaximumFontSizeHeight(double boxHeight)
    {
        double slopeFit = 7.0/9;
        double slopeYInt = 1.3;
        double size = slopeFit * (boxHeight - slopeYInt);
        if(size < 0)
        {
            size = 0;
        }
        return size;
    }
    /**
     * The idea behind this is that a font of X size should be scalable to any other size. Thus if we know about how much the font changes every increase in font size we could multiply by that an add it to the base amount
     * This will work for every single font size 1-1000 however 993 of those fonts will be over-approximations. Anything larger than 1000 may be an extreme over-approximation. However who uses a font that big?
     * @param wantedFontSize The size of the font you want
     * @param stringWidth The amount of characters that are in the string, this will scale the approximation
     * @return The approximate width that the font will take up
     */
    public static double getFontWidthApproximation(int wantedFontSize, int stringWidth)
    {
        double slopeFit = 0.5; 
        double slopeYInt = 0.7; 
        double size = (slopeFit * wantedFontSize) + slopeYInt;
        return size * stringWidth;
    }
    /**
     * This function is just the inverse of the fontWidthApproximation, as it most often is we know the width and the font size is unknown, this will solve for that value. However not every width will work and any non-positive answers will automatically become zero
     * @param boxWidth The width of whatever you want the font to fit into. In other words this will find the font that can fit inside of the width, approximately
     * @param stringWidth The amount of characters that are in the string, this will inversely scale the approximation. Essentially the longer the text, the smaller of a font to fit inside the box
     * @return The font size in double precision
     */
    public static double getApproximateMaximumFontSizeWidth(double boxWidth, int stringWidth)
    {
        double slopeFit = 0.5;
        double slopeYInt = 0.7;
        double size = ((boxWidth / stringWidth) - slopeYInt) / slopeFit;
        if(size < 0)
        {
            size = 0;
        }
        return size;
    }
    /**
     * This function returns a new Font that is the same as the original font given, but it can now fit the box that is created with the height and width variables given. 
     * Essentially this means that you will get a font that can 100% fit into a box of the given size no matter where it is on the screen (As long as you use this font and not the old one)
     * @param originalFont The original font that determines the fontName and fontStyle (see java.awt.font for more information)
     * @param boxWidth The width of the box that this font is going into
     * @param boxHeight The height of the box that this font is going into
     * @param stringWidth The width of the text that uses this font. Generally a good idea to use the longest length if many different sources use the same font
     * @return
     */
    public static Font getFittedFontForBox(Font originalFont, double boxWidth, double boxHeight, int stringWidth)
    {
        //Find out what sizes will fit in the box
        double widthSizeFit = getApproximateMaximumFontSizeWidth(boxWidth, stringWidth);
        double heightSizeFit = getApproximateMaximumFontSizeHeight(boxHeight);
        
        //Now decide which is smaller and that is going to be the new font size:
        double fittedFontSize = widthSizeFit;
        if(heightSizeFit < widthSizeFit)
        {
            fittedFontSize = heightSizeFit;
        }
        
        //Now return the new font keeping all of the rest of the user picked options, but with the fitted font size
        Font newFont  = new Font(originalFont.getName(), originalFont.getStyle(), (int)fittedFontSize);
        return newFont;
    }
}
