package utilities;


import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Complete copy from ChatGPT! This is not my work, I take no credit for this!
 */
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
}

