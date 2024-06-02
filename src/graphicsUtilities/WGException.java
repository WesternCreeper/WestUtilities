/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package graphicsUtilities;

/**
 *
 * @author Westley
 */
public class WGException extends Exception
{

    /**
     * Creates a new instance of <code>WGException</code> without detail
     * message.
     */
    public WGException() 
    {
        super("Error in graphics!!");
    }

    /**
     * Constructs an instance of <code>WGException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public WGException(String msg) {
        super(msg);
    }
}
