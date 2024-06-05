/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import graphicsUtilities.Console;
import graphicsUtilities.WGAnimation.WGAAnimationManager;
import graphicsUtilities.WGButton;
import graphicsUtilities.WGButtonListener;
import graphicsUtilities.WGLabel;
import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGTextArea;
import graphicsUtilities.WestGraphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Westley
 */
public class Logger extends Console
{
    //Final Variables:
    private final double slotSize = 50; //The standard size of the grid. AKA each slotSize is 1/50th of the screen in both x and y directions
    private final float borderSize = 5; //The standard size of the border. AKA the size of the rims around each object
    private final int updateTime = 875;
    private final String outputFile = "Output Log.txt";
    private final String errorFile = "Error Log.txt";
    
    //Display Info variables:
    private final Rectangle2D.Double displayInformationBounds = new Rectangle2D.Double(0, 0, 6/slotSize, 6/slotSize);
    private final String[] displayInformationText = {"Logger v0.2", "Running..."};
    private final Font displayInformationFont = new Font("Monospaced", Font.BOLD, 10);
    
    //Error Output variables:
    private final Rectangle2D.Double errorOutputBounds = new Rectangle2D.Double(6/slotSize, 5/slotSize, 20/slotSize, (slotSize - 5)/slotSize);
    private final Rectangle2D.Double errorOutputLabelBounds = new Rectangle2D.Double(6/slotSize, 0, 20/slotSize, 5/slotSize);
    private final Font errorOutputFont = new Font("Serif", 24, Font.PLAIN);
    
    //Output variables:
    private final Rectangle2D.Double outputBounds = new Rectangle2D.Double(26/slotSize, 5/slotSize, 20/slotSize, (slotSize - 5)/slotSize);
    private final Rectangle2D.Double outputLabelBounds = new Rectangle2D.Double(26/slotSize, 0, 20/slotSize, 5/slotSize);
    private final Font outputFont = new Font("Serif", Font.PLAIN, 24);
    
    //switch to logs variables:
    private final Rectangle2D.Double switchToLogsBounds = new Rectangle2D.Double(47/slotSize, 1/slotSize, 2/slotSize, 2/slotSize);
    private final Font switchToLogsFont = new Font("SanSerif", Font.BOLD, 24);
    
    //switch to unit tests variables:
    private final Rectangle2D.Double switchToUnitTestsBounds = new Rectangle2D.Double(47/slotSize, 4/slotSize, 2/slotSize, 2/slotSize);
    private final Font switchToUnitTestsFont = new Font("SanSerif", Font.BOLD, 24);
    
    //Unit Test variables:
    private final Rectangle2D.Double unitTestDisplayBounds = new Rectangle2D.Double(0, 5/slotSize, 46/slotSize, (slotSize - 5)/slotSize);
    private final Rectangle2D.Double unitTestDisplayLabelBounds = new Rectangle2D.Double(0, 0, 46/slotSize, 5/slotSize);
    private final Font unitTestDisplayFont = new Font("Monospaced", Font.PLAIN, 24);
    
    //Dynamic Variables
    private Color backgroundColor;
    private Color passColor;
    private Color failColor;
    private WGTextArea standardDisplayInformation;
    private WGTextArea errorOutputDisplay;
    private WGTextArea outputDisplay;
    private WGButton switchToLogsButton;
    private WGButton switchToUnitTestsButton;
    private WGLabel errorOutputDisplayLabel;
    private WGLabel outputDisplayLabel;
    private WGAAnimationManager animationManager = new WGAAnimationManager();
    
    //Standard System:
    private final int LOGS_PAGE = 0;
    private final int UNIT_TESTS_PAGE = 1;
    private int page = 0;
    
    //Unit Test System:
    private ArrayList<UnitTest> allTests = new ArrayList<UnitTest>(1);
    private WGLabel unitTestDisplayLabel;
    private WGTextArea unitTestDisplay;
    
    //File System:
    private boolean fileWritenTo = false;
    private PrintWriter errorWriter;
    private BufferedWriter outputWriter;
    
    public Logger(Color backgroundColor, Color borderColor, Color textColor, Color scrollbarColor, Color passColor, Color failColor)
    {
        this.backgroundColor = backgroundColor;
        this.passColor = passColor;
        this.failColor = failColor;
        try
        {
            standardDisplayInformation = new WGTextArea(displayInformationBounds, borderSize, displayInformationText, displayInformationFont, WGTextArea.TEXT_STYLE_LEFT, textColor, this);
            errorOutputDisplay = new WGTextArea(errorOutputBounds, borderSize, new String[0], errorOutputFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, backgroundColor, borderColor, scrollbarColor, this);
            outputDisplay = new WGTextArea(outputBounds, borderSize, new String[0], outputFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, backgroundColor, borderColor, scrollbarColor, this);
            errorOutputDisplayLabel = new WGLabel(errorOutputLabelBounds, borderSize, WGLabel.TEXT_STYLE_MIDDLE, "Error Output:", errorOutputFont, textColor, this);
            outputDisplayLabel = new WGLabel(outputLabelBounds, borderSize, WGLabel.TEXT_STYLE_MIDDLE, "Regular Output:", errorOutputFont, textColor, this);
        
            switchToLogsButton = new WGButton(switchToLogsBounds, borderSize, "Logs", switchToLogsFont, backgroundColor, borderColor, textColor, this, new SwitchToLogsListener());
            switchToUnitTestsButton = new WGButton(switchToUnitTestsBounds, borderSize, "Tests", switchToUnitTestsFont, backgroundColor, borderColor, textColor, this, new SwitchToUnitTestsListener());
        
            unitTestDisplayLabel = new WGLabel(unitTestDisplayLabelBounds, borderSize, WGLabel.TEXT_STYLE_LEFT, "Unit Tests:", unitTestDisplayFont, textColor, this);
            unitTestDisplay = new WGTextArea(unitTestDisplayBounds, borderSize, new String[0], unitTestDisplayFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, backgroundColor, borderColor, scrollbarColor, this);
        }
        catch(WGNullParentException e) {} //This should NEVER happen
        
        //Set up any animations:
        animationManager.addTimer(updateTime, new StandardUpdateListener());
        animationManager.startAllTimers();
        
        //Start on the proper page:
        switchToLogsButton.setIsShown(false);
        page = LOGS_PAGE;
        
        //Set up the filing system:
        try
        {
            outputWriter = new BufferedWriter(new FileWriter(outputFile, false));
            errorWriter = new PrintWriter(new FileWriter(errorFile, false));
            errorWriter.write("");
        }
        catch(IOException e)
        {
            try //Try to make the file if it doesn't exist, else log it
            {
                File outputFile = new File(this.outputFile);
                if(!outputFile.exists())
                {
                    outputFile.createNewFile();
                }
            }
            catch(IOException e3)
            {
                logError(e);
            }
        }
        
    }
    public void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D)g;
        WestGraphics g3 = new WestGraphics(g2);
        
        //Create the background:
        g2.setColor(backgroundColor);
        g2.fill(getBounds());
        
        //Buttons:
        g3.draw(switchToLogsButton);
        g3.draw(switchToUnitTestsButton);
        
        //logs Page:
        if(LOGS_PAGE == page)
        {
            g3.draw(standardDisplayInformation);
            g3.draw(errorOutputDisplay);
            g3.draw(outputDisplay);
            g3.draw(errorOutputDisplayLabel);
            g3.draw(outputDisplayLabel);
        }
        
        //Unit Tests Page:
        if(UNIT_TESTS_PAGE == page)
        {
            g3.draw(unitTestDisplayLabel);
            g3.draw(unitTestDisplay);
        }
    }
    
    public final void logError(Exception e)
    {
        String errorMessage = e.getLocalizedMessage();
        if(errorMessage != null)
        {
            errorOutputDisplay.addTextLine(errorMessage);
        }
        else
        {
            errorOutputDisplay.addTextLine("Error Detected. Check the Error File for more information");
        }
        
        try
        {
            errorWriter = new PrintWriter(new FileWriter(errorFile, true));

            e.printStackTrace(errorWriter);

            errorWriter.close();
        }
        catch(IOException e2)
        {
            try //Try to make the file if it doesn't exist, else just exit
            {
                File errorFile = new File(this.errorFile);
                if(!errorFile.exists())
                {
                    errorFile.createNewFile();
                }
            }
            catch(IOException e3)
            {
                System.exit(-1);
            }
        }
        this.requestFocus();
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
        this.requestFocus();
    }
    
    public final void testUnitTest(UnitTest test)
    {
        //Now add the result of the test to the unit test text area:
        Color textColor = passColor;
        if(!test.getResult())
        {
            textColor = failColor;
        }
        unitTestDisplay.addTextLine(test.toString(), textColor);
    }
    
    public final void testUnitTest(String title, String expectedResult, String result)
    {
        testUnitTest(new UnitTest(title, expectedResult, result));
    }
    
    public void showAllPageButtons()
    {
        switchToLogsButton.setIsShown(true);
        switchToUnitTestsButton.setIsShown(true);
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
    
    //Button classes:
    
    
    private class SwitchToLogsListener extends WGButtonListener
    {
        public void mouseClicked(MouseEvent e) 
        {
            if(isWithinBounds(e))
            {
                page = LOGS_PAGE;
                showAllPageButtons();
                switchToLogsButton.setIsShown(false);
            }
        }
    }
    
    private class SwitchToUnitTestsListener extends WGButtonListener
    {
        public void mouseClicked(MouseEvent e) 
        {
            if(isWithinBounds(e))
            {
                page = UNIT_TESTS_PAGE;
                showAllPageButtons();
                switchToUnitTestsButton.setIsShown(false);
            }
        }
    }
}
