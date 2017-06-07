package com.gdxengine.framework.light3d;

import com.gdxengine.framework.interfaces.IService;

/**
 * the Interface set value for Light parameters in shader 
 * the object of the interface can turn into a service 
 * because i have IService interface
 * @author Vinh
 *
 */
public interface ISetLight extends IService {

	/**
	 * Set value for Light parameters in shader 
	 */
	void setLightParameters();

}
