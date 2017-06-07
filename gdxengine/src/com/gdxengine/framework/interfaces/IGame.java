/**
 * 
 */
package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.ApplicationListener;

/**
 * Interface for game class of your game
 * 
 * @author Vinh
 *
 */

public interface IGame extends ApplicationListener {

	/**
	 * Get the service of your game
	 * 
	 * @return the service
	 */
	public IGameService getGameService();
	
}
