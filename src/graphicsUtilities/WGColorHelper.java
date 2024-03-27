/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Color;
import utilities.FileProcessor;

/**
 *
 * @author Westley
 * A static class that defines the basic components of working with colors, such as inversing them, or graying them out
 */
public class WGColorHelper
{
    private static final int RGBAMAX = 255;
    public static final int PREVENT_COLOR_OVERFLOW = 0;
    public static final int FLIP_ON_COLOR_OVERFLOW = 1;
    public static final int BOUNCE_ON_COLOR_OVERFLOW = 2;
    public static Color getInverseColor(Color originalColor)
    {
        Color newColor = new Color(RGBAMAX - originalColor.getRed(), RGBAMAX - originalColor.getGreen(), RGBAMAX - originalColor.getBlue());
        return newColor;
    }
    public static Color getGrayScaleColor(Color originalColor)
    {
        int average = (originalColor.getRed() + originalColor.getGreen() + originalColor.getBlue()) /3;
        Color newColor =  new Color(average, average, average);
        return newColor;
    }
    public static Color addToColor(Color originalColor, int red, int green, int blue, int alpha, int overflowColorBehavior)
    {
        Color newColor = new Color(fixOutOfBoundColor(originalColor.getRed() + red, overflowColorBehavior), fixOutOfBoundColor(originalColor.getGreen() + green, overflowColorBehavior), fixOutOfBoundColor(originalColor.getBlue() + blue, overflowColorBehavior), fixOutOfBoundColor(originalColor.getAlpha() + alpha, overflowColorBehavior));
        return newColor;
    }
    public static Color createColor(String colorData)
    {
        Color newColor = Color.black;
        String temp[] = colorData.split(",");
        if(temp.length == 3)
        {
            int red = FileProcessor.toInt(temp[0]);
            int blue = FileProcessor.toInt(temp[1]);
            int green = FileProcessor.toInt(temp[2]);
            if(red >= 0 && red <= 255 && blue >= 0 && blue <= 255 && green >= 0 && green <= 255)
            {
                newColor = new Color(red, blue, green);
            }
        }
        return newColor;
    }
    public static Color combineTwoColors(Color color1, double c1Percentage, Color color2, double c2Percentage)
    {
        Color combinedColor;
        int colorRed = (int)((color1.getRed() * c1Percentage) + (color2.getRed() * c2Percentage));
        int colorGreen = (int)((color1.getGreen() * c1Percentage) + (color2.getGreen() * c2Percentage));
        int colorBlue = (int)((color1.getBlue() * c1Percentage) + (color2.getBlue() * c2Percentage));
        int alpha = (int)((color1.getAlpha() * c1Percentage) + (color2.getAlpha() * c2Percentage));
        colorRed = putColorIntInRange(colorRed);
        colorGreen = putColorIntInRange(colorGreen);
        colorBlue = putColorIntInRange(colorBlue);
        alpha = putColorIntInRange(alpha);
        combinedColor = new Color(colorRed, colorGreen, colorBlue, alpha);
        return combinedColor;
    }
    public static int fixOutOfBoundColor(int colorInt, int behavior)
    {
        switch(behavior)
        {
            case BOUNCE_ON_COLOR_OVERFLOW:
                return bounceOutOfBoundColor(colorInt);
            case FLIP_ON_COLOR_OVERFLOW:
                return flipOutOfBoundColor(colorInt);
            default:
                return putColorIntInRange(colorInt);
        }
    }
    public static int putColorIntInRange(int colorInt)
    {
        int color = colorInt;
        if(color > 255)
        {
            color = 255;
        }
        else if(color < 0)
        {
            color = 0;
        }
        return color;
    }
    public static int flipOutOfBoundColor(int colorInt)
    {
        int color = colorInt;
        while(color > 255)
        {
            color -= 255;
        }
        while(color < 0)
        {
            color += 255;
        }
        return color;
    }
    public static int bounceOutOfBoundColor(int colorInt)
    {
        int color = colorInt;
        while(color > 255 || color < 0)
        {
            if(color > 255)
            {
                color = 255 - (color - 255);
            }
            if(color < 0)
            {
                color = -color;
            }
        }
        return color;
    }
    public static boolean willBounce(Color color, int redAdd, int greenAdd, int blueAdd, int alphaAdd)
    {
        int redComp = color.getRed() + redAdd;
        if(redComp > 255 || redComp < 0)
        {
            return true;
        }
        int greenComp = color.getGreen() + greenAdd;
        if(greenComp > 255 || greenComp < 0)
        {
            return true;
        }
        int blueComp = color.getBlue() + blueAdd;
        if(blueComp > 255 || blueComp < 0)
        {
            return true;
        }
        int alphaComp = color.getAlpha() + alphaAdd;
        if(alphaComp > 255 || alphaComp < 0)
        {
            return true;
        }
        return false;
    }
    protected WGColorHelper(){}
}
