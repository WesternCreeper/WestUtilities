package utilities;


import java.util.ArrayList;

import graphicsUtilities.ColoredString;
import javafx.scene.paint.Paint;
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
    public static ArrayList<ColoredString> wrapLine(ColoredString s, double width)
    {
    	ArrayList<ColoredString> wrappedLine = new ArrayList<ColoredString>(1);
    	
    	//Start with the easy stuff, if the whole line fits, return it:
    	if(stringWidth(s) <= width)
    	{
    		wrappedLine.add(s);
    	}
    	else
    	{
    		while(stringWidth(s) > width)
    		{
	    		//Now determine where in the string the break should occur:
	    		int textClosest = 1;
	        	for(int i = 1 ; i < s.getText().size(); i++) 
	        	{
	        		if(s.getTextOffsets().get(i) > width)
	        		{
	        			textClosest = i;
	        			break;
	        		}
	        	}
	        	//Now add the image widths:
	    		int imageClosest = -1;
	    		if(s.getImageOffsets().size() > 0)
	    		{
	    			imageClosest = 0;
		        	for(int i = 1 ; i < s.getImageOffsets().size(); i++) 
		        	{
		        		if(s.getImageOffsets().get(i) > width)
		        		{
		        			imageClosest = i;
		        			break;
		        		}
		        	}
	    		}
	    		
	    		//Figure out which arrayList needs to be broken:
	    		if(imageClosest == -1)
	    		{
	    			//No need to worry about the images, so just split the text:
	    			
	    			//Start by breaking the string at the index previous, because we found where the string breaks down:
	    			String str = s.getText().get(textClosest-1);
	    			Font font = s.getFonts().get(textClosest-1);
	    			Paint color = s.getColors().get(textClosest-1);
	    			
	    			//Now create the break as needed:
	    			FXFontMetrics fm = new FXFontMetrics(font);
	                //Now determine where in the string is the best location to split:
	                //Search Binarially:
	                int minimumSize = 0;
	                int maximumSize = str.length();
	                int sizeSplit = str.length();
	                while(true)
	                {
	                    int currentSize = (int)((minimumSize + maximumSize) /2.0);
	                    String test = str.substring(0, currentSize);
	                    double testLength = fm.stringWidth(test);
	                    if(testLength >= width)
	                    {
	                        maximumSize = currentSize;
	                    }
	                    else if(testLength < width)
	                    {
	                        minimumSize = currentSize;
	                    }
	                    if(minimumSize == maximumSize)
	                    {
	                        sizeSplit = minimumSize;
	                        break;
	                    }
	                    else if(minimumSize+1 == maximumSize) //Make sure to use the correct one:
	                    {
	                        test = str.substring(0, maximumSize);
	                        testLength = fm.stringWidth(test);
	                        if(testLength >= width)
	                        {
	                            sizeSplit = minimumSize;
	                        }
	                        if(testLength < width)
	                        {
	                            sizeSplit = maximumSize;
	                        }
	                        break;
	                    }
	                }
	                //Now Do stuff:
	                String thisLine = str.substring(0, sizeSplit+1);
	                String newLine = str.substring(sizeSplit+1);
	                //Now try to keep words together:
	                if(thisLine.contains(" "))
	                {
	                    //Split at the space instead if can:
	                    sizeSplit = thisLine.lastIndexOf(" ");
	                    thisLine = str.substring(0, sizeSplit+1);
	                    newLine = str.substring(sizeSplit+1);
	                }
	                
	                //Now reconstruct the old string and make the new string:
	                ColoredString beginningString = new ColoredString();
	                
	                for(int i = 0 ; i <= textClosest-1 ; i++)
	                {
	                	if(i == textClosest-1)
	                	{
	                		beginningString.addString(thisLine, font, color);
	                	}
	                	else
	                	{
	                		beginningString.addString(s.getText().get(i), s.getFonts().get(i), s.getColors().get(i));
	                	}
	                }
	                
	                //Now construct the new s object:
	                ColoredString enddingString = new ColoredString();
	                
	                for(int i = textClosest-1 ; i < s.getText().size() ; i++)
	                {
	                	if(i == textClosest-1)
	                	{
	                		enddingString.addString(newLine, font, color);
	                	}
	                	else
	                	{
	                		enddingString.addString(s.getText().get(i), s.getFonts().get(i), s.getColors().get(i));
	                	}
	                }
	                s = enddingString;
	                wrappedLine.add(beginningString);
	    		}
	    		else
	    		{
	    			//Now we have to determine which one is closer, it is better to break at a closer point rather than a further point:
	    			if(s.getImageOffsets().get(imageClosest) > s.getTextOffsets().get(textClosest))
	    			{
	    				//The split should be with the images:
	    				
	    				//First determine if the break is a image-image or a string-image break:
	    				int lastImage = imageClosest - 1;
	    				int lastText = 0;
	    	        	for(int i = 1 ; i < s.getText().size(); i++) 
	    	        	{
	    	        		if(s.getTextOffsets().get(i) > s.getImageOffsets().get(imageClosest))
	    	        		{
	    	        			lastText = i-1;
	    	        			break;
	    	        		}
	    	        	}
	    	        	
	    	        	//Now that we know what is behind us, we know how to break:
	    	        	
	    	        	//Make the first string and the last string:
	                    ColoredString beginningString;
	                    ColoredString enddingString;
	    	        	if(lastImage >= 0 && s.getImageOffsets().get(lastImage) > s.getTextOffsets().get(lastText)) //Break between images
	    	        	{
	    	        		beginningString = constructBeginningString(s, lastImage, -1);
	    	        		enddingString = constructEnddingString(s, lastImage+1, lastText+1);
	    	        	}
	    	        	else //Break between string and image
	    	        	{
	    	        		beginningString = constructBeginningString(s, -1, lastText);
	    	        		enddingString = constructEnddingString(s, lastImage, lastText+1);
	    	        	}
	    	        	s = enddingString;
	    	        	wrappedLine.add(beginningString);
	    			}
	    			else
	    			{
	    				int lastImage = 0;
	    	        	for(int i = 1 ; i < s.getImages().size(); i++) 
	    	        	{
	    	        		if(s.getImageOffsets().get(i) > s.getTextOffsets().get(textClosest-1))
	    	        		{
	    	        			lastImage = i;
	    	        			break;
	    	        		}
	    	        	}
	    				//Breaking between strings again, so just do that:
	    				ColoredString beginningString = constructBeginningString(s, -1, textClosest-1);
	    				ColoredString enddingString = constructEnddingString(s, lastImage, textClosest);
	    	        	s = enddingString;
	    	        	wrappedLine.add(beginningString);
	    			}
	    		}
    		}
    		//s is finally small enough, so add it:
    		wrappedLine.add(s);
    	}
    	
    	return wrappedLine;
    }
    private static ColoredString constructBeginningString(ColoredString original, int imageBreak, int textBreak)
    {
    	ColoredString str = new ColoredString();
    	int i = 0;
    	int j = 0;
    	while(i <= textBreak || j <= imageBreak) 
    	{
    		double textIndex = Double.MAX_VALUE;
    		if(i < original.getText().size())
    		{
    			//Then suggest the text index:
    			textIndex = original.getTextOffsets().get(i);
    		}
    		double imageIndex = Double.MAX_VALUE;
    		if(j < original.getImages().size())
    		{
    			//Then suggest the text index:
    			imageIndex = original.getImageOffsets().get(j);
    		}
    		
    		if(imageIndex < textIndex)
			{
    			str.addImage(original.getImages().get(j));
    			j++;
			}
    		else
    		{
    			str.addString(original.getText().get(i), original.getFonts().get(i), original.getColors().get(i));
    			i++;
    		}
    	}
    	
    	return str;
    }
    private static ColoredString constructEnddingString(ColoredString original, int imageBreak, int textBreak)
    {
    	ColoredString str = new ColoredString();
    	int i = imageBreak;
    	int j = textBreak;
    	while(i <= original.getText().size() || j <= original.getImages().size()) 
    	{
    		double textIndex = Double.MAX_VALUE;
    		if(i < original.getText().size())
    		{
    			//Then suggest the text index:
    			textIndex = original.getTextOffsets().get(i);
    		}
    		double imageIndex = Double.MAX_VALUE;
    		if(j < original.getImages().size())
    		{
    			//Then suggest the text index:
    			imageIndex = original.getImageOffsets().get(j);
    		}
    		
    		if(imageIndex < textIndex)
			{
    			str.addImage(original.getImages().get(j));
    			j++;
			}
    		else
    		{
    			str.addString(original.getText().get(i), original.getFonts().get(i), original.getColors().get(i));
    			i++;
    		}
    	}
    	
    	return str;
    }
}

