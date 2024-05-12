/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import graphicsUtilities.Console;
import graphicsUtilities.WGAnimation.WGAAnimationManager;
import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGTextArea;
import graphicsUtilities.WestGraphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Westley
 */
public class Logger extends Console
{
    //Final Variables:
    private final double slotSize = 50; //The standard size of the grid. AKA each slotSize is 1/50th of the screen in both x and y directions
    private final float borderSize = 5; //The standard size of the grid. AKA each slotSize is 1/50th of the screen in both x and y directions
    private final int updateTime = 875;
    
    //Display Info variables:
    private final Rectangle2D.Double displayInformationBounds = new Rectangle2D.Double(0, 0, 6/slotSize, 6/slotSize);
    private final String[] displayInformationText = {"Logger v0.1", "Running..."};
    private final Font displayInformationFont = new Font("Monospaced", Font.BOLD, 10);
    
    //Error Output variables:
    private final Rectangle2D.Double errorOutputBounds = new Rectangle2D.Double(6/slotSize, 0, 20/slotSize, 1);
    private final Font errorOutputFont = new Font("Serif", 24, Font.PLAIN);
    
    //Output variables:
    private final Rectangle2D.Double outputBounds = new Rectangle2D.Double(26/slotSize, 0, 20/slotSize, 1);
    private final Font outputFont = new Font("Serif", Font.PLAIN, 24);
    
    //Dynamic Variables
    private Color backgroundColor;
    private Color scrollbarColor;
    private Color borderColor;
    private Color textColor;
    private WGTextArea standardDisplayInformation;
    private WGTextArea errorOutputDisplay;
    private WGTextArea outputDisplay;
    private WGAAnimationManager animationManager = new WGAAnimationManager();
    
    //File System:
    private boolean fileWritenTo = false;
    private PrintWriter errorWriter;
    private BufferedWriter outputWriter;
    
    public Logger(Color backgroundColor, Color borderColor, Color textColor, Color scrollbarColor)
    {
        this.backgroundColor = backgroundColor;
        this.scrollbarColor = scrollbarColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
        try
        {
            standardDisplayInformation = new WGTextArea(displayInformationBounds.getX(), displayInformationBounds.getY(), displayInformationBounds.getWidth(), displayInformationBounds.getHeight(), borderSize, displayInformationText, displayInformationFont, WGTextArea.TEXT_STYLE_LEFT, textColor, this);
            errorOutputDisplay = new WGTextArea(errorOutputBounds.getX(), errorOutputBounds.getY(), errorOutputBounds.getWidth(), errorOutputBounds.getHeight(), borderSize, new String[0], errorOutputFont, WGTextArea.TEXT_STYLE_LEFT, textColor, backgroundColor, borderColor, scrollbarColor, this);
            outputDisplay = new WGTextArea(outputBounds.getX(), outputBounds.getY(), outputBounds.getWidth(), outputBounds.getHeight(), borderSize, new String[0], outputFont, WGTextArea.TEXT_STYLE_LEFT, textColor, backgroundColor, borderColor, scrollbarColor, this);
        }
        catch(WGNullParentException e) {} //This should NEVER happen
        
        //Set up any animations:
        animationManager.addTimer(updateTime, new StandardUpdateListener());
        animationManager.startAllTimers();
        
        //Set up the filing system:
        try
        {
            errorWriter = new PrintWriter(new FileWriter("Error Log.txt", false));
            outputWriter = new BufferedWriter(new FileWriter("Output Log.txt", false));
        }
        catch(IOException e)
        {
            logError(e);
        }
        
    }
    public void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D)g;
        WestGraphics g3 = new WestGraphics(g2);
        
        //Create the background:
        g2.setColor(backgroundColor);
        g2.fill(getBounds());
        
        //Now draw the upper label showing system is running and working:
        g3.draw(standardDisplayInformation);
        g3.draw(errorOutputDisplay);
        g3.draw(outputDisplay);
    }
    
    public final void logError(Exception e)
    {
        errorOutputDisplay.addTextLine("Error detected");
        if(errorWriter != null)
        {
            e.printStackTrace(errorWriter);
        }
    }
    
    public final void logOutput(String message)
    {
        //Make sure that the writer closes after the application has terminated
        if(!fileWritenTo)
        {
            try
            {
                outputWriter.write("");
            }
            catch(IOException e)
            {
                logError(e);
            }
            
            fileWritenTo = true;
            this.getPaneOwner().addWindowListener(new ApplicationListener());
        }
        outputDisplay.addTextLine(message);
        if(outputWriter != null)
        {
            try
            {
                outputWriter.append(message + "\n");
            }
            catch(IOException e)
            {
                logError(e);
            }
        }
    }
    
    private class StandardUpdateListener implements ActionListener
    {
        private final int runningTickMax = 4;
        private int runningTick = 0;
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String runningText = "Running" + ".".repeat(runningTick);
            runningTick++;
            if(runningTick == runningTickMax)
            {
                runningTick = 0;
            }
            standardDisplayInformation.setTextLine(1, runningText);
        }
    }
    private class ApplicationListener implements WindowListener
    {
        public void windowDeactivated(WindowEvent e){}
        public void windowActivated(WindowEvent e){}
        public void windowDeiconified(WindowEvent e){}
        public void windowIconified(WindowEvent e){}
        public void windowClosed(WindowEvent e){}
        public void windowOpened(WindowEvent e){}
        public void windowClosing(WindowEvent e)
        {
            try
            {
                outputWriter.close();
            }
            catch(Exception e2)
            {
                logError(e2);
            }
        }
    }
}
