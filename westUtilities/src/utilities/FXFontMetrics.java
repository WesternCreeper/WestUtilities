package utilities;


import graphicsUtilities.ColoredString;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FXFontMetrics 
{
    private final Text fm;

    public FXFontMetrics(Font font) 
    {
        this.fm = new Text("");
        fm.setFont(font);
    }

    public double getHeight(String s) 
    { 
    	fm.setText(s);
    	return fm.getLayoutBounds().getHeight(); 
    }
    public double stringWidth(String s) 
    {
    	fm.setText(s);
        return fm.getLayoutBounds().getWidth();
    }

    public static double getHeight(ColoredString s) 
    { 
        Text fm = new Text("");
    	//Find the largest height and return that:
    	int largest = 0;
        fm.setFont(s.getFonts().get(largest));
    	fm.setText(s.getText().get(largest));
    	double largestHeight = fm.getLayoutBounds().getHeight(); 
    	//Check the string heights...
    	for(int i = 1 ; i < s.getText().size() ; i++)
    	{
            fm.setFont(s.getFonts().get(i));
        	fm.setText(s.getText().get(i));
        	double height = fm.getLayoutBounds().getHeight(); 
    		if(height > largestHeight)
    		{
    			largest = i;
    			largestHeight = height;
    		}
    	}
    	fm.setText(s.getText().get(largest));
    	double stringTallest = fm.getLayoutBounds().getHeight();
    	
    	//Now check the image heights:
    	if(s.getImages().size() > 0)
    	{
	    	largest = 0;
	    	largestHeight = s.getImages().get(0).getHeight(); 
	    	for(int i = 1 ; i < s.getImages().size() ; i++)
	    	{
	        	double height = s.getImages().get(i).getHeight(); 
	    		if(height > largestHeight)
	    		{
	    			largest = i;
	    			largestHeight = height;
	    		}
	    	}
    	}
    	if(stringTallest > largestHeight)
    	{
    		return stringTallest;
    	}
    	return largestHeight; 
    }
    public static double stringWidth(ColoredString s) 
    {
        Text fm = new Text("");
    	double totalWidth = 0;
    	//Add the text width
    	for(int i = 0 ; i < s.getText().size(); i++) 
    	{
            fm.setFont(s.getFonts().get(i));
            fm.setText(s.getText().get(i));
    		totalWidth += fm.getLayoutBounds().getWidth();
    	}
    	//Now add the image widths:
    	for(int i = 0 ; i < s.getImages().size(); i++) 
    	{
    		totalWidth += s.getImages().get(i).getWidth();
    	}
    	return totalWidth;
    }
}

