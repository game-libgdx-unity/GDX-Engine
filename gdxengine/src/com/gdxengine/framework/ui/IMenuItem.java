package com.gdxengine.framework.ui;

import com.gdxengine.framework.interfaces.ICollectionItem;

/**
 * Interface for all menu item.
 * 
 * The item constructs the menu of game that usually exists in First Scene of the game.
 * 
 * @author Vinh
 *
 */
public interface IMenuItem extends ICollectionItem {
	
	/**
	 * Called when user click on menu
	 */
	public void onClick();
	/**
	 * Check menu item is selected or not.
	 * 
	 * @return
	 */
	public boolean isSelect();
	/**
	 * Set menu item is selected or not.
	 * 
	 * @param value
	 */
	public void setSelect(boolean value);
	
	public Object getContent();
	
	public float getX();
	
	public float getY();
}
