package com.gdxengine.framework.interfaces;

/**
 * Extended interface of IComponent. ICompomnent doesn't have a render ability
 * But IDrawable have. So When you add a IDrawable object into game scene by Add...() Methods, scene will
 * make the object update and render automatically.
 * 
 * This interface used for 2D Game Object only, for 3D Objects, you should use the IDrawable3D interface
 * 
 * @author Vinh
 *
 */
public interface IDrawable extends IComponent {
	
	public boolean isVisible();
	public void setVisible(boolean visible);
	/**
	 * Render object in 2D Scene
	 * @param gameTime time span between the current frame and the last frame in seconds.
	 */
	public void render(float gameTime);
}
