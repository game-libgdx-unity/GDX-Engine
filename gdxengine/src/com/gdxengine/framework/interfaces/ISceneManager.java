package com.gdxengine.framework.interfaces;

/**
 * Change the current active scene base their index or class type.
 * 
 * @author Vinh
 *
 */
public interface ISceneManager {
	/**
	 * Change the current active scene, the new scene will be activated
	 * @param activeScene Index of new scene is activated. it will make the current active scene is inactive.
	 */
	public void changeScene(int activeScene);

	/**
	 * Change the current active scene, the new scene will be activated
	 * @param <T> Class of scene
	 * @param sceneType Class Type of new scene is activated. it will make the current active scene is inactive.
	 */
	public <T> void changeScene(Class<T> sceneType);
/**
 * Get the  scene by index of scene
 * @param sceneIndex
 * @return
 */
	public IScene getScene(int sceneIndex);
	/**
	 * Get the  scene by class of scene
	 * @param sceneType
	 * @return
	 */
	public <T> T getScene(Class<T> sceneType);
/**
 * Get the current scene 
 * @return
 */
	public IScene getScene();
}
