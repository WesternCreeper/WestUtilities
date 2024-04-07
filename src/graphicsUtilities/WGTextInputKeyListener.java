/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Westley
 */
public class WGTextInputKeyListener implements KeyListener
{
    private static final int ENTER_KEY = 10;
    private static final int DELETE_KEY = 127;
    private static final int BACKSPACE_KEY = 8;
    private static final int CLEAR_KEY = 27;
    private WGTextInput parent;
    
    /**
     * This sets up a way for the text input to work on the WGTextInput object
     * @param parent The WGTextInput object that is using this to be able to get text
     */
    public WGTextInputKeyListener(WGTextInput parent)
    {
        this.parent = parent;
    }
    
    @Override
    public synchronized void keyTyped(KeyEvent e) 
    {
        if(parent.isFocused())
        {
            int keyCode = (e.getKeyChar() + "").codePointAt(0);
            if(keyCode == BACKSPACE_KEY || keyCode == DELETE_KEY)
            {
                //Deletes characters as needed:
                String str = parent.getText();
                if(str.length() >= 1)
                {
                    str = str.substring(0, str.length()-1);
                    parent.setText(str);
                    e.consume();
                }
            }
            else if(keyCode == CLEAR_KEY)
            {
                parent.setText("");
            }
            else if(keyCode == ENTER_KEY)
            {
                
            }
            else
            {
                //Add the char to the current text
                String str = parent.getText() + e.getKeyChar();
                parent.setText(str);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public synchronized void keyReleased(KeyEvent e) {}
    
}
