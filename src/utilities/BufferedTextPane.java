/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Westley
 */
public class BufferedTextPane extends TextPane
{
    private ArrayList<String> waitText = new ArrayList<String>(1);
    private Timer timmy = null;
    /**
     * The Constructor of the BufferedTextPane, No Argument variant,
     * Delay = 500 ms,
     * new text color = Bright Orange,
     * old text color = Orangish Gray,
     * background color = Dark Purple,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     */
    public BufferedTextPane()
    {
        super(new Color(255, 185, 71), new Color(107, 97, 81), new Color(8, 0, 120));
        timmy = new Timer(500, new TimmyListener());
    }
    /**
     * The Constructor of the BufferedTextPane, One Argument variant,
     * new text color = Bright Orange,
     * old text color = Orangish Gray,
     * background color = Dark Purple,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param delay The delay in milliseconds for the text to appear.
     */
    public BufferedTextPane(int delay)
    {
        super(new Color(255, 185, 71), new Color(107, 97, 81), new Color(8, 0, 120));
        timmy = new Timer(delay, new TimmyListener());
    }
    /**
     * The Constructor of the BufferedTextPane, Three Argument variant,
     * background color = Dark Purple,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param delay The delay in milliseconds for the text to appear.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     */
    public BufferedTextPane(int delay, Color ntc, Color otc)
    {
        super(ntc, otc, new Color(8, 0, 120));
        timmy = new Timer(delay, new TimmyListener());
    }
        /**
     * The Constructor of the BufferedTextPane, Four Argument variant,
     * scrolls up,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param delay The delay in milliseconds for the text to appear.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     */
    public BufferedTextPane(int delay, Color ntc, Color otc, Color bc)
    {
        super(ntc, otc, bc);
        timmy = new Timer(delay, new TimmyListener());
    }
    /**
     * The Constructor of the BufferedTextPane, All Argument variant,
     * maximum index = 10,
     * font = SanSerif, Bold, 20.
     * @param delay The delay in milliseconds for the text to appear.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     * @param back The Boolean that states whether the pane scrolls up or down, true = scrolls down, false = scrolls up
     */
    public BufferedTextPane(int delay, Color ntc, Color otc, Color bc, boolean back)
    {
        super(ntc, otc, bc, back);
        timmy = new Timer(delay, new TimmyListener());
    }
    /**
     * The Constructor of the TextPane, All Argument variant,
     * @param delay The delay in milliseconds for the text to appear.
     * @param ntc The Color of the text that shows up at the top
     * @param otc The Color of the rest of the text
     * @param bc The Color of the Background of the TextPane
     * @param back The Boolean that states whether the pane scrolls up or down, true = scrolls down, false = scrolls up
     * @param ft The Font that the TextPane is in.
     * @param mx The maximum index of the array
     */
    public BufferedTextPane(int delay, Color ntc, Color otc, Color bc, boolean back, Font ft, int mx)
    {
        super(ntc, otc, bc, back, ft, mx);
        timmy = new Timer(delay, new TimmyListener());
    }
    @Override
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
        timmy.start();
        waitText.add(str);
    }
    class TimmyListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(waitText.size() == 1)
            {
                timmy.stop();
            }
            if(scrollUp == false)
            {
                if((text.size() + 1) <= maxIndex)
                {
                    text.add(waitText.get(0));
                }
                else
                {
                    text.remove(0);
                    text.add(waitText.get(0));
                }
            }
            else
            {
                if((text.size() + 1) <= maxIndex)
                {
                    text.add(0, waitText.get(0));
                }
                else
                {
                    text.remove(text.size() -1);
                    text.add(0, waitText.get(0));
                }
            }
            waitText.remove(0);
            repaint();
        }
    }
    /**
     * Finds out if there is more text to be printed 
     * @return True if there is no more text waiting to be printed otherwise it says false
     */
    public boolean isFinished()
    {
        return waitText.isEmpty();
    }
    @Override
    public void removeText()
    {
        timmy.stop();
        while(!waitText.isEmpty())
        {
            waitText.remove(0);
        }
        super.removeText();
    }
}
