package com.gdxengine.framework.object3d;

import com.gdxengine.framework.GameComponent;
import com.gdxengine.framework.interfaces.IDrawable3D;
import com.gdxengine.framework.interfaces.IGame3DService;

public abstract class BaseObject3D extends GameComponent implements IDrawable3D  {

	boolean isVisible = true;
	
	public BaseObject3D(IGame3DService services) {
		super(services);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IGame3DService getGameService() {
		// TODO Auto-generated method stub
		return (IGame3DService) services;
	}
	
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return isVisible;
	}
	@Override
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	@Override
	public void setDead(boolean value) {
		setVisible(!value);
		setEnabled(!value);
		isDead = value;
	}
	
}
