package graphicsUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.OverlayLayout;

import dataStructures.HashTable;

public class MultiplePaneHolder 
{
	public static final String BACKGROUND_PANE = "Background";
	private HashTable<Component> allPanes = new HashTable<Component>(2, HashTable.HASHING_OPTION_LINEAR);
	private ArrayList<Component> allAddedPanes = new ArrayList<Component>(1);
	private Container parent;
	public MultiplePaneHolder(Container parent) 
	{
		this.parent = parent;
		parent.setLayout(new OverlayLayout(parent));
		parent.addComponentListener(new ResizeListener());
	}
	
	public void addPane(String name, Component pane)
	{
		allPanes.insert(name, pane);
		allAddedPanes.add(pane);
		if(parent != pane)
		{
			parent.add(pane);
			pane.setBounds(parent.getBounds());
		}
		if(!name.equals(BACKGROUND_PANE))
		{
			pane.setBackground(new Color(0, 0, 0, 0));
		}
	}
	
	/**
	 * Repaints the given pane by name
	 * @param name name of the pane
	 */
	public void repaint(String name)
	{
		allPanes.find(name).repaint();
	}
	
	public void repaintAllForegroundPanes(Graphics g)
	{
		Component backgroundPane = allPanes.find(BACKGROUND_PANE);
    	for(int i = 0 ; i < allAddedPanes.size() ; i++)
    	{
    		Component currentPane = allAddedPanes.get(i);
    		if(backgroundPane == currentPane)
    		{
    			continue;
    		}
    		currentPane.paint(g);
    	}
	}
	
	/**
	 * Starts a specific frame under a specific FPS
	 * @param name
	 */
	public void startFPS(String name, int targetFPS)
	{
		ScheduledExecutorService repainter = Executors.newScheduledThreadPool(1);
		int refreshRate = (int)(1000D/targetFPS);
        repainter.scheduleAtFixedRate(new RepaintTask(name), refreshRate, refreshRate, TimeUnit.MILLISECONDS);
	}
	
	

	//The repainter class for FPS
    private class RepaintTask implements Runnable
    {
    	private String paneName;
    	RepaintTask(String paneName)
    	{
    		this.paneName = paneName;
    	}
        public void run()
        {
        	allPanes.find(paneName).repaint();
        }
    }
    
    //The resizer class:
    private class ResizeListener implements ComponentListener
    {
        public void componentHidden(ComponentEvent e){}
        public void componentMoved(ComponentEvent e){}
        public void componentShown(ComponentEvent e)
        {
            resizeComps();
        }
        public void componentResized(ComponentEvent e)
        {
            resizeComps();
        }
        
        public void resizeComps()
        {
        	for(int i = 0 ; i < allAddedPanes.size() ; i++)
        	{
        		allAddedPanes.get(i).setBounds(parent.getBounds());
        	}
        }
    }
}
