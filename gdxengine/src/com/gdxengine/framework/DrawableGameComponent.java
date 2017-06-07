package com.gdxengine.framework;

import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;


/**
 * The standard class that extend from GameComponent and have IDrawable interface therefore the object that is instance of
 * this class could be auto-render if the object is visible and you have added the object into a scene 
 * using addDrawbleObject() or addObject3D() Method.
 * 
 * Not like sprite, the class have no texture or textureRegion, you should declare it explicitly in derived class 
 * 
 * @author Vinh
 *
 */
public abstract class DrawableGameComponent extends GameComponent implements IDrawable {

	boolean visible = true;

	public DrawableGameComponent(IGameService services) {
		super(services);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void setDead(boolean value) {
		
		if(value)
		{
			setEnabled(false);
			setVisible(false);
		}
		isDead = value;
	}
}
