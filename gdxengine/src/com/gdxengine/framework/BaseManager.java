package com.gdxengine.framework;

import java.util.HashMap;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IService;

/**
 * The base class for all managers in game like camera manager, light manager...
 * 
 * @author Vinh
 *
 * @param <K> Type of camera's key
 * @param <V> Type of Object that manager manage.
 */
public class BaseManager<K, V> implements IService {
	
	/**
	 * Collection that store all objects. You can access a object by provide its key
	 */
	public HashMap<K, V> collection = new HashMap<K, V>();
	/**
	 * Reference to current active object
	 */
	protected V activeObject;
	/**
	 * @return the collection
	 */
	public HashMap<K, V> getCollection() {
		return collection;
	}
	/**
	 * @param collection the collection to set
	 */
	public void setCollection(HashMap<K, V> collection) {
		this.collection = collection;
	}
	/**
	 * @return the activeObject
	 */
	public V getActiveObject() {
		return activeObject;
	}
	/**
	 * @param activeObject the activeObject to set
	 */
	public void setKeyActive(K key) {
		V obj = collection.get(key);
		
		if(obj != null)
			this.activeObject = obj;
	}
	/**Set active object from a object reference by its key
	 * @param obj
	 */
	public void setObjectActive(V obj) {
		if(collection.containsValue(obj)){
			this.activeObject = obj;
		}
		else
			throw new GdxRuntimeException("Object hasn't existed in collection! please choice another object reference to set active object");
	}
	/**
	 * Add new object reference to collection
	 * @param key
	 * @param value
	 */
	public void add(K key, V value)
	{
		if(collection.containsKey(key))
			throw new GdxRuntimeException("Key "+key.toString() + " has existed in collection! please choice another key to add object");
		else
			collection.put(key, value);
	}
	
	/**
	 * Remove a object value by its key
	 * @param key
	 * @return
	 */
	public V remove(K key)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Key "+key.toString() + " hasn't existed in collection! please choice another key to remove object");
		else
			return collection.remove(key);
	}
	
	/**Get a object value by its key
	 * @param key
	 * @return
	 */
	public V get(K key)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Key "+key.toString() + " hasn't existed in collection! please choice another key to get object");
		else
			return collection.get(key);
	}
	/**Get a object value by its key and specify the class of return type
	 * @param <T>
	 * @param key
	 * @return object that have strongly typed
	 */
	@SuppressWarnings("unchecked")
	public <T extends V> T get(K key, Class<T> returnType)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Key "+key.toString() + " hasn't existed in collection! please choice another key to get object");
		else
			return (T) collection.get(key);
	}
	
	/**Modify a object value by its key
	 * @param key
	 * @param value
	 */
	public void modify(K key, V value)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Key "+key.toString() + " hasn't existed in collection! please choice another key to modify object");
		else
		{
			collection.remove(key);
			collection.put(key, value);
		}
	}
	
	/**
	 * clear all object values in collection
	 */
	public void clear()
	{
		collection.clear();
	}
	
	/**
	 * The number of members in Manager
	 */
	public int count()
	{
		return collection.size();
	}
}
