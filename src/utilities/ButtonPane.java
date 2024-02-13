/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

/**
 *
 * @author Westley
 */
/**
 * A panel that draws text on a clickable panel
 */
public class ButtonPane extends JPanel
{
    private String label = "";
    private Font fontType = null;
    private Color originalTextColor = Color.red;
    private Color originalTopColor = new Color(0, 10, 100);
    private Color originalBottomColor = new Color(0, 0, 125);
    private Color textColor = Color.red;
    private Color topColor = new Color(0, 10, 100);
    private Color bottomColor = new Color(0, 0, 125);
    private boolean isGray = false;
    
    //Drawing:
    private Font tempFont = null;
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;
    private int gradientStartX = 0;
    private int stringX = 0;
    private int stringY = 0;
    /**
     * sets all of the standard settings
     * name - This is a label! ;)
     * font - Dialog, Plain, 90
     * text color - red
     * gradient - ocean/ blue gradient
     */
    public ButtonPane()
    {
        super();
        fontType = new Font("Dialog", Font.PLAIN, 90);
        label = "This is a Label! ;)";
    }
    /**
     * Sets only the name
     * @param str the text that is displayed on the button
     */
    public ButtonPane(String str)
    {
        super();
        fontType = new Font("Dialog", Font.PLAIN, 90);
        label = str;
    }
    /**
     *
     * @param str name
     * @param font font for the text
     */
    public ButtonPane(String str, Font font)
    {
        super();
        fontType = font;
        label = str;
    }
    /**
     * sets all values for the button
     * @param str name
     * @param font font type, size, etc.
     * @param cl text color
     * @param tc top of the gradient color
     * @param bc bottom of the gradient color
     */
    public ButtonPane(String str, Font font, Color cl, Color tc, Color bc)
    {
        super();
        fontType = font;
        label = str;
        textColor = cl;
        topColor = tc;
        bottomColor = bc;
        originalTextColor = cl;
        originalTopColor = tc;
        originalBottomColor = bc;
        isGray = false;
    }
    public void paintComponent(Graphics g)
    {
        resize();
        Graphics2D g2 = (Graphics2D) g;
        //Background
        GradientPaint backGroundColor = new GradientPaint(gradientStartX, startY, topColor, gradientStartX, endY, bottomColor);
        g2.setPaint(backGroundColor);
        g2.fillRect(startX, startY, endX, endY);
        //Outline
        g2.setColor(Color.black);
        g2.drawRect(startX, startY, endX, endY);
        //Text
        g2.setColor(textColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //text looks ridged without this
        g2.setFont(tempFont);
        g2.drawString(label, stringX, stringY);
    }
    /**
     * set the background color
     * @param c1
     * @param c2 
     */
    public void setBack(Color c1, Color c2)
    {
        topColor = c1;
        bottomColor = c2;
        originalTopColor = c1;
        originalBottomColor = c2;
        isGray = false;
    }
    /**
     * sets the Text color
     * @param c1
     */
    public void setTextColor(Color c1)
    {
        textColor = c1;
        originalTextColor = c1;
        isGray = false;
    }
    /**
     * 
     * @return the string of the label
     */
    public String getText()
    {
        return label;
    }
    /**
     * @param str The String of text for the button to change to
     */
    public void setText(String str)
    {
        label = str;
        repaint();
    }
    /**
     * Makes the text and background a gray hue based off of the average of the three colors.
     * It is NOT perfect, but is close enough.
     */
    public void grayOut()
    {
        topColor = PaintRef.grayOut(topColor);
        bottomColor = PaintRef.grayOut(bottomColor);
        textColor = PaintRef.grayOut(textColor);
        isGray = true;
    }
    /**
     * Makes the text and background the original colors they were set to before being grayed out
     */
    public void colorIn()
    {
        textColor = originalTextColor;
        topColor = originalTopColor;
        bottomColor = originalBottomColor;
        isGray = false;
    }
    /**
     * @return The state of the grayness
     * true = Grayed out, false = Colored in
     */
    public boolean getState()
    {
        return isGray;
    }
    public void resize()
    {
        //Font
        tempFont = new Font(fontType.getFamily(), fontType.getStyle(), 4);
        FontMetrics fm1 = getFontMetrics(tempFont);
        while(fm1.getHeight() < (getSize().height - fm1.getAscent())&& fm1.stringWidth(label) < getSize().width)
        {
            tempFont = new Font(fontType.getFamily(), fontType.getStyle(), tempFont.getSize()+1);
            fm1 = getFontMetrics(tempFont);
            if(tempFont.getSize() > fontType.getSize())
            {
                tempFont = new Font(fontType.getFamily(), fontType.getStyle(), fontType.getSize()+1);
                break;
            }
        }
        tempFont = new Font(fontType.getFamily(), fontType.getStyle(), tempFont.getSize()-1);
        fm1 = getFontMetrics(tempFont);

        //Now the vars:
        endX = getSize().width;
        endY = getSize().height;
        gradientStartX = endX / 2;
        stringX = (getSize().width - fm1.stringWidth(label)) /2;
        stringY = (getSize().height + fm1.getAscent()) /2;
    }
}
