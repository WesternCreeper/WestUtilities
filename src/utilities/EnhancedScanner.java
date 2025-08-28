/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Westley
 */
public class EnhancedScanner
{
    private Scanner sc;
    public EnhancedScanner(File file) throws FileNotFoundException
    {
        sc = new Scanner(file);
    }
    public EnhancedScanner(File file, String delimiter) throws FileNotFoundException
    {
        sc = new Scanner(file);
        sc.useDelimiter(delimiter);
    }
    public String next()
    {
        return sc.next();
    }
    public String nextLine()
    {
        return sc.nextLine();
    }
    public String nextLine(boolean removeComments)
    {
        if(removeComments)
        {
            return removeComments(sc.nextLine());
        }
        return sc.nextLine();
    }
    public boolean hasNext()
    {
        return sc.hasNext();
    }
    public boolean hasNextLine()
    {
        return sc.hasNextLine();
    }
    public String removeComments(String line)
    {
        int commentIndex = line.indexOf("//");
        return line.substring(0, commentIndex);
    }
    /**
     * This closes the Enhanced Scanner, just like a regular scanner so make sure to ONLY call this when you no longer need to use the Enhanced Scanner
     */
    public void close()
    {
        sc.close();
    }
    /**
     * This finds the section that is named scetionName. This sets the location of the scanner to the location of this section
     * @param sectionName The name of the section to be found
     */
    public void findSection(String sectionName)
    {
        if(sectionName.length() == 0)
        {
            return;
        }
        while(sc.hasNextLine())
        {
            String line = sc.nextLine();
            if(isSection(line) && line.length() >= 3) //Minimum length of a section is one character plus the square brackets
            {
                //This is a section so now verify that it is the section we want:
                line = line.strip().substring(1);
                //Now pop off the last part:
                line = line.substring(0, line.length()-1);
                if(line.equals(sectionName))
                {
                    //Now exit the loop, we have arrived at our destination:
                    break;
                }
            }
        }
    }
    /**
     * A section is defined as so: [Section Name], anything on one line that is surrounded by square brackets
     * @param line The line of text from a file. This is to be tested for being a section
     * @return If this line is a section
     */
    public static boolean isSection(String line)
    {
        //To be a section it needs to start and end with square brackets:
        if(line.contains("[") && line.contains("]"))
        {
            //Make sure that "[" is at the start and "]" is at the end:
            String temp = line.strip();
            if(temp.charAt(0) == '[' && temp.charAt(temp.length()-1) == ']')
            {
                return true;
            }
        }
        return false;
    }
}
