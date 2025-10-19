/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package graphicsUtilities;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import utilities.FXFontMetrics;

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
    public static Font getFittedFontForBox(Font originalFont, Canvas parent, double boxWidth, double boxHeight, String text, int maxSize)
    {
        int minimumSize = 1;
        int maximumSize = maxSize;
        int currentSize;
        Font newFont;
        while(true)
        {
            currentSize = (int)((minimumSize + maximumSize) /2.0);
            newFont = generateFont(originalFont, currentSize);
            FXFontMetrics testFM = new FXFontMetrics(newFont);
            if(testFM.getHeight(text) >= boxHeight || testFM.stringWidth(text) >= boxWidth)
            {
                maximumSize = currentSize;
            }
            else if(testFM.getHeight(text) < boxHeight || testFM.stringWidth(text) < boxWidth)
            {
                minimumSize = currentSize;
            }
            if(minimumSize == maximumSize)
            {
                break;
            }
            else if(minimumSize+1 == maximumSize)
            {
                newFont = generateFont(originalFont, minimumSize);
                break;
            }
        }
        return newFont;
    }
    /**
     * This function returns a new Font that is the same as the original font given, but it can now fit the box that is created with the width variables given. 
     * Essentially this means that you will get a font that can 100% fit into a box of the given size no matter where it is on the screen (As long as you use this font and not the old one)
     * This is an exact formula, therefore it will take longer than the original one that used approximations 
     * @param originalFont The original font that determines the fontName and fontStyle (see java.awt.font for more information)
     * @param parent The component that the text is being drawn on. This is use to create FontMetrics that allow for the font to fit in the box
     * @param boxWidth The width of the box that this font is going into
     * @param text The text that is being drawn in the box
     * @param maxSize The absolute maximum size of the text, this is used to have a starting point to determine if the font will fit or not.
     * @return
     */
    public static Font getFittedFontForWidth(Font originalFont, Canvas parent, double boxWidth, String text, int maxSize)
    {
        int minimumSize = 1;
        int maximumSize = maxSize;
        int currentSize;
        Font newFont;
        while(true)
        {
            currentSize = (int)((minimumSize + maximumSize) /2.0);
            newFont = generateFont(originalFont, currentSize);
            FXFontMetrics testFM = new FXFontMetrics(newFont);
            if(testFM.stringWidth(text) >= boxWidth)
            {
                maximumSize = currentSize;
            }
            else if(testFM.stringWidth(text) < boxWidth)
            {
                minimumSize = currentSize;
            }
            if(minimumSize == maximumSize)
            {
                break;
            }
            else if(minimumSize+1 == maximumSize)
            {
                newFont = generateFont(originalFont, minimumSize);
                break;
            }
        }
        return newFont;
    }
    /**
     * This function returns a new Font that is the same as the original font given, but it can now fit the box that is created with the height variables given. 
     * Essentially this means that you will get a font that can 100% fit into a box of the given size no matter where it is on the screen (As long as you use this font and not the old one)
     * This is an exact formula, therefore it will take longer than the original one that used approximations 
     * @param originalFont The original font that determines the fontName and fontStyle (see java.awt.font for more information)
     * @param parent The component that the text is being drawn on. This is use to create FontMetrics that allow for the font to fit in the box
     * @param boxHeight The height of the box that this font is going into
     * @param text The text that is being drawn in the box
     * @param maxSize The absolute maximum size of the text, this is used to have a starting point to determine if the font will fit or not.
     * @return
     */
    public static Font getFittedFontForHeight(Font originalFont, Canvas parent, double boxHeight, String text, int maxSize)
    {
        int minimumSize = 1;
        int maximumSize = maxSize;
        int currentSize;
        Font newFont;
        while(true)
        {
            currentSize = (int)((minimumSize + maximumSize) /2.0);
            newFont = generateFont(originalFont, currentSize);
            FXFontMetrics testFM = new FXFontMetrics(newFont);
            if(testFM.getHeight(text) >= boxHeight)
            {
                maximumSize = currentSize;
            }
            else if(testFM.getHeight(text) < boxHeight)
            {
                minimumSize = currentSize;
            }
            if(minimumSize == maximumSize)
            {
                break;
            }
            else if(minimumSize+1 == maximumSize)
            {
                newFont = generateFont(originalFont, minimumSize);
                break;
            }
        }
        return newFont;
    }
    public static ArrayList<String> wrapText(ArrayList<String> currentList, double objectWidth, Font usedFont, Canvas parent)
    {
        ArrayList<String> newList = new ArrayList<String>(currentList.size());
        FXFontMetrics textFM = new FXFontMetrics(usedFont);
        //First copy over the strings:
        for(int i = 0 ; i < currentList.size() ; i++)
        {
            newList.add(currentList.get(i));
        }
        //This requires that each line is split if too long:
        for(int i = 0 ; i < newList.size() ; i++)
        {
            String str = newList.get(i);
            double textLength = textFM.stringWidth(str);
            if(textLength > objectWidth) //There is a problem, the string is too long, now wrap it!
            {
                //Now determine where in the string is the best location to split:
                //Search Binarially:
                int minimumSize = 0;
                int maximumSize = str.length();
                int sizeSplit = str.length();
                while(true)
                {
                    int currentSize = (int)((minimumSize + maximumSize) /2.0);
                    String test = str.substring(0, currentSize);
                    double testLength = textFM.stringWidth(test);
                    if(testLength >= objectWidth)
                    {
                        maximumSize = currentSize;
                    }
                    else if(testLength < objectWidth)
                    {
                        minimumSize = currentSize;
                    }
                    if(minimumSize == maximumSize)
                    {
                        sizeSplit = minimumSize;
                        break;
                    }
                    else if(minimumSize+1 == maximumSize) //Make sure to use the correct one:
                    {
                        test = str.substring(0, maximumSize);
                        testLength = textFM.stringWidth(test);
                        if(testLength >= objectWidth)
                        {
                            sizeSplit = minimumSize;
                        }
                        if(testLength < objectWidth)
                        {
                            sizeSplit = maximumSize;
                        }
                        break;
                    }
                }
                //Now Do stuff:
                String thisLine = str.substring(0, sizeSplit+1);
                String newLine = str.substring(sizeSplit+1);
                //Now try to keep words together:
                if(thisLine.contains(" "))
                {
                    //Split at the space instead if can:
                    sizeSplit = thisLine.lastIndexOf(" ");
                    thisLine = str.substring(0, sizeSplit+1);
                    newLine = str.substring(sizeSplit+1);
                }
                newList.set(i, thisLine);
                newList.add(i+1, newLine.strip());
            }
        }
        return newList;
    }
    /**
     * Generates a new font based on the last one, which keeps all settings except the size, which is set to the given size
     * @param font Original font that the new font is based on
     * @param newSize The new size for the new font
     * @return Font The new font
     */
    public static Font generateFont(Font font, int newSize)
    {
        String[] fontParts = font.getStyle().split(" ");
        FontPosture posture = FontPosture.REGULAR;
        if(fontParts.length == 2)
        {
        	posture = FontPosture.findByName(fontParts[1].toUpperCase());
        }
        return Font.font(font.getName(), FontWeight.findByName(fontParts[0].toUpperCase()), posture, newSize);
    }
    /**
     * Generates a new font based on the last one, which keeps all settings except the font name, which is set to the given name
     * @param font The original font, whose settings are used
     * @param name The new name for the font. (Think like Arial, or Dialog)
     * @return Font The new Font
     */
    public static Font generateFont(Font font, String name)
    {
        String[] fontParts = font.getStyle().split(" ");
        FontPosture posture = FontPosture.REGULAR;
        if(fontParts.length == 2)
        {
        	posture = FontPosture.findByName(fontParts[1].toUpperCase());
        }
        return Font.font(name, FontWeight.findByName(fontParts[0].toUpperCase()), posture, font.getSize());
    }
    protected WGFontHelper(){}
}
