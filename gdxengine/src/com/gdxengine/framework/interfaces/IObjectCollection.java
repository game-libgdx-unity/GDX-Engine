package com.gdxengine.framework.interfaces;

import java.util.ArrayList;


/**
 * Interface for all 3D Object Collection class
 * 
 * @author Vinh
 *
 * @param <T>
 */
public interface IObjectCollection<T extends ICollectionItem> {

	/**
	 * pause all component objects in collection
	 */
	public abstract void pause();

	/**
	 * resume all component objects in collection
	 */
	public abstract void resume();

	/**
	 *  initialize all component objects in collection
	 */
	public abstract void initialize();

	/**
	 * add object to collection
	 */
	public abstract void addItem(T basicObject);

	/**
	 * get all component objects in collection
	 */
	public abstract ArrayList<T> get();
	/**
	 * get a component object in collection by its index
	 */
	public abstract T get(int index);
	
	/**get all component objects in collection
	 * @return
	 */
	public abstract ArrayList<T> getObjectCollection();
	
	/**
	 * Find all objects in collection by specifying their Class
	 * @param type
	 * @return all objects in special type
	 */
	public abstract ArrayList<T> findAllByType(Class<T> type);
	
	/**
	 * Find the first object in collection by specifying its Class
	 * @param type
	 * @return the first object in special type
	 */
	public abstract T findTheFirstByType(Class<T> type);
	/**
	 * clear all objects in collection
	 */
	public void clear();
}