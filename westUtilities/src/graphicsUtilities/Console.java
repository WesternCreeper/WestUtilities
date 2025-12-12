/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphicsUtilities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Westley
 */
public class Console extends Canvas
{
    //Repainter:
    private final int REFRESH_RATE = 16;
    private Timeline repainter;
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
        Group group = new Group();
        group.getChildren().add(this);
        Scene scene = new Scene(group);
        scene.widthProperty().addListener((e, old, newWidth) -> {
        	Platform.runLater(() -> {
        	repainter.stop();
        	this.setWidth(newWidth.doubleValue());
        	repainter.play();
        	});
        });
        scene.heightProperty().addListener((e, old, newHeight) -> {
        	Platform.runLater(() -> {
        	repainter.stop();
        	this.setHeight(newHeight.doubleValue());
        	repainter.play();
        	});
        });
        this.paneOwner.setScene(scene);
        this.paneOwner.show();
        this.setWidth(width);
        this.setHeight(height);
        
        //Set up the repainter
        repainter = new Timeline(new KeyFrame(Duration.millis(REFRESH_RATE), e -> draw()));
        repainter.setCycleCount(Animation.INDEFINITE);
        repainter.play();
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
    
    //Methods:
    public void draw() {}
    
    public void closeDown()
    {
    	repainter.stop();
    }
    
    //Getter:
    public Stage getPaneOwner() {
        return paneOwner;
    }
}
