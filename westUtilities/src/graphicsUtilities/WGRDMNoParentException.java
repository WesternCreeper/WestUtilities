package graphicsUtilities;

public class WGRDMNoParentException extends WGException
{
    /**
     * Creates a new instance of <code>WGRDMNoParentException</code> without
     * detail message.
     */
    public WGRDMNoParentException() 
    {
        this("Expected an parent pane. Found that the parent pane was null. Cannot continue construction");
    }

    /**
     * Constructs an instance of <code>WGRDMNoParentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WGRDMNoParentException(String msg) 
    {
        super(msg);
    }

}
