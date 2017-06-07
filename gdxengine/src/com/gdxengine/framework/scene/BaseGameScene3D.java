package com.gdxengine.framework.scene;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.Game;
import com.gdxengine.framework.Game3D;
import com.gdxengine.framework.camera.FreeCamera;
import com.gdxengine.framework.interfaces.IComponent;
import com.gdxengine.framework.interfaces.IDrawable3D;
import com.gdxengine.framework.interfaces.IGame3DService;
import com.gdxengine.framework.interfaces.IScene3D;
import com.gdxengine.framework.light3d.DirectionLight;
import com.gdxengine.framework.object3d.Object3DCollection;
import com.gdxengine.framework.object3d.RenderGL;
import com.gdxengine.framework.object3d.SelectVersionGL;


/**
 * 
 * Abstract class for all scene 3D of the game.
 * 3D Objects that implement Drawable3D interface should be added to scene's collection
 * by using addObject3D() method to make the object can be auto-update and auto-render
 * 
 * @author Vinh
 *
 */
public abstract class BaseGameScene3D extends Object3DCollection<IDrawable3D> implements IScene3D , RenderGL {

	/**
	 * the OpenGL
	 */
	GLCommon gl = null;
	/**
	 * Select the version of OpenGL ES depend on capable of device
	 */
	SelectVersionGL selectGL = null;
	
	/**
	 * The value of needContinueInit, if true, it calls initilize() method every time when scene is activated
	 * if false, the initilize() method will be called only one time when the first time the scene is activated. 
	 */
	private boolean needContinueInit = true;
	/**
	 *  indicate the scene is paused
	 */
	private boolean isPaused = false;
	/**
	 * indicate the scene is initialized in first time
	 */
	private boolean initializeTheFirstTime = true;
	/**
	 * View matrix used for draw 2D graphics on 3D Space
	 */
	private Matrix4 viewMatrix = new Matrix4();
	
	/**
	 * @param gameService the service of game
	 */
	public BaseGameScene3D(IGame3DService gameService) {
		this(gameService, true);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param services service of game
	 * @param continueInit if true, it calls initialize() method every time when scene is activated
	 * if false, the initialize() method will be called only one time when the first time the scene is activated. 
	 */
	public BaseGameScene3D(IGame3DService services, boolean continueInit) {

		super(services);
		this.needContinueInit = continueInit;
		selectGL =  getVersionGL();
	}
	
	@Override 
	public void renderGL2(float gameTime)
	{
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		SpriteBatch batch = getSpriteBatch();
		viewMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(viewMatrix);
		batch.begin();
		batch.disableBlending();
		batch.setColor(Color.WHITE);
		//begin render background
		renderBackground(gameTime);
		batch.enableBlending();
		batch.end();
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);
		camera.update();
		
		for(IDrawable3D obj : collection)
			if(obj.isVisible())
			{
				obj.renderGL2(gameTime);
			}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);

		batch.setProjectionMatrix(viewMatrix);
		batch.begin();
		
		//begin render foreground
		renderForeground(gameTime);
		
		//batch.enableBlending();
		//batch.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batch.end();
	}

	@Override 
	public void renderGL1(GL10 gl,float gameTime)
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		SpriteBatch batch = getSpriteBatch();
		viewMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(viewMatrix);
		batch.begin();
		batch.disableBlending();
		//begin render background
		renderBackground(gameTime);
		
		batch.end();
		
		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);

		camera.update();
		camera.apply(gl);
		setLighting(gl);

		for(IDrawable3D obj : collection)
			if(obj.isVisible())
			{
				obj.renderGL1(gl, gameTime);
			}
		
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
		batch.setProjectionMatrix(viewMatrix);
		//batch.setTransformMatrix(transformMatrix);
		batch.begin();
		batch.enableBlending();
		//begin render foreground
		renderForeground(gameTime);
		
		batch.enableBlending();
		batch.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batch.end();
	}
	protected void renderForeground(float gameTime) {
		//batch.draw(texture, 300, 50, 64, 64, 0, 0, 64, 64, false, false);
	}
	protected void setLighting(GL10 gl) {
		
		float[] direction = {1, 0.5f, 0, 0};
		
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
	}

	protected void renderBackground(float gameTime) {
		
		//batch.setColor(Color.WHITE);
		//batch.draw(backgroundTexture, 0, 0, 480, 320, 0, 0, 512, 512, false, false);
		
	}
	
//	private void setProjectionAndCamera() {
//		camera.position.set(0, 6, 2);
//		camera.direction.set(0, 0, -2).sub(camera.position).nor();
//		camera.update();
//	}
	@Override
	public void pause()
	{
		this.isPaused = true;
		for(IComponent com : collection)
		{
			com.pause();
		}
	}
	@Override
	public void resume()
	{
		this.isPaused = false;
		for(IComponent com : collection)
		{
			com.resume();
		}
	}
	@Override
	public void initialize()
	{
		for(IComponent com : collection)
		{
			com.initialize();
		}
		
		preRender();
	}
	
	/**
	 * Call right before starting render stage of Scene 3D
	 */
	protected void preRender() {
		//Create default member for managers if the managers don't have any member. 
		if(camera.count() == 0)
		{
			camera.add("default", new FreeCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector3(0, 5, 5), 0,135));
			camera.setActiveCamera("default");
		}
		if(light.count() == 0 && Game.isSupportOpenGL20)
		{
			light.addBaseLight("default", new DirectionLight(new Vector3(1, 1, 1), new Vector3(1, 1, -1)));
		}
		if(shader.count() == 0 && Game.isSupportOpenGL20)
		{
			String vs = loadShaderFile("effect/default-vs.glsl");
			String fs = loadShaderFile("effect/default-fs.glsl");
			ShaderProgram s = new ShaderProgram(vs , fs);
			if(!s.isCompiled())
			{
				throw new GdxRuntimeException("Cannot compile default shader");
			}
			shader.add("default", s);
			shader.setActiveShader("default");
		}
	}
	private String loadShaderFile(String filename)
	{
		String output = new String();
		URL url = getClass().getResource("/com/gdxengine/framework/" + filename);
		BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = null;

            while ((s = bf.readLine()) != null) {
            	output += s;
            	output += "\n";
            }
            return output.toString();
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error when read file");
        } finally {
            try {
            	bf.close();
            } catch (IOException ex) {
            	throw new GdxRuntimeException("Error when read file");
            }
        }
	}
	@Override
	public void initScene()
	{
		if(initializeTheFirstTime || needContinueInit)
		{
			initialize();
			initializeTheFirstTime = false;
		}
	}
	
	@Override
	public void addObject3D(IDrawable3D basicObject)
	{
		this.collection.add(basicObject);
	}
	@Override
	public void addOrRecycleObject(Object basicObject)
	{
		for(IDrawable3D b : collection)
		{
			if(b.isDead())
			{
				if(b.getClass() == basicObject.getClass())
				{
					b = (IDrawable3D) basicObject;
				}
			}
		}
		
		addItem((IDrawable3D) basicObject);
	}
	
	@Override
	public SelectVersionGL getVersionGL()
	{
		SelectVersionGL selectGL = null;
		if(Game3D.isSupportOpenGL20)
		{
			gl = Gdx.graphics.getGLCommon();
			selectGL = new SelectVersionGL() {
				
				@Override
				public void render(float gameTime) {
					renderGL2(gameTime);
				}
			};
		}
		else
		{
			final GL10 gl = Gdx.graphics.getGL10();
			selectGL = new SelectVersionGL() {
				
				@Override
				public void render(float gameTime) {
					renderGL1(gl, gameTime);
				}
			};
		}
		
		return selectGL;
	}
	
	@Override
	public void update(float gameTime) {
		if(!isPaused)
		{
			for (int i = 0; i < collection.size(); i++) {
				IDrawable3D object = collection.get(i);
				
				if(object.isDead())
				{
					collection.remove(i);
					i--;
					continue;
				}
				if(object.isEnabled())
					object.update(gameTime);
			}
		}
	}

	@Override
	public void render(float gameTime) {	
		selectGL.render(gameTime);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<IDrawable3D> getObjectCollection() {
		return collection;
	}

	@Override
	public boolean isContinueNeedInit() {
		// TODO Auto-generated method stub
		return needContinueInit;
	}

	@Override
	public void setContinueNeedInit(boolean value) {
		needContinueInit = value;
		
	}
	@Override
	public SpriteBatch getSpriteBatch() {
		// TODO Auto-generated method stub
		return services.getSpriteBatch();
	}
}
