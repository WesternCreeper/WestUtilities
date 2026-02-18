package graphicsUtilities.formatting;

import graphicsUtilities.WGNullParentException;
import graphicsUtilities.WGPane;

public abstract class WGFGroup 
{
	WGPane componentHolder;
	protected WGFGroup(WGPane componentHolder)
	{
		this.componentHolder = componentHolder;
	}
	
	
	//Methods:
	public abstract void setUp(Object... parameters) throws WGNullParentException;
	
	public abstract void update(Object... parameters);
	
	
	//Setters:
	public void setComponentHolder(WGPane componentHolder) {
		this.componentHolder = componentHolder;
	}
	
	
	//Getters:
	public WGPane getComponentHolder() {
		return componentHolder;
	}
}
