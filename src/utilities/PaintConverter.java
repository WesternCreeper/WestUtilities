/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.io.File;
import java.util.Scanner;
import java.awt.*;
import java.awt.geom.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Westley
 */
public class PaintConverter 
{
    public static PaintConverter add(PaintConverter PaintConverter1, PaintConverter PaintConverter2)
    {
        //Make sure that they are calibrated the same
        if(PaintConverter1.getParent().equals(PaintConverter2.getParent()))
        {
            PaintConverter result = new PaintConverter(PaintConverter1.getParent());
            //Jobs adding
            ArrayList<ArrayList<PaintJob>> resultJobs = result.getAllJobs();
            //Get them
            ArrayList<ArrayList<PaintJob>> allJobs1 = PaintConverter1.getAllJobs();
            ArrayList<ArrayList<PaintJob>> allJobs2 = PaintConverter2.getAllJobs();
            //Add them together
            for(int i = 0 ; i < allJobs1.size() ; i++)
            {
                resultJobs.add(allJobs1.get(i));
            }
            for(int i = 0 ; i < allJobs2.size() ; i++)
            {
                resultJobs.add(allJobs2.get(i));
            }
            
            //Names adding:
            ArrayList<String> resultNames = result.getAllNames();
            //Get them
            ArrayList<String> allNames1 = PaintConverter1.getAllNames();
            ArrayList<String> allNames2 = PaintConverter2.getAllNames();
            //Add them together
            for(int i = 0 ; i < allNames1.size() ; i++)
            {
                resultNames.add(allNames1.get(i));
            }
            for(int i = 0 ; i < allNames2.size() ; i++)
            {
                resultNames.add(allNames2.get(i));
            }
            alphabetizeJobsAndNames(result.getAllNames(), result.getAllJobs());
            
            return result;
        }
        return PaintConverter1;
    }
    public static void alphabetizeJobsAndNames(ArrayList<String> names, ArrayList<ArrayList<PaintJob>> jobs)
    {
        //Do it based on where the names and jobs are:
        for(int i = 0 ; i < names.size()-1 ; i++)
        {
            for(int j = i+1 ; j < names.size() ; j++)
            {
                if(names.get(i).compareToIgnoreCase(names.get(j)) > 0)
                {
                    //Swap the names and the arrays
                    String temp = names.get(i);
                    names.set(i, names.get(j));
                    names.set(j, temp);
                    
                    ArrayList<PaintJob> temp2 = jobs.get(i);
                    jobs.set(i, jobs.get(j));
                    jobs.set(j, temp2);
                }
            }
        }
    }
    
    private ArrayList<String> names = new ArrayList<String>(1);
    private ArrayList<ArrayList<PaintJob>> jobs = new ArrayList<ArrayList<PaintJob>>(1);
    private Component parent = null;
    public PaintConverter(Component par)
    {
        parent = par;
    }
    public PaintConverter(String fileName, Component par) throws FileNotFoundException
    {
        parent = par;
        readPaints(fileName, 0, 1, 1);
        alphabetizeJobsAndNames(names, jobs);
    }
    public PaintConverter(String fileName, Component par, String section) throws FileNotFoundException
    {
        parent = par;
        FileProcessor fil = new FileProcessor(fileName);
        int index = fil.findSectionIndex(section);
        readPaints(fileName, index,1, 1);
        alphabetizeJobsAndNames(names, jobs);
    }
    public PaintConverter(String fileName, Component par, String section, double xScale, double yScale) throws FileNotFoundException
    {
        parent = par;
        FileProcessor fil = new FileProcessor(fileName);
        int index = fil.findSectionIndex(section);
        readPaints(fileName, index, xScale, yScale);
        alphabetizeJobsAndNames(names, jobs);
    }
    public PaintConverter(String fileName, Component par, String section, double xScale, double yScale, Color tint, double tintPercentage, String additionalInfo) throws FileNotFoundException
    {
        parent = par;
        FileProcessor fil = new FileProcessor(fileName);
        int index = fil.findSectionIndex(section);
        readPaints(fileName, index, xScale, yScale, tint, tintPercentage, additionalInfo);
        alphabetizeJobsAndNames(names, jobs);
    }
    /**
     * Reads the file and converts that into strings
     * @param fileName The name of the file
     * @param startIndex The index to start the file reading
     */
    private void readPaints(String fileName, int startIndex, double xScale, double yScale) throws FileNotFoundException
    {
        readPaints(fileName, startIndex, xScale, yScale, null, 0, "");
    }
    /**
     * Reads the file and converts that into strings
     * @param fileName The name of the file
     * @param startIndex The index to start the file reading
     * @param additionalInfo The information added at the end of the section to differentiate it from the other similar named ones:
     */
    private void readPaints(String fileName, int startIndex, double xScale, double yScale, Color tint, double tintPercentage, String additionalInfo) throws FileNotFoundException
    {
        ArrayList<String> paints = new ArrayList<String>(1);
        File file = new File(fileName);
        if(!file.exists())
        {
            FileNotFoundException error = new FileNotFoundException("Cannot convert information to paintjobs! Reason: File doesn't Exist");
            throw(error);
        }
        Scanner sc = new Scanner(file);
        int index = -1;
        while(index < startIndex)
        {
            if(sc.hasNextLine())
            {
                sc.nextLine();
            }
            index++;
        }
        int i = 0;
        while(sc.hasNextLine())
        {
            String str = sc.nextLine();
            str = str.strip();
            str = removeWhiteAndComments(str);
            if(str.length() >= 1 && str.substring(0, 1).equals(FileProcessor.getHeaderString())) //A section header in the FileProcessor
            {
                break;
            }
            if(isValid(str))
            {
                str = str.toLowerCase();
                paints.add(str);
                if(str.length() >= 5 && str.substring(0, 5).equals("name:")) //Make sure that it is long enough
                {
                    names.add(str.substring(5) + additionalInfo);
                }
                i++;
            }
        }
        generatePaintJobs(xScale, yScale, paints, tint, tintPercentage);
    }
    /**
     * Removes the white space from a string and will remove all comments
     * @param str
     * @return 
     */
    private String removeWhiteAndComments(String str)
    {
        String temp[] = str.split(" ");
        str = "";
        for(int i = 0 ; i < temp.length ; i++)
        {
            str += temp[i];
        }
        for(int i = 0 ; i < str.length()-1 ; i++)
        {
            if(str.substring(i, i+2).equals("//"))
            {
                str = str.substring(0, i);
            }
        }
        return str;
    }
    /**
     * Finds out if the string can be read. For example it will ignore it if the string is a comment or is less than 2 characters long.
     * @param str a line from the file
     * @return true if the string is valid
     */
    private boolean isValid(String str)
    {
        if(str.length() >= 2)//if its lenght is greater than or equal to 2 then return true
        {
            return true;
        }
        return false;
    }
    /**
     * Generates the paint jobs from raw data from a file
     * @param xScale the scalar value of X Components
     * @param yScale the scalar value of Y Components
     * @param paints the entire paint array of paints to turn into jobs, this is raw data, the function processes it into useful data
     */
    private void generatePaintJobs(double xScale, double yScale, ArrayList<String> paints)
    {
        generatePaintJobs(xScale, yScale, paints, null, 0);
    }
    /**
     * Generates the paint jobs from raw data from a file
     * @param xScale the scalar value of X Components
     * @param yScale the scalar value of Y Components
     * @param paints the entire paint array of paints to turn into jobs, this is raw data, the function processes it into useful data
     * @param tint The color of the tint to the paint jobs
     * @param tintPercentage The percent of the tint color that is applied to the normal color
     */
    private void generatePaintJobs(double xScale, double yScale, ArrayList<String> paints, Color tint, double tintPercentage)
    {
        while(!jobs.isEmpty())
        {
            jobs.remove(0);
        }
        int j = -1;
        for(int i = 0 ; i < paints.size() ; i++)
        {
            String str = paints.get(i);
            //Skip over section names:
            if(str.contains("name:"))
            {
                jobs.add(new ArrayList<PaintJob>(1));
                j++;
                continue;
            }
            //Split the String into three for each type of section:
            String temp[] = str.split(":");
            if(temp.length == 3)
            {
                //Read the first one which is the color:
                String temp2[] = temp[0].split(",");
                if(temp2.length >= 3)
                {
                    //Red green blue, etc.
                    int red = FileProcessor.toInt(temp2[0]);
                    int green = FileProcessor.toInt(temp2[1]);
                    int blue = FileProcessor.toInt(temp2[2]);
                    //If it has the alpha level then use it:
                    int alpha = 255;
                    if(temp2.length >= 4)
                    {
                        alpha = FileProcessor.toInt(temp2[3]);
                    }
                    if(alpha < 0)
                    {
                        alpha = 255;
                    }
                    //If any value is out of range then exit
                    if(isOutOfRange(red) || isOutOfRange(green) || isOutOfRange(blue) || isOutOfRange(alpha))
                    {
                        continue;
                    }
                    Color shapeColor = new Color(red, green, blue, alpha);
                    //Now tint it if necessary:
                    if(tint != null)
                    {
                        shapeColor = combineTwoColors(shapeColor, 1 - tintPercentage, tint, tintPercentage);
                    }
                    //Now figure out what type of shape is being created:
                    temp2 = temp[1].split(",");
                    if(temp2.length == 5) //Rectangle or circle
                    {
                        String shape = temp2[0];
                        double x = parent.getSize().width * FileProcessor.toDouble(temp2[1]) * xScale;
                        double y = parent.getSize().height * FileProcessor.toDouble(temp2[2]) * yScale;
                        double width = parent.getSize().width * FileProcessor.toDouble(temp2[3]) * xScale;
                        double height = parent.getSize().height * FileProcessor.toDouble(temp2[4]) * yScale;
                        if(temp[2].equals("fill"))
                        {
                            if(shape.equals("rect"))
                            {
                                jobs.get(j).add(new PaintJob(new Rectangle2D.Double(x, y, width, height), shapeColor));
                            }
                            else if(shape.equals("oval"))
                            {
                                jobs.get(j).add(new PaintJob(new Ellipse2D.Double(x, y, width, height), shapeColor));
                            }
                        }
                    }
                    else if(temp2.length == 7) //Arc
                    {
                        double x = parent.getSize().width * FileProcessor.toDouble(temp2[0]) * xScale;
                        double y = parent.getSize().height * FileProcessor.toDouble(temp2[1]) * yScale;
                        double width = parent.getSize().width * FileProcessor.toDouble(temp2[2]) * xScale;
                        double height = parent.getSize().height * FileProcessor.toDouble(temp2[3]) * yScale;
                        double start = FileProcessor.toInt(temp2[4]);
                        double size = FileProcessor.toInt(temp2[5]);
                        String type = temp[6];
                        if(temp[3].equals("fill"))
                        {
                            if(type.equals("pie"))
                            {
                                jobs.get(j).add(new PaintJob(new Arc2D.Double(x, y, width, height, start, size, Arc2D.PIE), shapeColor));
                            }
                            else if(type.equals("chord"))
                            {
                                jobs.get(j).add(new PaintJob(new Arc2D.Double(x, y, width, height, start, size, Arc2D.CHORD), shapeColor));
                            }
                            else if(type.equals("open"))
                            {
                                jobs.get(j).add(new PaintJob(new Arc2D.Double(x, y, width, height, start, size, Arc2D.OPEN), shapeColor));
                            }
                        }
                    }
                }
            }
            else if(temp.length == 2) //The Path type:
            {
                String temp2[] = temp[0].split(",");
                if(temp2.length >= 3)
                {
                    int red = FileProcessor.toInt(temp2[0]);
                    int green = FileProcessor.toInt(temp2[1]);
                    int blue = FileProcessor.toInt(temp2[2]);
                    //If it has the alpha level then use it:
                    int alpha = 255;
                    if(temp2.length >= 4)
                    {
                        alpha = FileProcessor.toInt(temp2[3]);
                    }
                    if(alpha < 0)
                    {
                        alpha = 255;
                    }
                    //If any value is out of range then exit
                    if(isOutOfRange(red) || isOutOfRange(green) || isOutOfRange(blue) || isOutOfRange(alpha))
                    {
                        continue;
                    }
                    Color shapeColor = new Color(red, green, blue, alpha);
                    //Now tint it if necessary:
                    if(tint != null)
                    {
                        shapeColor = combineTwoColors(shapeColor, 1 - tintPercentage, tint, tintPercentage);
                    }
                    temp2 = temp[1].split(",");
                    if(temp2.length > 1) //Now as long as it has at least one points we keep, otherwise skip:
                    {
                        Path2D.Double shape = new Path2D.Double();
                        double xFactor = parent.getSize().width * xScale;
                        double yFactor = parent.getSize().height * yScale;
                        //Make sure the first point is a point and we haven't been duped:
                        String temp3[] = temp2[1].split("~");
                        if(temp3.length == 2)
                        {
                            //Then start the shape:
                            double x = FileProcessor.toDouble(temp3[0]) * xFactor;
                            double y = FileProcessor.toDouble(temp3[1]) * yFactor;
                            shape.moveTo(x, y);
                            //Do the rest and verifying along the way as needed
                            for(int k = 1 ; k < temp2.length ; k++)
                            {
                                temp3 = temp2[k].split("~");
                                if(temp3.length == 2)
                                {
                                    x = FileProcessor.toDouble(temp3[0]) * xFactor;
                                    y = FileProcessor.toDouble(temp3[1]) * yFactor;
                                    shape.lineTo(x, y);
                                }
                            }
                            shape.closePath();
                            //Finish up and send to jobs:
                            jobs.get(j).add(new PaintJob(shape, shapeColor));
                        }
                    }
                }
            }
        }
    }
    public ArrayList<PaintJob> getSection(String name)
    {
        return getSection(name, 0, names.size());
    }
    public ArrayList<PaintJob> getSection(String name, int start, int end)
    {
        int i = (start + end) /2;
        if(i == names.size() || start > end)
        {
            return new ArrayList<PaintJob>(0);
        }
        if(names.get(i).equalsIgnoreCase(name))
        {
            return jobs.get(i);
        }
        else if(names.get(i).compareToIgnoreCase(name) < 0)
        {
            return getSection(name, i+1, end);
        }
        else
        {
            return getSection(name, start, i-1);
        }
    }
    private boolean isOutOfRange(int colPart)
    {
        if(colPart > 255 || colPart < 0)
        {
            return true;
        }
        return false;
    }
    private Color combineTwoColors(Color c1, double c1Percentage, Color c2, double c2Percentage)
    {
        Color combinedColor;
        int colorRed = (int)((c1.getRed() * c1Percentage) + (c2.getRed() * c2Percentage));
        int colorGreen = (int)((c1.getGreen() * c1Percentage) + (c2.getGreen() * c2Percentage));
        int colorBlue = (int)((c1.getBlue() * c1Percentage) + (c2.getBlue() * c2Percentage));
        int alpha = (int)((c1.getAlpha() * c1Percentage) + (c2.getAlpha() * c2Percentage));
        colorRed = putColorIntInRange(colorRed);
        colorGreen = putColorIntInRange(colorGreen);
        colorBlue = putColorIntInRange(colorBlue);
        alpha = putColorIntInRange(alpha);
        combinedColor = new Color(colorRed, colorGreen, colorBlue, alpha);
        return combinedColor;
    }
    private int putColorIntInRange(int colorInt)
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
    private ArrayList<ArrayList<PaintJob>> getAllJobs()
    {
        return jobs;
    }
    private void setAllJobs(ArrayList<ArrayList<PaintJob>> newJobs)
    {
        jobs = newJobs;
    }
    private ArrayList<String> getAllNames()
    {
        return names;
    }
    private void setAllNames(ArrayList<String> newNames)
    {
        names = newNames;
    }
    public Component getParent()
    {
        return parent;
    }
    public String toString()
    {
        String temp = "";
        for(int i = 0 ; i < jobs.size() ; i++)
        {
            temp += (jobs.get(i) + "\n");
        }
        return temp;
    }
}
