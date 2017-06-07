package com.gdxengine.framework.light3d;

import com.badlogic.gdx.math.Vector3;
import com.gdxengine.framework.BaseManager;

/**
 * the class manager all collection in your game
 * a Light class was extended from baseLight Should be added to this class's light collection
 * by using addBaseLight() method
 * When render object3D, just reference to this class, and get the light you need  by using
 * getBaseLight() for the specific light or getAllLights() for get all collection of collection
 * 
 * @author Vinh
 *
 */
public class LightManager extends BaseManager<String, BaseLight> {
	
	/**
	 * Construct LightManager with value of ambient color
	 * . ambient color will make scene is not too dark.
	 * @param ambientColor
	 */
	public LightManager(Vector3 ambientColor)
	{
		this.ambientColor.set(ambientColor);
	}
	
	/**
	 * store value of ambient color
	 */
	public final Vector3 ambientColor = new Vector3();
	
	/**
	 * Get a specific light by its key
	 * @param index the key (index)
	 * @return
	 */
	public BaseLight getBaseLight(int index)
	{
		if(index < 0 || index > collection.size())
			return null;
		
		return collection.get(index);
	}
	
	/**
	 * Add a light to collection
	 * @param key 
	 * @param light
	 */
	public void addBaseLight(String key, BaseLight light)
	{
		collection.put(key, light);
	}
	
	/**
	 * Remove all collection of collection
	 */
	public void clear() {
		// TODO Auto-generated method stub
		collection.clear();
	}
}
