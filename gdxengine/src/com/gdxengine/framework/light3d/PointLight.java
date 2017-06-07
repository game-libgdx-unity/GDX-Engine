package com.gdxengine.framework.light3d;

import com.badlogic.gdx.math.Vector3;

/**
 * Simulate a point light has light position and radius
 * @author Vinh
 *
 */
public class PointLight extends BaseLight{

	/**
	 * position of light
	 */
	public final Vector3 lightPosition = new Vector3();
	/**
	 * radius of light
	 */
	public float radius;
	
	/**
	 * Construct a point light by specification of color and position, radius
	 * @param color
	 * @param lightPosition
	 * @param radius
	 */
	public PointLight(Vector3 color,Vector3 lightPosition, float radius) {
		super(color);
		
		this.lightPosition.set(lightPosition);
		this.radius  = radius;
		// TODO Auto-generated constructor stub
	}

}
