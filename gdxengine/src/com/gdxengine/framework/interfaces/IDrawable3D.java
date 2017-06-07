package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.graphics.GL10;


/**
 * Every object 3D in game should be implements the interface.
 * a IDrawable3D object can be added to scene3D by addObject3D() method
 * 
 * This interface used for 3D Game Object only, for 2D Objects, you should use the IDrawable interface
 * 
 * @author Vinh
 *
 */
public interface IDrawable3D extends IComponent {

	/**
	 * Render using OpenGL ES 1.x
	 * @param gl the GL10
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public abstract void renderGL1(GL10 gl, float gameTime);

	/**
	 * Render using OpenGL ES 2.0
	 * 
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public abstract void renderGL2(float gameTime);

	/**
	 * Check object is visible
	 */
	public abstract boolean isVisible();
	/**
	 * Set object is visible or not.
	 */
	public abstract void setVisible(boolean visible);

}