package com.gdxengine.framework.interfaces;

import com.gdxengine.framework.camera.CameraManager;
import com.gdxengine.framework.effect.ShaderManager;
import com.gdxengine.framework.light3d.LightManager;

/**
 * This class include all methods from IGameService interface
 * this class have own methods that need to create 3D Game Only
 * such as Camera, Shader and Light Manager
 * 
 * @author Vinh
 *
 */
public interface IGame3DService extends IGameService {
	/**
	 * Get the Camera Manager, You can add new, set current active camera, get combined matrix from active camera...
	 * @return
	 */
	public CameraManager getCameraManager();
	/**
	 * Get the Shader Manager, You can add new, set current active shader, get active shader, set parameters for active shader
	 * , render objects using current active shader...
	 * @return
	 */
	public ShaderManager getShaderManager();
	/**
	 * Get the Light Manager, You can add new lights, the direction light or point light or both
	 * The lights in LightManager will provide the values for shader's light parameters like Light direction or light Color...
	 * @return
	 */
	public LightManager getLightManager();
}
