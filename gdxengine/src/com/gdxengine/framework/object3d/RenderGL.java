package com.gdxengine.framework.object3d;

import com.badlogic.gdx.graphics.GL10;


/**
 * The interface will make the object that is implemented select OpenGL ES version automatically
 * If OpenGL ES 2.0 is supported, renderGL2 method will be used to render object
 * If only OpenGL ES 1.x is supported, renderGL1 method will be used to render object
 * @author Vinh
 *
 */
public interface RenderGL {

	/**
	 * Render using OpenGL ES 1.x
	 * @param gl GL10
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public void renderGL1(GL10 gl, float gameTime);
	/**
	 * Render using OpenGL ES 2.0
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public void renderGL2(float gameTime);
	/**
	 * Get the version of OpenGL automatically
	 * @return the version
	 */
	public SelectVersionGL getVersionGL();
}
