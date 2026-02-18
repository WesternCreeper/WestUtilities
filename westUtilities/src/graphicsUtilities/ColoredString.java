package graphicsUtilities;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.FXFontMetrics;
import utilities.FileProcessor;

public class ColoredString
{
	private ArrayList<Image> images;
	private ArrayList<Double> imageOffsets;
	private ArrayList<Double> textOffsets;
	private ArrayList<Paint> colors;
	private ArrayList<String> text;
	private ArrayList<Font> fonts;
	
	public ColoredString()
	{
		textOffsets = new ArrayList<Double>();
		colors = new ArrayList<Paint>();
		this.text = new ArrayList<String>();
		this.fonts = new ArrayList<Font>();
		this.images = new ArrayList<Image>();
		this.imageOffsets = new ArrayList<Double>();
	}
	
	public ColoredString(ColoredString str)
	{
		textOffsets = new ArrayList<Double>();
		colors = new ArrayList<Paint>();
		this.text = new ArrayList<String>();
		this.fonts = new ArrayList<Font>();
		this.images = new ArrayList<Image>();
		this.imageOffsets = new ArrayList<Double>();
		
		//Now create exact copies of each element in each arrayList:
		for(int i = 0 ; i < str.getText().size(); i++)
		{
			textOffsets.add(Double.valueOf(str.getTextOffsets().get(i)));
			colors.add(str.getColors().get(i)); //No need to copy because these objects are immutable
			this.text.add(new String(str.getText().get(i)));
			this.fonts.add(str.getFonts().get(i)); //No need to copy because these objects are immutable
		}
		for(int i = 0 ; i < str.getImages().size() ; i++)
		{
			images.add(str.getImages().get(i));
			imageOffsets.add(str.getImageOffsets().get(i));
		}
	}
	public ColoredString(String text, Font font, Paint color)
	{
		textOffsets = new ArrayList<Double>();
		colors = new ArrayList<Paint>();
		this.text = new ArrayList<String>();
		this.fonts = new ArrayList<Font>();
		this.images = new ArrayList<Image>();
		this.imageOffsets = new ArrayList<Double>();
		textOffsets.add(0.0);
		colors.add(color);
		this.text.add(formatString(text));
		this.fonts.add(font);
	}
	
	
	//Methods:
	public void concat(String str, int index)
	{
		//Add the string to the correct formatted string.
		if(index >= 0 && index < text.size())
		{
			text.set(index, text.get(index).concat(formatString(str)));
		}
	}
	public void addString(String str, Font font, Paint color)
	{
		//Generate the next textOffset based on the size of the string
		double offset = FXFontMetrics.stringWidth(this);
		textOffsets.add(offset);
		
		//Now add the color:
		colors.add(color);
		
		//Next add the font:
		fonts.add(font);
		
		//Finally add the string:
		text.add(formatString(str));
	}
	public void addString(ColoredString str)
	{
		//Take all of the information from the one and add it to ours:
		double offsetAmount = FXFontMetrics.stringWidth(this); //So text adds to the end of our string...
		for(int i = 0 ; i < str.getText().size() ; i++)
		{
			text.add(new String(str.getText().get(i)));
			colors.add(str.getColors().get(i));
			fonts.add(str.getFonts().get(i));
			textOffsets.add(str.getTextOffsets().get(i) + offsetAmount);
		}
		for(int i = 0 ; i < str.getImages().size() ; i++)
		{
			images.add(str.getImages().get(i));
			imageOffsets.add(str.getImageOffsets().get(i) + offsetAmount);
		}
	}
	public void addImage(Image image)
	{
		double offset = FXFontMetrics.stringWidth(this);
		imageOffsets.add(offset);
		images.add(image);
	}
	private String formatString(String str)
	{
		//Go through the entire string formating it:
		for(int i = 0 ; i < str.length(); i++) 
		{
			if(str.charAt(i) == '/')
			{
				//Check that there exists a proper code point:
				if(i+1 < str.length() && (str.charAt(i+1) == 'U' || str.charAt(i+1) == 'u'))
				{
					//Now search for the end of the digits:
					String digits = "";
					int endIndex = i+1;
					for(int j = i+2 ; j < str.length(); j++)
					{
						if(Character.isDigit(str.charAt(j)))
						{
							digits += str.charAt(j);
							endIndex = j;
						}
						else
						{
							break;
						}
					}
					int codePoint = FileProcessor.toInt(digits, 0);
					if(endIndex+1 < str.length())
					{
						str = str.substring(0, i) + new String(Character.toChars(codePoint)) + str.substring(endIndex+1);
					}
					else
					{
						str = str.substring(0, i) + new String(Character.toChars(codePoint));
					}
				}
			}
		}
		return str;
	}

	public void setFont(Font font) 
	{
		for(int i = 0 ; i < fonts.size(); i++) 
		{
			fonts.set(i, font);
		}
	}
	
	
	//Getters:
	public ArrayList<Double> getTextOffsets() {
		return textOffsets;
	}
	public ArrayList<Paint> getColors() {
		return colors;
	}
	public ArrayList<String> getText() {
		return text;
	}
	public ArrayList<Font> getFonts() {
		return fonts;
	}
	public ArrayList<Image> getImages() {
		return images;
	}
	public ArrayList<Double> getImageOffsets() {
		return imageOffsets;
	}
}
