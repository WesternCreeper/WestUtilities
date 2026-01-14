/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.scene.paint.Color;
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
    public static final int LINEAR_SEARCH_COLOR_OVERFLOW = 3;
    public static final boolean PREFERRANCE_COLOR_LIGHTER = true;
    public static final boolean PREFERRANCE_COLOR_DARKER = false;
    private static final int DARKER_CUT_OFF = 25;
    public static Color getInverseColor(Color originalColor)
    {
        Color newColor = new Color(1 - originalColor.getRed(), 1 - originalColor.getGreen(), 1 - originalColor.getBlue(), originalColor.getOpacity());
        return newColor;
    }
    public static Color getGrayScaleColor(Color originalColor)
    {
        int average = (int)(RGBAMAX * (originalColor.getRed() + originalColor.getGreen() + originalColor.getBlue()) /3);
        Color newColor =  Color.rgb(average, average, average);
        return newColor;
    }
    public static Color addToColor(Color originalColor, int red, int green, int blue, double alpha, int overflowColorBehavior)
    {
        Color newColor;
        if(overflowColorBehavior != LINEAR_SEARCH_COLOR_OVERFLOW)
        {
            newColor = Color.rgb(fixOutOfBoundColor((int)(RGBAMAX * originalColor.getRed()) + red, overflowColorBehavior), fixOutOfBoundColor((int)(RGBAMAX * originalColor.getGreen()) + green, overflowColorBehavior), fixOutOfBoundColor((int)(RGBAMAX * originalColor.getBlue()) + blue, overflowColorBehavior), fixOutOfBoundColor(originalColor.getOpacity() + alpha, overflowColorBehavior));
        }
        else
        {
            double currentAlpha = originalColor.getOpacity();
            int currentBlue = (int)(RGBAMAX * originalColor.getBlue());
            int currentGreen = (int)(RGBAMAX * originalColor.getGreen());
            int currentRed = (int)(RGBAMAX * originalColor.getRed());
            if(currentAlpha + alpha > 1)
            {
                currentAlpha = (currentAlpha + alpha) - 1;
                currentBlue++;
            }
            if(currentBlue + blue > 255)
            {
                currentBlue = (currentBlue + blue) - 255;
                currentGreen++;
            }
            if(currentGreen + green > 255)
            {
                currentGreen = (currentGreen + green) - 255;
                currentRed++;
            }
            newColor = Color.rgb(fixOutOfBoundColor(currentRed + red, PREVENT_COLOR_OVERFLOW), fixOutOfBoundColor(currentGreen, PREVENT_COLOR_OVERFLOW), fixOutOfBoundColor(currentBlue, PREVENT_COLOR_OVERFLOW), fixOutOfBoundColor(currentAlpha, PREVENT_COLOR_OVERFLOW));
        }
        
        return newColor;
    }
    public static Color createColor(String colorData)
    {
        Color newColor = Color.BLACK;
        String temp[] = colorData.split(",");
        if(temp.length >= 3)
        {
            int red = FileProcessor.toInt(temp[0]);
            int green = FileProcessor.toInt(temp[1]);
            int blue = FileProcessor.toInt(temp[2]);
            int alpha = 255;
            if(temp.length >= 4)
            {
                alpha = FileProcessor.toInt(temp[3]);
            }
            newColor = Color.rgb(putColorIntInRange(red), putColorIntInRange(green), putColorIntInRange(blue), putColorIntInRange(alpha / 255.0));
        }
        return newColor;
    }
    public static String colorToString(Color color)
    {
        String text = "0, 0, 0";
        if(color != null)
        {
            text = (int)(RGBAMAX * color.getRed()) + ", " + (int)(RGBAMAX * color.getGreen()) + ", " + (int)(RGBAMAX * color.getBlue()) + ", " + (int)(RGBAMAX * color.getOpacity());
        }
        return text;
    }
    public static Color combineTwoColors(Color color1, double c1Percentage, Color color2, double c2Percentage)
    {
        Color combinedColor;
        int colorRed = (int)(((int)(RGBAMAX * color1.getRed()) * c1Percentage) + ((int)(RGBAMAX * color2.getRed()) * c2Percentage));
        int colorGreen = (int)(((int)(RGBAMAX * color1.getGreen()) * c1Percentage) + ((int)(RGBAMAX * color2.getGreen()) * c2Percentage));
        int colorBlue = (int)(((int)(RGBAMAX * color1.getBlue()) * c1Percentage) + ((int)(RGBAMAX * color2.getBlue()) * c2Percentage));
        double alpha = ((color1.getOpacity() * c1Percentage) + (color2.getOpacity() * c2Percentage));
        colorRed = putColorIntInRange(colorRed);
        colorGreen = putColorIntInRange(colorGreen);
        colorBlue = putColorIntInRange(colorBlue);
        alpha = putColorIntInRange(alpha);
        combinedColor = Color.rgb(colorRed, colorGreen, colorBlue, alpha);
        return combinedColor;
    }
    /**
     * This will return the most sensible of the two options, for instance when given black this function is most definitely not going to give the darker version of that, because that is the same as the original color given
     * @param color The Color to be darkened or lightened based on the characteristics of the color
     * @return 
     */
    public static Color getDarkerOrLighter(Color color)
    {
        //Assume darker is ok
        Color newColor = color.darker();
        int red = (int)(RGBAMAX * color.getRed());
        int green = (int)(RGBAMAX * color.getGreen());
        int blue = (int)(RGBAMAX * color.getBlue());
        if(red < DARKER_CUT_OFF && green < DARKER_CUT_OFF && blue < DARKER_CUT_OFF) //Test for if it is not
        {
            newColor = color.brighter();
        }
        return newColor;
    }
    /**
     * This is the same as the one parameter version, just with a number of times application
     * @param color The Color to be darkened or lightened based on the characteristics of the color
     * @param numTimes The number of times the darkening or lightening will occur
     * @param preferance The preferred action. Lighter or Darker
     * @return 
     */
    public static Color getDarkerOrLighter(Color color, int numTimes, boolean preferance)
    {
        Color newColor = color;
        if(!preferance)
        {
            int darkerCutOff = DARKER_CUT_OFF * numTimes;
            //Assume darker is ok
            for(int i = 0 ; i < numTimes ; i++)
            {
                newColor = newColor.darker();
            }
            int red = (int)(RGBAMAX * color.getRed());
            int green = (int)(RGBAMAX * color.getGreen());
            int blue = (int)(RGBAMAX * color.getBlue());
            if(red < darkerCutOff && green < darkerCutOff && blue < darkerCutOff) //Test for if it is not
            {
                newColor = color;
                for(int i = 0 ; i < numTimes ; i++)
                {
                    newColor = newColor.brighter();
                }
            }
        }
        else
        {
            int lighterCutOff = 255 - (DARKER_CUT_OFF * numTimes);
            //Assume darker is ok
            for(int i = 0 ; i < numTimes ; i++)
            {
                newColor = newColor.brighter();
            }
            int red = (int)(RGBAMAX * color.getRed());
            int green = (int)(RGBAMAX * color.getGreen());
            int blue = (int)(RGBAMAX * color.getBlue());
            if(red > lighterCutOff && green > lighterCutOff && blue > lighterCutOff) //Test for if it is not
            {
                newColor = color;
                for(int i = 0 ; i < numTimes ; i++)
                {
                    newColor = newColor.darker();
                }
            }
        }
        return newColor;
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
    public static double fixOutOfBoundColor(double colorInt, int behavior)
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
    public static double putColorIntInRange(double colorInt)
    {
        double color = colorInt;
        if(color > 1.0)
        {
            color = 1.0;
        }
        else if(color < 0.0)
        {
            color = 0.0;
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
    public static double flipOutOfBoundColor(double colorInt)
    {
        double color = colorInt;
        while(color > 1.0)
        {
            color -= 1.0;
        }
        while(color < 0)
        {
            color += 1.0;
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
    public static double bounceOutOfBoundColor(double colorInt)
    {
        double color = colorInt;
        while(color > 1.0 || color < 0)
        {
            if(color > 1.0)
            {
                color = 1.0 - (color - 1.0);
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
        int redComp = (int)(RGBAMAX * color.getRed()) + redAdd;
        if(redComp > 255 || redComp < 0)
        {
            return true;
        }
        int greenComp = (int)(RGBAMAX * color.getGreen()) + greenAdd;
        if(greenComp > 255 || greenComp < 0)
        {
            return true;
        }
        int blueComp = (int)(RGBAMAX * color.getBlue()) + blueAdd;
        if(blueComp > 255 || blueComp < 0)
        {
            return true;
        }
        int alphaComp = (int)(RGBAMAX * color.getOpacity()) + alphaAdd;
        if(alphaComp > 255 || alphaComp < 0)
        {
            return true;
        }
        return false;
    }
    /**
     * Compares two colors based on their RGBA sequence
     * @param firstColor The first Color
     * @param secondColor The second Color
     * @return True if the firstColor is larger than the secondColor. Essentially, compares the red, then the green, then the blue, then the alpha. At each comparison if one is larger than the other, then it returns the larger one
     */
    public static boolean isColorGreaterThanColor(Color firstColor, Color secondColor)
    {
        int[] color1 = {(int)(RGBAMAX * firstColor.getRed()), (int)(RGBAMAX * firstColor.getGreen()), (int)(RGBAMAX * firstColor.getBlue()), (int)(RGBAMAX * firstColor.getOpacity())};
        int[] color2 = {(int)(RGBAMAX * secondColor.getRed()), (int)(RGBAMAX * secondColor.getGreen()), (int)(RGBAMAX * secondColor.getBlue()), (int)(RGBAMAX * secondColor.getOpacity())};
        for(int i = 0 ; i < color1.length ; i++)
        {
            if(color1[i] > color2[i])
            {
                return true;
            }
            else if(color1[i] < color2[i])
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Compares two colors based on their RGBA sequence
     * @param firstColor The first Color
     * @param secondColor The second Color
     * @return True if these two colors are equivalent
     */
    public static boolean isColorEqualToColor(Color firstColor, Color secondColor)
    {
        int[] color1 = {(int)(RGBAMAX * firstColor.getRed()), (int)(RGBAMAX * firstColor.getGreen()), (int)(RGBAMAX * firstColor.getBlue()), (int)(RGBAMAX * firstColor.getOpacity())};
        int[] color2 = {(int)(RGBAMAX * secondColor.getRed()), (int)(RGBAMAX * secondColor.getGreen()), (int)(RGBAMAX * secondColor.getBlue()), (int)(RGBAMAX * secondColor.getOpacity())};
        for(int i = 0 ; i < color1.length ; i++)
        {
            if(color1[i] != color2[i])
            {
                return false;
            }
        }
        return true;
    }
    public static Color shade(Color originalColor, double brightness)
    {
    	int red = (int)(RGBAMAX * originalColor.getRed() * brightness);
    	int green = (int)(RGBAMAX * originalColor.getGreen() * brightness);
    	int blue = (int)(RGBAMAX * originalColor.getBlue() * brightness);
    	
    	return  Color.rgb(putColorIntInRange(red), putColorIntInRange(green), putColorIntInRange(blue), (int)(RGBAMAX * originalColor.getOpacity()));
    }
    public static Color changeTransparency(Color originalColor, double newTransparency)
    {
    	int red = (int)(RGBAMAX * originalColor.getRed());
    	int green = (int)(RGBAMAX * originalColor.getGreen());
    	int blue = (int)(RGBAMAX * originalColor.getBlue());
    	
    	return  Color.rgb(putColorIntInRange(red), putColorIntInRange(green), putColorIntInRange(blue), newTransparency);
    }
    protected WGColorHelper(){}
}
