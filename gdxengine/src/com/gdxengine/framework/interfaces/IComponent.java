package com.gdxengine.framework.interfaces;

/**
 * The interface for Component that have all methods from ICollectionItem interface
 * The interface have dispose method to dispose it's resource and can get Game Services
 * @author Vinh
 *
 */
public interface IComponent extends ICollectionItem {

	
	/**
	 * Dispose the resource of object
	 */
	public abstract void dispose();

	/**
	 * Get the Game Service of Object
	 * @return Game Services
	 */
	public abstract IGameService getGameService();

}