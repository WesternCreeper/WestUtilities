package graphicsUtilities;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class WGImageHelper 
{
	/**
	 * Takes an image shades it with the brightness given. (This only makes the image darker, thus "shade")
	 * @param image
	 * @param brightness Needs to be within the range of 0 to 1, where 1 is shade to same color
	 * @return returns the shaded image
	 */
	public static Image shadeImage(Image image, double brightness)
	{
		PixelReader reader = image.getPixelReader();
		WritableImage newImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
		PixelWriter writer = newImage.getPixelWriter();
		
		for(int a = 0 ; a < image.getWidth() ; a++)
		{
			for(int b = 0 ; b < image.getHeight(); b++)
			{
				Color pixel = reader.getColor(a, b);
				pixel = WGColorHelper.shade(pixel, brightness);
				writer.setColor(a, b, pixel);
			}
		}
		return newImage;
	}
	/**
	 * Takes an image and gives it a tint in the given color at the amount of the given amount
	 * @param image The image to be tinted
	 * @param tint The color that creates the tint for the image
	 * @param amount Needs to be within the range of 0 to 1, where 1 is tint fully and 0 is no tint at all.
	 * @return returns the shaded image
	 */
	public static Image tintImage(Image image, Color tint, double amount)
	{
		PixelReader reader = image.getPixelReader();
		WritableImage newImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
		PixelWriter writer = newImage.getPixelWriter();
		
		for(int a = 0 ; a < image.getWidth() ; a++)
		{
			for(int b = 0 ; b < image.getHeight(); b++)
			{
				Color pixel = reader.getColor(a, b);
				pixel = WGColorHelper.combineTwoColors(pixel, 1 - amount, tint, amount);
				writer.setColor(a, b, pixel);
			}
		}
		return newImage;
	}
}
