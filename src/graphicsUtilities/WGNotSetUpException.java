/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package graphicsUtilities;

/**
 *
 * @author Westley
 */
public class WGNotSetUpException extends Exception
{

    /**
     * Creates a new instance of <code>WGNotSetUpException</code> without detail
     * message.
     */
    public WGNotSetUpException() 
    {
        this("Error: Cannot run function. Reason: Function was not set up. Please set up the function first!");
    }

    /**
     * Constructs an instance of <code>WGNotSetUpException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WGNotSetUpException(String msg) 
    {
        super(msg);
    }
}
