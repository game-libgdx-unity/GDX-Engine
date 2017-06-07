package com.gdxengine.framework.interfaces;

/**
 * The interface that provide many methods can be used for the most game object
 * such as update, check object is enable (enabled object should be updated each game loop)
 * and check object is visible (visible object should be rendered each game loop) but this interface is used for Item which
 * should be render by a ItemCollection, Therefore there is not render method for a item.
 * the dead objects should be removed in ItemCollection as soon as possible.
 * 
 * @author Vinh
 *
 */
public interface ICollectionItem {
	
	/**
	 * update object each game loop
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public abstract void update(float gameTime);

	/**
	 * check object is enabled
	 * @return
	 */
	public abstract boolean isEnabled();

	/**
	 * set object enable or disabled
	 * @param enable the value to set
	 */
	public abstract void setEnabled(boolean enable);
	
	/**
	 * check object is visible 
	 * @return 
	 */
	public boolean isVisible();
	
	/**
	 * set object visible or invisible
	 * @param visible value to set
	 */
	public void setVisible(boolean visible);

	/**
	 * check object is dead or alive
	 * @return
	 */
	public abstract boolean isDead();

	/**
	 * set object is dead or alive
	 * @param value
	 */
	public abstract void setDead(boolean value);

	/**
	 * Be called when the application is paused
	 */
	public abstract void pause();

	/**
	 * Be called when the application is resumed
	 */
	public abstract void resume();

	/**
	 * Be called when Initialize() method of GameScene is called
	 * Initialize() method of GameScene is called when you activate a gameScene using ChangeScene() method
	 * of Game Services object.
	 */
	public abstract void initialize();
}
