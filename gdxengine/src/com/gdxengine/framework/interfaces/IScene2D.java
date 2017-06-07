package com.gdxengine.framework.interfaces;

/**
 * 
 * Every scene 2D should implements the interface.
 * @author Vinh
 *
 */
public interface IScene2D extends IScene {

	/**
	 * Add object that implements IDrawable to scene to make the object
	 * could be initialized, updated and rendered automatically. 
	 * 
	 * @param obj object to add
	 */
	public abstract void  addDrawbleObject(IDrawable obj);

}