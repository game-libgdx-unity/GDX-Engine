package com.gdxengine.framework;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IObjectCollection;


/**
 * 
 * Abstract class for all sprite collection of the game.
 * 3D Objects that implement Drawable3D interface should be added to collection
 * by using add...() method to make the sprite can be auto-update and auto-render
 * 
 * @author Vinh
 *
 */
public class SpriteCollection<T extends IDrawable> extends ObjectCollection<T> implements IDrawable, Iterable<T>, IObjectCollection<T>  {
	/**
	 * @param services service of game
	 * @param continueInit if true, it calls initialize() method every time when scene is active
	 * if false, the initialize() method will be called only one time when the first time the scene is active. 
	 */
	public SpriteCollection(IGameService services) {
		super(services);
	}
	
	@Override 
	public void render(float gameTime){
		for(IDrawable drawableObject : objectCollection)
		{
			if(drawableObject.isVisible())
				drawableObject.render(gameTime);
		}
	}
}
