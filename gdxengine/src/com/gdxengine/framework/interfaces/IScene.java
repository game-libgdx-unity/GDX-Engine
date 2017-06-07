package com.gdxengine.framework.interfaces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The standard interface using for all scene in game
 * include 2D Scene or 3D Scene
 * 
 * @author Vinh
 *
 */
public interface IScene {
	
	/**
	 * Update the scene, update all objects in object collection of the scene
	 * 
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public void update(float gameTime);
	/**
	 * Render the scene, render all objects in object collection of the scene
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public void render(float gameTime);
	
	/**
	 * Call when game is paused so the game will make scene is paused
	 */
	public void pause();
	/**
	 * Call when game is resumed so the game will make scene is resumed
	 */
	public void resume();
	/**
	 * Call when the scene is disposed
	 */
	public void dispose();
	/**
	 * @return the value of continueInit variable of scene
	 */
	public boolean isContinueNeedInit();
	/**
	 * set value of continueInit variable of scene
	 * @param value Value to set
	 */
	public void setContinueNeedInit(boolean value);
	/**
	 * initilize the scene, by calling the initilize() method of scene.
	 * When the scene is activated, the game automatically call the initScene() method of GameScene.
	 * if value of continueInit is true, it calls initilize() method every time when scene is active
	 * if value of continueInit is false, the initilize() method will be called only one time when the first time the scene is activated. 
	 */
	public void initScene();

	/**
	 * initilize the scene
	 */
	public void initialize();
	/**
	 * Get the spriteBatch from game services
	 * @return
	 */
	public SpriteBatch getSpriteBatch();
	/**
	 * Get the object collection of scene
	 * @param <T>
	 * @return
	 */
	public <T> ArrayList<T> getObjectCollection();
	
	/**
	 * Recycle a object if existing the same object class in collection and the object is dead
	 * otherwish the object will be add to collection 
	 * @param obj
	 */
	public abstract void addOrRecycleObject(Object obj);
	/**
	 * Get the Game Service of Game
	 * @return Game Services
	 */
	public abstract IGameService getGameService();
}
