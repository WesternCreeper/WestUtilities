package utilities;


import graphicsUtilities.ColoredString;
import graphicsUtilities.FormattedString;
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

    public double getHeight(FormattedString s) 
    { 
    	fm.setText(s.getText());
    	return fm.getLayoutBounds().getHeight(); 
    }
    public double stringWidth(FormattedString s) 
    {
    	Font oldFont = fm.getFont();
        fm.setFont(s.getFont());
    	fm.setText(s.getText());
        fm.setFont(oldFont);
        return fm.getLayoutBounds().getWidth();
    }

    public double getHeight(ColoredString s) 
    { 
    	//Find the largest height and return that:
    	int largest = 0;
    	Font oldFont = fm.getFont();
        fm.setFont(s.getText().get(largest).getFont());
    	fm.setText(s.getText().get(largest).getText());
    	double largestHeight = fm.getLayoutBounds().getHeight(); 
    	for(int i = 1 ; i < s.getText().size() ; i++)
    	{
            fm.setFont(s.getText().get(i).getFont());
        	fm.setText(s.getText().get(i).getText());
        	double height = fm.getLayoutBounds().getHeight(); 
    		if(height > largestHeight)
    		{
    			largest = i;
    			largestHeight = height;
    		}
    	}
    	fm.setText(s.getText().get(largest).getText());
        fm.setFont(oldFont);
    	return fm.getLayoutBounds().getHeight(); 
    }
    public double stringWidth(ColoredString s) 
    {
    	double totalWidth = 0;
    	for(int i = 0 ; i < s.getText().size(); i++) 
    	{
    		totalWidth += stringWidth(s.getText().get(i));
    	}
    	return totalWidth;
    }
}

