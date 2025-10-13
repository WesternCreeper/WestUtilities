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
public class WGTextInputKeyListener implements EventHandler<KeyEvent>
{
    private static final KeyCode BACKSPACE_KEY = KeyCode.BACK_SPACE;
    private static final KeyCode ENTER_KEY = KeyCode.ENTER;
    private static final KeyCode SHIFT_KEY = KeyCode.SHIFT;
    private static final KeyCode CLEAR_KEY = KeyCode.CLEAR;
    private static final KeyCode LEFT_ARROW_KEY = KeyCode.KP_LEFT;
    private static final KeyCode UP_ARROW_KEY = KeyCode.KP_UP;
    private static final KeyCode RIGHT_ARROW_KEY = KeyCode.KP_RIGHT;
    private static final KeyCode DOWN_ARROW_KEY = KeyCode.KP_DOWN;
    private static final KeyCode DELETE_KEY = KeyCode.DELETE;
    private boolean alphabeticalAllowed = false;
    private boolean numericAllowed = false;
    private boolean whitespaceAllowed = false;
    private boolean allAllowed = true;
    private WGTextInput parent;
    
    /**
     * Use ONLY with subclasses and make sure you know that the parent is NOT null by the time it is listening in to the object
     */
    public WGTextInputKeyListener() {}
    /**
     * This sets up a way for the text input to work on the WGTextInput object
     * @param parent The WGTextInput object that is using this to be able to get text
     */
    public WGTextInputKeyListener(WGTextInput parent)
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
    
    public synchronized final void keyTyped(KeyEvent e) 
    {
        if(parent.isFocused())
        {
            KeyCode keyCode = e.getCode();
            if(keyCode == BACKSPACE_KEY)
            {
                backspaceEvent(e);
            }
            else if(keyCode == DELETE_KEY)
            {
                deleteEvent(e);
            }
            else if(keyCode == CLEAR_KEY)
            {
                clearEvent(e);
            }
            else if(keyCode == ENTER_KEY)
            {
                enterEvent(e);
            }
            else
            {
                standardEvent(e);
            }
            e.consume();
        }
    }

    public synchronized void keyPressed(KeyEvent e) 
    {
        if(parent.isFocused())
        {
            if(e.getCode() == SHIFT_KEY)
            {
                shiftEvent(e, true);
            }
            e.consume();
        }
    }

    public synchronized final void keyReleased(KeyEvent e) 
    {
        if(parent.isFocused())
        {
            keyCodeEvent(e);
            KeyCode keyCode = e.getCode();
            if(keyCode == LEFT_ARROW_KEY)
            {
                leftArrowEvent(e);
            }
            else if(keyCode == RIGHT_ARROW_KEY)
            {
                rightArrowEvent(e);
            }
            else if(keyCode == UP_ARROW_KEY)
            {
                upArrowEvent(e);
            }
            else if(keyCode == DOWN_ARROW_KEY)
            {
                downArrowEvent(e);
            }
            else if(keyCode == SHIFT_KEY)
            {
                shiftEvent(e, false);
            }
            e.consume();
        }
    }
    /**
     * This is an override-able function that gets called whenever ANY KEY is pressed
     * @param e The key event called from the keyTyped function
     */
    protected synchronized void keyCodeEvent(KeyEvent e) {}
    /**
     * This is an override-able function that gets called when the enter key is pressed
     * @param e The key event called from the keyTyped function
     */
    protected synchronized void enterEvent(KeyEvent e)
    {
        parent.setHighlightShown(false);
        parent.setFocused(false);
    }
    /**
     * This is an override-able function that gets called when the shift key is pressed
     * @param e The key event called from the keyTyped function
     * @param state The state of the button press, either true(pressed) or false(released)
     */
    protected synchronized void shiftEvent(KeyEvent e, boolean state)
    {
        parent.setShiftHeld(state);
    }
    /**
     * This is an override-able function that gets called when the delete key is pressed
     * @param e The key event called from the keyTyped function
     */
    protected synchronized void deleteEvent(KeyEvent e)
    {
        String text = parent.getText();
        String str = text;
        //Delete the text in highlight:
        if(parent.isHighlightShown())
        {
            highlightDelete();
            return;
        }
        //Deletes characters on the forward side
        text = parent.getText();
        int place = parent.getCursorPosition();
        if(str.length() >= 1 && place < str.length())
        {
            str = text.substring(0, place);
            str += text.substring(place+1);
            parent.setText(str);
        }
    }
    /**
     * This is an override-able function that gets called when the backspace key is pressed
     * @param e The key event called from the keyTyped function
     */
    protected synchronized void backspaceEvent(KeyEvent e)
    {
        String text = parent.getText();
        String str = text;
        //Delete the text in highlight:
        if(parent.isHighlightShown())
        {
            highlightDelete();
            return;
        }
        //Deletes characters as needed:
        text = parent.getText();
        int place = parent.getCursorPosition();
        if(str.length() >= 1 && place > 0)
        {
            str = str.substring(0, place-1);
            str += text.substring(place);
            parent.setCursorPosition(parent.getCursorPosition() - 1);
            parent.setText(str);
        }
    }
    /**
     * This is an override-able function that gets called when the clear key is pressed
     * @param e The key event called from the keyTyped function
     */
    protected synchronized void clearEvent(KeyEvent e)
    {
        //Delete the text in highlight:
        if(parent.isHighlightShown())
        {
            highlightDelete();
            return;
        }
        parent.setHighlightShown(false);
        parent.setText("");
    }
    /**
     * This is an override-able function that gets called when any other key is pressed
     * @param e The key event called from the keyTyped function
     */
    protected synchronized void standardEvent(KeyEvent e)
    {
        String text = parent.getText();
        String str = text;
        //Delete the text in highlight:
        if(parent.isHighlightShown())
        {
            highlightDelete();
        }
        text = parent.getText();
        
        //Check that the character can be added to the text based on the masks.
        boolean allowed = allAllowed;
        char addedChar = e.getCharacter().charAt(0);
        if(alphabeticalAllowed && Character.isAlphabetic(addedChar))
        {
            allowed = true;
        }
        else if(numericAllowed && Character.isDigit(addedChar))
        {
            allowed = true;
        }
        else if(whitespaceAllowed  && Character.isWhitespace(addedChar))
        {
            allowed = true;
        }
        
        //Add the char to the current text
        if(allowed)
        {
            int place = parent.getCursorPosition();
            if(place > text.length()) 
            {
            	place = text.length();
            }
            str = text.substring(0, place);
            str += addedChar + parent.getText().substring(place);
            parent.setText(str);
            parent.setCursorPosition(place + 1);
        }
    }
    /**
     * This is an override-able function that gets called when the left arrow key is pressed
     * @param e The key event called from the keyReleased function
     */
    protected synchronized void leftArrowEvent(KeyEvent e)
    {
        if(parent.getCursorPosition() >= 1)
        {
            parent.setCursorPosition(parent.getCursorPosition() - 1);
            if(e.isShiftDown())
            {
                highlightMove(parent.getCursorPosition(), parent.getCursorPosition()+1);
            }
        }
        if(!e.isShiftDown())
        {
            parent.setHighlightShown(false);
        }
    }
    /**
     * This is an override-able function that gets called when the right arrow key is pressed
     * @param e The key event called from the keyReleased function
     */
    protected synchronized void rightArrowEvent(KeyEvent e)
    {
        int parentStringSize = parent.getText().length();
        if(parent.getCursorPosition() < parentStringSize)
        {
            parent.setCursorPosition(parent.getCursorPosition() + 1);
            if(e.isShiftDown())
            {
                highlightMove(parent.getCursorPosition(), parent.getCursorPosition()-1);
            }
        }
        if(!e.isShiftDown())
        {
            parent.setHighlightShown(false);
        }
    }
    /**
     * This is an override-able function that gets called when the up arrow key is pressed
     * @param e The key event called from the keyReleased function
     */
    protected synchronized void upArrowEvent(KeyEvent e)
    {
        int beforePos = parent.getCursorPosition();
        int parentStringSize = parent.getText().length();
        parent.setCursorPosition(parentStringSize);
        if(e.isShiftDown())
        {
            highlightMove(parentStringSize, beforePos);
        }
        else
        {
            parent.setHighlightShown(false);
        }
    }
    /**
     * This is an override-able function that gets called when the down arrow key is pressed
     * @param e The key event called from the keyReleased function
     */
    protected synchronized void downArrowEvent(KeyEvent e)
    {
        int beforePos = parent.getCursorPosition();
        parent.setCursorPosition(0);
        if(e.isShiftDown())
        {
            highlightMove(0, beforePos);
        }
        else
        {
            parent.setHighlightShown(false);
        }
    }
    private synchronized void highlightDelete()
    {
        String text = parent.getText();
        String str = text;
        int highlightStart = parent.getHighlightStart();
        int highlightEnd = parent.getHighlightEnd();
        if(highlightStart > highlightEnd)
        {
            int temp = highlightStart;
            highlightStart = highlightEnd;
            highlightEnd = temp;
        }
        str = text.substring(0, highlightStart);
        if(highlightEnd < text.length())
        {
            str += text.substring(highlightEnd);
        }
        parent.setText(str);
        parent.setHighlightShown(false);
        parent.setCursorPosition(highlightStart);
        if(parent.getCursorPosition() > str.length())
        {
            parent.setCursorPosition(str.length());
        }
    }
    private synchronized void highlightMove(int newX, int startX)
    {
        if(!parent.isHighlightShown())
        {
            parent.setHighlightStart(startX);
        }
        parent.setHighlightEnd(newX);
        parent.setHighlightShown(true);
    }
    
    //Mask Methods:
    /**
     * This makes all of the characters allowed to be inputted to the standardEvent() function, by resetting all of the masks and allowing all
     */
    public synchronized void resetAllCharacterMasks()
    {
        alphabeticalAllowed = false;
        numericAllowed = false;
        allAllowed = true;
    }
    /**
     * Allows all alphabetical characters. Removes allowing all
     * @param state The state (False = not allowed)
     */
    public synchronized void setAlphaMask(boolean state)
    {
        alphabeticalAllowed = state;
        allAllowed = false;
    }
    /**
     * Allows all numeric characters. Removes allowing all
     * @param state The state (False = not allowed)
     */
    public synchronized void setNumericMask(boolean state)
    {
        numericAllowed = state;
        allAllowed = false;
    }
    /**
     * Allows white space characters. Removes allowing all
     * @param state The state (False = not allowed)
     */
    public synchronized void setWhitespaceMask(boolean state)
    {
        whitespaceAllowed = state;
        allAllowed = false;
    }
    
    //Setter:
    public void setParent(WGTextInput parent) {
        this.parent = parent;
    }
    //Getter:
    public WGTextInput getParent() {
        return parent;
    }
}
