package com.gdxengine.framework.material3d;

import com.gdxengine.framework.interfaces.IService;

/**
 * the Interface set value for material parameters in shader 
 * the object of the interface can turn into a service 
 * because i have IService interface
 * @author Vinh
 *
 */
public interface ISetMaterial extends IService {
	/**
	 * Set value for material parameters in shader 
	 */
	public void setMaterialParameters();
}
