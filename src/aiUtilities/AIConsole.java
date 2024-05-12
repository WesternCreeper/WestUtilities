/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aiUtilities;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
/**
 * This is a standardized communication device with an AI and translates your messages into AI language rather than teaching it a language.
 * @author Westley
 */
public final class AIConsole 
{
    private JFrame consoleFrame = new JFrame();
    private JPanel consolePane = new JPanel(null);
    private JTextArea consoleOutput = new JTextArea();
    private JTextArea consoleInput = new JTextArea();
    private JButton consoleReturnButton = new JButton();
    private AICore consoleOwner;
    /**
     * The standard way to communicate with the user
     * @param core This is the owner of the console. Allows for communication between the user and the AI. Otherwise it would be impossible to communicate
     */
    public AIConsole(AICore core)
    {
        consoleOwner = core;
        setUp();
    }
    private void setUp()
    {
        consoleFrame.setTitle(consoleOwner.getIdentificationName() + "'s Console");
        consoleFrame.setSize(620, 640);
        consoleFrame.setLayout(new GridLayout());
        consoleOutput.setBounds(10, 10, 580, 400);
        consoleOutput.setEditable(false);
        consoleOutput.setLineWrap(true);
        consoleInput.setBounds(10, 420, 580, 80);
        consoleInput.addKeyListener(new consoleTextReturn());
        consoleReturnButton.setBounds(10, 510, 580, 80);
        consoleReturnButton.setText("Return");
        consoleReturnButton.addActionListener(new consoleReturn());
        consolePane.add(consoleOutput);
        consolePane.add(consoleInput);
        consolePane.add(consoleReturnButton);
        consoleFrame.add(consolePane);
    }
    public void launch()
    {
        consoleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        consoleFrame.setVisible(true);
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
                consoleOutput.setText(consoleOutput.getText() + "\n" + inputData[i]);
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
        consoleOutput.setText(consoleOutput.getText() + string);
    }
    public void resetOutput()
    {
        consoleOutput.setText("");
    }
    private class consoleTextReturn implements KeyListener
    {
        public void keyPressed(KeyEvent e){}
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e)
        {
            if(e.getKeyChar() == '\n')
            {
                readInputToOutput(consoleInput.getText());
            }
        }
    }
    private class consoleReturn implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String transferText = consoleInput.getText();
            consoleInput.setText("");
            consoleOutput.setText(consoleOutput.getText() + "\n" + transferText);
        }
    }
}
