package graphicsUtilities;

public class WGDragDropNonRelativePaneException extends Exception 
{
    /**
     * Creates a new instance of <code>WGDragDropNonRelativePaneException</code> without
     * detail message.
     */
    public WGDragDropNonRelativePaneException() 
    {
        this("Expected an parent pane. Found that the parent pane was null. Cannot continue construction");
    }

    /**
     * Constructs an instance of <code>WGDragDropNonRelativePaneException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WGDragDropNonRelativePaneException(String msg) 
    {
        super(msg);
    }
}
