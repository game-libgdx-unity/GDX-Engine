package com.gdxengine.framework.light3d;

import com.badlogic.gdx.math.Vector3;

/**
 * The class simulate a Direction of light based BaseLight class 
 * @author Vinh
 *
 */
public class DirectionLight extends BaseLight {
	/**
	 * Construct a direction light by specification of color and direction
	 * @param color
	 * @param direction
	 */
	public DirectionLight(Vector3 color, Vector3 direction) {
		super(color);
		this.direction.set(direction);
	}

	/**
	 * Direction of light
	 */
	 public final Vector3 direction = new Vector3();
	
	/** Get Direction of light
	 * @return
	 */
	public Vector3 getLightDirection()
	{
		return direction;
	}
	
}
