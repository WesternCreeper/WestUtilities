/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import graphicsUtilities.Console;
import graphicsUtilities.WGButton;
import graphicsUtilities.WGButtonListener;
import graphicsUtilities.WGLabel;
import graphicsUtilities.WGAnimation.WGAAnimationManager;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGTextArea;
import graphicsUtilities.WestGraphics;

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
    //Static Varibles:
    private static Canvas currentParent;
    
    //Final Variables:
    private final double slotSize = 50; //The standard size of the grid. AKA each slotSize is 1/50th of the screen in both x and y directions
    private final float borderSize = 5; //The standard size of the border. AKA the size of the rims around each object
    private final int updateTime = 437;
    private final String outputFile = "Output Log.txt";
    private final String errorFile = "Error Log.txt";
    
    //Display Info variables:
    private final Rectangle2D displayInformationBounds = new Rectangle2D(0, 0, 6/slotSize, 6/slotSize);
    private final String[] displayInformationText = {"Logger v0.2", "Running..."};
    private final Font displayInformationFont = Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 10);
    
    //Error Output variables:
    private final Rectangle2D errorOutputBounds = new Rectangle2D(6/slotSize, 5/slotSize, 20/slotSize, (slotSize - 5)/slotSize);
    private final Rectangle2D errorOutputLabelBounds = new Rectangle2D(6/slotSize, 0, 20/slotSize, 5/slotSize);
    private final Font errorOutputFont = Font.font("Serif", FontWeight.NORMAL, FontPosture.REGULAR, 24);
    
    //Output variables:
    private final Rectangle2D outputBounds = new Rectangle2D(26/slotSize, 5/slotSize, 20/slotSize, (slotSize - 5)/slotSize);
    private final Rectangle2D outputLabelBounds = new Rectangle2D(26/slotSize, 0, 20/slotSize, 5/slotSize);
    private final Font outputFont = Font.font("Serif", FontWeight.NORMAL, FontPosture.REGULAR, 24);
    
    //switch to logs variables:
    private final Rectangle2D switchToLogsBounds = new Rectangle2D(47/slotSize, 1/slotSize, 2/slotSize, 2/slotSize);
    private final Font switchToLogsFont = Font.font("SanSerif", FontWeight.BOLD, FontPosture.REGULAR, 24);
    
    //switch to unit tests variables:
    private final Rectangle2D switchToUnitTestsBounds = new Rectangle2D(47/slotSize, 4/slotSize, 2/slotSize, 2/slotSize);
    private final Font switchToUnitTestsFont = Font.font("SanSerif", FontWeight.BOLD, FontPosture.REGULAR, 24);
    
    //Unit Test variables:
    private final Rectangle2D unitTestDisplayBounds = new Rectangle2D(0, 5/slotSize, 46/slotSize, (slotSize - 5)/slotSize);
    private final Rectangle2D unitTestDisplayLabelBounds = new Rectangle2D(0, 0, 46/slotSize, 5/slotSize);
    private final Font unitTestDisplayFont = Font.font("Monospaced", FontWeight.NORMAL, FontPosture.REGULAR, 24);
    
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
    private boolean outputWriterClosed = false;
    private boolean alertOnOutput = false;
    private PrintWriter errorWriter;
    private BufferedWriter outputWriter;
    
    public Logger(Color backgroundColor, Color borderColor, Color textColor, Color scrollbarColor, Color passColor, Color failColor)
    {
        this(backgroundColor, borderColor, textColor, scrollbarColor, passColor, failColor, true);
    }
    public Logger(Color backgroundColor, Color borderColor, Color textColor, Color scrollbarColor, Color passColor, Color failColor, boolean alertOnOutput)
    {
        this.alertOnOutput = alertOnOutput;
        this.backgroundColor = backgroundColor;
        this.passColor = passColor;
        this.failColor = failColor;
        currentParent = this;
        try
        {
            standardDisplayInformation = new WGTextArea(displayInformationBounds, borderSize, displayInformationText, displayInformationFont, WGTextArea.TEXT_STYLE_LEFT, textColor, this);
            errorOutputDisplay = new WGTextArea(errorOutputBounds, borderSize, new String[0], errorOutputFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, scrollbarColor, this);
            outputDisplay = new WGTextArea(outputBounds, borderSize, new String[0], outputFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, scrollbarColor, this);
            errorOutputDisplayLabel = new WGLabel(errorOutputLabelBounds, borderSize, WGLabel.TEXT_STYLE_MIDDLE, "Error Output:", errorOutputFont, textColor, this);
            outputDisplayLabel = new WGLabel(outputLabelBounds, borderSize, WGLabel.TEXT_STYLE_MIDDLE, "Regular Output:", errorOutputFont, textColor, this);
        
            switchToLogsButton = new WGButton(switchToLogsBounds, borderSize, "Logs", switchToLogsFont, backgroundColor, borderColor, textColor, this, new SwitchToLogsListener());
            switchToUnitTestsButton = new WGButton(switchToUnitTestsBounds, borderSize, "Tests", switchToUnitTestsFont, backgroundColor, borderColor, textColor, this, new SwitchToUnitTestsListener());
        
            unitTestDisplayLabel = new WGLabel(unitTestDisplayLabelBounds, borderSize, WGLabel.TEXT_STYLE_LEFT, "Unit Tests:", unitTestDisplayFont, textColor, this);
            unitTestDisplay = new WGTextArea(unitTestDisplayBounds, borderSize, new String[0], unitTestDisplayFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, scrollbarColor, this);
        }
        catch(WGNullParentException e) {} //This should NEVER happen
        
        //Set up any animations:
        animationManager.addTimer(new KeyFrame(Duration.millis(updateTime), e -> new StandardUpdateListener().actionPerformed()));
        animationManager.startAllTimers();
        
        //Start on the proper page:
        switchToLogsButton.setShown(false);
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
        
        //Make sure the WestGraphics System has the correct mouseEvent always:
        WestGraphics.setUpMouseListener(this);
    }
    @Override
    public void draw() 
    {
        GraphicsContext g2 = this.getGraphicsContext2D();
        WestGraphics g3 = new WestGraphics(g2);
        
        //Create the background:
        g2.setFill(backgroundColor);
        g2.fillRect(0, 0, getPaneOwner().getWidth(), getPaneOwner().getHeight());
        
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
    
    @Override
    public void launchConsole(Stage paneOwner, String title, int width, int height)
    {
        super.launchConsole(paneOwner,title, width, height);
        this.addEventHandler(WindowEvent.ANY, new ApplicationListener());
    }
    @Override
    public void launchConsole(Stage paneOwner, String title, int width, int height, boolean maximized)
    {
        super.launchConsole(paneOwner,title, width, height);
        this.addEventHandler(WindowEvent.ANY, new ApplicationListener());
    }
    @Override
    public void launchConsole(Stage paneOwner, String title, int x, int y, int width, int height, boolean maximized, int minimumWidth, int minimumHeight)
    {
        super.launchConsole(paneOwner,title, x, y, width, height, maximized, minimumWidth, minimumHeight);
        this.addEventHandler(WindowEvent.ANY, new ApplicationListener());
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
        //this.requestFocus();
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
        }
        outputDisplay.addTextLine(message);
        if(outputWriterClosed)
        {
            try
            {
                outputWriter = new BufferedWriter(new FileWriter(outputFile, true));
            }
            catch(Exception e)
            {
                logError(e);
            }
        }
        if(outputWriter != null)
        {
            try
            {
                outputWriter.append(message + "\n");
                if(this.getPaneOwner() == null)
                {
                    outputWriter.close();
                    outputWriterClosed = true;
                }
            }
            catch(IOException e)
            {
                logError(e);
            }
        }
        //Silences the output when needed
        if(alertOnOutput)
        {
            this.requestFocus();
        }
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
        switchToLogsButton.setShown(true);
        switchToUnitTestsButton.setShown(true);
    }
    
    
    private class StandardUpdateListener
    {
        private final int runningTickMax = 4;
        private int runningTick = 0;
        public void actionPerformed() 
        {
            String runningText = "Running" + ".".repeat(runningTick);
            runningTick++;
            if(runningTick == runningTickMax)
            {
                runningTick = 0;
            }
            standardDisplayInformation.setTextLine(1, runningText);
            draw();
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
                switchToLogsButton.setShown(false);
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
                switchToUnitTestsButton.setShown(false);
            }
        }
    }
    private static class ApplicationListener implements EventHandler<WindowEvent>
    {
		@Override
		public void handle(WindowEvent e) 
		{
			if(e.getEventType().equals(WindowEvent.WINDOW_SHOWN))
			{
	            WestGraphics.setCurrentActiveParent(currentParent);
			}
			
		}
    }
}
