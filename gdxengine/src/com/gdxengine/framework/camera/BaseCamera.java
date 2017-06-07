package com.gdxengine.framework.camera;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;

public abstract class BaseCamera extends PerspectiveCamera{
	
	protected boolean needUpdateProjection = true;
	protected boolean needUpdateView = true;
	protected boolean needUpdateFrustum;
	
	public BaseCamera(float fieldOfView, float viewportWidth, float viewportHeight)
    {
		this.fieldOfView = fieldOfView;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
        
        view.idt();
    }
	
	public Matrix4 getCombined()
	{
		if(needUpdateProjection)
			updateProjection();
		if(needUpdateView)
			updateViewMatrix();

		combined.set(projection);
		combined.mul(view);
		return combined;
	}
	
	public Frustum getFrustum()
	{
		if(needUpdateFrustum)
		{
			updateFrustum();
		}
		
		return frustum;
	}
	
	public void updateProjection()
	{
		float aspect = viewportWidth / viewportHeight;
 		projection.setToProjection(Math.abs(near), Math.abs(far), fieldOfView, aspect);
 		needUpdateProjection = false;
	}
	
	public abstract void updateViewMatrix();
	
	public void updateFrustum()
	{
		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
		needUpdateFrustum = false;
		
	}
}
