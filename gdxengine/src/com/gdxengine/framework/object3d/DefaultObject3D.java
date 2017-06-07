package com.gdxengine.framework.object3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.gdxengine.framework.camera.CameraManager;
import com.gdxengine.framework.effect.ShaderManager;
import com.gdxengine.framework.interfaces.IGame3DService;
import com.gdxengine.framework.light3d.DirectionLight;
import com.gdxengine.framework.light3d.ISetLight;
import com.gdxengine.framework.light3d.LightManager;

public class DefaultObject3D extends BaseObject3D implements ISetLight {
	
	//If The field is not use by overriding setLightParameters() method, it will null forever. 
	//other wish, you need the object to set value for light's parameters in shader.
	//View setLightParameters() method for more details.
	ISetLight setLightEffectObject = null;

	//normal fields used for individual object3D derived from DefaultObject3D
	public final StillModel model;
	public final Texture texture;
	public final Vector3 position = new Vector3();
	public final Vector3 rotate = new Vector3();
	public final Vector3 direction = new Vector3();
	public final Vector3 scale = new Vector3();
	public final Color color = new Color();
	
	//static fields used for all object3D derived from DefaultObject3D
	public static final Matrix4 transform = new Matrix4();;
	protected static final Matrix4 normal = new Matrix4();
	protected static final Matrix3 normal3 = new Matrix3();
	protected  static ShaderManager shader;
	protected  static CameraManager camera;
	protected  static LightManager light;
	
	protected boolean isVisible = true;
	@Override
	public void renderGL2(float gameTime) {
		
				texture.bind();
		
				shader.begin();
				
				setLightParameters();
				
				normal.idt();
				normal.rotate(1, 0, 0, rotate.x);
				normal.rotate(0, 1, 0, rotate.y);
				normal.rotate(0, 0, 1, rotate.z);
				normal3.set(normal.toNormalMatrix());
				shader.setUniformMatrix("u_normal", normal3);
				transform.set(camera.getCombined());
				transform.translate(position.x, position.y, position.z);
				transform.rotate(1, 0, 0, rotate.x);
				transform.rotate(0, 1, 0, rotate.y);
				transform.rotate(0, 0, 1, rotate.z);
				transform.scale(scale.x, scale.y, scale.z);
				shader.setUniformMatrix("u_projView", transform);
				model.render(shader.getShader());
				
				shader.end();
	}
	
	@Override
	public void renderGL1(GL10 gl, float gameTime) {
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glColor4f(color.r, color.g, color.b, color.a);
		
		texture.bind();
		
		gl.glPushMatrix();
		gl.glTranslatef(position.x, position.y, position.z);
		gl.glRotatef(rotate.x, 1, 0, 0);
		gl.glRotatef(rotate.y, 0, 1, 0);
		gl.glRotatef(rotate.z, 0, 0, 1);
		gl.glScalef(scale.x, scale.y, scale.z);
		model.render();
		gl.glPopMatrix();
	}
	
	public DefaultObject3D(IGame3DService services, StillModel model, Texture texture)
	{
		this(services,new Vector3(1,1,1), Vector3.Zero, Vector3.Zero, model, texture, null);
	}
	public DefaultObject3D(IGame3DService services, Vector3 scale, Vector3 position,Vector3 rotate, StillModel model, Texture texture, ISetLight setLightEffectObject) {
		super(services);

		this.model = model;
		this.texture = texture;
		this.position.set(position);
		this.rotate.set(rotate);
		this.scale.set(scale);
		this.color.set(Color.WHITE);
		this.setLightEffectObject = setLightEffectObject;
		
		shader = services.getShaderManager();
		camera = services.getCameraManager();
		light = services.getLightManager();
	}
	
	@Override
	public void update(float gameTime) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void setLightParameters() {
		if(setLightEffectObject == null)
		{
			if(light.count() == 1)
			{
				//render the default light
				if(light.collection.containsKey("default"))
				{
					DirectionLight directlight = light.get("default", DirectionLight.class);
					shader.setUniformVector("u_light_ambient_color", light.ambientColor);
					shader.setUniformVector("u_light_color", directlight.getLightColor());
					shader.setUniformVector("u_light", directlight.getLightDirection());
				}
			}
		}
		else
		{
			setLightEffectObject.setLightParameters();
		}
	}
}
