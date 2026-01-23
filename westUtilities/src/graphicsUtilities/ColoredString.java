package graphicsUtilities;

import java.util.ArrayList;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import utilities.FXFontMetrics;

public class ColoredString
{
	private ArrayList<Double> textOffsets;
	private ArrayList<Paint> colors;
	private ArrayList<FormattedString> text;
	
	public ColoredString(ColoredString str)
	{
		textOffsets = new ArrayList<Double>();
		colors = new ArrayList<Paint>();
		this.text = new ArrayList<FormattedString>();
		
		//Now create exact copies of each element in each arrayList:
		for(int i = 0 ; i < str.getText().size(); i++)
		{
			textOffsets.add(Double.valueOf(str.getTextOffsets().get(i)));
			colors.add(str.getColors().get(i)); //No need to copy because these objects are immutable
			this.text.add(new FormattedString(str.getText().get(i)));
		}
	}
	public ColoredString(FormattedString text, Paint color)
	{
		textOffsets = new ArrayList<Double>();
		colors = new ArrayList<Paint>();
		this.text = new ArrayList<FormattedString>();
		textOffsets.add(0.0);
		colors.add(color);
		this.text.add(text);
	}
	public ColoredString(String text, Font font, Paint color)
	{
		textOffsets = new ArrayList<Double>();
		colors = new ArrayList<Paint>();
		this.text = new ArrayList<FormattedString>();
		textOffsets.add(0.0);
		colors.add(color);
		this.text.add(new FormattedString(text, font));
	}
	
	
	//Methods:
	public void concat(String str, int index)
	{
		//Add the string to the correct formatted string.
		if(index >= 0 && index < text.size())
		{
			text.get(index).concat(str);
		}
	}
	public void addString(FormattedString str, Paint color)
	{
		FXFontMetrics lastTextFM = new FXFontMetrics(text.get(text.size()-1).getFont());
		//Generate the next textOffset based on the last offset and the size of the last string
		textOffsets.add(textOffsets.get(textOffsets.size()-1) + lastTextFM.stringWidth(text.get(text.size()-1).getText()));
		
		//Now add the color:
		colors.add(color);
		
		//Finally add the string:
		text.add(str);
	}
	public void addString(ColoredString str)
	{
		//Take all of the information from the one and add it to ours:
		for(int i = 0 ; i < str.getText().size() ; i++)
		{
			text.add(str.getText().get(i));
			colors.add(str.getColors().get(i));
		}
		
		//Now add the text offsets, but remembering to add it to the end:
		FXFontMetrics lastTextFM = new FXFontMetrics(text.get(text.size()-1).getFont());
		double offsetAmount = textOffsets.get(textOffsets.size()-1) + lastTextFM.stringWidth(text.get(text.size()-1).getText());
		for(int i = 0 ; i < str.getTextOffsets().size() ; i++)
		{
			textOffsets.add(str.getTextOffsets().get(i) + offsetAmount);
		}
	}
	
	
	//Getters:
	public ArrayList<Double> getTextOffsets() {
		return textOffsets;
	}
	public ArrayList<Paint> getColors() {
		return colors;
	}
	public ArrayList<FormattedString> getText() {
		return text;
	}
}
