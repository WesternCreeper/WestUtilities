package utilities;

import com.sun.javafx.tk.Toolkit;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Complete copy from ChatGPT! This is not my work, I take no credit for this!
 */
public class FXFontMetrics 
{
    private final Font font;
    private final com.sun.javafx.tk.FontMetrics fm;

    public FXFontMetrics(Font font) 
    {
        this.font = font;
        this.fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
    }

    public double getAscent() 
    {
    	return fm.getAscent(); 
	}
    public double getDescent() 
    {
    	return fm.getDescent(); 
    }
    public double getHeight() 
    { 
    	return fm.getLineHeight(); 
    }
    public double getLeading()
    {
    	return fm.getLeading();
    }
    public double stringWidth(String s) 
    {
        Text text = new Text(s);
        text.setFont(font);
        return text.getLayoutBounds().getWidth();
    }
}

