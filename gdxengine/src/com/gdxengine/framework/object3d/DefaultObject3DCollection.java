package com.gdxengine.framework.object3d;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxengine.framework.camera.CameraManager;
import com.gdxengine.framework.effect.ShaderManager;
import com.gdxengine.framework.interfaces.IComponent;
import com.gdxengine.framework.interfaces.IDrawable3D;
import com.gdxengine.framework.interfaces.IGame3DService;
import com.gdxengine.framework.light3d.LightManager;
import com.gdxengine.framework.object3d.BaseObject3D;


/**
 * 
 * Abstract class for all scene 3D of the game.
 * 3D Objects that implement Drawable3D interface should be added to scene's collection
 * by using addObject3D() method to make the object can be auto-update and auto-render
 * 
 * @author Vinh
 *
 */
public abstract class DefaultObject3DCollection<T extends IDrawable3D> extends BaseObject3D implements IDrawable3D, Iterable<T>  {
	
	protected ArrayList<T> objectCollection = new ArrayList<T>();
	
	protected final ShaderManager shader;
	protected final CameraManager camera;
	protected final LightManager light;
	
	/**
	 * @param services service of game
	 * @param continueInit if true, it calls initilize() method every time when scene is active
	 * if false, the initilize() method will be called only one time when the first time the scene is active. 
	 */
	public DefaultObject3DCollection(IGame3DService services) {
		super(services);
		
		shader = services.getShaderManager();
		camera = services.getCameraManager();
		light = services.getLightManager();
	}
	
	@Override 
	public void renderGL2(float gameTime)
	{
		for(IDrawable3D obj : objectCollection)
			if(obj.isVisible())
			{
				obj.renderGL2(gameTime);
			}
	}

	@Override 
	public void renderGL1(GL10 gl,float gameTime)
	{
		for(IDrawable3D obj : objectCollection)
			if(obj.isVisible())
			{
				obj.renderGL1(gl, gameTime);
			}
	}
	protected SpriteBatch getSpriteBatch() {
		// TODO Auto-generated method stub
		return services.getSpriteBatch();
	}
	
	@Override
	public void pause()
	{
		for(IComponent com : objectCollection)
		{
			com.pause();
		}
	}
	@Override
	public void resume()
	{
		for(IComponent com : objectCollection)
		{
			com.resume();
		}
	}
	@Override
	public void initialize()
	{
		for(IComponent com : objectCollection)
		{
			com.initialize();
		}
	}

	public void addObjectItem3D(T basicObject)
	{
		this.objectCollection.add(basicObject);
	}
	
	@Override
	public void update(float gameTime) {

		for (int i = 0; i < objectCollection.size(); i++) {
			IDrawable3D object = objectCollection.get(i);
//			if(object.isDead())
//			{
//				objectCollection.remove(i);
//				i--;
//				continue;
//			}
			if(object.isEnabled())
				object.update(gameTime);
		}
	}

	public ArrayList<T> getObjectCollection() {
		return  objectCollection;
	}

	@Override
	public Iterator<T> iterator() {
		return objectCollection.iterator();
	}
}
