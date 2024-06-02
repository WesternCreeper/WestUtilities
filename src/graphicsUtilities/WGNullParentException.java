/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package graphicsUtilities;

/**
 *
 * @author Westley
 * Defines a 
 */
public class WGNullParentException extends WGException
{

    /**
     * Creates a new instance of <code>WGNullParentException</code> without
     * detail message.
     */
    public WGNullParentException() 
    {
        this("Expected a non-null parent. Received a parent that was null. Cannot continue construction");
    }

    /**
     * Constructs an instance of <code>WGNullParentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WGNullParentException(String msg) 
    {
        super(msg);
    }
}
