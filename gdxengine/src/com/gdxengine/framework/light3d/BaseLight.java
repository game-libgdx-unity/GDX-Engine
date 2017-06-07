package com.gdxengine.framework.light3d;

import com.badlogic.gdx.math.Vector3;

/**
 * Base class for all lights in game
 * support the light color 
 * 
 * @author Vinh
 *
 */
public class BaseLight {
	/**
	 * Color of light source
	 */
	public final Vector3 lightColor = new Vector3(); 
	
	public BaseLight(Vector3 color)
	{
		this.lightColor.set(color);
	}
	
	/**
	 * Get the Color of light source
	 * @return
	 */
	public Vector3 getLightColor()
	{
		return lightColor;
	}
}
