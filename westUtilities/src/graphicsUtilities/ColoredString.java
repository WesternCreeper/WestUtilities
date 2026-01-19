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
	
	public ColoredString(FormattedString text, Paint color)
	{
		textOffsets.add(0.0);
		colors.add(color);
		this.text.add(text);
	}
	public ColoredString(String text, Font font, Paint color)
	{
		textOffsets.add(0.0);
		colors.add(color);
		this.text.add(new FormattedString(text, font));
	}
	
	
	//Methods:
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
