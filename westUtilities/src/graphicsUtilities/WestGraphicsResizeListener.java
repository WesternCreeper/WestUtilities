package graphicsUtilities;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;

public class WestGraphicsResizeListener
{
	private Canvas parent;
    private ChangeListener<Number> resizeListener;
    public WestGraphicsResizeListener(Canvas parent)
    {
    	this.parent = parent;
        this.resizeListener = (obs, oldVal, newVal) -> resizeComps();
        parent.widthProperty().addListener(resizeListener);
        parent.heightProperty().addListener(resizeListener);
        resizeComps();
    }
	public synchronized void resizeComps()
	{
    	Platform.runLater(() -> {
    		//Before bothering to resize, just make sure the resizing goes to a non-zero parent size. There is no use resizing 
    		if(parent.getWidth() == 0 || parent.getHeight() == 0)
    		{
    			return;
    		}
			//Resize the components:
			for(int i = 0 ; i < WestGraphics.getAllClickables().size() ; i++)
			{
				WGDrawingObject obj = WestGraphics.getAllClickables().get(i);
				if(obj.isShown() && obj.getParentOwningPane() == null)
				{
					obj.getResizer().resizeCompsWithoutDelay();
				}
			}
    	});
	}
}
