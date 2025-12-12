/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import graphicsUtilities.Console;
import graphicsUtilities.WGButton;
import graphicsUtilities.WGButtonListener;
import graphicsUtilities.WGAnimation.WGAAnimationManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGTextArea;
import graphicsUtilities.WGTextInput;
import graphicsUtilities.WGTextInputClickListener;
import graphicsUtilities.WGTextInputKeyListener;
import graphicsUtilities.WestGraphics;
/**
 * This is a standardized communication device with an AI and translates your messages into AI language rather than teaching it a language.
 * @author Westley
 */
public class AIConsole extends Console
{
    private WGTextArea consoleOutput;
    private WGTextInput consoleInput;
    private WGButton consoleReturnButton;
    private WGAAnimationManager animationManager;
    private AICore consoleOwner;
    
    //Graphics variables:
    private final double slotSize = 50;
    private final float standardBorderSize = 5;
    private final Color backgroundColor = Color.rgb(209, 211, 255);
    private final Color scrollbarColor = Color.rgb(149, 151, 201);
    private final Color highlightColor = Color.rgb(195, 219, 191);
    private final Color borderColor = Color.rgb(186, 189, 245);
    private final Color cursorColor = Color.rgb(149, 151, 201);
    private final Color textColor = Color.rgb(196, 175, 16);
    private final Font outputFont = Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 23);
    private final Font inputFont = Font.font("Monospaced", FontWeight.NORMAL, FontPosture.ITALIC, 23);
    private final Font returnButtonFont = Font.font("Serif", FontWeight.BOLD, FontPosture.REGULAR, 23);
    private final Rectangle2D consoleOutputBounds = new Rectangle2D(1 / slotSize, 1/ slotSize, 48/slotSize, 42 / slotSize);
    private final Rectangle2D consoleInputBounds = new Rectangle2D(6 / slotSize, 43/ slotSize, 43/slotSize, 6 / slotSize);
    private final Rectangle2D consoleReturnButtonBounds = new Rectangle2D(1 / slotSize, 43/ slotSize, 5/slotSize, 6 / slotSize);
    
    /**
     * The standard way to communicate with the user
     * @param core This is the owner of the console. Allows for communication between the user and the AI. Otherwise it would be impossible to communicate
     */
    public AIConsole(AICore core)
    {
        consoleOwner = core;
        try
        {
            animationManager = new WGAAnimationManager();
            consoleOutput = new WGTextArea(consoleOutputBounds, standardBorderSize, new String[0], outputFont, WGTextArea.TEXT_STYLE_LEFT, true, textColor, scrollbarColor, this);
            consoleInput = new WGTextInput(consoleInputBounds, standardBorderSize, inputFont, backgroundColor, borderColor, textColor, cursorColor, highlightColor, this, animationManager, new WGTextInputClickListener(), new consoleTextReturn());
            consoleReturnButton = new WGButton(consoleReturnButtonBounds, standardBorderSize, "Return", returnButtonFont, backgroundColor, borderColor, textColor, this, new consoleReturn());
        }
        catch(WGNullParentException e){System.exit(-1);}
        
        this.requestFocus();
    }
    public void draw() 
    {
        GraphicsContext g2 = this.getGraphicsContext2D();
        WestGraphics g3 = new WestGraphics(g2);
        
        g2.setFill(backgroundColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        g3.draw(consoleOutput);
        g3.draw(consoleInput);
        g3.draw(consoleReturnButton);
    }
    public void readInputToOutput(String transferData)
    {
        String inputData[] = transferData.split("\n");
        
        //Brings input to the output:
        consoleInput.setText("");
        for(int i = 0 ; i < inputData.length ; i++)
        {
            //Not wasting time reading an empty string:
            if(!inputData[i].isEmpty())
            {
                consoleOutput.addTextLine(inputData[i]);
                //AI reads the input:
                //For commands:
                String firstCharacter = inputData[i].substring(0,1);
                if(firstCharacter.equals("/"))
                {
                    consoleOwner.getFunctions().command(inputData[i]);
                }
                else //It isn't a command so instead read it for langauge capiblities
                {
                    consoleOwner.getFunctions().read(inputData[i]);
                }
            }
        }
    }
    public void addToOutput(String string)
    {
        consoleOutput.addTextLine(string);
    }
    private class consoleTextReturn extends WGTextInputKeyListener
    {
        public synchronized void enterEvent(KeyEvent e)
        {
            readInputToOutput(consoleInput.getText());
        }
    }
    private class consoleReturn extends WGButtonListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(isWithinBounds(e) && !e.isConsumed())
            {
                String transferText = consoleInput.getText();
                consoleInput.setText("");
                consoleOutput.addTextLine(transferText);
            }
        }
    }
}
