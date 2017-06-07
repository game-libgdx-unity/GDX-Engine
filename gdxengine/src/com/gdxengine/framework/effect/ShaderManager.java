package com.gdxengine.framework.effect;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.BaseManager;

/**
 * The shader manager help you manage all shader in your game. a shader should be loaded from your Game Asset, then the
 * Shader could be added to this class collection by using the addShader() methods.
 * 
 * @author Vinh
 *
 */
public class ShaderManager extends BaseManager<String, ShaderProgram> {
	
	/**
	 * Get the specified Shader by providing shader 's key
	 * @param key
	 * @return
	 */
	public ShaderProgram getShader(String key)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Shader is not existing in ShaderManager!");
		
		return collection.get(key);
	}
	
	/**
	 * Add new shader, also provide its key
	 * @param shader
	 * @param key
	 */
	public void addShader(ShaderProgram shader, String key)
	{
		if(collection.containsKey(key))
			throw new GdxRuntimeException("Shader key is existing in ShaderManager!");
		
		collection.put(key, shader);
	}
	
	/**
	 * set current active shader by the shader's key
	 * @param key
	 */
	public void setActiveShader(String key)
	{
		if(!collection.containsKey(key))
			throw new GdxRuntimeException("Shader key is not existing in ShaderManager!");
		
		activeObject = collection.get(key);
	}
	
	/**
	 * set current active shader by reference to the shader
	 * @param shader
	 */
	public void setActiveShader(ShaderProgram shader)
	{
		if(!collection.containsValue(shader))
			throw new GdxRuntimeException("Shader is not existing in ShaderManager!");
		
		activeObject = shader;
	}
	
	/**
	 * Call the begin() method of current active shader
	 */
	public void begin()
	{
		activeObject.begin();
	}
	/**
	 * Call the end() method of current active shader
	 */
	public void end()
	{
		activeObject.end();
	}

	/**
	 * Set parameters for current active shader, read name of method to know what the parameter is  
	 * @param name
	 * @param value
	 */
	public void setUniformMatrix(String name, Matrix3 value)
	{
		activeObject.setUniformMatrix(name, value);
	}
	/**
	 * Set parameters for current active shader, read name of method to know what the parameter is  
	 * @param name
	 * @param value
	 */
	public void setUniformMatrix(String name, Matrix4 value)
	{
		activeObject.setUniformMatrix(name, value);
	}
	/**
	 * Set parameters for current active shader, read name of method to know what the parameter is  
	 * @param name
	 * @param value
	 */
	public void setUniformVector(String name, Vector3 value)
	{
		activeObject.setUniformf(name, value);
	}
	/**
	 * Set parameters for current active shader, read name of method to know what the parameter is  
	 * @param name
	 * @param value
	 */
	public void setUniformVector(String name, Vector2 value)
	{
		activeObject.setUniformf(name, value);
	}
	/**
	 * Set parameters for current active shader, read name of method to know what the parameter is  
	 * @param name
	 * @param value
	 */
	public void setUniformi(String string, int i) {
		activeObject.setUniformi(string, i);
	}
	/**
	 * get the current active shader
	 * @return
	 */
	public ShaderProgram getShader() {
		// TODO Auto-generated method stub
		return activeObject;
	}

	public void setUniformf(String name, float f, float g, float z, float w) {
		
		activeObject.setUniformf(name, f, g, z, w);
	}
	
}
