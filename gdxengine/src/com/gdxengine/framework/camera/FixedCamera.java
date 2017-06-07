package com.gdxengine.framework.camera;

import com.badlogic.gdx.math.Vector3;


/**
 * The FixedCamera have fixed camera position and fixed camera target.
 * 
 * @author Vinh
 *
 */
public class FixedCamera extends BaseCamera {

	/** Constructs a new {@link FixedCamera} with the given field of view and viewport size. The apsect ratio is derrived from
	 * the viewport size.
	 * 
	 * @param fieldOfView the field of view in degrees
	 * @param viewportWidth the viewport width
	 * @param viewportHeight the viewport height */
	public FixedCamera (float fieldOfView, float viewportWidth, float viewportHeight, Vector3 position, Vector3 target) {
		super(fieldOfView, viewportWidth, viewportHeight);
		this.position.set(position);
		direction.set(target).sub(position).nor();
	}
	
	/**
	 * Update camera following camera position and target
	 * @param position new Position
	 * @param target new Target
	 */
	public void update(Vector3 position, Vector3 target)
	{
		this.position.set(position);
		direction.set(target).sub(position).nor();
	}

	@Override
	public void updateViewMatrix() {
		view.setToLookAt(direction, up);
		needUpdateView = false;
	}
}
