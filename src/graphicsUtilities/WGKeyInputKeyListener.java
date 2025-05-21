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
public class WGKeyInputKeyListener implements KeyListener
{
    private static final int BACKSPACE_KEY = 8;
    private static final int ENTER_KEY = 10;
    private static final int SHIFT_KEY = 16;
    private static final int ESC_KEY = 27;
    private static final int LEFT_ARROW_KEY = 37;
    private static final int UP_ARROW_KEY = 38;
    private static final int RIGHT_ARROW_KEY = 39;
    private static final int DOWN_ARROW_KEY = 40;
    private static final int DELETE_KEY = 127;
    private static final int PAUSE_KEY = 19;
    private WGKeyInput parent;
    
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGKeyInputKeyListener() {}
    /**
     * This sets up a way for the text input to work on the WGTextInput object
     * @param parent The WGTextInput object that is using this to be able to get text
     */
    public WGKeyInputKeyListener(WGKeyInput parent)
    {
        this.parent = parent;
    }
    
    @Override
    public synchronized final void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public synchronized final void keyReleased(KeyEvent e) 
    {
        if(parent.isFocused())
        {
            keyCodeEvent(e);
            e.consume();
            parent.setFocused(false);
        }
        WestGraphics.doRepaintJob(parent.getParent());
    }
    
    protected synchronized void keyCodeEvent(KeyEvent e) 
    {
        if(e.getExtendedKeyCode() == 0)
        {
            return;
        }
        getParent().setText("[" + codePointToString(e.getExtendedKeyCode()) + "]");
    }
    private String codePointToString(int codePoint)
    {
        String str = "";
        if(BACKSPACE_KEY == codePoint)
        {
            str = "BACKSPACE";
        }
        else if(PAUSE_KEY == codePoint)
        {
            str = "PAUSE";
        }
        else if(ENTER_KEY == codePoint)
        {
            str = "ENTER";
        }
        else if(SHIFT_KEY == codePoint)
        {
            str = "SHIFT";
        }
        else if(ESC_KEY == codePoint)
        {
            str = "ESC";
        }
        else if(LEFT_ARROW_KEY == codePoint)
        {
            str = "LEFT ARROW";
        }
        else if(UP_ARROW_KEY == codePoint)
        {
            str = "UP ARROW";
        }
        else if(RIGHT_ARROW_KEY == codePoint)
        {
            str = "RIGHT ARROW";
        }
        else if(DOWN_ARROW_KEY == codePoint)
        {
            str = "DOWN ARROW";
        }
        else if(DELETE_KEY == codePoint)
        {
            str = "DELETE";
        }
        else
        {
            str = Character.toString(codePoint);
        }
        return str;
    }
    
    //Setter:
    public void setParent(WGKeyInput parent) {
        this.parent = parent;
    }
    //Getter:
    public WGKeyInput getParent() {
        return parent;
    }
}
