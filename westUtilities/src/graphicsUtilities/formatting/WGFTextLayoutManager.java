package graphicsUtilities.formatting;

import java.util.ArrayList;

import graphicsUtilities.ColoredString;
import javafx.geometry.Point2D;

public class WGFTextLayoutManager 
{
	private ArrayList<PositionedString> strings = new ArrayList<PositionedString>();
	
	public WGFTextLayoutManager() {}
	
	public void addString(ColoredString str, Point2D pos)
	{
		strings.add(new PositionedString(pos.getX(), pos.getY(), str));
	}
	
	public void addString(PositionedString str)
	{
		placeString(str);
		strings.add(str);
	}
	
	public PositionedString removeString(int i)
	{
		if(i >= 0 && i < strings.size()) 
		{
			return strings.remove(i);
		}
		return null;
	}
	
	public PositionedString setString(int i, PositionedString str)
	{
		if(i >= 0 && i < strings.size()) 
		{
			placeString(str);
			return strings.set(i, str);
		}
		return null;
	}
	
	public PositionedString get(int i)
	{
		if(i >= 0 && i < strings.size()) 
		{
			return strings.get(i);
		}
		return null;
	}
	
	public int size()
	{
		return strings.size();
	}
	
	private void placeString(PositionedString str)
	{
		//Go through all the other strings and determine if there is any overlap:
		boolean overlap = false;
		double minY = 0;
		for(int i = 0 ; i < strings.size() ; i++)
		{
			double currentY = strings.get(i).getY() + strings.get(i).getHeight();
			if(minY > currentY)
			{
				minY = currentY;
			}
			if(strings.get(i).getBounds().intersects(str.getBounds()))
			{
				overlap = true;
			}
		}
		if(overlap)
		{
			str.setY(minY);
		}
	}
}
