/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * A panel that has a number of lines that can scroll up or down the screen
 */
public class TextPane extends JPanel
{
    protected Font font = null;
    protected ArrayList<String> text = new ArrayList<String>(10);
    protected int maxIndex = 10;
    protected boolean scrollUp = true;
    protected Color newestTextColor = new Color(0, 80, 255);
    protected Color oldTextColor = new Color(80, 80, 80);
    protected Color backColor = null;
    protected boolean outline = false;
    /**
     * The Constructor of the TextPane, Empty Argument variant,
     * new text color = light blue,
     * old text color = dark gray,
     * background color = black,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     */
    public TextPane()
    {
        font = new Font("SanSerif", Font.BOLD, 20);
        backColor = Color.black;
        newestTextColor = new Color(0, 80, 255);
        oldTextColor = new Color(80, 80, 80);
    }
        /**
     * The Constructor of the TextPane, Two Argument variant,
     * background color = black,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     */
    public TextPane(Color ntc, Color otc)
    {
        font = new Font("SanSerif", Font.BOLD, 20);
        newestTextColor = ntc;
        oldTextColor = otc; 
        backColor = Color.black;
    }
        /**
     * The Constructor of the TextPane, Three Argument variant,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     */
    public TextPane(Color ntc, Color otc, Color bc)
    {
        font = new Font("SanSerif", Font.BOLD, 20);
        newestTextColor = ntc;
        oldTextColor = otc;
        backColor = bc;
    }
    /**
     * The Constructor of the TextPane, All Argument variant,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     * @param back The Boolean that states whether the pane scrolls up or down, true = scrolls down, false = scrolls up
     */
    public TextPane(Color ntc, Color otc, Color bc, boolean back)
    {
        font = new Font("SanSerif", Font.BOLD, 20);
        scrollUp = back;
        newestTextColor = ntc;
        oldTextColor = otc; 
        backColor = bc;
    }
    /**
     * The Constructor of the TextPane, All Argument variant,
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     * @param back The Boolean that states whether the pane scrolls up or down, true = scrolls down, false = scrolls up
     * @param ft The Font that the TextPane is in.
     * @param mx The maximum index of the array
     */
    public TextPane(Color ntc, Color otc, Color bc, boolean back, Font ft, int mx)
    {
        maxIndex = mx;
        font = ft;
        scrollUp = back;
        newestTextColor = ntc;
        oldTextColor = otc; 
        backColor = bc;
    }
    /**
     * The Constructor of the TextPane, All Argument variant,
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     * @param back The Boolean that states whether the pane scrolls up or down, true = scrolls down, false = scrolls up
     * @param ft The Font that the TextPane is in.
     * @param mx The maximum index of the array
     * @param isOutlined True = outlined, false = not outlined
     */
    public TextPane(Color ntc, Color otc, Color bc, boolean back, Font ft, int mx, boolean isOutlined)
    {
        maxIndex = mx;
        font = ft;
        scrollUp = back;
        newestTextColor = ntc;
        oldTextColor = otc; 
        backColor = bc;
        outline = isOutlined;
    }
    /**
     * 
     * @param g 
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(backColor);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        if(outline)
        {
            g2.setColor(Color.black);
            g2.drawRect(0, 0, getSize().width, getSize().height);
        }
        if(text.size() > 0)
        {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //text looks ridged without this
            //Deal with font size isues
            Font tempFont = font;
            tempFont = new Font(font.getFamily(), font.getStyle(), 4);
            FontMetrics fm1 = getFontMetrics(tempFont);
            while(fm1.getHeight() * text.size() < (int)(getSize().height / 1.01)&& fm1.stringWidth(findLongestLine()) < getSize().width)
            {
                tempFont = new Font(font.getFamily(), font.getStyle(), tempFont.getSize()+1);
                fm1 = getFontMetrics(tempFont);
                if(tempFont.getSize() > font.getSize())
                {
                    tempFont = new Font(font.getFamily(), font.getStyle(), font.getSize()+1);
                    break;
                }
            }
            tempFont = new Font(font.getFamily(), font.getStyle(), tempFont.getSize()-1);
            fm1 = getFontMetrics(tempFont);

            g2.setFont(tempFont);
            if(text.isEmpty())
            {
                return;
            }
            int height = fm1.getHeight() * 2;
            int i = 0;
            if(!scrollUp)
            {
                g2.setColor(newestTextColor);
                g2.drawString(text.get(0), ( getSize().width - fm1.stringWidth(text.get(0)) ) /2, fm1.getHeight());
                i++;
                g2.setColor(oldTextColor);
                while(height < getSize().height && i < text.size())
                {
                    if(!text.get(i).equals(" "))
                    {
                        g2.drawString(text.get(i), ( getSize().width - fm1.stringWidth(text.get(i)) ) /2, height);
                        i++;
                        height += fm1.getHeight();
                    }
                    else
                    {
                        i++;
                    }
                }
            }
            else
            {
                g2.setColor(newestTextColor);
                g2.drawString(text.get(i), ( getSize().width - fm1.stringWidth(text.get(i)) ) /2, fm1.getHeight());
                i++;
                g2.setColor(oldTextColor);
                while(height < getSize().height && i < text.size())
                {
                    if(!text.get(i).equals(" "))
                    {
                        g2.drawString(text.get(i), ( getSize().width - fm1.stringWidth(text.get(i)) ) /2, height);
                        i++;
                        height += fm1.getHeight();
                    }
                    else
                    {
                        i++;
                    }
                } 
            }
        }
    }
    /**
     * Finds and returns the longest line in the array
     * @return STRING
     */
    private String findLongestLine()
    {
        int index = 0;
        for(int i = 0 ; i < text.size() ; i++)
        {
            if(text.get(i).length() > text.get(index).length())
            {
                index = i;
            }
        }
        return text.get(index);
    }
    /**
     * Adds text to the TextPane. Does NOT except an empty space like " "
     * @param str The string of text that will be added
     */
    public void addText(String str)
    {
        if(str == " ")
        {
            return;
        }
        if(scrollUp == false)
        {
            if((text.size() + 1) <= maxIndex)
            {
                text.add(str);
            }
            else
            {
                text.remove(0);
                text.add(str);
            }
        }
        else
        {
            if((text.size() + 1) <= maxIndex)
            {
                text.add(0, str);
            }
            else
            {
                text.remove(text.size() -1);
                text.add(0, str);
            }
        }
        repaint();
    }
    /**
     * Removes all of the text from the TextPane
     */
    public void removeText()
    {
        while(!text.isEmpty())
        {
            text.remove(0);
        }
    }
    /**
     * Sets the newest text to the color ntc
     * @param ntc a color
     */
    public void setNewestTextColor(Color ntc)
    {
        newestTextColor = ntc;
    }
    /**
     * Sets the old text to the color otc
     * @param otc a color
     */
    public void setOldTextColor(Color otc)
    {
        oldTextColor = otc;
    }
    /**
     * Sets the back color to the color bc
     * @param bc a color
     */
    public void setBackColor(Color bc)
    {
        backColor = bc;
    }
}
