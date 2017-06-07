package com.gdxengine.framework.object3d;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Matrix4;
import com.gdxengine.framework.camera.CameraManager;
import com.gdxengine.framework.effect.ShaderManager;
import com.gdxengine.framework.interfaces.ICollectionItem;
import com.gdxengine.framework.interfaces.IDrawable3D;
import com.gdxengine.framework.interfaces.IGame3DService;
import com.gdxengine.framework.interfaces.IObjectCollection;
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
public abstract class Object3DCollection<T extends ICollectionItem> extends BaseObject3D implements IDrawable3D, Iterable<T>, IObjectCollection<T>  {
	
	protected ArrayList<T> collection = new ArrayList<T>();
	
	protected final ShaderManager shader;
	protected final CameraManager camera;
	protected final LightManager light;
	
	protected final Matrix4 transform = new Matrix4();
	protected StillModel model;
	protected Texture texture;
	/**
	 * @return the model
	 */
	public StillModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(StillModel model) {
		this.model = model;
	}

	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	/**
	 * @param services service of game
	 * @param continueInit if true, it calls initialize() method every time when scene is active
	 * if false, the initialize() method will be called only one time when the first time the scene is active. 
	 */
	public Object3DCollection(IGame3DService services) {
		super(services);
		
		shader = services.getShaderManager();
		camera = services.getCameraManager();
		light = services.getLightManager();
	}
	
	@Override 
	public abstract void renderGL2(float gameTime);

	@Override 
	public abstract void renderGL1(GL10 gl,float gameTime);
	
	protected SpriteBatch getSpriteBatch() {
		// TODO Auto-generated method stub
		return services.getSpriteBatch();
	}
	
	@Override
	public void pause()
	{
		for(ICollectionItem com : collection)
		{
			com.pause();
		}
	}
	@Override
	public void resume()
	{
		for(ICollectionItem com : collection)
		{
			com.resume();
		}
	}
	@Override
	public void initialize()
	{
		for(ICollectionItem com : collection)
		{
			com.initialize();
		}
	}

	@Override
	public void addItem(T basicObject)
	{
		this.collection.add(basicObject);
	}
	
	@Override
	public void update(float gameTime) {

		for (int i = 0; i < collection.size(); i++) {
			ICollectionItem object = collection.get(i);
			if(object.isEnabled())
				object.update(gameTime);
		}
	}

	@Override
	public ArrayList<T> getObjectCollection() {
		return  collection;
	}

	@Override
	public Iterator<T> iterator() {
		return collection.iterator();
	}
	@Override
	public void clear() {
		collection.clear();
	}
	@Override
	public ArrayList<T> get() {
		// TODO Auto-generated method stub
		return collection;
	}

	@Override
	public T get(int index) {
		return collection.get(index);
	}

	@Override
	public ArrayList<T> findAllByType(Class<T> type) {
		ArrayList<T> array = new ArrayList<T>();
		
		for(int i = 0; i < collection.size(); i++)
			if(collection.get(i).getClass() == type)
			{
				array.add(collection.get(i));
			}
		
		return array;
	}

	@Override
	public T findTheFirstByType(Class<T> type) {

		for(int i = 0; i < collection.size(); i++)
			if(collection.get(i).getClass() == type)
			{
				return collection.get(i);
			}
		
		return null;
	}
}
