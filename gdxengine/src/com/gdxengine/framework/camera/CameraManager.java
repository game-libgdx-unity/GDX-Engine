package com.gdxengine.framework.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.BaseManager;

/**
 * The class will manager all camera on your game. You can add new camera,
 * set Active Camera. The Combined Matrix of Active Camera will use to render
 * DefaultObject3D and other 3D stuffs.
 * 
 * @author Vinh
 *
 */
public class CameraManager extends BaseManager<String, Camera> {
	
	/**
	 * Get a camera by provide its key (Key is string)
	 * @param key Key used to identify the camera you need.
	 * @return the Camera
	 */
	@Override
	public Camera get(String key)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Camera is not existing in CameraManager!");
		
		return collection.get(key);
	}
	
	/**
	 * Add camera to collection collection of CameraManager
	 * @param camera Camera to Add
	 * @param key Key (ID) of camera
	 */
	public void addCamera(String key, Camera camera)
	{
		if(collection.containsKey(key))
			throw new GdxRuntimeException("Camera key is existing in CameraManager!");
		
		collection.put(key, camera);
	}
	
	/**
	 * Set a camera is the current active camera
	 * @param key Key of camera that i want to make it is current active camera
	 */
	public void setActiveCamera(String key)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Camera key is not existing in CameraManager!");
		
		activeObject = collection.get(key);
	}
	
	/**
	 * Set the camera is the current active camera
	 * @param camera reference to the camera you want.
	 */
	public void setActiveCamera(Camera camera)
	{
		if(!collection.containsValue(camera))
			throw new GdxRuntimeException("Camera is not existing in CameraManager!");
		
		activeObject = camera;
	}
	
	/**
	 * Get the combined Matrix of active camera
	 * @return
	 */
	public Matrix4 getCombined()
	{
		return activeObject.combined;
	}
	
	/**
	 * Call the update method of current active camera
	 */
	public void update()
	{
		activeObject.update();
	}
	
	/**
	 * Apply camera matrixs to GL10. Usage for Open GL ES 1.x Only
	 * @param gl
	 */
	public void apply(GL10 gl)
	{
		activeObject.apply(gl);
	}


	/**
	 * get the current active camera
	 * @return
	 */
	public Camera getActiveCamera() {
		// TODO Auto-generated method stub
		return activeObject;
	}
}
