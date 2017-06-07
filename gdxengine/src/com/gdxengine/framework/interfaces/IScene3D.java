package com.gdxengine.framework.interfaces;


/**
 * 
 * Every scene 3D should implements the interface.
 * @author Vinh
 *
 */
public interface IScene3D extends IScene {

	/**
	 * Add object3D that implements IDrawable3D to scene to make the object
	 * could be updated and rendered automatically.
	 * 
	 * @param obj object to add
	 */
	public abstract void addObject3D(IDrawable3D obj);
	
}