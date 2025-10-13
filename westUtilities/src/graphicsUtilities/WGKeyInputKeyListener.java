/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Westley
 */
public class WGKeyInputKeyListener implements EventHandler<KeyEvent>
{
    private static final KeyCode BACKSPACE_KEY = KeyCode.BACK_SPACE;
    private static final KeyCode ENTER_KEY = KeyCode.ENTER;
    private static final KeyCode SHIFT_KEY = KeyCode.SHIFT;
    private static final KeyCode ESC_KEY = KeyCode.ESCAPE;
    private static final KeyCode LEFT_ARROW_KEY = KeyCode.KP_LEFT;
    private static final KeyCode UP_ARROW_KEY = KeyCode.KP_UP;
    private static final KeyCode RIGHT_ARROW_KEY = KeyCode.KP_RIGHT;
    private static final KeyCode DOWN_ARROW_KEY = KeyCode.KP_DOWN;
    private static final KeyCode DELETE_KEY = KeyCode.DELETE;
    private static final KeyCode PAUSE_KEY = KeyCode.PAUSE;
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
	public void handle(KeyEvent e) 
	{
		if(e.getEventType().equals(KeyEvent.KEY_TYPED))
		{
			keyTyped(e);
		}
		else if(e.getEventType().equals(KeyEvent.KEY_RELEASED))
		{
			keyReleased(e);
		}
		else if(e.getEventType().equals(KeyEvent.KEY_PRESSED))
		{
			keyPressed(e);
		}
	}
    
    public synchronized final void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}

    public synchronized final void keyReleased(KeyEvent e) 
    {
        if(parent.isFocused())
        {
            keyCodeEvent(e);
            e.consume();
            parent.setFocused(false);
        }
    }
    
    protected synchronized void keyCodeEvent(KeyEvent e) 
    {
        getParent().setText("[" + codePointToString(e.getCode()) + "]");
    }
    private String codePointToString(KeyCode codePoint)
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
            str = codePoint.getChar();
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
