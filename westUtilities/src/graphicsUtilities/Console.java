/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

/**
 *
 * @author Westley
 */
public class Console extends Canvas
{
    private Stage paneOwner;
    /**
     * This creates the console's basic functions then shows it. Starts at 0,0 and the given width/height are its minimums. No extended state set
     * @param title The title of the frame
     * @param width The starting width
     * @param height The starting height
     */
    public void launchConsole(Stage paneOwner, String title, int width, int height)
    {
        this.paneOwner = paneOwner;
        //Set up the frame
        this.paneOwner.setTitle(title);
        this.paneOwner.setWidth(width);
        this.paneOwner.setHeight(height);
        this.paneOwner.setMinWidth(width);
        this.paneOwner.setMinHeight(height);
        this.paneOwner.show();
    }
    /**
     * This creates the console's basic functions then shows it. Starts at 0,0 and the given width/height are its minimums
     * @param title The title of the frame
     * @param width The starting width
     * @param height The starting height
     * @param maximized Whether or not the console is maximized
     */
    public void launchConsole(Stage paneOwner, String title, int width, int height, boolean maximized)
    {
        launchConsole(paneOwner, title, width, height);
        //Set up the frame
        paneOwner.setMaximized(maximized);
    }
    /**
     * This creates the console's basic functions then shows it.
     * @param title The title of the frame
     * @param x The starting x
     * @param y The starting y
     * @param width The starting width
     * @param height The starting height
     * @param maximized Whether or not the console is maximized
     * @param minimumWidth The smallest width the function can be
     * @param minimumHeight The smallest height the function can be
     */
    public void launchConsole(Stage paneOwner, String title, int x, int y, int width, int height, boolean maximized, int minimumWidth, int minimumHeight)
    {
        launchConsole(paneOwner, title, width, height, maximized);
        //Set up the frame
        paneOwner.setX(x);
        paneOwner.setY(y);
        this.paneOwner.setMinWidth(minimumWidth);
        this.paneOwner.setMinHeight(minimumHeight);
    }
    
    //Getter:

    public Stage getPaneOwner() {
        return paneOwner;
    }
}
