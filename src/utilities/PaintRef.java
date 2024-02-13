/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;
import java.io.File;
import java.util.Scanner;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
/**
 * A class that draws for you. Do not make a PaintRef, rather use it as a static class
 */
public class PaintRef 
{
    private boolean flipX = false;
    private double xFold = 0; //The fold to flip over
    private boolean flipY = false;
    private double yFold = 0; //The fold to flip over
    private ArrayList<String> paints = new ArrayList<String>(1);
    private ArrayList<String> names = new ArrayList<String>(1);
    private ArrayList<Integer> location = new ArrayList<Integer>(1);
    /**
     * Gets the fileName and makes sure that it exists
     * @param fileName the name of the file to be read
     * @deprecated
     */
    @Deprecated
    public PaintRef(String fileName)
    {
        File file = new File(fileName);
        if(!file.exists())
        {
            System.err.print("Please supply a valid .txt file name!");
            System.exit(0);
        }
        try
        {
            Scanner sc = new Scanner(file);
            int i = 0;
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                str = str.strip();
                str = removeWhiteAndComments(str);
                if(isValid(str))
                {
                    str = str.toLowerCase();
                    paints.add(str);
                    if(str.length() >= 5 && str.substring(0, 5).equals("name:")) //Make sure that it is long enough
                    {
                        names.add(str.substring(5));
                        location.add(i);
                    }  
                    i++;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * Finds out if the string can be read. For example it will ignore it if the string is a comment or is less than 2 characters long.
     * @param str a line from the file
     * @return true if the string is valid
     */
    private boolean isValid(String str)
    {
        if(str.length() >= 2 && !str.substring(0,2).equals("//"))//if its lenght is greater than or equal to 2 and it isn't a comment then return true
        {
            return true;
        }
        return false;
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
     * Checks the string and returns the number that the string represents
     * @param str the string
     * @return a number, if it is invalid then it will return 0
     */
    private int isNumber(String str)
    {
        int num = FileProcessor.toInt(str);
        if(num == Integer.MAX_VALUE)
        {
            num = 0;
        }
        return num;
    }
    /**
     * Using a string, that is a fraction, this function will make that into a decimal value
     * @param str
     * @return The number created during division, will return 0 if the second number is 0 or there is an error
     */
    private double turnToDecimal(String str)
    {
        String temp[] = str.split("/");
        if(temp.length == 2)
        {
            double a = isNumber(temp[1]);
            if(a > 0)
            {
                return isNumber(temp[0]) / a;
            }
        }
        return 0;
    }
    /**
     * Using a string this function will make that into a boolean value
     * @param str
     * @return A true or false value, false if string can't be turned into a boolean or the value is false, else true
     */
    private boolean turnToBoolean(String str)
    {
        if(str.equals("true"))
        {
            return true;
        }
        return false;
    }
    /**
     * Paints the component according to the file starting at a specified marker
     * @param g2 The graphics so that it can draw
     * @param start The String that it is looking for so it can start the drawing process
     * @param parent The place that is being draw to, for calculations to work
     * @deprecated 
     */
    @Deprecated
    public void paint(Graphics2D g2, String start, Component parent)
    {
        paint(g2, start, parent, 0, 0);
    }
    /**
     * Paints the component according to the file starting at a specified marker
     * @param g2 The graphics so that it can draw
     * @param start The String that it is looking for so it can start the drawing process
     * @param parent The place that is being draw to, for calculations to work
     * @param x the x-axis start
     * @param y the y-axis start
     * @deprecated 
     */
    @Deprecated
    public void paint(Graphics2D g2, String start, Component parent, double x, double y)
    {
        try
        {
            Path2D.Float path = new Path2D.Float();
            int place = paints.size(); //If not found then this will prevent it from painting
            int next = 0;
            start = removeWhiteAndComments(start);
            start = start.toLowerCase();
            for(int i = 0 ; i < names.size() ; i++)
            {
                String str = names.get(i);
                if(str.equals(start)) //Make sure that it is the string we are looking for.
                {
                    place = location.get(i)+1;
                    next = i;
                    break;
                }
            }
            for(int i = place ; i < paints.size() ; i++)
            {
                String str = paints.get(i);
                if(next+1 < location.size() && i == location.get(next+1))
                {
                    flipX = false;
                    flipY = false;
                    break;
                }
                String temp[] = null;
                String temp2[] = str.split("=");
                if(temp2.length >= 1)
                {
                    switch(temp2[0])
                    {
                        case "fillrect":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 4)
                            {
                                Rectangle2D.Double rect = new Rectangle2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]) );
                                flipRect(rect, parent);
                                rect.setFrame(rect.getX() + x, rect.getY() + y, rect.getWidth(), rect.getHeight());
                                g2.fill(rect);
                            }
                            break;
                        case "filloval":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 4)
                            {
                                Ellipse2D.Double elli = new Ellipse2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]) );
                                flipRect(elli, parent);
                                elli.setFrame(elli.getX() + x, elli.getY() + y, elli.getWidth(), elli.getHeight());
                                g2.fill(elli);
                            }
                            break;
                        case "fillarc":
                            str = str.substring(8);
                            temp = str.split(",");
                            if(temp.length == 7)
                            {
                                Arc2D.Double arc;
                                if(temp[6].equalsIgnoreCase("chord"))
                                {
                                    arc = new Arc2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]), isNumber(temp[4]), isNumber(temp[5]), Arc2D.CHORD);
                                }
                                else if(temp[6].equalsIgnoreCase("pie"))
                                {
                                    arc = new Arc2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]), isNumber(temp[4]), isNumber(temp[5]), Arc2D.PIE);
                                }
                                else
                                {
                                    arc = new Arc2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]), isNumber(temp[4]), isNumber(temp[5]), Arc2D.OPEN);
                                }
                                flipRect(arc, parent);
                                arc.setFrame(arc.getX() + x, arc.getY() + y, arc.getWidth(), arc.getHeight());
                                g2.fill(arc);
                            }
                            break;
                        case "fillPath":
                            g2.fill(path);
                            break;
                        case "drawrect":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 4)
                            {
                                Rectangle2D.Double rect = new Rectangle2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]) );
                                flipRect(rect, parent);
                                rect.setFrame(rect.getX() + x, rect.getY() + y, rect.getWidth(), rect.getHeight());
                                g2.draw(rect);
                            }
                            break;
                        case "drawoval":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 4)
                            {
                                Ellipse2D.Double elli = new Ellipse2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]) );
                                flipRect(elli, parent);
                                elli.setFrame(elli.getX() + x, elli.getY() + y, elli.getWidth(), elli.getHeight());
                                g2.draw(elli);
                            }
                            break;
                        case "drawarc":
                            str = str.substring(8);
                            temp = str.split(",");
                            if(temp.length == 7)
                            {
                                Arc2D.Double arc;
                                if(temp[6].equalsIgnoreCase("chord"))
                                {
                                    arc = new Arc2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]), isNumber(temp[4]), isNumber(temp[5]), Arc2D.CHORD);
                                }
                                else if(temp[6].equalsIgnoreCase("pie"))
                                {
                                    arc = new Arc2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]), isNumber(temp[4]), isNumber(temp[5]), Arc2D.PIE);
                                }
                                else
                                {
                                    arc = new Arc2D.Double( parent.getSize().width * turnToDecimal(temp[0]), parent.getSize().height * turnToDecimal(temp[1]), parent.getSize().width * turnToDecimal(temp[2]), parent.getSize().height * turnToDecimal(temp[3]), isNumber(temp[4]), isNumber(temp[5]), Arc2D.OPEN);
                                }
                                flipRect(arc, parent);
                                arc.setFrame(arc.getX() + x, arc.getY() + y, arc.getWidth(), arc.getHeight());
                                g2.draw(arc);
                            }
                            break;
                        case "drawpath":
                            g2.draw(path);
                            break;
                        case "drawstring":
                            str = str.substring(10);
                            temp = str.split(",");
                            if(temp.length == 7)
                            {
                                temp[0] = createSpaces(temp[0]);
                                Rectangle2D.Double bound = new Rectangle2D.Double(parent.getSize().width * turnToDecimal(temp[1]) + x, parent.getSize().height * turnToDecimal(temp[2]) + y,parent.getSize().width * turnToDecimal(temp[3]) + x, parent.getSize().height * turnToDecimal(temp[4]) + y);
                                int maxFont = isNumber(temp[5]);
                                if(maxFont == 0)
                                {
                                    maxFont = Integer.MAX_VALUE;
                                }
                                //Font
                                Font tempFont = new Font("SanSerif", Font.PLAIN, 4);
                                FontMetrics fm1 = parent.getFontMetrics(tempFont);
                                while(fm1.getHeight() < (bound.getHeight() )&& fm1.stringWidth(temp[0]) < bound.getWidth())
                                {
                                    tempFont = new Font(tempFont.getFamily(), tempFont.getStyle(), tempFont.getSize()+1);
                                    fm1 = parent.getFontMetrics(tempFont);
                                    if(tempFont.getSize() > maxFont)
                                    {
                                        tempFont = new Font(tempFont.getFamily(), tempFont.getStyle(), maxFont+1);
                                        break;
                                    }
                                }
                                tempFont = new Font(tempFont.getFamily(), tempFont.getStyle(), tempFont.getSize()-1);
                                fm1 = parent.getFontMetrics(tempFont);
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //text looks ridged without this
                                g2.setFont(tempFont);
                                //Draw
                                g2.drawString(temp[0], (float)((parent.getSize().width - fm1.stringWidth(temp[0])) * turnToDecimal(temp[1]) + x), (float)((parent.getSize().height + fm1.getAscent()) * (float)turnToDecimal(temp[2]) + y));
                            }
                            break;
                        case "setcolor":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 3)
                            {
                                int red = isNumber(temp[0]);
                                int green = isNumber(temp[1]);
                                int blue = isNumber(temp[2]);
                                if(red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0)
                                {
                                    break;
                                }
                                g2.setColor( new Color( red, green, blue ) );
                            }
                            break;
                        case "setgradient":
                            str = str.substring(12);
                            temp = str.split(",");
                            if(temp.length == 10)
                            {
                                g2.setPaint( new GradientPaint(parent.getSize().width * (float)turnToDecimal(temp[0]), parent.getSize().height * (float)turnToDecimal(temp[1]), new Color(isNumber(temp[2]), isNumber(temp[3]), isNumber(temp[4])), parent.getSize().width * (float)turnToDecimal(temp[5]), parent.getSize().height * (float)turnToDecimal(temp[6]), new Color(isNumber(temp[7]), isNumber(temp[8]), isNumber(temp[9]))));
                            }
                            break;
                        case "flip":
                            str = str.substring(5);
                            temp = str.split(",");
                            if(temp.length == 4)
                            {
                                flipX = turnToBoolean(temp[0]);
                                if(flipX)
                                {
                                    xFold = turnToDecimal(temp[1]);
                                }
                                flipY = turnToBoolean(temp[2]);
                                if(flipY)
                                {
                                    yFold = turnToDecimal(temp[3]);
                                }
                            }
                            break;
                        case "endflip":
                            flipX = false;
                            flipY = false;
                            break;
                        case "resetpath":
                            path.reset();
                            break;
                        case "movepath":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 2)
                            {
                                path.moveTo( parent.getSize().width * flipOverX(turnToDecimal(temp[0])) + x, parent.getSize().height * flipOverY(turnToDecimal(temp[1])) + y );
                            }
                            break;
                        case "linepath":
                            str = str.substring(9);
                            temp = str.split(",");
                            if(temp.length == 2)
                            {
                                path.lineTo( parent.getSize().width * flipOverX(turnToDecimal(temp[0])) + x, parent.getSize().height * flipOverY(turnToDecimal(temp[1])) + y );
                            }
                            break;
                        case "closepath":
                            path.closePath();
                            break;
                        case "curvepath":
                            str = str.substring(10);
                            temp = str.split(",");
                            if(temp.length == 6)
                            {
                                path.curveTo( parent.getSize().width * turnToDecimal(temp[0]) + x, parent.getSize().height * turnToDecimal(temp[1]) + y, parent.getSize().width * turnToDecimal(temp[2]) + x, parent.getSize().height * turnToDecimal(temp[3]) + y, parent.getSize().width * turnToDecimal(temp[4]) + x, parent.getSize().height * turnToDecimal(temp[5]) + y);
                            }
                            break;
                        default:
                            continue;
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.err.println("Error in paint job, Exiting!!!!");
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     * Turns all /s into spaces
     * @param str The string that wants spaces
     * @return The new String that has spaces
     */
    private String createSpaces(String str)
    {
        for(int i = 0 ; i < str.length()-1 ; i++)
        {
            if(str.substring(i, i+2).equalsIgnoreCase("/s"))
            {
                str = str.substring(0, i) + " " + str.substring(i+2);
            }
        }
        return str;
    }
    /**
     * Flips the given shape over any nessary folds
     * @param shape the shape to be flipped
     */
    private void flipRect(RectangularShape shape, Component c)
    {
        if(flipX)
        {
            xFold *= c.getWidth();
            shape.setFrame(flipOverX(shape.getX()) - shape.getWidth(), shape.getY(), shape.getWidth(), shape.getHeight());
            xFold /= c.getWidth();
            if(shape instanceof Arc2D.Float)
            {
                Arc2D.Float shape2 = (Arc2D.Float)shape;
                shape2.setAngleExtent(shape2.getAngleExtent());
                shape2.setAngleStart(-1 * shape2.getAngleStart());
            }
        }
        if(flipY)
        {
            yFold *= c.getHeight();
            shape.setFrame(shape.getX(), flipOverY(shape.getY()) - shape.getHeight(), shape.getWidth(), shape.getHeight());
            yFold /= c.getHeight();
            if(shape instanceof Arc2D.Float)
            {
                Arc2D.Float shape2 = (Arc2D.Float)shape;
                shape2.setAngleStart(180 - shape2.getAngleStart());
                shape2.setAngleExtent(shape2.getAngleExtent());
            }
        }
    }
    /**
     * Changes the number to a new number flipped over the xFold
     * @param num the number to be changed
     * @return Float
     */
    private double flipOverX(double num)
    {
        if(flipX)
        {
            num = xFold + (xFold - num);
        }
        return num;
    }
    /**
     * Changes the number to a new number flipped over the yFold
     * @param num the number to be changed
     * @return Float
     */
    private double flipOverY(double num)
    {
        if(flipY)
        {
            num = yFold + (yFold - num);
        }
        return num;
    }
    /**
     * Makes a color which is the equivalent of adding the two colors
     * @param c1 The first Color
     * @param c2 The second Color
     * @return The new Color in the middle of the two
     */
    public static Color addColors(Color c1, Color c2)
    {
        int red = (c1.getRed() + c2.getRed());
        int green = (c1.getGreen() + c2.getGreen());
        int blue = (c1.getBlue() + c2.getBlue());
        if(red > 255)
        {
            red = 255;
        }
        if(green >= 255)
        {
            green = 255;
        }
        if(blue >= 255)
        {
            blue = 255;
        }
        return new Color(red, green, blue);
    }
    /**
     * Makes a color which is in the middle of the two Colors
     * @param c1 The first Color
     * @param c2 The second Color
     * @return The new Color in the middle of the two
     */
    public static Color equalizeColors(Color c1, Color c2)
    {
        int red = (c1.getRed() + c2.getRed()) /2;
        int green = (c1.getGreen() + c2.getGreen()) /2;
        int blue = (c1.getBlue() + c2.getBlue()) /2;
        if(red > 255)
        {
            red = 255;
        }
        if(green >= 255)
        {
            green = 255;
        }
        if(blue >= 255)
        {
            blue = 255;
        }
        return new Color(red, green, blue);
    }
    /**
     * Creates a new Color that is a percent of the old one
     * @param c1 The color
     * @param percent The percent of the color left
     * @return The new color
     */
    public static Color percentColor(Color c1, float percent)
    {
        if(percent < 1 && percent >= 0)
        {
            int red = (int)(c1.getRed() * percent);
            int green = (int)(c1.getGreen() * percent);
            int blue = (int)(c1.getBlue() * percent);
            if(red > 255)
            {
                red = 255;
            }
            if(green >= 255)
            {
                green = 255;
            }
            if(blue >= 255)
            {
                blue = 255;
            }
            return new Color(red, green, blue);
        }
        return c1;
    }
    public static Color grayOut(Color c)
    {
        int rgb = (c.getRed() + c.getGreen() + c.getBlue())/3;
        if(rgb > 255)
        {
            rgb = 255;
        }
        else if(rgb < 0)
        {
            rgb = 0;
        }
        return new Color(rgb, rgb, rgb);
    }
    public static Color invertColor(Color c)
    {
        int r = (255 - c.getRed());
        int g = (255 - c.getGreen());
        int b = (255 - c.getBlue());
        return new Color(r, g, b);
    }
    /**
     * Paints a bar that goes down based on the percent given.
     * @param g2 The graphics
     * @param bounds The bounds for the maximum area needed to draw
     * @param percent The percent left 100-0
     * @param start The starting color that the bar starts with
     * @param end The color that the bar gets to when it is at the end, Note that you won't see this since it would be at 0% when the color is seen
     * @param back The background Color
     * @param horizontal True = goes down horizontally, False = goes down vertically
     */
    public static void paintBar(Graphics2D g2, Rectangle2D.Float bounds, float percent, Color start, Color end, Color back, boolean horizontal)
    {
        g2.setColor(back);
        g2.fill(bounds);
        if(percent > 1)
        {
            percent = 1;
        }
        if(percent > .5)
        {
            g2.setColor( addColors(percentColor(start, 100), percentColor(end, 2 * (1 - percent))) );
        }
        else
        {
            g2.setColor( addColors(percentColor(start, percent * 2), percentColor(end, 100)) );
        }
        if(horizontal)
        {
            g2.fill(new Rectangle2D.Float((float)bounds.getX(), (float)bounds.getY(), percent * (float)bounds.getWidth(), (float)bounds.getHeight()));
        }
        else
        {
            g2.fill(new Rectangle2D.Float((float)bounds.getX(), (float)bounds.getY(), (float)bounds.getWidth(), percent * (float)bounds.getHeight()));
        }
    }
    
    /**
     * Paints a bar that goes down based on the percent given. Also places text onto the bar
     * @param g2 The graphics
     * @param bounds The bounds for the maximum area needed to draw
     * @param percent The percent left 100-0
     * @param start The starting color that the bar starts with
     * @param end The color that the bar gets to when it is at the end, Note that you won't see this since it would be at 0% when the color is seen
     * @param back The background Color
     * @param horizontal True = goes down horizontally, False = goes down vertically
     * @param text The text to printed
     * @param parent Where it is being printed. For text sizing purposes
     */
    public static void paintTextedBar(Graphics2D g2, Rectangle2D.Float bounds, float percent, Color start, Color end, Color back, boolean horizontal, String text, Component parent)
    {
        paintBar(g2, bounds, percent, start, end, back, horizontal);
        //Text
        Font tempFont = new Font("SanSerif", Font.PLAIN, 4);
        FontMetrics fm1 = parent.getFontMetrics(tempFont);
        while(fm1.getHeight() < (bounds.getHeight() )&& fm1.stringWidth(text) < bounds.getWidth()/2 + bounds.getX() - (fm1.stringWidth(text)/2.0))
        {
            tempFont = new Font(tempFont.getFamily(), tempFont.getStyle(), tempFont.getSize()+1);
            fm1 = parent.getFontMetrics(tempFont);
        }
        tempFont = new Font(tempFont.getFamily(), tempFont.getStyle(), tempFont.getSize()-1);
        fm1 = parent.getFontMetrics(tempFont);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //text looks ridged without this
        g2.setFont(tempFont);
        Color c2 = invertColor(equalizeColors( grayOut(g2.getColor()), grayOut(back) ));
        if(c2.getRed() + c2.getBlue() + c2.getGreen() >= 384)
        {
            g2.setColor(c2.darker());
        }
        else
        {
            g2.setColor(c2.brighter());
        }
        g2.drawString(text, (int)( bounds.getWidth()/2 + bounds.getX() - (fm1.stringWidth(text)/2.0)), (int)(bounds.getY() + (fm1.getAscent())));
    }
}