package com.gdxengine.framework;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxengine.framework.interfaces.ICollectionItem;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IObjectCollection;

/**
 * 
 * Abstract class for all scene 3D of the game. 3D Objects that implement
 * Drawable3D interface should be added to scene's collection by using
 * addObject3D() method to make the object can be auto-update and auto-render
 * 
 * @author Vinh
 * 
 */
public abstract class ObjectCollection<T extends ICollectionItem> extends
	DrawableGameComponent implements IDrawable, Iterable<T>,
	IObjectCollection<T> {

    protected ArrayList<T> objectCollection = new ArrayList<T>();

    /**
     * @param services
     *            service of game
     * @param continueInit
     *            if true, it calls initialize() method every time when scene is
     *            active if false, the initialize() method will be called only
     *            one time when the first time the scene is active.
     */
    public ObjectCollection(IGameService services) {
	super(services);
    }

    @Override
    public abstract void render(float gameTime);

    protected SpriteBatch getSpriteBatch() {
	// TODO Auto-generated method stub
	return services.getSpriteBatch();
    }

    @Override
    public void pause() {
	for (ICollectionItem com : objectCollection) {
	    com.pause();
	}
    }

    @Override
    public void resume() {
	for (ICollectionItem com : objectCollection) {
	    com.resume();
	}
    }

    @Override
    public void initialize() {
	for (ICollectionItem com : objectCollection) {
	    com.initialize();
	}
    }

    @Override
    public void addItem(T basicObject) {
	this.objectCollection.add(basicObject);
    }

    public void addItemWithInitialize(T basicObject) {
	basicObject.initialize();
	this.objectCollection.add(basicObject);
    }

    @Override
    public void update(float gameTime) {

	for (int i = 0; i < objectCollection.size(); i++) {
	    ICollectionItem object = objectCollection.get(i);
	    if (object.isEnabled())
		object.update(gameTime);
	    else if (object.isDead()) {
		objectCollection.remove(i);
		i--;
	    }
	}
    }

    @Override
    public ArrayList<T> getObjectCollection() {
	return objectCollection;
    }

    @Override
    public Iterator<T> iterator() {
	return objectCollection.iterator();
    }

    @Override
    public void clear() {
	objectCollection.clear();
    }

    @Override
    public ArrayList<T> get() {
	// TODO Auto-generated method stub
	return objectCollection;
    }

    @Override
    public T get(int index) {
	return objectCollection.get(index);
    }

    @Override
    public ArrayList<T> findAllByType(Class<T> type) {
	ArrayList<T> array = new ArrayList<T>();

	for (int i = 0; i < objectCollection.size(); i++)
	    if (objectCollection.get(i).getClass() == type) {
		array.add(objectCollection.get(i));
	    }

	return array;
    }

    @Override
    public T findTheFirstByType(Class<T> type) {

	for (int i = 0; i < objectCollection.size(); i++)
	    if (objectCollection.get(i).getClass() == type) {
		return objectCollection.get(i);
	    }

	return null;
    }

    public boolean remove(T t) {
	t.setDead(true);
	t.setEnabled(false);
	t.setVisible(false);
	return objectCollection.remove(t);
    }
}
