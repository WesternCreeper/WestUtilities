package graphicsUtilities;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import utilities.FileProcessor;

public class FormattedString 
{
	private String text;
	private Font font;

	public FormattedString(FormattedString text)
	{
		this.text = new String(text.getText());
        this.font = text.getFont(); //No need to copy because this is immutable
	}
	public FormattedString(String text, Font font)
	{
		this.text = text;
		this.font = font;
		formatString();
	}
	
	//Methods:
	public void fitToBox(double width, double height, Canvas parent, int maxSize)
	{
		font = WGFontHelper.getFittedFontForBox(font, parent, width, height, text, maxSize);
	}
	public void formatString()
	{
		//Go through the entire string formating it:
		for(int i = 0 ; i < text.length(); i++) 
		{
			if(text.charAt(i) == '/')
			{
				//Check that there exists a proper code point:
				if(i+1 < text.length() && (text.charAt(i+1) == 'U' || text.charAt(i+1) == 'u'))
				{
					//Now search for the end of the digits:
					String digits = "";
					int endIndex = i+1;
					for(int j = i+2 ; j < text.length(); j++)
					{
						if(Character.isDigit(text.charAt(j)))
						{
							digits += text.charAt(j);
							endIndex = j;
						}
						else
						{
							break;
						}
					}
					int codePoint = FileProcessor.toInt(digits, 0);
					if(endIndex+1 < text.length())
					{
						text = text.substring(0, i) + new String(Character.toChars(codePoint)) + text.substring(endIndex+1);
					}
					else
					{
						text = text.substring(0, i) + new String(Character.toChars(codePoint));
					}
				}
			}
		}
	}
	@Override
	public String toString()
	{
		return text;
	}
	
	//Wrapper for string methods:
	public FormattedString substring(int beginIndex)
	{
		return new FormattedString(text.substring(beginIndex), font);
	}
	public FormattedString substring(int beginIndex, int endIndex)
	{
		return new FormattedString(text.substring(beginIndex, endIndex), font);
	}
	public void concat(String str)
	{
		text += str;
		formatString();
	}
	public void concat(FormattedString str)
	{
		text += str.getText();
	}
	
	//Getters:
	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	public void setText(String text) {
		this.text = text;
		formatString();
	}

	public void setFont(Font font) {
		this.font = font;
	}
}
