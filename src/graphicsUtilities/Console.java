/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Westley
 */
public class Console extends JPanel
{
    private JFrame paneOwner;
    /**
     * This creates the console's basic functions then shows it. Starts at 0,0 and the given width/height are its minimums. No extended state set
     * @param title The title of the frame
     * @param width The starting width
     * @param height The starting height
     * @param closeOperation The default operation this frame undergoes when closed
     */
    public void launchConsole(String title, int width, int height, int closeOperation)
    {
        paneOwner = new JFrame();
        //Set up the frame
        paneOwner.setTitle(title);
        paneOwner.setBounds(0, 0, width, height);
        paneOwner.setMinimumSize(new Dimension(width, height));
        paneOwner.setDefaultCloseOperation(closeOperation);
        paneOwner.add(this);
        paneOwner.setVisible(true);
    }
    /**
     * This creates the console's basic functions then shows it. Starts at 0,0 and the given width/height are its minimums
     * @param title The title of the frame
     * @param width The starting width
     * @param height The starting height
     * @param extendedState The state that it is in. see setExtendedState
     * @param closeOperation The default operation this frame undergoes when closed
     */
    public void launchConsole(String title, int width, int height, int extendedState, int closeOperation)
    {
        launchConsole(title, width, height, closeOperation);
        //Set up the frame
        paneOwner.setExtendedState(extendedState);
    }
    /**
     * This creates the console's basic functions then shows it.
     * @param title The title of the frame
     * @param x The starting x
     * @param y The starting y
     * @param width The starting width
     * @param height The starting height
     * @param extendedState The state that it is in. see setExtendedState
     * @param minimumWidth The smallest width the function can be
     * @param minimumHeight The smallest height the function can be
     * @param closeOperation The default operation this frame undergoes when closed
     */
    public void launchConsole(String title, int x, int y, int width, int height, int extendedState, int minimumWidth, int minimumHeight, int closeOperation)
    {
        launchConsole(title, width, height, extendedState, closeOperation);
        //Set up the frame
        paneOwner.setBounds(x, y, width, height);
        paneOwner.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
    }
    
    //Getter:

    public JFrame getPaneOwner() {
        return paneOwner;
    }
}
